package com.netshelter.ifbrands.api.controller;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.netshelter.ifbrands.api.model.ActionObjectType;
import com.netshelter.ifbrands.api.model.ActionType;
import com.netshelter.ifbrands.api.service.ActionLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.model.campaign.Feed;
import com.netshelter.ifbrands.api.model.campaign.Feed.Ordering;
import com.netshelter.ifbrands.api.model.campaign.FeedStory;
import com.netshelter.ifbrands.api.model.campaign.FeedStoryStatus;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.service.FeedService;
import com.netshelter.ifbrands.api.util.GeneralClientException;
import com.netshelter.ifbrands.api.util.MvcUtils;
import com.netshelter.ifbrands.util.KeyGeneratorUtils.KeyGenerator;

/**
 * Controller for 'feed' API calls. A 'Feed' is a collection of stories.
 * It belongs to a Campaign and owns a collection of FeedStories
 *
 * @author bgray
 */
@Controller("feedController")
@RequestMapping("/feed")
public class FeedController
        extends BaseController
{
    @Autowired
    private FeedService feedService;
    @Autowired
    private ActionLogService actionLogService;
    @Autowired
    private KeyGenerator keyGenerator;

    /**
     * Flush the cache.
     */
    @RequestMapping(value = "/flush")
    @ResponseBody
    public GenericStatus flushCache()
    {
        feedService.flushCache();
        return GenericStatus.okay(FeedService.FEED_CACHE + " cache flushed");
    }

    /////////////////////
    // Feed Management //
    /////////////////////

    /**
     * Create a Feed object.  A unique key will be assigned.
     *
     * @param name Name of Feed
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public Feed createFeed(@RequestParam("name") String name)
    {
        logger.info("/feed/create [%s]", name);
        String key = keyGenerator.generateKey();
        return feedService.createFeed(key, name, Ordering.AUTO);
    }

    /**
     * This method takes in a feed id, name and campaignId, then creates a new feed, with the stories of the
     * source feed associated with it.
     *
     * @param feedId
     * @param name - required form parameter
     * @return newly created Feed object
     */
    @RequestMapping( "/clone/{feedId}" )
    @ResponseBody
    public GenericPayload<Feed> cloneFeed(
            @PathVariable( "feedId" ) Integer feedId,
            @RequestParam( value="name"     , required=true ) String name
    )
    {
        logger.info( "/clone/%s [%s]", feedId, name );
        Feed existingFeed = feedService.getFeed(feedId);
        String key = keyGenerator.generateKey();
        String feedName = existingFeed.getName();
        Integer feedCampaignId = null;
        if (StringUtils.isNotBlank(name))
        {
            feedName = name;
        }
        Feed newFeed = feedService.createFeed(key, feedName, existingFeed.getOrdering());
        List<FeedStory> existingStories = existingFeed.getFeedStories();
        for (int i = 0; i < existingStories.size(); i++)
        {
            FeedStory feedStory = existingStories.get(i);
            try
            {
                feedService.addFeedStory(
                    newFeed.getId(),
                    Collections.singleton(feedStory.getStoryId()),
                    feedStory.getStatus().getId(),
                    feedStory.getUpdateSource(),
                    feedStory.getIgnoreBot()
                );
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        newFeed = feedService.getFeed(newFeed.getId()); // re-fetch with newly created feed stories
        return new GenericPayload<Feed>( "feed", newFeed );
    }

    /**
     * Delete a Feed obect.
     *
     * @param feedId ID of Feed to delete
     * @return GenericStatus showing success/failure
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public GenericStatus deleteFeed(@PathVariable("id") Integer feedId)
    {
        logger.info("/feed/delete/%s", feedId);
        boolean success = feedService.deleteFeed(feedId);
        return GenericStatus.successFail(success);
    }

    /**
     * Updae a Feed object.  Null fields will be ignored.
     *
     * @param feedId ID of Feed to update
     * @param feedName new name
     * @return updated Feed
     */
    @RequestMapping("/update/{id}")
    @ResponseBody
    public Feed updateFeed(@PathVariable("id") Integer feedId,
                           @RequestParam(value = "name", required = false) String feedName)
    {
        logger.info("/update/%s [%s]", feedId, feedName);
        return feedService.updateFeed(feedId, feedName);
    }

    /**
     * Get the set of Feeds according to a set of filters.  This may return an empty set.
     *
     * @param feedFilter Set of IDs on which to filter (comma-separated).  Use "-" for all IDs.
     * @param feedKey Feed Key on which to filter
     * @return set of filtered objects
     */
    @RequestMapping("/{ids}")
    @ResponseBody
    public GenericPayload<Collection<Feed>> getFeeds(@PathVariable("ids") String feedFilter,
                                                     @RequestParam(value = "key", required = false) String feedKey)
    {
        logger.info("/%s [%s]", feedFilter, feedKey);
        List<Integer> ids = MvcUtils.getIdsFromFilter(feedFilter);
        Collection<Feed> feeds = feedService.getFeeds(ids, feedKey);
        logger.debug("...%d found", feeds.size());
        return new GenericPayload<Collection<Feed>>("feeds", feeds);
    }

    /**
     * Publish specified feeds.
     *
     * @param feedFilter Set of IDs of feeds to publish (comma-separated).  Use "-" for all IDs.
     * @param feedKey Feed Key on which to filter
     * @return set of filtered objects
     */
    @RequestMapping("/publish/{ids}")
    @ResponseBody
    public GenericStatus publishFeeds(@PathVariable("ids") String feedFilter,
                                      @RequestParam(value = "key", required = false) String feedKey)
    {
        logger.info("/%s [%s]", feedFilter, feedKey);
        List<Integer> ids = MvcUtils.getIdsFromFilter(feedFilter);
        Collection<Feed> feeds = feedService.getFeeds(ids, feedKey);
        boolean success = feedService.publishFeeds(feeds, false);
        return GenericStatus.successFail(success);
    }

    /**
     * Generate feed story thumbnails.
     *
     * @param feedFilter Set of IDs of feeds to generate thumbs for (comma-separated).  Use "-" for all IDs.
     * @param feedKey Feed Key on which to filter
     * @return set of filtered objects
     */
    @RequestMapping("/generatethumbs/{ids}")
    @ResponseBody
    public GenericStatus generateFeedThumbnails(@PathVariable("ids") String feedFilter,
                                                @RequestParam(value = "key", required = false) String feedKey)
    {
        logger.info("/%s [%s]", feedFilter, feedKey);
        List<Integer> ids = MvcUtils.getIdsFromFilter(feedFilter);
        Collection<Feed> feeds = feedService.getFeeds(ids, feedKey);
        boolean success = feedService.publishFeeds(feeds, true);
        return GenericStatus.successFail(success);
    }

    ///////////////////////////
    // Feed Story Management //
    ///////////////////////////


    /** Author: Taposh Date: July 8th 2013
     * Create a functionality, which effectively adds multiple DpStories to a multiple Feeds.
     *
     * @param feedIdList ID of Feeds to modify
     * @param dpStoryList ID of DpStory to add to Feed
     * @param updateSourceStr - optional update source (human / bot / etc...)
     * @param feedStoryStatusId Status for this story
     * @return FeedStory object
     * @ignoreBotStr - optional ignore bot (true / false)
     */

    @RequestMapping("/story/add/feed:{feedId}")
    @ResponseBody
    public Collection<FeedStory> addFeedStory(@PathVariable("feedId") String feedIdList,
                                              @RequestParam("stories") String dpStoryList,
                                              @RequestParam(value = "update_source",
                                                      defaultValue = "") String updateSourceStr,
                                              @RequestParam(value = "ignore_bot",
                                                      defaultValue = "") String ignoreBotStr,
                                              @RequestParam("status") Integer feedStoryStatusId)
    {
        logger.info("/feed/story/add [%s,%s,%s]", feedIdList, dpStoryList, feedStoryStatusId);

        //list of feeds to be added stories
        List<Integer> feedIds = MvcUtils.getIdsFromFilter(feedIdList);

        //list of stories to be added to feeds
        List<Integer> dpStoryIds = MvcUtils.getIdsFromFilter(dpStoryList);

        String updateSource = null;
        Boolean ignoreBot = null;
        if (StringUtils.isNotBlank(updateSourceStr))
        {
            updateSource = updateSourceStr;
        }
        if (StringUtils.isNotBlank(ignoreBotStr))
        {
            ignoreBot = Boolean.parseBoolean(ignoreBotStr);
        }

        //addfeedstory needs to be overloaded to get
        return feedService.addFeedStory(feedIds, dpStoryIds, feedStoryStatusId, updateSource, ignoreBot);
    }



    /**
     * Create a FeedStory, which effectively adds a DpStory to a Feed.
     *
     * @param feedId ID of Feed to modify
     * @param dpStoryList ID of DpStory to add to Feed
     * @param updateSourceStr - optional update source (human / bot / etc...)
     * @param feedStoryStatusId Status for this story
     * @return FeedStory object
     * @ignoreBotStr - optional ignore bot (true / false)
     */
   /*
    @RequestMapping("/story/add/feed:{feedId}")
    @ResponseBody
    public Collection<FeedStory> addFeedStory(@PathVariable("feedId") Integer feedId,
                                              @RequestParam("stories") String dpStoryList,
                                              @RequestParam(value = "update_source",
                                                            defaultValue = "") String updateSourceStr,
                                              @RequestParam(value = "ignore_bot",
                                                            defaultValue = "") String ignoreBotStr,
                                              @RequestParam("status") Integer feedStoryStatusId)
    {
        logger.info("/feed/story/add [%s,%s,%s]", feedId, dpStoryList, feedStoryStatusId);
        List<Integer> dpStoryIds = MvcUtils.getIdsFromFilter(dpStoryList);
        String updateSource = null;
        Boolean ignoreBot = null;
        if (StringUtils.isNotBlank(updateSourceStr))
        {
            updateSource = updateSourceStr;
        }
        if (StringUtils.isNotBlank(ignoreBotStr))
        {
            ignoreBot = Boolean.parseBoolean(ignoreBotStr);
        }
        return feedService.addFeedStory(feedId, dpStoryIds, feedStoryStatusId, updateSource, ignoreBot);
    }

     */

    /**
     * Delete a Story from a Feed.
     *
     * @param feedId feed ID from which to delete
     * @param dpStoryList DpStoryId to delete
     * @return GenericStatus showing success/failure
     */
    @RequestMapping("/story/remove/feed:{feedId}/story:{storyList}")
    @ResponseBody
    public GenericStatus removeFeedStory(@PathVariable("feedId") Integer feedId,
                                         @PathVariable("storyList") String dpStoryList)
    {
        logger.info("/feed/story/remove/feed:%s/story:%s", feedId, dpStoryList);
        List<Integer> dpStoryIds = MvcUtils.getIdsFromFilter(dpStoryList);
        boolean success = feedService.removeFeedStory(feedId, dpStoryIds);
        return GenericStatus.successFail(success);
    }

    /**
     * Update a Story within a Feed.  Null fields will be ignored.
     *
     * @param feedId feed ID from which to update
     * @param dpStoryList DpStoryId list to update
     * @param updateSourceStr - optional update source (human / bot / etc...)
     * @param feedStoryStatusId new status
     * @return updated entity
     * @ignoreBotStr - optional ignore bot (true / false)
     */
    @RequestMapping("/story/update/feed:{feedId}/story:{storyList}")
    @ResponseBody
    public Collection<FeedStory> updateFeedStory(@PathVariable("feedId") Integer feedId,
                                                 @PathVariable("storyList") String dpStoryList,
                                                 @RequestParam(value = "update_source",
                                                               defaultValue = "") String updateSourceStr,
                                                 @RequestParam(value = "ignore_bot",
                                                               defaultValue = "") String ignoreBotStr,
                                                 @RequestParam(value = "status",
                                                               required = false) Integer feedStoryStatusId)
    {
        logger.info("/feed/story/update/feed:%s/story:%s [%s]", feedId, dpStoryList, feedStoryStatusId);
        List<Integer> dpStoryIds = MvcUtils.getIdsFromFilter(dpStoryList);
        String updateSource = null;
        Boolean ignoreBot = null;
        if (StringUtils.isNotBlank(updateSourceStr))
        {
            updateSource = updateSourceStr;
        }
        if (StringUtils.isNotBlank(ignoreBotStr))
        {
            ignoreBot = Boolean.parseBoolean(ignoreBotStr);
        }
        return feedService.updateFeedStory(feedId, dpStoryIds, feedStoryStatusId, updateSource, ignoreBot);
    }

    /**
     * Get the set of Story's in a Feed.  This may return an empty set.
     *
     * @param feedId Feed on which to filter
     * @param feedStoryStatusIds Statuses on which to filter
     * @return set of filtered objects
     */
    @RequestMapping("/story/feed:{feedId}")
    @ResponseBody
    public GenericPayload<Collection<FeedStory>> getFeedStories(
            @PathVariable("feedId") Integer feedId,
            @RequestParam(value = "status", required = false) List<Integer> feedStoryStatusIds)
    {
        logger.info("/feed/story/feed:%s [%s]", feedId, feedStoryStatusIds);
        Collection<FeedStory> feedStories = feedService.getFeedStories(feedId, feedStoryStatusIds);
        return new GenericPayload<Collection<FeedStory>>("feedStories", feedStories);
    }

    /**
     * Set a custom image for a story
     *
     * @param storyId The id of the story to modify the image thumbnails for
     * @param imageUrl The url of the image to create thumnails for
     * @return GenericStatus Whether the request succeeded/failed
     */
    @RequestMapping(value = "/storyimage/{id}")
    @ResponseBody
    public Story setCustomStoryImage(@PathVariable("id") Integer storyId,
                                     @RequestParam("imageurl") String imageUrl)
    {
        try
        {
            Story story = null;
            imageUrl = UriUtils.decode(imageUrl, "UTF-8");
            logger.info("/feed/storyimage/%s [%s]", storyId, imageUrl);
            story = feedService.setCustomStoryImage(storyId, imageUrl);
            return story;
        }
        catch (UnsupportedEncodingException e)
        {
            throw new GeneralClientException("Cannot decode imageurl", e);
        }
    }

    /**
     * Apply a new ordering to a FeedStory
     *
     * @param feedId
     * @param storyList
     * @return
     */
    @RequestMapping("/story/order/feed:{feedId}")
    @ResponseBody
    public GenericStatus reorderFeedStories(@PathVariable("feedId") Integer feedId,
                                            @RequestParam("stories") String storyList)
    {
        logger.info("/feed/story/order/feed:%s [%s]", feedId, storyList);
        List<Integer> storyIds = MvcUtils.getIdsFromFilter(storyList);
        boolean success = feedService.orderFeedStories(feedId, storyIds);
        return GenericStatus.successFail(success);
    }


    ////////////////////////////////
    // FeedStoryStatus Management //
    ////////////////////////////////

    /**
     * Create a FeedStoryStatus object.
     *
     * @param name name of status
     * @return new object
     */
    @RequestMapping(value = "/story/status/create")
    @ResponseBody
    public FeedStoryStatus createFeedStoryStatus(@RequestParam("name") String name)
    {
        logger.info("/feed/storystatus/create [%s]", name);
        return feedService.createFeedStoryStatus(name);
    }

    /**
     * Delete a FeedStoryStatus object.
     *
     * @param feedStoryStatusId ID to delete
     * @return GenericStatus showing success/failure
     */
    @RequestMapping(value = "/story/status/delete/{id}")
    @ResponseBody
    public GenericStatus deleteFeedStoryStatus(@PathVariable("id") Integer feedStoryStatusId)
    {
        logger.info("/feed/storystatus/delete/%s", feedStoryStatusId);
        boolean success = feedService.deleteFeedStoryStatus(feedStoryStatusId);
        return GenericStatus.successFail(success);
    }

    /**
     * Get the set of FeedStoryStatus's according to a set of filters.  This may return an empty set.
     *
     * @param filter - Set of IDs on which to filter (comma-separated).  Use "-" for all IDs.
     * @return set of filtered objects
     */
    @RequestMapping(value = "/story/status/{ids}")
    @ResponseBody
    public GenericPayload<Collection<FeedStoryStatus>> getFeedStoryStatus(@PathVariable("ids") String filter)
    {
        logger.info("/feed/storystatus/%s", filter);
        List<Integer> ids = MvcUtils.getIdsFromFilter(filter);
        Collection<FeedStoryStatus> feedStoryStatuses = feedService.getFeedStoryStatuses(ids);
        logger.debug("...%d found", feedStoryStatuses.size());
        return new GenericPayload<Collection<FeedStoryStatus>>("feedStoryStatuses", feedStoryStatuses);
    }
}
