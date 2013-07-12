package com.netshelter.ifbrands.publish;


public interface PublishService
{
  public enum OutputType { GZIP,TEXT };

  public void publishFeed( int feedId );
  //public String getFeedUrl( String feedKey );
  public String getGeneratedFeedUrl( String feedKey );
}
