package com.netshelter.ifbrands.data.dao;

import java.io.Serializable;
import java.util.List;

import com.netshelter.ifbrands.data.entity.IfbUserState;


public class UserStateDao extends BaseDao<IfbUserState>
{
  @Override
  public Class<IfbUserState> getEntityClass()
  {
    return IfbUserState.class;
  }

  @Override
  protected Serializable getIdentifier( IfbUserState entity )
  {
    return entity.getUserStateId();
  }

  @Override
  protected void updateIdentifier( IfbUserState entity, Serializable id )
  {
    entity.setUserStateId( (Integer)id );
  }

  @Override
  public boolean theSame( IfbUserState a, IfbUserState b )
  {
    return a.getUserKey().equals( b.getUserKey() )
        && a.getDpBrandId() == b.getDpBrandId();
  }

  @Override
  public String asString( IfbUserState entity )
  {
    return String.format( "IfbUserState(%d:%s,%d)",
                          entity.getUserStateId(),
                          entity.getUserKey(),
                          entity.getDpBrandId() );
  }

  public IfbUserState getByUserKey( String userKey )
  {
    return findFirstByProperty( "userKey", userKey );
  }

  public List<IfbUserState> getEntitiesForBrand( int dpBrandId )
  {
    return findByProperty( "dpBrandId", dpBrandId );
  }
}

