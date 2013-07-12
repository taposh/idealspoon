package com.netshelter.ifbrands.api.model.influence;

import com.netshelter.ifbrands.api.service.SurveyServiceImpl;

import java.util.List;

public class Influence
{
  private long events, score, influencers, peopleInfluenced, uniqueCount,
      impressions, consumptions, amplifiedConsumptions, storyTimeSpent, storyImpressions;

  private List<SurveyServiceImpl.SurveyResult> surveyResultList = null;

  public long getEvents()
  {
    return events;
  }

  public void setEvents( long events )
  {
    this.events = events;
  }

  public long getPoints()
  {
    return peopleInfluenced;
  }

  public long getScore()
  {
    return score;
  }

  public void setScore( long score )
  {
    this.score = score;
  }

  public long getUniqueCount()
  {
    return uniqueCount;
  }

  public void setUniqueCount( long uniqueCount )
  {
    this.uniqueCount = uniqueCount;
  }

  public long getInfluencers()
  {
    return influencers;
  }

  public void setInfluencers( long influencers )
  {
    this.influencers = influencers;
  }

  public long getPeopleInfluenced()
  {
    return peopleInfluenced;
  }

  public void setPeopleInfluenced( long peopleInfluenced )
  {
    this.peopleInfluenced = peopleInfluenced;
  }

  public long getImpressions()
  {
    return impressions;
  }

  public void setImpressions( long impressions )
  {
    this.impressions = impressions;
  }

  public long getConsumptions() {
	return consumptions;
  }

  public void setConsumptions(long consumptions) {
      this.consumptions = consumptions;
  }

  public long getAmplifiedConsumptions() {
      return amplifiedConsumptions;
  }

  public void setAmplifiedConsumptions(long amplifiedConsumptions) {
      this.amplifiedConsumptions = amplifiedConsumptions;
  }

  public long getStoryTimeSpent()
  {
    return storyTimeSpent;
  }

  public void setStoryTimeSpent( long storyTimeSpent )
  {
    this.storyTimeSpent = storyTimeSpent;
  }

  public long getStoryImpressions()
  {
    return storyImpressions;
  }

  public void setStoryImpressions( long storyImpressions )
  {
    this.storyImpressions = storyImpressions;
  }

  public List<SurveyServiceImpl.SurveyResult> getSurveyResultList()
  {
    return surveyResultList;
  }

  public void setSurveyResultList( List<SurveyServiceImpl.SurveyResult> surveyResultList )
  {
    this.surveyResultList = surveyResultList;
  }

  public void add( Influence that )
  {
    this.score += that.score;
    this.events += that.events;
    this.influencers += that.influencers;
    this.uniqueCount += that.uniqueCount;
    this.impressions += that.impressions;
    this.peopleInfluenced += that.peopleInfluenced;
    this.consumptions += that.consumptions;
    this.amplifiedConsumptions += that.amplifiedConsumptions;
    this.storyTimeSpent += that.storyTimeSpent;
    this.storyImpressions += that.storyImpressions;
  }

  @Override
  public String toString()
  {
    return "Influence [events=" + events + ", score=" + score + ", influencers="
        + influencers + ", peopleInfluenced=" + peopleInfluenced +", uniqueCount=" + uniqueCount + ", impressions=" + impressions + "]";
  }
}
