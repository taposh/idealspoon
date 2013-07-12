package com.netshelter.ifbrands.etl.transform.campaign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.campaign.CampaignStatus;
import com.netshelter.ifbrands.api.model.campaign.CampaignType;
import com.netshelter.ifbrands.api.model.user.UserInfo;
import com.netshelter.ifbrands.api.service.EntityService;
import com.netshelter.ifbrands.api.service.UserService;
import com.netshelter.ifbrands.data.entity.IfbCampaign;
import com.netshelter.ifbrands.data.entity.IfbCampaignStatus;
import com.netshelter.ifbrands.data.entity.IfbCampaignType;

@Component
public class CampaignTransformer
{

    @Autowired
    EntityService entityService;
    @Autowired
    UserService userService;

    public Campaign transform(IfbCampaign e)
    {
        if (e == null) return null;
        Campaign p = new Campaign(e.getCampaignId());
        if (Hibernate.isInitialized(e))
        {
            p.setCampaignStatus(transformCampaignStatus(e.getIfbCampaignStatus()));
            p.setCampaignType(transformCampaignType(e.getIfbCampaignType()));
            p.setCampaignKey(e.getCampaignKey());
            p.setCampaignName(e.getCampaignName());
            p.setUserKey(e.getUserKey());
            p.setBrand(entityService.getBrand(e.getDpBrandId()));
            p.setStartDate(new LocalDate(e.getStartDate()));
            p.setStopDate(new LocalDate(e.getStopDate()));
            p.setKeywords(e.getKeywords());
            p.setCampaignEnabled(e.isCampaignEnabled());
            p.setCreateTimestamp(new DateTime(e.getCreateTimestamp()));
            p.setClientUserKey(e.getClientUserKey());
            p.setFeedId(e.getFeedId());
            p.setProductName(e.getProductName());
            p.setProductDestination(e.getProductDestination());
            p.setCallToAction(e.getCallToAction());
            p.setLogoLocation(e.getLogoLocation());
            p.setGoal(e.getGoal());

            UserInfo userInfo = userService.getUserInfo(e.getUserKey());
            // If user doesn't exist let's not try to set his name
            if (userInfo != null)
            {
                p.setFullUserName(userInfo.getFullName());
                p.setUserEmail(userInfo.getEmail());
            }

            if (StringUtils.isNotBlank(e.getClientUserKey()))
            {
                UserInfo clientUserInfo = userService.getUserInfo(e.getClientUserKey());
                // If user doesn't exist let's not try to set his name
                if (clientUserInfo != null)
                {
                    p.setClientFullUserName(clientUserInfo.getFullName());
                }
            }
        }

        return p;
    }

    public List<Campaign> transform(Collection<IfbCampaign> list)
    {
        List<Campaign> results = new ArrayList<Campaign>(list.size());
        for (IfbCampaign e : list)
        {
            results.add(transform(e));
        }

        return results;
    }

    public CampaignType transformCampaignType(IfbCampaignType e)
    {
        if (e == null) return null;
        CampaignType p = new CampaignType(e.getCampaignTypeId());
        if (Hibernate.isInitialized(e))
        {
            p.setName(e.getCampaignTypeName());
        }
        return p;
    }

    public List<CampaignType> transformCampaignType(Collection<IfbCampaignType> list)
    {
        List<CampaignType> results = new ArrayList<CampaignType>(list.size());
        for (IfbCampaignType e : list)
        {
            results.add(transformCampaignType(e));
        }
        return results;
    }

    public CampaignStatus transformCampaignStatus(IfbCampaignStatus e)
    {
        if (e == null) return null;
        CampaignStatus p = new CampaignStatus(e.getCampaignStatusId());
        if (Hibernate.isInitialized(e))
        {
            p.setName(e.getCampaignStatusName());
        }
        return p;
    }

    public List<CampaignStatus> transformCampaignStatus(Collection<IfbCampaignStatus> list)
    {
        List<CampaignStatus> results = new ArrayList<CampaignStatus>(list.size());
        for (IfbCampaignStatus e : list)
        {
            results.add(transformCampaignStatus(e));
        }

        return results;
    }


}
