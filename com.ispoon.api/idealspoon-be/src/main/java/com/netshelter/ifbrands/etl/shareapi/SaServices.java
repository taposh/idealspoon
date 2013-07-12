package com.netshelter.ifbrands.etl.shareapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.joda.time.DateTime;


public interface SaServices
{
  public String getShortUrl( String brandName, String postTitle, String postUrl ) throws UnsupportedEncodingException;
  public Integer getShortUrlStats( String shortUrlKey, DateTime start, DateTime stop ) throws JsonParseException, JsonMappingException, IOException;
}
