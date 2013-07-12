package com.netshelter.ifbrands.dataplatform;

import java.util.Arrays;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.etl.dataplatform.DpServices;
import com.netshelter.ifbrands.etl.dataplatform.model.DpCategory;
import com.netshelter.ifbrands.etl.dataplatform.model.DpOrderType;
import com.netshelter.ifbrands.etl.dataplatform.model.DpRollupInterval;
import com.netshelter.ifbrands.etl.dataplatform.model.DpSite;
import com.netshelter.ifbrands.etl.dataplatform.model.DpStoryInfluence;
import com.netshelter.ifbrands.etl.dataplatform.model.GetAuthorsResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetCategoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceAndSentimentStoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceRollupBreakdownResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceRollupTotalResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceStoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetSitesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetStoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetStoryEngagementsResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetSuccessStatusResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetSuccessStatusWithStoryResponse;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( {"classpath:application-context.xml" })
public class DpServicesImplTest extends Assert
{
  Logger logger = LoggerFactory.getLogger();

  @Autowired
  private DpServices dpServices;

  private static final int ID_SAMSUNG = 714;
  private static final String KEY_CAMPAIGN = "sprint.s3_300X250.2012_q3";
  private static final DateTime FROM = new DateTime( "2012-09-14" );
  private static final DateTime TO = new DateTime( "2012-09-30" );
  private static final int STORY_ID = 2662911;
  private static final String IMAGE_URL = "http://www.bootspring.com/wp-content/uploads/2010/08/testing-darth-vader-300x240.jpg";

  @Test
  public void testGetSites() throws Exception
  {
    GetSitesResponse r1 = dpServices.getSites( null );
    assertNotNull( r1 );
    assertTrue( r1.getSites().size() > 0 );
    // ///////////////////////////////////////////////////////
    Iterator<DpSite> iter = r1.getSites().iterator();
    DpSite s1 = iter.next();
    DpSite s2 = iter.next();
    // ///////////////////////////////////////////////////////
    GetSitesResponse r2 = dpServices.getSites( Arrays.asList( s1.getId() ) );
    assertEquals( s1.getName(), r2.getSites().iterator().next().getName() );
    // ///////////////////////////////////////////////////////
    GetSitesResponse r3 = dpServices.getSites( Arrays.asList( s1.getId(), s2.getId() ) );
    assertEquals( 2, r3.getSites().size() );
  }

  @Test
  public void testGetBrands() throws Exception
  {
    GetCategoriesResponse r1 = dpServices.getBrands( null );
    assertNotNull( r1 );
    assertTrue( r1.getCategories().size() > 0 );
    // ///////////////////////////////////////////////////////
    Iterator<DpCategory> iter = r1.getCategories().iterator();
    DpCategory b1 = iter.next();
    DpCategory b2 = iter.next();
    // ///////////////////////////////////////////////////////
    GetCategoriesResponse r2 = dpServices.getBrands( Arrays.asList( b1.getId() ) );
    assertEquals( b1.getName(), r2.getCategories().iterator().next().getName() );
    // ///////////////////////////////////////////////////////
    GetCategoriesResponse r3 = dpServices.getBrands( Arrays.asList( b1.getId(), b2.getId() ) );
    assertEquals( 2, r3.getCategories().size() );
  }

  @Test
  public void testGetAuthors() throws Exception
  {
    GetAuthorsResponse r1 = dpServices.getAuthors( Arrays.asList( 1 ) );
    assertTrue( 1 == r1.getAuthors().size() );
    GetAuthorsResponse r2 = dpServices.getAuthors( Arrays.asList( 2 ) );
    assertTrue( 1 == r2.getAuthors().size() );
    // ///////////////////////////////////////////////////////
    GetAuthorsResponse r3 = dpServices.getAuthors( Arrays.asList( 1, 2 ) );
    assertEquals( 2, r3.getAuthors().size() );
  }

  @Test
  public void testGetStories() throws Exception
  {
    GetStoriesResponse r1 = dpServices.getStories( Arrays.asList( 1188610 ));
    assertTrue( 1 == r1.getStories().size() );
    GetStoriesResponse r2 = dpServices.getStories( Arrays.asList( 1188611 ));
    assertTrue( 1 == r2.getStories().size() );
    // ///////////////////////////////////////////////////////
    GetStoriesResponse r3 = dpServices.getStories( Arrays.asList( 1188610, 1188611 ));
    assertEquals( 2, r3.getStories().size() );
  }

  @Test
  public void testGetInfluenceTrendTotal() throws Exception
  {
    GetInfluenceRollupTotalResponse r1 = dpServices.getInfluenceRollupTotal( Arrays.asList( ID_SAMSUNG ),
                                                                   null, null, FROM, TO, "phone", DpRollupInterval.DAY );
    assertTrue( r1.getRollup().size() > 0 );
  }

  @Test
  public void testGetInfluenceTrendBreakdown() throws Exception
  {
    GetInfluenceRollupBreakdownResponse r1 = dpServices.getInfluenceRollupBreakdown( Arrays.asList( ID_SAMSUNG ),
                                                                                     Arrays.asList( KEY_CAMPAIGN ),
                                                                                     null, null, FROM, TO,
                                                                                     "phone", DpRollupInterval.DAY, false );
    assertTrue( r1.getPaid()    != null );
    assertTrue( r1.getTotal()   != null );
    assertTrue( r1.getOrganic() != null );
  }

//  @Test
//  public void testGetInfluenceStories() throws Exception
//  {
//    GetInfluenceStoriesResponse r1 = dpServices.getInfluenceStories( Arrays.asList( ID_SAMSUNG ), null, null,
//                                                                     FROM, TO, "phone", DpOrderType.INFLUENCESCORE, 1 );
//    assertEquals( 1, r1.getResultCount() );
//    assertEquals( 1, r1.getStoryInfluence().size() );
//
//    Iterable<DpStoryInfluence> i = r1.getStoryInfluence();
//    DpStoryInfluence s = i.iterator().next();
//
//    logger.info( "Points: " + s.getScoredInfluence().getPoints() );
//  }

  @Test
  public void testGetInfluenceAndSentimentStories() throws Exception
  {
    GetInfluenceAndSentimentStoriesResponse r1 =
        dpServices.getInfluenceAndSentimentStoriesTotal( Arrays.asList( ID_SAMSUNG ),
                                                         null, null,
                                                         FROM, TO,
                                                         "phone", DpOrderType.INFLUENCESCORE, 1, null, null);
    assertEquals( 1, r1.getResultCount() );
    assertEquals( 1, r1.getStoryInfluenceAndSentiment().size() );
  }

  @Test
  public void testGetInfluenceAndSentimentStoriesPaid() throws Exception
  {
    GetInfluenceAndSentimentStoriesResponse r1 =
        dpServices.getInfluenceAndSentimentStoriesPaid( Arrays.asList( ID_SAMSUNG ),
                                                        Arrays.asList( KEY_CAMPAIGN ),
                                                        null, null,
                                                        FROM, TO,
                                                        "phone", DpOrderType.INFLUENCESCORE, 1 );
    assertEquals( 1, r1.getResultCount() );
    assertEquals( 1, r1.getStoryInfluenceAndSentiment().size() );
  }

  @Test
  public void testSetCustomStoryImage() throws Exception
  {
    GetSuccessStatusResponse response = dpServices.setCustomStoryImage( STORY_ID, IMAGE_URL );

    assertEquals( true, response.getSuccess() );
  }

  @Test
  public void testGetStoryEngagements() throws Exception
  {
    GetStoryEngagementsResponse r1 = dpServices.getStoryEngagements( Arrays.asList( STORY_ID ), null, null );

    assertEquals( 1, r1.getEngagements().size() );
  }

  @Test
  public void testBuildAllStoryThumbnails() throws Exception
  {
    try {
      GetSuccessStatusWithStoryResponse r = dpServices.buildAllStoryThumbnails( STORY_ID );
      assertNotNull( r );
    } catch( RestClientException e ) {

    }
  }
}
