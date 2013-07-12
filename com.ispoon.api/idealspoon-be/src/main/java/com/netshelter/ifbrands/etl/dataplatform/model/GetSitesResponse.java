package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class GetSitesResponse
{
  private int resultCount;
  private Collection<DpSite> sites;

  public int getResultCount()
  {
    return resultCount;
  }

  public void setResultCount( int resultCount )
  {
    this.resultCount = resultCount;
  }

  public Collection<DpSite> getSites()
  {
    return sites;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setSites( Collection<DpSite> sites )
  {
    this.sites = sites;
  }

  public static class DpNetwork
  {
    private int id;
    private String name;

    public int getId()
    {
      return id;
    }

    public void setId( int id )
    {
      this.id = id;
    }

    public String getName()
    {
      return name;
    }

    public void setName( String name )
    {
      this.name = name;
    }

  }
}
