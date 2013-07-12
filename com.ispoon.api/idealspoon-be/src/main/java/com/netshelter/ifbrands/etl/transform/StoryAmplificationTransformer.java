package com.netshelter.ifbrands.etl.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.storyamplification.StoryAmplification;
import com.netshelter.ifbrands.api.service.EntityService;
import com.netshelter.ifbrands.data.entity.IfbStoryAmplification;
import com.netshelter.ifbrands.etl.transform.campaign.AdTransformer;

@Component
public class StoryAmplificationTransformer
{
  @Autowired
  EntityService entityService;
  @Autowired
  AdTransformer adTransformer;

  public StoryAmplification transform( IfbStoryAmplification e )
  {
    StoryAmplification p = new StoryAmplification( e.getStoryAmplificationId() );
    p.setId( e.getStoryAmplificationId() );
    p.setShortUrlKey( e.getShortUrlKey() );
    p.setStory( entityService.getStory( e.getDpStoryId() ) );
    p.setUserKey( e.getUserKey() );
    p.setType( e.getType() );
    p.setCreated( new DateTime( e.getCreatedAmplification() ) );
    p.setLastAmplified( new DateTime( e.getLastAmplification() ) );
    
    // A null campaignId is a valid case (old storyAmplifications had no ad associations)
    Integer campaignId = ( e.getIfbCampaign() != null) ? e.getIfbCampaign().getCampaignId() : null; 
    p.setCampaignId( campaignId );
    
    return p;
  }

  public List<StoryAmplification> transform( Collection<IfbStoryAmplification> list )
  {
    List<StoryAmplification> results = new ArrayList<StoryAmplification>( list.size() );
    for ( IfbStoryAmplification e : list ) {
      results.add( transform( e ) );
    }

    return results;
  }
}
