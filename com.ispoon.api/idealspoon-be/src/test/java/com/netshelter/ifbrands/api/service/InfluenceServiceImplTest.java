package com.netshelter.ifbrands.api.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.model.influence.BrandQuery;
import com.netshelter.ifbrands.api.model.influence.InfluenceBreakdown;
import com.netshelter.ifbrands.api.model.influence.InfluenceComparison;
import com.netshelter.ifbrands.api.model.influence.InfluenceStories;
import com.netshelter.ifbrands.api.model.influence.InfluenceSummary;
import com.netshelter.ifbrands.api.model.influence.InfluenceTrend;
import com.netshelter.ifbrands.data.dao.CampaignDaoTest;
import com.netshelter.ifbrands.data.entity.IfbCampaign;

public class InfluenceServiceImplTest extends BaseServiceTest
{
  Logger logger = LoggerFactory.getLogger();

  private static final int ID_SAMSUNG = 690;
  private static final int STORY_ID = 2662911;

  @Autowired
  private InfluenceService service;
  @Autowired
  private CampaignDaoTest campaignDaoTest;

  @Test
  public void getTrendTotalTest() throws Exception
  {
    InfluenceTrend trend = service.getTrendTotal( Arrays.asList( ID_SAMSUNG ), null,null,
                                                  new DateTime().minusMonths( 1 ),
                                                  new DateTime(), "phone" );
    assertTrue( trend.getTrend().size() > 0 );
  }

  @Test
  public void getTrendBreakdownTest() throws Exception
  {
    IfbCampaign c = campaignDaoTest.makeEntity();
    String[] goals = { "trend","summary" };
    for( String g: goals ) {
      System.out.println( "Goal: "+ g );
      InfluenceBreakdown<InfluenceTrend> trend = service.getBreakdown( Arrays.asList( ID_SAMSUNG ),
                                                                       Arrays.asList( c.getCampaignId() ),
                                                                       null,null,
                                                                       new DateTime().minusMonths( 1 ),
                                                                       new DateTime(), "phone", g, false, false);
      assertTrue( trend.getPaid()    != null );
      assertTrue( trend.getTotal()   != null );
      assertTrue( trend.getOrganic() != null );
    }
  }

  @Test
  public void getTrendComparisonTest() throws Exception
  {
    String[] series = new String[] { ID_SAMSUNG+":iphone", ID_SAMSUNG+":galaxy", ID_SAMSUNG+":" };
    InfluenceComparison<InfluenceTrend> trend = service.getTrendComparison( BrandQuery.parse( series ), null, null,
                                                                            new DateTime().minusDays( 7 ), new DateTime() );
    assertEquals( 3, trend.size() );
    Set<String> keys = trend.keySet();
    assertTrue( keys.contains( series[0] ));
    assertTrue( keys.contains( series[1] ));
  }

  @Test
  public void getSummaryPaidTest() throws Exception
  {
      InfluenceComparison<InfluenceSummary> compare = service.getSummaryPaidByCampaign( Arrays.asList( 1,2,3 ),
                                                                            new DateTime().minusMonths( 1 ),
                                                                            new DateTime(), false );
    assertEquals( 3, compare.size() );
  }

  @Test
  public void getStoriesTotalTest() throws Exception
  {
    InfluenceStories r1 = service.getStoriesTotal( Arrays.asList( ID_SAMSUNG ), null,null,
                                                   new DateTime().minusDays( 30 ),
                                                   new DateTime(),
                                                   "phone", "influencescore", 1 , null, null);
    assertEquals( 1, r1.getResultCount() );
    assertEquals( 1, r1.getResultCount() );
  }

  @Test
  public void getStoriesPaidTest() throws Exception
  {
    InfluenceStories r1 = service.getStoriesPaid( Arrays.asList( ID_SAMSUNG ),
                                                  Arrays.asList( campaignDaoTest.makeEntity().getCampaignId() ),
                                                  null,null,
                                                  new DateTime( 2012, 8, 1, 0, 0),
                                                  new DateTime( 2012, 11, 1, 0, 0),
                                                  "phone", "influencescore", 1 );
    assertEquals( 1, r1.getResultCount() );
    assertEquals( 1, r1.getResultCount() );
  }

  @Test
  public void getEngagementMetricsAsStringTest() throws Exception
  {
    Map<String,String> metrics = service.getEngagementMetrics( STORY_ID );

    assertNotNull( metrics );

  }

  @Test
  public void testGetStoryEngagements() throws Exception
  {
    Collection<Story> r1 = service.getStoryEngagements( Arrays.asList( STORY_ID ), null, null );

    assertEquals( 1, r1.size() );
  }

}

