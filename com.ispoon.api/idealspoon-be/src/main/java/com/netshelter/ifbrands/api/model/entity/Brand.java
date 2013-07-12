package com.netshelter.ifbrands.api.model.entity;

import com.netshelter.ifbrands.util.MoreObjects;

public class Brand
{
  private Integer id;
  private String name;

  public Brand( Integer id )
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

  @Override
  public String toString()
  {
    return "Brand [id=" + id + ", name=" + name + "]";
  }

  @Override
  public boolean equals( Object obj )
  {
    if( obj instanceof Brand ) {
      Brand that = (Brand)obj;
      if( (this.getId() == that.getId()) && MoreObjects.equivalent( this.getName(), that.getName() ) )
        return true;
    }
    return false;
  }
}
