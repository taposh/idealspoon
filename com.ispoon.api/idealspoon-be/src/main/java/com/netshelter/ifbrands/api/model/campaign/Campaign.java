package com.netshelter.ifbrands.api.model.campaign;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.netshelter.ifbrands.api.model.entity.Brand;

public class Campaign
{
    private Integer id;
    private String campaignKey;
    private String campaignName;
    private String userKey;
    private String clientUserKey;
    private String fullUserName;
    private String clientFullUserName;
    private Brand brand;
    private LocalDate startDate;
    private LocalDate stopDate;
    private String keywords;
    private boolean campaignEnabled;
    private DateTime createTimestamp;
    private CampaignStatus campaignStatus;
    private CampaignType campaignType;
    private List<Ad> ads;
    private Integer feedId;
    private String userEmail;
    private String productName;
    private String productDestination;
    private String callToAction;
    private String logoLocation;
    private String goal;

    public Campaign(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCampaignKey()
    {
        return campaignKey;
    }

    public void setCampaignKey(String campaignKey)
    {
        this.campaignKey = campaignKey;
    }

    public String getCampaignName()
    {
        return campaignName;
    }

    public void setCampaignName(String campaignName)
    {
        this.campaignName = campaignName;
    }

    public String getUserKey()
    {
        return userKey;
    }

    public void setUserKey(String userKey)
    {
        this.userKey = userKey;
    }

    public String getFullUserName()
    {
        return fullUserName;
    }

    public void setFullUserName(String fullUserName)
    {
        this.fullUserName = fullUserName;
    }

    public Brand getBrand()
    {
        return brand;
    }

    public void setBrand(Brand brand)
    {
        this.brand = brand;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getStopDate()
    {
        return stopDate;
    }

    public void setStopDate(LocalDate stopDate)
    {
        this.stopDate = stopDate;
    }

    public String getKeywords()
    {
        return keywords;
    }

    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }

    public boolean isCampaignEnabled()
    {
        return campaignEnabled;
    }

    public void setCampaignEnabled(boolean campaignEnabled)
    {
        this.campaignEnabled = campaignEnabled;
    }

    public DateTime getCreateTimestamp()
    {
        return createTimestamp;
    }

    public void setCreateTimestamp(DateTime createTimestamp)
    {
        this.createTimestamp = createTimestamp;
    }

    public CampaignStatus getCampaignStatus()
    {
        return campaignStatus;
    }

    public void setCampaignStatus(CampaignStatus campaignStatus)
    {
        this.campaignStatus = campaignStatus;
    }

    public CampaignType getCampaignType()
    {
        return campaignType;
    }

    public void setCampaignType(CampaignType campaignType)
    {
        this.campaignType = campaignType;
    }

    public List<Ad> getAds()
    {
        return ads;
    }

    public void setAds(List<Ad> ads)
    {
        this.ads = ads;
    }

    public String getClientUserKey()
    {
        return clientUserKey;
    }

    public void setClientUserKey(String clientUserKey)
    {
        this.clientUserKey = clientUserKey;
    }

    public String getClientFullUserName()
    {
        return clientFullUserName;
    }

    public void setClientFullUserName(String clientFullUserName)
    {
        this.clientFullUserName = clientFullUserName;
    }

    public Integer getFeedId()
    {
        return feedId;
    }

    public void setFeedId(Integer feedId)
    {
        this.feedId = feedId;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getCallToAction()
    {
        return callToAction;
    }

    public void setCallToAction(String callToAction)
    {
        this.callToAction = callToAction;
    }

    public String getLogoLocation()
    {
        return logoLocation;
    }

    public void setLogoLocation(String logoLocation)
    {
        this.logoLocation = logoLocation;
    }

    public String getProductDestination()
    {
        return productDestination;
    }

    public void setProductDestination(String productDestination)
    {
        this.productDestination = productDestination;
    }

    public String getGoal()
    {
        return goal;
    }

    public void setGoal(String goal)
    {
        this.goal = goal;
    }

    @Override
    public String toString()
    {
        return String.format(
                "Campaign [id=%s, campaignKey=%s, campaignName=%s, userKey=%s, brand=%s, startDate=%s, stopDate=%s, keywords=%s, campaignEnabled=%s, createTimestamp=%s, campaignStatus=%s, campaignType=%s]",
                id, campaignKey, campaignName, userKey, brand, startDate, stopDate, keywords,
                campaignEnabled, createTimestamp, campaignStatus, campaignType);
    }

}
