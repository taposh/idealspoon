package com.netshelter.ifbrands.api.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.netshelter.ifbrands.api.model.campaign.Feed;
import com.netshelter.ifbrands.api.model.campaign.FeedStory;
import com.netshelter.ifbrands.api.model.campaign.FeedStoryStatus;
import com.netshelter.ifbrands.api.model.entity.Story;

public interface FeedService
{
  public static final String FEED_CACHE = "ifb.mvc.feed";

  // Cache Services
  public void flushCache();

  public boolean isFeedStoryActive( FeedStory feedStory );
  public boolean isFeedStoryActive( int feedStoryId );

  /////////////////////
  // Feed Management //
  /////////////////////
  public Feed createFeed( String key, String name, Feed.Ordering ordering );
  public boolean deleteFeed( Integer feedId );
  public Feed updateFeed( Integer feedId, String feedName );
  public Feed getFeed( Integer feedId );
  public Collection<Feed> getFeeds( Collection<Integer> feedIds, String feedKey );

  public boolean publishFeeds( Collection<Feed> feeds, boolean generateThumbnails );
 
  ///////////////////////////
  // Feed Story Management //
  ///////////////////////////

  //Author: Taposh  ///////////////////////////
  public Collection<FeedStory> addFeedStory( Collection<Integer> feedIds, Collection<Integer> dpStoryIds, Integer feedStoryStatusId,
                                               String updateSource, Boolean ignoreBot);
  //////////////////////////////////////////////////////
  public Collection<FeedStory> addFeedStory( Integer feedId, Collection<Integer> dpStoryIds, Integer feedStoryStatusId,
                                             String updateSource, Boolean ignoreBot);

  public Collection<FeedStory> addFeedStories( Integer feedId, Map<Integer, Integer> stories,
                                               String updateSource, Boolean ignoreBot);
  
  public boolean removeFeedStory( Integer feed, Collection<Integer> dpStoryIds );
  public Collection<FeedStory> updateFeedStory( Integer feedId, Collection<Integer> dpStoryIds, Integer feedStoryStatusId,
                                                String updateSource, Boolean ignoreBot);
  public FeedStory getFeedStory( Integer feedId, Integer dpStoryId );
  public Collection<FeedStory> getFeedStories( Integer feedId, Collection<Integer> feedStoryStatusIds );
  public Collection<FeedStory> getFeedStories( Collection<Integer> feedIds);
  public boolean orderFeedStories( Integer feedId, List<Integer> dpStoryIds );

  public Story setCustomStoryImage( Integer dpStoryId, String imageUrl );
  public boolean buildAllStoryThumbnails( Integer dpStoryId );

  ////////////////////////////////
  // FeedStoryStatus Management //
  ////////////////////////////////
  public FeedStoryStatus createFeedStoryStatus( String name );
  public boolean deleteFeedStoryStatus( Integer feedStoryStatusId );
  public FeedStoryStatus getFeedStoryStatus( Integer feedStoryStatusId );
  public Collection<FeedStoryStatus> getFeedStoryStatuses( Collection<Integer> feedStoryStatusIds );
}
