package com.netshelter.ifbrands.api.model;

/**
 * @author Dmitriy T
 * This enum is used in ActionLogService
 */
public enum ActionType
{
    STORY_STATUS_CHANGE,        // FeedServiceImpl.updateFeedStory(...)
    STORY_CUSTOM_THUMBNAIL_SET, // FeedServiceImpl.setCustomStoryImage(...)
    CAMPAIGN_STATUS_CHANGE,     // CampaignServiceImpl 2 methods called updateCampaign(...)
    CAMPAIGN_CACHED,            // InfluenceServiceImpl.getSummaryPaidByCampaign(...)


    PRE_CACHER_FINISHED,
    SYSTEM_STARTED,
}
