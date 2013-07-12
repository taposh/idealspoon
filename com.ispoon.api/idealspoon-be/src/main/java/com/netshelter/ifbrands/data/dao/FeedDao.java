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

import com.netshelter.ifbrands.data.entity.IfbFeed;
import com.netshelter.ifbrands.data.entity.IfbFeedStory;
import com.netshelter.ifbrands.data.entity.IfbFeedStoryStatus;
import com.netshelter.ifbrands.util.MoreCollections;

/**
 * FeedDao
 */
public class FeedDao extends BaseDao<IfbFeed>
{
  @Autowired
  public FeedStoryDao feedStoryDao;
  @Autowired
  public FeedStoryStatusDao feedStoryStatusDao;

  @Override
  protected void customiseCriteria( Criteria criteria )
  {
    criteria.createAlias( "ifbFeedStories", "story", CriteriaSpecification.LEFT_JOIN );
    criteria.createAlias( "story.ifbFeedStoryStatus", "status", CriteriaSpecification.LEFT_JOIN );
    criteria.setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY );
  }

  @Override
  public String asString( IfbFeed entity )
  {
    return String.format( "IfbFeed(%d:%s,%s)", entity.getFeedId(), entity.getFeedKey(), entity.getFeedName() );
  }

  @Override
  public boolean theSame( IfbFeed a, IfbFeed b )
  {
    return (a != null) && (b != null) && (a.getFeedKey().equals( b.getFeedKey() ));
  }

  @Override
  protected Class<IfbFeed> getEntityClass()
  {
    return IfbFeed.class;
  }

  @Override
  protected Serializable getIdentifier( IfbFeed entity )
  {
    return entity.getFeedId();
  }

  @Override
  protected void updateIdentifier( IfbFeed entity, Serializable id )
  {
    entity.setFeedId( (Integer)id );
  }

  /**
   * Fetch the list of entities which match the given filters.  Null values are ignored.
   */
  public List<IfbFeed> getByAny( Collection<Integer> ids, String feedKey )
  {
    DetachedCriteria criteria = getDetachedCriteria();
    addIdRestriction( criteria, ids );
    addPropertyRestriction( criteria, "feedKey", feedKey );
    return findByCriteria( criteria );
  }



  /**
   * FeedStoryDao
   */
  public static class FeedStoryDao extends BaseDao<IfbFeedStory>
  {
    @Override
    protected void customiseCriteria( Criteria criteria )
    {
      criteria.createAlias( "ifbFeedStoryStatus", "status", CriteriaSpecification.INNER_JOIN );
    }

    @Override
    public String asString( IfbFeedStory entity )
    {
      return String.format( "IfbFeedStory(%d:%d,%d,%s)", entity.getFeedStoryId(), entity.getIfbFeed()
          .getFeedId(), entity.getDpStoryId(), entity.getIfbFeedStoryStatus().getFeedStoryStatusName() );
    }

    @Override
    public boolean theSame( IfbFeedStory a, IfbFeedStory b )
    {
      return (a != null) && (b != null) && a.getIfbFeed().getFeedId().equals( b.getIfbFeed().getFeedId() )
          && a.getDpStoryId() == b.getDpStoryId();
    }

    @Override
    protected Class<IfbFeedStory> getEntityClass()
    {
      return IfbFeedStory.class;
    }

    @Override
    protected Serializable getIdentifier( IfbFeedStory entity )
    {
      return entity.getFeedStoryId();
    }

    @Override
    protected void updateIdentifier( IfbFeedStory entity, Serializable id )
    {
      entity.setFeedStoryId( (Integer)id );
    }

    /**
     * Fetch the list of entities which match the given filters.  Null values are ignored.
     */
   public List<IfbFeedStory> getByAny( Integer feedId, Integer dpStoryId, Collection<Integer> feedStoryStatusIds )
   {
     DetachedCriteria criteria = getDetachedCriteria();
     addPropertyRestriction( criteria, "dpStoryId", dpStoryId );
     addPropertyRestriction( criteria, "ifbFeed.feedId", feedId );
     addPropertyRestriction( criteria, "ifbFeedStoryStatus.feedStoryStatusId", feedStoryStatusIds );
     return findByCriteria( criteria );
   }

    public List<IfbFeedStory> getByFeedId( Collection<Integer> feedIds )
    {
      DetachedCriteria criteria = getDetachedCriteria();
      addPropertyRestriction( criteria, "ifbFeed.feedId", feedIds );
      return findByCriteria( criteria );
    }

   public IfbFeedStory getFirstByAny( Integer feedId, Integer dpStoryId, Collection<Integer> feedStoryStatusIds )
   {
     return MoreCollections.firstOrNull( getByAny( feedId, dpStoryId, feedStoryStatusIds ));
   }

  }



  /**
   * FeedStoryStatusDao
   */
  public static class FeedStoryStatusDao extends BaseDao<IfbFeedStoryStatus>
  {
    final public IfbFeedStoryStatus ACTIVE, INACTIVE;

    public FeedStoryStatusDao()
    {
      super();
      Date now = new Date();
      ACTIVE = new IfbFeedStoryStatus();
      ACTIVE.setFeedStoryStatusId( 1 );
      ACTIVE.setFeedStoryStatusName( "Active" );
      ACTIVE.setCreateTimestamp( now );
      INACTIVE = new IfbFeedStoryStatus();
      INACTIVE.setFeedStoryStatusId( 2 );
      INACTIVE.setFeedStoryStatusName( "Inactive" );
      INACTIVE.setCreateTimestamp( now );
    }

    @Override
    public Collection<IfbFeedStoryStatus> getEntityConstants()
    {
      return Arrays.asList( ACTIVE, INACTIVE );
    }

    @Override
    public String asString( IfbFeedStoryStatus entity )
    {
      return String.format( "IfbFeedStoryStatus(%d:%s)", entity.getFeedStoryStatusId(),
                            entity.getFeedStoryStatusName() );
    }

    @Override
    public boolean theSame( IfbFeedStoryStatus a, IfbFeedStoryStatus b )
    {
      return (a != null) && (b != null) && a.getFeedStoryStatusName().equals( b.getFeedStoryStatusName() );
    }

    @Override
    protected Class<IfbFeedStoryStatus> getEntityClass()
    {
      return IfbFeedStoryStatus.class;
    }

    @Override
    protected Serializable getIdentifier( IfbFeedStoryStatus entity )
    {
      return entity.getFeedStoryStatusId();
    }

    @Override
    protected void updateIdentifier( IfbFeedStoryStatus entity, Serializable id )
    {
      entity.setFeedStoryStatusId( (Integer)id );
    }
  }
}
