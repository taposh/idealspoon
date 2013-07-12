package com.netshelter.ifbrands.api.model.storyamplification;

public class ShortUrl {
  private String shortUrl;
  private String key;
  
  // For compatibility with ShareAPI JSON response
  public void setShort_url(String shortUrl) {
    this.shortUrl = shortUrl;
  }
  
  public String getShortUrl() {
    return shortUrl;
  }
  
  public void setShortUrl(String shortUrl) {
    this.shortUrl = shortUrl;
  }
  
  public String getKey() {
    return key;
  }
  
  public void setKey(String key) {
    this.key = key;
  }
  
  @Override
  public String toString()
  {
    return String.format( "shortUrl(%s:%s)", shortUrl, key );
  }
}
