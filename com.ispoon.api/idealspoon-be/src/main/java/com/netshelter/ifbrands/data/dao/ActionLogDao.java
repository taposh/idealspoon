package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.IfbActionLog;

import java.io.Serializable;

/**
 * @author Dmitriy T
 */
public class ActionLogDao
        extends BaseDao<IfbActionLog>
{
    @Override
    protected Class<IfbActionLog> getEntityClass()
    {
        return IfbActionLog.class;
    }

    @Override
    protected Serializable getIdentifier(IfbActionLog entity)
    {
        return entity.getActionLogId();
    }

    @Override
    protected void updateIdentifier(IfbActionLog entity, Serializable id)
    {
        entity.setActionLogId( (Integer)id );
    }

    @Override
    public String asString(IfbActionLog entity)
    {
        if (entity != null) {
            return "IfbActionLog{" +
                    "actionLogId=" + entity.getActionLogId() +
                    ", createTimestamp=" + entity.getCreateTimestamp()+
                    '}';
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean theSame(IfbActionLog a, IfbActionLog b)
    {
        return (a != null) && (b != null) &&
                a.getActionLogId().equals( b.getActionLogId() );
    }
}