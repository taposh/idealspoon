package com.netshelter.ifbrands.api.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.netshelter.ifbrands.util.RestTemplateWithTimeout;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.model.storyamplification.AmplifyDetail;
import com.netshelter.ifbrands.api.model.storyamplification.ShortUrl;
import com.netshelter.ifbrands.api.model.storyamplification.StoryAmplification;
import com.netshelter.ifbrands.api.model.storyamplification.StoryAmplificationContainer;
import com.netshelter.ifbrands.api.model.storyamplification.StoryAmplifyDetail;
import com.netshelter.ifbrands.api.model.user.UserInfo;
import com.netshelter.ifbrands.api.util.GeneralClientException;
import com.netshelter.ifbrands.api.util.GeneralServerException;
import com.netshelter.ifbrands.data.dao.CampaignDao;
import com.netshelter.ifbrands.data.dao.StoryAmplificationDao;
import com.netshelter.ifbrands.data.entity.IfbStoryAmplification;
import com.netshelter.ifbrands.etl.shareapi.SaServices;
import com.netshelter.ifbrands.etl.transform.ShortUrlTransformer;
import com.netshelter.ifbrands.etl.transform.StoryAmplificationTransformer;
import com.netshelter.ifbrands.util.MoreCollections;

public class ShareServiceImpl
        implements ShareService
{
    public static final String FACEBOOK_URL = "http://www.facebook.com/sharer.php?";
    public static final String TWITTER_URL = "https://twitter.com/share?";

    @Autowired
    private SaServices saServices;
    @Autowired
    private EntityService entityService;
    @Autowired
    private StoryAmplificationDao storyAmplificationDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private StoryAmplificationTransformer storyAmplificationTransformer;
    @Autowired
    private CampaignDao campaignDao;

    private RestTemplate restWithTimeout = new RestTemplateWithTimeout( 45 );

    private String shareApiAuthority;

    public void setShareApiAuthority(String shareApiAuthority)
    {
        this.shareApiAuthority = shareApiAuthority;
    }

    public String getShareApiAuthority()
    {
        return shareApiAuthority;
    }

    public boolean isAlive()
    {
        boolean result = false;
        if (StringUtils.isNotBlank(shareApiAuthority))
        {
            // Call health API
            String url = String.format( "http://%s/health=1", shareApiAuthority );
            ResponseEntity<String> response = restWithTimeout.getForEntity( url, String.class );
            if ( response.getStatusCode() == HttpStatus.OK )
            {
                result = true;
            }
        }
        return result;
    }

    private List<StoryAmplifyDetail> buildStoryAmplifyDetails(List<AmplifyDetail> amplifyDetails)
    {
        HashMap<Integer, StoryAmplifyDetail> storyAmplificationMap = new HashMap<Integer, StoryAmplifyDetail>();

        for (AmplifyDetail amplifyDetail : amplifyDetails)
        {
            Object result = storyAmplificationMap.get(amplifyDetail.getStoryId());

            StoryAmplifyDetail storyAmplifyDetail = new StoryAmplifyDetail();
            List<AmplifyDetail> amps = new ArrayList<AmplifyDetail>();

            // If this story/brand combo has already been seen...
            if (result != null)
            {
                storyAmplifyDetail = (StoryAmplifyDetail) result;

                amps = storyAmplifyDetail.getAmplifyDetails();
            }
            // This is a new story/brand amplification, bootstrap the
            // storyAmplifyDetail
            else
            {
                storyAmplifyDetail.setStoryId(amplifyDetail.getStoryId());
            }

            amps.add(amplifyDetail);
            storyAmplifyDetail.setAmplifyDetails(amps);

            storyAmplificationMap.put(amplifyDetail.getStoryId(), storyAmplifyDetail);
        }

        List<StoryAmplifyDetail> results = new ArrayList<StoryAmplifyDetail>(storyAmplificationMap.values());

        return results;
    }

    private StoryAmplificationContainer fillContainer(StoryAmplificationContainer storyContainer,
                                                      StoryAmplification amplification, String type)
    {
        // Put into correct spot in the container
        if (type.equals("facebook"))
        {
            storyContainer.setFacebookAmplification(amplification);
        }
        else if (type.equals("twitter"))
        {
            storyContainer.setTwitterAmplification(amplification);
        }

        return storyContainer;
    }

    // TODO: Refactor the use of StoryAmplificationContainers in this method to
    // AmplifyList/StoryAmplifyDetail/AmplifyDetail objects
    @Override
    public List<StoryAmplificationContainer> getAmplifyStats(String callType, Integer id, DateTime start, DateTime stop)

    {
        List<IfbStoryAmplification> list = new ArrayList<IfbStoryAmplification>();

        if (callType.equals("ad"))
        {
            list = storyAmplificationDao.getEntitiesForAd(id);
        }
        else if (callType.equals("campaign"))
        {
            list = storyAmplificationDao.getEntitiesForCampaign(id);
        }

        List<StoryAmplification> storyAmplifications = storyAmplificationTransformer.transform(list);

        HashMap<Integer, StoryAmplificationContainer> storyContainers = new HashMap<Integer, StoryAmplificationContainer>();

        for (StoryAmplification amplification : storyAmplifications)
        {
            String shortUrlKey = amplification.getShortUrlKey();
            Integer consumptions = getShortUrlClicks(shortUrlKey, start, stop);

            amplification.setConsumptions(consumptions);

            DateTime startDateTime = start.toDateTime(DateTimeZone.forID("UTC"));
            DateTime stopDateTime = stop.toDateTime(DateTimeZone.forID("UTC"));

            if (consumptions > 0 || startDateTime.isBefore(amplification.getLastAmplified())
                    && stopDateTime.isAfter(amplification.getLastAmplified()) && consumptions == 0)
            {
                Object result = storyContainers.get(amplification.getStory().getId());

                StoryAmplificationContainer storyContainer = new StoryAmplificationContainer();

                // If this story/brand combo has already been seen...
                if (result != null)
                {
                    storyContainer = (StoryAmplificationContainer) result;

                    storyContainer = fillContainer(storyContainer, amplification, amplification.getType());
                }
                else
                {
                    // Fill in Story and Brand
                    storyContainer.setStory(entityService.getStory(amplification.getStory().getId()));

                    storyContainer = fillContainer(storyContainer, amplification, amplification.getType());
                }

                storyContainers.put(amplification.getStory().getId(), storyContainer);

            }
        }

        List<StoryAmplificationContainer> results = new ArrayList<StoryAmplificationContainer>(
                storyContainers.values());

        return results;
    }

    @Override
    public List<StoryAmplifyDetail> getAmplifyStats(String callType, List<Integer> storyIds, Integer id)

    {
        List<AmplifyDetail> amplifyDetails = new ArrayList<AmplifyDetail>();
        for (Integer storyId : storyIds)
        {

            List<IfbStoryAmplification> list = new ArrayList<IfbStoryAmplification>();
            if (callType == null)
            {
                list = storyAmplificationDao.getByStory(new Story(storyId));
            }
            else if (callType.equals("ad"))
            {
                list = storyAmplificationDao.getByAny(new Story(storyId), new Ad(id),
                                                      new Campaign(null), null);
            }
            else if (callType.equals("campaign"))
            {
                list = storyAmplificationDao.getByAny(new Story(storyId), new Ad(null),
                                                      new Campaign(id), null);
            }

            List<StoryAmplification> storyAmplifications = storyAmplificationTransformer.transform(list);

            // Transform StoryAmplification to AmplifyDetail
            for (StoryAmplification amplification : storyAmplifications)
            {
                String userName = null;

                // Grab the userName of the user that amplified the story (from Insights
                // API)
                if (!amplification.getUserKey().isEmpty())
                {
                    UserInfo userInfo = userService.getUserInfo(amplification.getUserKey());

                    userName = userInfo.getFirstName() + ' ' + userInfo.getLastName();
                }

                AmplifyDetail amplifyDetail = new AmplifyDetail();
                amplifyDetail.setStoryId(amplification.getStory().getId());
                amplifyDetail.setUserKey(amplification.getUserKey());
                amplifyDetail.setUserName(userName);
                amplifyDetail.setAmplifyTarget(amplification.getType());
                amplifyDetail.setAdId(amplification.getAdId());
                amplifyDetail.setCampaignId(amplification.getCampaignId());
                amplifyDetail.setAmplifyDateTime(amplification.getLastAmplified());

                amplifyDetails.add(amplifyDetail);
            }
        }

        List<StoryAmplifyDetail> results = buildStoryAmplifyDetails(amplifyDetails);

        return results;
    }

    /**
     * Retrieves a shortened URL from an external Share service (currently NS ShareAPI),
     * and prepends the required share service (facebook, twitter, etc.) sharing URL
     * As a by-product, records a StoryAmplification in the IFB persistent store as well
     *
     * @param storyId The story to be shared
     * @param userKey The user responsible for sharing the story
     * @param shareType The share service to share through
     * @param campaignId The campaign to associate this amplification with
     */
    @Override
    public String getAmplifyUrl(Integer storyId, String userKey, String shareType, Integer campaignId)

    {
        ShortUrl shortUrl = getShortUrl(storyId, shareType, campaignId);
        recordAmplification(storyId, userKey, shareType, shortUrl, campaignId);

        Story story = entityService.getStory(storyId);

        StringBuilder sb = new StringBuilder();
        try
        {
            if (shareType.equals("facebook"))
            {
                sb.append(FACEBOOK_URL);
                sb.append("t=");
                sb.append(UriUtils.encodeQueryParam(story.getTitle(), "UTF-8"));
                sb.append("&u=");
                sb.append(UriUtils.encodeQueryParam(shortUrl.getShortUrl(), "UTF-8"));
            }
            else if (shareType.equals("twitter"))
            {
                sb.append(TWITTER_URL);
                sb.append("text=");
                sb.append(UriUtils.encodeQueryParam(story.getTitle(), "UTF-8"));
                sb.append("&url=");
                sb.append(UriUtils.encodeQueryParam(shortUrl.getShortUrl(), "UTF-8"));
            }
        }
        catch (UnsupportedEncodingException e)
        {
            throw new GeneralServerException("Bad Encoding", e);
        }

        return sb.toString();
    }

    @Override
    public ShortUrl getShortUrl(Integer storyId, String shareType, Integer campaignId)

    {
        Story story = entityService.getStory(storyId);

        Campaign campaign = null;
        if (campaignId != null)
        {
            campaign = campaignService.getCampaign(campaignId);
            if (campaign == null)
            {
                // campaignId provided by no corresponding ad exists, should never happen
                throw new GeneralClientException("Campaign %d does not exist", campaignId);
            }
        }

        // Provide an ifb namespaced campaign name for recording within ShareAPI
        String campaignName = "ifb_" + "GenericBrand";

        IfbStoryAmplification amplification = MoreCollections.firstOrNull(
                storyAmplificationDao.getByAny(story, new Ad(null), campaign, shareType));

        // If already amplified, return existing ShortUrl
        if (amplification != null)
        {
            ShortUrl shortUrl = new ShortUrl();
            shortUrl.setShortUrl(String.format("http://%s/%s", shareApiAuthority, amplification.getShortUrlKey()));
            shortUrl.setKey(amplification.getShortUrlKey());

            return shortUrl;
        }
        else
        {
            try
            {
                String response = saServices.getShortUrl(campaignName, story.getTitle(), story.getStoryUrl());
                return ShortUrlTransformer.transform(response);
            }
            catch (Exception e)
            {
                throw new GeneralServerException("Cannot parse ShareAPI response", e);
            }
        }
    }

    @Override
    public Integer getShortUrlClicks(String shortUrlKey, DateTime start, DateTime stop)

    {
        try
        {
            return saServices.getShortUrlStats(shortUrlKey, start, stop);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new GeneralServerException("Cannot parse ShareAPI response", e);
        }
        finally
        {
            return 0;
        }
    }

    @Override
    public StoryAmplification recordAmplification(Integer storyId, String userKey, String typeFilter,
                                                  ShortUrl shortUrl, Integer campaignId)
    {
        // Check for existence of a StoryAmplification
        IfbStoryAmplification existingAmplification = storyAmplificationDao.getByShortUrlKey(shortUrl.getKey());

        // If exists, update the LastAmplified field
        if (existingAmplification != null)
        {
            existingAmplification.setUserKey(userKey);
            existingAmplification.setLastAmplification(new Date());

            storyAmplificationDao.update(existingAmplification);

            return storyAmplificationTransformer.transform(existingAmplification);
        }
        else
        {
            IfbStoryAmplification amplification = new IfbStoryAmplification();

            amplification.setDpStoryId(storyId);
            amplification.setUserKey(userKey);
            amplification.setShortUrlKey(shortUrl.getKey());
            amplification.setType(typeFilter);
            amplification.setCreatedAmplification(new Date());
            amplification.setLastAmplification(new Date());

            // adId is optional, so don't try getting a null proxy
            if (campaignId != null)
            {
                amplification.setIfbCampaign((campaignDao.getProxyById(campaignId)));
            }

            amplification = storyAmplificationDao.save(amplification);

            return storyAmplificationTransformer.transform(storyAmplificationDao.getById(amplification
                                                                                                 .getStoryAmplificationId()));
        }

    }

    @Override
    public boolean deleteAmplifications(String typeFilter, Integer id, Integer storyId)
    {
        boolean success = true;

        List<IfbStoryAmplification> list = new ArrayList<IfbStoryAmplification>();
        if (typeFilter == null)
        {
            list = storyAmplificationDao.getByStory(new Story(storyId));
        }
        else if (typeFilter.equals("ad"))
        {
            list = storyAmplificationDao.getByAny(new Story(storyId), new Ad(id),
                                                  new Campaign(null), null);
        }
        else if (typeFilter.equals("campaign"))
        {
            list = storyAmplificationDao.getByAny(new Story(storyId), new Ad(null),
                                                  new Campaign(id), null);
        }

        if (list.size() == 0)
        {
            success = false;
        }
        else
        {
            try
            {
                for (IfbStoryAmplification amplification : list)
                {
                    storyAmplificationDao.delete(amplification);
                }
            }
            catch (Exception x)
            {
                success = false;
            }
        }

        return success;
    }
}
