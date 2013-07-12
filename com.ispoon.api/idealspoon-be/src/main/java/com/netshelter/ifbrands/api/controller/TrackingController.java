package com.netshelter.ifbrands.api.controller;

import com.netshelter.ifbrands.DetailedError;
import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.ObjectTypeEnum;
import com.netshelter.ifbrands.api.model.campaign.Tracking;
import com.netshelter.ifbrands.api.model.campaign.TrackingSetEnum;
import com.netshelter.ifbrands.api.model.campaign.TrackingTypeEnum;
import com.netshelter.ifbrands.api.service.AdService;
import com.netshelter.ifbrands.api.service.CampaignService;
import com.netshelter.ifbrands.api.service.TrackingService;
import com.netshelter.ifbrands.api.util.MvcUtils;
import com.netshelter.ifbrands.validation.ValidationResponse;
import com.netshelter.ifbrands.validation.ValidationResponseCode;
import com.netshelter.ifbrands.validation.suite.ClickTrackingValidationSuite;
import com.netshelter.ifbrands.validation.suite.ImpressionTrackingValidationSuite;
import com.netshelter.ifbrands.validation.suite.ScriptTrackingValidationSuite;
import com.netshelter.ifbrands.validation.suite.ValidationSuite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitriy T
 */
@Controller( "trackingController" )
@RequestMapping( "/tracking" )
public class TrackingController
    extends BaseController
{
    @Autowired
    private TrackingService trackingService = null;
    @Autowired
    private AdService adService = null;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object create(
            @RequestParam( value = "objectid") Integer objectId,
            @RequestParam( value = "objecttype") String objectType,
            @RequestParam( value = "trackingset") String trackingSet,
            @RequestParam( value = "trackingtype") String trackingType,
            @RequestParam( value = "textvalue") String textValue,
            @RequestParam( value = "validate", defaultValue = "false") Boolean validate

    )
    {
        logger.info( "POST /tracking/ [%s,%s,%s,%s] validate(%s)",
                     objectId, objectType, trackingSet, trackingType, validate);
        if (validate)
        {
            List<Map> objects = new ArrayList<Map>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("trackingType", trackingType);
            map.put("textValue", textValue);
            objects.add(map);
            List<ValidationResponse> validationResponseList = trackingService.validateTrackingEntries(objects);
            for (ValidationResponse validationResponse : validationResponseList)
            {
                if (validationResponse != null && validationResponse.getResponseCode() != ValidationResponseCode.OK)
                {
                    return validationResponseList;
                }
            }
        }
        Tracking result = trackingService.create(
                ObjectTypeEnum.valueOf(objectType),
                objectId,
                TrackingSetEnum.valueOf(trackingSet),
                TrackingTypeEnum.valueOf(trackingType),
                textValue
        );
        logger.debug("...Created %s", result.toString());
        return result;
    }

    /**
     * Returns a collection of tracking objects.
     * Ticket: http://jira.netshelter.net/browse/DEV-7075
     * @param trackingId
     * @param objectId
     * @param objectType
     * @param trackingSet
     * @param trackingType
     * @return
     */
    @RequestMapping( value = "/list", method = RequestMethod.GET )
    @ResponseBody
    public GenericPayload<Collection<Tracking>> listByAny(
            @RequestParam( value = "trackingid",            required = false) Integer trackingId,
            @RequestParam( value = "objectid",              required = false) Integer objectId,
            @RequestParam( value = "objecttype",            required = false) ObjectTypeEnum objectType,
            @RequestParam( value = "trackingset",           required = false) TrackingSetEnum trackingSet,
            @RequestParam( value = "trackingtype",          required = false) TrackingTypeEnum trackingType

    )
    {
        logger.info( "GET /tracking/list by [%s,%s,%s,%s,%s]",
                     trackingId, objectId, objectType, trackingSet, trackingType);
        List<Tracking> result = trackingService.getByAny(
                trackingId,
                objectType,
                objectId,
                trackingSet,
                trackingType
        );
        if (objectId != null &&
                objectType != null &&
                objectType == ObjectTypeEnum.Ad &&
                result.size() == 0)
        {
            // in this case get the parent campaign and get tracking for it...
            Ad ad = adService.getAd(objectId);
            if (ad != null)
            {
                result = trackingService.getByAny(
                        trackingId,
                        ObjectTypeEnum.Campaign,
                        ad.getCampaign().getId(),
                        trackingSet,
                        trackingType
                );
            }
        }
        logger.debug("...%d found", result.size());
        return new GenericPayload<Collection<Tracking>>( "trackingList", result );
    }

    @RequestMapping( value = "/list/{ids}", method = RequestMethod.GET )
    @ResponseBody
    public GenericPayload<Collection<Tracking>> getByIds(
            @PathVariable( "ids" ) String ids
    )
    {
        List<Integer> trackingIds = MvcUtils.getIdsFromFilter(ids);
        logger.info("GET /tracking/list (by %s ids)", trackingIds.size());
        Collection<Tracking> result = trackingService.all(trackingIds);
        logger.debug("...%d found", result.size());
        return new GenericPayload<Collection<Tracking>>( "campaignTrackingList", result );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.POST )
    @ResponseBody
    public Object update(
            @PathVariable( value = "id")                                      Integer trackingId,
            @RequestParam( value = "objectid",              required = false) Integer objectId,
            @RequestParam( value = "objecttype",            required = false) ObjectTypeEnum objectType,
            @RequestParam( value = "trackingset",           required = false) TrackingSetEnum trackingSet,
            @RequestParam( value = "trackingtype",          required = false) TrackingTypeEnum trackingType,
            @RequestParam( value = "textvalue",             required = false) String textValue,
            @RequestParam( value = "validate", defaultValue = "false") Boolean validate

    )
    {
        logger.info( "POST /tracking [%s,%s,%s,%s,%s] validate(%s)",
                     trackingId, objectId, objectType, trackingSet, trackingType, validate);
        if (validate)
        {
            Tracking tracking = trackingService.get(trackingId);
            if (tracking != null)
            {
                List<Map> objects = new ArrayList<Map>();
                Map<String, Object> map = new HashMap<String, Object>();
                if (trackingType != null)
                {
                    map.put("trackingType", trackingType);
                }
                else
                {
                    map.put("trackingType", tracking.getTrackingType().name());
                }
                if (textValue != null)
                {
                    map.put("textValue", textValue);
                }
                else
                {
                    map.put("textValue", tracking.getTextValue());
                }

                objects.add(map);
                List<ValidationResponse> validationResponseList = trackingService.validateTrackingEntries(objects);
                for (ValidationResponse validationResponse : validationResponseList)
                {
                    if (validationResponse != null && validationResponse.getResponseCode() != ValidationResponseCode.OK)
                    {
                        return validationResponseList;
                    }
                }
            }
            else
            {
                return null;
            }
        }
        Tracking result = trackingService.update(
                trackingId,
                objectType,
                objectId,
                trackingSet,
                trackingType,
                textValue
        );
        logger.debug("Tracking %d updated", result.getTrackingId());
        return result;
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    @ResponseBody
    public Boolean delete(
            @PathVariable( value = "id") Integer crackingId
    )
    {
        return trackingService.delete(crackingId);
    }

    @RequestMapping( value = "/trackingset/list", method = RequestMethod.GET )
    @ResponseBody
    public List<TrackingSetEnum> getTrackingSetList()
    {
        return Arrays.asList(TrackingSetEnum.values());
    }

    @RequestMapping( value = "/trackingtype/list", method = RequestMethod.GET )
    @ResponseBody
    public List<TrackingTypeEnum> getTrackingTypeList()
    {
        return Arrays.asList(TrackingTypeEnum.values());
    }

    @RequestMapping( value = "/objecttype/list", method = RequestMethod.GET )
    @ResponseBody
    public List<ObjectTypeEnum> getObjectTypeList()
    {
        return Arrays.asList(ObjectTypeEnum.values());
    }

    /*
    TODO: Test with this data:
[{
"trackingType": "ClickImpression",
"textValue": "https://www.google.com"
},{
"trackingType": "Impression",
"textValue": "http://www.textfiles.com/100/914bbs.txt"
},{
"trackingType": "Impression",
"textValue": "http://thatgamecompany.com/wp-content/themes/thatgamecompany/_include/img/flower/flower-game-screenshot-2.jpg"
},{
"trackingType": "Click",
"textValue": "http://thatgamecompany.com/wp-content/themes/thatgamecompany/_include/img/flower/flower-game-screenshot-2.jpg"
},{
"trackingType": "JavaScript",
"textValue": "https://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"
},{
"trackingType": "JavaScript",
"textValue": "https://ajax.googleapis.com/ajax/libs/jquery/1.8.0/THIS-IS-A-BAD-URL"
},{
"trackingType": "NonExistingTrackingType",
"textValue": "bla bla bla"
}]
     */
    @RequestMapping( value = "/validate/json",
                     method = RequestMethod.POST,
                     headers="Content-Type=application/json")
    @ResponseBody
    public List<ValidationResponse> validate(@RequestBody List<Map> objects)
    {
        return trackingService.validateTrackingEntries(objects);
    }
}
