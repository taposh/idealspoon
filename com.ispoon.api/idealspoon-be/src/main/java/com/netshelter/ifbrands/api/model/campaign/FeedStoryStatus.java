package com.netshelter.ifbrands.api.model.campaign;


public class FeedStoryStatus
{
  private Integer id;
  private String name;

  public FeedStoryStatus( Integer id )
  {
    this.id = id;
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

  @Override
  public String toString()
  {
    return "FeedStoryStatus [id=" + id + ", name=" + name + "]";
  }
}
