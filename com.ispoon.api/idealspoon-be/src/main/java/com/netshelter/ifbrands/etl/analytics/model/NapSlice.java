package com.netshelter.ifbrands.etl.analytics.model;

public class NapSlice
{
  private Integer id;
  private NapDimension dimension;

  public NapSlice( NapDimension dimension )
  {
    this.dimension = dimension;
  }

  public NapSlice( NapDimension dimension, int id )
  {
    this.id = new Integer( id );
    this.dimension = dimension;
  }

  public NapDimension getDimension()
  {
    return dimension;
  }

  public Integer getId()
  {
    return id;
  }

  public String getHyperFilter()
  {
    if( id == null ) {
      return dimension.toString();
    }
    return String.format( "%s:%d", dimension.toString(), id );
  }

  @Override
  public String toString()
  {
    return "NapSlice [dimension=" + dimension + ", id=" + id + "]";
  }

}
