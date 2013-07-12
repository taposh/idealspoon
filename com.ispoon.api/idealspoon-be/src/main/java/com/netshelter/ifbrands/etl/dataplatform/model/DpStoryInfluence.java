package com.netshelter.ifbrands.etl.dataplatform.model;


public class DpStoryInfluence
{
  private DpSynopsis synopsis;
  private DpInfluence scoredInfluence;

  public DpSynopsis getSynopsis()
  {
    return synopsis;
  }

  public void setSynopsis( DpSynopsis synopsis )
  {
    this.synopsis = synopsis;
  }

  public DpInfluence getScoredInfluence()
  {
    return scoredInfluence;
  }

  public void setScoredInfluence( DpInfluence scoredInfluence )
  {
    this.scoredInfluence = scoredInfluence;
  }

  @Override
  public String toString()
  {
    return "DpStoryInfluence [synopsis.storyId=" + synopsis.getStoryId() + "]";
  }

}