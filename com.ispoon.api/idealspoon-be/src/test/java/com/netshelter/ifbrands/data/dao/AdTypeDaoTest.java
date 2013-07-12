package com.netshelter.ifbrands.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.data.dao.AdDao.AdTypeDao;
import com.netshelter.ifbrands.data.entity.IfbAdType;

@Component
public class AdTypeDaoTest extends BaseDaoTest<IfbAdType>
{
  @Autowired AdTypeDao dao;

  @Override
  public BaseDao<IfbAdType> getDao()
  {
    return dao;
  }

  @Override
  public IfbAdType makeEntity()
  {
    IfbAdType e = new IfbAdType();
    e.setAdTypeName( uniqueString() );
    e.setCreateTimestamp( now );
    return dao.save( e );
  }
}

