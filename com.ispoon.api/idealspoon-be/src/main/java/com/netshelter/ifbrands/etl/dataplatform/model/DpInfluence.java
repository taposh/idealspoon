package com.netshelter.ifbrands.etl.dataplatform.model;


public class DpInfluence
{
  private long events, points, score, influencers, uniqueStories, impressions;

  public long getEvents()
  {
    return events;
  }

  public void setEvents( long events )
  {
    this.events = events;
  }

  public long getPoints()
  {
    return points;
  }

  public void setPoints( long points )
  {
    this.points = points;
  }

  public long getScore()
  {
    return score;
  }

  public void setScore( long score )
  {
    this.score = score;
  }

  public long getUniqueStories()
  {
    return uniqueStories;
  }

  public void setUniqueStories( long uniqueStories )
  {
    this.uniqueStories = uniqueStories;
  }

  public long getInfluencers()
  {
    return influencers;
  }

  public void setInfluencers( long influencers )
  {
    this.influencers = influencers;
  }

  public long getImpressions()
  {
    return impressions;
  }

  public void setImpressions( long impressions )
  {
    this.impressions = impressions;
  }

  @Override
  public String toString()
  {
    return "DpInfluence [events=" + events + ", points=" + points + ", score=" + score + ", influencers="
        + influencers + ", uniqueStories=" + uniqueStories + ", impressions=" + impressions + "]";
  }
}
