package com.netshelter.ifbrands.etl.dataplatform;

import java.util.Collection;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.netshelter.ifbrands.etl.dataplatform.model.DpSentiment;
import com.netshelter.ifbrands.util.MoreEnums;

/**
 * Useful utilities for dealing with DpServices service.
 * @author bgray
 *
 */
public class DpUtils
{
  /**
   * Parse a collection of sentiment strings into their enum values.
   * @param sentiments Sentiment strings
   * @return DpSentiment enums
   */
  public static Collection<DpSentiment> parseSentiments( Collection<String> sentiments )
  {
    return MoreEnums.parse( sentiments, DpSentiment.class );
  }

  public static DateTimeZone validateAndGetDateTimeZone( DateTime startTime, DateTime endTime )
  {
    if( !startTime.getZone().equals( endTime.getZone() )) {
      throw new IllegalArgumentException( "Cannot specify start/end times in different TimeZones" );
    }
    return startTime.getZone();
  }

}
