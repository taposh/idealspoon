package com.netshelter.ifbrands.etl.dataplatform.model;


import com.netshelter.ifbrands.etl.dataplatform.model.GetSitesResponse.DpNetwork;

public class DpSite
{
  private int id;
  private String name, domain;
  private DpNetwork network;

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

  public String getDomain()
  {
    return domain;
  }

  public void setDomain( String domain )
  {
    this.domain = domain;
  }

  public DpNetwork getNetwork()
  {
    return network;
  }

  public void setNetwork( DpNetwork network )
  {
    this.network = network;
  }

  @Override
  public String toString()
  {
    return "DpSite [id=" + id + ", name=" + name + ", domain=" + domain + ", network=" + network + "]";
  }

}