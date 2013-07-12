package com.netshelter.ifbrands.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.campaign.CampaignStatus;
import com.netshelter.ifbrands.api.model.campaign.CampaignType;

public class CampaignServiceImplTest extends BaseServiceTest {
  private static final String CAMPAIGN_NAME = "TestCampaignName";
  private static final String CAMPAIGN_NAME2 = "TestCampaignName2";
  private static final String USER_KEY = "123";
  private static final Integer BRAND_ID = 690;
  private static final LocalDate START_DATE = new LocalDate( 2012, 8, 1 );
  private static final LocalDate STOP_DATE = new LocalDate( 2012, 9, 1 );
  private static final String KEYWORDS = "Test1 AND Test2 AND Test 3";
  private static final Boolean CAMPAIGN_ENABLED = Boolean.TRUE;
  private static final String CAMPAIGN_KEY = "lksjf3klsdf";
  private static final String CAMPAIGN_KEY2 = "lksjf3klsdf2";
  private static final String CLIENT_USER_KEY = "1234567890";

  @Autowired
  private CampaignService service;

  @Test
  public void testCampaignService() throws Exception
  {
    // Create
    CampaignStatus cs1 = service.createCampaignStatus( "TestCampaignStatus1" );
    CampaignStatus cs2 = service.createCampaignStatus( "TestCampaignStatus2" );
    CampaignType ct = service.createCampaignType( "TestCampaignType1" );

    Campaign c1 = service.createCampaign( CAMPAIGN_NAME, USER_KEY, BRAND_ID,
        START_DATE, STOP_DATE, KEYWORDS, CAMPAIGN_ENABLED, cs1.getId(),
        ct.getId(), CAMPAIGN_KEY, null, null, null, null, null, null, null );
    assertNotNull( c1 );

    Campaign c2 = service.createCampaign( CAMPAIGN_NAME2, USER_KEY, BRAND_ID,
        START_DATE, STOP_DATE, KEYWORDS, CAMPAIGN_ENABLED, cs2.getId(),
        ct.getId(), CAMPAIGN_KEY2, CLIENT_USER_KEY, null, null, null, null, null, null, null);
    assertNotNull( c2 );

    // Get
    Campaign c3 = service.getCampaign( c1.getId() );
    assertEquals( c1.getCampaignKey(), c3.getCampaignKey() );

    // Get multiple
    Collection<Integer> statusIds = new ArrayList<Integer>();
    statusIds.add( cs1.getId() );
    statusIds.add( cs2.getId() );
    Collection<Campaign> campaigns = service.getCampaigns( Arrays.asList( c1.getId(), c2.getId() ), CAMPAIGN_ENABLED,
                                                           Arrays.asList( BRAND_ID ), Arrays.asList( c1.getCampaignKey(), c2.getCampaignKey() ),
                                                           statusIds, Arrays.asList( ct.getId() ),
                                                           Arrays.asList( USER_KEY ));
    assertNotNull( campaigns );
    assertTrue( campaigns.size() == 2 );

    campaigns = service.getCampaigns( Arrays.asList( c1.getId(), c2.getId() ), CAMPAIGN_ENABLED,
            Arrays.asList( BRAND_ID ), Arrays.asList( c1.getCampaignKey(), c2.getCampaignKey() ),
            statusIds, Arrays.asList( ct.getId() ),
            Arrays.asList( USER_KEY ), Arrays.asList ( CLIENT_USER_KEY ));
    assertNotNull( campaigns );
    assertEquals( 1,  campaigns.size() );


    campaigns = service.getCampaigns(null, null, null, null, null, null, null, Arrays.asList ( CLIENT_USER_KEY ));
    assertNotNull( campaigns );
    assertEquals( 1,  campaigns.size() );

    campaigns = service.getCampaigns(null, null, null, null, null, null, null, Arrays.asList ( CLIENT_USER_KEY, "123", "321" ));
    assertNotNull( campaigns );
    assertEquals( 1,  campaigns.size() );

    // Delete
    Boolean b = service.deleteCampaign( c1.getId() );
    assertTrue( b );
    Campaign c4 = service.getCampaign( c1.getId() );
    assertNull( c4 );
  }

  @Test
  public void createCampaignStatusTest() throws Exception
  {
    CampaignStatus cs = service.createCampaignStatus( "TestCampaignStatus3" );
    assertNotNull( cs );
  }

  @Test
  public void createCampaignTypeTest() throws Exception
  {
    CampaignType ct = service.createCampaignType( "TestCampaignType2" );
    assertNotNull( ct );
  }
}
