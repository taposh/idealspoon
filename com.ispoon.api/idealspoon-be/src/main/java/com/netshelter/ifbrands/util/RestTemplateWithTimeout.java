package com.netshelter.ifbrands.util;

import java.util.concurrent.TimeUnit;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateWithTimeout extends RestTemplate
{
  private int timeout;

  public RestTemplateWithTimeout( int timeoutSeconds )
  {
    this( timeoutSeconds, new SimpleClientHttpRequestFactory() );
  }

  public RestTemplateWithTimeout( int timeoutSeconds, ClientHttpRequestFactory factory )
  {
    // Use a custom ClientHttpRequestFactory so we can set a low timeout
    super( factory );
    setTimeout( (int)TimeUnit.SECONDS.toMillis( timeoutSeconds ));
  }

  public void setTimeout( int timeout )
  {
    this.timeout = timeout;
    ClientHttpRequestFactory factory = getRequestFactory();
    if( factory instanceof HttpComponentsClientHttpRequestFactory ) {
      HttpComponentsClientHttpRequestFactory rf = (HttpComponentsClientHttpRequestFactory)getRequestFactory();
      rf.setConnectTimeout( timeout );
      rf.setReadTimeout( timeout );
    } else if( factory instanceof SimpleClientHttpRequestFactory ) {
      SimpleClientHttpRequestFactory rf = (SimpleClientHttpRequestFactory)getRequestFactory();
      rf.setConnectTimeout( timeout );
      rf.setReadTimeout( timeout );
    } else {
      throw new IllegalArgumentException( "Unsupported ClientHttpRequestFactory type: "+ factory.getClass().getName() );
    }
  }

  public int getTimeout()
  {
    return timeout;
  }
}
