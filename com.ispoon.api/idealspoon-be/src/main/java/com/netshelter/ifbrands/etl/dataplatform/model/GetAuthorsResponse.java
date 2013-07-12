package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class GetAuthorsResponse
{
  private int resultCount;
  private Collection<DpAuthor> authors;

  public int getResultCount()
  {
    return resultCount;
  }

  public void setResultCount( int resultCount )
  {
    this.resultCount = resultCount;
  }

  public Collection<DpAuthor> getAuthors()
  {
    return authors;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setAuthors( Collection<DpAuthor> authors )
  {
    this.authors = authors;
  }
}
