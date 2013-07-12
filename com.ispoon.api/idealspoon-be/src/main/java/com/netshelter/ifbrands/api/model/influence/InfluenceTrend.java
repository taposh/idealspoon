package com.netshelter.ifbrands.api.model.influence;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDate;

public class InfluenceTrend extends InfluenceSummary implements Serializable

{
  private LocalDate startDate, stopDate;
  private List<TrendDetail> trend;

  @Override
  public InfluenceTrend setToZero()
  {
    super.setToZero();
    setTrend( Collections.<TrendDetail>emptyList() );
    return this;
  }

  public LocalDate getStartDate()
  {
    return startDate;
  }

  public void setStartDate( LocalDate startDate )
  {
    this.startDate = startDate;
  }

  public LocalDate getStopDate()
  {
    return stopDate;
  }

  public void setStopDate( LocalDate stopDate )
  {
    this.stopDate = stopDate;
  }

  public List<TrendDetail> getTrend()
  {
    return trend;
  }

  public void setTrend( List<TrendDetail> trend )
  {
    this.trend = trend;
  }

  @Override
  public String toString()
  {
    return "InfluenceTrendTotal [startDate=" + getStartDate() + ", stopDate=" + getStopDate() + "]";
  }

  public static class TrendDetail implements Serializable
  {
    private LocalDate date;
    private Influence influence;

    public LocalDate getDate()
    {
      return date;
    }

    public void setDate( LocalDate date )
    {
      this.date = date;
    }

    public Influence getInfluence()
    {
      return influence;
    }

    public void setInfluence( Influence influence )
    {
      this.influence = influence;
    }

    @Override
    public String toString()
    {
      return "TrendDetail [date=" + date + "]";
    }

  }
}
