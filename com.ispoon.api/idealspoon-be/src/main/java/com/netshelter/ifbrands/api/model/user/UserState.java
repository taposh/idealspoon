package com.netshelter.ifbrands.api.model.user;

import org.joda.time.DateTime;

import com.netshelter.ifbrands.api.model.entity.Brand;
import com.netshelter.ifbrands.util.MoreObjects;

public class UserState
{
  private Integer id;
  private Brand brand;
  private String userKey;
  private DateTime lastLogin;

  public void setId( Integer id )
  {
    this.id = id;
  }

  public Integer getId()
  {
    return id;
  }

  public Brand getBrand()
  {
    return brand;
  }

  public void setBrand( Brand brand )
  {
    this.brand = brand;
  }

  public String getUserKey()
  {
    return userKey;
  }

  public void setUserKey( String userKey )
  {
    this.userKey = userKey;
  }

  public DateTime getLastLogin()
  {
    return lastLogin;
  }

  public void setLastLogin( DateTime lastLogin )
  {
    this.lastLogin = lastLogin;
  }

  @Override
  public String toString()
  {
    return "UserState [id=" + id + ", brand=" + brand + ", userKey=" + userKey + ", lastLogin=" + lastLogin
        + "]";
  }

  @Override
  public boolean equals( Object obj )
  {
    if( obj instanceof UserState ) {
      UserState that = (UserState)obj;
      if( MoreObjects.equivalent( this.getBrand(), that.getBrand() )
          && MoreObjects.equivalent( this.getUserKey(), that.getUserKey() )
          && MoreObjects.equivalent( this.getLastLogin(), that.getLastLogin() ) ) return true;
    }
    return false;
  }
}
