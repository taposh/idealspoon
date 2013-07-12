package com.netshelter.ifbrands.etl.analytics.model;

public class NapDimensionInfo
{
  int id;
  String name, display, dimension;

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

  public String getDisplay()
  {
    return display;
  }

  public void setDisplay( String display )
  {
    this.display = display;
  }

  public String getDimension()
  {
    return dimension;
  }

  public void setDimension( String dimension )
  {
    this.dimension = dimension;
  }
}
