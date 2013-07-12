package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.AdNetwork;
import com.netshelter.ifbrands.api.model.campaign.AdSize;
import com.netshelter.ifbrands.api.model.campaign.AdState;
import com.netshelter.ifbrands.api.model.campaign.AdStatus;
import com.netshelter.ifbrands.api.model.campaign.AdType;
import com.netshelter.ifbrands.api.model.campaign.Feed;
import com.netshelter.ifbrands.api.model.campaign.FeedStory;
import com.netshelter.ifbrands.api.model.campaign.ObjectTypeEnum;
import com.netshelter.ifbrands.api.model.campaign.Tracking;
import com.netshelter.ifbrands.api.util.GenerateThumbnailsException;
import com.netshelter.ifbrands.data.dao.AdDao;
import com.netshelter.ifbrands.data.dao.AdNetworkDao;
import com.netshelter.ifbrands.data.dao.AdSizeDao;
import com.netshelter.ifbrands.data.dao.CampaignDao;
import com.netshelter.ifbrands.data.dao.FeedDao;
import com.netshelter.ifbrands.data.entity.IfbAd;
import com.netshelter.ifbrands.data.entity.IfbAdState;
import com.netshelter.ifbrands.data.entity.IfbAdStatus;
import com.netshelter.ifbrands.data.entity.IfbAdType;
import com.netshelter.ifbrands.etl.transform.campaign.AdNetworkTransformer;
import com.netshelter.ifbrands.etl.transform.campaign.AdSizeTransformer;
import com.netshelter.ifbrands.etl.transform.campaign.AdTransformer;
import com.netshelter.ifbrands.util.KeyGeneratorUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdServiceImpl
        implements AdService
{
    @Autowired
    private AdDao adDao;
    @Autowired
    private AdDao.AdTypeDao adTypeDao;
    @Autowired
    private AdSizeDao adSizeDao;
    @Autowired
    private AdNetworkDao adNetworkDao;
    @Autowired
    private FeedDao feedDao;
    @Autowired
    private CampaignDao campaignDao;
    @Autowired
    private AdTransformer adTransformer;
    @Autowired
    private AdSizeTransformer adSizeTransformer;
    @Autowired
    private AdNetworkTransformer adNetworkTransformer;
    @Autowired
    private FeedService feedService;
    @Autowired
    private KeyGeneratorUtils.KeyGenerator keyGenerator;
    @Autowired
    private TrackingService trackingService;
    @Autowired
    NotesService notesService = null;

    @Override
//  @CollectionEvict( cacheName = AD_CACHE, removeAll=true )
    public void flushCache()
    {
    }

    ///////////////////
    // Ad Management //
    ///////////////////
    @Override
    public Ad createAd(String key, String name, Integer typeId, Integer statusId, Integer feedId, Integer campaignId,
                       Integer adNetworkId, Integer adSizeId, Integer adStateId, LocalDate start,
                       LocalDate stop, Integer actionUserKey, String templateKey)
    {
        IfbAd ad = new IfbAd();
        ad.setAdKey(key);
        ad.setAdName(name);
        ad.setIfbFeed(feedDao.getProxyById(feedId));
        ad.setIfbAdType(adDao.adTypeDao.getProxyById(typeId));
        ad.setIfbAdStatus(adDao.adStatusDao.getProxyById(statusId));
        ad.setIfbAdState(adDao.adStateDao.getProxyById(adStateId));
        ad.setIfbCampaign(campaignDao.getProxyById(campaignId));
        if (templateKey != null) ad.setTemplateKey(templateKey);
        if (start != null) ad.setStartDate(start.toDate());
        if (stop != null)ad.setStopDate(stop.toDate());
        if (adSizeId == null)
        {
            adSizeId = 0;
        }
        ad.setAdSizeId(adSizeId);
        ad.setCreateTimestamp(new Date());
        if (adNetworkId == null)
        {
            adNetworkId = 0;
        }
        ad.setAdNetworkId(adNetworkId);
        ad = adDao.save(ad);

        if (ad != null && campaignId != null && actionUserKey != null)
        {
            String adName = createAdTextForNote(ad);
            notesService.create(
                    actionUserKey,
                    campaignId,
                    String.format(NotesService.NOTE_TEMPLATE_AD_CREATED, adName)
            );
        }

        return getAd(ad.getAdId());
    }

    @Override
    public boolean deleteAd(Integer adId)
    {
        boolean success = true;
        try
        {
            adDao.delete(adDao.getById(adId));
        }
        catch (Exception x)
        {
            success = false;
        }
        return success;
    }

    @Override
    public Ad updateAd(Integer adId, String name, Integer typeId, Integer statusId,
                       Integer adNetworkId, Integer adSizeId,
                       Integer adStateId,LocalDate start, LocalDate stop, Integer actionUserKey,
                       String templateKey)
    {
        IfbAd ad = adDao.getById(adId);
        if (ad == null) throw new IllegalArgumentException("Ad not found");
        if (name != null) ad.setAdName(name);
        if (typeId != null) ad.setIfbAdType(adDao.adTypeDao.getProxyById(typeId));
        if (statusId != null) ad.setIfbAdStatus(adDao.adStatusDao.getProxyById(statusId));
        if (adNetworkId != null) ad.setAdNetworkId(adNetworkId);
        if (adSizeId != null) ad.setAdSizeId(adSizeId);
        if (adStateId != null) ad.setIfbAdState(adDao.adStateDao.getProxyById(adStateId));
        if (start != null) ad.setStartDate(start.toDate());
        if (stop != null)ad.setStopDate(stop.toDate());
        if (templateKey != null) ad.setTemplateKey(templateKey);
        adDao.update(ad);

        Ad result = getAd(adId);

        if (result != null && result.getCampaign().getId() != null && adStateId != null && actionUserKey != null)
        {
            String adName = createAdTextForNote(ad);
            IfbAdState adState = adDao.adStateDao.getById(adStateId);
            notesService.create(
                    actionUserKey,
                    ad.getIfbCampaign().getCampaignId(),
                    String.format(NotesService.NOTE_TEMPLATE_AD_STATE_CHANGED, adName, adState.getAdStateName())
            );
        }

        return result;
    }

    private String createAdTextForNote(IfbAd ad)
    {
        StringBuffer adName = new StringBuffer();
        adName.append(ad.getAdName());
        if (ad.getAdNetworkId() != null && ad.getAdNetworkId() > 0)
        {
            adName.append(" ");
            adName.append(adNetworkDao.getById( ad.getAdNetworkId() ).getAdNetworkName());
        }
        if (ad.getAdSizeId() > 0)
        {
            adName.append(" / ");
            adName.append(adSizeDao.getById( ad.getAdSizeId() ).getAdSizeName());
        }
        return adName.toString();
    }

    @Override
    public Ad getAd(Integer adId)
    {
        return adTransformer.transform(adDao.getById(adId));
    }

    @Override
    public Collection<Ad> getAds(Collection<Integer> adIds,
                                 Collection<String> adKeys,
                                 Collection<Integer> adTypeIds,
                                 Collection<Integer> adStatusIds,
                                 Collection<Integer> campaignIds)
    {
        Collection<IfbAd> ads = adDao.getByAny(adIds, adKeys, adTypeIds, adStatusIds, campaignIds);
        List<Ad> result = getTrackingSource(adTransformer.transform(ads));
        return result;
    }

    /**
     * Returns a "changed" list of Ads will trackingSource field entered with Ad, Campaign or null depending on
     * tracking type found...
     *
     * @param ads
     * @return
     */
    private List<Ad> getTrackingSource(List<Ad> ads)
    {
        if (ads != null && ads.size() > 0)
        {
            for (Ad ad : ads )
            {
                if (ad != null)
                {
                    List<Tracking> trackingList =
                            trackingService.getByAny(
                                    null,
                                    ObjectTypeEnum.Ad,
                                    ad.getId(),
                                    null,
                                    null
                            );
                    if (trackingList != null && trackingList.size() > 0)
                    {
                        fillInTrackingSource(ad, "Ad");
                    }
                    else
                    if (trackingList != null && trackingList.size() == 0)
                    {
                        // in this case get the parent campaign and get tracking for it...
                        trackingList = trackingService.getByAny(
                                null,
                                ObjectTypeEnum.Campaign,
                                ad.getCampaign().getId(),
                                null,
                                null
                        );
                        if (trackingList != null && trackingList.size() > 0)
                        {
                            fillInTrackingSource(ad, "Campaign");
                        }
                    }
                }
            }
        }
        return ads;
    }

    private void fillInTrackingSource(Ad ad, String trackingSource)
    {
        if (ad != null)
        {
            ad.setTrackingSource(trackingSource);
        }
    }

    ///////////////////////
    // AdType management //
    ///////////////////////
    @Override
    public AdType createAdType(String name)
    {
        IfbAdType adType = new IfbAdType();
        adType.setAdTypeName(name);
        adType.setCreateTimestamp(new Date());
        adDao.adTypeDao.save(adType);
        return adTransformer.transformType(adType);
    }

    @Override
    public boolean deleteAdType(Integer adTypeId)
    {
        boolean success = true;
        try
        {
            adDao.adTypeDao.delete(adDao.adTypeDao.getById(adTypeId));
        }
        catch (Exception x)
        {
            success = false;
        }
        return success;
    }

    @Override
    public AdType getAdType(Integer adTypeId)
    {
        return adTransformer.transformType(adDao.adTypeDao.getById(adTypeId));
    }

    @Override
    public Collection<AdType> getAdTypes(Collection<Integer> adTypeIds)
    {
        return adTransformer.transformType((adTypeIds == null) ? adDao.adTypeDao.getAll()
                : adDao.adTypeDao.getByIds(adTypeIds));
    }

    ///////////////////////
    // AdStatus management //
    ///////////////////////
    @Override
    public AdStatus createAdStatus(String name)
    {
        IfbAdStatus adStatus = new IfbAdStatus();
        adStatus.setAdStatusName(name);
        adStatus.setCreateTimestamp(new Date());
        adDao.adStatusDao.save(adStatus);
        return adTransformer.transformStatus(adStatus);
    }

    @Override
    public boolean deleteAdStatus(Integer adStatusId)
    {
        boolean success = true;
        try
        {
            adDao.adStatusDao.delete(adDao.adStatusDao.getById(adStatusId));
        }
        catch (Exception x)
        {
            success = false;
        }
        return success;
    }

    @Override
    public AdStatus getAdStatus(Integer adStatusId)
    {
        return adTransformer.transformStatus(adDao.adStatusDao.getById(adStatusId));
    }

    @Override
    public AdState getAdState(Integer adStateId)
    {
        return adTransformer.transformState(adDao.adStateDao.getById(adStateId));
    }

    @Override
    public Collection<AdStatus> getAdStatuses(Collection<Integer> adStatusIds)
    {
        return adTransformer.transformStatus((adStatusIds == null) ? adDao.adStatusDao.getAll()
                : adDao.adStatusDao.getByIds(adStatusIds));
    }

    @Override
    public Collection<AdState> getAdStates(Collection<Integer> adStateIds)
    {
        return adTransformer.transformState((adStateIds == null) ? adDao.adStateDao.getAll()
                : adDao.adStateDao.getByIds(adStateIds));
    }

    /**
     * Get the Ads that contain the stories queried for (through Feeds->FeedStories relationship)
     */
    @Override
    public MultiValueMap<Integer, Ad> getStoryContainers(Collection<Integer> storyIds, Collection<Integer> adIds, Collection<Integer> adTypeIds,
                                                         Collection<Integer> adStatusIds, Collection<Integer> campaignIds, Collection<Integer> campaignTypeIds,
                                                         Collection<Integer> campaignStatusIds, Collection<Integer> brandIds)
    {

        Collection<Ad> ads = adTransformer.transform(adDao.findByStoryIds(storyIds, adIds, adTypeIds,
                adStatusIds, campaignIds, campaignTypeIds,
                campaignStatusIds, brandIds));

        MultiValueMap<Integer, Ad> map = new LinkedMultiValueMap<Integer, Ad>();

        for (Ad ad : ads)
        {
            // Get all the FeedStories contained within the Ad
            for (FeedStory feedStory : ad.getFeed().getFeedStories())
            {
                // Add this Ad to the map for every FeedStory (creates the correct structure in result), key on DpStoryId
                map.add(feedStory.getStoryId(), ad);
            }
        }

        return map;
    }

    /**
     * Creates a new Ad (clone) object based on existing Ad object with existing Ad id = adId
     * Also creates Feed and FeedStories associated with newly created Ad similar to existing Ad object.
     * adTypeId, adSizeId, adNetworkId are optional parameters, if null or blank - the original values of existing
     * objects are used...
     *
     * @param adId
     * @param name
     * @param adTypeId
     * @param adSizeId
     * @param adNetworkId
     * @param actionUserKey
     * @param templateKey
     * @return - newly created Ad object
     */
    public Ad cloneAd(Integer adId, String name, Integer adTypeId, Integer adSizeId,
                      Integer adNetworkId, Integer actionUserKey, String templateKey)
    {
        GenerateThumbnailsException x = null;

        List<Integer> feedStoryStatusIds = new ArrayList<Integer>(2);
        feedStoryStatusIds.add(feedDao.feedStoryStatusDao.ACTIVE.getFeedStoryStatusId());
        feedStoryStatusIds.add(feedDao.feedStoryStatusDao.INACTIVE.getFeedStoryStatusId());

        Map<Integer, Integer> storyIds = new HashMap<Integer, Integer>();

        Ad result = null;
        Ad existingAd = getAd(adId);

        if (existingAd != null)
        {
            // default values
            AdType adType = existingAd.getType();
            AdSize adSize = existingAd.getAdSize();
            AdNetwork adNetwork = existingAd.getAdNetwork();

            // overwriting values if provided...
            if ( adTypeId != null )
            {
                adType = adTransformer.transformType( adTypeDao.getById( adTypeId ) );
            }
            if ( adSizeId  != null )
            {
                adSize = adSizeTransformer.transform( adSizeDao.getById( adSizeId ) );
            }
            if ( adNetworkId  != null )
            {
                adNetwork = adNetworkTransformer.transform( adNetworkDao.getById( adNetworkId ) );
            }

            Feed existingFeed = existingAd.getFeed();
            Collection<FeedStory> existingFeedStories = feedService.getFeedStories(existingFeed.getId(),
                    feedStoryStatusIds);

            String key = keyGenerator.generateKey();
            Feed feed = feedService.createFeed(
                    key,
                    existingFeed.getName(),
                    existingFeed.getOrdering()
            );
            if (existingFeedStories != null)
            {
                String updateSource = null;
                Boolean ignoreBot = null;
                for (FeedStory feedStory : existingFeedStories)
                {
                    storyIds.put(feedStory.getStoryId(), feedStory.getStatus().getId());
                    updateSource = feedStory.getUpdateSource();
                    ignoreBot = feedStory.getIgnoreBot();
                }

                try
                {
                    feedService.addFeedStories(
                            feed.getId(),
                            storyIds,
                            updateSource,
                            ignoreBot
                    );
                }
                // Catch and store any thumbnail exception
                catch (GenerateThumbnailsException e)
                {
                    x = e;
                }
            }
            key = keyGenerator.generateKey();
            if (templateKey == null && existingAd.getTemplate() != null && existingAd.getTemplate().getKey() != null)
            {
                templateKey = existingAd.getTemplate().getKey();
            }
            result = createAd(
                    key,
                    name,
                    adType.getId(),
                    existingAd.getStatus().getId(),
                    feed.getId(),
                    existingAd.getCampaign().getId(),
                    (adNetwork != null ? adNetwork.getAdNetworkId() : null),
                    (adSize != null ? adSize.getAdSizeId() : null),
                    existingAd.getState().getId(),
                    existingAd.getStartDate(),
                    existingAd.getStopDate(),
                    actionUserKey,
                    templateKey
            );

        }

        // If we caught a thumbnail error, throw it now
        if (x != null)
        {
            x.setReturnObject(result);
            throw x;
        }
        return result;
    }
    
    
    @Override
    public String getAdTag(Integer adId)
    {
    	
    	String tag="<script type=\"text/javascript\">" 
+"var inPoweredSettings = {"
+"    \"CAMPAIGN\": {"
+"        \"ID\": \"aximav1twiqh\","
+"        \"SITE\": \"%%SITE%%\","
+"    },"
+"    \"CREATIVE\": {"
+"        \"ID\": \"aximd68etva3\","
+"        \"FEED\": {"
+"            \"URL\": \"http://cdn.files.inpwrd.com/xml/posts/feed_aximd5za940j.jsongz\","
+"            \"RANDOMIZE\": true"
+"        },"
+"        \"TRACKING\": {"
+"            \"IMPRESSIONS\": ["

+"],"
+"            \"CLICKS\": ["
+"                \"%%CLICK_URL_UNESC%%\","
+"                \"http://ad.doubleclick.net/clk;273750088;100200285;t?chain_unesc_url\""
+"            ],"
+"            \"CLICK_IMPRESSIONS\": ["

+"            ],"
+"            \"SCRIPTS\": ["
+"            ]"
+"        }"
+"    }"
+"};"
+"</script>"
+"<script type=\"text/javascript\" src=\"http://cdn.ip.inpwrd.com/creatives/550x250/stories_6/4/bootstrap.min.jgz\"> </script>";
						
        return tag;
    }
}

