package com.netshelter.ifbrands.etl.dataplatform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.joda.time.DateTime;

public class DpStory
{
  private int storyId, authorId, siteId;
  private String hash, title, summary;
  private String storyUrl, imageUrl;
  private DateTime publishTime;
  private Collection<DpImageSpec> thumbnails;

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

  public String getHash()
  {
    return hash;
  }

  public void setHash( String hash )
  {
    this.hash = hash;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle( String title )
  {
    this.title = title;
  }

  public String getSummary()
  {
    return summary;
  }

  public void setSummary( String summary )
  {
    this.summary = summary;
  }

  public String getStoryUrl()
  {
    return storyUrl;
  }

  public void setStoryUrl( String storyUrl )
  {
    this.storyUrl = storyUrl;
  }

  public String getImageUrl()
  {
    return imageUrl;
  }

  public void setImageUrl( String imageUrl )
  {
    this.imageUrl = imageUrl;
  }

  public DateTime getPublishTime()
  {
    return publishTime;
  }

  public void setPublishDate( DateTime publishDate )
  {
    this.publishTime = publishDate;
  }

  public Collection<DpImageSpec> getThumbnails()
  {
    return thumbnails;
  }

  @JsonDeserialize(as=ArrayList.class)
  public void setThumbnails( Collection<DpImageSpec> thumbnails )
  {
    this.thumbnails = thumbnails;
  }

  @Override
  public String toString()
  {
    return "DpStory [storyId=" + storyId + ", authorId=" + authorId + ", siteId=" + siteId + ", hash=" + hash
        + ", title=" + StringUtils.abbreviate( title, 15 ) + ", publishTime=" + publishTime + "]";
  }

}