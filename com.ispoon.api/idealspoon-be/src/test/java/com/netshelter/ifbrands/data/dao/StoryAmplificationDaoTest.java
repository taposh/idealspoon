package com.netshelter.ifbrands.data.dao;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.data.entity.IfbCampaign;
import com.netshelter.ifbrands.data.entity.IfbStoryAmplification;

@Component
public class StoryAmplificationDaoTest extends BaseDaoTest<IfbStoryAmplification>
{
  @Autowired
  private StoryAmplificationDao dao;
  @Autowired
  private CampaignDaoTest campaignDaoTest;

  @Override
  public BaseDao<IfbStoryAmplification> getDao()
  {
    return dao;
  }
/*
  @Test
  public void updateIdentifierTest() throws Exception
  {
    Serializable id = new Integer( 5 );
    StoryAmplification amp = new StoryAmplification();
    amp.setId( 0 );
    amp = dao.updateIdentifier( amp, id );
    assertEquals( id, amp.getId() );
  }
*/
  @Test
  public void testStoryAmplification() throws Exception
  {
    IfbStoryAmplification p = makeStoryAmplification( "kdj4klsIk", 1777777, "10", "facebook", 5 );
    p = dao.save( p );

    IfbStoryAmplification s = dao.getById( p.getStoryAmplificationId() );
    assertNotNull(s);
  }

  public static IfbStoryAmplification makeStoryAmplification( String shortUrlKey, int storyId, String userKey, String type, int consumptions )
  {
    IfbStoryAmplification e = new IfbStoryAmplification();
    e.setShortUrlKey( shortUrlKey );
    e.setDpStoryId( storyId );
    e.setUserKey( userKey );
    e.setType( type );
    e.setCreatedAmplification( new DateTime().toDate() );
    e.setLastAmplification( new DateTime().toDate() );
    return e;
  }

  @Override
  public IfbStoryAmplification makeEntity()
  {
    IfbCampaign ifbCampaign = campaignDaoTest.makeEntity();

    IfbStoryAmplification e = new IfbStoryAmplification();
    e.setShortUrlKey( uniqueString() );
    e.setDpStoryId( 2662912 );
    e.setType( "facebook" );
    e.setUserKey( "125" );
    e.setIfbCampaign( ifbCampaign );
    e.setCreatedAmplification( now );
    e.setLastAmplification( now );

    return dao.save( e );
  }
}
