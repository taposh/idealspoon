package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.IfbAdSize;
import com.netshelter.ifbrands.data.entity.IfbFeed;

import java.io.Serializable;

/**
 * User: Dmitriy T
 */
public class AdSizeDao
    extends BaseDao<IfbAdSize>
{
    @Override
    protected Class<IfbAdSize> getEntityClass()
    {
        return IfbAdSize.class;
    }

    @Override
    protected Serializable getIdentifier(IfbAdSize entity)
    {
        return entity.getAdSizeId();
    }

    @Override
    protected void updateIdentifier(IfbAdSize entity, Serializable id)
    {
        entity.setAdSizeId( (Integer)id );
    }

    @Override
    public String asString(IfbAdSize entity)
    {
        if (entity != null) {
            return "IfbAdSize{" +
                    "adSizeId=" + entity.getAdSizeId() +
                    ", adSizeName='" + entity.getAdSizeName() + '\'' +
                    ", width=" + entity.getWidth() +
                    ", height=" + entity.getHeight() +
                    ", createTimestamp=" + entity.getCreateTimestamp()+
                    '}';
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean theSame(IfbAdSize a, IfbAdSize b)
    {
        return (a != null) && (b != null) &&
                a.getAdSizeId().equals( b.getAdSizeId() );
    }
}
