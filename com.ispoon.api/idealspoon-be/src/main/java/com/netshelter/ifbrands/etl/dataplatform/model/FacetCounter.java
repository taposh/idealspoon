package com.netshelter.ifbrands.etl.dataplatform.model;

import java.io.Serializable;

/**
 * @author Dmitriy T
 */
public class FacetCounter
    implements Serializable
{
    private String  key;
    private Long    count;
    private String  name;

    @Override
    public String toString()
    {
        return "FacetCounter{" +
                "key='" + key + '\'' +
                ", count=" + count +
                ", name='" + name + '\'' +
                '}';
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public Long getCount()
    {
        return count;
    }

    public void setCount(Long count)
    {
        this.count = count;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
