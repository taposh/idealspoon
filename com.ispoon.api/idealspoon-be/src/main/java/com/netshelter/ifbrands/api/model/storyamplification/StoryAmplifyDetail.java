package com.netshelter.ifbrands.api.model.storyamplification;

import java.util.List;

public class StoryAmplifyDetail 
{
  private Integer storyId;
  private List<AmplifyDetail> amplifyDetails;
  
  public Integer getStoryId()
  {
    return storyId;
  }
  public void setStoryId(Integer storyId)
  {
    this.storyId = storyId;
  }
  public List<AmplifyDetail> getAmplifyDetails()
  {
    return amplifyDetails;
  }
  public void setAmplifyDetails(List<AmplifyDetail> amplifyDetails)
  {
    this.amplifyDetails = amplifyDetails;
  }
  
  @Override
  public String toString()
  {
    return String.format( "StoryAmplifyDetail [storyId=%s, amplifyDetails=%s]", storyId, amplifyDetails );
  }
}
