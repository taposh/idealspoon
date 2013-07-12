package com.netshelter.ifbrands.api.model.entity;

import com.netshelter.ifbrands.util.MoreObjects;

public class Site
{
  private Integer id;
  private String name, domain, network;

  public Site( Integer id )
  {
    this.id = id;
  }

  public void setId( Integer id )
  {
    this.id = id;
  }

  public Integer getId()
  {
    return id;
  }

  public void setName( String name )
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setDomain( String domain )
  {
    this.domain = domain;
  }

  public String getDomain()
  {
    return domain;
  }

  public String getNetwork()
  {
    return network;
  }

  public void setNetwork( String network )
  {
    this.network = network;
  }

  @Override
  public String toString()
  {
    return "Site [id=" + id + ", name=" + name + "]";
  }

  @Override
  public boolean equals( Object obj )
  {
    if( obj instanceof Site ) {
      Site that = (Site)obj;
      if( (this.getId() == that.getId()) && MoreObjects.equivalent( this.getName(), that.getName() )
          && MoreObjects.equivalent( this.getDomain(), that.getDomain() )
          && MoreObjects.equivalent( this.getNetwork(), that.getNetwork() ) ) return true;
    }
    return false;
  }
}
