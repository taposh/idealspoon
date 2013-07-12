package com.netshelter.ifbrands.api.model.campaign;

import org.joda.time.DateTime;

/**
 * @author Dmitriy T
 */
public class AdState
{
    private Integer id;
    private String name;
    private DateTime createTimestamp;

    public AdState(Integer id)
    {
        this.id = id;
    }

    public AdState(Integer id, String name, DateTime createTimestamp)
    {
        this.id = id;
        this.name = name;
        this.createTimestamp = createTimestamp;
    }

    @Override
    public String toString()
    {
        return "AdState{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTimestamp=" + createTimestamp +
                '}';
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public DateTime getCreateTimestamp()
    {
        return createTimestamp;
    }

    public void setCreateTimestamp(DateTime createTimestamp)
    {
        this.createTimestamp = createTimestamp;
    }
}
