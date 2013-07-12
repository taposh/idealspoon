package com.netshelter.ifbrands.etl.transform.campaign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.netshelter.ifbrands.api.model.campaign.AdNetwork;
import com.netshelter.ifbrands.api.model.campaign.AdState;
import com.netshelter.ifbrands.api.service.AdNetworkService;
import com.netshelter.ifbrands.api.service.AdSizeService;
import com.netshelter.ifbrands.api.service.TemplateService;
import com.netshelter.ifbrands.data.entity.IfbAdState;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.AdStatus;
import com.netshelter.ifbrands.api.model.campaign.AdType;
import com.netshelter.ifbrands.data.entity.IfbAd;
import com.netshelter.ifbrands.data.entity.IfbAdStatus;
import com.netshelter.ifbrands.data.entity.IfbAdType;

@Component
public class AdTransformer
{
    @Autowired
    private FeedTransformer feedTransformer;
    @Autowired
    private CampaignTransformer campaignTransformer;
    @Autowired
    private AdSizeService adSizeService;
    @Autowired
    private AdNetworkService adNetworkService;
    @Autowired
    private TemplateService templateService;

    public AdType transformType(IfbAdType e)
    {
        if (e == null) return null;
        AdType p = new AdType(e.getAdTypeId());
        if (Hibernate.isInitialized(e))
        {
            p.setName(e.getAdTypeName());
        }
        return p;
    }

    public List<AdType> transformType(Collection<IfbAdType> list)
    {
        List<AdType> results = new ArrayList<AdType>(list.size());
        for (IfbAdType e : list)
        {
            results.add(transformType(e));
        }
        return results;
    }

    public AdStatus transformStatus(IfbAdStatus e)
    {
        if (e == null) return null;
        AdStatus p = new AdStatus(e.getAdStatusId());
        if (Hibernate.isInitialized(e))
        {
            p.setName(e.getAdStatusName());
        }
        return p;
    }

    public AdState transformState(IfbAdState e)
    {
        if (e == null) return null;
        AdState p = new AdState(e.getAdStateId());
        if (Hibernate.isInitialized(e))
        {
            p.setName(e.getAdStateName());
        }
        return p;
    }

    public List<AdStatus> transformStatus(Collection<IfbAdStatus> list)
    {
        List<AdStatus> results = new ArrayList<AdStatus>(list.size());
        for (IfbAdStatus e : list)
        {
            results.add(transformStatus(e));
        }
        return results;
    }

    public List<AdState> transformState(Collection<IfbAdState> list)
    {
        List<AdState> results = new ArrayList<AdState>(list.size());
        for (IfbAdState e : list)
        {
            results.add(transformState(e));
        }
        return results;
    }

    public Ad transform(IfbAd e)
    {
        if (e == null) return null;
        Ad p = new Ad(e.getAdId());
        if (Hibernate.isInitialized(e))
        {
            p.setType(transformType(e.getIfbAdType()));
            p.setStatus(transformStatus(e.getIfbAdStatus()));
            p.setState(transformState(e.getIfbAdState()));
            p.setFeed(feedTransformer.transform(e.getIfbFeed()));
            p.setCampaign(campaignTransformer.transform(e.getIfbCampaign()));
            p.setKey(e.getAdKey());
            p.setName(e.getAdName());
            if (StringUtils.isNotBlank(e.getTemplateKey()))
            {
                p.setTemplate(templateService.getByKey(e.getTemplateKey()));
            }
            if(e.getStartDate() !=null )
            {
                p.setStartDate(new LocalDate(e.getStartDate()));
            }

            if(e.getStopDate() !=null )
            {
                p.setStopDate(new LocalDate(e.getStopDate()));
            }

            if (e.getAdNetworkId() != null && e.getAdNetworkId() > 0)
            {
                p.setAdNetwork(adNetworkService.get(e.getAdNetworkId()));
            }

            p.setCreateTimestamp(new DateTime(e.getCreateTimestamp()));
            if (e.getAdSizeId() > 0)
            {
                p.setAdSize(adSizeService.get(e.getAdSizeId()));
            }

        }
        return p;
    }

    public List<Ad> transform(Collection<IfbAd> list)
    {
        List<Ad> results = new ArrayList<Ad>(list.size());
        for (IfbAd e : list)
        {
            results.add(transform(e));
        }
        return results;
    }
}
