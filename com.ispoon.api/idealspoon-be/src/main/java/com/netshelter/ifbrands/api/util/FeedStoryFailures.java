package com.netshelter.ifbrands.api.util;

import java.util.Map;

public class FeedStoryFailures 
{
  public Map<Integer,FeedStoryFailure> feedStoryFailures;

  public FeedStoryFailures() {}
  
  public FeedStoryFailures( Map<Integer,FeedStoryFailure> feedStoryFailures )
  {
        this.feedStoryFailures = feedStoryFailures;
  }

  public Map<Integer,FeedStoryFailure> getFeedStoryFailures()
  {
    return feedStoryFailures;
  }

  public void setFeedStoryFailures( Map<Integer,FeedStoryFailure> feedStoryFailures )
  {
    this.feedStoryFailures = feedStoryFailures;
  }
}
