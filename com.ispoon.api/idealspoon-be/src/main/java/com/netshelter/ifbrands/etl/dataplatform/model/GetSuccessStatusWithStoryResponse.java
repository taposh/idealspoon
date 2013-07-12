package com.netshelter.ifbrands.etl.dataplatform.model;


public class GetSuccessStatusWithStoryResponse extends GetSuccessStatusResponse
{
  private DpStory story;

  public DpStory getStory()
  {
    return story;
  }

  public void setStory( DpStory story )
  {
    this.story = story;
  }

  
}
