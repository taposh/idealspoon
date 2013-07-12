package com.netshelter.ifbrands.data.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.data.entity.IfbCampaign;
import com.netshelter.ifbrands.data.entity.IfbCampaignStatus;
import com.netshelter.ifbrands.data.entity.IfbCampaignType;

public class CampaignDao extends BaseDao<IfbCampaign>
{
  @Autowired
  public CampaignStatusDao campaignStatusDao;
  @Autowired
  public CampaignTypeDao campaignTypeDao;

  @Override
  public void customiseCriteria( Criteria criteria )
  {
    criteria.createAlias( "ifbCampaignStatus", "status", CriteriaSpecification.INNER_JOIN );
    criteria.createAlias( "ifbCampaignType", "type", CriteriaSpecification.INNER_JOIN );
  }

  @Override
  protected Class<IfbCampaign> getEntityClass()
  {
    return IfbCampaign.class;
  }

  @Override
  protected Serializable getIdentifier( IfbCampaign entity )
  {
    return entity.getCampaignId();
  }

  @Override
  protected void updateIdentifier( IfbCampaign entity, Serializable id )
  {
    entity.setCampaignId( (Integer)id );
  }

  @Override
  public boolean theSame( IfbCampaign a, IfbCampaign b )
  {
    return a.getCampaignKey().equals( b.getCampaignKey() );
  }

  @Override
  public String asString( IfbCampaign entity )
  {
    return String.format( "IfbCampaign [campaignId=%s, campaignKey=%s, campaignName=%s]",
                          entity.getCampaignId(), entity.getCampaignKey(), entity.getCampaignName() );
  }

  public IfbCampaign getByCampaignKey( String campaignKey )
  {
    DetachedCriteria criteria = DetachedCriteria.forClass( IfbCampaign.class );
    addPropertyRestriction( criteria, "campaignKey", campaignKey) ;

    return findFirstByCriteria( criteria );
  }

  public List<IfbCampaign> findByAny( Collection<Integer> campaignIds, Boolean enabled,
                                      Collection<Integer> brandIds, Collection<String> campaignKeys,
                                      Collection<Integer> campaignStatusIds,
                                      Collection<Integer> campaignTypeIds, Collection<String> userKeys )
  {
    return findByAny(campaignIds, enabled, brandIds, campaignKeys, campaignStatusIds, campaignTypeIds, userKeys, null);
  }

  public List<IfbCampaign> findByAny( Collection<Integer> campaignIds, Boolean enabled,
                                      Collection<Integer> brandIds, Collection<String> campaignKeys,
                                      Collection<Integer> campaignStatusIds,
                                      Collection<Integer> campaignTypeIds, Collection<String> userKeys,
                                      Collection<String> clientUserKeys)
  {
    DetachedCriteria criteria = DetachedCriteria.forClass( IfbCampaign.class );
    addIdRestriction( criteria, campaignIds );
    addPropertyRestriction( criteria, "userKey", userKeys );
    addPropertyRestriction( criteria, "clientUserKey", clientUserKeys );
    addPropertyRestriction( criteria, "dpBrandId", brandIds );
    addPropertyRestriction( criteria, "campaignKey", campaignKeys );
    addPropertyRestriction( criteria, "campaignEnabled", enabled );
    addPropertyRestriction( criteria, "ifbCampaignType.campaignTypeId"  , campaignTypeIds );
    addPropertyRestriction( criteria, "ifbCampaignStatus.campaignStatusId", campaignStatusIds );

    return findByCriteria( criteria );
  }

  public static class CampaignStatusDao extends BaseDao<IfbCampaignStatus>
  {
    final public IfbCampaignStatus PENDING, ACTIVE, INACTIVE;

    public CampaignStatusDao()
    {
      super();
      Date now = new Date();
      PENDING = new IfbCampaignStatus();
      PENDING.setCampaignStatusId( 1 );
      PENDING.setCampaignStatusName( "Pending" );
      PENDING.setCreateTimestamp( now );
      ACTIVE = new IfbCampaignStatus();
      ACTIVE.setCampaignStatusId( 2 );
      ACTIVE.setCampaignStatusName( "Active" );
      ACTIVE.setCreateTimestamp( now );
      INACTIVE = new IfbCampaignStatus();
      INACTIVE.setCampaignStatusId( 3 );
      INACTIVE.setCampaignStatusName( "Inactive" );
      INACTIVE.setCreateTimestamp( now );
    }

    @Override
    public Collection<IfbCampaignStatus> getEntityConstants()
    {
      return Arrays.asList( PENDING, ACTIVE, INACTIVE );
    }

    @Override
    public String asString( IfbCampaignStatus entity )
    {
      return String.format( "IfbCampaignStatus [campaignStatusId=%s, campaignStatusName=%s]",
                            entity.getCampaignStatusId(), entity.getCampaignStatusName() );
    }

    @Override
    public boolean theSame( IfbCampaignStatus a, IfbCampaignStatus b )
    {
      return a.getCampaignStatusName().equals( b.getCampaignStatusName() );
    }

    @Override
    protected Class<IfbCampaignStatus> getEntityClass()
    {
      return IfbCampaignStatus.class;
    }

    @Override
    protected Serializable getIdentifier( IfbCampaignStatus entity )
    {
      return entity.getCampaignStatusId();
    }

    @Override
    protected void updateIdentifier( IfbCampaignStatus entity, Serializable id )
    {
      entity.setCampaignStatusId( (Integer)id );
    }
  }

  public static class CampaignTypeDao extends BaseDao<IfbCampaignType>
  {
    final public IfbCampaignType SOCIAL, PAID, DEMO;

    public CampaignTypeDao()
    {
      super();
      Date now = new Date();
      SOCIAL = new IfbCampaignType();
      SOCIAL.setCampaignTypeId( 1 );
      SOCIAL.setCampaignTypeName( "Social" );
      SOCIAL.setCreateTimestamp( now );
      PAID = new IfbCampaignType();
      PAID.setCampaignTypeId( 2 );
      PAID.setCampaignTypeName( "Paid" );
      PAID.setCreateTimestamp( now );
      DEMO = new IfbCampaignType();
      DEMO.setCampaignTypeId( 3 );
      DEMO.setCampaignTypeName( "Demo" );
      DEMO.setCreateTimestamp( now );
    }

    @Override
    public Collection<IfbCampaignType> getEntityConstants()
    {
      return Arrays.asList( SOCIAL, PAID, DEMO );
    }

    @Override
    public String asString( IfbCampaignType entity )
    {
      return String.format( "IfbCampaignType [campaignTypeId=%s, campaignTypeName=%s]",
                            entity.getCampaignTypeId(), entity.getCampaignTypeName() );
    }

    @Override
    public boolean theSame( IfbCampaignType a, IfbCampaignType b )
    {
      return a.getCampaignTypeName().equals( b.getCampaignTypeName() );
    }

    @Override
    protected Class<IfbCampaignType> getEntityClass()
    {
      return IfbCampaignType.class;
    }

    @Override
    protected Serializable getIdentifier( IfbCampaignType entity )
    {
      return entity.getCampaignTypeId();
    }

    @Override
    protected void updateIdentifier( IfbCampaignType entity, Serializable id )
    {
      entity.setCampaignTypeId( (Integer)id );
    }
  }
}
