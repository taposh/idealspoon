package com.netshelter.ifbrands.api.model.user;

import java.util.AbstractMap;

public class UserValue extends AbstractMap.SimpleEntry<String,String>
{
  public UserValue( String key, String value )
  {
    super( key, value );
  }

}
