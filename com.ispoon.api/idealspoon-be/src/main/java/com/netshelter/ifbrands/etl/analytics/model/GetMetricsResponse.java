package com.netshelter.ifbrands.etl.analytics.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class GetMetricsResponse
{
  private Collection<NapNode> metrics;

  public Collection<NapNode> getMetrics()
  {
    return metrics;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setMetrics( Collection<NapNode> metrics )
  {
    this.metrics = metrics;
  }

}
