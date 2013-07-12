package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.Collection;

import org.joda.time.DateTime;

public class GetStoryEngagementsResponse 
{
  private DateTime startTime;
  private DateTime endTime;
  private Collection<DpEngagement> engagements;

  public DateTime getStartTime()
  {
    return startTime;
  }

  public void setStartTime( DateTime startTime )
  {
    this.startTime = startTime;
  }

  public DateTime getEndTime()
  {
    return endTime;
  }

  public void setEndTime( DateTime endTime )
  {
    this.endTime = endTime;
  }

  public Collection<DpEngagement> getEngagements()
  {
    return engagements;
  }

  public void setEngagements( Collection<DpEngagement> engagements )
  {
    this.engagements = engagements;
  }

  @Override
  public String toString()
  {
    return String.format( "GetStoryEngagementsResponse [engagements=%s]", engagements );
  }
}
