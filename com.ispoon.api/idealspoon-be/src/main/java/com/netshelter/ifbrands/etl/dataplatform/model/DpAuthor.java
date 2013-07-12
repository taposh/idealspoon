package com.netshelter.ifbrands.etl.dataplatform.model;

public class DpAuthor
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

  @Override
  public String toString()
  {
    return "DpAuthor [id=" + id + ", name=" + name + "]";
  }

}