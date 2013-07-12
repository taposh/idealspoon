package com.netshelter.ifbrands.api.model.campaign;

import java.util.List;

import org.joda.time.DateTime;

public class Feed
{
  public enum Ordering
  {
    ORDINAL, RANDOM, AUTO;
  }

  private Integer id;
  private List<FeedStory> feedStories;
  private Ordering ordering;
  private String key, name, url;
  private DateTime lastModified;

  public Feed( Integer id )
  {
    this.id = id;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId( Integer id )
  {
    this.id = id;
  }

  public List<FeedStory> getFeedStories()
  {
    return feedStories;
  }

  public void setFeedStories( List<FeedStory> feedStories )
  {
    this.feedStories = feedStories;
  }

  public Ordering getOrdering()
  {
    return ordering;
  }

  public void setOrdering( Ordering ordering )
  {
    this.ordering = ordering;
  }

  public String getKey()
  {
    return key;
  }

  public void setKey( String key )
  {
    this.key = key;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl( String url )
  {
    this.url = url;
  }

  public DateTime getLastModified()
  {
    return lastModified;
  }

  public void setLastModified( DateTime lastModified )
  {
    this.lastModified = lastModified;
  }

  @Override
  public String toString()
  {
    return "Feed [id=" + id + ", key=" + key + "]";
  }

}
