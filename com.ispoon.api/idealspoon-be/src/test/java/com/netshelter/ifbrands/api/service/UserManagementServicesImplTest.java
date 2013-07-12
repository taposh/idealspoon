package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.usermanagement.GroupInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Dmitriy T
 * Date: 2/5/13
 */
public class UserManagementServicesImplTest
    extends BaseServiceTest
{

    @Autowired
    private UserManagementServices userManagementServices;

    @Test
    public void testGetGroupNames() throws Exception
    {
        List<String> groupIds = new ArrayList<String>();
        groupIds.add("1");
        groupIds.add("2");
        groupIds.add("3");
        groupIds.add("4");
        groupIds.add("5");
        groupIds.add("6");
        groupIds.add("7");
        groupIds.add("8");
        groupIds.add("9");
        groupIds.add("10");
        List<GroupInfo> groupInfoList = userManagementServices.getGroupInfo(groupIds);
        for (int i = 0; i < groupInfoList.size(); i++) {
            GroupInfo groupInfo = groupInfoList.get(i);
            System.out.println(groupInfo);
        }
    }

}
