package com.netshelter.ifbrands.route.util;

import java.io.InputStream;

import java.io.StringReader;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.input.ReaderInputStream;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;

public class ToInputStreamProcessor implements Processor
{
  private static Logger logger = LoggerFactory.getLogger();
  public static final String CONTENT_LENGTH = "NS-ToInputStreamProcessor-contentLength";

  @Override
  public void process( Exchange exchange ) throws Exception
  {
    int contentLength=0;
    InputStream istrm = null;
    String str = exchange.getIn().getBody( String.class );
    if( str != null ) {
      contentLength = str.length();
      logger.info( "Converting string of %d characters to InputStream", contentLength );
      StringReader sr = new StringReader( str );
      istrm = new ReaderInputStream( sr );
    } else {
      String type = null;
      Object body = exchange.getIn().getBody();
      if( body != null ) type = body.getClass().getSimpleName();
      logger.info( "Cannot convert body of type '%s' to InputStream", type );
    }
    exchange.getIn().setBody( istrm );
    exchange.getIn().setHeader( CONTENT_LENGTH, contentLength );
  }
}
