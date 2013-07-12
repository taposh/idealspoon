package com.netshelter.ifbrands.api.model.user;

import java.util.List;


public class UserValueList
{
  private String userKey;
  private List<UserValue> values;

  public String getUserKey()
  {
    return userKey;
  }

  public void setUserKey( String userKey )
  {
    this.userKey = userKey;
  }

  @Override
  public String toString()
  {
    return "UserValueList [userKey=" + userKey + ", values=" + values.size() + "]";
  }

  public List<UserValue> getValues()
  {
    return values;
  }

  public void setValues( List<UserValue> values )
  {
    this.values = values;
  }

}
