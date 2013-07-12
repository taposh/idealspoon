package com.netshelter.ifbrands.api.model.storyamplification;

import org.joda.time.DateTime;

public class AmplifyDetail 
{
  private Integer storyId;
  private String userKey;
  private String userName;
  private String amplifyTarget;
  private Integer adId;
  private Integer campaignId;
  private DateTime amplifyDateTime;
  
  public Integer getStoryId()
  {
    return storyId;
  }
  public void setStoryId(Integer storyId)
  {
    this.storyId = storyId;
  }
  public String getUserKey()
  {
    return userKey;
  }
  public void setUserKey(String userKey)
  {
    this.userKey = userKey;
  }
  public String getUserName()
  {
    return userName;
  }
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  public String getAmplifyTarget()
  {
    return amplifyTarget;
  }
  public void setAmplifyTarget(String amplifyTarget)
  {
    this.amplifyTarget = amplifyTarget;
  }
  public Integer getAdId()
  {
    return adId;
  }
  public void setAdId( Integer adId )
  {
    this.adId = adId;
  }
  public Integer getCampaignId()
  {
    return campaignId;
  }
  public void setCampaignId( Integer campaignId )
  {
    this.campaignId = campaignId;
  }
  public DateTime getAmplifyDateTime()
  {
    return amplifyDateTime;
  }
  public void setAmplifyDateTime(DateTime amplifyDateTime)
  {
    this.amplifyDateTime = amplifyDateTime;
  }
  
  @Override
  public String toString()
  {
    return String
        .format(
            "AmplifyDetail [storyId=%s, userKey=%s, userName=%s, amplifyTarget=%s, amplifyDateTime=%s]",
            storyId, userKey, userName, amplifyTarget,
            amplifyDateTime);
  }
}
