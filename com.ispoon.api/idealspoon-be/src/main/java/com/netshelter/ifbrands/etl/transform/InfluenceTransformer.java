package com.netshelter.ifbrands.etl.transform;

import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.influence.Influence;
import com.netshelter.ifbrands.etl.dataplatform.model.DpInfluence;

@Component
public class InfluenceTransformer
{
  public Influence transform( DpInfluence dpi )
  {
    Influence influence = new Influence();
    influence.setEvents( dpi.getEvents() );
    influence.setScore( dpi.getScore() );
    influence.setInfluencers( dpi.getInfluencers() );
    influence.setUniqueCount( dpi.getUniqueStories() );
    influence.setPeopleInfluenced( dpi.getPoints() );
    influence.setImpressions( dpi.getImpressions() );
    return influence;
  }

}
