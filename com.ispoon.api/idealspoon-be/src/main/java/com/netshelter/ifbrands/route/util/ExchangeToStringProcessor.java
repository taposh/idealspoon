package com.netshelter.ifbrands.route.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.commons.lang.StringUtils;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.util.DebugUtil;

public class ExchangeToStringProcessor implements Processor
{
  private static Logger logger = LoggerFactory.getLogger();
  private static final int MAX_TRACE_DEPTH = 10;
  public static final String DATESTAMP_FORMAT = "yyyyMMdd.hhmmss.SSS";
  public BodyInfoAgent bodyInfoAgent;

  public ExchangeToStringProcessor()
  {}

  public ExchangeToStringProcessor( BodyInfoAgent bodyInfoAgent )
  {
    this.bodyInfoAgent = bodyInfoAgent;
  }

  @Override
  public void process( Exchange exchange ) throws Exception
  {
    try {
      Message msg = exchange.getIn();
      Object body = msg.getBody();

      String datetime = new SimpleDateFormat( DATESTAMP_FORMAT ).format( new Date() );
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter( sw );

      pw.println( "Dead Letter \t: " + datetime );
      pw.println( "RouteId     \t: " + exchange.getFromRouteId() );
      pw.println( "IsFault     \t: " + msg.isFault() );
      pw.println( "ExchangeId  \t: " + exchange.getExchangeId() );
      pw.println( "MessageId   \t: " + msg.getMessageId() );
      pw.println( "FromEndpoint\t: " + exchange.getFromEndpoint() );

      pw.println( "Properties" );
      if( exchange.hasProperties() ) {
        for( Map.Entry<String, Object> e : exchange.getProperties().entrySet() ) {
          pw.printf( "   %-30s = %s\n", e.getKey(), StringUtils.abbreviate( String.valueOf( e.getValue() ), 200 ));
        }
      } else {
        pw.println( "\tnone" );
      }

      pw.println( "Headers" );
      if( msg.hasHeaders() ) {
        for( Map.Entry<String, Object> e : msg.getHeaders().entrySet() ) {
          pw.printf( "   %-30s \t= %s\n", e.getKey(), StringUtils.abbreviate( String.valueOf( e.getValue() ), 200 ));
        }
      } else {
        pw.println( "\tnone" );
      }

      pw.println();
      pw.println( "=== BODY ===" );
      if( body == null ) {
        pw.println( "null" );
      } else {
        pw.println( body.getClass().getSimpleName() );
        if( bodyInfoAgent != null ) {
          bodyInfoAgent.writeBodyInfo( pw, body );
        } else {
          pw.println( body.toString() );
        }
      }
      pw.println( "=== BODY ===" );

      pw.println();
      pw.println( "=== CAUGHT EXCEPTION ===" );
      if( exchange.getProperty( Exchange.EXCEPTION_CAUGHT ) != null ) {
        Throwable th = (Exception)exchange.getProperty( Exchange.EXCEPTION_CAUGHT );
        String trace = DebugUtil.getStackTraceAsString( th, MAX_TRACE_DEPTH );
        pw.print( trace );
      } else {
        pw.println( "none" );
      }
      pw.println( "=== CAUGHT EXCEPTION ===" );

      pw.println();
      pw.println( "=== GET EXCEPTION ===" );
      if( exchange.getException() != null ) {
        String trace = DebugUtil.getStackTraceAsString( exchange.getException(), MAX_TRACE_DEPTH );
        pw.print( trace );
      } else {
        pw.println( "none" );
      }
      pw.println( "=== GET EXCEPTION ===" );

      String s = sw.toString();
      logger.error( s );
      msg.setBody( s );
    } catch( Exception e ) {
      logger.warn( "Cannot parse ExchangeToInputStream", e );
    }
  }

  public static interface BodyInfoAgent
  {
    public void writeBodyInfo( PrintWriter pw, Object body );
  }
}

