package com.netshelter.ifbrands.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.data.dao.AdDao.AdStatusDao;
import com.netshelter.ifbrands.data.entity.IfbAdStatus;

@Component
public class AdStatusDaoTest extends BaseDaoTest<IfbAdStatus>
{
  @Autowired AdStatusDao dao;

  @Override
  public BaseDao<IfbAdStatus> getDao()
  {
    return dao;
  }

  @Override
  public IfbAdStatus makeEntity()
  {
    IfbAdStatus e = new IfbAdStatus();
    // Appending millis to overcome unique constraint
    e.setAdStatusName( uniqueString() );
    e.setCreateTimestamp( now );
    return dao.save( e );
  }
}

