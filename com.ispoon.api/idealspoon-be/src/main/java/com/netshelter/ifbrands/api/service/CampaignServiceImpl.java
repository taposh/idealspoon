package com.netshelter.ifbrands.api.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.netshelter.ifbrands.api.model.ActionObjectType;
import com.netshelter.ifbrands.api.model.ActionType;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.campaign.CampaignStatus;
import com.netshelter.ifbrands.api.model.campaign.CampaignType;
import com.netshelter.ifbrands.api.model.influence.Influence;
import com.netshelter.ifbrands.data.dao.CampaignDao;
import com.netshelter.ifbrands.data.dao.CampaignDao.CampaignStatusDao;
import com.netshelter.ifbrands.data.dao.CampaignDao.CampaignTypeDao;
import com.netshelter.ifbrands.data.entity.IfbCampaign;
import com.netshelter.ifbrands.data.entity.IfbCampaignStatus;
import com.netshelter.ifbrands.data.entity.IfbCampaignType;
import com.netshelter.ifbrands.etl.transform.campaign.CampaignTransformer;
import org.springframework.transaction.annotation.Transactional;

public class CampaignServiceImpl
        implements CampaignService
{

    @Autowired
    CampaignDao campaignDao = null;
    @Autowired
    CampaignStatusDao campaignStatusDao = null;
    @Autowired
    CampaignTypeDao campaignTypeDao = null;
    @Autowired
    CampaignTransformer campaignTransformer = null;
    @Autowired
    ActionLogService actionLogService = null;
    @Autowired
    NotesService notesService = null;

    @Override
    public Campaign createCampaign(String campaignName, String userKey, Integer brandId,
                                   LocalDate startDate, LocalDate stopDate, String keywords,
                                   Boolean campaignEnabled, Integer campaignStatusId,
                                   Integer campaignTypeId, String campaignKey,
                                   Integer feedId,String productName,
                                   String productDestination,
                                   String callToAction,
                                   String logoLocation,
                                   Integer actionUserKey,
                                   String goal)
    {
        IfbCampaign campaign = new IfbCampaign();
        campaign.setCampaignName(campaignName);
        campaign.setUserKey(userKey);
        campaign.setDpBrandId(brandId);
        campaign.setStartDate(startDate.toDate());
        campaign.setStopDate(stopDate.toDate());
        campaign.setKeywords(keywords);
        campaign.setCampaignEnabled(campaignEnabled);
        campaign.setIfbCampaignStatus(campaignStatusDao.getProxyById(campaignStatusId));
        campaign.setIfbCampaignType(campaignTypeDao.getProxyById(campaignTypeId));
        campaign.setCreateTimestamp(new Date());
        campaign.setCampaignKey(campaignKey);
        campaign.setFeedId(feedId);
        campaign.setProductName(productName);
        campaign.setProductDestination(productDestination);
        campaign.setCallToAction(callToAction);
        campaign.setLogoLocation(logoLocation);
        campaign.setGoal(goal);

        campaign = campaignDao.save(campaign);

        if (campaign != null && actionUserKey != null)
        {
            notesService.create(
                    actionUserKey,
                    campaign.getCampaignId(),
                    NotesService.NOTE_TEMPLATE_CAMPAIGN_CREATED
            );
        }

        return getCampaign(campaign.getCampaignId());
    }

    @Override
    public Campaign createCampaign(String campaignName, String userKey, Integer brandId,
                                   LocalDate startDate, LocalDate stopDate, String keywords,
                                   Boolean campaignEnabled, Integer campaignStatusId,
                                   Integer campaignTypeId, String campaignKey, String clientUserKey,
                                   Integer feedId,String productName,
                                   String productDestination,
                                   String callToAction,
                                   String logoLocation,
                                   Integer actionUserKey,
                                   String goal)
    {
        IfbCampaign campaign = new IfbCampaign();
        campaign.setClientUserKey(clientUserKey);
        campaign.setCampaignName(campaignName);
        campaign.setUserKey(userKey);
        campaign.setDpBrandId(brandId);
        campaign.setStartDate(startDate.toDate());
        campaign.setStopDate(stopDate.toDate());
        campaign.setKeywords(keywords);
        campaign.setCampaignEnabled(campaignEnabled);
        campaign.setIfbCampaignStatus(campaignStatusDao.getProxyById(campaignStatusId));
        campaign.setIfbCampaignType(campaignTypeDao.getProxyById(campaignTypeId));
        campaign.setCreateTimestamp(new Date());
        campaign.setCampaignKey(campaignKey);
        campaign.setFeedId(feedId);
        campaign.setProductName(productName);
        campaign.setProductDestination(productDestination);
        campaign.setCallToAction(callToAction);
        campaign.setLogoLocation(logoLocation);
        campaign.setGoal(goal);

        campaign = campaignDao.save(campaign);

        if (campaign != null && actionUserKey != null)
        {
            notesService.create(
                    actionUserKey,
                    campaign.getCampaignId(),
                    NotesService.NOTE_TEMPLATE_CAMPAIGN_CREATED
            );
        }

        return getCampaign(campaign.getCampaignId());
    }

    @Override
    public boolean deleteCampaign(Integer campaignId)
    {
        boolean success = true;
        try
        {
            IfbCampaign e = campaignDao.getById(campaignId);
            campaignDao.delete(e);
        }
        catch (Exception x)
        {
            success = false;
        }
        return success;
    }

    @Override
    public Campaign getCampaign(Integer campaignId)
    {
        return campaignTransformer.transform(campaignDao.getById(campaignId));
    }

    @Override
//  @CollectionCache( cacheName=CAMPAIGN_CACHE, keyPrefix="campaign", keyField="campaignId" )
    public Collection<Campaign> getCampaigns(Collection<Integer> ids)
    {
        List<IfbCampaign> list = (ids == null) ? campaignDao.getAll() : campaignDao.getByIds(ids);
        return campaignTransformer.transform(list);
    }

    @Override
    public Collection<Campaign> getCampaigns(Collection<Integer> campaignIds,
                                             Boolean enabled,
                                             Collection<Integer> brandIds,
                                             Collection<String> campaignKeys,
                                             Collection<Integer> campaignStatusIds,
                                             Collection<Integer> campaignTypeIds,
                                             Collection<String> userKeys)
    {
        return getCampaigns(campaignIds, enabled, brandIds, campaignKeys, campaignStatusIds, campaignTypeIds, userKeys,
                            null);
    }

    public Collection<Campaign> getCampaigns(Collection<Integer> campaignIds,
                                             Boolean enabled,
                                             Collection<Integer> brandIds,
                                             Collection<String> campaignKeys,
                                             Collection<Integer> campaignStatusIds,
                                             Collection<Integer> campaignTypeIds,
                                             Collection<String> userKeys,
                                             Collection<String> clientUserKeys)
    {
        Collection<IfbCampaign> campaigns = campaignDao.findByAny(campaignIds, enabled,
                                                                  brandIds, campaignKeys,
                                                                  campaignStatusIds,
                                                                  campaignTypeIds, userKeys, clientUserKeys);
        return campaignTransformer.transform(campaigns);
    }

    @Override
    public Campaign updateCampaign(Integer campaignId, String campaignName, String userKey,
                                   Integer dpBrandId, LocalDate startDate, LocalDate stopDate,
                                   String keywords, Boolean campaignEnabled,
                                   Integer campaignStatusId, Integer campaignTypeId,
                                   Integer feedId,
                                   String productName,
                                   String productDestination,
                                   String callToAction,
                                   String logoLocation,
                                   Integer actionUserKey,
                                   String goal)
    {
        IfbCampaign e = campaignDao.getById(campaignId);
        if (e == null) throw new IllegalArgumentException("Cannot find campaign " + campaignId);

        // Only update the fields that aren't null
        if (campaignName != null) e.setCampaignName(campaignName);
        if (dpBrandId != null) e.setDpBrandId(dpBrandId);
        if (userKey != null) e.setUserKey(userKey);
        if (startDate != null) e.setStartDate(startDate.toDate());
        if (stopDate != null) e.setStopDate(stopDate.toDate());
        if (keywords != null) e.setKeywords(keywords);
        if (campaignEnabled != null) e.setCampaignEnabled(campaignEnabled);
        if (productName != null) e.setProductName(productName);
        if (productDestination != null) e.setProductDestination(productDestination);
        if (callToAction != null) e.setCallToAction(callToAction);
        if (logoLocation != null) e.setLogoLocation(logoLocation);
        if (goal != null) e.setGoal(goal);
        if (campaignStatusId != null)
        {
            actionLogService.makeLogEntry(
                    ActionType.CAMPAIGN_STATUS_CHANGE,
                    ActionObjectType.CAMPAIGN,
                    campaignId,
                    null,
                    "original status: " + e.getIfbCampaignStatus().getCampaignStatusId() + " new status: " + campaignStatusId
            );
            e.setIfbCampaignStatus(campaignStatusDao.getById(campaignStatusId));
        }
        if (campaignTypeId != null) e.setIfbCampaignType(campaignTypeDao.getProxyById(campaignTypeId));
        if (feedId != null)
        {
            if (feedId >=0)
            {
                e.setFeedId(feedId);
            }
            else
            {
                e.setFeedId(null);
            }
        }

        // Update and return
        campaignDao.update(e);

        // Valid campaign and status changed ?
        if (e != null && campaignStatusId != null && actionUserKey != null)
        {
            notesService.create(
                    actionUserKey,
                    e.getCampaignId(),
                    String.format(NotesService.NOTE_TEMPLATE_CAMPAIGN_STATUS_CHANGED,
                                  e.getIfbCampaignStatus().getCampaignStatusName())
            );
        }

        return getCampaign(campaignId);
    }

    @Override
    @Transactional
    public Campaign updateCampaign(Integer campaignId, String campaignName, String userKey,
                                   Integer dpBrandId, LocalDate startDate, LocalDate stopDate,
                                   String keywords, Boolean campaignEnabled,
                                   Integer campaignStatusId, Integer campaignTypeId, String clientUserKey,
                                   Integer feedId,
                                   String productName,
                                   String productDestination,
                                   String callToAction,
                                   String logoLocation,
                                   Integer actionUserKey,
                                   String goal)
    {
        IfbCampaign e = campaignDao.getById(campaignId);
        if (e == null) throw new IllegalArgumentException("Cannot find campaign " + campaignId);

        // Only update the fields that aren't null
        if (campaignName != null) e.setCampaignName(campaignName);
        if (dpBrandId != null) e.setDpBrandId(dpBrandId);
        if (userKey != null) e.setUserKey(userKey);
        if (startDate != null) e.setStartDate(startDate.toDate());
        if (stopDate != null) e.setStopDate(stopDate.toDate());
        if (keywords != null) e.setKeywords(keywords);
        if (campaignEnabled != null) e.setCampaignEnabled(campaignEnabled);
        if (productName != null) e.setProductName(productName);
        if (productDestination != null) e.setProductDestination(productDestination);
        if (callToAction != null) e.setCallToAction(callToAction);
        if (logoLocation != null) e.setLogoLocation(logoLocation);
        if (goal != null) e.setGoal(goal);
        if (campaignStatusId != null)
        {
            actionLogService.makeLogEntry(
                    ActionType.CAMPAIGN_STATUS_CHANGE,
                    ActionObjectType.CAMPAIGN,
                    campaignId,
                    null,
                    "original status: " + e.getIfbCampaignStatus().getCampaignStatusId() + " new status: " + campaignStatusId
            );
            e.setIfbCampaignStatus(campaignStatusDao.getById(campaignStatusId));
        }
        if (campaignTypeId != null) e.setIfbCampaignType(campaignTypeDao.getProxyById(campaignTypeId));
        if (StringUtils.isNotBlank(clientUserKey)) e.setClientUserKey(clientUserKey);
        if (feedId != null)
        {
            if (feedId >=0)
            {
                e.setFeedId(feedId);
            }
            else
            {
                e.setFeedId(null);
            }
        }
        // Update and return
        campaignDao.update(e);

        // Valid campaign and status changed ?
        if (e != null && campaignStatusId != null && actionUserKey != null)
        {
            notesService.create(
                    actionUserKey,
                    e.getCampaignId(),
                    String.format(NotesService.NOTE_TEMPLATE_CAMPAIGN_STATUS_CHANGED,
                                  e.getIfbCampaignStatus().getCampaignStatusName())
            );
        }

        return getCampaign(campaignId);
    }

    @Override
    public Collection<String> getCampaignKeys(Collection<Integer> campaignIds)
    {
        Collection<Campaign> campaigns = getCampaigns(campaignIds);
        if (campaigns.size() != campaignIds.size())
        {
            throw new IllegalArgumentException("One or more specified CampaignId's could not be found.");
        }
        Collection<String> campaignKeys = new ArrayList<String>();
        for (Campaign c : campaigns)
        {
            campaignKeys.add(c.getCampaignKey());
        }
        return campaignKeys;
    }


    @Override
    public CampaignStatus createCampaignStatus(String name)
    {
        IfbCampaignStatus e = new IfbCampaignStatus();
        e.setCampaignStatusName(name);
        e.setCreateTimestamp(new Date());

        campaignDao.campaignStatusDao.save(e);

        return campaignTransformer.transformCampaignStatus(e);
    }

    @Override
    public boolean deleteCampaignStatus(Integer id)
    {
        boolean success = true;
        try
        {
            IfbCampaignStatus e = campaignDao.campaignStatusDao.getById(id);
            campaignDao.campaignStatusDao.delete(e);
        }
        catch (Exception x)
        {
            success = false;
        }
        return success;
    }

    @Override
    public CampaignStatus getCampaignStatus(Integer id)
    {
        IfbCampaignStatus e = campaignStatusDao.getById(id);
        return campaignTransformer.transformCampaignStatus(e);
    }

    @Override
    public Collection<CampaignStatus> getCampaignStatuses(Collection<Integer> ids)
    {
        return campaignTransformer.transformCampaignStatus(ids == null ? campaignStatusDao.getAll() :
                                                                   campaignStatusDao.getByIds(ids));
    }


    @Override
    public CampaignType createCampaignType(String name)
    {
        IfbCampaignType e = new IfbCampaignType();
        e.setCampaignTypeName(name);
        e.setCreateTimestamp(new Date());

        campaignDao.campaignTypeDao.save(e);

        return campaignTransformer.transformCampaignType(e);
    }

    @Override
    public boolean deleteCampaignType(Integer id)
    {
        boolean success = true;
        try
        {
            IfbCampaignType e = campaignDao.campaignTypeDao.getById(id);
            campaignDao.campaignTypeDao.delete(e);
        }
        catch (Exception x)
        {
            success = false;
        }
        return success;
    }

    @Override
    public CampaignType getCampaignType(Integer id)
    {
        IfbCampaignType e = campaignTypeDao.getById(id);
        return campaignTransformer.transformCampaignType(e);
    }

    @Override
    public Collection<CampaignType> getCampaignTypes(Collection<Integer> ids)
    {
        return campaignTransformer.transformCampaignType(ids == null ? campaignTypeDao.getAll() :
                                                                 campaignTypeDao.getByIds(ids));
    }

    public boolean isDemo(Campaign campaign)
    {
        return campaignTypeDao.DEMO.getCampaignTypeId().equals(campaign.getCampaignType().getId());
    }

    private static Random R = new Random();

    public void makeFake(double min, double max, double avg, double lift, Influence organic, Influence paid, Influence total)
    {
        // We want maximum lift at minimum organic
        double point = 1 + organic.getPeopleInfluenced();
        double goal = avg * lift / 100;                                   // Goal lift per point
        double norm = (point - min - min) / (max);                              // Normalized point 0-1
        double norm10 = 10 - 10 * norm;                                       // Normalized 10-0
        double adjust = 2 * norm10 * goal / 10;                                 // Adjustment
        double wobble = 0.8 + R.nextDouble() * 0.2;                       // Random wobble +/-20%
        double increase = adjust * wobble;                                  // Actual lift on this point
        paid.setPeopleInfluenced((int) increase);
        paid.setInfluencers((int) (5 + increase * organic.getInfluencers() / point));
        paid.setUniqueCount(3);
        total.setUniqueCount(organic.getUniqueCount() + paid.getUniqueCount());
        total.setInfluencers(organic.getInfluencers() + paid.getInfluencers());
        total.setPeopleInfluenced(organic.getPeopleInfluenced() + paid.getPeopleInfluenced());

    }
}
