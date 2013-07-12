package com.netshelter.ifbrands.api.model;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Container for generic status, including detailed message and trace info.
 *
 * @author bgray
 */
public class GenericStatus
{
  /** Simple OKAY status. */
  public static final GenericStatus OKAY = new GenericStatus( true );
  /** Simple FAIL status. */
  public static final GenericStatus FAIL = new GenericStatus( false );

  /**
   * Get a simple status based on a success variable.
   *
   * @param success indicates success
   * @return if success is TRUE returns OKAY else returns FAIL
   */
  public static GenericStatus successFail( boolean success )
  {
    return success ? OKAY : FAIL;
  }

  /** Return an OKAY status with the given message. */
  public static GenericStatus okay( String message )
  {
    return new GenericStatus( true, message );
  }

  /** Return a FAIL status with the given message. */
  public static GenericStatus fail( String message )
  {
    return new GenericStatus( false, message );
  }

  /** Return a FAIL status, pulling message and trace from the given Exception. */
  public static GenericStatus fail( Exception ex )
  {
    return fail( ex, null );
  }

  public static GenericStatus fail( Exception ex, Integer statusCode )
  {
    GenericStatus status = fail( ex.getMessage() );
    StringWriter sw = new StringWriter();
    ex.printStackTrace( new PrintWriter( sw ) );
    status.setTrace( sw.toString() );
    status.setStatusCode( statusCode );
    return status;
  }

  protected String status, message, trace;
  private Integer statusCode;

  protected GenericStatus()
  {
	  
  }
  
  /** Constructor for a given success state. */
  private GenericStatus( boolean success )
  {
    this.status = ((success) ? "OKAY" : "FAIL");
  }

  /** Constructor for a given success state and message. */
  private GenericStatus( boolean success, String message )
  {
    this( success );
    this.message = message;
  }

  public String getStatus()
  {
    return status;
  }

  public void setStatus( String status )
  {
    this.status = status;
  }

  public Integer getStatusCode()
  {
    return statusCode;
  }

  public void setStatusCode( Integer statusCode )
  {
    this.statusCode = statusCode;
  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage( String message )
  {
    this.message = message;
  }

  public String getTrace()
  {
    return trace;
  }

  public void setTrace( String trace )
  {
    this.trace = trace;
  }
}
