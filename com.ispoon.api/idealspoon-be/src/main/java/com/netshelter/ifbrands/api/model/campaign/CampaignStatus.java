package com.netshelter.ifbrands.api.model.campaign;

import org.joda.time.DateTime;

public class CampaignStatus {
  private Integer id;
  private String name;
  private DateTime createTimestamp;

  public CampaignStatus()
  {
  }

  public CampaignStatus( Integer id )
  {
    this.id = id;
  }

  public CampaignStatus( Integer id, String name )
  {
    this.id = id;
    this.name = name;
  }

  public CampaignStatus( String name )
  {
    this.name = name;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId( Integer id )
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

  public DateTime getCreateTimestamp()
  {
    return createTimestamp;
  }

  public void setCreateTimestamp( DateTime createTimestamp )
  {
    this.createTimestamp = createTimestamp;
  }

  @Override
  public String toString()
  {
    return String.format("CampaignStatus [id=%s, name=%s, createTimestamp=%s]",
        id, name, createTimestamp);
  }

}
