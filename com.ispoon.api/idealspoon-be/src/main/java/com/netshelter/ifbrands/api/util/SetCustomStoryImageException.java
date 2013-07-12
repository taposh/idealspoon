package com.netshelter.ifbrands.api.util;

import java.util.List;
import java.util.Map;

public class SetCustomStoryImageException extends GeneralException
{
  private Map<Integer, List<FeedStoryFailure>> feedStoryFailures;
  
  public SetCustomStoryImageException( String format, Throwable cause, Object... values )
  {
    super( format, cause, values );
  }

  public SetCustomStoryImageException( String format, Object... values )
  {
    super( format, values );
  }
  
  public SetCustomStoryImageException( Map<Integer, List<FeedStoryFailure>> feedStoryFailures )
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
}