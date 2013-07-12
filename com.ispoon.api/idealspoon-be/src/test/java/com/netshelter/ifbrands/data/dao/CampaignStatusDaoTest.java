package com.netshelter.ifbrands.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.data.dao.CampaignDao.CampaignStatusDao;
import com.netshelter.ifbrands.data.entity.IfbCampaignStatus;

@Component
public class CampaignStatusDaoTest extends BaseDaoTest<IfbCampaignStatus>
{
  @Autowired
  private CampaignStatusDao dao;

  @Override
  public BaseDao<IfbCampaignStatus> getDao()
  {
    return dao;
  }

  @Override
  protected IfbCampaignStatus makeEntity()
  {
    IfbCampaignStatus e = new IfbCampaignStatus();
    e.setCampaignStatusName( uniqueString() );
    e.setCreateTimestamp( now );
    return dao.save( e );
  }
}
