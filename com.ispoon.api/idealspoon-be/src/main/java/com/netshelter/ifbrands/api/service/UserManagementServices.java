package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.usermanagement.GroupInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User: Dmitriy T
 * Date: 2/5/13
 */
public interface UserManagementServices
{

    public static final String CACHE_NAME = "ifb.usermanagement";

    public List<GroupInfo>              getGroupInfoAll(    boolean ignoreCache);
    public List<GroupInfo>              getGroupInfo(       Collection<String> groupIds);
    public Map<String, GroupInfo>       getGroupInfoMap(    Collection<String> groupIds);

}
