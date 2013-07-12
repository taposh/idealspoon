package com.netshelter.ifbrands.publish;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This is a POJO used to serialize FeedStory data to S3
 * @author ekrevets
 *
 */
public class JsonFeedStoryFull
{
  private String postId;
  private String postHash;
  private String title;
  private String summary;
  private String siteName;
  private String siteTitle;
  private String siteId;
  private String link;
  private String author;
  private String authorId;
  private String updated;
  private String events;
  private String points;
  private String metrics;
  private String imgUrl;
  private String imgUrlCustom;
  private String thumbnailGenerated;
  private String topics;
  private String rowId;
  private Map<String,String> thumbnails;
  
  @JsonProperty( "post_id" )
  public String getPostId()
  {
    return postId;
  }
  public void setPostId( String postId )
  {
    this.postId = postId;
  }
  
  @JsonProperty( "post_hash" )
  public String getPostHash()
  {
    return postHash;
  }
  public void setPostHash( String postHash )
  {
    this.postHash = postHash;
  }
  
  @JsonProperty( "title" )
  public String getTitle()
  {
    return title;
  }
  public void setTitle( String title )
  {
    this.title = title;
  }
  
  @JsonProperty( "summary" )
  public String getSummary()
  {
    return summary;
  }
  public void setSummary( String summary )
  {
    this.summary = summary;
  }
  
  @JsonProperty( "site_name" )
  public String getSiteName()
  {
    return siteName;
  }
  public void setSiteName( String siteName )
  {
    this.siteName = siteName;
  }
  
  @JsonProperty( "site_title" )
  public String getSiteTitle()
  {
    return siteTitle;
  }
  public void setSiteTitle( String siteTitle )
  {
    this.siteTitle = siteTitle;
  }
  
  @JsonProperty( "site_id" )
  public String getSiteId()
  {
    return siteId;
  }
  public void setSiteId( String siteId )
  {
    this.siteId = siteId;
  }
  @JsonProperty( "link" )
  public String getLink()
  {
    return link;
  }
  public void setLink( String link )
  {
    this.link = link;
  }
  
  @JsonProperty( "author" )
  public String getAuthor()
  {
    return author;
  }
  public void setAuthor( String author )
  {
    this.author = author;
  }
  
  @JsonProperty( "author_id" )
  public String getAuthorId()
  {
    return authorId;
  }
  public void setAuthorId( String authorId )
  {
    this.authorId = authorId;
  }
  
  @JsonProperty( "updated" )
  public String getUpdated()
  {
    return updated;
  }
  public void setUpdated( String updated )
  {
    this.updated = updated;
  }
  
  @JsonProperty( "events" )
  public String getEvents()
  {
    return events;
  }
  public void setEvents( String events )
  {
    this.events = events;
  }
  
  @JsonProperty( "points" )
  public String getPoints()
  {
    return points;
  }
  public void setPoints( String points )
  {
    this.points = points;
  }
  
  @JsonProperty( "metrics" )
  public String getMetrics()
  {
    return metrics;
  }
  public void setMetrics( String metrics )
  {
    this.metrics = metrics;
  }
  
  @JsonProperty( "img_url" )
  public String getImgUrl()
  {
    return imgUrl;
  }
  public void setImgUrl( String imgUrl )
  {
    this.imgUrl = imgUrl;
  }
  
  @JsonProperty( "img_url_custom" )
  public String getImgUrlCustom()
  {
    return imgUrlCustom;
  }
  public void setImgUrlCustom( String imgUrlCustom )
  {
    this.imgUrlCustom = imgUrlCustom;
  }
  
  @JsonProperty( "thumbnail_generated" )
  public String getThumbnailGenerated()
  {
    return thumbnailGenerated;
  }
  public void setThumbnailGenerated( String thumbnailGenerated )
  {
    this.thumbnailGenerated = thumbnailGenerated;
  }
  
  @JsonProperty( "topics" )
  public String getTopics()
  {
    return topics;
  }
  public void setTopics( String topics )
  {
    this.topics = topics;
  }
  
  @JsonProperty( "row_id" )
  public String getRowId()
  {
    return rowId;
  }
  public void setRowId( String rowId )
  {
    this.rowId = rowId;
  }
  
  @JsonProperty( "thumbnails" )
  public Map<String,String> getThumbnails()
  {
    return thumbnails;
  }
  public void setThumbnails( Map<String,String> thumbnails )
  {
    this.thumbnails = thumbnails;
  }
  
}
