package com.netshelter.ifbrands.api.util;

import com.netshelter.ifbrands.api.model.FailCode;
import com.netshelter.ifbrands.api.model.FailSeverity;

public class FeedStoryFailure 
{
  private FailSeverity failureSeverity;
  private FailCode failureCode;
  
  public FeedStoryFailure() {};
  
  public FeedStoryFailure( FailSeverity failureSeverity, FailCode failureCode )
  {
    this.failureSeverity = failureSeverity;
    this.failureCode = failureCode;
  }
  
  public FailSeverity getFailureSeverity()
  {
    return failureSeverity;
  }
  public void setFailureSeverity( FailSeverity failureSeverity )
  {
    this.failureSeverity = failureSeverity;
  }
  public FailCode getFailureCode()
  {
    return failureCode;
  }
  public void setFailureCode( FailCode failureCode )
  {
    this.failureCode = failureCode;
  }
}
