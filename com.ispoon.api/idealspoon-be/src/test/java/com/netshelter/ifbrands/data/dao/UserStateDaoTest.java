package com.netshelter.ifbrands.data.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.data.entity.IfbUserState;

public class UserStateDaoTest extends BaseDaoTest<IfbUserState>
{
  @Autowired
  private UserStateDao dao;

  @Override
  public BaseDao<IfbUserState> getDao()
  {
    return dao;
  }

  @Override
  public IfbUserState makeEntity()
  {
    IfbUserState e = new IfbUserState();
    e.setUserKey( uniqueString() );
    e.setLastLogin( new Date() );
    e.setDpBrandId( 5 );
    e.setCreateTimestamp( now );
    return dao.save( e );
  }

}
