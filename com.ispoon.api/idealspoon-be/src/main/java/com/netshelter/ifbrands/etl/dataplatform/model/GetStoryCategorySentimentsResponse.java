package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class GetStoryCategorySentimentsResponse
{
  private Collection<DpStoryCategorySentiment> storyCategorySentiments;

  public Collection<DpStoryCategorySentiment> getStoryCategorySentiments()
  {
    return storyCategorySentiments;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setStoryBrandSentiments( Collection<DpStoryCategorySentiment> storyCategorySentiments )
  {
    this.storyCategorySentiments = storyCategorySentiments;
  }
}
