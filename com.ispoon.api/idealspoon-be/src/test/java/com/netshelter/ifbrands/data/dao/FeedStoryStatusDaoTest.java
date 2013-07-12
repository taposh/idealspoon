package com.netshelter.ifbrands.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.data.dao.FeedDao.FeedStoryStatusDao;
import com.netshelter.ifbrands.data.entity.IfbFeedStoryStatus;

@Component
public class FeedStoryStatusDaoTest extends BaseDaoTest<IfbFeedStoryStatus>
{
  @Autowired FeedStoryStatusDao dao;

  @Override
  public BaseDao<IfbFeedStoryStatus> getDao()
  {
    return dao;
  }

  @Override
  public IfbFeedStoryStatus makeEntity()
  {
    IfbFeedStoryStatus e = new IfbFeedStoryStatus();
    e.setFeedStoryStatusName( uniqueString() );
    e.setCreateTimestamp( now );
    return dao.save( e );
  }
}

