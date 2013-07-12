package com.netshelter.ifbrands.api.model.influence;

import java.util.Collection;
import java.util.LinkedHashMap;

public class InfluenceComparison<T extends InfluenceSummary> extends LinkedHashMap<String,T>
{
  public InfluenceComparison<T> setToZero( Collection<?> keys, T zero )
  {
    for( Object o: keys ) {
      if( o != null ) {
        put( o.toString(), zero );
      }
    }
    return this;
  }
}
