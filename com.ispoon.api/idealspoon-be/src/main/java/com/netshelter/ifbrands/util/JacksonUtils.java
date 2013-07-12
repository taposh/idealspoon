package com.netshelter.ifbrands.util;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Utilities for working with Jackson.
 * @author bgray
 *
 */
public class JacksonUtils
{
  /** Produce a custom ObjectMaper.  This ObjectMapper will:
   * 1. write date as timestamps, not Strings, on serialization
   * 2. ignore unknown properties on deserialization
   * @author bgray
   *
   */
  public static class JacksonCustomObjectMapper extends ObjectMapper
  {
    public JacksonCustomObjectMapper()
    {
      super.setSerializationInclusion( JsonSerialize.Inclusion.NON_NULL );
      super.configure( SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false );
      super.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );
      super.registerModule( JodaUtils.getJacksonDateTimeZoneModule() );
    }
  }
}
