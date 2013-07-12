package com.netshelter.ifbrands.api.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.netshelter.ifbrands.api.model.ActionObjectType;
import com.netshelter.ifbrands.api.model.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.util.UriUtils;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.api.model.FailCode;
import com.netshelter.ifbrands.api.model.FailSeverity;
import com.netshelter.ifbrands.api.model.campaign.Feed;
import com.netshelter.ifbrands.api.model.campaign.Feed.Ordering;
import com.netshelter.ifbrands.api.model.campaign.FeedStory;
import com.netshelter.ifbrands.api.model.campaign.FeedStoryStatus;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.util.FeedStoryFailure;
import com.netshelter.ifbrands.api.util.GenerateThumbnailsException;
import com.netshelter.ifbrands.api.util.SetCustomStoryImageException;
import com.netshelter.ifbrands.api.util.ThumbnailValidationUtils;
import com.netshelter.ifbrands.data.dao.CampaignDao;
import com.netshelter.ifbrands.data.dao.FeedDao;
import com.netshelter.ifbrands.data.dao.FeedDao.FeedStoryDao;
import com.netshelter.ifbrands.data.dao.FeedDao.FeedStoryStatusDao;
import com.netshelter.ifbrands.data.entity.IfbFeed;
import com.netshelter.ifbrands.data.entity.IfbFeedStory;
import com.netshelter.ifbrands.data.entity.IfbFeedStoryStatus;
import com.netshelter.ifbrands.etl.dataplatform.DpServices;
import com.netshelter.ifbrands.etl.dataplatform.model.GetSuccessStatusResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetSuccessStatusWithStoryResponse;
import com.netshelter.ifbrands.etl.transform.StoryTransformer;
import com.netshelter.ifbrands.etl.transform.campaign.FeedTransformer;
import com.netshelter.ifbrands.publish.PublishService;

public class FeedServiceImpl
        implements FeedService
{
    Logger logger = LoggerFactory.getLogger();

    @Autowired
    private FeedDao feedDao;
    @Autowired
    private FeedStoryDao feedStoryDao;
    @Autowired
    private FeedStoryStatusDao feedStoryStatusDao;
    @Autowired
    private CampaignDao campaignDao;
    @Autowired
    private FeedTransformer feedTransformer;
    @Autowired
    private DpServices dpServices;
    @Autowired
    private EntityService entityService;
    @Autowired
    private PublishService publishService;
    @Autowired
    private StoryTransformer storyTransformer;
    @Autowired
    private ActionLogService actionLogService;

    public static final String SOURCE_HUMAN = "Human";
    public static final String SOURCE_BOT = "Bot";

    @Override
//  @CollectionEvict( cacheName = FEED_CACHE, removeAll=true )
    public void flushCache()
    {
    }

    @Override
    public boolean isFeedStoryActive(FeedStory feedStory)
    {
        return feedStory.getStatus().getId().equals(feedStoryStatusDao.ACTIVE.getFeedStoryStatusId());
    }

    @Override
    public boolean isFeedStoryActive(int feedStoryId)
    {
        IfbFeedStory ifbFeedStory = feedStoryDao.getById(feedStoryId);

        return ifbFeedStory.getIfbFeedStoryStatus().getFeedStoryStatusId().equals(
                feedStoryStatusDao.ACTIVE.getFeedStoryStatusId());
    }

    /////////////////////
    // Feed Management //
    /////////////////////
    @Override
    public Feed createFeed(String key, String name, Ordering ordering)

    {
        Date now = new Date();
        IfbFeed feed = new IfbFeed();
        feed.setFeedKey(key);
        feed.setFeedName(name);
        feed.setOrdering(ordering.toString());
        //feed.setFeedUrl( publishService.getFeedUrl( key ) );
        feed.setLastModified(now);
        feed.setCreateTimestamp(now);
        feed = feedDao.save(feed);

        // We need to publish the feed, now that a FeedUrl has been set in the persistent store
        // Not handling AWS errors right now, but will do so in the near future
        publishService.publishFeed(feed.getFeedId());

        return getFeed(feed.getFeedId());
    }

    @Override
    public boolean deleteFeed(Integer feedId)
    {
        boolean success = true;
        try
        {
            feedDao.delete(feedDao.getById(feedId));
        }
        catch (Exception x)
        {
            success = false;
        }
        return success;
    }

    @Override
    public Feed updateFeed(Integer feedId, String feedName)
    {
        IfbFeed feed = feedDao.getById(feedId);
        if (feed == null) throw new IllegalArgumentException("Feed not found");
        if (feedName != null) feed.setFeedName(feedName);
        feed.setLastModified(new Date());

        feedDao.update(feed);

        // I don't think a feed re-publish is necessary here
        //updatePublishedFeed( feedId );

        return getFeed(feedId);
    }

    @Override
    public Feed getFeed(Integer feedId)
    {
        return feedTransformer.transform(feedDao.getById(feedId));
    }

    @Override
    public Collection<Feed> getFeeds(Collection<Integer> feedIds, String feedKey)
    {
        return feedTransformer.transform(feedDao.getByAny(feedIds, feedKey));
    }

    @Override
    public boolean publishFeeds(Collection<Feed> feeds, boolean generateThumbnails)
    {
        boolean success = true;

        try
        {
            if (generateThumbnails == true)
            {
                for (Feed feed : feeds)
                {
                    for (FeedStory feedStory : feed.getFeedStories())
                    {
                        logger.info("Generating thumbnails for story: %s", feedStory.getStoryId());
                        buildAllStoryThumbnails(feedStory.getStoryId());
                    }
                }
            }

            logger.info("Flushing ENTITY_CACHE");
            entityService.flushCache();
            for (Feed feed : feeds)
            {
                updatePublishedFeed(feed.getId());
            }
        }
        catch (Exception e)
        {
            logger.debug("Feed publishing threw exception: %s", e.getMessage());
            success = false;
        }

        return success;
    }

    /**********************************************/
    //Added by Taposh
    /**********************************************/
    /////////////////////////////////////////////////////////////////////////////////
    @Override
    public Collection<FeedStory> addFeedStory( Collection<Integer> feedIds, Collection<Integer> dpStoryIds, Integer feedStoryStatusId,
                                              String updateSource, Boolean ignoreBot)
    {
        // Prep a map for possible feedStory failures
        Map<Integer, List<FeedStoryFailure>> feedStoryFailures = new HashMap<Integer, List<FeedStoryFailure>>();

        //Used for thumbnail validation
        ThumbnailValidationUtils thumbnailValidationUtils = new ThumbnailValidationUtils();

        //variable for thumbnail present or not
        boolean buildSuccess = false;

        //Collection of stories with thumbnails
        Collection<FeedStory> feedStories = new ArrayList<FeedStory>();

        // Hashtable with stories tested and their thumbnailstatus
        Map<Integer, Boolean> storyStatus  = new HashMap<Integer, Boolean>();


        //Iterate over the feedIds
        for (Integer feedId : feedIds)
        {
            for (Integer sid : dpStoryIds)
            {

                Story s = entityService.getStory(sid);

                //if story has already been validated for thumbnail then add to feed
                //
                if (!storyStatus.containsKey(sid))
                {

                        // Validate that we have the right number of thumbnails, only try re-building if less
                        if (thumbnailValidationUtils.validateThumbnailQuantity(s.getThumbnails()) == false)
                        {
                            logger.info("Story %s is missing thumbnails, re-generating", s.getId());
                            buildSuccess = buildAllStoryThumbnails(sid);
                        }
                        else
                        {
                            buildSuccess = true;
                        }

                        if (buildSuccess == false)
                        {
                            FeedStoryFailure feedStoryFailure = new FeedStoryFailure(FailSeverity.NON_FATAL,
                                    FailCode.GENERATE_THUMBNAILS_GENERIC);
                            feedStoryFailures.put(sid, Collections.singletonList(feedStoryFailure));
                        }

                        ///update the storyStatus for future checks
                            storyStatus.put(s.getId(),buildSuccess);

                }
                else
                {
                        //Setting the value from the storyStatus
                         buildSuccess= storyStatus.get(sid);
                }

                IfbFeedStory story = feedStoryDao.getFirstByAny(feedId, sid, null);

                if (story == null)
                {
                    story = new IfbFeedStory();
                }

                story.setOrdinal(0);
                story.setDpStoryId(sid);
                if (updateSource != null)
                {
                    story.setUpdateSource(updateSource);
                }
                else
                {
                    story.setUpdateSource(SOURCE_HUMAN);
                }
                if (ignoreBot != null)
                {
                    story.setIgnoreBot(ignoreBot);
                }
                else
                {
                    story.setIgnoreBot(false);
                }
                story.setIfbFeed(feedDao.getProxyById(feedId));

                // feedStoryStatus override if necessary (when thumbnails don't get generated successfully)
                if (buildSuccess == true)
                {
                    story.setIfbFeedStoryStatus(feedDao.feedStoryStatusDao.getProxyById(feedStoryStatusId));
                }
                else
                {
                    // Force inactive state if thumbnails could not be generated
                    story.setIfbFeedStoryStatus(feedDao.feedStoryStatusDao.INACTIVE);
                }
                story.setCreateTimestamp(new Date());
                story.setUpdateTimestamp(story.getCreateTimestamp());
                try
                {
                    if (story.getFeedStoryId() == null)
                    {
                        feedDao.feedStoryDao.save(story);
                    }
                    else
                    {
                        feedDao.feedStoryDao.update(story);
                    }

                }
                catch (DataIntegrityViolationException d)
                {
                    logger.error(d.getMessage());
                    // Duplicate story in feed: just proceed
                }
                updateFeed(feedId, null);
                feedStories.add(getFeedStory(feedId, sid));

            }

            updatePublishedFeed(feedId);
        }

        // If we found some issues with thumbnail generation, let's put them here
        if (feedStoryFailures.size() > 0)
        {
            throw new GenerateThumbnailsException(feedStoryFailures);
        }

        return feedStories;
    }
    ///////////////////////////
/**********************************************/

    @Override
    public Collection<FeedStory> addFeedStory(Integer feedId, Collection<Integer> dpStoryIds, Integer feedStoryStatusId,
                                              String updateSource, Boolean ignoreBot)
    {
        // Prep a map for possible feedStory failures
        Map<Integer, List<FeedStoryFailure>> feedStoryFailures = new HashMap<Integer, List<FeedStoryFailure>>();

        ThumbnailValidationUtils thumbnailValidationUtils = new ThumbnailValidationUtils();

        boolean buildSuccess = false;

        Collection<FeedStory> feedStories = new ArrayList<FeedStory>();
        for (Integer sid : dpStoryIds)
        {

            Story s = entityService.getStory(sid);


            // Validate that we have the right number of thumbnails, only try re-building if less
            if (thumbnailValidationUtils.validateThumbnailQuantity(s.getThumbnails()) == false)
            {
                logger.info("Story %s is missing thumbnails, re-generating", s.getId());
                buildSuccess = buildAllStoryThumbnails(sid);
            }
            else
            {
                buildSuccess = true;
            }

            if (buildSuccess == false)
            {
                FeedStoryFailure feedStoryFailure = new FeedStoryFailure(FailSeverity.NON_FATAL,
                                                                         FailCode.GENERATE_THUMBNAILS_GENERIC);
                feedStoryFailures.put(sid, Collections.singletonList(feedStoryFailure));
            }

            IfbFeedStory story = feedStoryDao.getFirstByAny(feedId, sid, null);

            if (story == null)
            {
                story = new IfbFeedStory();
            }

            story.setOrdinal(0);
            story.setDpStoryId(sid);
            if (updateSource != null)
            {
                story.setUpdateSource(updateSource);
            }
            else
            {
                story.setUpdateSource(SOURCE_HUMAN);
            }
            if (ignoreBot != null)
            {
                story.setIgnoreBot(ignoreBot);
            }
            else
            {
                story.setIgnoreBot(false);
            }
            story.setIfbFeed(feedDao.getProxyById(feedId));

            // feedStoryStatus override if necessary (when thumbnails don't get generated successfully)
            if (buildSuccess == true)
            {
                story.setIfbFeedStoryStatus(feedDao.feedStoryStatusDao.getProxyById(feedStoryStatusId));
            }
            else
            {
                // Force inactive state if thumbnails could not be generated
                story.setIfbFeedStoryStatus(feedDao.feedStoryStatusDao.INACTIVE);
            }
            story.setCreateTimestamp(new Date());
            story.setUpdateTimestamp(story.getCreateTimestamp());
            try
            {
                if (story.getFeedStoryId() == null)
                {
                    feedDao.feedStoryDao.save(story);
                }
                else
                {
                    feedDao.feedStoryDao.update(story);
                }

            }
            catch (DataIntegrityViolationException d)
            {
                logger.error(d.getMessage());
                // Duplicate story in feed: just proceed
            }
            updateFeed(feedId, null);
            feedStories.add(getFeedStory(feedId, sid));

        }

        updatePublishedFeed(feedId);

        // If we found some issues with thumbnail generation, let's put them here
        if (feedStoryFailures.size() > 0)
        {
            throw new GenerateThumbnailsException(feedStoryFailures);
        }

        return feedStories;
    }

    @Override
    public Collection<FeedStory> addFeedStories(Integer feedId, Map<Integer, Integer> storyIds,
                                                String updateSource, Boolean ignoreBot)
    {
        // Prep a map for possible feedStory failures
        Map<Integer, List<FeedStoryFailure>> feedStoryFailures = new HashMap<Integer, List<FeedStoryFailure>>();

        ThumbnailValidationUtils thumbnailValidationUtils = new ThumbnailValidationUtils();

        boolean buildSuccess = false;

        Collection<FeedStory> stories = new ArrayList<FeedStory>();

        if (storyIds == null) return null;
        for (Map.Entry<Integer, Integer> entry : storyIds.entrySet())
        {

            Integer storyId = entry.getKey();
            Integer storyStatusId = entry.getValue();

            logger.info("Flushing story %s", storyId);

            // Primary flush (with no cache key prefix)
            entityService.flushStory(storyId);

            dpServices.setDpMasterMode(true);

            // Secondary flush (with "config=admin" cache key prefix)
            entityService.flushStory(storyId);

            Story story = entityService.getStory(storyId);

            // Validate that we have the right number of thumbnails, only try re-building if less
            if (thumbnailValidationUtils.validateThumbnailQuantity(story.getThumbnails()) == false)
            {
                logger.info("Story %s is missing thumbnails, re-generating", story.getId());
                buildSuccess = buildAllStoryThumbnails(storyId);
            }
            else
            {
                buildSuccess = true;
            }

            dpServices.setDpMasterMode(false);

            if (buildSuccess == false)
            {
                FeedStoryFailure feedStoryFailure = new FeedStoryFailure(FailSeverity.NON_FATAL,
                                                                         FailCode.GENERATE_THUMBNAILS_GENERIC);
                feedStoryFailures.put(storyId, Collections.singletonList(feedStoryFailure));
            }

            IfbFeedStory feedStory = new IfbFeedStory();
            feedStory.setOrdinal(0);
            feedStory.setDpStoryId(storyId);
            feedStory.setIfbFeed(feedDao.getProxyById(feedId));

            if (updateSource != null)
            {
                feedStory.setUpdateSource(updateSource);
            }
            else
            {
                feedStory.setUpdateSource(SOURCE_HUMAN);
            }
            if (ignoreBot != null)
            {
                feedStory.setIgnoreBot(ignoreBot);
            }
            else
            {
                feedStory.setIgnoreBot(false);
            }

            // feedStoryStatus override if necessary (when thumbnails don't get generated successfully)
            if (buildSuccess == true)
            {
                feedStory.setIfbFeedStoryStatus(feedDao.feedStoryStatusDao.getProxyById(storyStatusId));
            }
            else
            {
                // Force inactive state if thumbnails could not be generated
                feedStory.setIfbFeedStoryStatus(feedDao.feedStoryStatusDao.INACTIVE);
            }
            feedStory.setCreateTimestamp(new Date());
            feedStory.setUpdateTimestamp(feedStory.getCreateTimestamp());
            try
            {
                feedStory = feedDao.feedStoryDao.save(feedStory);
            }
            catch (DataIntegrityViolationException d)
            {
                // Duplicate story in feed: just proceed
            }
            updateFeed(feedId, null);
            stories.add(getFeedStory(feedId, storyId));

        }

        updatePublishedFeed(feedId);

        // If we found some issues with thumbnail generation, let's put them here
        if (feedStoryFailures.size() > 0)
        {
            throw new GenerateThumbnailsException(feedStoryFailures);
        }

        return stories;
    }

    @Override
    public boolean removeFeedStory(Integer feedId, Collection<Integer> dpStoryIds)
    {
        boolean success = true;
        try
        {
            for (Integer sid : dpStoryIds)
            {
                feedDao.feedStoryDao.delete(feedDao.feedStoryDao.getFirstByAny(feedId, sid, null));
            }
        }
        catch (Exception x)
        {
            success = false;
        }
        updateFeed(feedId, null);

        updatePublishedFeed(feedId);

        return success;
    }

    @Override
    public Collection<FeedStory> updateFeedStory(Integer feedId, Collection<Integer> dpStoryIds, Integer feedStoryStatusId,
                                                 String updateSource, Boolean ignoreBot)
    {
        ThumbnailValidationUtils thumbnailValidationUtils = new ThumbnailValidationUtils();

        // Prep a map for possible feedStory failures
        Map<Integer, List<FeedStoryFailure>> feedStoryFailures = new HashMap<Integer, List<FeedStoryFailure>>();

        Collection<FeedStory> stories = new ArrayList<FeedStory>();
        for (Integer sid : dpStoryIds)
        {
            IfbFeedStory story = feedDao.feedStoryDao.getFirstByAny(feedId, sid, null);

            // Check for valid thumbnails
            if (feedStoryStatusId != null)
            {

                actionLogService.makeLogEntry(
                        ActionType.STORY_STATUS_CHANGE,
                        ActionObjectType.FEED_STORY,
                        story.getFeedStoryId(),
                        null,
                        "original status: " + story.getIfbFeedStoryStatus().getFeedStoryStatusId() + ", new status: " + feedStoryStatusId
                );

                if (feedStoryStatusId.equals(feedDao.feedStoryStatusDao.ACTIVE.getFeedStoryStatusId()) &&
                        thumbnailValidationUtils.validateThumbnailQuantity(
                                entityService.getStory(sid).getThumbnails()) == false)
                {
                    boolean buildSuccess = buildAllStoryThumbnails(sid);

                    if (buildSuccess == false)
                    {
                        FeedStoryFailure feedStoryFailure = new FeedStoryFailure(FailSeverity.NON_FATAL,
                                                                                 FailCode.GENERATE_THUMBNAILS_GENERIC);
                        feedStoryFailures.put(sid, Collections.singletonList(feedStoryFailure));

                        story.setIfbFeedStoryStatus(feedDao.feedStoryStatusDao.INACTIVE);
                    }
                }
                else
                {
                    if (feedStoryStatusId != null)
                    {
                        story.setIfbFeedStoryStatus(feedDao.feedStoryStatusDao.getProxyById(feedStoryStatusId));
                    }
                }
            }

            if (updateSource != null)
            {
                story.setUpdateSource(updateSource);
            }

            if (ignoreBot != null)
            {
                story.setIgnoreBot(ignoreBot);
            }

            story.setUpdateTimestamp(new Date()); // current timestamp

            feedDao.feedStoryDao.update(story);
            updateFeed(feedId, null);
            stories.add(getFeedStory(feedId, sid));
        }

        if (feedStoryStatusId != null)
        {
            updatePublishedFeed(feedId);
        }

        // If we found some issues with thumbnail generation, let's put them here
        if (feedStoryFailures.size() > 0)
        {
            throw new GenerateThumbnailsException(feedStoryFailures);
        }

        return stories;
    }

    @Override
    public FeedStory getFeedStory(Integer feedId, Integer dpStoryId)
    {
        return feedTransformer.transformStory(feedDao.feedStoryDao.getFirstByAny(feedId, dpStoryId, null));
    }

    @Override
    public Collection<FeedStory> getFeedStories(Integer feedId, Collection<Integer> feedStoryStatusIds)
    {
        return feedTransformer.transformStory(feedDao.feedStoryDao.getByAny(feedId, null, feedStoryStatusIds));
    }

    public Collection<FeedStory> getFeedStories(Collection<Integer> feedIds)
    {
        return feedTransformer.transformStory(feedDao.feedStoryDao.getByFeedId(feedIds));
    }

    @Override
    public boolean orderFeedStories(Integer feedId, List<Integer> feedStoryIds)
    {
//        updateFeed( feedId, null );
        throw new RuntimeException("Functionality not yet supported.");
    }

    private void createCustomStoryImageActionLogEntry(Integer storyId, String message)
    {
        actionLogService.makeLogEntry(
                ActionType.STORY_CUSTOM_THUMBNAIL_SET,
                ActionObjectType.FEED_STORY,
                storyId,
                null,
                message
        );
    }

    @Override
    public Story setCustomStoryImage(Integer dpStoryId, String imageUrl)
    {
        try
        {
            imageUrl = UriUtils.encodeQueryParam(imageUrl, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Could not decode url", e);
        }

        GetSuccessStatusWithStoryResponse response;
        try
        {
            response = dpServices.setCustomStoryImage(dpStoryId, imageUrl);
        }
        catch (Exception e)
        {
            FeedStoryFailure feedStoryFailure = new FeedStoryFailure(FailSeverity.NON_FATAL,
                                                                     FailCode.CUSTOM_IMAGE_GENERIC);
            Map<Integer, List<FeedStoryFailure>> feedStoryFailures = new HashMap<Integer, List<FeedStoryFailure>>();
            feedStoryFailures.put(dpStoryId, Collections.singletonList(feedStoryFailure));

            throw new SetCustomStoryImageException(feedStoryFailures);
        }

        entityService.flushStory(dpStoryId);

        // Republish all feeds that include this story
        Collection<IfbFeedStory> ifbFeedStories = feedStoryDao.getByAny(null, dpStoryId, Collections.singletonList(
                feedStoryStatusDao.ACTIVE.getFeedStoryStatusId()));
        for (IfbFeedStory ifbFeedStory : ifbFeedStories)
        {
            updatePublishedFeed(ifbFeedStory.getIfbFeed().getFeedId());
        }

        return storyTransformer.transform(response.getStory());
    }

    @Override
    public FeedStoryStatus createFeedStoryStatus(String name)
    {
        IfbFeedStoryStatus status = new IfbFeedStoryStatus();
        status.setFeedStoryStatusName(name);
        status.setCreateTimestamp(new Date());
        status = feedDao.feedStoryStatusDao.save(status);
        return getFeedStoryStatus(status.getFeedStoryStatusId());
    }

    @Override
    public boolean deleteFeedStoryStatus(Integer feedStoryStatusId)
    {
        boolean success = true;
        try
        {
            feedDao.feedStoryStatusDao.delete(feedDao.feedStoryStatusDao.getById(feedStoryStatusId));
        }
        catch (Exception x)
        {
            success = false;
        }
        return success;
    }

    @Override
    public FeedStoryStatus getFeedStoryStatus(Integer feedStoryStatusId)
    {
        return feedTransformer.transformStoryStatus(feedDao.feedStoryStatusDao.getById(feedStoryStatusId));
    }

    @Override
    public Collection<FeedStoryStatus> getFeedStoryStatuses(Collection<Integer> ids)
    {
        return feedTransformer.transformStoryStatus((ids == null) ? feedDao.feedStoryStatusDao.getAll()
                                                            : feedDao.feedStoryStatusDao.getByIds(ids));
    }

    /**
     * Rebuild all CDN thumbnails and clear the story from cache
     *
     * @param dpStoryId
     */
    public boolean buildAllStoryThumbnails(Integer dpStoryId)
    {
        GetSuccessStatusResponse response;
        try
        {
            response = dpServices.buildAllStoryThumbnails(dpStoryId);
        }
        catch (Exception e)
        {
            return false;
        }
        entityService.flushStory(dpStoryId);

        return response.getSuccess();
    }

    /**
     * Checks for existence of feedUrl in Feed, if missing, publishes to S3 and updates persistent store
     *
     * @param feedId
     */
    private void updatePublishedFeed(int feedId)
    {
        publishService.publishFeed(feedId);
//    IfbFeed ifbFeed = feedDao.getById( feedId );

        // If feed url hasn't been saved yet (or doesn't match what's the most current URL), save/update it
//    if ( ifbFeed.getFeedUrl() == null || 
//         ifbFeed.getFeedUrl().isEmpty() || 
//         !publishService.getFeedUrl( ifbFeed.getFeedKey() ).equals( ifbFeed.getFeedUrl() ) ) {
//      ifbFeed.setFeedUrl( publishService.getFeedUrl( ifbFeed.getFeedKey() ) );
//      feedDao.update( ifbFeed );
//    }
    }
}
