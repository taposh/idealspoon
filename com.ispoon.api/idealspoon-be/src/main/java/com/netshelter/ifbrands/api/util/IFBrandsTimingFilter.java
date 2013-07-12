package com.netshelter.ifbrands.api.util;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.util.NestedServletException;

import com.netshelter.stats.spring.TimingFilter;

/**
 * Implementation of the TimingFilter pattern.
 * @author bgray
 *
 */
public class IFBrandsTimingFilter extends TimingFilter
{
  private static final Pattern ALPHA    = Pattern.compile( "[\\p{Alpha}]+" );
  private static final Pattern ALPHA_ID = Pattern.compile( "[\\p{Alpha}]+:[\\p{Digit}]+" );

  /**
   * Provide the salient part of a given path for Stats logging.
   */
  @Override
  protected String getPathInfo( String httpRequestPathInfo )
  {
    if( httpRequestPathInfo.startsWith( "/status" )) return null;
    String[] split = httpRequestPathInfo.split( "/" );

    StringBuilder sb = new StringBuilder();
    for( String s : split  ) {
      String t=null;
      if( s.length() == 0 ) {
        continue;
      } else if( ALPHA.matcher( s ).matches() ) {
        t=s;
      } else if( ALPHA_ID.matcher( s ).matches() ) {
        int idx = s.indexOf( ':' );
        t = s.substring( 0, idx );
      }
      if( t == null ) break;
      sb.append( '/' ).append( t );
    }
    return sb.toString();
  }

  @Override
  public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
  throws IOException, ServletException
  {
    try {
      super.doFilter( request, response, chain );
    } catch( NestedServletException nse ) {
      response.getWriter().print( "ERROR!" );
    } catch( Error e ) {
      throw new NestedServletException( "Error!", e );
    }
  }
}
