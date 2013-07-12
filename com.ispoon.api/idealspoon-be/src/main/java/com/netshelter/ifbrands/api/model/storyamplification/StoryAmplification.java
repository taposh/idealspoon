package com.netshelter.ifbrands.api.model.storyamplification;

import org.joda.time.DateTime;

import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.model.user.UserState;

public class StoryAmplification
{
  private Integer id;
  private String shortUrlKey;
  private Story story;
  private String userKey;
  private UserState userState;
  private String type;
  private DateTime created;
  private DateTime lastAmplified;
  private Integer adId;
  private Integer campaignId;
  private Integer consumptions;
    
  public StoryAmplification ( Integer id )
  {
    this.id = id;
  }
  
  public Integer getId()
  {
    return id;
  }
  
  public void setId( Integer id )
  {
    this.id = id;
  }
  
  public String getShortUrlKey()
  {
    return shortUrlKey;
  }

  public void setShortUrlKey( String shortUrlKey )
  {
    this.shortUrlKey = shortUrlKey;
  }
  
  public Story getStory()
  {
    return story;
  }
  
  public void setStory( Story story )
  {
    this.story = story;
  }
  
  public String getUserKey() {
    return userKey;
  }

  public void setUserKey(String userKey) {
    this.userKey = userKey;
  }

  public UserState getUserState()
  {
    return userState;
  }
  
  public void setUserState( UserState userState )
  {
    this.userState = userState;
  }
  
  public String getType()
  {
    return type;
  }

  public void setType( String type )
  {
    this.type = type;
  }

  public DateTime getCreated()
  {
    return created;
  }
  
  public void setCreated( DateTime created )
  {
    this.created = created;
  }
  
  public DateTime getLastAmplified()
  {
    return lastAmplified;
  }
  
  public void setLastAmplified( DateTime lastAmplified )
  {
    this.lastAmplified = lastAmplified;
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

  public Integer getConsumptions()
  {
    return consumptions;
  }

  public void setConsumptions( Integer consumptions )
  {
    this.consumptions = consumptions;
  }

  @Override
  public String toString()
  {
    return String
        .format(
            "StoryAmplification [id=%s, shortUrlKey=%s, story=%s, userKey=%s, userState=%s, type=%s, created=%s, lastAmplified=%s, adId=%s, consumptions=%s]",
            id, shortUrlKey, story, userKey, userState, type, created,
            lastAmplified, adId, consumptions);
  }
  
}