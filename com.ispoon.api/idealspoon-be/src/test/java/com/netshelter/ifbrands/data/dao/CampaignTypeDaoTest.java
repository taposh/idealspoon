package com.netshelter.ifbrands.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.data.dao.CampaignDao.CampaignTypeDao;
import com.netshelter.ifbrands.data.entity.IfbCampaignType;

@Component
public class CampaignTypeDaoTest extends BaseDaoTest<IfbCampaignType>
{
  @Autowired
  private CampaignTypeDao dao;

  @Override
  public BaseDao<IfbCampaignType> getDao()
  {
    return dao;
  }

  @Override
  protected IfbCampaignType makeEntity()
  {
    IfbCampaignType e = new IfbCampaignType();
    e.setCampaignTypeName( uniqueString() );
    e.setCreateTimestamp( now );
    return dao.save( e );
  }

}
