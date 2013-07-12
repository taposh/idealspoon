package com.netshelter.ifbrands.api.controller;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FeedControllerTest extends BaseControllerTest
{
  private static final int STORY_ID = 2662911;
  private static final String IMAGE_URL = "http%3A%2F%2Fcdn1.sbnation.com%2Fcommunity_logos%2F34088%2Fverge-med.png";
  
  @Autowired
  FeedController controller;

  ////////////////////////////////////////
  // These just test wiring correctness //
  ////////////////////////////////////////
  @Test
  public void testGetFeeds()
  {
    controller.getFeeds( "-", null );
  }

  @Test
  public void testGetFeedStories()
  {
    controller.getFeedStories( 1, null );
  }

  @Test
  public void testGetFeedStoryStatuses()
  {
    controller.getFeedStoryStatus( "-" );
  }
  
  @Test
  public void testSetCustomStoryImage() throws UnsupportedEncodingException
  {
    controller.setCustomStoryImage( STORY_ID, IMAGE_URL );
  }
}
