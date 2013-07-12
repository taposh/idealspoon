package com.netshelter.ifbrands.api.model.storyamplification;

import com.netshelter.ifbrands.api.model.entity.Brand;
import com.netshelter.ifbrands.api.model.entity.Story;

public class StoryAmplificationContainer
{
  private Brand brand;
  private Story story;
  private StoryAmplification facebookAmplification;
  private StoryAmplification twitterAmplification;
  
  public Brand getBrand() {
    return brand;
  }
  public void setBrand(Brand brand) {
    this.brand = brand;
  }
  public Story getStory() {
    return story;
  }
  public void setStory(Story story) {
    this.story = story;
  }
  public StoryAmplification getFacebookAmplification()
  {
    return facebookAmplification;
  }
  public void setFacebookAmplification(StoryAmplification facebookAmplification)
  {
    this.facebookAmplification = facebookAmplification;
  }
  public StoryAmplification getTwitterAmplification()
  {
    return twitterAmplification;
  }
  public void setTwitterAmplification(StoryAmplification twitterAmplification)
  {
    this.twitterAmplification = twitterAmplification;
  }
  
  @Override
  public String toString()
  {
    return "StoryAmplificationContainer [facebookAmplification="
        + facebookAmplification + ", twitterAmplification="
        + twitterAmplification + "]";
  }
}
