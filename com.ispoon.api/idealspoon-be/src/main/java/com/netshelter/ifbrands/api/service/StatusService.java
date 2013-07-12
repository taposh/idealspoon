package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.StatusInfo;

public interface StatusService
{
  public void flushAllCaches();
  public StatusInfo getStatusInfo();
}
