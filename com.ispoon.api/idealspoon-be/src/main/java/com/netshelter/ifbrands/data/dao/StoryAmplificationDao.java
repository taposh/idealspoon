package com.netshelter.ifbrands.data.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.data.entity.IfbStoryAmplification;

public class StoryAmplificationDao extends BaseDao<IfbStoryAmplification>
{

  @Override
  public void customiseCriteria( Criteria criteria )
  {
    criteria.createAlias( "ifbCampaign", "campaign", CriteriaSpecification.LEFT_JOIN );
    //criteria.createAlias( "ad.ifbCampaign", "campaign", CriteriaSpecification.LEFT_JOIN );
    //criteria.createAlias( "ifbCampaign", "campaign", CriteriaSpecification.INNER_JOIN );

  }

  @Override
  protected Class<IfbStoryAmplification> getEntityClass()
  {
    return IfbStoryAmplification.class;
  }

  @Override
  protected Serializable getIdentifier( IfbStoryAmplification entity )
  {
    return entity.getStoryAmplificationId();
  }

  @Override
  protected void updateIdentifier( IfbStoryAmplification entity, Serializable id )
  {
    entity.setStoryAmplificationId( (Integer)id );
  }

  @Override
  public boolean theSame( IfbStoryAmplification a, IfbStoryAmplification b )
  {
    return a.getShortUrlKey().equals( b.getShortUrlKey() );
  }

  @Override
  public String asString( IfbStoryAmplification entity )
  {
    return String.format( "IfbStoryAmplification(%d:%s,%d,%s)",
                          entity.getStoryAmplificationId(),
                          entity.getShortUrlKey(),
                          entity.getType() );
  }

  public List<IfbStoryAmplification> getEntitiesForAd( int adId )
  {
    DetachedCriteria criteria = DetachedCriteria.forClass( IfbStoryAmplification.class );
    addPropertyRestriction( criteria, "ifbAd.adId", adId );

    return findByCriteria( criteria );
  }
  
  public List<IfbStoryAmplification> getEntitiesForCampaign( int campaignId )
  {
    DetachedCriteria criteria = DetachedCriteria.forClass( IfbStoryAmplification.class );
    addPropertyRestriction( criteria, "ifbCampaign.campaignId", campaignId );

    return findByCriteria( criteria );
  }

  public IfbStoryAmplification getByShortUrlKey( String shortUrlKey )
  {
    DetachedCriteria criteria = DetachedCriteria.forClass( IfbStoryAmplification.class );
    criteria.add( Restrictions.eq( "shortUrlKey", shortUrlKey ));
    return findFirstByCriteria( criteria );
  }

  public List<IfbStoryAmplification> getByStory( Story story )
  {
    DetachedCriteria criteria = DetachedCriteria.forClass( IfbStoryAmplification.class );
    criteria.add( Restrictions.eq( "dpStoryId", story.getId() ) );
    return findByCriteria( criteria );
  }

  public List<IfbStoryAmplification> getByAny( Story story, Ad ad, Campaign campaign, String shareType )
  {
    DetachedCriteria criteria = DetachedCriteria.forClass( IfbStoryAmplification.class );
    addPropertyRestriction( criteria, "dpStoryId", story.getId() );
    addPropertyRestriction( criteria, "type", shareType );

    if ( campaign != null ) {
      addPropertyRestriction( criteria, "campaign.campaignId", campaign.getId() );
    }
    else {
      addNullPropertyRestriction( criteria, "campaign.campaignId" );
    }

    return findByCriteria( criteria );
  }

}
