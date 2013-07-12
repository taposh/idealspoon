package com.netshelter.ifbrands.route;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;

public abstract class BaseRoute extends RouteBuilder implements CamelContextAware
{
  public final String LOG_TRACE = "log:"+getClass().getName()+"?level=DEBUG&showAll=true&multiline=true";
  public final String LOG_DEBUG = "log:"+getClass().getName()+"?level=DEBUG&showExchangePattern=false&showHeaders=true";
  public final String LOG_INFO  = "log:"+getClass().getName()+"?level=INFO&showExchangePattern=false";
  public final String LOG_WARN  = "log:"+getClass().getName()+"?level=WARN&showAll=true&multiline=true";
  public final String LOG_ERROR = "log:"+getClass().getName()+"?level=WARN&showAll=true&multiline=true";

  protected Logger logger = LoggerFactory.getLogger();
  protected boolean simpleTrace;
  protected CamelContext camelContext;
  protected ProducerTemplate producerTemplate;

  @Override
  public void setCamelContext( CamelContext camelContext )
  {
    this.camelContext = camelContext;
    this.producerTemplate = camelContext.createProducerTemplate();
  }

  @Override
  public CamelContext getCamelContext()
  {
    return camelContext;
  }

  public void setSimpleTrace( boolean simpleTrace )
  {
    this.simpleTrace = simpleTrace;
  }

  public RouteDefinition fromN( String uri )
  {
    // Determine pretty name from scheme
    String routeId = uri;
    // Clip query
    int qmIdx = uri.indexOf( '?' );
    if( qmIdx > 0 ) {
      routeId = uri.substring( 0, qmIdx );
    }
    // Optionally trim scheme
    int clIdx = uri.indexOf( ':' );
    if( routeId.startsWith( "direct:" )) {
      routeId = routeId.substring( clIdx+1 );
    }
    RouteDefinition rd = from( uri ).routeId( String.format( "%-20s", routeId ));
    if( simpleTrace ) rd = rd.process( new SimpleTrace( routeId ));
    return rd;
  }

  public class SimpleTrace implements Processor
  {
    private String routeId;
    public SimpleTrace( String routeId )
    {
      this.routeId = routeId;
    }

    @Override
    public void process( Exchange exchange ) throws Exception
    {
      String cl="";
      Object body = exchange.getIn().getBody();
      if( body != null ) cl = body.getClass().getSimpleName();
      logger.info( "%s:%s(%s)", routeId, cl, body );
    }
  }
}
