package com.netshelter.ifbrands.api.model.campaign;

import org.joda.time.DateTime;

public class CampaignType {
  private Integer id;
  private String name;
  private DateTime createTimestamp;

  public CampaignType()
  {
  }

  public CampaignType( Integer id )
  {
    this.id = id;
  }

  public CampaignType( Integer id, String name )
  {
    this.id = id;
    this.name = name;
  }

  public CampaignType( String name )
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
    return String.format("CampaignType [id=%s, name=%s, createTimestamp=%s]",
        id, name, createTimestamp);
  }
}
