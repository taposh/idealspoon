package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DpInfluenceRollup
{
  private DateTime startTime, endTime;
  private DateTimeZone timezone;
  private DpRollupInterval dpRollupInterval;
  private DpInfluence totalInfluence;
  private Map<String, DpInfluence> rollup;

  public DateTime getStartTime()
  {
    return startTime;
  }

  public void setStartTime( DateTime startTime )
  {
    this.startTime = startTime;
  }

  public DateTime getEndTime()
  {
    return endTime;
  }

  public void setEndTime( DateTime endTime )
  {
    this.endTime = endTime;
  }

  public DateTimeZone getTimezone()
  {
    return timezone;
  }

  public void setTimezone( DateTimeZone timezone )
  {
    this.timezone = timezone;
  }

  public DpRollupInterval getRollupInterval()
  {
    return dpRollupInterval;
  }

  public void setRollupInterval( DpRollupInterval dpRollupInterval )
  {
    this.dpRollupInterval = dpRollupInterval;
  }

  public DpInfluence getTotalInfluence()
  {
    return totalInfluence;
  }

  public void setTotalInfluence( DpInfluence totalInfluence )
  {
    this.totalInfluence = totalInfluence;
  }

  public Map<String, DpInfluence> getRollup()
  {
    return rollup;
  }

  public void setRollup( Map<String, DpInfluence> rollup )
  {
    this.rollup = rollup;
  }

  @Override
  public String toString()
  {
    return "DpInfluenceRollup [startTime=" + startTime + ", endTime=" + endTime + ", dpRollupInterval="
        + dpRollupInterval + "]";
  }

}
