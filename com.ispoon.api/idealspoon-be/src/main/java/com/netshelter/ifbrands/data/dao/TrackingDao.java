package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.api.model.campaign.ObjectTypeEnum;
import com.netshelter.ifbrands.api.model.campaign.TrackingSetEnum;
import com.netshelter.ifbrands.api.model.campaign.TrackingTypeEnum;
import com.netshelter.ifbrands.data.entity.IfbTracking;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * @author Dmitriy T
 */
public class TrackingDao
    extends BaseDao<IfbTracking>
{
    @Override
    protected Class<IfbTracking> getEntityClass()
    {
        return IfbTracking.class;
    }

    @Override
    protected Serializable getIdentifier(IfbTracking entity)
    {
        return entity.getTrackingId();
    }

    @Override
    protected void updateIdentifier(IfbTracking entity, Serializable id)
    {
        entity.setTrackingId((Integer) id);
    }

    public List<IfbTracking> findByAny(
            Integer trackingId,
            Integer objectId,
            ObjectTypeEnum objectType,
            TrackingSetEnum trackingSet,
            TrackingTypeEnum trackingType
    )
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( IfbTracking.class );
        addPropertyRestriction( criteria, "trackingId", trackingId );
        addPropertyRestriction( criteria, "objectId", objectId );
        addPropertyRestriction( criteria, "objectType", (objectType != null?objectType.name():null) );
        addPropertyRestriction( criteria, "trackingSet", (trackingSet != null?trackingSet.name():null) );
        addPropertyRestriction( criteria, "trackingType", (trackingType != null?trackingType.name():null) );
        return findByCriteria( criteria );
    }

    @Override
    public String asString(IfbTracking entity)
    {
        if (entity != null) {
            return entity.toString();
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean theSame(IfbTracking a, IfbTracking b)
    {
        return (a != null) && (b != null) &&
                a.getTrackingId().equals( b.getTrackingId() );
    }
}
