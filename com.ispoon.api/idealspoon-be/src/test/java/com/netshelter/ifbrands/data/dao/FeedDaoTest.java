package com.netshelter.ifbrands.data.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.campaign.Feed;
import com.netshelter.ifbrands.data.entity.IfbFeed;
import com.netshelter.ifbrands.data.entity.IfbFeedStory;

@Component
public class FeedDaoTest extends BaseDaoTest<IfbFeed>
{
  @Autowired private FeedDao dao;
  @Autowired private CampaignDaoTest campaignDaoTest;
  @Autowired private FeedStoryDaoTest feedStoryDaoTest;

  @Override
  public BaseDao<IfbFeed> getDao()
  {
    return dao;
  }

  @Test
  public void testDao() throws Exception
  {
    // Test eager fetching
    IfbFeedStory fs = feedStoryDaoTest.makeEntity();
    IfbFeed a = dao.getById( fs.getIfbFeed().getFeedId() );
    assertNotNull( a.getIfbFeedStories().iterator().next().getCreateTimestamp() );
    assertNotNull( a.getIfbFeedStories().iterator().next().getIfbFeedStoryStatus().getFeedStoryStatusName() );
  }

  @Override
  public IfbFeed makeEntity()
  {
    IfbFeed e = new IfbFeed();
    e.setOrdering( Feed.Ordering.ORDINAL.toString() );
    e.setFeedKey( uniqueString() );
    e.setFeedName( "name" );
    //e.setFeedUrl( uniqueString() );
    e.setLastModified( now );
    e.setCreateTimestamp( now );
    return dao.save( e );
  }
}
