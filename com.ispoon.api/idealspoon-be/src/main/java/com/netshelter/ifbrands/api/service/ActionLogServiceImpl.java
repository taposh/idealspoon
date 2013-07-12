package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.ActionObjectType;
import com.netshelter.ifbrands.api.model.ActionType;
import com.netshelter.ifbrands.data.dao.ActionLogDao;
import com.netshelter.ifbrands.data.entity.IfbActionLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Dmitriy T
 */
public class ActionLogServiceImpl
    implements ActionLogService
{

    private Logger logger = Logger.getLogger(ActionLogServiceImpl.class);

    @Autowired
    ActionLogDao actionLogDao = null;

    @Override
    /**
     * This service method creates an entry into the ifb_action_log table based on the parameters provided
     * @param type - required type of the action
     * @param objectType - optional ActionObjectType
     * @param objectId - optional object id (Integer id)
     * @param userKey - optional user key (Integer id)
     * @param data - optional readable data (2000 chars max)
     */
    public void makeLogEntry(ActionType type, ActionObjectType objectType, Integer objectId,
                             Integer userKey, String data)
    {
        IfbActionLog logEntry = new IfbActionLog();
        logEntry.setActionType( type.name() );
        if (objectType != null)
        {
            logEntry.setObjectType( objectType.name() );
            logEntry.setObjectId( objectId );
        }
        logEntry.setUserKey( userKey );
        logEntry.setTextValue( data );
        logEntry.setCreateTimestamp( new Date() );

        actionLogDao.save( logEntry );

        // Logging info to log4j output, later to be picked up by SumoLogic
        logger.debug("ActionLog{" +
                             "actionLogId=" + logEntry.getActionLogId() +
                             ", actionType='" + logEntry.getActionType() + '\'' +
                             ", objectType='" + logEntry.getObjectType() + '\'' +
                             ", objectId=" + logEntry.getObjectId() +
                             ", userKey=" + logEntry.getUserKey()  +
                             ", textValue='" + logEntry.getTextValue() + '\'' +
                             ", createTimestamp=" + logEntry.getCreateTimestamp() +
                             '}');

    }
}
