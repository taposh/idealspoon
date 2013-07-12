package com.netshelter.ifbrands.api.controller;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.netshelter.ifbrands.data.dao.CampaignDaoTest;
import com.netshelter.ifbrands.data.entity.IfbCampaign;


public class InfluenceControllerTest extends BaseControllerTest
{
  @Autowired
  private InfluenceController controller;
  @Autowired
  private CampaignDaoTest campaignDaoTest;

  ////////////////////////////////////////
  // These just test wiring correctness //
  ////////////////////////////////////////
  @Test
  public void getInfluenceTrendTotalTest()
  {
    LocalDate start = new LocalDate( "2012-01-01" );
    LocalDate stop = new LocalDate( "2012-01-30" );
    controller.getInfluenceTrendTotal( "714", start, stop, null, null, "iphone" );
  }

    @Test
    public void getInfluenceBreakdownTest()
    {
        IfbCampaign c = campaignDaoTest.makeEntity();
        LocalDate start = new LocalDate( "2012-01-01" );
        LocalDate stop = new LocalDate( "2012-01-30" );
        controller.getInfluenceBreakdown( "trend", "714", c.getCampaignId().toString(), start, stop, null, null, "iphone", "false" );
        controller.getInfluenceBreakdown( "summary", "714", c.getCampaignId().toString(), start, stop, null, null, "iphone", "false" );
    }

    @Test
    public void getInfluenceBreakdownByCampaignSpeedTest()
    {
        StringBuilder builder = new StringBuilder();
        LocalDate start = new LocalDate( "2012-01-01" );
        LocalDate stop = new LocalDate( "2012-01-30" );
        long ms = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            builder.append((builder.length() > 0?",":""));
            builder.append(campaignDaoTest.makeEntity().getCampaignId());
        }
        controller.getInfluenceBreakdownByCampaign( "summary", "714", builder.toString().trim(), start, stop, null, null, "iphone", "false");
        logger.debug("getInfluenceBreakdownByCampaignSpeedTest: DONE IN " + (System.currentTimeMillis() - ms) + "ms");
    }

    @Test
    public void getInfluenceBreakdownSpeedTest()
    {
        LocalDate start = new LocalDate( "2012-01-01" );
        LocalDate stop = new LocalDate( "2012-01-30" );
        long ms = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Integer cid = campaignDaoTest.makeEntity().getCampaignId();
            controller.getInfluenceBreakdown( "summary", "714", cid.toString(), start, stop, null, null, "iphone", "false" );
        }
        logger.debug("getInfluenceBreakdownSpeedTest: DONE IN " + (System.currentTimeMillis() - ms) + "ms");
    }

  @Test
  public void getInfluenceStoriesTotalTest()
  {
    LocalDate start = new LocalDate( "2012-01-01" );
    LocalDate stop = new LocalDate( "2012-01-30" );
    controller.getInfluenceStoriesTotal( "1", "influencescore", start, stop, null, null, "iphone", 5 , null);
  }

  @Test
  public void getInfluenceStoriesPaidTest()
  {
    LocalDate start = new LocalDate( "2012-01-01" );
    LocalDate stop = new LocalDate( "2012-01-30" );
    controller.getInfluenceStoriesPaid( "1", campaignDaoTest.makeEntity().getCampaignId().toString(),
                                        "influencescore", start, stop, null, null, "iphone", 5 );
  }

}
