package com.netshelter.ifbrands.etl.dataplatform.model;


public class DpCategory
{
  private int id;
  private String name;
  private DpCategoryType type;

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
  public DpCategoryType getType()
  {
    return type;
  }

  public void setType( DpCategoryType type )
  {
    this.type = type;
  }

  @Override
  public String toString()
  {
    return "DpCategory [id=" + id + ", name=" + name + ", type=" + type + "]";
  }


}