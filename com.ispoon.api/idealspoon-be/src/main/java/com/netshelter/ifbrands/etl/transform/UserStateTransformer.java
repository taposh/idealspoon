package com.netshelter.ifbrands.etl.transform;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.entity.Brand;
import com.netshelter.ifbrands.api.model.user.UserState;
import com.netshelter.ifbrands.data.entity.IfbUserState;

@Component
public class UserStateTransformer
{
  public IfbUserState transform( UserState pojo )
  {
    IfbUserState entity = new IfbUserState();
    entity.setUserStateId( pojo.getId() );
    entity.setUserKey( pojo.getUserKey() );
    entity.setDpBrandId( pojo.getBrand().getId() );
    entity.setLastLogin( pojo.getLastLogin().toDate() );
    return entity;
  }

  public UserState transform( IfbUserState entity )
  {
    UserState pojo = new UserState();
    pojo.setId( entity.getUserStateId() );
    pojo.setUserKey( entity.getUserKey() );
    pojo.setBrand( new Brand( entity.getDpBrandId() ));
    pojo.setLastLogin( new DateTime( entity.getLastLogin() ));
    return pojo;
  }

}
