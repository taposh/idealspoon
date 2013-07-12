package com.netshelter.ifbrands.api.model.storyamplification;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class AmplifyList 
{
  private List<StoryAmplifyDetail> storyAmplifyDetails;

  public AmplifyList( Collection<StoryAmplifyDetail> storyAmplifyDetails )
  {
    this.storyAmplifyDetails = ImmutableList.copyOf( storyAmplifyDetails );
  }
  
  public List<StoryAmplifyDetail> getStoryAmplifyDetails()
  {
    return storyAmplifyDetails;
  }

  public void setStoryAmplifyDetails(List<StoryAmplifyDetail> storyAmplifyDetails)
  {
    this.storyAmplifyDetails = storyAmplifyDetails;
  }

  @Override
  public String toString()
  {
    return String.format("AmplifyList [storyAmplifyDetails=%s]",
        storyAmplifyDetails);
  }
  
}
