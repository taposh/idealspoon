package com.netshelter.ifbrands.data.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.netshelter.ifbrands.data.entity.IfbAdState;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.data.entity.IfbAd;
import com.netshelter.ifbrands.data.entity.IfbAdStatus;
import com.netshelter.ifbrands.data.entity.IfbAdType;

public class AdDao
        extends BaseDao<IfbAd>
{
    @Autowired
    public AdTypeDao adTypeDao;
    @Autowired
    public AdStatusDao adStatusDao;
    @Autowired
    public AdStateDao adStateDao;

    @Override
    public void customiseCriteria(Criteria criteria)
    {
        criteria.createAlias("ifbAdType", "type", CriteriaSpecification.INNER_JOIN);
        criteria.createAlias("ifbAdStatus", "status", CriteriaSpecification.INNER_JOIN);
        criteria.createAlias("ifbAdState", "state", CriteriaSpecification.INNER_JOIN);
        criteria.createAlias("ifbFeed", "feed", CriteriaSpecification.INNER_JOIN);
    }

    @Override
    public String asString(IfbAd entity)
    {
        return String.format("IfbAd(%d:%s,%s)", entity.getAdId(), entity.getAdKey(), entity.getAdName());
    }

    @Override
    public boolean theSame(IfbAd a, IfbAd b)
    {
        return (a != null) && (b != null) && a.getAdKey().equals(b.getAdKey());
    }

    @Override
    protected Class<IfbAd> getEntityClass()
    {
        return IfbAd.class;
    }

    @Override
    protected Serializable getIdentifier(IfbAd entity)
    {
        return entity.getAdId();
    }

    @Override
    protected void updateIdentifier(IfbAd entity, Serializable id)
    {
        entity.setAdId((Integer) id);
    }

    /**
     * Fetch the list of entities which match the given filters.  Null values are ignored.
     */
    public List<IfbAd> getByAny(Collection<Integer> adIds,
                                Collection<String> adKeys,
                                Collection<Integer> adTypeIds,
                                Collection<Integer> adStatusIds,
                                Collection<Integer> campaignIds)
    {
        DetachedCriteria criteria = getDetachedCriteria();
        addIdRestriction(criteria, adIds);
        addPropertyRestriction(criteria, "adKey", adKeys);
        addPropertyRestriction(criteria, "ifbAdType.adTypeId", adTypeIds);
        addPropertyRestriction(criteria, "ifbAdStatus.adStatusId", adStatusIds);
        addPropertyRestriction(criteria, "ifbCampaign.campaignId", campaignIds);
        return findByCriteria(criteria);
    }

    /**
     * Lots of joins here.  Need to find the Campaign(s), Ad(s) the story is in
     */
    public List<IfbAd> findByStoryIds(Collection<Integer> storyIds, Collection<Integer> adIds, Collection<Integer> adTypeIds,
                                      Collection<Integer> adStatusIds, Collection<Integer> campaignIds, Collection<Integer> campaignTypeIds,
                                      Collection<Integer> campaignStatusIds, Collection<Integer> brandIds)
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(IfbAd.class);
        criteria.createAlias("ifbCampaign", "campaign", CriteriaSpecification.LEFT_JOIN);
        criteria.createAlias("campaign.ifbCampaignType", "campaignType", CriteriaSpecification.LEFT_JOIN);
        criteria.createAlias("campaign.ifbCampaignStatus", "campaignStatus", CriteriaSpecification.LEFT_JOIN);
        criteria.createAlias("feed.ifbFeedStories", "feedStories", CriteriaSpecification.LEFT_JOIN);

        addPropertyRestriction(criteria, "feedStories.dpStoryId", storyIds);
        addPropertyRestriction(criteria, "adId", adIds);
        addPropertyRestriction(criteria, "ifbAdType.adTypeId", adTypeIds);
        addPropertyRestriction(criteria, "ifbAdStatus.adStatusId", adStatusIds);
        addPropertyRestriction(criteria, "campaign.campaignId", campaignIds);
        addPropertyRestriction(criteria, "campaignType.campaignTypeId", campaignTypeIds);
        addPropertyRestriction(criteria, "campaignStatus.campaignStatusId", campaignStatusIds);
        addPropertyRestriction(criteria, "campaign.brandId", brandIds);

        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return findByCriteria(criteria);
    }

    public static class AdTypeDao
            extends BaseDao<IfbAdType>
    {
        public static IfbAdType IP_STORIES, IP_BILLBOARD, IP_TOWER, CUSTOM, IP_STORIES_3, IP_STORIES_6,
            S3S1, S7S3;

        public AdTypeDao()
        {
            super();
            Date now = new Date();

            IP_STORIES = new IfbAdType();
            IP_STORIES.setAdTypeId(1);
            IP_STORIES.setAdTypeName("inPowered Stories");
            IP_STORIES.setCreateTimestamp(now);

            IP_BILLBOARD = new IfbAdType();
            IP_BILLBOARD.setAdTypeId(2);
            IP_BILLBOARD.setAdTypeName("inPowered Billboard");
            IP_BILLBOARD.setCreateTimestamp(now);

            IP_TOWER = new IfbAdType();
            IP_TOWER.setAdTypeId(3);
            IP_TOWER.setAdTypeName("inPowered Tower");
            IP_TOWER.setCreateTimestamp(now);

            CUSTOM = new IfbAdType();
            CUSTOM.setAdTypeId(4);
            CUSTOM.setAdTypeName("Custom");
            CUSTOM.setCreateTimestamp(now);

            IP_STORIES_3 = new IfbAdType();
            IP_STORIES_3.setAdTypeId(5);
            IP_STORIES_3.setAdTypeName("inPowered Stories (3)");
            IP_STORIES_3.setCreateTimestamp(now);

            IP_STORIES_6 = new IfbAdType();
            IP_STORIES_6.setAdTypeId(6);
            IP_STORIES_6.setAdTypeName("inPowered Stories (6)");
            IP_STORIES_6.setCreateTimestamp(now);

            S3S1 = new IfbAdType();
            S3S1.setAdTypeId(7);
            S3S1.setAdTypeName("S3S1");
            S3S1.setCreateTimestamp(now);

            S7S3 = new IfbAdType();
            S7S3.setAdTypeId(8);
            S7S3.setAdTypeName("S7S3");
            S7S3.setCreateTimestamp(now);

        }

        @Override
        public Collection<IfbAdType> getEntityConstants()
        {
            return Arrays.asList(IP_STORIES, IP_BILLBOARD, IP_TOWER, CUSTOM, IP_STORIES_3, IP_STORIES_6, S3S1, S7S3);
        }

        @Override
        public String asString(IfbAdType entity)
        {
            return String.format("IfbAdType(%d:%s)", entity.getAdTypeId(), entity.getAdTypeName());
        }

        @Override
        public boolean theSame(IfbAdType a, IfbAdType b)
        {
            return (a != null) && (b != null) && a.getAdTypeName().equals(b.getAdTypeName());
        }

        @Override
        protected Class<IfbAdType> getEntityClass()
        {
            return IfbAdType.class;
        }

        @Override
        protected Serializable getIdentifier(IfbAdType entity)
        {
            return entity.getAdTypeId();
        }

        @Override
        protected void updateIdentifier(IfbAdType entity, Serializable id)
        {
            entity.setAdTypeId((Integer) id);
        }
    }

    public static class AdStatusDao
            extends BaseDao<IfbAdStatus>
    {
        final public IfbAdStatus ENABLED, DISABLED;

        public AdStatusDao()
        {
            super();
            Date now = new Date();
            ENABLED = new IfbAdStatus();
            ENABLED.setAdStatusId(1);
            ENABLED.setAdStatusName("enabled");
            ENABLED.setCreateTimestamp(now);
            DISABLED = new IfbAdStatus();
            DISABLED.setAdStatusId(2);
            DISABLED.setAdStatusName("disabled");
            DISABLED.setCreateTimestamp(now);
        }

        @Override
        public Collection<IfbAdStatus> getEntityConstants()
        {
            return Arrays.asList(ENABLED, DISABLED);
        }

        @Override
        public String asString(IfbAdStatus entity)
        {
            return String.format("IfbAdStatus(%d:%s)", entity.getAdStatusId(), entity.getAdStatusName());
        }

        @Override
        public boolean theSame(IfbAdStatus a, IfbAdStatus b)
        {
            return (a != null) && (b != null) && a.getAdStatusName().equals(b.getAdStatusName());
        }

        @Override
        protected Class<IfbAdStatus> getEntityClass()
        {
            return IfbAdStatus.class;
        }

        @Override
        protected Serializable getIdentifier(IfbAdStatus entity)
        {
            return entity.getAdStatusId();
        }

        @Override
        protected void updateIdentifier(IfbAdStatus entity, Serializable id)
        {
            entity.setAdStatusId((Integer) id);
        }
    }

    public static class AdStateDao extends BaseDao<IfbAdState>
    {
        final public IfbAdState PENDING, ACTIVE, INACTIVE;

        public AdStateDao()
        {
            super();
            Date now = new Date();
            PENDING = new IfbAdState();
            PENDING.setAdStateId(1);
            PENDING.setAdStateName("Pending");
            PENDING.setCreateTimestamp( now );

            ACTIVE = new IfbAdState();
            ACTIVE.setAdStateId(2);
            ACTIVE.setAdStateName("Active");
            ACTIVE.setCreateTimestamp( now );

            INACTIVE = new IfbAdState();
            INACTIVE.setAdStateId(3);
            INACTIVE.setAdStateName("Inactive");
            INACTIVE.setCreateTimestamp( now );
        }

        @Override
        public Collection<IfbAdState> getEntityConstants()
        {
            return Arrays.asList( PENDING, ACTIVE, INACTIVE );
        }

        @Override
        public String asString( IfbAdState entity )
        {
            return String.format( "IfbAdState(%d:%s)", entity.getAdStateId(), entity.getAdStateName() );
        }

        @Override
        public boolean theSame( IfbAdState a, IfbAdState b )
        {
            return (a != null)&&(b != null)&& a.getAdStateName().equals( b.getAdStateName() );
        }

        @Override
        protected Class<IfbAdState> getEntityClass()
        {
            return IfbAdState.class;
        }

        @Override
        protected Serializable getIdentifier( IfbAdState entity )
        {
            return entity.getAdStateId();
        }

        @Override
        protected void updateIdentifier( IfbAdState entity, Serializable id )
        {
            entity.setAdStateId((Integer) id);
        }
    }
}
