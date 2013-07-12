package com.netshelter.ifbrands.api.service;

import java.util.List;

import org.joda.time.DateTime;

import com.netshelter.ifbrands.api.model.storyamplification.ShortUrl;
import com.netshelter.ifbrands.api.model.storyamplification.StoryAmplification;
import com.netshelter.ifbrands.api.model.storyamplification.StoryAmplificationContainer;
import com.netshelter.ifbrands.api.model.storyamplification.StoryAmplifyDetail;

public interface ShareService
{
    /**
     * Used in health check - returns true if the remote service is alive
     * @return
     */
    public boolean isAlive();

    public String getShareApiAuthority();

    public boolean deleteAmplifications(String typeFilter, Integer id, Integer storyId);

    public List<StoryAmplificationContainer> getAmplifyStats(String callType, Integer id, DateTime start, DateTime stop);


    public List<StoryAmplifyDetail> getAmplifyStats(String callType, List<Integer> storyIds, Integer id);


    public String getAmplifyUrl(Integer storyId, String userKey, String shareType, Integer campaignId);


    public ShortUrl getShortUrl(Integer storyId, String shareType, Integer campaignId);


    public Integer getShortUrlClicks(String shortUrlKey, DateTime start, DateTime stop);


    public StoryAmplification recordAmplification(Integer storyId, String userKey, String typeFilter,
                                                  ShortUrl shortUrl, Integer campaignId);
}