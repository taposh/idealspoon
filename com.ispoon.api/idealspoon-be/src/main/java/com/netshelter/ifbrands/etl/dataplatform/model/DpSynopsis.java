package com.netshelter.ifbrands.etl.dataplatform.model;

import org.joda.time.DateTime;

public class DpSynopsis
{
  private int storyId, authorId, siteId;
  private DateTime publishTime;

  public int getStoryId()
  {
    return storyId;
  }

  public void setStoryId( int storyId )
  {
    this.storyId = storyId;
  }

  public int getAuthorId()
  {
    return authorId;
  }

  public void setAuthorId( int authorId )
  {
    this.authorId = authorId;
  }

  public int getSiteId()
  {
    return siteId;
  }

  public void setSiteId( int siteId )
  {
    this.siteId = siteId;
  }

  public DateTime getPublishTime()
  {
    return publishTime;
  }

  public void setPublishTime( DateTime publishTime )
  {
    this.publishTime = publishTime;
  }

  @Override
  public String toString()
  {
    return "DpSynopsis [storyId=" + storyId + ", authorId=" + authorId + ", siteId=" + siteId
        + ", publishTime=" + publishTime + "]";
  }

}
