package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class DpEngagement 
{
  private DpStory story;
  private Collection<DpEngagementMetric> metrics;
  private DpInfluence influence;
  
  public DpStory getStory()
  {
    return story;
  }

  public void setStory( DpStory story )
  {
    this.story = story;
  }

  public Collection<DpEngagementMetric> getMetrics()
  {
    return metrics;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setMetrics( Collection<DpEngagementMetric> metrics )
  {
    this.metrics = metrics;
  }
  
  public DpInfluence getInfluence()
  {
    return influence;
  }

  public void setInfluence( DpInfluence influence )
  {
    this.influence = influence;
  }

  @Override
  public String toString()
  {
    return String.format( "DpEngagement [story=%s, metrics=%s]", story, metrics );
  }

  public static class DpEngagementMetric 
  {
    private String name;
    private Integer value;
    
    public String getName()
    {
      return name;
    }
    
    public void setName( String name )
    {
      this.name = name;
    }
    
    public Integer getValue()
    {
      return value;
    }
    
    public void setValue( Integer value )
    {
      this.value = value;
    }
  
    @Override
    public String toString()
    {
      return String.format( "DpMetric [name=%s, value=%s]", name, value );
    }
  }

}
