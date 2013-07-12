package com.netshelter.ifbrands.api.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdControllerTest extends BaseControllerTest
{
  @Autowired
  AdController controller;

  ////////////////////////////////////////
  // These just test wiring correctness //
  ////////////////////////////////////////
  @Test
  public void testGetAds()
  {
    controller.getAds( "-", null, null, null, null );
  }

  @Test
  public void testGetAdTypes()
  {
    controller.getAdType( "-" );
  }

  @Test
  public void testGetAdStatuses()
  {
    controller.getAdStatus( "-" );
  }

}
