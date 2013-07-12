package com.netshelter.ifbrands.etl.transform.campaign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.campaign.Feed;
import com.netshelter.ifbrands.api.model.campaign.FeedStory;
import com.netshelter.ifbrands.api.model.campaign.FeedStoryStatus;
import com.netshelter.ifbrands.data.entity.IfbFeed;
import com.netshelter.ifbrands.data.entity.IfbFeedStory;
import com.netshelter.ifbrands.data.entity.IfbFeedStoryStatus;
import com.netshelter.ifbrands.publish.PublishService;

@Component
public class FeedTransformer
{
  @Autowired
  private PublishService publishService;

  public Feed transform( IfbFeed e )
  {
    if( e == null ) return null;
    Feed p = new Feed( e.getFeedId() );
    if( Hibernate.isInitialized( e )) {
      p.setKey( e.getFeedKey() );
      p.setName( e.getFeedName() );
      //p.setUrl( e.getFeedUrl() );
      p.setUrl( publishService.getGeneratedFeedUrl( e.getFeedKey() ) );
      p.setLastModified( new DateTime( e.getLastModified() ));
      p.setOrdering( Feed.Ordering.valueOf( e.getOrdering() ));
      if( Hibernate.isInitialized( e.getIfbFeedStories() )) {
        p.setFeedStories( transformStory( e.getIfbFeedStories() ));
      }
    }
    return p;
  }

  public List<Feed> transform( Collection<IfbFeed> list )
  {
    List<Feed> results = new ArrayList<Feed>( list.size() );
    for( IfbFeed e : list ) {
      results.add( transform( e ) );
    }
    return results;
  }

  public FeedStory transformStory( IfbFeedStory e )
  {
    if( e == null ) return null;
    FeedStory p = new FeedStory( e.getFeedStoryId() );
    if( Hibernate.isInitialized( e )) {
      p.setOrdinal( e.getOrdinal() );
      p.setStoryId( e.getDpStoryId() );
      p.setDpStoryId( e.getDpStoryId() );
      p.setCreateDateTime( new DateTime( e.getCreateTimestamp() ));
      if (e.getUpdateTimestamp() != null)
      {
        p.setUpdateTimestamp( new DateTime( e.getUpdateTimestamp() ) );
      }
      if (e.getIgnoreBot() != null)
      {
        p.setIgnoreBot( e.getIgnoreBot() );
      }
      if (e.getUpdateSource() != null)
      {
        p.setUpdateSource( e.getUpdateSource() );
      }

      p.setStatus( transformStoryStatus( e.getIfbFeedStoryStatus() ) );
    }
    return p;
  }

  public List<FeedStory> transformStory( Collection<IfbFeedStory> list )
  {
    List<FeedStory> results = new ArrayList<FeedStory>( list.size() );
    for( IfbFeedStory e : list ) {
      results.add( transformStory( e ) );
    }
    return results;
  }

  public FeedStoryStatus transformStoryStatus( IfbFeedStoryStatus e )
  {
    if( e == null ) return null;
    FeedStoryStatus p = new FeedStoryStatus( e.getFeedStoryStatusId() );
    if( Hibernate.isInitialized( e )) {
      p.setName( e.getFeedStoryStatusName() );
    }
    return p;
  }

  public List<FeedStoryStatus> transformStoryStatus( Collection<IfbFeedStoryStatus> list )
  {
    List<FeedStoryStatus> results = new ArrayList<FeedStoryStatus>( list.size() );
    for( IfbFeedStoryStatus e : list ) {
      results.add( transformStoryStatus( e ) );
    }
    return results;
  }
}
