package com.netshelter.ifbrands.api.service;

import java.util.List;

import com.netshelter.ifbrands.api.model.user.UserInfo;
import com.netshelter.ifbrands.api.model.user.UserState;
import com.netshelter.ifbrands.api.model.user.UserValue;

public interface UserService
{
  public static final String USER_CACHE = "ifb.mvc.user";

  public void flushCache();
  public List<UserState> getUser( List<String> userKeys );
  public UserState createUser( String userKey, int brandId );
  public UserState loginUser( String userKey );
  public boolean deleteUser( String userKey );
  public List<UserValue> getValues( String userKey, String keyName );
  public boolean setValue( String userKey, String keyName, String value );
  public UserInfo getUserInfo( String userKey );
  public List<UserInfo> getUserInfo( List<String> userKeys );
}
