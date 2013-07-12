package com.netshelter.ifbrands.api.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.api.model.StatusInfo;

public class StatusServiceImplTest extends BaseServiceTest
{
  @Autowired
  private StatusServiceImpl service;

  @Test
  public void test()
  {
    StatusInfo si = service.getStatusInfo();
    assertNotNull( si );
  }
}
