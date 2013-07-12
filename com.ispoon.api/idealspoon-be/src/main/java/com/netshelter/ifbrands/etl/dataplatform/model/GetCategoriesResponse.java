package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class GetCategoriesResponse
{
  private int resultCount;
  private Collection<DpCategory> categories;

  public int getResultCount()
  {
    return resultCount;
  }

  public void setResultCount( int resultCount )
  {
    this.resultCount = resultCount;
  }

  public Collection<DpCategory> getCategories()
  {
    return categories;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setBrands( Collection<DpCategory> categories )
  {
    this.categories = categories;
  }
}
