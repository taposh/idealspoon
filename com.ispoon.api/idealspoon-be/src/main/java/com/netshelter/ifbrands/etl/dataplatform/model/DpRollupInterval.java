package com.netshelter.ifbrands.etl.dataplatform.model;

import org.joda.time.DateTime;

public enum DpRollupInterval
{
  HOUR,DAY,INFINITE;

  public DateTime addInterval( DateTime dt )
  {
    switch( this ) {
      case HOUR:     return dt.plusHours( 1 );
      case DAY:      return dt.plusDays( 1 );
      case INFINITE: return new DateTime( Long.MAX_VALUE );
    }
    throw new IllegalStateException( "DpRollupInterval.addInterval() missing support for "+ this );
  }

  public static DpRollupInterval valueFrom( String text )
  {
    return valueOf( text.toUpperCase() );
  }
}
