package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.ActionObjectType;
import com.netshelter.ifbrands.api.model.ActionType;

/**
 * @author Dmitriy T
 */
public interface ActionLogService
{
    /**
     * This service method creates an entry into the ifb_action_log table based on the parameters provided
     * @param type - required type of the action
     * @param objectType - optional ActionObjectType
     * @param objectId - optional object id (Integer id)
     * @param userKey - optional user key (Integer id)
     * @param data - optional readable data (2000 chars max)
     */
    public void makeLogEntry(ActionType type, ActionObjectType objectType, Integer objectId, Integer userKey, String data);
}
