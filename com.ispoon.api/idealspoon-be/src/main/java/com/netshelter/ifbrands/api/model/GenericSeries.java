package com.netshelter.ifbrands.api.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class GenericSeries<T>
{
  private T total;
  private Map<String,T> seriesMap = new LinkedHashMap<String,T>();

  public void setTotal( T total )
  {
    this.total = total;
  }

  public T getTotal()
  {
    return total;
  }

  public void addSeries( String key, T data )
  {
    seriesMap.put( key, data );
  }

  public Map<String,T> getSeries()
  {
    return seriesMap;
  }
}
