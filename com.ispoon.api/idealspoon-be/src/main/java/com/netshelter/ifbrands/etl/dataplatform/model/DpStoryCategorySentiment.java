package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class DpStoryCategorySentiment
{
  private int storyId;
  Collection<DpCategorySentiment> categorySentiments;

  public int getStoryId()
  {
    return storyId;
  }

  public void setStoryId( int storyId )
  {
    this.storyId = storyId;
  }

  public Collection<DpCategorySentiment> getCategorySentiments()
  {
    return categorySentiments;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setCategorySentiments( Collection<DpCategorySentiment> categorySentiments )
  {
    this.categorySentiments = categorySentiments;
  }

  @Override
  public String toString()
  {
    return "DpStoryCategorySentiment [storyId=" + storyId + "]";
  }
}