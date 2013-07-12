package com.netshelter.ifbrands.nap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.netshelter.ifbrands.etl.analytics.NapServices;
import com.netshelter.ifbrands.etl.analytics.model.GetDimensionIdResponse;
import com.netshelter.ifbrands.etl.analytics.model.GetDimensionInfoResponse;
import com.netshelter.ifbrands.etl.analytics.model.NapDimension;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( {"classpath:application-context.xml" })
public class NapServicesImplTest
{
  @Autowired
  private NapServices napServices;

  @Test
  public void getDimensionIdTest()
  {
    GetDimensionIdResponse r = napServices.getExistingDimensionId( NapDimension.CAMPAIGN, "foo" );
    Assert.assertNotNull( r );
  }

  @Test
  public void getDimensionInfoTest()
  {
    GetDimensionInfoResponse r = napServices.getDimensionInfo( NapDimension.CAMPAIGN, null );
    Assert.assertNotNull( r );
  }
}
