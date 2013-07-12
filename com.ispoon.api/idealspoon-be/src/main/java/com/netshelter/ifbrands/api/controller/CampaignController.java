package com.netshelter.ifbrands.api.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.netshelter.ifbrands.api.model.usermanagement.GroupInfo;
import com.netshelter.ifbrands.api.service.NotesService;
import com.netshelter.ifbrands.api.service.UserManagementServices;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.campaign.CampaignStatus;
import com.netshelter.ifbrands.api.model.campaign.CampaignType;
import com.netshelter.ifbrands.api.model.user.UserInfo;
import com.netshelter.ifbrands.api.service.CampaignService;
import com.netshelter.ifbrands.api.util.MvcUtils;
import com.netshelter.ifbrands.util.KeyGeneratorUtils.KeyGenerator;

/**
 * Controller for 'campaign' API calls.  A 'campaign' is a container for social and ad analytics that may
 * contain 0-to-n 'ads' (aka Creatives)
 *
 * @author ekrevets
 */
@Controller("campaignController")
@RequestMapping("/campaign")
public class CampaignController
    extends BaseController
{
    @Autowired
    private KeyGenerator keyGenerator = null;
    @Autowired
    private CampaignService campaignService = null;
    @Autowired
    private UserManagementServices userManagementServices = null;

    /**
     * Create new campaign.
     */
    @RequestMapping(value = "/create")
    @ResponseBody
    public Campaign createCampaign(@RequestParam("campaignname") String campaignName,
                                   @RequestParam("userkey") String userKey,
                                   @RequestParam(value = "clientuserkey", defaultValue = "") String clientUserKey,
                                   @RequestParam("brandid") Integer brandId,
                                   @RequestParam("start") LocalDate start,
                                   @RequestParam("stop") LocalDate stop,
                                   @RequestParam(value = "keywords", defaultValue = "") String keywords,
                                   @RequestParam("enabled") Boolean campaignEnabled,
                                   @RequestParam("statusid") Integer campaignStatusId,
                                   @RequestParam("typeid") Integer campaignTypeId,
                                   @RequestParam(value = "feedid", required = false) Integer feedId,
                                   @RequestParam(value = "productname", required = false) String productName,
                                   @RequestParam(value = "productdestination", required = false) String productDestination,
                                   @RequestParam(value = "calltoaction", required = false) String callToAction,
                                   @RequestParam(value = "logolocation", required = false) String logoLocation,
                                   @RequestParam(value = "actionuserkey", required = false) Integer actionUserKey,
                                   @RequestParam(value = "goal", required = false) String goal
    ) {
        logger.info("/campaign/create [%s,userKey:%s,%s,%s,%s,%s,%s,%s,%s,clientUserKey:%s]",
                campaignName, userKey, brandId, start, stop,
                keywords, campaignEnabled, campaignStatusId, campaignTypeId, clientUserKey);

        String key = keyGenerator.generateKey();
        Campaign campaign;
        if (StringUtils.isNotBlank(clientUserKey)) {
            campaign = campaignService.createCampaign(campaignName, userKey, brandId, start,
                    stop, keywords, campaignEnabled,
                    campaignStatusId, campaignTypeId, key, clientUserKey, feedId,
                    productName, productDestination, callToAction, logoLocation, actionUserKey, goal);
        } else {
            campaign = campaignService.createCampaign(campaignName, userKey, brandId, start,
                    stop, keywords, campaignEnabled,
                    campaignStatusId, campaignTypeId, key, feedId,
                    productName, productDestination, callToAction, logoLocation, actionUserKey, goal);
        }
        return campaign;
    }

    /**
     * Delete campaign.
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public GenericStatus deleteCampaign(@PathVariable("id") String filter) {
        logger.info("/campaign/delete/%s", filter);

        Integer campaignId = Integer.parseInt(filter);

        boolean success = campaignService.deleteCampaign(campaignId);

        return GenericStatus.successFail(success);
    }

    @RequestMapping(value = "/{ids}")
    @ResponseBody
    public GenericPayload<Collection<Campaign>> getCampaigns(@PathVariable("ids") String campaignFilter,
                                                             @RequestParam(value = "enabled", required = false) Boolean enabled,
                                                             @RequestParam(value = "brand", required = false) List<Integer> brandIds,
                                                             @RequestParam(value = "key", required = false) List<String> campaignKeys,
                                                             @RequestParam(value = "status", required = false) List<Integer> campaignStatusIds,
                                                             @RequestParam(value = "type", required = false) List<Integer> campaignTypeIds,
                                                             @RequestParam(value = "userkey", required = false) List<String> userKeys,
                                                             @RequestParam(value = "clientuserkey", required = false) List<String> clientUserKeys)
    {
        logger.info("/campaign/%s [%s,%s,%s,%s,%s,%s,clientUserKeys:%s]", campaignFilter, enabled, brandIds, campaignKeys, campaignStatusIds, campaignTypeIds, userKeys, clientUserKeys);
        // Decode filters
        Collection<Integer> campaignIds = MvcUtils.getIdsFromFilter( campaignFilter );
        // Get campaigns
        Collection<Campaign> campaigns = campaignService.getCampaigns( campaignIds, enabled, brandIds, campaignKeys,
                campaignStatusIds, campaignTypeIds, userKeys,
                clientUserKeys );

        // getting a list of group names corresponding to group id / ClientUserKey
        List<String> groupIds = new ArrayList<String>();
        if ( campaigns != null && !campaigns.isEmpty() )
        {
            for (Iterator<Campaign> iterator = campaigns.iterator(); iterator.hasNext(); )
            {
                Campaign next = iterator.next();
                if ( next.getClientUserKey()  != null && !groupIds.contains( next.getClientUserKey() ))
                {
                    groupIds.add( next.getClientUserKey()  );
                }
            }
            Map<String, GroupInfo> groupInfoMap = userManagementServices.getGroupInfoMap( groupIds );
            for (Iterator<Campaign> iterator = campaigns.iterator(); iterator.hasNext(); )
            {
                Campaign next = iterator.next();
                if ( groupInfoMap.containsKey( next.getClientUserKey() ) )
                {
                    next.setClientFullUserName( groupInfoMap.get( next.getClientUserKey() ).getName() );
                }
                else
                {
                    if ( next.getClientUserKey() != null )
                    {
                        next.setClientFullUserName( "N/A" );
                    }
                }
            }
        }

        return new GenericPayload<Collection<Campaign>>( "campaigns", campaigns );
    }

    @RequestMapping(value = "/users")
    @ResponseBody
    public GenericPayload<Collection<UserInfo>> getCampaignUsers(@RequestParam(value = "enabled", required = false) Boolean enabled,
                                                                 @RequestParam(value = "brand", required = false) List<Integer> brandIds,
                                                                 @RequestParam(value = "status", required = false) List<Integer> campaignStatusIds,
                                                                 @RequestParam(value = "type", required = false) List<Integer> campaignTypeIds) {
        logger.info("/campaign/users [%s,%s,%s,%s]", enabled, brandIds, campaignStatusIds, campaignTypeIds);
        // Get campaigns by filter
        Collection<Campaign> campaigns = campaignService.getCampaigns(null, enabled, brandIds, null, campaignStatusIds, campaignTypeIds, null);
        // Create users set
        Set<UserInfo> users = new TreeSet<UserInfo>(new UserInfo.FieldComparator(UserInfo.FieldComparator.Field.FULL_NAME));
        for (Campaign c : campaigns) {
            UserInfo ui = new UserInfo();
            ui.setUserKey(c.getUserKey());
            ui.setFullName(c.getFullUserName());
            users.add(ui);
        }
        return new GenericPayload<Collection<UserInfo>>("users", users);
    }

    @RequestMapping(value = "/clients")
    @ResponseBody
    public GenericPayload<Collection<UserInfo>> getCampaignClientUsers(
            @RequestParam(value = "enabled", required = false) Boolean enabled,
            @RequestParam(value = "brand", required = false) List<Integer> brandIds,
            @RequestParam(value = "status", required = false) List<Integer> campaignStatusIds,
            @RequestParam(value = "type", required = false) List<Integer> campaignTypeIds) {
        logger.info("/campaign/clients [%s,%s,%s,%s]", enabled, brandIds, campaignStatusIds, campaignTypeIds);
        // Get campaigns by filter
        Collection<Campaign> campaigns = campaignService.getCampaigns(null, enabled, brandIds, null,
                campaignStatusIds, campaignTypeIds, null);
        // Create users set
        Set<UserInfo> users = new TreeSet<UserInfo>(new UserInfo.FieldComparator(UserInfo.FieldComparator.Field.FULL_NAME));
        for (Campaign c : campaigns) {
            UserInfo ui = new UserInfo();
            if (StringUtils.isNotBlank(c.getClientUserKey())) {
                ui.setUserKey(c.getClientUserKey());
                ui.setFullName(c.getClientFullUserName());
                users.add(ui);
            }
        }
        return new GenericPayload<Collection<UserInfo>>("users", users);
    }

    @RequestMapping(value = "/groups")
    @ResponseBody
    public GenericPayload<Collection<UserInfo>> getCampaignGroups(
            @RequestParam(value = "enabled", required = false) Boolean enabled,
            @RequestParam(value = "brand", required = false) List<Integer> brandIds,
            @RequestParam(value = "status", required = false) List<Integer> campaignStatusIds,
            @RequestParam(value = "type", required = false) List<Integer> campaignTypeIds) {
        logger.info("/campaign/groups [%s,%s,%s,%s]", enabled, brandIds, campaignStatusIds, campaignTypeIds);
        // Get campaigns by filter
        Collection<Campaign> campaigns = campaignService.getCampaigns(null, enabled, brandIds, null,
                campaignStatusIds, campaignTypeIds, null);
        // Create users set
        Set<UserInfo> users = new HashSet<UserInfo>();
        Set<UserInfo> response = new TreeSet<UserInfo>(new UserInfo.FieldComparator(UserInfo.FieldComparator.Field.FULL_NAME));
        for (Campaign c : campaigns) {
            if (StringUtils.isNotBlank(c.getClientUserKey())) {
                UserInfo ui = new UserInfo();
                // Instead of fetching group names one by one inside transformer we'll do it later
                // using this call: http://usermanagerbe.n2idev.netshelter.net/n2ixmr.php?rid=1&an=inPoweredForBrands&fi=4&gid=3,4&fmt=2
                ui.setUserKey(c.getClientUserKey());  // in this case it will be a group id (string) not client key
                if (!users.contains(ui))
                {
                    users.add(ui);
                }
            }
        }
        Collection<String> groupIds = new ArrayList<String>();
        for (Iterator<UserInfo> iterator = users.iterator(); iterator.hasNext(); ) {
            UserInfo next = iterator.next();
            groupIds.add(next.getUserKey());
        }
        Map<String, GroupInfo> groupInfoMap = userManagementServices.getGroupInfoMap(groupIds);
        for (Iterator<UserInfo> iterator = users.iterator(); iterator.hasNext(); ) {
            UserInfo next = iterator.next();
            if (groupInfoMap.containsKey(next.getUserKey())) {
                UserInfo ui = new UserInfo();
                ui.setFullName(groupInfoMap.get(next.getUserKey()).getName());
                ui.setUserKey(next.getUserKey());
                response.add(ui);
            }
            else
            {
                // we are not adding any groups that were not found using userManagementServices call...
            }
        }

        return new GenericPayload<Collection<UserInfo>>("users", response);
    }

    /**
     * Update existing campaign.
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Campaign updateCampaign(@RequestParam(value = "campaignid") Integer campaignId,
                                   @RequestParam(value = "campaignname", required = false) String campaignName,
                                   @RequestParam(value = "userkey", required = false) String userKey,
                                   @RequestParam(value = "clientuserkey", required = false) String clientuserkey,
                                   @RequestParam(value = "brandid", required = false) Integer brandId,
                                   @RequestParam(value = "start", required = false) LocalDate start,
                                   @RequestParam(value = "stop", required = false) LocalDate stop,
                                   @RequestParam(value = "keywords", required = false) String keywords,
                                   @RequestParam(value = "enabled", required = false) Boolean campaignEnabled,
                                   @RequestParam(value = "statusid", required = false) Integer campaignStatusId,
                                   @RequestParam(value = "typeid", required = false) Integer campaignTypeId,
                                   @RequestParam(value = "feedid", required = false) Integer feedId,
                                   @RequestParam(value = "productname", required = false) String productName,
                                   @RequestParam(value = "productdestination", required = false) String productDestination,
                                   @RequestParam(value = "calltoaction", required = false) String callToAction,
                                   @RequestParam(value = "logolocation", required = false) String logoLocation,
                                   @RequestParam(value = "actionuserkey", required = false) Integer actionUserKey,
                                   @RequestParam(value = "goal", required = false) String goal
    ) {
        logger.info("/campaign/update [%s,%s,%s,%s,%s,%s,%s,%s,%s,%s]",
                campaignId, campaignName, userKey, brandId, start, stop,
                keywords, campaignEnabled, campaignStatusId, campaignTypeId);

        Campaign campaign = campaignService.updateCampaign(campaignId, campaignName, userKey, brandId,
                start, stop, keywords, campaignEnabled,
                campaignStatusId, campaignTypeId, clientuserkey, feedId,
                productName, productDestination, callToAction, logoLocation, actionUserKey, goal);

        return campaign;
    }


    /**
     * Create new campaign status.
     *
     * @param name
     * @return GenericStatus Returns created CampaignStatus as a String.
     */
    @RequestMapping(value = "/status/create")
    @ResponseBody
    public GenericStatus createNewStatus(@RequestParam("name") String name) {
        logger.info("/campaign/status/create [%s]", name);
        CampaignStatus cs = campaignService.createCampaignStatus(name);

        return GenericStatus.okay(cs.toString());
    }

    /**
     * Delete campaign status.
     */
    @RequestMapping(value = "/status/delete/{id}")
    @ResponseBody
    public GenericStatus deleteStatus(@PathVariable("id") String filter) {
        logger.info("/campaign/status/delete/%s", filter);

        Integer id = Integer.parseInt(filter);

        boolean success = campaignService.deleteCampaignStatus(id);

        return GenericStatus.successFail(success);
    }

    @RequestMapping(value = "/status/{ids}")
    @ResponseBody
    public GenericPayload<Collection<CampaignStatus>> getStatuses(@PathVariable("id") String filter) {
        logger.info("/campaign/status/%s", filter);
        Collection<Integer> ids = MvcUtils.getIdsFromFilter(filter);
        Collection<CampaignStatus> statuses = campaignService.getCampaignStatuses(ids);
        return new GenericPayload<Collection<CampaignStatus>>("statuses", statuses);
    }

    /**
     * Create new campaign type.
     */
    @RequestMapping(value = "/type/create")
    @ResponseBody
    public GenericStatus createNewType(@RequestParam("name") String filter) {
        logger.info("/campaign/type/create [%s]", filter);
        CampaignType ct = campaignService.createCampaignType(filter);

        return GenericStatus.okay(ct.toString());
    }

    /**
     * Delete campaign type.
     */
    @RequestMapping(value = "/type/delete/{id}")
    @ResponseBody
    public GenericStatus deleteType(@PathVariable("id") String filter) {
        logger.info("/campaign/type/delete/%s", filter);

        Integer id = Integer.parseInt(filter);

        boolean success = campaignService.deleteCampaignType(id);

        return GenericStatus.successFail(success);
    }

    @RequestMapping(value = "/type/{ids}")
    @ResponseBody
    public GenericPayload<Collection<CampaignType>> getTypes(@PathVariable("ids") String filter) {
        logger.info("/campaign/type/%s", filter);
        Collection<Integer> ids = MvcUtils.getIdsFromFilter(filter);
        Collection<CampaignType> types = campaignService.getCampaignTypes(ids);
        return new GenericPayload<Collection<CampaignType>>("types", types);
    }
}
