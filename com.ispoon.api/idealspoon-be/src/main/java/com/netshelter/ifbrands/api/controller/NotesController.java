package com.netshelter.ifbrands.api.controller;

import com.netshelter.ifbrands.api.service.NotesService;
import com.netshelter.ifbrands.api.model.campaign.Notes;
import com.netshelter.ifbrands.api.util.MvcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * User: taposhdr
 * Date: 6/28/13
 */

@Controller( "NotesController" )
@RequestMapping( "/notes" )
public class NotesController
        extends BaseController
{

    @Autowired
    private NotesService notesService = null;

    @RequestMapping(method = RequestMethod.POST,value = "/")
    @ResponseBody
    public Notes create(
            @RequestParam( value = "notestext") String notesText,
            @RequestParam( value = "campaignid") Integer campaignId,
            @RequestParam( value = "userkey") Integer userKey
    )
    {
        logger.info( "PUT /notes/ [%s,%s,%s]", notesText, campaignId, userKey);
        Notes result = notesService.create(userKey, campaignId, notesText);
        logger.debug("...Created %s", result.toString());
        return result;
    }


    @RequestMapping(method = RequestMethod.POST,value = "/{notesid}")
    @ResponseBody
    public Notes update(
            @PathVariable("notesid") Integer notesId,
            @RequestParam( value = "notestext", required = false) String notesText,
            @RequestParam( value = "campaignid", required = false) Integer campaignId,
            @RequestParam( value = "userkey", required = false) Integer userKey
    )
    {
        logger.info( "POST /notes/ [%s,%s,%s,%s]", notesId, notesText, campaignId, userKey);
        Notes result = notesService.update(notesId, userKey, campaignId, notesText);
        logger.debug("...Updated %s", result.toString());
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{notesidcsv}")
    @ResponseBody
    public List<Notes> get(
            @PathVariable("notesidcsv") String notesIdCsv
    )
    {
        logger.info( "GET /notes/ [%s]", notesIdCsv);
        List<Integer> notesIds = MvcUtils.getIdsFromFilter(notesIdCsv);
        return notesService.getList(notesIds);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/campaign/{campaignidcsv}")
    @ResponseBody
    public Map<Integer, List<Notes>> getByCampaign(
            @PathVariable("campaignidcsv") String campaignIdCsv
    )
    {
        logger.info( "GET /notes/campaign/ [%s]", campaignIdCsv);
        List<Integer> campaignIds = MvcUtils.getIdsFromFilter(campaignIdCsv);
        return notesService.getListByCampaignIds(campaignIds);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/{notesid}")
    @ResponseBody
    public Boolean delete(
            @PathVariable("notesid") Integer notesId
    )
    {
        logger.info( "DELETE /notes/ [%s]", notesId);
        return notesService.delete(notesId);
    }

}

