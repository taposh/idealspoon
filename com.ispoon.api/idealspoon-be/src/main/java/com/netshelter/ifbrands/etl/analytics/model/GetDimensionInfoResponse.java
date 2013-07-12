package com.netshelter.ifbrands.etl.analytics.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class GetDimensionInfoResponse
{
  private Collection<NapDimensionInfo> dimensionInfos;

  public Collection<NapDimensionInfo> getDimensionInfos()
  {
    return dimensionInfos;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setDimensionInfos( Collection<NapDimensionInfo> dimensionInfos )
  {
    this.dimensionInfos = dimensionInfos;
  }

}
