package com.netshelter.ifbrands.api.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.api.model.storyamplification.ShortUrl;
import com.netshelter.ifbrands.api.model.storyamplification.StoryAmplificationContainer;
import com.netshelter.ifbrands.api.model.storyamplification.StoryAmplifyDetail;
import com.netshelter.ifbrands.data.dao.CampaignDaoTest;
import com.netshelter.ifbrands.data.entity.IfbCampaign;
import com.netshelter.ifbrands.etl.shareapi.SaServices;

public class ShareServiceImplTest extends BaseServiceTest
{
  public static final Integer STORY_ID1 = 1951966;
  public static final Integer STORY_ID2 = 2662912;
  public static final String USER_ID = "125";
  public static final String SHARE_TYPE = "facebook";
  public static final String SHORT_URL_KEY = "a257a3fb";
  public static final Integer CAMPAIGN_ID = 1;

  @Autowired
  private ShareService service;
  @Autowired
  private CampaignDaoTest campaignDaoTest;
  @Autowired
  private SaServices saService;

  @Test
  public void getShortUrlTest() throws Exception
  {
    ShortUrl shortUrl = service.getShortUrl( STORY_ID2, SHARE_TYPE, null );
    assertNotNull( shortUrl );
  }

  /**
   * Tests to confirm that amplifying a story works
   * @throws Exception
   */
  @Test
  public void amplifyPostTests() throws Exception
  {
    // Simple amplifyPost with no associated Campaign
    String amplifyUrl1 = service.getAmplifyUrl( STORY_ID1, USER_ID, SHARE_TYPE, null );
    assertNotNull( amplifyUrl1 );

    // Create a test Campaign
    IfbCampaign c0 = campaignDaoTest.makeEntity();
    assertNotNull( c0 );

    // Confirm that two amplifyPost calls with no associated Ad produce the same amplifyUrl
    String amplifyUrl2 = service.getAmplifyUrl( STORY_ID1, USER_ID, SHARE_TYPE, null );
    assertNotNull( amplifyUrl2);
    assertEquals( amplifyUrl2, amplifyUrl1 );

    // Compare amplifyPost with no associated Ad to amplifyPost with an associated Ad (should have different amplifyUrls)
    String amplifyUrl3 = service.getAmplifyUrl( STORY_ID1, USER_ID, SHARE_TYPE, c0.getCampaignId() );
    assertNotNull( amplifyUrl3);
    assertNotSame( amplifyUrl3, amplifyUrl1 );
  }

  /**
   * Tests for story amplification stats calls
   * @throws Exception
   */
  @Test
  public void getAmplifyStatsTest() throws Exception
  {
    // Create a test Campaign
    IfbCampaign c0 = campaignDaoTest.makeEntity();
    assertNotNull( c0 );
    
    // Creates a fake storyAmplification indirectly (with an associated ShareAPI record)
    service.getAmplifyUrl( STORY_ID1, USER_ID, SHARE_TYPE, c0.getCampaignId() );

    List<Integer> storyIds = new ArrayList<Integer>();
    storyIds.add( STORY_ID1 );
    storyIds.add( STORY_ID2 );

    // Get stats for stories
    List<StoryAmplifyDetail> storyAmplifications2 = service.getAmplifyStats( null, storyIds, null );
    assertNotNull( storyAmplifications2 );
    assertTrue( "Zero items returned", storyAmplifications2.size() > 0 );

    // Get stats for campaign over date range
    List<StoryAmplificationContainer> storyAmplifications4 = service.getAmplifyStats( "campaign", c0.getCampaignId(), new DateTime( 2012, 8, 10, 0, 0 ), new DateTime() );
    assertNotNull( storyAmplifications4 );
    assertTrue( "Zero items returned", storyAmplifications4.size() > 0 );
  }

  @Test
  public void getShortUrlStatsTest() throws Exception
  {
    // Creates a fake storyAmplification indirectly (with an associated ShareAPI record)
    ShortUrl shortUrl = service.getShortUrl( STORY_ID1, SHARE_TYPE, null );

    Integer clicks = saService.getShortUrlStats( shortUrl.getKey(), DateTime.now().minusDays( 2 ), DateTime.now().plusDays( 2 ) );
    assertNotNull( clicks );
  }
}
