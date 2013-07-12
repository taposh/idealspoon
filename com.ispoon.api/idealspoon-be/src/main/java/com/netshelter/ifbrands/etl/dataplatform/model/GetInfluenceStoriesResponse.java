package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class GetInfluenceStoriesResponse
{
  private int resultCount, totalResultCount, categoryId;
  private Collection<DpStoryInfluence> storyInfluence;

  public int getCategoryId()
  {
    return categoryId;
  }

  public void setCategoryId( int categoryId )
  {
    this.categoryId = categoryId;
  }

  public int getResultCount()
  {
    return resultCount;
  }

  public void setResultCount( int resultCount )
  {
    this.resultCount = resultCount;
  }

  public int getTotalResultCount()
  {
    return totalResultCount;
  }

  public void setTotalResultCount( int totalResultCount )
  {
    this.totalResultCount = totalResultCount;
  }

  public Collection<DpStoryInfluence> getStoryInfluence()
  {
    return storyInfluence;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setStoryInfluence( Collection<DpStoryInfluence> storyInfluence )
  {
    this.storyInfluence = storyInfluence;
  }
}
