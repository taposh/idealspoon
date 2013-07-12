package com.netshelter.ifbrands.api.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.netshelter.ifbrands.api.model.usermanagement.GroupInfo;
import com.netshelter.ifbrands.api.service.UserManagementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.model.user.UserState;
import com.netshelter.ifbrands.api.model.user.UserValue;
import com.netshelter.ifbrands.api.model.user.UserValueList;
import com.netshelter.ifbrands.api.service.UserService;

/**
 * Controller for all user operations.  Some of these are just wrappers for
 * other external systems.
 *
 * @author bgray
 */
@Controller("userController")
@RequestMapping("/user")
public class UserController extends BaseController {

    public static final int USER_KEY_VALUE_LIMIT = 2000;
    public static final String USER_KEY_VALUE_LIMIT_MSG = "LIMIT ERROR";

    @Autowired
    private UserService userService;
    @Autowired
    private UserManagementServices userManagementServices;

    /**
     * Flush cache.
     */
    @RequestMapping(value = "/flush", method = RequestMethod.GET)
    @ResponseBody
    public GenericStatus flushCache() {
        userService.flushCache();
        return GenericStatus.okay(UserService.USER_CACHE + " cache flushed.");
    }

    /**
     * For a set of userKeys, return the current state for each.
     *
     * @param filter Set of keys
     * @return List of state
     */
    @RequestMapping(value = "/state/user:{userKeys}", method = RequestMethod.GET)
    @ResponseBody
    public GenericPayload<Collection<UserState>> getUserStates(@PathVariable("userKeys") String filter) {
        logger.info("/user/state/user:%s", filter);
        List<String> userKeys = null;
        if (!"-".equals(filter)) {
            userKeys = Arrays.asList(filter.split(","));
        }
        List<UserState> userStates = userService.getUser(userKeys);
        logger.debug("...%d found", userStates.size());
        return new GenericPayload<Collection<UserState>>("userStates", userStates);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public UserState createNewUser(@RequestParam("userkey") String userKey,
                                   @RequestParam("brand") Integer brandId) {
        logger.info("/user/create [%s,%s]", userKey, brandId);
        UserState userState = userService.createUser(userKey, brandId);
        logger.debug("...User %d created", userState.getId());
        return userState;
    }

    @RequestMapping(value = "/delete/user:{userKey}", method = RequestMethod.GET)
    @ResponseBody
    public GenericStatus deleteUser(@PathVariable("userKey") String userKey) {
        logger.info("/user/delete/user:%s", userKey);
        boolean success = userService.deleteUser(userKey);
        logger.debug("...User %d deleted", userKey);
        return GenericStatus.successFail(success);
    }

    @RequestMapping(value = "/login/user:{userKey}", method = RequestMethod.GET)
    @ResponseBody
    public UserState userLogin(@PathVariable("userKey") String userKey) {
        logger.info("/user/login/%s", userKey);
        UserState userState = userService.loginUser(userKey);
        logger.debug("...Login updated to %s", userState.getLastLogin());
        return userState;
    }

    @RequestMapping(value = "/value/user:{userKey}/{key}", params = "!value", method = RequestMethod.GET)
    @ResponseBody
    public UserValueList getValue(@PathVariable("userKey") String userKey,
                                  @PathVariable("key") String keyFilter) {
        logger.info("/user/value/user:%s/%s (get)", userKey, keyFilter);
        if ("-".equals(keyFilter)) keyFilter = null;
        List<UserValue> values = userService.getValues(userKey, keyFilter);
        logger.debug("...Found %d values", values.size());
        UserValueList uvl = new UserValueList();
        uvl.setUserKey(userKey);
        uvl.setValues(values);
        return uvl;
    }

    @RequestMapping(value = "/value/user:{userKey}/{key}", params = "value", method = RequestMethod.GET)
    @ResponseBody
    public GenericStatus setValue(@PathVariable("userKey") String userKey,
                                  @PathVariable("key") String keyFilter,
                                  @RequestParam("value") String value) {
        logger.info("/user/value/user:%s/%s [%s]", userKey, keyFilter, value);
        boolean success = false;
        if (!"-".equals(keyFilter) && value != null) {
            if (value.length() < USER_KEY_VALUE_LIMIT) {
                success = userService.setValue(userKey, keyFilter, value);
            } else {
                return GenericStatus.fail(USER_KEY_VALUE_LIMIT_MSG);
            }
        }
        logger.debug("...Set %d values", success ? 1 : 0);
        return GenericStatus.successFail(success);
    }

    @RequestMapping(value = "/groups/all")
    @ResponseBody
    public GenericPayload<List<GroupInfo>> getAllGroups() {
        logger.info("/user/groups/all");

        List<GroupInfo> groupInfoList = userManagementServices.getGroupInfoAll(false);

        return new GenericPayload<List<GroupInfo>>("groupInfoList", groupInfoList);
    }

}
