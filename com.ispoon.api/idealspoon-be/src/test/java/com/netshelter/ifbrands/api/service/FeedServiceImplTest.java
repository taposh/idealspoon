package com.netshelter.ifbrands.api.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.api.model.campaign.Feed;
import com.netshelter.ifbrands.api.model.campaign.Feed.Ordering;
import com.netshelter.ifbrands.api.model.campaign.FeedStory;
import com.netshelter.ifbrands.api.model.campaign.FeedStoryStatus;
import com.netshelter.ifbrands.data.dao.CampaignDaoTest;
import com.netshelter.ifbrands.data.dao.FeedDao.FeedStoryStatusDao;
import com.netshelter.ifbrands.data.dao.FeedDaoTest;
import com.netshelter.ifbrands.data.dao.FeedStoryStatusDaoTest;

public class FeedServiceImplTest extends BaseServiceTest
{
  @Autowired
  private FeedService service;
  @Autowired
  private CampaignDaoTest campaignDaoTest;
  @Autowired
  private FeedDaoTest feedDaoTest;
  @Autowired
  private FeedStoryStatusDaoTest feedStoryStatusDaoTest;
  @Autowired
  private FeedStoryStatusDao feedStoryStatusDao;

  @Test
  public void testFeed()
      throws JsonGenerationException, JsonMappingException, IOException
  {
    int cId = campaignDaoTest.makeEntity().getCampaignId();
    // Create
    Feed f0 = service.createFeed( "key1", "name", Ordering.AUTO );
    assertNotNull( f0 );
    service.createFeed( "key2", "name", Ordering.AUTO );
    // Get
    int fId = f0.getId();
    Feed f1 = service.getFeed( fId );
    assertEquals( f0.getKey(), f1.getKey() );
    // Get Collection by IDs
    Collection<Feed> f2 = service.getFeeds( Collections.singleton( fId ), null );
    assertEquals( 1, f2.size() );
    assertEquals( f0.getKey(), f2.iterator().next().getKey() );
    // Get Collection by key
    f2 = service.getFeeds( null, f0.getKey() );
    assertEquals( 1, f2.size() );
    // Get Collection by campaign
    assertEquals( f0.getKey(), f2.iterator().next().getKey() );
    f2 = service.getFeeds( null, null );
    assertEquals( 2, f2.size() );
    // Update
    Feed f3 = service.updateFeed( fId, "newName" );
    Feed f4 = service.getFeed( fId );
    assertEquals( "newName", f3.getName() );
    assertEquals( "newName", f4.getName() );
    // Delete
    boolean b = service.deleteFeed( fId );
    assertTrue( b );
    Feed f5 = service.getFeed( fId );
    assertNull( f5 );

  }

  @Test
  public void testFeedStory()
      throws JsonGenerationException, JsonMappingException, IOException
  {
    int sId = 2483398;
    int fId = feedDaoTest.makeEntity().getFeedId();
    int fssId0 = feedStoryStatusDaoTest.makeEntity().getFeedStoryStatusId();
    int fssId1 = feedStoryStatusDaoTest.makeEntity().getFeedStoryStatusId();

    // Add
    FeedStory fs0 = service.addFeedStory( Collections.singleton(fId), Arrays.asList( sId ), fssId0, null, null ).iterator().next();
    assertNotNull( fs0 );
    // Get
    FeedStory fs1 = service.getFeedStory( fId, sId );
    assertEquals( fs0.getId(), fs1.getId() );
    // Get Collection by ids
    Collection<FeedStory> fs2 = service.getFeedStories( fId, null );
    assertEquals( 1, fs2.size() );
    assertEquals( fs0.getId(), fs2.iterator().next().getId() );
    // Update
    FeedStory fs3 = service.updateFeedStory( fId, Arrays.asList( sId ), fssId1, null, null ).iterator().next();
    FeedStory fs4 = service.getFeedStory( fId, sId );
    assertTrue( fssId1 == fs3.getStatus().getId() );
    assertTrue( fssId1 == fs4.getStatus().getId() );
    // Delete
    boolean b = service.removeFeedStory( fId, Arrays.asList( sId ));
    assertTrue( b );
    FeedStory fs5 = service.getFeedStory( fId, sId );
    assertNull( fs5 );
  }

  @Test
  public void testFeedStoryStatus()
  {
    // Create
    FeedStoryStatus fss0 = service.createFeedStoryStatus( "status" );
    assertNotNull( fss0 );
    // Get
    FeedStoryStatus fss1 = service.getFeedStoryStatus( fss0.getId() );
    assertEquals( fss0.getName(), fss1.getName() );
    // Get Collection
    Collection<FeedStoryStatus> fss2 = service.getFeedStoryStatuses( Collections.singleton( fss0.getId() ));
    assertEquals( 1, fss2.size() );
    assertEquals( fss0.getName(), fss2.iterator().next().getName() );
    // Delete
    boolean b = service.deleteFeedStoryStatus( fss0.getId() );
    assertTrue( b );
    FeedStoryStatus fss3 = service.getFeedStoryStatus( fss0.getId() );
    assertNull( fss3 );
  }

  @Test
  public void testSetCustomStoryImage()
  {
    int sId = 2483398;
    int fId = feedDaoTest.makeEntity().getFeedId();
    int fssId0 = feedStoryStatusDao.ACTIVE.getFeedStoryStatusId();

    // Add
    Collection<FeedStory> fs0 = service.addFeedStory( fId, Arrays.asList( sId ), fssId0, null, null );
    assertNotNull( fs0 );
    // Get
    FeedStory fs1 = service.getFeedStory( fId, sId );
    assertEquals( fs0.iterator().next().getId(), fs1.getId() );
    service.setCustomStoryImage( sId, "http://farm3.static.flickr.com/2248/2282734669_596c7822ee.jpg" );
  }

}
