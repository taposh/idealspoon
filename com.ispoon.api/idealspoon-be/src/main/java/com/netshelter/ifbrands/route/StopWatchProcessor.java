package com.netshelter.ifbrands.route;

import org.apache.camel.Exchange;

public class StopWatchProcessor
{
  public static final String STOPWATCH_STOP     = "NS-StopWatch-stop";
  public static final String STOPWATCH_START    = "NS-StopWatch-start";
  public static final String STOPWATCH_DURATION = "NS-StopWatch-duration";

  public void start( Exchange exchange )
  {
    exchange.getIn().setHeader( STOPWATCH_START, System.currentTimeMillis() );
  }

  public void stop( Exchange exchange )
  {
    long start = exchange.getIn().getHeader( STOPWATCH_START, Long.class ).longValue();
    long stop = System.currentTimeMillis();
    exchange.getIn().setHeader( STOPWATCH_STOP, stop );
    exchange.getIn().setHeader( STOPWATCH_DURATION, (stop-start)/1000 );
  }

}
