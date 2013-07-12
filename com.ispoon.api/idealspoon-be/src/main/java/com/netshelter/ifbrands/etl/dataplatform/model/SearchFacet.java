package com.netshelter.ifbrands.etl.dataplatform.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author Dmitriy T
 */
public class SearchFacet
    implements Serializable
{
    private String name;
    private List<FacetCounter> countList = new ArrayList<FacetCounter>();

    @Override
    public String toString()
    {
        return "SearchFacet{" +
                "name='" + name + '\'' +
                ", countList=" + countList +
                '}';
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<FacetCounter> getCountList()
    {
        return countList;
    }

    public void setCountList(List<FacetCounter> countList)
    {
        this.countList = countList;
    }
}
