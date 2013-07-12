package com.netshelter.ifbrands.data.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.data.entity.IfbAd;

@Component
public class AdDaoTest extends BaseDaoTest<IfbAd>
{
  @Autowired AdDao dao;
  @Autowired FeedDaoTest feedDaoTest;
  @Autowired AdTypeDaoTest adTypeDaoTest;
  @Autowired AdStatusDaoTest adStatusDaoTest;
  @Autowired CampaignDaoTest campaignDaoTest;

  @Override
  protected BaseDao<IfbAd> getDao()
  {
    return dao;
  }

  @Test
  public void testDao() throws Exception
  {
    // Test eager fetching
    IfbAd e = makeEntity();
    IfbAd a = dao.getById( e.getAdId() );
    assertNotNull( a.getIfbAdType().getAdTypeName() );
  }

  @Override
  public IfbAd makeEntity()
  {
    IfbAd e = new IfbAd();
    e.setIfbFeed( feedDaoTest.makeEntity() );
    e.setIfbAdType( adTypeDaoTest.makeEntity() );
    e.setIfbAdStatus( adStatusDaoTest.makeEntity() );
    e.setIfbCampaign( campaignDaoTest.makeEntity() );
    e.setAdKey( uniqueString() );
    e.setAdName( "name" );
    e.setCreateTimestamp( now );
    return dao.save( e );
  }
}

