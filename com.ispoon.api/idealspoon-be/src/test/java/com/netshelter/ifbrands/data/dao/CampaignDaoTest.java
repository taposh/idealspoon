package com.netshelter.ifbrands.data.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.data.entity.IfbCampaign;

@Component
public class CampaignDaoTest extends BaseDaoTest<IfbCampaign>
{
  @Autowired
  private CampaignDao dao;
  @Autowired
  private CampaignTypeDaoTest campaignTypeDaoTest;
  @Autowired
  private CampaignStatusDaoTest campaignStatusDaoTest;

  @Override
  public BaseDao<IfbCampaign> getDao()
  {
    return dao;
  }

  @Test
  public void testDao() throws Exception
  {
    // Test eager fetching
    IfbCampaign e = makeEntity();
    IfbCampaign a = dao.getById( e.getCampaignId() );
    assertNotNull( a.getIfbCampaignType().getCampaignTypeName() );
    assertNotNull( a.getIfbCampaignStatus().getCampaignStatusName() );
  }

  @Override
  public IfbCampaign makeEntity()
  {
    IfbCampaign e = new IfbCampaign();
    e.setCampaignKey( uniqueString() );
//    e.setCampaignKey( "asewhr7adzmt" );
    e.setCampaignName( "name" );
    e.setUserKey( "userKey" );
    e.setDpBrandId( 5 );
    e.setStartDate( now );
    e.setStopDate( now );
    e.setKeywords( "keywords" );
    e.setCampaignEnabled( true );
    e.setCreateTimestamp( now );
    e.setIfbCampaignType( campaignTypeDaoTest.makeEntity() );
    e.setIfbCampaignStatus( campaignStatusDaoTest.makeEntity() );

    return dao.save( e );
  }
//
//
//  @Test
//  public void testDrillBackwards()
//  {
//    feedStoryDaoTest.makeEntity();
//    List<IfbCampaign> ifbCampaigns = dao.findByDpStoryId( Integer.valueOf( 5 ) );
//    
//    String.format( "%s", "test" );
//  }
}
