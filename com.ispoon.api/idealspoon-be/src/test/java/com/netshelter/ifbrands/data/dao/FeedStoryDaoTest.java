package com.netshelter.ifbrands.data.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.data.dao.FeedDao.FeedStoryDao;
import com.netshelter.ifbrands.data.entity.IfbFeedStory;

@Component
public class FeedStoryDaoTest extends BaseDaoTest<IfbFeedStory>
{
  Logger logger = LoggerFactory.getLogger();
  
  @Autowired FeedStoryDao dao;
  @Autowired FeedDaoTest feedDaoTest;
  @Autowired FeedStoryStatusDaoTest feedStoryStatusDaoTest;

  @Override
  public BaseDao<IfbFeedStory> getDao()
  {
    return dao;
  }

  @Test
  public void testDao() throws Exception
  {
    // Test eager fetching
    IfbFeedStory e = makeEntity();
    IfbFeedStory a = dao.getById( e.getFeedStoryId() );
    assertNotNull( a.getIfbFeedStoryStatus().getFeedStoryStatusName() );
  }

  @Override
  public IfbFeedStory makeEntity()
  {
    IfbFeedStory e = new IfbFeedStory();
    e.setIfbFeed( feedDaoTest.makeEntity() );
    e.setIfbFeedStoryStatus( feedStoryStatusDaoTest.makeEntity() );
    e.setDpStoryId( 5 );
    e.setOrdinal( 5 );
    e.setCreateTimestamp( now );
    e.setUpdateTimestamp( e.getCreateTimestamp() );
    return dao.save( e );
  }
}