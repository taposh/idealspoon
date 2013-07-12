package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class GetInfluenceAndSentimentStoriesResponse
{
  private int totalResultCount, resultCount, categoryId;
  private Collection<DpStoryInfluenceAndSentiment> storyInfluenceAndSentiment;

  public int getTotalResultCount()
  {
    return totalResultCount;
  }

  public void setTotalResultCount( int totalResultCount )
  {
    this.totalResultCount = totalResultCount;
  }

  public int getResultCount()
  {
    return resultCount;
  }

  public void setResultCount( int resultCount )
  {
    this.resultCount = resultCount;
  }

  public int getCategoryId()
  {
    return categoryId;
  }

  public void setCategoryId( int categoryId )
  {
    this.categoryId = categoryId;
  }

  public Collection<DpStoryInfluenceAndSentiment> getStoryInfluenceAndSentiment()
  {
    return storyInfluenceAndSentiment;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setStoryInfluenceAndSentiment( Collection<DpStoryInfluenceAndSentiment> storyInfluenceAndSentiment )
  {
    this.storyInfluenceAndSentiment = storyInfluenceAndSentiment;
  }

}
