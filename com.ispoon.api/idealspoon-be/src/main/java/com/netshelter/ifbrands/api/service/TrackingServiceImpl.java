package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.DetailedError;
import com.netshelter.ifbrands.api.model.campaign.ObjectTypeEnum;
import com.netshelter.ifbrands.api.model.campaign.Tracking;
import com.netshelter.ifbrands.api.model.campaign.TrackingSetEnum;
import com.netshelter.ifbrands.api.model.campaign.TrackingTypeEnum;
import com.netshelter.ifbrands.data.dao.TrackingDao;
import com.netshelter.ifbrands.data.entity.IfbTracking;
import com.netshelter.ifbrands.etl.transform.campaign.TrackingTransformer;
import com.netshelter.ifbrands.validation.ValidationResponse;
import com.netshelter.ifbrands.validation.suite.ClickTrackingValidationSuite;
import com.netshelter.ifbrands.validation.suite.ImpressionTrackingValidationSuite;
import com.netshelter.ifbrands.validation.suite.ScriptTrackingValidationSuite;
import com.netshelter.ifbrands.validation.suite.ValidationSuite;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitriy T
 */
public class TrackingServiceImpl
    implements TrackingService
{

    public static final String CAMPAIGN_TRACKING_CACHE = "ifb.mvc.tracking";

    @Autowired
    TrackingDao dao;
    @Autowired
    TrackingTransformer transformer;

    @Override
    @CacheEvict(value = CAMPAIGN_TRACKING_CACHE, allEntries = true)
    public void flushCache()
    {
        CacheManager manager = CacheManager.getInstance();
        if (manager != null)
        {
            Cache cache = manager.getCache(CAMPAIGN_TRACKING_CACHE);
            if (cache != null)
            {
                cache.removeAll();
            }
        }
    }

    @Override
    public Tracking create(ObjectTypeEnum objectType, Integer objectId, TrackingSetEnum trackingSet,
                                   TrackingTypeEnum trackingType, String textValue)
    {
        IfbTracking tracking = new IfbTracking();
        tracking.setObjectId(objectId);
        tracking.setObjectType(objectType.name());
        tracking.setTrackingSet(trackingSet.name());
        tracking.setTrackingType(trackingType.name());
        tracking.setTextValue(textValue);
        tracking.setCreateTimestamp(new Date());
        dao.save( tracking );
        flushCache();
        return transformer.transform(tracking);
    }

    @Override
    @Cacheable(CAMPAIGN_TRACKING_CACHE)
    public List<Tracking> getByAny(Integer trackingId,
                                   ObjectTypeEnum objectType,
                                   Integer objectId,
                                   TrackingSetEnum trackingSet,
                                   TrackingTypeEnum trackingType)
    {
        return transformer.transform(dao.findByAny(trackingId, objectId, objectType, trackingSet, trackingType));
    }

    @Cacheable(CAMPAIGN_TRACKING_CACHE)
    public Tracking get(Integer trackingId)
    {
        return transformer.transform(dao.getById(trackingId));
    }

    @Override
    public Tracking update(Integer trackingId,
                           ObjectTypeEnum objectType,
                           Integer objectId,
                           TrackingSetEnum trackingSet,
                           TrackingTypeEnum trackingType,
                           String textValue)
    {
        Tracking result = null;
        IfbTracking found = dao.getById( trackingId );
        if (found != null)
        {
            if (objectId != null)
            {
                found.setObjectId(objectId);
            }
            if (objectType != null)
            {
                found.setObjectType(objectType.name());
            }
            if (trackingSet != null)
            {
                found.setTrackingSet( trackingSet.name() );
            }
            if (trackingType != null)
            {
                found.setTrackingType( trackingType.name() );
            }
            if (textValue != null)
            {
                found.setTextValue( textValue );
            }
            dao.update(found);
            result = transformer.transform(found);
            flushCache();
        }
        return result;
    }

    @Override
    public boolean delete( Integer campaignTrackingId )
    {
        boolean result = false;
        IfbTracking found = dao.getById( campaignTrackingId );
        if (found != null)
        {
            dao.delete(found);
            result = true;
        }
        flushCache();
        return result;
    }

    @Cacheable(CAMPAIGN_TRACKING_CACHE)
    public List<Tracking> all( List<Integer> campaignTrackingIds )
    {
        List<Tracking> result = null;
        if (campaignTrackingIds != null && campaignTrackingIds.size() > 0)
        {
            result = transformer.transform( dao.getByIds( campaignTrackingIds ) );
        }
        return result;
    }

    public List<ValidationResponse> validateTrackingEntries(List<Map> objects)
    {
        List<ValidationResponse> response = new ArrayList<ValidationResponse>();
        if (objects != null && objects.size() > 0)
        {
            for (Map map : objects)
            {
                String trackingType = (String)map.get("trackingType");
                String textValue = (String)map.get("textValue");
                if (TrackingTypeEnum.ClickImpression.name().equals(trackingType) ||
                        TrackingTypeEnum.Impression.name().equals(trackingType))
                {
                    ValidationSuite validationSuite = new ImpressionTrackingValidationSuite(textValue);
                    ValidationResponse resp = validationSuite.validate();
                    Tracking tracking = new Tracking(null, null, null, null, TrackingTypeEnum.valueOf(trackingType), textValue, null);
                    if (resp.getResponse() == null) // if the object was changed by validation suite - do not update it
                    {
                        resp.setResponse(tracking);
                    }
                    response.add(resp);
                }
                else
                if (TrackingTypeEnum.Click.name().equals(trackingType))
                {
                    ValidationSuite validationSuite = new ClickTrackingValidationSuite(textValue);
                    ValidationResponse resp = validationSuite.validate();
                    Tracking tracking = new Tracking(null, null, null, null, TrackingTypeEnum.valueOf(trackingType), textValue, null);
                    if (resp.getResponse() == null) // if the object was changed by validation suite - do not update it
                    {
                        resp.setResponse(tracking);
                    }
                    response.add(resp);
                }
                else
                if (TrackingTypeEnum.JavaScript.name().equals(trackingType))
                {
                    ValidationSuite validationSuite = new ScriptTrackingValidationSuite(textValue);
                    ValidationResponse resp = validationSuite.validate();
                    Tracking tracking = new Tracking(null, null, null, null, TrackingTypeEnum.valueOf(trackingType), textValue, null);
                    if (resp.getResponse() == null) // if the object was changed by validation suite - do not update it
                    {
                        resp.setResponse(tracking);
                    }
                    response.add(resp);
                }
                else
                {
                    ValidationResponse resp = new ValidationResponse();
                    resp.setError(new DetailedError("Tracking type ["+trackingType+"] is not valid", null, null, null, null));
                    Tracking tracking = new Tracking(null, null, null, null, null, textValue, null);
                    resp.setResponse(tracking);
                    response.add(resp);
                }
            }
        }
        return response;
    }
}
