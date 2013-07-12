package com.netshelter.ifbrands.api.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;

/**
 * This class offers authentication funcationality for HTTP POST requests. The general MAC form is md5( SECRET
 * + SALT + MSG ). We also disallow requests more than X seconds old.
 *
 * @author bgray
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter
{
  private Logger logger = LoggerFactory.getLogger();
  private boolean enabled;
  private String sharedSecret;
  private int ageLimit;

  public void setEnabled( boolean enabled )
  {
    this.enabled = enabled;
  }

  public void setSecret( String sharedSecret )
  {
    this.sharedSecret = sharedSecret;
  }

  public void setAgeLimit( int ageLimit )
  {
    this.ageLimit = ageLimit;
  }

  /**
   * Handle the request before it enters the Controller mapping logic. We check for MAC here and return HTTP
   * error if it is invalid or expired.
   */
  @Override
  public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler )
      throws Exception
  {

    if( !enabled ) return true;

    // Get our header auth string
    String auth = request.getHeader( "X-NS-Auth" );
    if( auth == null ) {
      return refuse( request, response, "No X-NS-Auth header" );
    }

    // Format is "ts:msg:mac
    String[] split = auth.split( ":" );
    if( split.length != 3 ) {
      return refuse( request, response, "Malformed X-NS-Auth header" + auth );
    }

    // Ensure timeliness of request
    long now = System.currentTimeMillis() / 1000;
    long rts = Long.parseLong( split[0] );
    if( now - rts > ageLimit ) {
      return refuse( request, response, "Stale timestamp" );
    }

    // Check mac
    String check = mac( sharedSecret, split[0], split[1] );
    if( !check.equals( split[2] ) ) {
      return refuse( request, response, "Bad MAC" );
    }
    return true;
  }

  /**
   * Calculate a MAC string. The formula is MD5( secret + salt + msg ).
   *
   * @param secret The secret
   * @param salt The salt
   * @param msg The message
   * @return The MAC
   */
  public String mac( String secret, String salt, String msg )
  {
    String input = new StringBuffer().append( secret ).append( salt ).append( msg ).toString();
    return DigestUtils.md5DigestAsHex( input.getBytes() );
  }

  /**
   * Set refusal information.
   *
   * @param request The request object
   * @param response The response object
   * @param reason Reason for refusal
   * @return false if request should be rejected
   * @throws IOException
   */
  public boolean refuse( HttpServletRequest request, HttpServletResponse response, String reason )
      throws IOException
  {
    response.sendError( HttpServletResponse.SC_FORBIDDEN, "Access Denied" );
    logger.debug( "Connection refused: %s : %s", reason, request.getRemoteAddr() );
    return false;
  }
}
