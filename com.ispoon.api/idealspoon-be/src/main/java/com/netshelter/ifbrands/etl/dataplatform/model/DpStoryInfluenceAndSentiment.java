package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class DpStoryInfluenceAndSentiment extends DpStoryInfluence
{
  private Collection<DpCategorySentiment> categorySentiments;

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
    return "DpStoryInfluenceAndSentiment [synopsis.storyId=" + getSynopsis().getStoryId() + "]";
  }

}
