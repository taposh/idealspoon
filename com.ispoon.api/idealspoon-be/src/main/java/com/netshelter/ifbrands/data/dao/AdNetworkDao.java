package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.IfbAdNetwork;
import com.netshelter.ifbrands.data.entity.IfbAdNetwork;

import java.io.Serializable;

/**
 * @author Dmitriy T
 */
public class AdNetworkDao
    extends BaseDao<IfbAdNetwork>
{
    @Override
    protected Class<IfbAdNetwork> getEntityClass()
    {
        return IfbAdNetwork.class;
    }

    @Override
    protected Serializable getIdentifier(IfbAdNetwork entity)
    {
        return entity.getAdNetworkId();
    }

    @Override
    protected void updateIdentifier(IfbAdNetwork entity, Serializable id)
    {
        entity.setAdNetworkId( (Integer)id );
    }

    @Override
    public String asString(IfbAdNetwork entity)
    {
        if (entity != null) {
            return "IfbAdNetwork{" +
                    "adNetworkId=" + entity.getAdNetworkId() +
                    ", adNetworkName='" + entity.getAdNetworkName() + '\'' +
                    ", createTimestamp=" + entity.getCreateTimestamp()+
                    '}';
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean theSame(IfbAdNetwork a, IfbAdNetwork b)
    {
        return (a != null) && (b != null) &&
                a.getAdNetworkId().equals( b.getAdNetworkId() );
    }
}
