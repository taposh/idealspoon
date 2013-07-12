package com.netshelter.ifbrands.api.util;

import java.util.List;
import java.util.Map;

public class GenerateThumbnailsException extends GeneralException
{
  private Map<Integer, List<FeedStoryFailure>> feedStoryFailures;
  private Object returnObject;
  
  public GenerateThumbnailsException( String format, Throwable cause, Object... values )
  {
    super( format, cause, values );
  }

  public GenerateThumbnailsException( String format, Object... values )
  {
    super( format, values );
  }
  
  public GenerateThumbnailsException( Map<Integer, List<FeedStoryFailure>> feedStoryFailures )
  {
    super( "Aggregate exception, check failure array", "" );
    this.feedStoryFailures = feedStoryFailures;
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

  public void setReturnObject( Object returnObject )
  {
    this.returnObject = returnObject;
  }
  
}
