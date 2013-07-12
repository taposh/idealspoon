package com.netshelter.ifbrands.etl.dataplatform.model;

import com.netshelter.ifbrands.api.model.entity.Story;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Dmitriy T
 */
public class GetInfluenceAndSentimentStoriesFacetedResponse
{
    private int totalResults;
    private int limit;
    private int offset;
    private List<DpIndexedStory> stories;
    private List<SearchFacet> facets;

    @Override
    public String toString()
    {
        return "GetInfluenceAndSentimentStoriesFacetedResponse{" +
                "totalResults=" + totalResults +
                ", limit=" + limit +
                ", offset=" + offset +
                ", stories=" + stories +
                ", facets=" + facets +
                '}';
    }

    public int getTotalResults()
    {
        return totalResults;
    }

    public void setTotalResults(int totalResults)
    {
        this.totalResults = totalResults;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public List<DpIndexedStory> getStories()
    {
        return stories;
    }

    public void setStories(List<DpIndexedStory> stories)
    {
        this.stories = stories;
    }

    public List<SearchFacet> getFacets()
    {
        return facets;
    }

    public void setFacets(List<SearchFacet> facets)
    {
        this.facets = facets;
    }

}
