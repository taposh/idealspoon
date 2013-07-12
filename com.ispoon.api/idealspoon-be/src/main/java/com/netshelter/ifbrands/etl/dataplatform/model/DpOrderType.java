package com.netshelter.ifbrands.etl.dataplatform.model;

public enum DpOrderType
{
    PUBLISHTIME,
    INFLUENCESCORE,
    SEARCHSIMILARITY,
    INFLUENCERS,
    POINTS,
    EVENTS;

  public static DpOrderType valueFrom( String text )
  {
    return valueOf( text.toUpperCase() );
  }
}
