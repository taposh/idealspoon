package com.netshelter.ifbrands.api.controller;

import java.util.Collection;
import java.util.List;

import com.netshelter.ifbrands.api.model.entity.Template;
import com.netshelter.ifbrands.api.service.AdTagGenerationService;
import com.netshelter.ifbrands.api.service.NotesService;
import com.netshelter.ifbrands.api.service.TemplateService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.AdStatus;
import com.netshelter.ifbrands.api.model.campaign.AdType;
import com.netshelter.ifbrands.api.service.AdService;
import com.netshelter.ifbrands.api.util.MvcUtils;
import com.netshelter.ifbrands.util.KeyGeneratorUtils.KeyGenerator;

/**
 * Controller for 'ad' API calls, including related relations (AdType).
 * An Ad belongs to a Campaign and owns a Feed.
 * @author bgray
 *
 */
@Controller( "adController" )
@RequestMapping( "/ad" )
public class AdController extends BaseController
{
    @Autowired
    private AdService adService = null;
    @Autowired
    private KeyGenerator keyGenerator = null;
    @Autowired
    private NotesService notesService = null;
    @Autowired
    AdTagGenerationService adTagGenerationService = null;
    @Autowired
    TemplateService templateService = null;

    /** Flush the cache. */
    @RequestMapping( value = "/flush" )
    @ResponseBody
    public GenericStatus flushCache()
    {
        adService.flushCache();
        return GenericStatus.okay( AdService.AD_CACHE + " cache flushed" );
    }

    ///////////////////
    // Ad Management //
    ///////////////////

    /**
     * Create a new Ad object.  A unqiue key will be assigned.
     *
     * @param name
     * @param typeId
     * @param statusId
     * @param feedId
     * @param campaignId
     * @param adNetworkId - optional param
     * @param adSizeId - optional param
     * @return
     */
    @RequestMapping( value = "/create" )
    @ResponseBody
    public Ad createAd( @RequestParam( "name"     )             String name,
                        @RequestParam( "type"     )             Integer typeId,
                        @RequestParam( "status"   )             Integer statusId,
                        @RequestParam( "feed"     )             Integer feedId,
                        @RequestParam( "campaign" )             Integer campaignId,
                        @RequestParam( value = "adnetworkid", defaultValue = "0")       String adNetworkId,
                        @RequestParam( value = "adsizeid",    defaultValue = "0")       String adSizeId,
                        @RequestParam( value = "adstateid",    defaultValue = "1")       String adStateId,
                        @RequestParam(value = "start", required = false) LocalDate start,
                        @RequestParam(value ="stop", required = false) LocalDate stop,
                        @RequestParam(value = "actionuserkey", required = false) Integer actionUserKey,
                        @RequestParam(value = "templatekey", required = false) String templateKey)
    {
        logger.info( "/ad/create [%s,%s,%s,%s,%s,%s,%s]", name, typeId, statusId, feedId, campaignId,start,stop );
        String key = keyGenerator.generateKey();
        Ad ad = adService.createAd( key, name, typeId, statusId, feedId, campaignId, Integer.parseInt( adNetworkId ), Integer.parseInt( adSizeId ),
                Integer.parseInt(adStateId),start,stop, actionUserKey, templateKey );
        logger.debug( "...Created %s", ad.toString() );
        return ad;
    }

    /**
     * Delete an Ad object.
     * @param adId ID of Ad to delete
     * @return GenericStatus showing success/failure.
     */
    @RequestMapping( value = "/delete/{id}" )
    @ResponseBody
    public GenericStatus deleteAd( @PathVariable( "id" ) Integer adId )
    {
        logger.info( "/ad/delete/%s", adId );
        boolean success = adService.deleteAd(adId);
        return GenericStatus.successFail( success );
    }

    /**
     * Updae an Ad object.  Leave a field null to not modify.
     *
     * @param adId
     * @param name - optional parameter
     * @param typeId - optional parameter
     * @param statusId - optional parameter
     * @param adNetworkIdStr - optional parameter
     * @param adSizeIdStr - optional parameter
     * @return
     */
    @RequestMapping( value = "/update/{id}" )
    @ResponseBody
    public Ad updateAd( @PathVariable( "id" ) Integer adId,
                        @RequestParam( value="name"  , required=false ) String name,
                        @RequestParam( value="type"  , required=false ) Integer typeId,
                        @RequestParam( value="status", required=false ) Integer statusId,
                        @RequestParam( value = "adnetworkid", required = false)           String adNetworkIdStr,
                        @RequestParam( value = "adsizeid", required = false)              String adSizeIdStr,
                        @RequestParam( value = "adstateid",  required = false)            Integer adStateId,
                        @RequestParam(value = "start", required = false) LocalDate start,
                        @RequestParam(value ="stop", required = false) LocalDate stop,
                        @RequestParam(value = "actionuserkey", required = false) Integer actionUserKey,
                        @RequestParam(value = "templatekey", required = false) String templateKey)

    {
        logger.info( "/ad/update/%s [%s,%s,%s,%s,%s]", adId, name, typeId, statusId,start,stop );
        Integer adNetworkId = null;
        if (StringUtils.isNotBlank( adNetworkIdStr ))
        {
            adNetworkId = Integer.parseInt( adNetworkIdStr );
        }
        Integer adSizeId = null;
        if (StringUtils.isNotBlank( adSizeIdStr ))
        {
            adSizeId = Integer.parseInt( adSizeIdStr );
        }
        return adService.updateAd(adId, name, typeId, statusId, adNetworkId, adSizeId, adStateId,
                                  start, stop, actionUserKey, templateKey);
    }

    /**
     * Get the set of Ads according to a set of filters.  This may return an empty set.
     * @param adFilter Set of IDs on which to filter (comma-separated).  Use "-" for all IDs.
     * @return set of filtered objects
     */
    @RequestMapping( value = "/{ids}" )
    @ResponseBody
    public GenericPayload<Collection<Ad>> getAds( @PathVariable( "ids" ) String adFilter,
                                                  @RequestParam( value="key"     , required=false ) List<String>  adKeys,
                                                  @RequestParam( value="type"    , required=false ) List<Integer> adTypeIds,
                                                  @RequestParam( value="status"  , required=false ) List<Integer> adStatusIds,
                                                  @RequestParam( value="campaign", required=false ) List<Integer> campaignIds )
    {
        logger.info( "/ad/%s [%s,%s,%s,%s]", adFilter, adKeys, adTypeIds, adStatusIds, campaignIds );
        List<Integer> adIds = MvcUtils.getIdsFromFilter( adFilter );
        Collection<Ad> ads = adService.getAds( adIds, adKeys, adTypeIds, adStatusIds, campaignIds );
        logger.debug("...%d found", ads.size());
        return new GenericPayload<Collection<Ad>>( "ads", ads );
    }

    /**
     * Creates a new Ad (clone) object based on existing Ad object with existing Ad id = adId
     * Also creates Feed and FeedStories associated with newly created Ad similar to existing Ad object.
     * @param adId
     * @param name
     * @return - newly created Ad object
     */
    @RequestMapping( value = "/clone" )
    @ResponseBody
    public Ad createSimilarAd(
            @RequestParam( value = "id",          required = true) Integer adId,
            @RequestParam( value = "name",        required = true) String name,
            @RequestParam( value = "type",        required = false) Integer adTypeId,
            @RequestParam( value = "adsizeid",    required = false) Integer adSizeId,
            @RequestParam( value = "adnetworkid", required = false) Integer adNetworkId,
            @RequestParam( value = "actionuserkey", required = false) Integer actionUserKey,
            @RequestParam(value = "templatekey", required = false) String templateKey
    )
    {
        logger.info( "/ad/clone [%s, %s]", adId, name );
        return adService.cloneAd( adId, name, adTypeId, adSizeId, adNetworkId, actionUserKey, templateKey );
    }



    ///////////////////////
    // AdType management //
    ///////////////////////
    /**
     * Create an AdType object.
     * @param name Name of AdType (must be unique)
     * @return new AdType object
     */
    @RequestMapping( value = "/type/create" )
    @ResponseBody
    public AdType createAdType( @RequestParam( "name" ) String name )
    {
        logger.info( "/ad/type/create [%s]", name );
        AdType adType = adService.createAdType( name );
        logger.debug( "... Created: %s", adType.toString() );
        return adType;
    }

    /**
     * Delete an AdType object.
     * @param adTypeId ID of AdType to delete
     * @return GenericStatus showing success/failure.
     */
    @RequestMapping( value = "/type/delete/{id}" )
    @ResponseBody
    public GenericStatus deleteAdType( @PathVariable( "id" ) Integer adTypeId )
    {
        logger.info( "/ad/type/delete/%s", adTypeId );
        boolean success = adService.deleteAdType(adTypeId );
        return GenericStatus.successFail( success );
    }

    /**
     * Get the set of AdTypes according to a set of filters.  This may return an empty set.
     * @param - adFilter Set of IDs on which to filter (comma-separated).  Use "-" for all IDs.
     * @return set of filtered objects
     */
    @RequestMapping( value = "/type/{ids}" )
    @ResponseBody
    public GenericPayload<Collection<AdType>> getAdType( @PathVariable( "ids" ) String filter )
    {
        logger.info( "/ad/type/%s", filter );
        List<Integer> ids = MvcUtils.getIdsFromFilter( filter );
        Collection<AdType> adTypes = adService.getAdTypes( ids );
        logger.debug( "...%d found", adTypes.size() );
        return new GenericPayload<Collection<AdType>>( "adTypes", adTypes );
    }



    ///////////////////////
    // AdStatus management //
    ///////////////////////
    /**
     * Create an AdStatus object.
     * @param name Name of AdStatus (must be unique)
     * @return new AdStatus object
     */
    @RequestMapping( value = "/status/create" )
    @ResponseBody
    public AdStatus createAdStatus( @RequestParam( "name" ) String name )
    {
        logger.info( "/ad/status/create [%s]", name );
        AdStatus adStatus = adService.createAdStatus( name );
        logger.debug( "... Created: %s", adStatus.toString() );
        return adStatus;
    }

    /**
     * Delete an AdStatus object.
     * @param adStatusId ID of AdStatus to delete
     * @return GenericStatus showing success/failure.
     */
    @RequestMapping( value = "/status/delete/{id}" )
    @ResponseBody
    public GenericStatus deleteAdStatus( @PathVariable( "id" ) Integer adStatusId )
    {
        logger.info( "/ad/status/delete/%s", adStatusId );
        boolean success = adService.deleteAdStatus(adStatusId );
        return GenericStatus.successFail( success );
    }

    /**
     * Get the set of AdStatuses according to a set of filters.  This may return an empty set.
     * @param - adFilter Set of IDs on which to filter (comma-separated).  Use "-" for all IDs.
     * @return set of filtered objects
     */
    @RequestMapping( value = "/status/{ids}" )
    @ResponseBody
    public GenericPayload<Collection<AdStatus>> getAdStatus( @PathVariable( "ids" ) String filter )
    {
        logger.info( "/ad/status/%s", filter );
        List<Integer> ids = MvcUtils.getIdsFromFilter( filter );
        Collection<AdStatus> adStatuses = adService.getAdStatuses( ids );
        logger.debug( "...%d found", adStatuses.size() );
        return new GenericPayload<Collection<AdStatus>>( "adStatuses", adStatuses );
    }

    /**
     * Get story referencers (Ads, Campaigns, etc.)
     */
    @RequestMapping( value = "/story/{ids}" )
    @ResponseBody
    public GenericPayload<MultiValueMap<Integer, Ad>> getStoryContainers( @PathVariable( "ids" ) List<Integer> storyIds,
                                                                          @RequestParam( value="ad"             , required=false ) List<Integer> adIds,
                                                                          @RequestParam( value="adtype"         , required=false ) List<Integer> adTypeIds,
                                                                          @RequestParam( value="adstatus"       , required=false ) List<Integer> adStatusIds,
                                                                          @RequestParam( value="campaign"       , required=false ) List<Integer> campaignIds,
                                                                          @RequestParam( value="campaigntype"   , required=false ) List<Integer> campaignTypeIds,
                                                                          @RequestParam( value="campaignstatus" , required=false ) List<Integer> campaignStatusIds,
                                                                          @RequestParam( value="brand"          , required=false ) List<Integer> brandIds )
    {
        logger.info(  "/ad/story [%s,%s,%s,%s,%s,%s,%s,%s]", storyIds, adIds, adTypeIds, adStatusIds, campaignIds, campaignTypeIds, campaignStatusIds, brandIds );

        MultiValueMap<Integer, Ad> stories = adService.getStoryContainers( storyIds, adIds, adTypeIds,
                adStatusIds, campaignIds, campaignTypeIds,
                campaignStatusIds, brandIds );

        return new GenericPayload<MultiValueMap<Integer, Ad>>( "storyToAdsMap", stories );
    }
    
    
    /**
     * Get Ad Tag
     * @param adId ID of Ad to get tag for
     * @return Ad tag
     */
    @RequestMapping( value = "/tag/{id}" )
   // @ResponseBody
    public String getAdTag( @PathVariable( "id" ) Integer adId )
    {
        logger.info( "/ad/getTag/%s", adId );
        
      
        String result = adTagGenerationService.createTag(1, "example.vm");
        return result;
        //String  tag = adService.getAdTag(adId);
        //return new GenericPayload<String>("tag",tag);
      //  System.out.println(result);
        
    }

    @RequestMapping( value = "/templatelist" )
    @ResponseBody
    public List<Template> getTemplateList()
    {
        logger.info( "/ad/templatelist" );
        return templateService.getAllList();
    }
    
}
