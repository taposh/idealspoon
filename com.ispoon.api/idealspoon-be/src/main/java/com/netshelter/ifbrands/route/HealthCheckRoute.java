package com.netshelter.ifbrands.route;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.netshelter.ifbrands.api.model.StatusInfo;
import com.netshelter.ifbrands.api.model.StatusInfo.ServiceInfo;
import com.netshelter.ifbrands.api.service.StatusService;

public class HealthCheckRoute extends ErrorRoute
{
  public static final String STATUS_INFO = "NS-HealthCheckRoute-StatusInfo";

  private ObjectWriter jsonWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

  @Autowired
  private StatusService statusService;
  private String pollingPeriod;

  @Required
  public void setPollingPeriod( String pollingPeriod )
  {
    this.pollingPeriod = pollingPeriod;
  }

  @Override
  public void configure() throws Exception
  {
    // Configure ErrorRoute
    super.configure();

    configureHealthCheck();
  }

  public void configureHealthCheck() throws Exception
  {
    // Wait 10s then check every 'pollingPeriod'
    from( String.format( "timer://healthCheck?delay=10s&period=%s", pollingPeriod ))

    // Get StatusInfo directly from service
    .bean( statusService, "getStatusInfo" )
    .log( "Initiating internal health-check (${body.serverOkay})" )
    // Split into services
    .setHeader( STATUS_INFO, body() )
    // Check for failed ones
    .split( simple( "${body.services}" ))
    .log( "...Service: ${body.name} (${body.uri}) [${body.okay}]" )
    .filter( simple( "${body.okay} == false"))
    // Form e-mail text
    .process( new Processor() {
      @Override
      public void process( Exchange exchange ) throws Exception
      {
        StatusInfo info = exchange.getIn().getHeader( STATUS_INFO, StatusInfo.class );
        ServiceInfo service = exchange.getIn().getBody( ServiceInfo.class );

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter( sw );
        pw.println( "Detected downstream dependency outtage for IFBBE." );
        pw.format( "DateTime: %s\n", new DateTime().toString() );
        pw.println();
        pw.println( "Troubled service:" );
        pw.println( service );
        pw.println();
        pw.println( "Full StatusInfo object:" );
        pw.print( jsonWriter.writeValueAsString( info ));

        exchange.getIn().setBody( sw.toString() );
      }
    } )
    // Send to WARNING
    .to( R_WARNING_SNS )
    ;
  }
}
