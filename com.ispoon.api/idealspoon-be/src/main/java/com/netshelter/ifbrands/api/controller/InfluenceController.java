package com.netshelter.ifbrands.api.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.netshelter.ifbrands.api.util.LiftCalculatorUtils;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceAndSentimentStoriesFacetedResponse;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.model.influence.BrandQuery;
import com.netshelter.ifbrands.api.model.influence.InfluenceBreakdown;
import com.netshelter.ifbrands.api.model.influence.InfluenceComparison;
import com.netshelter.ifbrands.api.model.influence.InfluenceStories;
import com.netshelter.ifbrands.api.model.influence.InfluenceSummary;
import com.netshelter.ifbrands.api.model.influence.InfluenceTrend;
import com.netshelter.ifbrands.api.service.InfluenceService;
import com.netshelter.ifbrands.api.util.GeneralClientException;
import com.netshelter.ifbrands.api.util.MvcUtils;

/**
 * Controller for all queries involving influence and/or sentiment.
 *
 * @author bgray
 */
@Controller("influenceController")
@RequestMapping("/influence")
public class InfluenceController extends BaseController {
    public static final String ALL = "-";

    @Autowired
    private InfluenceService influenceService;
    @Autowired
    private LiftCalculatorUtils liftCalculatorUtils;

    /**
     * Flush cache.
     */
    @RequestMapping(value = "/flush")
    @ResponseBody
    public GenericStatus flushCache() {
        influenceService.flushCache();
        return GenericStatus.okay(InfluenceService.INFLUENCE_CACHE + " cache flushed");
    }

    /**
     * Fetch the day-by-day total influence trend for a given (brand[],query) tuple.  This trend can further
     * be optionally filtered by sentiment or author.  Dates are assumed Americas/NewYork for now.
     *
     * @param brandList       List of brand ids
     * @param startDate       Start date for query
     * @param stopDate        Stop  date for query
     * @param authorFilter    Optional author filter (id list)
     * @param sentimentFilter Optional sentiment filter (enum list)
     * @param query           Optional query string
     * @return Influence Trend
     */
    @RequestMapping(value = "/trend/brand:{brands}")
    @ResponseBody
    public InfluenceTrend getInfluenceTrendTotal(@PathVariable("brands") String brandList,
                                                 @RequestParam(value = "start", required = true) LocalDate startDate,
                                                 @RequestParam(value = "stop", required = true) LocalDate stopDate,
                                                 @RequestParam(value = "authors", required = false) String authorFilter,
                                                 @RequestParam(value = "sentiments", required = false) String sentimentFilter,
                                                 @RequestParam(value = "q", required = false) String query) {
        logger.info("/influence/trend/brand:%s [%s,%s,%s,%s,%s]",
                brandList, startDate, stopDate, authorFilter, sentimentFilter, query);
        // Parse filter lists
        List<Integer> brandIds = MvcUtils.getIdsFromFilter(brandList);
        List<Integer> authorIds = MvcUtils.getIdsFromFilter(authorFilter);
        List<String> sentiments = MvcUtils.getStringsFromFilter(sentimentFilter);
        // Create DateTime's assuming EST
        DateTime startTime = startDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT));
        DateTime stopTime = stopDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT)).plusDays(1).minusMillis(1);
        // Call service
        InfluenceTrend trend = influenceService.getTrendTotal(brandIds, authorIds, sentiments, startTime, stopTime, query);
        logger.debug("..%d days trend found", trend.getTrend().size());
        return trend;
    }

    /**
     * Fetch the day-by-day influence trend breakdown for a given (brand[],campaign[],query) tuple This trend can further
     * be optionally filtered by sentiment or author.  Dates are assumed Americas/NewYork for now.
     *
     * @param brandList       List of brand ids
     * @param startDate       Start date for trend
     * @param stopDate        Stop  date for trend
     * @param authorFilter    Optional author filter (id list)
     * @param sentimentFilter Optional sentiment filter (enum list)
     * @param query           Optional query string
     * @return Influence Breakdown of Trends
     */
    @RequestMapping(value = "/{goal:(?:trend|summary)}/brand:{brands}/campaign:{campaigns}")
    @ResponseBody
    public InfluenceBreakdown<InfluenceTrend> getInfluenceBreakdown(@PathVariable("goal") String goal,
                                                                    @PathVariable("brands") String brandList,
                                                                    @PathVariable("campaigns") String campaignList,
                                                                    @RequestParam(value = "start", required = true) LocalDate startDate,
                                                                    @RequestParam(value = "stop", required = true) LocalDate stopDate,
                                                                    @RequestParam(value = "authors", required = false) String authorFilter,
                                                                    @RequestParam(value = "sentiments", required = false) String sentimentFilter,
                                                                    @RequestParam(value = "q", required = false) String query,
                                                                    @RequestParam(value = "campaignonly", required = false) String campaignOnly) {
        logger.info("/influence/%s/brand:%s/campaign:%s [%s,%s,%s,%s,%s]",
                goal, brandList, campaignList, startDate, stopDate, authorFilter, sentimentFilter, query);
        // Validate arguments
        if (brandList == null) throw new GeneralClientException("Must specify at least one brand");
        if (campaignList == null) throw new GeneralClientException("Must specify at least one campaign");
        // Parse filter lists
        List<Integer> brandIds = MvcUtils.getIdsFromFilter(brandList);
        List<Integer> campaignIds = MvcUtils.getIdsFromFilter(campaignList);
        List<Integer> authorIds = MvcUtils.getIdsFromFilter(authorFilter);
        List<String> sentiments = MvcUtils.getStringsFromFilter(sentimentFilter);
        // Create DateTime's assuming EST
        DateTime startTime = startDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT));
        DateTime stopTime = stopDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT)).plusDays(1).minusMillis(1);
        // Call service
        Boolean campaignOnlyFlag = false;
        if (StringUtils.isNotBlank(campaignOnly)) {
            campaignOnlyFlag = Boolean.parseBoolean(campaignOnly);
        }
        InfluenceBreakdown<InfluenceTrend> trend =
                influenceService.getBreakdown(brandIds, campaignIds, authorIds, sentiments, startTime, stopTime,
                        query, goal, campaignOnlyFlag, false);
        liftCalculatorUtils.calculateLift(trend);
        logger.debug("..%d days trend found", trend.getTotal().getTrend().size());
        return trend;
    }

    /**
     * Fetch the day-by-day influence trend breakdown for a given (brand[],campaign[],query) tuple.  This trend can further
     * be optionally filtered by sentiment or author.  Dates are assumed Americas/NewYork for now.
     *
     * @param brandList       List of brand ids
     * @param startDate       Start date for trend
     * @param stopDate        Stop  date for trend
     * @param authorFilter    Optional author filter (id list)
     * @param sentimentFilter Optional sentiment filter (enum list)
     * @param query           Optional query string
     * @return Influence Breakdown of Trends
     */
    @RequestMapping(value = "/list/{goal:(?:trend|summary)}/brand:{brands}/campaign:{campaigns}")
    @ResponseBody
    public GenericPayload<Map<Integer, InfluenceBreakdown<InfluenceTrend>>> getInfluenceBreakdownByCampaign(
            @PathVariable("goal") String goal,
            @PathVariable("brands") String brandList,
            @PathVariable("campaigns") String campaignList,
            @RequestParam(value = "start", required = true) LocalDate startDate,
            @RequestParam(value = "stop", required = true) LocalDate stopDate,
            @RequestParam(value = "authors", required = false) String authorFilter,
            @RequestParam(value = "sentiments", required = false) String sentimentFilter,
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "campaignonly", required = false) String campaignOnly) {
        logger.info("/influence/list/%s/brand:%s/campaign:%s [%s,%s,%s,%s,%s]",
                goal, brandList, campaignList, startDate, stopDate, authorFilter, sentimentFilter, query);
        // Validate arguments
        if (brandList == null) throw new GeneralClientException("Must specify at least one brand");
        if (campaignList == null) throw new GeneralClientException("Must specify at least one campaign");
        MvcUtils.getIdsFromFilter(brandList);
        List<Integer> campaignIds = MvcUtils.getIdsFromFilter(campaignList);
        List<Integer> authorIds = MvcUtils.getIdsFromFilter(authorFilter);
        List<String> sentiments = MvcUtils.getStringsFromFilter(sentimentFilter);
        Boolean campaignOnlyFlag = false;
        if (StringUtils.isNotBlank(campaignOnly)) {
            campaignOnlyFlag = Boolean.parseBoolean(campaignOnly);
        }

        Map<Integer, InfluenceBreakdown<InfluenceTrend>> result = new HashMap<Integer, InfluenceBreakdown<InfluenceTrend>>();

        Map<Integer, InfluenceBreakdown<InfluenceTrend>> influenceBreakdownByCampaign =
                influenceService.getBreakdownByCampaign(
                        campaignIds,
                        authorIds,
                        sentiments,
                        InfluenceService.Goal.TREND, //
                        campaignOnlyFlag,
                        false
                );

        Set<Integer> keys = influenceBreakdownByCampaign.keySet();
        for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext(); ) {
            Integer key = iterator.next();
            InfluenceBreakdown<InfluenceTrend> next = influenceBreakdownByCampaign.get(key);
            liftCalculatorUtils.calculateLift(next);
            // In cases of goal="SUMMARY" to remove the trend data,
            // since the response object is cached we can't change it directly but need to clone it
            if (InfluenceService.Goal.valueOf(goal.toUpperCase()) == InfluenceService.Goal.SUMMARY) {
                InfluenceBreakdown<InfluenceTrend> breakdown = new InfluenceBreakdown<InfluenceTrend>();
                breakdown.setLift(next.getLift());
                breakdown.setOrganic(cloneInfluenceWithoutTrend(next.getOrganic()));
                breakdown.setPaid(cloneInfluenceWithoutTrend(next.getPaid()));
                breakdown.setTotal(cloneInfluenceWithoutTrend(next.getTotal()));
                result.put(key, breakdown);
            } else {
                result = influenceBreakdownByCampaign;
            }

        }

        return new GenericPayload<Map<Integer, InfluenceBreakdown<InfluenceTrend>>>
                ("influenceBreakdownByCampaign", result);
    }


    /**
     * Fetch multiple day-by-day influence trends for a set of (brand,query) tuples.  This can further be filtered
     * by sentiment and/or author.  Dates are assumed Americas/NewYork for now.
     *
     * @param series          Individual trend tuples of form "brandId:queryString"
     * @param startDate       Start date for query
     * @param stopDate        Stop  date for query
     * @param authorFilter    Optional author filter (id list)
     * @param sentimentFilter Optional sentiment filter (enum list)
     * @return Influence Comparison of Trends
     */
    @RequestMapping(value = "/trend/compare")
    @ResponseBody
    public InfluenceComparison<InfluenceTrend> getInfluenceTrendCompare(@RequestParam(value = "series") String[] series,
                                                                        @RequestParam(value = "start", required = true) LocalDate startDate,
                                                                        @RequestParam(value = "stop", required = true) LocalDate stopDate,
                                                                        @RequestParam(value = "authors", required = false) String authorFilter,
                                                                        @RequestParam(value = "sentiments", required = false) String sentimentFilter) {
        logger.info("/influence/trend/compare [%s,%s,%s,%s]",
                startDate, stopDate, authorFilter, sentimentFilter);
        for (String s : series) {
            logger.info("...%s", s);
        }

        // Parse filter lists
        List<BrandQuery> bqList = BrandQuery.parse(series);
        List<Integer> authorIds = MvcUtils.getIdsFromFilter(authorFilter);
        List<String> sentiments = MvcUtils.getStringsFromFilter(sentimentFilter);
        // Create DateTime's assuming EST
        DateTime startTime = startDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT));
        DateTime stopTime = stopDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT)).plusDays(1).minusMillis(1);

        // Call service for each tuple
        InfluenceComparison<InfluenceTrend> trend = influenceService.getTrendComparison(bqList, authorIds, sentiments, startTime, stopTime);
        logger.debug("..%d trend series found", trend.size());
        return trend;
    }


    /**
     * Get the paid influence data for a set of Campaigns.
     *
     * @param campaignFilter List of Campaign Ids
     * @param startDate      Start date for query
     * @param stopDate       Stop  date for query
     * @return Influence Comparison of Summaries
     */
    @RequestMapping(value = "/paid/campaign:{campaigns}")
    @ResponseBody
    public InfluenceComparison<InfluenceSummary> getInfluenceSummaryPaid(@PathVariable("campaigns") String campaignFilter,
                                                                         @RequestParam(value = "start") LocalDate startDate,
                                                                         @RequestParam(value = "stop") LocalDate stopDate) {
        logger.info("/influence/paid/campaign:%s [%s,%s]", campaignFilter, startDate, stopDate);
        // Parse filter lists
        List<Integer> campaignIds = MvcUtils.getIdsFromFilter(campaignFilter);
        // Create DateTime's assuming EST
        DateTime startTime = startDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT));
        DateTime stopTime = stopDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT)).plusDays(1).minusMillis(1);
        // Call service
        return influenceService.getSummaryPaidByCampaign(campaignIds, startTime, stopTime, false);
    }

    /**
     * Get the paid influence data for a set of Ads.
     *
     * @param campaignId Campaign Id
     * @param adFilter   List of Ad Ids
     * @param startDate  Start date for query
     * @param stopDate   Stop  date for query
     * @return Influence Comparison of Summaries
     */
    @RequestMapping(value = "/paid/campaign:{campaignId}/ad:{ads}")
    @ResponseBody
    public InfluenceComparison<InfluenceSummary> getInfluenceSummaryPaid(@PathVariable("campaignId") Integer campaignId,
                                                                         @PathVariable("ads") String adFilter,
                                                                         @RequestParam(value = "start") LocalDate startDate,
                                                                         @RequestParam(value = "stop") LocalDate stopDate,
                                                                         @RequestParam(value = "status", required = false) String adStatusFilter) {
        logger.info("/influence/paid/campaign:%s/ad:%s [%s,%s]", campaignId, adFilter, startDate, stopDate);
        // Parse filter list
        List<Integer> adIds = MvcUtils.getIdsFromFilter(adFilter);
        List<Integer> adStatusIds = MvcUtils.getIdsFromFilter(adStatusFilter);
        // Create DateTime's assuming EST
        DateTime startTime = startDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT));
        DateTime stopTime = stopDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT)).plusDays(1).minusMillis(1);
        // Call service
        InfluenceComparison<InfluenceSummary> result = influenceService.getSummaryPaidByAd(campaignId, adIds, adStatusIds, startTime, stopTime);
        return result;
    }

    /**
     * Get the paid influence data for a set of Stories
     *
     * @param campaignId Campaign Id
     * @param adId       Ad Ids
     * @param startDate  Start date for query
     * @param stopDate   Stop  date for query
     * @return Influence Comparison of Summaries
     */
    @RequestMapping(value = "/paid/campaign:{campaignId}/ad:{adId}/story")
    @ResponseBody
    public InfluenceComparison<InfluenceSummary> getInfluenceSummaryPaid(@PathVariable("campaignId") Integer campaignId,
                                                                         @PathVariable("adId") Integer adId,
                                                                         @RequestParam(value = "start") LocalDate startDate,
                                                                         @RequestParam(value = "stop") LocalDate stopDate,
                                                                         @RequestParam(value = "status", required = false) String storyStatusFilter) {
        logger.info("/influence/paid/campaign:%s/ad:%s/story [%s,%s]", campaignId, adId, startDate, stopDate);
        // Parse filter list
        List<Integer> storyStatusIds = MvcUtils.getIdsFromFilter(storyStatusFilter);
        // Create DateTime's assuming EST
        DateTime startTime = startDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT));
        DateTime stopTime = stopDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT)).plusDays(1).minusMillis(1);
        // Call service
        return influenceService.getSummaryPaidByStory(campaignId, adId, storyStatusIds, startTime, stopTime);
    }

    /**
     * Returns story-level analytics at campaign level.
     *
     * @param campaignId Campaign Id
     * @param startDate  Start date for query
     * @param stopDate   Stop  date for query
     * @return Influence Comparison of Summaries
     */
    @RequestMapping(value = "/paid/campaign:{campaignId}/story")
    @ResponseBody
    public InfluenceComparison<InfluenceSummary> getInfluenceSummaryPaidPerCampaign(
            @PathVariable("campaignId") Integer campaignId,
            @RequestParam(value = "start") LocalDate startDate,
            @RequestParam(value = "stop") LocalDate stopDate,
            @RequestParam(value = "status", required = false) String storyStatusFilter
    )
    {
        logger.info("/influence/paid/campaign:%s/story [%s,%s]", campaignId, startDate, stopDate);
        // Parse filter list
        List<Integer> storyStatusIds = MvcUtils.getIdsFromFilter(storyStatusFilter);
        // Create DateTime's assuming EST
        DateTime startTime = startDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT));
        DateTime stopTime = stopDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT)).plusDays(1).minusMillis(1);
        // Call service
        return influenceService.getSummaryPaidByCampaignStoryLevel(campaignId, storyStatusIds, startTime, stopTime);
    }


    /**
     * Fetch the list of stories relevant to a given (brand,query) tuple, with explicit ordering.
     * Dates are assumed to be Americas/NewYork.
     *
     * @param brandList       List of brandIds for tuple (currently only one supported).
     * @param order           Order of result set (enum)
     * @param startDate       Start  Start ddate for query
     * @param stopDate        Stop   Stop date for query
     * @param authorFilter    Optional author filter (id list)
     * @param sentimentFilter Optional sentiment filter (enum list)
     * @param query           Optional query string
     * @param limit           Maximum number of stories in result set
     * @return Influence Stories
     */
    @RequestMapping(value = "/stories/brand:{brands}/{order}")
    @ResponseBody
    public InfluenceStories getInfluenceStoriesTotal(@PathVariable("brands") String brandList,
                                                     @PathVariable("order") String order,
                                                     @RequestParam(value = "start") LocalDate startDate,
                                                     @RequestParam(value = "stop") LocalDate stopDate,
                                                     @RequestParam(value = "authors", required = false) String authorFilter,
                                                     @RequestParam(value = "sentiments", required = false) String sentimentFilter,
                                                     @RequestParam(value = "q", required = false) String query,
                                                     @RequestParam(value = "limit", required = false) Integer limit,
                                                     @RequestParam(value = "campaignids", required = false) String campaigns)
    {
        logger.info("/influence/stories/brand:%s/%s [%s,%s,%s,%s,%s]",
                brandList, order, startDate, stopDate, sentimentFilter, query, limit);
        // Parse filter lists
        List<Integer> brandIds = MvcUtils.getIdsFromFilter(brandList);
        List<Integer> authorIds = MvcUtils.getIdsFromFilter(authorFilter);
        List<String> sentiments = MvcUtils.getStringsFromFilter(sentimentFilter);
        List<Integer> campaignIds = MvcUtils.getIdsFromFilter(campaigns);
        // Currently only support single brand in query
        if ((brandIds == null) || (brandIds.size() != 1)) {
            throw new GeneralClientException("Must specify exactly one brandId");
        }
        // Create DateTime's assuming EST
        DateTime startTime = startDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT));
        DateTime stopTime = stopDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT)).plusDays(1).minusMillis(1);
        // Call Service
        InfluenceStories stories = influenceService.getStoriesTotal(brandIds, authorIds, sentiments,
                startTime, stopTime, query, order, limit,
                campaignIds, null);
        logger.debug("..%d of %d stories found", stories.getResultCount(), stories.getTotalResultCount());
        return stories;
    }

    /**
     * Fetch the list of stories relevant to a given (brand,query) tuple, with explicit ordering.
     * Dates are assumed to be Americas/NewYork.
     *
     * @param brandList       List of brandIds for tuple (currently only one supported).
     * @param order           Order of result set (enum)
     * @param startDate       Start  Start ddate for query
     * @param stopDate        Stop   Stop date for query
     * @param authorFilter    Optional author filter (id list)
     * @param sentimentFilter Optional sentiment filter (enum list)
     * @param query           Optional query string
     * @param limit           Maximum number of stories in result set
     * @param offset          Offset for pagination
     * @return Influence Stories
     */
    @RequestMapping(value = "/facetedstories/brand:{brands}/{order}")
    @ResponseBody
    public GetInfluenceAndSentimentStoriesFacetedResponse getFacetedSearchResults(@PathVariable("brands") String brandList,
                                                     @PathVariable("order") String order,
                                                     @RequestParam(value = "start") LocalDate startDate,
                                                     @RequestParam(value = "stop") LocalDate stopDate,
                                                     @RequestParam(value = "authors", required = false) String authorFilter,
                                                     @RequestParam(value = "sentiments", required = false) String sentimentFilter,
                                                     @RequestParam(value = "q", required = false) String query,
                                                     @RequestParam(value = "offset", required = false) Integer offset,
                                                     @RequestParam(value = "limit", required = false) Integer limit,
                                                     @RequestParam(value = "ignoredpcache", defaultValue = "false") Boolean ignoreDpCache,
                                                     @RequestParam(value = "campaignid", required = false) Integer campaignId,
                                                     @RequestParam(value = "campaignonly", defaultValue = "true") Boolean campaignOnly
    )
    {
        logger.info("/influence/stories/brand:%s/%s [%s,%s,%s,%s,%s]",
                    brandList, order, startDate, stopDate, sentimentFilter, query, limit);
        // Parse filter lists
        List<Integer> brandIds = MvcUtils.getIdsFromFilter(brandList);
        List<Integer> authorIds = MvcUtils.getIdsFromFilter(authorFilter);
        List<String> sentiments = MvcUtils.getStringsFromFilter(sentimentFilter);
        // Currently only support single brand in query
        if ((brandIds == null) || (brandIds.size() != 1)) {
            throw new GeneralClientException("Must specify exactly one brandId");
        }
        // Create DateTime's assuming EST
        DateTime startTime = startDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT));
        DateTime stopTime = stopDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT)).plusDays(1).minusMillis(1);
        // Call Service
        GetInfluenceAndSentimentStoriesFacetedResponse stories = influenceService.getStoriesFaceted(campaignId, campaignOnly, brandIds, authorIds, sentiments,
                                                                    startTime, stopTime, query, order, limit,
                                                                    offset, ignoreDpCache,
                                                                    null);
        logger.debug("..%d of %d stories found", stories.getTotalResults(), stories.getLimit());
        return stories;
    }

    /**
     * Returns a list of new (unamplified) stories search result
     * @param campaignIdsStr
     * @param publishedDaysBack
     * @return
     */
    @RequestMapping(value = "/searchnewstories/campaign:{campaignIds}")
    @ResponseBody
    public GenericPayload<Map<Integer, Collection<Story>>>  searchNewStories(
            @PathVariable("campaignIds") String campaignIdsStr,
            @RequestParam(value = "pubdaysback", required = false) Integer publishedDaysBack
    )
    {
        logger.info("/searchnewstories/campaign:%s",campaignIdsStr);
        List<Integer> campaignIds = MvcUtils.getIdsFromFilter(campaignIdsStr);
        return influenceService.searchNewStories( campaignIds, publishedDaysBack, false );
    }

    /**
     * Returns a list of new (unamplified) stories search result
     * @param campaignIdsStr
     * @param publishedDaysBack
     * @return
     */
    @RequestMapping(value = "/searchnewstories_v2/campaign:{campaignIds}")
    @ResponseBody
    public GenericPayload<Map<Integer, GetInfluenceAndSentimentStoriesFacetedResponse>>  searchNewStories_v2(
            @PathVariable("campaignIds") String campaignIdsStr,
            @RequestParam(value = "pubdaysback", required = false) Integer publishedDaysBack
    )
    {
        logger.info("/searchnewstories_v2/campaign:%s",campaignIdsStr);
        List<Integer> campaignIds = MvcUtils.getIdsFromFilter(campaignIdsStr);
        return influenceService.searchNewStories_v2( campaignIds, publishedDaysBack, false );
    }



    /**
     * Fetch the list of stories relevant to a given (brand,query) tuple, with explicit ordering.
     * Dates are assumed to be Americas/NewYork.
     *
     * @param brandList       List of brandIds for tuple (currently only one supported).
     * @param order           Order of result set (enum)
     * @param startDate       Start  Start ddate for query
     * @param stopDate        Stop   Stop date for query
     * @param authorFilter    Optional author filter (id list)
     * @param sentimentFilter Optional sentiment filter (enum list)
     * @param query           Optional query string
     * @param limit           Maximum number of stories in result set
     * @return Influence Stories
     */
    @RequestMapping(value = "/stories/brand:{brands}/campaign:{campaigns}/{order}")
    @ResponseBody
    public InfluenceStories getInfluenceStoriesPaid(@PathVariable("brands") String brandList,
                                                    @PathVariable("campaigns") String campaignList,
                                                    @PathVariable("order") String order,
                                                    @RequestParam(value = "start") LocalDate startDate,
                                                    @RequestParam(value = "stop") LocalDate stopDate,
                                                    @RequestParam(value = "authors", required = false) String authorFilter,
                                                    @RequestParam(value = "sentiments", required = false) String sentimentFilter,
                                                    @RequestParam(value = "q", required = false) String query,
                                                    @RequestParam(value = "limit", required = false) Integer limit) {
        logger.info("/influence/stories/brand:%s/campaign:%s/%s [%s,%s,%s,%s,%s]",
                brandList, campaignList, order, startDate, stopDate, sentimentFilter, query, limit);
        // Parse filter lists
        List<Integer> brandIds = MvcUtils.getIdsFromFilter(brandList);
        List<Integer> campaignIds = MvcUtils.getIdsFromFilter(campaignList);
        List<Integer> authorIds = MvcUtils.getIdsFromFilter(authorFilter);
        List<String> sentiments = MvcUtils.getStringsFromFilter(sentimentFilter);
        // Currently only support single brand in query
        if ((brandIds == null) || (brandIds.size() != 1)) {
            throw new GeneralClientException("Must specify exactly one brandId");
        }
        if ((campaignIds == null) || (campaignIds.size() != 1)) {
            throw new GeneralClientException("Must specify exactly one campaignId");
        }
        // Create DateTime's assuming EST
        DateTime startTime = startDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT));
        DateTime stopTime = stopDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT)).plusDays(1).minusMillis(1);
        // Call Service
        InfluenceStories stories = influenceService.getStoriesPaid(brandIds, campaignIds, authorIds, sentiments,
                startTime, stopTime, query, order, limit);
        logger.debug("..%d of %d stories found", stories.getResultCount(), stories.getTotalResultCount());
        return stories;
    }

    /**
     * Get the story details (like the story entity call but with appended influence info)
     *
     * @param storyIds
     * @param startDate
     * @param stopDate
     */
    @RequestMapping(value = "/story/detail/{ids}")
    @ResponseBody
    public GenericPayload<Collection<Story>> getInfluenceStoryDetail(@PathVariable("ids") List<Integer> storyIds,
                                                                     @RequestParam(value = "start") LocalDate startDate,
                                                                     @RequestParam(value = "stop") LocalDate stopDate) {
        logger.info("/influence/story/detail/%s [%s,%s]", storyIds, startDate, stopDate);

        // Create DateTime's assuming EST
        DateTime startTime = startDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT));
        DateTime stopTime = stopDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(TZ_DEFAULT)).plusDays(1).minusMillis(1);

        Collection<Story> stories = influenceService.getStoryEngagements(storyIds, startTime, stopTime);

        return new GenericPayload<Collection<Story>>("stories", stories);
    }


    private InfluenceTrend cloneInfluenceWithoutTrend(InfluenceTrend trend) {
        InfluenceTrend result = null;
        if (trend != null) {
            result = new InfluenceTrend();
            result.setTotalInfluence(trend.getTotalInfluence());
            result.setStartDate(trend.getStartDate());
            result.setStopDate(trend.getStopDate());
        }
        return result;
    }

//  public void enforceMinimumDuration( Summary request, int minDays )
//  {
//    LocalDate start = request.getStart();
//    LocalDate stop = request.getStop();
//    int days = Days.daysBetween( start, stop ).getDays();
//    if( days < minDays ) {
//      LocalDate now = new LocalDate();
//      int shift = Days.daysBetween( stop, now ).getDays();
//      shift = Math.min( days, shift );
//      stop = stop.plusDays( shift );
//      shift = days-shift;
//      if( shift > 0 ) start = start.minusDays( shift );
//      logger.debug( "..Shifting date range to %s-%s", start, stop );
//    }
//    LocalDate minStop = start.plusDays( 30 );
//    if( stop.isBefore( minStop )) stop = minStop;
//    request.setStartStop( start, stop );
//  }
}
