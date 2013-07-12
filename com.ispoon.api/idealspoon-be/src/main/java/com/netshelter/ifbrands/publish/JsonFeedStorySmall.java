package com.netshelter.ifbrands.publish;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This is a POJO used to serialize FeedStory data to S3
 * @author ekrevets
 *
 */
public class JsonFeedStorySmall 
{
  private String postHash;
  private String title;
  private String siteName;
  private String siteTitle;
  private String link;
  private String metrics;
  private String thumbnailsVersion;
    
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
  
  @JsonProperty( "link" )
  public String getLink()
  {
    return link;
  }
  public void setLink( String link )
  {
    this.link = link;
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
  
  @JsonProperty( "thumbnails_version" )
  public String getThumbnailsVersion()
  {
    return thumbnailsVersion;
  }
  public void setThumbnailsVersion( String thumbnailsVersion )
  {
    this.thumbnailsVersion = thumbnailsVersion;
  }  
}
