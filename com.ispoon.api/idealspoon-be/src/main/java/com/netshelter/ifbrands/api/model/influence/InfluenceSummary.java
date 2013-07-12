package com.netshelter.ifbrands.api.model.influence;

public class InfluenceSummary
{
  private Influence totalInfluence;

  public InfluenceSummary setToZero()
  {
    setTotalInfluence( new Influence() );
    return this;
  }

  public Influence getTotalInfluence()
  {
    return totalInfluence;
  }

  public void setTotalInfluence( Influence totalInfluence )
  {
    this.totalInfluence = totalInfluence;
  }

}
