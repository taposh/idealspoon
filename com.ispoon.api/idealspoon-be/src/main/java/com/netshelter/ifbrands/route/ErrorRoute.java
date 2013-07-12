package com.netshelter.ifbrands.route;

import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.component.aws.sns.SnsConstants;
import org.apache.camel.impl.DefaultExchange;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import com.netshelter.ifbrands.route.util.ExchangeToStringProcessor;
import com.netshelter.ifbrands.route.util.TruncateStringProcessor;
import com.netshelter.stats.StatsStatic;

public abstract class ErrorRoute extends BaseRoute
{
  private static final int SNS_MAX_LENGTH = 7500; // 8k - overhead

  private static final int ERROR_MAX_REDELIVERIES = 10;
  private static final int ERROR_BACKOFF_MULTIPLIER = 5;
  private static final long ERROR_REDELIVERY_DELAY = 600;
  private static final long ERROR_MAX_REDELIVERY_DELAY = TimeUnit.HOURS.toMillis( 1 );

  public static final String R_WARNING_SNS  = "seda:warnSNS";
  public static final String R_CRITICAL_SNS = "seda:errorSNS";
  public static final String R_SHUNT_SNS    = "seda:shuntSNS";
  public static final String R_DEAD_LETTER  = "direct:deadLetter";

  private String epCriticalSNS, epWarningSNS;
  private String snsSubjectWarning, snsSubjectCritical;
  private String snsTopicWarning, snsTopicCritical;
  private String snsUriFormat;

  @Required
  public void setSnsSubjectWarning( String snsSubjectWarning )
  {
    this.snsSubjectWarning = snsSubjectWarning;
  }

  @Required
  public void setSnsSubjectCritical( String snsSubjectCritical )
  {
    this.snsSubjectCritical = snsSubjectCritical;
  }

  @Required
  public void setSnsTopicWarning( String snsTopicWarning )
  {
    this.snsTopicWarning = snsTopicWarning;
  }

  @Required
  public void setSnsTopicCritical( String snsTopicCritical )
  {
    this.snsTopicCritical = snsTopicCritical;
  }

  @Required
  public void setSnsUriFormat( String snsUriFormat )
  {
    this.snsUriFormat = snsUriFormat;
  }

  @Override
  public void configure() throws Exception
  {
    // Configure endpoint URI's if configured
    epWarningSNS  = StringUtils.isBlank( snsTopicWarning  ) ? R_SHUNT_SNS : String.format( snsUriFormat, snsTopicWarning  );
    epCriticalSNS = StringUtils.isBlank( snsTopicCritical ) ? R_SHUNT_SNS : String.format( snsUriFormat, snsTopicCritical );

    // Default ErrorHandler is: try 4 times, delay of 600ms to 1hr
    errorHandler( deadLetterChannel( R_DEAD_LETTER ).logHandled( true )
        .redeliveryDelay( ERROR_REDELIVERY_DELAY )
        .useExponentialBackOff()
        .backOffMultiplier( ERROR_BACKOFF_MULTIPLIER )
        .maximumRedeliveries( ERROR_MAX_REDELIVERIES )
        .logRetryStackTrace( true )
        .maximumRedeliveryDelay( ERROR_MAX_REDELIVERY_DELAY )
        .retryAttemptedLogLevel( LoggingLevel.INFO )
        .onRedelivery( new RedeliveryProcessor() ) );

    configureExceptions();
    configureDeadLetter();
  }

  public void configureExceptions()
  {
    // Do nothing
  }

  /**
   * DeadLetter route. Write a verbose error message to both SNS and S3.
   */
  private void configureDeadLetter()
  {
    from( R_DEAD_LETTER ).routeId( R_DEAD_LETTER )
        .log( "Giving up delivery; moving to dead-letter" )
        .beanRef( "statsdClient", "counter( 'errorFailure' )" )

//        // Handle failure cleanup (if necessary) based on body type
//        .choice()
//        .when( simple( "${body} is '" + clazz + "'" ) )
//        .end()

        // Convert our Exchange to a verbose error message
        .process( new ExchangeToStringProcessor() )

        // Send to SNS
        .to( R_CRITICAL_SNS );

    // Send to SNS (trim to 7500 characters)
    from( R_CRITICAL_SNS ).routeId( R_CRITICAL_SNS )
        .process( new TruncateStringProcessor( SNS_MAX_LENGTH, "------- SNIP -------\n" ) )
        .setHeader( SnsConstants.SUBJECT, constant( snsSubjectCritical ) )
        .to( epCriticalSNS )
    .to( LOG_ERROR )
    .stop();

    // SNS Warning route
    from( R_WARNING_SNS ).routeId( R_WARNING_SNS )
        .process( new TruncateStringProcessor( SNS_MAX_LENGTH, "------- SNIP -------\n" ) )
        .setHeader( SnsConstants.SUBJECT, constant( snsSubjectWarning ) )
        .to( epWarningSNS )
    .to( LOG_WARN )
    .stop();

    from( R_SHUNT_SNS )
    .to( LOG_WARN )
    .stop();
  }

  private class RedeliveryProcessor implements Processor
  {
    @Override
    public void process( Exchange exchange ) throws Exception
    {
      // Log an retry attempt
      StatsStatic.counter( "errorRetry" );
      // Check redeliver count
      int redeliveryCount =
          exchange.getIn().getHeader( Exchange.REDELIVERY_COUNTER, Number.class ).intValue();
      logger.info( "Retry count: %d  Destination: %s", redeliveryCount, exchange.getFromEndpoint()
          .getEndpointUri() );
      if( redeliveryCount > ERROR_MAX_REDELIVERIES ) {
        // Send an SNS when doing infinite retries but have tried more than our normal retry count
        // First create a new Exchange w/ exception to a useful string
        Exchange warnExchange = new DefaultExchange( exchange );
        new ExchangeToStringProcessor().process( exchange );
        // Prepend a note that this is just a warning
        String msg = String.format( "NOTE:  Transient error has exceeded nominal retry threshold.\n\n" );
        producerTemplate.sendBody( R_WARNING_SNS, msg + warnExchange.getIn().getBody( String.class ) );
        // Original exchange will be delivered as per normal
      }
    }
  }
}
