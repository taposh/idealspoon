package com.netshelter.ifbrands.etl.analytics.model;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.joda.time.LocalDate;

public class NapNode
{
    private String name;
    private NapMetrics metricsTotalsRollup;
    private NapMetrics metricsTotals;
    private LocalDate startDate, stopDate;
    private Collection<NapNode> children;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public NapMetrics getMetricsTotalsRollup()
    {
        return metricsTotalsRollup;
    }

    @JsonProperty("totalsRollup")
    public void setMetricsTotalsRollup(NapMetrics metricsTotalsRollup)
    {
        this.metricsTotalsRollup = metricsTotalsRollup;
    }

    public NapMetrics getMetricsTotals()
    {
        return metricsTotals;
    }

    @JsonProperty("totals")
    public void setMetricsTotals(NapMetrics metricsTotals)
    {
        this.metricsTotals = metricsTotals;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getStopDate()
    {
        return stopDate;
    }

    public void setStopDate(LocalDate stopDate)
    {
        this.stopDate = stopDate;
    }

    public Collection<NapNode> getChildren()
    {
        return children;
    }

    @JsonDeserialize(as = ArrayList.class)
    public void setChildren(Collection<NapNode> children)
    {
        this.children = children;
    }

}
