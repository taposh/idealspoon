package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class GetStoriesResponse
{
  private int resultCount;
  private Collection<DpStory> stories;

  public int getResultCount()
  {
    return resultCount;
  }

  public void setResultCount( int resultCount )
  {
    this.resultCount = resultCount;
  }

  public Collection<DpStory> getStories()
  {
    return stories;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setStories( Collection<DpStory> stories )
  {
    this.stories = stories;
  }
}
