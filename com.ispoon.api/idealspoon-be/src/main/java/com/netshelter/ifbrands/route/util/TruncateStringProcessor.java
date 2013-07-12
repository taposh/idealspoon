package com.netshelter.ifbrands.route.util;


import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.netshelter.ifbrands.util.MoreStrings;

public class TruncateStringProcessor implements Processor
{
  private int maxChars;
  private String message;

  public TruncateStringProcessor( int maxChars, String message )
  {
    this.maxChars = maxChars;
    this.message = message;
  }

  @Override
  public void process( Exchange exchange )
  {
    Message msg = exchange.getIn();
    String body = msg.getBody( String.class );
    if( body == null ) return;
    body = MoreStrings.truncateLinesByChars( body, maxChars, message );
    msg.setBody( body );
  }
}
