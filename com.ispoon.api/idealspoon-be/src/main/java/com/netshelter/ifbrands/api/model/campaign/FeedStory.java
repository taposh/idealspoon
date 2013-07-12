package com.netshelter.ifbrands.api.model.campaign;

import org.joda.time.DateTime;

import java.util.Date;

public class FeedStory
{
    private Integer id, storyId;
    private int ordinal;
    private DateTime createDateTime;
    private FeedStoryStatus status;
    private String updateSource;
    private Boolean ignoreBot;
    private DateTime updateTimestamp;
    private int dpStoryId;

    public FeedStory(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getStoryId()
    {
        return storyId;
    }

    public void setStoryId(Integer storyId)
    {
        this.storyId = storyId;
    }

    public FeedStoryStatus getStatus()
    {
        return status;
    }

    public void setStatus(FeedStoryStatus status)
    {
        this.status = status;
    }

    public int getOrdinal()
    {
        return ordinal;
    }

    public void setOrdinal(int ordinal)
    {
        this.ordinal = ordinal;
    }

    public DateTime getCreateDateTime()
    {
        return createDateTime;
    }

    public void setCreateDateTime(DateTime createDateTime)
    {
        this.createDateTime = createDateTime;
    }

    public String getUpdateSource()
    {
        return updateSource;
    }

    public void setUpdateSource(String updateSource)
    {
        this.updateSource = updateSource;
    }

    public Boolean getIgnoreBot()
    {
        return ignoreBot;
    }

    public void setIgnoreBot(Boolean ignoreBot)
    {
        this.ignoreBot = ignoreBot;
    }

    public DateTime getUpdateTimestamp()
    {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(DateTime updateTimestamp)
    {
        this.updateTimestamp = updateTimestamp;
    }

    public int getDpStoryId()
    {
        return dpStoryId;
    }

    public void setDpStoryId(int dpStoryId)
    {
        this.dpStoryId = dpStoryId;
    }

    @Override
    public String toString()
    {
        return "FeedStory [id=" + id + ", storyId=" + storyId + ", status=" + status.getName() + "]";
    }

}
