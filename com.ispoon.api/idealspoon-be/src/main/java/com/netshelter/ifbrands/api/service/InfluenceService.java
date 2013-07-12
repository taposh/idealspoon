package com.netshelter.ifbrands.api.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceAndSentimentStoriesFacetedResponse;
import org.joda.time.DateTime;

import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.model.influence.BrandQuery;
import com.netshelter.ifbrands.api.model.influence.InfluenceBreakdown;
import com.netshelter.ifbrands.api.model.influence.InfluenceComparison;
import com.netshelter.ifbrands.api.model.influence.InfluenceStories;
import com.netshelter.ifbrands.api.model.influence.InfluenceSummary;
import com.netshelter.ifbrands.api.model.influence.InfluenceTrend;


public interface InfluenceService
{
    public static final String INFLUENCE_CACHE = "ifb.mvc.influence";

    public enum Goal
    {
        SUMMARY, TREND;
    }

    public static final int DEFAULT_DAYS_BACK_SEARCH = 2;

    public void flushCache();

    // Summary
    public InfluenceComparison<InfluenceSummary> getSummaryPaidByCampaign(Collection<Integer> campaignIds,
                                                                          DateTime startTime, DateTime stopTime,
                                                                          boolean ignoreCache);

    public InfluenceComparison<InfluenceSummary> getSummaryPaidByAd(Integer campaignId, Collection<Integer> adIds,
                                                                    Collection<Integer> adStatusIds,
                                                                    DateTime startTime, DateTime stopTime);

    public InfluenceComparison<InfluenceSummary> getSummaryPaidByStory(Integer campaignId, Integer adId,
                                                                       Collection<Integer> storyStatusIds,
                                                                       DateTime startTime, DateTime stopTime);

    public InfluenceComparison<InfluenceSummary> getSummaryPaidByCampaignStoryLevel(Integer campaignId,
                                                                                    Collection<Integer> storyStatusIds,
                                                                                    DateTime startTime, DateTime stopTime);

    // Trend
    public InfluenceTrend getTrendTotal(Collection<Integer> brandIds, Collection<Integer> authorIds, Collection<String> sentiments,
                                        DateTime startTime, DateTime stopTime, String query);

    public InfluenceBreakdown<InfluenceTrend> getBreakdown(Collection<Integer> brandIds, Collection<Integer> campaignIds,
                                                           Collection<Integer> authorIds, Collection<String> sentiments,
                                                           DateTime startTime, DateTime stopTime, String query, String goal,
                                                           Boolean campaignOnly, Boolean ignoreCache);

    public Map<Integer, InfluenceBreakdown<InfluenceTrend>> getBreakdownByCampaign(
            Collection<Integer> campaignIds,
            final Collection<Integer> authorIds,
            final Collection<String> sentiments,
            final Goal goal,
            final Boolean campaignOnly, final Boolean ignoreCache);

    public InfluenceComparison<InfluenceTrend> getTrendComparison(Collection<BrandQuery> series, Collection<Integer> authorIds,
                                                                  Collection<String> sentiments, DateTime startTime, DateTime stopTime);


    public InfluenceStories getStoriesTotal(Collection<Integer> brandIds,
                                            Collection<Integer> authorIds, Collection<String> sentiments,
                                            DateTime startTime, DateTime stopTime,
                                            String query, String order, Integer limit,
                                            List<Integer> campaignIds, DateTime pubStartDate);

    public GetInfluenceAndSentimentStoriesFacetedResponse getStoriesFaceted(
            Integer campaignId,
            Boolean campaignOnly,
            Collection<Integer> brandIds,
            Collection<Integer> authorIds,
            Collection<String> sentiments,
            DateTime startTime, DateTime stopTime,
            String query,
            String order,
            Integer limit,
            Integer offset,
            boolean ignoreDpCache,
            DateTime pubStartDate);

    public InfluenceStories getStoriesPaid(Collection<Integer> brandIds, Collection<Integer> campaignIds,
                                           Collection<Integer> authorIds, Collection<String> sentiments,
                                           DateTime startTime, DateTime stopTime,
                                           String query, String order, Integer limit);

    public Map<String, String> getEngagementMetrics(Integer dpStoryId);

    public Map<String, String> getEngagementMetricsUnjoined(Integer dpStoryId);

    public Collection<Story> getStoryEngagements(Collection<Integer> dpStoryIds, DateTime startTime, DateTime stopTime);

    public GenericPayload<Map<Integer, Collection<Story>>> searchNewStories(List<Integer> campaignIds,
                                                                            Integer daysBack, boolean ignoreCache);

    public GenericPayload<Map<Integer, GetInfluenceAndSentimentStoriesFacetedResponse>> searchNewStories_v2(List<Integer> campaignIds,
                                                                            Integer daysBack, boolean ignoreCache);

}
