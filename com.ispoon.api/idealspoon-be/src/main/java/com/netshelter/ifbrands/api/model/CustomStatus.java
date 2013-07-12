package com.netshelter.ifbrands.api.model;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import com.netshelter.ifbrands.api.util.FeedStoryFailure;

/**
 * Container for generic status, including detailed message and trace info.
 *
 * @author ekrevets
 */
public class CustomStatus extends GenericStatus
{
  /** Simple OKAY status. */
  public static final CustomStatus OKAY = new CustomStatus( true );
  /** Simple FAIL status. */
  public static final CustomStatus FAIL = new CustomStatus( false );

  /** Constructor for a given success state. */
  private CustomStatus( boolean success )
  {
    this.status = ((success) ? "OKAY" : "FAIL");
  }
  
  private Map<Integer, List<FeedStoryFailure>> feedStoryFailures;
  private Object returnObject;
	
  public static CustomStatus fail( Exception ex, Integer statusCode, Map<Integer, List<FeedStoryFailure>> feedStoryFailures, Object returnObject )
  {
	  CustomStatus status = FAIL;
    StringWriter sw = new StringWriter();
    ex.printStackTrace( new PrintWriter( sw ) );
    status.setTrace( sw.toString() );
    status.setStatusCode( statusCode );
    status.setFeedStoryFailures( feedStoryFailures );
    status.setReturnObject( returnObject );
    return status;
  }

  public Map<Integer, List<FeedStoryFailure>> getFeedStoryFailures()
  {
    return feedStoryFailures;
  }

  public void setFeedStoryFailures( Map<Integer, List<FeedStoryFailure>> feedStoryFailures )
  {
    this.feedStoryFailures = feedStoryFailures;
  }

  public Object getReturnObject()
  {
    return returnObject;
  }

  public void setReturnObject(Object returnObject)
  {
    this.returnObject = returnObject;
  }

}
