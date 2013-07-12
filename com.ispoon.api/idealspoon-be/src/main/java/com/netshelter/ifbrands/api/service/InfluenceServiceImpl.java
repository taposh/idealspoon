package com.netshelter.ifbrands.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.netshelter.ifbrands.api.controller.BaseController;
import com.netshelter.ifbrands.api.model.ActionObjectType;
import com.netshelter.ifbrands.api.model.ActionType;
import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.campaign.Feed;
import com.netshelter.ifbrands.api.model.entity.Author;
import com.netshelter.ifbrands.etl.dataplatform.model.DpImageSpec;
import com.netshelter.ifbrands.etl.dataplatform.model.DpIndexedStory;
import com.netshelter.ifbrands.etl.dataplatform.model.FacetCounter;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceAndSentimentStoriesFacetedResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.SearchFacet;

import net.sf.cglib.core.CollectionUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.ObjectArrays;
import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.api.controller.InfluenceController;
import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.campaign.FeedStory;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.model.influence.BrandQuery;
import com.netshelter.ifbrands.api.model.influence.Influence;
import com.netshelter.ifbrands.api.model.influence.InfluenceBreakdown;
import com.netshelter.ifbrands.api.model.influence.InfluenceComparison;
import com.netshelter.ifbrands.api.model.influence.InfluenceStories;
import com.netshelter.ifbrands.api.model.influence.InfluenceSummary;
import com.netshelter.ifbrands.api.model.influence.InfluenceTrend;
import com.netshelter.ifbrands.api.model.influence.InfluenceTrend.TrendDetail;
import com.netshelter.ifbrands.etl.analytics.NapServices;
import com.netshelter.ifbrands.etl.analytics.model.GetDimensionIdResponse;
import com.netshelter.ifbrands.etl.analytics.model.GetMetricsResponse;
import com.netshelter.ifbrands.etl.analytics.model.NapDimension;
import com.netshelter.ifbrands.etl.analytics.model.NapNode;
import com.netshelter.ifbrands.etl.analytics.model.NapSlice;
import com.netshelter.ifbrands.etl.dataplatform.DpServices;
import com.netshelter.ifbrands.etl.dataplatform.DpUtils;
import com.netshelter.ifbrands.etl.dataplatform.model.DpEngagement;
import com.netshelter.ifbrands.etl.dataplatform.model.DpEngagement.DpEngagementMetric;
import com.netshelter.ifbrands.etl.dataplatform.model.DpOrderType;
import com.netshelter.ifbrands.etl.dataplatform.model.DpRollupInterval;
import com.netshelter.ifbrands.etl.dataplatform.model.DpSentiment;
import com.netshelter.ifbrands.etl.dataplatform.model.DpStory;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceAndSentimentStoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceRollupBreakdownResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceRollupTotalResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetStoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetStoryEngagementsResponse;
import com.netshelter.ifbrands.etl.transform.InfluenceStoriesTransformer;
import com.netshelter.ifbrands.etl.transform.InfluenceSummaryTransformer;
import com.netshelter.ifbrands.etl.transform.InfluenceTrendTransformer;
import com.netshelter.ifbrands.etl.transform.StoryTransformer;
import com.netshelter.ifbrands.util.ExecutorUtils;
import com.netshelter.ifbrands.util.MoreCollections;
import com.netshelter.ifbrands.util.MoreEnums;
import com.netshelter.ifbrands.util.MoreObjects;

public class InfluenceServiceImpl
        implements InfluenceService
{
    protected transient Logger logger = LoggerFactory.getLogger();
    private static Random R = new Random();

    @Autowired
    private AdService adService;
    @Autowired
    private DpServices dpService;
    @Autowired
    private NapServices napService;
    @Autowired
    private FeedService feedService;
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private InfluenceTrendTransformer influenceTrendTransformer;
    @Autowired
    private InfluenceSummaryTransformer influenceSummaryTransformer;
    @Autowired
    private InfluenceStoriesTransformer influenceStoriesTransformer;
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private ActionLogService actionLogService;

    @Autowired
    private StoryTransformer storyTransformer;

    @Override
    @CacheEvict(value = INFLUENCE_CACHE, allEntries = true)
    public void flushCache()
    {
        CacheManager manager = CacheManager.getInstance();
        Cache cache = manager.getCache(INFLUENCE_CACHE);
        cache.removeAll();
    }

    public static final String KEY_PREFIX = "InfluenceBreakdown_of_InfluenceTrendOrSummary:";
    public static final String KEY_SEPARATOR = ":";

    public static final String SHARE_BAR_PREFIX = "ip.share_bar";
    public static final String IP_OVERLAY = "ip.overlay";
	private static final String SHORTY_BAR = "ip.shorty_bar";

    @Override
    @Cacheable(INFLUENCE_CACHE)
    public InfluenceTrend getTrendTotal(Collection<Integer> brandIds,
                                        Collection<Integer> authorIds,
                                        Collection<String> sentiments,
                                        DateTime startTime, DateTime stopTime, String query)
    {
        // Parse sentiment enums
        Collection<DpSentiment> sentimentSet = DpUtils.parseSentiments(sentiments);
        GetInfluenceRollupTotalResponse response =
                dpService.getInfluenceRollupTotal(brandIds, authorIds, sentimentSet,
                                                  startTime, stopTime,
                                                  query, DpRollupInterval.DAY);
        return influenceTrendTransformer.transform(response);
    }

    @Override
    /**
     * Returns the breakdown of SUMMARY or TREND based on dates provided in campaign with
     * one exception: if the campaign stop date is in the future we change it to today's date
     *
     * Caches entities internally
     */
    @Cacheable(INFLUENCE_CACHE)
    public Map<Integer, InfluenceBreakdown<InfluenceTrend>> getBreakdownByCampaign(
            Collection<Integer> campaignIds,
            final Collection<Integer> authorIds,
            final Collection<String> sentiments,
            final Goal goal,
            final Boolean campaignOnly,
            final Boolean ignoreCache)
    {
        final Map<Integer, InfluenceBreakdown<InfluenceTrend>> result =
                Collections.synchronizedMap(
                        new Hashtable<Integer, InfluenceBreakdown<InfluenceTrend>>(campaignIds.size()));
        final List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
        for (Iterator<Integer> iterator = campaignIds.iterator(); iterator.hasNext(); )
        {
            final Integer campaignId = iterator.next();
            final Campaign campaign = campaignService.getCampaign(campaignId);
            final DateTime campaignStartTime = campaign.getStartDate()
                    .toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(InfluenceController.TZ_DEFAULT));

            // this is where we check if the campaign date is in the future
            LocalDate stopDate = campaign.getStopDate();
            LocalDate now = LocalDate.now(DateTimeZone.forTimeZone(InfluenceController.TZ_DEFAULT));
            if (stopDate.isAfter(now))
            {
                stopDate = now;
            }
            final DateTime campaignStopTime = stopDate
                    .toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(InfluenceController.TZ_DEFAULT))
                    .plusDays(1)
                    .minusMillis(1);
            tasks.add(
                    new Callable<Void>()
                    {
                        @Override
                        public Void call()
                                throws
                                Exception
                        {
                            try
                            {
                                Collection<Integer> brandIdCollection =
                                        (campaign.getBrand() != null ? Collections.singleton(
                                                campaign.getBrand().getId()) : null);
                                InfluenceBreakdown<InfluenceTrend> influenceBreakdown = getBreakdown(
                                        brandIdCollection,
                                        Collections.singleton(campaignId),
                                        authorIds,
                                        sentiments,
                                        campaignStartTime,
                                        campaignStopTime,
                                        campaign.getKeywords(),
                                        goal.name(),
                                        campaignOnly,
                                        ignoreCache);
                                result.put(campaignId, influenceBreakdown);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }
            );
        }
        if (tasks.size() > 0)
        {
            ExecutorUtils.invokeAll(executorService, tasks);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Cacheable(INFLUENCE_CACHE)
    /**
     * Integrates internal use of ehCache to cache InfluenceBreakdown objects for requested parameters
     */
    public InfluenceBreakdown<InfluenceTrend> getBreakdown(Collection<Integer> brandIds,
                                                           Collection<Integer> campaignIds,
                                                           Collection<Integer> authorIds,
                                                           Collection<String> sentiments, DateTime startTime,
                                                           DateTime stopTime, String query, String goal,
                                                           Boolean campaignOnly,
                                                           Boolean ignoreCache)
    {
        InfluenceBreakdown<InfluenceTrend> result = null;
        // Convert CampaignId's into CampaignKeys's
        Collection<String> campaignKeys = campaignService.getCampaignKeys(campaignIds);
        // Parse sentiment enums
        Collection<DpSentiment> sentimentSet = DpUtils.parseSentiments(sentiments);
        // Fetch breakdown
        DpRollupInterval interval;
        Goal g = MoreEnums.parse(goal, Goal.class);
        switch (g)
        {
            case SUMMARY:
                interval = DpRollupInterval.INFINITE;
                break;
            case TREND:
                interval = DpRollupInterval.DAY;
                break;
            default:
                throw new IllegalArgumentException("Goal " + goal + " not supported.");
        }


        CacheManager manager = CacheManager.getInstance();
        Cache cache = manager.getCache(INFLUENCE_CACHE);

        String key = KEY_PREFIX + "_" + interval + "_" +
                (
                        brandIds + KEY_SEPARATOR +
                                campaignKeys + KEY_SEPARATOR +
                                authorIds + KEY_SEPARATOR +
                                sentimentSet + KEY_SEPARATOR +
                                startTime + KEY_SEPARATOR +
                                stopTime + KEY_SEPARATOR +
                                campaignOnly + KEY_SEPARATOR +
//                    query + KEY_SEPARATOR +
                                interval
                ).hashCode();

        if (cache != null)
        {
            Element element = null;
            if (!ignoreCache)
            {
                element = cache.get(key);
            }
            if (element != null && element.getObjectValue() instanceof InfluenceBreakdown)
            {
                logger.debug("Getting from cache, key:" + key);
                result = (InfluenceBreakdown<InfluenceTrend>) element.getObjectValue();
            }
        }
        if (result == null)
        {
            GetInfluenceRollupBreakdownResponse response =
                    dpService.getInfluenceRollupBreakdown(brandIds, campaignKeys, authorIds, sentimentSet,
                                                          startTime, stopTime, query, interval, campaignOnly);

            // Only do the Analytics call to get impressionServes if we're doing the aggregation call (not trending call)
            if (interval.equals(DpRollupInterval.INFINITE))
            {
                InfluenceComparison<InfluenceSummary> napResult = getLeafNodeSummaries(startTime, stopTime,
                                                                                       NapDimension.CAMPAIGN, null,
                                                                                       campaignKeys);
                long napImpressionServes = napResult.get(
                        MoreCollections.firstOrNull(campaignKeys)).getTotalInfluence().getImpressions();

                response.getPaid().getTotalInfluence().setImpressions(napImpressionServes);
            }

            result = influenceTrendTransformer.transform(response);
            if (cache != null)
            {
                logger.debug("Inserting into cache, key:" + key);
                cache.put(new Element(key, result));
            }
        }

        ////////////////////////////
        // DEMO HACK
        ////////////////////////////
        Collection<Campaign> demos = new ArrayList<Campaign>();
        CampaignServiceImpl csi = (CampaignServiceImpl) campaignService;
        for (Integer cid : campaignIds)
        {
            Campaign c = campaignService.getCampaign(cid);
            if ((c != null) && (csi.isDemo(c)))
            {
                demos.add(c);
            }
        }
        if (!demos.isEmpty())
        {
            Campaign c = campaignService.getCampaign(campaignIds.iterator().next());
            Influence oTotal = result.getOrganic().getTotalInfluence();
            Influence pTotal = new Influence();
            Influence tTotal = new Influence();
            double[] fudge = doFudge(c, oTotal, pTotal);
            double days = result.getPaid().getTrend().size();
            tTotal.add(oTotal);
            tTotal.add(pTotal);

            if (Goal.TREND.equals(g))
            {
                // We'll iterate through each TrendDetail in lock-step
                Iterator<TrendDetail> pIter = result.getPaid().getTrend().iterator();
                Iterator<TrendDetail> tIter = result.getTotal().getTrend().iterator();
                Iterator<TrendDetail> oIter = result.getOrganic().getTrend().iterator();
                while (oIter.hasNext())
                {
                    TrendDetail pDetail = pIter.next();
                    TrendDetail tDetail = tIter.next();
                    TrendDetail oDetail = oIter.next();
                    // Distribute desired fudge evenly across all days +/- 10% wobble
                    // This does not affect totals so we can be creative
                    double x = fudge[PFUDGE] / days * (R.nextFloat() * 0.21234 + 0.91234);
                    pDetail.getInfluence().setPeopleInfluenced((int) x);
                    pDetail.getInfluence().setInfluencers((int) (fudge[IFUDGE] / days));
                    pDetail.getInfluence().setUniqueCount(3);
                    // Tally detail total
                    tDetail.setInfluence(new Influence());
                    tDetail.getInfluence().add(oDetail.getInfluence());
                    tDetail.getInfluence().add(pDetail.getInfluence());
                }
            }
            result.getPaid().setTotalInfluence(pTotal);
            result.getTotal().setTotalInfluence(tTotal);
        }
        return result;
    }

    @Override
    @Cacheable(INFLUENCE_CACHE)
    public InfluenceComparison<InfluenceTrend> getTrendComparison(final Collection<BrandQuery> series,
                                                                  final Collection<Integer> authorIds,
                                                                  final Collection<String> sentiments,
                                                                  final DateTime startTime,
                                                                  final DateTime stopTime)
    {
        // Results holder
        InfluenceComparison<InfluenceTrend> results = new InfluenceComparison<InfluenceTrend>();
        final Map<String, InfluenceTrend> syncMap = Collections.synchronizedMap(results);
        // Iterate on series to create tasks
        List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
        for (final BrandQuery bq : series)
        {
            // Fetch trend
            tasks.add(new Callable<Void>()
            {
                @Override
                public Void call()
                {
                    Collection<Integer> brandList = null;
                    if (bq.getBrandId() != null)
                    {
                        brandList = Collections.singleton(bq.getBrandId());
                    }
                    // Get the trend
                    InfluenceTrend trend = getTrendTotal(brandList, authorIds, sentiments, startTime, stopTime,
                                                         bq.getQuery());
                    // Store series
                    syncMap.put(bq.getSpec(), trend);
                    return null;
                }
            });
        }
        // Execute and wait on all
        ExecutorUtils.invokeAll(executorService, tasks);
        return results;
    }

    @Override
//    @Cacheable(INFLUENCE_CACHE) cachable annotation is not
// needed because we are caching within the method
    public InfluenceComparison<InfluenceSummary> getSummaryPaidByCampaign(final Collection<Integer> campaignIds,
                                                                          final DateTime startTimeOptional,
                                                                          final DateTime stopTimeOptional,
                                                                          final boolean ignoreCache)
    {
        if (campaignIds == null) throw new IllegalArgumentException("Must specify at least one campaignId");
        InfluenceComparison<InfluenceSummary> response = new InfluenceComparison<InfluenceSummary>();
        List<Callable<Map<Integer, InfluenceComparison<InfluenceSummary>>>> tasks = new ArrayList<Callable<Map<Integer, InfluenceComparison<InfluenceSummary>>>>();

        CacheManager manager = CacheManager.getInstance();
        final Cache cache = manager.getCache(INFLUENCE_CACHE);

        for (final Integer campaignId : campaignIds)
        {
            // Fetch trend
            tasks.add(new Callable<Map<Integer, InfluenceComparison<InfluenceSummary>>>()
            {
                @Override
                public Map<Integer, InfluenceComparison<InfluenceSummary>> call()
                {
                    Map<Integer, InfluenceComparison<InfluenceSummary>> entityResponse =
                            Collections.synchronizedMap(new HashMap<Integer, InfluenceComparison<InfluenceSummary>>());
                    try
                    {
                        long ms = System.currentTimeMillis();
                        InfluenceComparison<InfluenceSummary> resultCampaign = null;
                        CampaignServiceImpl csi = (CampaignServiceImpl) campaignService;
                        Campaign c = campaignService.getCampaign(campaignId);

                        DateTime startTime;
                        DateTime stopTime;
                        DateTime now = DateTime.now(DateTimeZone.forTimeZone(BaseController.TZ_DEFAULT))
                                .withTime(23,59,59,999);

                        if (campaignIds.size() == 1 && startTimeOptional != null && stopTimeOptional != null)
                        {
                            startTime = startTimeOptional;
                            stopTime = stopTimeOptional;
                        }
                        else
                        {
                            startTime = c.getStartDate().toDateTimeAtStartOfDay(
                                    DateTimeZone.forTimeZone(BaseController.TZ_DEFAULT));
                            stopTime = c.getStopDate().toDateTimeAtStartOfDay(
                                    DateTimeZone.forTimeZone(BaseController.TZ_DEFAULT)).plusDays(1).minusMillis(1);
                        }
                        if (stopTime.isAfter(now))
                        {
                            stopTime = now;
                        }
                        // By now we have all we need to create key for the caching mechanism
                        String key = "getSummaryPaidByCampaign("+campaignId+
                                ","+startTime.toDate().getTime()+
                                ","+stopTime.toDate().getTime()+")";
                        logger.debug("Using key: " + key);
                        if (cache != null && !ignoreCache)
                        {
                            Element element = cache.get(key);
                            if (element != null)
                            {
                                logger.debug("Retrieving data from cache using key: " + key);
                                resultCampaign = (InfluenceComparison<InfluenceSummary>)element.getObjectValue();
                            }
                        }

                        if (resultCampaign == null && campaignId != null)
                        {
                            NapSlice[] shareBarSlices = new NapSlice[2];
                            Integer shareBarId = convertToNapId( NapDimension.CREATIVE, "ip.share_bar.v5" );
                            Integer napId = convertToNapId( NapDimension.CAMPAIGN, campaignId );
                            if (napId != null)
                            {
                                shareBarSlices[0] = new NapSlice( NapDimension.CAMPAIGN, napId );
                                shareBarSlices[1] = new NapSlice( NapDimension.CREATIVE, shareBarId );

                                resultCampaign = getLeafNodeSummaries(
                                        startTime,
                                        stopTime,
                                        NapDimension.CAMPAIGN,
                                        Collections.singleton(campaignId),
                                        null
                                );

                                InfluenceComparison<InfluenceSummary> resultSharebar = getLeafNodeSummaries(
                                        startTime,
                                        stopTime,
                                        NapDimension.CAMPAIGN,
                                        Collections.singleton(campaignId),
                                        null
                                        ,shareBarSlices
                                );


                                shareBarId = convertToNapId(NapDimension.CREATIVE, IP_OVERLAY);
                                shareBarSlices[0] = new NapSlice(NapDimension.CAMPAIGN, convertToNapId(NapDimension.CAMPAIGN, campaignId));
                                shareBarSlices[1] = new NapSlice(NapDimension.CREATIVE, shareBarId);
                                InfluenceComparison<InfluenceSummary> resultOverlay = getLeafNodeSummaries(
                                        startTime,
                                        stopTime,
                                        NapDimension.CAMPAIGN,
                                        Collections.singleton(campaignId),
                                        null
                                        ,shareBarSlices
                                );
                                
                                shareBarId = convertToNapId(NapDimension.CREATIVE, SHORTY_BAR);
                                shareBarSlices[0] = new NapSlice(NapDimension.CAMPAIGN, convertToNapId(NapDimension.CAMPAIGN, campaignId));
                                shareBarSlices[1] = new NapSlice(NapDimension.CREATIVE, shareBarId);
                                InfluenceComparison<InfluenceSummary> resultShortybar = getLeafNodeSummaries(
                                        startTime,
                                        stopTime,
                                        NapDimension.CAMPAIGN,
                                        Collections.singleton(campaignId),
                                        null
                                        ,shareBarSlices
                                );

                                // At this point we have the response object ready - and we
                                // have to add a SurveyResult list for every campaign / ad
                                Set keys = resultCampaign.keySet();
                                for (Iterator iterator = keys.iterator(); iterator.hasNext(); )
                                {
                                    String k = (String) iterator.next();
                                    Integer campaignId = null;
                                    try
                                    {
                                        campaignId = Integer.parseInt(k);
                                    }
                                    catch (NumberFormatException e)
                                    {
                                        /* this might have been a share-bar key which is not a number (NOT adID) - no need to print the exception stack */
                                    }
                                    if (campaignId != null)
                                    {
                                        String campaignKey = campaignService.getCampaign(campaignId).getCampaignKey();
                                        List<SurveyServiceImpl.SurveyResult> resultList =
                                                surveyService.getSurveyResults(
                                                        campaignKey,
                                                        null,
                                                        null,
                                                        startTime,
                                                        stopTime,
                                                        null);
                                        if (resultList != null && resultList.size() > 0)
                                        {
                                            resultCampaign.get(k).getTotalInfluence().setSurveyResultList(resultList);
                                        }
                                    }
                                }


                                if (resultCampaign.get(c.getId().toString()).getTotalInfluence() != null &&
                                        resultOverlay.get(c.getId().toString()).getTotalInfluence() != null)
                                {
                                    // subtract values from
                                    long origImpressions = resultCampaign.get(c.getId().toString()).getTotalInfluence().getImpressions();
                                    long overlayImpressions = resultOverlay.get(c.getId().toString()).getTotalInfluence().getImpressions();
                                    resultCampaign.get(c.getId().toString()).getTotalInfluence().setImpressions( origImpressions - overlayImpressions );
                                }

                                if (resultCampaign.get(c.getId().toString()).getTotalInfluence() != null &&
                                        resultSharebar.get(c.getId().toString()).getTotalInfluence() != null)
                                {
                                    resultCampaign.get(c.getId().toString()).getTotalInfluence().setStoryImpressions(
                                            resultSharebar.get(c.getId().toString()).getTotalInfluence().getStoryImpressions()
                                    );
                                    long origImpressions = resultCampaign.get(c.getId().toString()).getTotalInfluence().getImpressions();
                                    long sharebarImpressions = resultSharebar.get(c.getId().toString()).getTotalInfluence().getImpressions();
                                    resultCampaign.get(c.getId().toString()).getTotalInfluence().setImpressions( origImpressions - sharebarImpressions );
                                }
                                
                                if (resultCampaign.get(c.getId().toString()).getTotalInfluence() != null &&
                                        resultShortybar.get(c.getId().toString()).getTotalInfluence() != null)
                                {
                                    resultCampaign.get(c.getId().toString()).getTotalInfluence().setStoryImpressions(
                                    		resultCampaign.get(c.getId().toString()).getTotalInfluence().getStoryImpressions() +                                            
                                    		resultShortybar.get(c.getId().toString()).getTotalInfluence().getStoryImpressions()
                                    );
                                    long origImpressions = resultCampaign.get(c.getId().toString()).getTotalInfluence().getImpressions();
                                    long shortybarImpressions = resultShortybar.get(c.getId().toString()).getTotalInfluence().getImpressions();
                                    resultCampaign.get(c.getId().toString()).getTotalInfluence().setImpressions( origImpressions - shortybarImpressions );
                                }

                                if (csi.isDemo(c))
                                {
                                    final int cnt = campaignIds.size();
                                    InfluenceBreakdown<InfluenceTrend> breakdown = null;
                                    if (breakdown == null)
                                    {
                                        breakdown = getBreakdown(Collections.singleton(c.getBrand().getId()),
                                                                 Collections.singleton(c.getId()),
                                                                 null, Arrays.asList("positive", "neutral"),
                                                                 startTime, stopTime, c.getKeywords(), "summary", false, false);
                                    }
                                    double w = getWobble(cnt, 1);
                                    Influence paid = breakdown.getPaid().getTotalInfluence();
                                    Influence item = resultCampaign.get(c.getId().toString()).getTotalInfluence();
                                    item.setPeopleInfluenced((int) ((double) paid.getPeopleInfluenced() / cnt * w));
                                    item.setInfluencers((int) ((double) paid.getInfluencers() / cnt * w));
                                    item.setImpressions((int) ((double) paid.getImpressions() / cnt * w));
                                    item.setUniqueCount(paid.getUniqueCount());
                                    logger.info("Salting campaign payload with fabricated data (paid=%s, item=%s, wobble=%f)", paid, item,
                                                w);
                                }

                                long storyTimeSpent = resultCampaign.get(campaignId.toString()).getTotalInfluence().getStoryTimeSpent();
                                if ( storyTimeSpent > 0 )
                                {
                                    resultCampaign.get(campaignId.toString()).getTotalInfluence().setStoryTimeSpent(
                                            resultCampaign.get(campaignId.toString()).getTotalInfluence().getStoryTimeSpent() / 1000
                                    );
                                }

                                if (cache != null)
                                {
                                    actionLogService.makeLogEntry(
                                            ActionType.CAMPAIGN_CACHED,
                                            ActionObjectType.CAMPAIGN,
                                            campaignId,
                                            null,
                                            "took: " + (System.currentTimeMillis() - ms) + "ms key: [" + key + "]"
                                    );
                                    logger.debug("Inserting data into cache with key: " + key);
                                    cache.put(new Element(key, resultCampaign));
                                }
                            }

                        }
                        entityResponse.put(campaignId, resultCampaign);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    return entityResponse;
                }
            });

        }



        Map<Integer, InfluenceComparison<InfluenceSummary>> tmpResponse = new HashMap<Integer, InfluenceComparison<InfluenceSummary>>();
        // Execute and wait on all
        List<Future<Map<Integer, InfluenceComparison<InfluenceSummary>>>> futures = ExecutorUtils.invokeAll(
                executorService, tasks);
        for (int i = 0; i < futures.size(); i++)
        {
            Future<Map<Integer, InfluenceComparison<InfluenceSummary>>> mapFuture = futures.get(i);
            try
            {
                tmpResponse.putAll( mapFuture.get() );
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }


        Set<Integer> keys = tmpResponse.keySet();
        for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext(); )
        {
            Integer nextId = iterator.next();
            if (nextId != null &&
                    tmpResponse.get(nextId) != null &&
                    tmpResponse.get(nextId).get(nextId.toString()) != null &&
                    tmpResponse.get(nextId).get(nextId.toString()).getTotalInfluence() != null)
            {
                response.put(nextId.toString(), tmpResponse.get(nextId).get(nextId.toString()));
            }
        }

        // nice idea on how to sort the Map by some fields from the Value element object
        // TODO: http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
        return response;
    }

    @Override
    @Cacheable(INFLUENCE_CACHE)
    public InfluenceComparison<InfluenceSummary> getSummaryPaidByAd(Integer campaignId,
                                                                    Collection<Integer> adIds,
                                                                    Collection<Integer> adStatusIds,
                                                                    DateTime startTime,
                                                                    DateTime stopTime)
    {
        // If no ads specified, get all from this campaign
        if (adIds == null)
        {
            Collection<Ad> ads = adService.getAds(null, null, null, adStatusIds, Collections.singleton(campaignId));
            adIds = new ArrayList<Integer>(ads.size());
            for (Ad a : ads)
            {
                adIds.add(a.getId());
            }
        }
        // Get NAP Id
        Integer napCampaignId = convertToNapId(NapDimension.CAMPAIGN, campaignId);
        // If not found in NAP then return zeroes
        if (napCampaignId == null)
        {
            InfluenceComparison<InfluenceSummary> result = new InfluenceComparison<InfluenceSummary>().setToZero(adIds,
                                                                                                                 new InfluenceSummary().setToZero());
            ////////////////////////////
            // DEMO HACK
            ////////////////////////////
            CampaignServiceImpl csi = (CampaignServiceImpl) campaignService;
            Campaign c = campaignService.getCampaign(campaignId);
            if ((c != null) && (csi.isDemo(c)))
            {
                InfluenceComparison<InfluenceSummary> fake = getSummaryPaidByCampaign(Collections.singleton(campaignId),
                                                                                      startTime, stopTime, false);
                if (fake.values().iterator().hasNext())
                {
                    Influence cPaid = fake.values().iterator().next().getTotalInfluence();
                    int cnt = adIds.size();
                    int num = 0;
                    for (Integer a : adIds)
                    {
                        double w = getWobble(cnt, num);
                        num++;
                        InfluenceSummary aSumm = new InfluenceSummary().setToZero();
                        Influence aPaid = aSumm.getTotalInfluence();
                        aPaid.setPeopleInfluenced((int) ((double) cPaid.getPeopleInfluenced() / cnt * w));
                        aPaid.setInfluencers((int) ((double) cPaid.getInfluencers() / cnt * w));
                        aPaid.setImpressions((int) ((double) cPaid.getImpressions() / cnt * w));
                        aPaid.setUniqueCount(cPaid.getUniqueCount());
                        result.put(a.toString(), aSumm);
                        logger.info("Salting ad payload with fabricated data (camp=%s, ad=%s, wobble=%f)", cPaid, aPaid, w);
                    }
                }
            }
            return result;
        }
        InfluenceComparison<InfluenceSummary> result = getRollupSummaries(startTime, stopTime, NapDimension.CREATIVE,
                                                                          adIds, null,
                                                                          new NapSlice(NapDimension.CAMPAIGN,
                                                                                       napCampaignId),
                                                                          new NapSlice(NapDimension.CREATIVE),
                                                                          new NapSlice(NapDimension.PARENT));

        // At this point we have the response object ready - and we
        // have to add a SurveyResult list for every campaign / ad
        Set keys = result.keySet();
        for (Iterator iterator = keys.iterator(); iterator.hasNext(); )
        {
            String key = (String) iterator.next();
            Integer adId = null;
            try
            {
                adId = Integer.parseInt(key);
            }
            catch (NumberFormatException e)
            {
                /* this might have been a share-bar key which is not a number (NOT adID) - no need to print the exception stack */
            }
            if (adId != null)
            {
                Ad ad = adService.getAd(adId);
                if (ad != null)
                {
                    String adKey = ad.getKey();
                    String campaignKey = campaignService.getCampaign(campaignId).getCampaignKey();
                    List<SurveyServiceImpl.SurveyResult> resultList =
                            surveyService.getSurveyResults(
                                    campaignKey,
                                    adKey,
                                    null,
                                    startTime,
                                    stopTime,
                                    null);
                    if (resultList != null && resultList.size() > 0)
                    {
                        result.get(key).getTotalInfluence().setSurveyResultList(resultList);
                    }
                }
            }
        }
        return result;
    }

    @Override
    @Cacheable(INFLUENCE_CACHE)
    public InfluenceComparison<InfluenceSummary> getSummaryPaidByStory(Integer campaignId,
                                                                       Integer adId,
                                                                       Collection<Integer> storyStatusIds,
                                                                       DateTime startTime,
                                                                       DateTime stopTime)
    {
        // Fetch story ids associated feed & story ids
        Ad ad = adService.getAd(adId);
        if (ad == null) throw new IllegalArgumentException("Cannot find ad " + adId);
        Collection<FeedStory> feedStories = feedService.getFeedStories(ad.getFeed().getId(), storyStatusIds);

        if ( feedStories == null || feedStories.size() == 0 ) {
          return new InfluenceComparison<InfluenceSummary>();
        }
        
        // Convert individual FeedStory objects into their DP IDs
        Collection<Integer> dpStoryIds = new ArrayList<Integer>();
        for (FeedStory fs : feedStories)
        {
            dpStoryIds.add(fs.getStoryId());
        }
        // Fetch story info based on ID
        GetStoriesResponse r = dpService.getStories(dpStoryIds);
        // Create map of NAP compatible asset names and actual DpStory IDs
        Map<String, Integer> assetNameToIdMap = new HashMap<String, Integer>();
        for (DpStory s : r.getStories())
        {
            assetNameToIdMap.put("story.n2ih." + s.getHash(), s.getStoryId());
        }

        // Get other NAP ids
        Integer napCampaignId = convertToNapId(NapDimension.CAMPAIGN, campaignId);
        Integer napCreativeId = convertToNapId(NapDimension.CREATIVE, adId);
        // If not found in NAP return zeroes
        if ((napCampaignId == null) || (napCreativeId == null))
        {
            InfluenceComparison<InfluenceSummary> result = new InfluenceComparison<InfluenceSummary>().setToZero(
                    assetNameToIdMap.values(), new InfluenceSummary().setToZero());
            ////////////////////////////
            // DEMO HACK
            ////////////////////////////
            CampaignServiceImpl csi = (CampaignServiceImpl) campaignService;
            Campaign c = campaignService.getCampaign(campaignId);
            if ((c != null) && (csi.isDemo(c)))
            {
//        int[] stats = getCampaignStats( c, startTime );
//        logger.info( "Salting payload with fabricated paid data (total=%d)...", stats[TOTAL] );
//        Iterator<InfluenceSummary> summIter = result.values().iterator();
//        while( summIter.hasNext() ) {
//          doFudge( summIter.next().getTotalInfluence(), stats );
//        }
            }
            return result;
        }
        // Get actual Influence info, keyed by assetName
        InfluenceComparison<InfluenceSummary> c = getLeafNodeSummaries(startTime, stopTime, NapDimension.ASSET,
                                                                       null, assetNameToIdMap.keySet(),
                                                                       new NapSlice(NapDimension.CAMPAIGN,
                                                                                    napCampaignId),
                                                                       new NapSlice(NapDimension.CREATIVE,
                                                                                    napCreativeId));
        // Iterate all returned values, switching assetName for DpStoryId
        InfluenceComparison<InfluenceSummary> result = new InfluenceComparison<InfluenceSummary>();
        Iterator<Map.Entry<String, InfluenceSummary>> iter = c.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry<String, InfluenceSummary> entry = iter.next();
            // Get DpStoryId
            Integer id = assetNameToIdMap.get(entry.getKey());
            if (id == null) throw new IllegalStateException("Cannot match hash to story id: " + entry.getKey());
            // Add to result, remove from c
            result.put(id.toString(), entry.getValue());
            iter.remove();
        }

        Set keys = result.keySet();
        for (Iterator iterator = keys.iterator(); iterator.hasNext(); )
        {
            String key = (String) iterator.next();
            Integer dpStoryId = null;
            try
            {
                dpStoryId = Integer.parseInt(key);
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
            if (dpStoryId != null)
            {
                String adKey = adService.getAd(adId).getKey();
                String campaignKey = campaignService.getCampaign(campaignId).getCampaignKey();
                List<SurveyServiceImpl.SurveyResult> resultList =
                        surveyService.getSurveyResults(campaignKey, adKey, dpStoryId, startTime, stopTime, null);
                if (resultList != null && resultList.size() > 0)
                {
                    result.get(key).getTotalInfluence().setSurveyResultList(resultList);
                }
            }
        }

        return result;
    }

    /**
     * Returns a Story-Level Analytics for Campaign
     * @param campaignId
     * @param storyStatusIds
     * @param startTime
     * @param stopTime
     * @return
     */
    @Override
    @Cacheable(INFLUENCE_CACHE)
    public InfluenceComparison<InfluenceSummary> getSummaryPaidByCampaignStoryLevel(Integer campaignId,
                                                                                    Collection<Integer> storyStatusIds,
                                                                                    DateTime startTime, DateTime stopTime)
    {
        if( campaignId == null ) throw new IllegalArgumentException( "Cannot find campaign "+ campaignId );
        // unique set
        LinkedHashSet<Ad> adList = new LinkedHashSet(
                adService.getAds( null, null, null, null, Collections.singleton( campaignId ) )
        );
        Collection<Integer> dpStoryIds = new ArrayList<Integer>();
        Collection<Integer> feedIds = new ArrayList<Integer>();
        for (Ad ad : adList)
        {
          feedIds.add( ad.getFeed().getId() );
        }

        Collection<FeedStory> feedStories = feedService.getFeedStories( feedIds );
        
        if ( feedStories == null || feedStories.size() == 0 ) {
          return new InfluenceComparison<InfluenceSummary>();
        }
        
        for( FeedStory fs: feedStories )
        {
            dpStoryIds.add( fs.getStoryId() );
        }

        // Fetch story info based on ID
        GetStoriesResponse r = dpService.getStories( dpStoryIds );
        // Create map of NAP compatible asset names and actual DpStory IDs
        Map<String,Integer> assetNameToIdMap = new HashMap<String,Integer>();
        for( DpStory s: r.getStories() ) {
            assetNameToIdMap.put( "story.n2ih."+ s.getHash(), s.getStoryId() );
        }

        // Get other NAP ids
        Integer napCampaignId = convertToNapId( NapDimension.CAMPAIGN, campaignId );
        // If not found in NAP return zeroes
        if ( napCampaignId == null ) {
            InfluenceComparison<InfluenceSummary> result = new InfluenceComparison<InfluenceSummary>()
                    .setToZero( assetNameToIdMap.values(), new InfluenceSummary().setToZero() );
            ////////////////////////////
            // DEMO HACK
            ////////////////////////////
            CampaignServiceImpl csi = (CampaignServiceImpl)campaignService;
            Campaign c = campaignService.getCampaign( campaignId );
            if(( c != null )&&( csi.isDemo( c ))) {
            }
            return result;
        }
        // Get actual Influence info, keyed by assetName
        InfluenceComparison<InfluenceSummary> c = getLeafNodeSummariesAggregated(startTime, stopTime,
                                                                                 NapDimension.ASSET,
                                                                                 null, assetNameToIdMap.keySet(),
                                                                                 new NapSlice(NapDimension.CAMPAIGN,
                                                                                              napCampaignId));
        // Iterate all returned values, switching assetName for DpStoryId
        InfluenceComparison<InfluenceSummary> result = new InfluenceComparison<InfluenceSummary>();
        Iterator<Map.Entry<String,InfluenceSummary>> iter = c.entrySet().iterator();
        while( iter.hasNext() ) {
            Map.Entry<String,InfluenceSummary> entry = iter.next();
            // Get DpStoryId
            Integer id = assetNameToIdMap.get( entry.getKey() );
//            if ( id == null ) throw new IllegalStateException( "Cannot match hash to story id: "+ entry.getKey() );
            // Add to result, remove from c
            if (id != null)
            {
                result.put( id.toString(), entry.getValue() );
            }
            iter.remove();
        }
        return result;
    }

    /**
     * Fetch a tree of data from NAP and get InfluenceSummary objects from the leaf nodes.
     * The "slices" param is a set of NapSlice objects which describe the tree exclusive
     * of the leaf level.  The "leaf" parameters then describe the leaf layer.  Since each
     * leaf request is made independently (in parallel) we know that every tree returned will
     * actually be a linear graph and if the graph depth is equal to our expected depth the
     * destired data must be that data contained in the leaf node.  Note that a request to
     * NAP may not actually return any data in which case we should return a zero object.
     * <p/>
     * Exactly one of leafIds/leafNames must be specified.
     *
     * @param startTime Start DateTime of query
     * @param stopTime Stop DateTime of query
     * @param leafDimension Dimension of leaf node
     * @param leafIds Filter ID set for leaf node (NAP ids)
     * @param leafNames Filter name set for leaf node (NAP names)
     * @param slices Additional hyper-query slices, from root
     * @return Influence Summaries, keyed by ID
     */
    private InfluenceComparison<InfluenceSummary> getLeafNodeSummaries(final DateTime startTime,
                                                                       final DateTime stopTime,
                                                                       final NapDimension leafDimension,
                                                                       final Collection<Integer> leafIds,
                                                                       final Collection<String> leafNames,
                                                                       final NapSlice... slices)
    {
        if (!(leafIds != null) ^ (leafNames != null))
        {
            throw new IllegalArgumentException("Must specify exactly one of 'leafIds' or 'leafNames'");
        }
        // Results holder
        InfluenceComparison<InfluenceSummary> results = new InfluenceComparison<InfluenceSummary>();
        final Map<String, InfluenceSummary> syncMap = Collections.synchronizedMap(results);
        // Iterate on ids to create tasks
        final LocalDate startDate = startTime.toLocalDate();
        final LocalDate stopDate = stopTime.toLocalDate();
        List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
        Iterable<?> iterable = MoreObjects.ifNull(leafIds, leafNames);
        for (final Object leaf : iterable)
        {
            tasks.add(new Callable<Void>()
            {
                @Override
                public Void call()
                {
                    // Get NAP id from IFB id
                    InfluenceSummary infSummary = null;
                    Integer napId = convertToNapId(leafDimension, leaf);
                    if (napId != null)
                    {
                        // We have a valid ID, so request data from NAP
                        NapSlice leafSlice = new NapSlice(leafDimension, napId);
                        GetMetricsResponse response = null;
                        try
                        {
                            response = napService.getTotalMetrics(startDate, stopDate,
                                                                  ObjectArrays.concat(slices, leafSlice));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        GetMetricsResponse mediaMetricsResponse = null;
                        GetMetricsResponse mediaMetricsResponse2 = null;
                        if (slices != null && slices.length > 0)
                        {
                            try {
                                NapSlice[] shareBarSlices = new NapSlice[slices.length + 2];
                                int shareBarSlicesCounter = 0;
                                for (NapSlice slice : slices)
                                {
                                    if (slice.getDimension().equals(NapDimension.CAMPAIGN))
                                    {
                                        shareBarSlices[shareBarSlicesCounter] = new NapSlice(NapDimension.CAMPAIGN, slice.getId());
                                        shareBarSlicesCounter++;
                                    }
                                    if (slice.getDimension().equals(NapDimension.CREATIVE))
                                    {
                                        shareBarSlices[shareBarSlicesCounter] = new NapSlice(NapDimension.PARENT, slice.getId());
                                        shareBarSlicesCounter++;
                                    }
                                }
                                Integer shareBarId = convertToNapId(NapDimension.CREATIVE, "ip.share_bar.v5");
                                shareBarSlices[shareBarSlicesCounter] = new NapSlice(NapDimension.CREATIVE, shareBarId);
                                shareBarSlicesCounter++;
                                shareBarSlices[shareBarSlicesCounter] = new NapSlice(NapDimension.ASSET, napId);

                                mediaMetricsResponse = napService.getShareBarMediaMetrics(startDate, stopDate,
                                                                                          shareBarSlices);
                                
                                NapSlice[] shortyBarSlices = new NapSlice[slices.length + 2];
                                int shortyBarSlicesCounter = 0;
                                for (NapSlice slice : slices)
                                {
                                    if (slice.getDimension().equals(NapDimension.CAMPAIGN))
                                    {
                                    	shortyBarSlices[shortyBarSlicesCounter] = new NapSlice(NapDimension.CAMPAIGN, slice.getId());
                                        shortyBarSlicesCounter++;
                                    }
                                    if (slice.getDimension().equals(NapDimension.CREATIVE))
                                    {
                                    	shortyBarSlices[shortyBarSlicesCounter] = new NapSlice(NapDimension.PARENT, slice.getId());
                                        shortyBarSlicesCounter++;
                                    }
                                }
                                Integer shortyBarId = convertToNapId(NapDimension.CREATIVE, SHORTY_BAR);
                                shortyBarSlices[shortyBarSlicesCounter] = new NapSlice(NapDimension.CREATIVE, shortyBarId);
                                shortyBarSlicesCounter++;
                                shortyBarSlices[shortyBarSlicesCounter] = new NapSlice(NapDimension.ASSET, napId);

                                mediaMetricsResponse2 = napService.getShortyBarMediaMetrics(startDate, stopDate,
                                		shortyBarSlices);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        // Data is in leaf node (if it exists)
                        if (!response.getMetrics().isEmpty())
                        {
                            try
                            {
                                int depth = 1 + slices.length;
                                // Start with root node
                                NapNode dataNode = response.getMetrics().iterator().next();
                                // Iterate (d-1) more times
                                for (int i = 1; i < depth; i++)
                                {
                                    dataNode = dataNode.getChildren().iterator().next();
                                }

                                // Clear the mediaTime first
                                //dataNode.getMetricsTotalsRollup().setMediaTimeStory( 0 );
                                
                                // Fill in share bar mediaTime/mediaViews
                                if (mediaMetricsResponse != null)
                                {
                                    // Divide by 1000 to get seconds from millis
                                    dataNode.getMetricsTotalsRollup().setMediaTimeStory(
                                    		dataNode.getMetricsTotalsRollup().getMediaTimeStory() + 
                                            mediaMetricsResponse.getMetrics().iterator().next().getMetricsTotalsRollup().getMediaTimeStory());
                                    
                                    // Let's add ShareBar Metrics to the parent node (this will make numbers match the Ad level numbers, DEV-6460)
                                    dataNode.getMetricsTotalsRollup().setEngagementConsumesStory( 
                                            dataNode.getMetricsTotalsRollup().getEngagementConsumesStory() +
                                            mediaMetricsResponse.getMetrics().iterator().next().getMetricsTotalsRollup().getEngagementConsumesStory() );
                                    
                                    dataNode.getMetricsTotalsRollup().setEngagementAmplifiesStory(  
                                            dataNode.getMetricsTotalsRollup().getEngagementAmplifiesStory() +
                                            mediaMetricsResponse.getMetrics().iterator().next().getMetricsTotalsRollup().getEngagementAmplifiesStory() );
                                    
                                    dataNode.getMetricsTotalsRollup().setEngagementSharesStory( 
                                            dataNode.getMetricsTotalsRollup().getEngagementSharesStory() +
                                            mediaMetricsResponse.getMetrics().iterator().next().getMetricsTotalsRollup().getEngagementSharesStory() );
                                }
                                
                                // Fill in shorty bar mediaTime/mediaViews
                                if (mediaMetricsResponse2 != null)
                                {
                                    // Divide by 1000 to get seconds from millis
                                    dataNode.getMetricsTotalsRollup().setMediaTimeStory(
                                    		dataNode.getMetricsTotalsRollup().getMediaTimeStory() + 
                                            mediaMetricsResponse2.getMetrics().iterator().next().getMetricsTotalsRollup().getMediaTimeStory());
                                    
                                    // Let's add ShortyBar Metrics to the parent node (this will make numbers match the Ad level numbers, DEV-6460)
                                    dataNode.getMetricsTotalsRollup().setEngagementConsumesStory( 
                                            dataNode.getMetricsTotalsRollup().getEngagementConsumesStory() +
                                            mediaMetricsResponse2.getMetrics().iterator().next().getMetricsTotalsRollup().getEngagementConsumesStory() );
                                    
                                    dataNode.getMetricsTotalsRollup().setEngagementAmplifiesStory(  
                                            dataNode.getMetricsTotalsRollup().getEngagementAmplifiesStory() +
                                            mediaMetricsResponse2.getMetrics().iterator().next().getMetricsTotalsRollup().getEngagementAmplifiesStory() );
                                    
                                    dataNode.getMetricsTotalsRollup().setEngagementSharesStory( 
                                            dataNode.getMetricsTotalsRollup().getEngagementSharesStory() +
                                            mediaMetricsResponse2.getMetrics().iterator().next().getMetricsTotalsRollup().getEngagementSharesStory() );
                                }


//                                dataNode.getMetricsTotalsRollup().setMediaTimeStory(
//                                		dataNode.getMetricsTotalsRollup().getMediaTimeStory() / 1000);

                                
                                infSummary = influenceSummaryTransformer.transform(dataNode);

                                // TODO: This is a "not-so-pretty" injection of data in already transformed object,
                                // TODO: we might want to refactor this code and make transform method - better

                                //infSummary.getTotalInfluence().setStoryImpressions( 0 );

                                
                                if (infSummary != null && infSummary.getTotalInfluence() != null && mediaMetricsResponse != null)
                                {
                                    infSummary.getTotalInfluence().setStoryImpressions(
                                    		infSummary.getTotalInfluence().getStoryImpressions() + 
                                    		mediaMetricsResponse.getMetrics().iterator().next().getMetricsTotalsRollup().getMediaViewsStory()
                                    );
                                }
                                if (infSummary != null && infSummary.getTotalInfluence() != null && mediaMetricsResponse2 != null)
                                {
                                    infSummary.getTotalInfluence().setStoryImpressions(
                                    		infSummary.getTotalInfluence().getStoryImpressions() + 
                                            mediaMetricsResponse2.getMetrics().iterator().next().getMetricsTotalsRollup().getMediaViewsStory()
                                    );
                                }
                            }
                            catch (Exception e)
                            {
                              e.printStackTrace();
                                // If we cannot navigate this graph for any reason, assume there is just no data
                            }
                        }
                    }
                    // Either no data, or perhaps the ID doesn't even exist in NAP yet, so just return zero object
                    if (infSummary == null)
                    {
                        infSummary = new InfluenceSummary().setToZero();
                    }
                    syncMap.put(leaf.toString(), infSummary);
                    return null;
                }
            });
        }
        // Execute and wait on all or if the size of tasks is 1 execute in the caller thread simply tasks.get(0).call();
        if (tasks.size() > 0)
        {
            if (tasks.size() > 1)
            {
                ExecutorUtils.invokeAll(executorService, tasks);
            }
            else
            {
                try
                {
                    tasks.get(0).call();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        return results;
    }

    /**
     * Fetch a tree of data from NAP and get InfluenceSummary objects from the rolled up totalsRollup nodes.
     * The "slices" param is a set of NapSlice objects which describe the tree exclusive
     * of the leaf level.  The "leaf" parameters then describe the leaf layer.  Since each
     * leaf request is made independently (in parallel) we know that every tree returned will
     * actually be a linear graph and if the graph depth is equal to our expected depth the
     * destired data must be that data contained in the leaf node.  Note that a request to
     * NAP may not actually return any data in which case we should return a zero object.
     * <p/>
     * Exactly one of leafIds/leafNames must be specified.
     *
     * @param startTime Start DateTime of query
     * @param stopTime Stop DateTime of query
     * @param leafDimension Dimension of leaf node
     * @param leafIds Filter ID set for leaf node (NAP ids)
     * @param leafNames Filter name set for leaf node (NAP names)
     * @param slices Additional hyper-query slices, from root
     * @return Influence Summaries, keyed by ID
     */
    @Cacheable(INFLUENCE_CACHE)
    private InfluenceComparison<InfluenceSummary> getRollupSummaries(final DateTime startTime,
                                                                     final DateTime stopTime,
                                                                     final NapDimension leafDimension,
                                                                     final Collection<Integer> leafIds,
                                                                     final Collection<String> leafNames,
                                                                     final NapSlice... slices)
    {
        if (!(leafIds != null) ^ (leafNames != null))
        {
            throw new IllegalArgumentException("Must specify exactly one of 'leafIds' or 'leafNames'");
        }
        // Results holder
        InfluenceComparison<InfluenceSummary> results = new InfluenceComparison<InfluenceSummary>();
        final Map<String, InfluenceSummary> syncMap = Collections.synchronizedMap(results);
        // Iterate on ids to create tasks
        final LocalDate startDate = startTime.toLocalDate();
        final LocalDate stopDate = stopTime.toLocalDate();
        List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
        Collection collection = new ArrayList();
        if (leafIds != null)
        {
            collection.addAll(leafIds);
        }
        if (leafNames != null)
        {
            collection.addAll(leafNames);
        }
        collection.add(SHARE_BAR_PREFIX + ".v3");
        collection.add(SHARE_BAR_PREFIX + ".v4");
        collection.add(SHARE_BAR_PREFIX + ".v5");
        collection.add( SHORTY_BAR );
        collection.add( IP_OVERLAY );

        // Iterable<?> iterable = MoreObjects.ifNull( leafIds, leafNames );
        for (final Object leaf : collection)
        {
            tasks.add(new Callable<Void>()
            {
                @Override
                public Void call()
                {
                    // Get NAP id from IFB id
                    InfluenceSummary infSummary = null;
                    Integer napId = convertToNapId(leafDimension, leaf);
                    if (napId != null)
                    {
                        // We have a valid ID, so request data from NAP
                        //NapSlice leafSlice = new NapSlice( leafDimension, napId );
                        GetMetricsResponse response = napService.getRollupTotalExperienceMetrics(startDate, stopDate,
                                                                                                 slices);
                        // Data is in leaf node (if it exists)
                        if (!response.getMetrics().isEmpty())
                        {
                            try
                            {
                                Collection<NapNode> napMetrics = response.getMetrics();
                                NapNode dataNode = null;
                                String adKey = null;
                                if (leaf instanceof Integer)
                                {
                                    adKey = adService.getAd((Integer) leaf).getKey();
                                }


                                for (NapNode napMetric : napMetrics)
                                {
                                    dataNode = napMetric;
                                    if (dataNode.getName().equals(adKey))
                                    {

                                        Map<String, Long> mediaMetrics = semiRecursiveParser(dataNode);

                                        if (mediaMetrics != null)
                                        {
                                            dataNode.getMetricsTotalsRollup().setMediaTimeStory(mediaMetrics.get("mediaTimeStory"));
                                            dataNode.getMetricsTotalsRollup().setMediaViewsStory(
                                                    mediaMetrics.get("mediaViewsStory"));
                                        }

                                        // copy individual totals value and not the totalsRollup ( DEV-6891 )
                                        dataNode.getMetricsTotalsRollup().setImpressionServes(
                                                dataNode.getMetricsTotals().getImpressionServes()
                                        );

                                        break;
                                    }
                                    else if (adKey == null &&
                                            dataNode.getName().equals(leaf))
                                    {  // share-bar

                                        dataNode.getMetricsTotalsRollup().setMediaTimeStory(
                                                dataNode.getMetricsTotalsRollup().getMediaTimeStory() / 1000);
                                        dataNode.getMetricsTotalsRollup().setMediaViewsStory(
                                                dataNode.getMetricsTotalsRollup().getMediaViewsStory());

                                        break;
                                    }
                                    else
                                    {

                                        dataNode = null;

                                        // Ad not found in Analytics response
                                        //throw new Exception( "Cannot find matching Ad in Clerk response: " + adKey );
                                    }
                                }
                                if (dataNode != null)
                                {
                                    infSummary = influenceSummaryTransformer.transform(dataNode);
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                // If we cannot navigate this graph for any reason, assume there is just no data
                            }
                        }
                    }
                    // Either no data, or perhaps the ID doesn't even exist in NAP yet, so just return zero object
                    if (infSummary == null)
                    {
                        infSummary = new InfluenceSummary().setToZero();
                    }
                    syncMap.put(leaf.toString(), infSummary);
                    return null;
                }
            });
        }
        // Execute and wait on all or if the size of tasks is 1 execute in the caller thread simply tasks.get(0).call();
        if (tasks.size() > 0)
        {
            if (tasks.size() > 1)
            {
                ExecutorUtils.invokeAll(executorService, tasks);
            }
            else
            {
                try
                {
                    tasks.get(0).call();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        results = collapseAllSharebarEntriesIntoOne(results);

        return results;
    }


    private InfluenceComparison<InfluenceSummary> collapseAllSharebarEntriesIntoOne(
            InfluenceComparison<InfluenceSummary> src
    )
    {
        InfluenceComparison<InfluenceSummary> results = new InfluenceComparison<InfluenceSummary>();
        InfluenceSummary shareBarSummary = new InfluenceSummary();
        shareBarSummary.setTotalInfluence(new Influence());
        Set keys = src.keySet();
        for (Iterator iterator = keys.iterator(); iterator.hasNext(); )
        {
            String key = (String) iterator.next();
            if (key.startsWith(SHARE_BAR_PREFIX) || key.startsWith(IP_OVERLAY) || key.startsWith(SHORTY_BAR))
            {
                shareBarSummary.getTotalInfluence().add(src.get(key).getTotalInfluence());
            }
            else
            {
                // if (src.get(key).)
                results.put(key, src.get(key));
            }
        }
        results.put(SHARE_BAR_PREFIX, shareBarSummary);
        return results;
    }


    /*
    private MultiValueMap<String,Map<String,Integer>> recursiveParser( NapNode dataNode )
    {
      if ( dataNode.getChildren() == null ) {
        return null;
      }

      for ( NapNode node : dataNode.getChildren() ) {
        Map<String,Integer> metrics = new HashMap<String,Integer>();

        if ( node.getName().contains( "share_bar" ) ) {
          metrics.put( "mediaTimeStory", node.getMetricsTotalsRollup().getMediaTimeStory() );
          metrics.put( "mediaViewsStory", node.getMetricsTotalsRollup().getMediaViewsStory() );

          return recursiveParser( node ).add( String.valueOf( node.hashCode() ), metrics );
        }
      }

      return metrics;
    }
    */
    private Map<String, Long> semiRecursiveParser(NapNode dataNode)
    {
        Map<String, Long> mediaMetrics = new HashMap<String, Long>();
        long mediaTimeStory = 0;
        long mediaViewsStory = 0;

        if (dataNode.getChildren() == null)
        {
            return null;
        }
        // 2nd level of Experience
        for (NapNode child : dataNode.getChildren())
        {
            if (child.getName().contains("share_bar") || child.getName().contains(SHORTY_BAR) )
            {
                mediaTimeStory += child.getMetricsTotalsRollup().getMediaTimeStory();
                mediaViewsStory += child.getMetricsTotalsRollup().getMediaViewsStory();
            }

            if (child.getChildren() == null)
            {
                mediaMetrics.put("mediaTimeStory", mediaTimeStory / 1000);
                mediaMetrics.put("mediaViewsStory", mediaViewsStory);
                return mediaMetrics;
            }
            // 3rd level of Experience
            for (NapNode subchild : child.getChildren())
            {
                if (subchild.getName().contains("share_bar") || subchild.getName().contains(SHORTY_BAR))
                {
                    mediaTimeStory += child.getMetricsTotalsRollup().getMediaTimeStory();
                    mediaViewsStory += child.getMetricsTotalsRollup().getMediaViewsStory();

                    mediaMetrics.put("mediaTimeStory", mediaTimeStory / 1000);
                    mediaMetrics.put("mediaViewsStory", mediaViewsStory);
                }
            }
        }

        return mediaMetrics;
    }

    @Cacheable(INFLUENCE_CACHE)
    public InfluenceStories getStoriesTotal(Collection<Integer> brandIds,
                                            Collection<Integer> authorIds,
                                            Collection<String> sentiments,
                                            DateTime startTime, DateTime stopTime,
                                            String query, String order, Integer limit,
                                            List<Integer> campaignIds,
                                            DateTime pubStartDate)
    {
        DpOrderType orderType = DpOrderType.valueFrom(order);
        Collection<DpSentiment> sentimentSet = DpUtils.parseSentiments(sentiments);
        GetInfluenceAndSentimentStoriesResponse response =
                dpService.getInfluenceAndSentimentStoriesTotal(brandIds, authorIds, sentimentSet,
                                                               startTime, stopTime,
                                                               query, orderType, limit,
                                                               campaignIds, pubStartDate);
        if (brandIds != null && brandIds.size() > 0)
        {
            response.setCategoryId(brandIds.iterator().next());
        }
        return influenceStoriesTransformer.transform(response);
    }

    @Cacheable(INFLUENCE_CACHE)
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
            DateTime pubStartDate)
    {
        DpOrderType orderType = DpOrderType.valueFrom(order);
        Collection<DpSentiment> sentimentSet = DpUtils.parseSentiments(sentiments);
        List<Integer> dpStoryIds = new ArrayList<Integer>();
        if (campaignId != null)
        {
            Campaign campaign = campaignService.getCampaign(campaignId);
            if (campaign != null)
            {
                List<Ad> adList =
                        (List)adService.getAds(null, null, null, null, Collections.singleton(campaignId));
                for (Ad ad : adList)
                {
                    List<FeedStory> feedStoryList = (List)feedService.getFeedStories(ad.getFeed().getId(), null);
                    for (FeedStory feedStory : feedStoryList)
                    {
                        dpStoryIds.add(feedStory.getDpStoryId());
                    }
                }
            }
        }

        GetInfluenceAndSentimentStoriesFacetedResponse response =
                dpService.getInfluenceAndSentimentStoriesFaceted(dpStoryIds, campaignOnly, brandIds, authorIds, sentimentSet,
                                                                 startTime, stopTime,
                                                                 query, orderType, limit, offset, ignoreDpCache,
                                                                 pubStartDate);
        List<Integer> storyIds = new ArrayList<Integer>();
        for (DpIndexedStory dpIndexedStory : response.getStories())
        {
            storyIds.add(dpIndexedStory.getId());
        }
        if (storyIds != null && storyIds.size() > 0)
        {
            Map<Integer, Story> stories = entityService.getStoriesLazy( storyIds );

            for (DpIndexedStory dpIndexedStory : response.getStories())
            {
                Story story = stories.get(dpIndexedStory.getId());
                if (story != null)
                {
                    dpIndexedStory.setImageUrl(story.getImageUrl());
                    dpIndexedStory.setThumbnails(story.getThumbnails());
                }
            }

            Collection<Integer> facetAuthorIds = new ArrayList<Integer>();
            for (int i = 0; i < response.getFacets().size(); i++)
            {
                SearchFacet searchFacet = response.getFacets().get(i);
                if ("authorId".equals(searchFacet.getName()))
                {
                    List<FacetCounter> facetCounterList = searchFacet.getCountList();
                    for (int j = 0; j < facetCounterList.size(); j++)
                    {
                        FacetCounter facetCounter = facetCounterList.get(j);
                        facetAuthorIds.add(Integer.parseInt(facetCounter.getKey()));
                    }
                }
            }
            Map<Integer, Author> authorMap = entityService.getAuthorsMap(facetAuthorIds);

            for (int i = 0; i < response.getFacets().size(); i++)
            {
                SearchFacet searchFacet = response.getFacets().get(i);
                if ("authorId".equals(searchFacet.getName()))
                {
                    List<FacetCounter> facetCounterList = searchFacet.getCountList();
                    for (FacetCounter facetCounter : facetCounterList)
                    {
                        facetCounter.setName(authorMap.get(Integer.parseInt(facetCounter.getKey())).getName());
                    }
                }
            }
        }

//
//        for (int i = 0; i < stories.size(); i++)
//        {
//            Story story = stories.get(i);
//            GetInfluenceAndSentimentStoriesFacetedResponse.StorySearchEntry storySearchEntry =
//                    storySearchEntryMap.get(story.getId());
//            if (storySearchEntry != null)
//            {
//                storySearchEntry.setStory(story);
//            }
//        }

        return response;
    }

    @Override
    @Cacheable(INFLUENCE_CACHE)
    public InfluenceStories getStoriesPaid(Collection<Integer> brandIds,
                                           Collection<Integer> campaignIds,
                                           Collection<Integer> authorIds,
                                           Collection<String> sentiments,
                                           DateTime startTime, DateTime stopTime,
                                           String query, String order, Integer limit)
    {
        // Convert CampaignId's into CampaignKeys's
        Collection<String> campaignKeys = campaignService.getCampaignKeys(campaignIds);
        DpOrderType orderType = DpOrderType.valueFrom(order);
        Collection<DpSentiment> sentimentSet = DpUtils.parseSentiments(sentiments);
        GetInfluenceAndSentimentStoriesResponse response =
                dpService.getInfluenceAndSentimentStoriesPaid(brandIds, campaignKeys,
                                                              authorIds, sentimentSet,
                                                              startTime, stopTime,
                                                              query, orderType, limit);
        response.setCategoryId(brandIds.iterator().next());
        return influenceStoriesTransformer.transform(response);
    }

    /**
     * Get story engagement data from Data Platform for a set of stories
     *
     * @param dpStoryId
     * @return Map Story engagements map with metrics string and additional influence metrics
     */
    @Override
    @Cacheable(INFLUENCE_CACHE)
    public Map<String, String> getEngagementMetrics(Integer dpStoryId)
    {
        GetStoryEngagementsResponse r = dpService.getStoryEngagements(Collections.singletonList(dpStoryId), null, null);
        Collection<DpEngagement> dpEngagements = r.getEngagements();

        Map<String, String> response = new HashMap<String, String>();

        // Some stories don't have metrics, skip them
        if (dpEngagements.isEmpty()) return null;

        Joiner joiner = Joiner.on(",");
        MapJoiner mapJoiner = joiner.withKeyValueSeparator("=");

        //for ( DpEngagement dpEngagement : dpEngagements ) {
        Iterable<DpEngagement> i = dpEngagements;
        DpEngagement dpEngagement = i.iterator().next();

        Collection<DpEngagementMetric> dpEngagementMetrics = dpEngagement.getMetrics();

        Map<String, String> metrics = new HashMap<String, String>();

        for (DpEngagementMetric dpEngagementMetric : dpEngagementMetrics)
        {
            metrics.put(dpEngagementMetric.getName(), dpEngagementMetric.getValue().toString());
        }

        response.put("metrics", mapJoiner.join(metrics).toString());
        response.put("events", String.valueOf(dpEngagement.getInfluence().getEvents()));
        response.put("points", String.valueOf(dpEngagement.getInfluence().getPoints()));
        response.put("influencers", String.valueOf(dpEngagement.getInfluence().getInfluencers()));
        response.put("uniqueStories", String.valueOf(dpEngagement.getInfluence().getUniqueStories()));

        return response;
        //}

    }

    /**
     * Get story engagement data from Data Platform for a set of stories (for new Feed format, un-concatenated metrics)
     *
     * @param dpStoryId
     * @return Map Story engagement metrics map
     */
    @Override
    @Cacheable(INFLUENCE_CACHE)
    public Map<String, String> getEngagementMetricsUnjoined(Integer dpStoryId)
    {
        GetStoryEngagementsResponse r = dpService.getStoryEngagements(Collections.singletonList(dpStoryId), null, null);
        Collection<DpEngagement> dpEngagements = r.getEngagements();

        // Some stories don't have metrics, skip them
        if (dpEngagements.isEmpty()) return null;

        Iterable<DpEngagement> i = dpEngagements;
        DpEngagement dpEngagement = i.iterator().next();

        Collection<DpEngagementMetric> dpEngagementMetrics = dpEngagement.getMetrics();

        Map<String, String> metrics = new HashMap<String, String>();

        for (DpEngagementMetric dpEngagementMetric : dpEngagementMetrics)
        {
            metrics.put(dpEngagementMetric.getName(), dpEngagementMetric.getValue().toString());
        }

        return metrics;
    }

    /**
     * Get story engagement data from Data Platform for a set of stories (#2)
     *
     * @param storyIds The story ids to search for
     * @param startTime Start of date-time range (applied for influence metrics)
     * @param stopTime Stop of date-time range (applied for influence metrics)
     * @return Map Story engagements map with metrics string and additional influence metrics
     */
    @Override
    public Collection<Story> getStoryEngagements(Collection<Integer> storyIds, DateTime startTime, DateTime stopTime)
    {
        GetStoryEngagementsResponse response = dpService.getStoryEngagements(storyIds, startTime, stopTime);

        return storyTransformer.transform(response);
    }

    private Integer convertToNapId(NapDimension dimension, Object id)
    {
        if (id instanceof Integer) return convertToNapId(dimension, (Integer) id);
        if (id instanceof String) return convertToNapId(dimension, (String) id);
        throw new IllegalArgumentException("Can't currently convert type " + id.getClass().getSimpleName());
    }

    /**
     * Convert a IFB ID into a NAP ID for a given dimension.
     * We do this by using the IFB ID to find the key from the db, then
     * use the key to query the NAP ID from the NAP API.  Unknown IDs are ignored.
     *
     * @param ifbId IFB id
     * @return NAP id
     */
    private Integer convertToNapId(NapDimension dimension, Integer ifbId)
    {
        String key = null;
        Integer napId = null;
        switch (dimension)
        {
            case CAMPAIGN:
                Campaign c = campaignService.getCampaign(ifbId);
                if (c != null) key = c.getCampaignKey();
                break;

            case CREATIVE:
                Ad a = adService.getAd(ifbId);
                if (a != null) key = a.getKey();
                break;

            default:
                throw new IllegalArgumentException(dimension + " not yet supported");
        }
        if (key != null)
        {
            GetDimensionIdResponse r = napService.getExistingDimensionId(dimension, key);
            napId = r.getValue();
        }
        return napId;
    }

//    @Cacheable(INFLUENCE_CACHE)
    // Internal caching is used
    public GenericPayload<Map<Integer, Collection<Story>>> searchNewStories( List<Integer> campaignIds,
                                                                             Integer daysBack, final boolean ignoreCache )
    {
        final Map<Integer, Collection<Story>> result = new HashMap<Integer, Collection<Story>>();
        final int minusDaysBack;
        if (daysBack == null)
        {
            minusDaysBack = InfluenceService.DEFAULT_DAYS_BACK_SEARCH;
        }
        else
        {
            minusDaysBack = daysBack.intValue();
        }
        if (campaignIds != null && campaignIds.size() > 0)
        {
            final List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
            final List<Campaign> campaignList = (List<Campaign>)campaignService.getCampaigns(campaignIds);
            for (final Campaign campaign : campaignList)
            {
                final DateTime twoDaysAgo = DateTime.now(DateTimeZone.forTimeZone(InfluenceController.TZ_DEFAULT))
                        .minusDays(minusDaysBack);
                final DateTime now = DateTime.now(DateTimeZone.forTimeZone(InfluenceController.TZ_DEFAULT))
                        .withTime(23,59,59,999);
                // if the search is brand-less use relevance sort
                final String dpOrder = DpOrderType.SEARCHSIMILARITY.name();
                final Collection<Integer> brandIds;
                if (campaign.getBrand() != null && campaign.getBrand().getId() != null)
                {
                    brandIds = Collections.singleton( campaign.getBrand().getId() );
                    //ARTEM COMMENTED OUT FOR TEMPORARY PERFORMANCE dpOrder = DpOrderType.INFLUENCESCORE.name();
                }
                else
                {
                    brandIds = new ArrayList<Integer>();
                }
                List<Ad> adList = (List<Ad>)adService.getAds(null, null, null, null, campaignIds);
                final List<Integer> foundStoryIdList = new ArrayList<Integer>();
                for (Ad ad : adList)
                {
                    List<FeedStory> feedStoryList =
                            (List<FeedStory>)feedService.getFeedStories(Collections.singleton( ad.getFeed().getId() ));
                    for (FeedStory feedStory : feedStoryList)
                    {
                        foundStoryIdList.add(feedStory.getStoryId());
                    }
                }

                logger.debug("Fetching new stories for keywords:["+campaign.getKeywords()+"] ("+twoDaysAgo+" -to- "+now+")");
                CacheManager manager = CacheManager.getInstance();
                final Cache cache = manager.getCache(INFLUENCE_CACHE);
                tasks.add( new Callable<Void>()
                {
                    @Override
                    public Void call()
                            throws
                            Exception
                    {
                        String key = "searchNewStories(" + campaign.getId() + "," + minusDaysBack + ")";
                        List<Story> tmp = null;
                        if (cache != null)
                        {
                            Element element = null;
                            if (!ignoreCache)
                            {
                                element = cache.get(key);
                                if (element != null)
                                {
                                    logger.debug("Retrieving data from cache using key: " + key);
                                    tmp = (List<Story>) element.getObjectValue();
                                }
                            }
                        }

                        if (tmp == null)
                        {
                            InfluenceStories stories = getStoriesTotal(brandIds,
                                                                       null,
                                                                       Arrays.asList( DpSentiment.POSITIVE.name(), DpSentiment.NEUTRAL.name() ),
                                                                       twoDaysAgo,
                                                                       now,
                                                                       campaign.getKeywords(),
                                                                       dpOrder,
                                                                       20, // limit
                                                                       Collections.singletonList( campaign.getId() ),
                                                                       twoDaysAgo);
                            List<InfluenceStories.Synopsis> synopsisList =
                                    (List<InfluenceStories.Synopsis>)stories.getSynopses();
                            List<Integer> storyIdsList = new ArrayList<Integer>();
                            for (InfluenceStories.Synopsis synopsis : synopsisList)
                            {
                                if (!foundStoryIdList.contains( synopsis.getStoryId() ))
                                {
                                    storyIdsList.add( synopsis.getStoryId() );
                                }
                            }
                            tmp = (List<Story>)entityService.getStories( storyIdsList );
                            if (cache != null && tmp != null)
                            {
                                logger.debug("Inserting data into cache using key: " + key);
                                cache.put(new Element(key, tmp));
                            }
                        }

                        // at this point we should have the variable tmp filled in from cache or service
                        List<Story> resultSubList = new ArrayList<Story>();
                        if (tmp != null && tmp.size() > 0)
                        {
                            for (Story story : tmp)
                            {
                                if (!foundStoryIdList.contains( story.getId() ))
                                {
                                    resultSubList.add( story );
                                }
                            }
                        }

                        result.put( campaign.getId(), resultSubList );
                        return null;
                    }
                });
            }

            if (tasks.size() > 0)
            {
                ExecutorUtils.invokeAll(executorService, tasks, 15, TimeUnit.MINUTES);
            }
        }
        return new GenericPayload<Map<Integer, Collection<Story>>>( "stories", result );
    }

    public GenericPayload<Map<Integer, GetInfluenceAndSentimentStoriesFacetedResponse>> searchNewStories_v2( List<Integer> campaignIds,
                                                                             Integer daysBack,
                                                                             final boolean ignoreCache )
    {
        final Map<Integer, GetInfluenceAndSentimentStoriesFacetedResponse> result =
                new HashMap<Integer, GetInfluenceAndSentimentStoriesFacetedResponse>();
        final int minusDaysBack;
        if (daysBack == null)
        {
            minusDaysBack = InfluenceService.DEFAULT_DAYS_BACK_SEARCH;
        }
        else
        {
            minusDaysBack = daysBack.intValue();
        }
        if (campaignIds != null && campaignIds.size() > 0)
        {
            final List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
            final List<Campaign> campaignList = (List<Campaign>)campaignService.getCampaigns(campaignIds);
            for (final Campaign campaign : campaignList)
            {
                final DateTime twoDaysAgo = DateTime.now(DateTimeZone.forTimeZone(InfluenceController.TZ_DEFAULT))
                        .minusDays(minusDaysBack);
                final DateTime now = DateTime.now(DateTimeZone.forTimeZone(InfluenceController.TZ_DEFAULT))
                        .withTime(23,59,59,999);
                // if the search is brand-less use relevance sort
                final String dpOrder = DpOrderType.SEARCHSIMILARITY.name();
                final Collection<Integer> brandIds;
                if (campaign.getBrand() != null && campaign.getBrand().getId() != null)
                {
                    brandIds = Collections.singleton( campaign.getBrand().getId() );
                    //ARTEM COMMENTED OUT FOR TEMPORARY PERFORMANCE dpOrder = DpOrderType.INFLUENCESCORE.name();
                }
                else
                {
                    brandIds = new ArrayList<Integer>();
                }
                List<Ad> adList = (List<Ad>)adService.getAds(null, null, null, null, campaignIds);
                final List<Integer> foundStoryIdList = new ArrayList<Integer>();
                for (Ad ad : adList)
                {
                    List<FeedStory> feedStoryList =
                            (List<FeedStory>)feedService.getFeedStories(Collections.singleton( ad.getFeed().getId() ));
                    for (FeedStory feedStory : feedStoryList)
                    {
                        foundStoryIdList.add(feedStory.getDpStoryId());
                    }
                }

                logger.debug("Fetching new stories for keywords:["+campaign.getKeywords()+"] ("+twoDaysAgo+" -to- "+now+")");
                CacheManager manager = CacheManager.getInstance();
                final Cache cache = manager.getCache(INFLUENCE_CACHE);
                tasks.add( new Callable<Void>()
                {
                    @Override
                    public Void call()
                            throws
                            Exception
                    {
                        String key = "searchNewStories_v2(" + campaign.getId() + "," + minusDaysBack + ")";
                        GetInfluenceAndSentimentStoriesFacetedResponse tmp = null;
                        if (cache != null)
                        {
                            Element element = null;
                            if (!ignoreCache)
                            {
                                element = cache.get(key);
                                if (element != null)
                                {
                                    logger.debug("Retrieving data from cache using key: " + key);
                                    tmp = (GetInfluenceAndSentimentStoriesFacetedResponse) element.getObjectValue();
                                }
                            }
                        }

                        if (tmp == null)
                        {
//                            InfluenceStories stories = getStoriesTotal(brandIds,
//                                                                       null,
//                                                                       Arrays.asList( DpSentiment.POSITIVE.name(), DpSentiment.NEUTRAL.name() ),
//                                                                       twoDaysAgo,
//                                                                       now,
//                                                                       campaign.getKeywords(),
//                                                                       dpOrder,
//                                                                       20, // limit
//                                                                       Collections.singletonList( campaign.getId() ),
//                                                                       twoDaysAgo);

                            tmp = dpService.getInfluenceAndSentimentStoriesFaceted(null, false, brandIds,
                                                                                     null,
                                                                                     Arrays.asList( DpSentiment.POSITIVE, DpSentiment.NEUTRAL ),
                                                                                     twoDaysAgo, now,
                                                                                     campaign.getKeywords(), DpOrderType.SEARCHSIMILARITY,
                                                                                     20, 0, false,
                                                                                     twoDaysAgo);

                            List<DpIndexedStory> indexedStoryList = tmp.getStories();

                            // removing the stories already connected to provided campaign...
                            List<DpIndexedStory> newStoryList = new ArrayList<DpIndexedStory>();
                            for (DpIndexedStory story : indexedStoryList)
                            {
                                if (!foundStoryIdList.contains(story.getId()))
                                {
                                    newStoryList.add(story);
                                }
                            }
                            tmp.setStories(newStoryList);


                            // filling in the author, thumbnails and other story parameters, using lazy fetching...
                            List<Integer> storyIds = new ArrayList<Integer>();
                            for (DpIndexedStory dpIndexedStory : tmp.getStories())
                            {
                                storyIds.add(dpIndexedStory.getId());
                            }
                            if (storyIds != null && storyIds.size() > 0)
                            {
                                Map<Integer, Story> stories = entityService.getStoriesLazy( storyIds );

                                for (DpIndexedStory dpIndexedStory : tmp.getStories())
                                {
                                    Story story = stories.get(dpIndexedStory.getId());
                                    if (story != null)
                                    {
                                        dpIndexedStory.setImageUrl(story.getImageUrl());
                                        dpIndexedStory.setThumbnails(story.getThumbnails());
                                    }
                                }

                                Collection<Integer> facetAuthorIds = new ArrayList<Integer>();
                                for (int i = 0; i < tmp.getFacets().size(); i++)
                                {
                                    SearchFacet searchFacet = tmp.getFacets().get(i);
                                    if ("authorId".equals(searchFacet.getName()))
                                    {
                                        List<FacetCounter> facetCounterList = searchFacet.getCountList();
                                        for (int j = 0; j < facetCounterList.size(); j++)
                                        {
                                            FacetCounter facetCounter = facetCounterList.get(j);
                                            facetAuthorIds.add(Integer.parseInt(facetCounter.getKey()));
                                        }
                                    }
                                }
                                Map<Integer, Author> authorMap = entityService.getAuthorsMap(facetAuthorIds);

                                for (int i = 0; i < tmp.getFacets().size(); i++)
                                {
                                    SearchFacet searchFacet = tmp.getFacets().get(i);
                                    if ("authorId".equals(searchFacet.getName()))
                                    {
                                        List<FacetCounter> facetCounterList = searchFacet.getCountList();
                                        for (FacetCounter facetCounter : facetCounterList)
                                        {
                                            facetCounter.setName(authorMap.get(Integer.parseInt(facetCounter.getKey())).getName());
                                        }
                                    }
                                }
                            }



                            // tmp = (List<Story>)entityService.getStories( storyIdsList );
                            if (cache != null && tmp != null)
                            {
                                logger.debug("Inserting data into cache using key: " + key);
                                cache.put(new Element(key, tmp));
                            }
                        }

                        // at this point we should have the variable tmp filled in from cache or service
//                        List<Story> resultSubList = new ArrayList<Story>();
//                        if (tmp != null && tmp.size() > 0)
//                        {
//                            for (Story story : tmp)
//                            {
//                                if (!foundStoryIdList.contains( story.getId() ))
//                                {
//                                    resultSubList.add( story );
//                                }
//                            }
//                        }

                        result.put( campaign.getId(), tmp );
                        return null;
                    }
                });
            }

            if (tasks.size() > 0)
            {
                ExecutorUtils.invokeAll(executorService, tasks, 15, TimeUnit.MINUTES);
            }
        }
        return new GenericPayload<Map<Integer, GetInfluenceAndSentimentStoriesFacetedResponse>>( "stories", result );
    }

    private Integer convertToNapId(NapDimension dimension, String name)
    {
        GetDimensionIdResponse r = napService.getExistingDimensionId(dimension, name);
        return r == null ? null : r.getValue();
    }

//  /**
//   * Convert a collection of IFB ids into NAP ids for a given dimension.
//   * Return order is the iteration order of the inbound Collection.  Unknown IDs are ignored.
//   * @param ifbIds
//   * @return napIds
//   */
//  private List<Integer> convertToNapIds( NapDimension dimension, Collection<Integer> ifbIds )
//  {
//    List<Integer> napIds = new ArrayList<Integer>( ifbIds.size() );
//    for( Integer ifbId: ifbIds ) {
//      Integer napId = convertToNapId( dimension, ifbId );
//      if( napId != null ) {
//        napIds.add( napId );
//      }
//    }
//    return napIds;
//  }

//  private final int MIN=0,MAX=1,AVG=2,TOTAL=3,LIFT=4,INFL=5;
//  private int[] getCampaignStats( Campaign c, DateTime startTime )
//  {
//    int lift;
//    if( c.getCampaignName().toLowerCase().contains( "microsoft" )) {
//      lift = 10;
//    } else if( c.getCampaignName().toLowerCase().contains( "samsung" )) {
//      lift = 30;
//    } else {
//      lift = 15;
//    }
//    // Find min/max/avg peopleInfluenced for campaign dates
//    DateTimeZone zone = startTime.getZone();
//    DateTime m=c.getStartDate().toDateTimeAtStartOfDay( zone );
//    DateTime n=c.getStopDate().plusDays( 1 ).toDateTimeAtStartOfDay( zone ).minusMillis( 1 );
//    GetInfluenceRollupBreakdownResponse aaa =
//        dpService.getInfluenceRollupBreakdown( Collections.singleton( c.getBrand().getId() ),
//                                               Collections.singleton( c.getCampaignKey() ),
//                                               null, Arrays.asList( DpSentiment.POSITIVE, DpSentiment.NEUTRAL ),
//                                               m,n, c.getKeywords(), DpRollupInterval.DAY );
//    InfluenceBreakdown<InfluenceTrend> bbb = influenceTrendTransformer.transform( aaa );
//    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
//    for( TrendDetail td: bbb.getOrganic().getTrend() )  {
//      min = Math.min( min, td.getInfluence().getPeopleInfluenced() );
//      max = Math.max( max, td.getInfluence().getPeopleInfluenced() );
//    }
//    int tot = bbb.getOrganic().getTotalInfluence().getPeopleInfluenced();
//    int avg = tot / bbb.getOrganic().getTrend().size();
//    int infl = bbb.getOrganic().getTotalInfluence().getInfluencers();
//    return new int[] { min,max,avg,tot,lift,infl };
//  }

    @SuppressWarnings("unused")
    private final int PFUDGE = 0, IRATE = 1, IFUDGE = 2;

    private double[] doFudge(Campaign c, Influence src, Influence inf)
    {
        int lift;
        if (c.getCampaignName().toLowerCase().contains("microsoft"))
        {
            lift = 10;
        }
        else if (c.getCampaignName().toLowerCase().contains("samsung"))
        {
            lift = 30;
        }
        else
        {
            lift = 15;
        }
        double pfudge = (double) src.getPeopleInfluenced() * lift / 100 * 1.01234;
        double irate = (((double) pfudge % 100 / 100f * 0.051234) + 0.381234) / 100;
        double ifudge = (double) src.getInfluencers() * (((pfudge % 100) / 100f * 0.031234f) + 0.071234);
        inf.setPeopleInfluenced((int) pfudge);
        inf.setInfluencers((int) (ifudge));
        inf.setImpressions((int) (pfudge / irate));
        inf.setUniqueCount(3);
        double[] f = new double[]{pfudge, irate, ifudge};
        logger.info("Salting payload with fabricated paid data...(src=%s,lift=%d,fudge=%s,inf=%s)",
                    src, lift, Arrays.toString(f), inf);
        return f;
    }

    private double getWobble(int cnt, int num)
    {
        double f;
        if (((cnt % 2) > 0) && (num == (cnt - 1)))
        {
            f = 0;
        }
        else
        {
            int t = 10;
            int s = 1;
            int n = num;
            while (n > 0)
            {
                if (s > 0)
                {
                    s = -1;
                }
                else
                {
                    t--;
                    s = 1;
                }
                n--;
            }
            f = t * s;
        }
        f /= 100;
        f += 1;
        logger.info("Wobble: cnt=%d, num=%d, f=%f", cnt, num, f);
        return f;
    }

    public boolean isOdd(int n)
    {
        return 0 == (n & 1);
    }

    public boolean isEven(int n)
    {
        return 1 == (n & 1);
    }

    /**
     * Fetch a tree of data from NAP and get InfluenceSummary objects from the leaf nodes.
     * The "slices" param is a set of NapSlice objects which describe the tree exclusive
     * of the leaf level.  The "leaf" parameters then describe the leaf layer.  Since each
     * leaf request is made independently (in parallel) we know that every tree returned will
     * actually be a linear graph and if the graph depth is equal to our expected depth the
     * destired data must be that data contained in the leaf node.  Note that a request to
     * NAP may not actually return any data in which case we should return a zero object.
     * <p/>
     * Exactly one of leafIds/leafNames must be specified.
     *
     * @param startTime Start DateTime of query
     * @param stopTime Stop DateTime of query
     * @param leafDimension Dimension of leaf node
     * @param leafIds Filter ID set for leaf node (NAP ids)
     * @param leafNames Filter name set for leaf node (NAP names)
     * @param slices Additional hyper-query slices, from root
     * @return Influence Summaries, keyed by ID
     */
    private InfluenceComparison<InfluenceSummary> getLeafNodeSummariesAggregated(final DateTime startTime,
                                                                       final DateTime stopTime,
                                                                       final NapDimension leafDimension,
                                                                       final Collection<Integer> leafIds,
                                                                       final Collection<String> leafNames,
                                                                       final NapSlice... slices)
    {
        if (!(leafIds != null) ^ (leafNames != null))
        {
            throw new IllegalArgumentException("Must specify exactly one of 'leafIds' or 'leafNames'");
        }
        // Results holder
        InfluenceComparison<InfluenceSummary> results = new InfluenceComparison<InfluenceSummary>();
        // Iterate on ids to create tasks
        final LocalDate startDate = startTime.toLocalDate();
        final LocalDate stopDate = stopTime.toLocalDate();
                
        InfluenceSummary infSummary = null;
        
        List<NapSlice> napSlices = new ArrayList<NapSlice>();
        for ( NapSlice slice : slices ) {
          napSlices.add( slice );
        }
        
        GetMetricsResponse response = null;
        GetMetricsResponse shareBarResponse = null;
        GetMetricsResponse shortyBarResponse = null;
        try
        {
            response = napService.getTotalMetrics(startDate, stopDate,
                                                  ObjectArrays.concat( slices, new NapSlice( NapDimension.ASSET ) ) );
            
            Integer shareBarId = convertToNapId(NapDimension.CREATIVE, "ip.share_bar.v5");

            // Create a slices ArrayList to alter the slices, including share bar as a creative
            List<NapSlice> shareBarSlices = new ArrayList<NapSlice>(napSlices);
            shareBarSlices.add( new NapSlice( NapDimension.CREATIVE, shareBarId) );
            shareBarSlices.add( new NapSlice( NapDimension.ASSET) );
            
            // Convert back to simple array to re-use napService method
            NapSlice[] simpleArray = new NapSlice[ shareBarSlices.size() ];
            shareBarResponse = napService.getTotalMetrics(startDate, stopDate,
                shareBarSlices.toArray( simpleArray ) );
            
            Integer shortyBarId = convertToNapId(NapDimension.CREATIVE, "ip.shorty_bar");

            // Create a slices ArrayList to alter the slices, including share bar as a creative
            List<NapSlice> shortyBarSlices = new ArrayList<NapSlice>(napSlices);
            shortyBarSlices.add( new NapSlice( NapDimension.CREATIVE, shortyBarId) );
            shortyBarSlices.add( new NapSlice( NapDimension.ASSET) );
            
            // Convert back to simple array to re-use napService method
            NapSlice[] simpleArray2 = new NapSlice[ shortyBarSlices.size() ];
            shortyBarResponse = napService.getTotalMetrics(startDate, stopDate,
            		shortyBarSlices.toArray( simpleArray2 ) );
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
  

        // Data is in leaf node (if it exists)
        if (!response.getMetrics().isEmpty())
        {
            try
            {
                int depth = slices.length;
                // Start with root node
                NapNode dataNode = response.getMetrics().iterator().next();
                // Iterate (d-1) more times
                for (int i = 1; i < depth; i++)
                {
                    dataNode = dataNode.getChildren().iterator().next();
                }
                
                Collection<NapNode> shareBarAssets = shareBarResponse.getMetrics().iterator().next().getChildren().iterator().next().getChildren();
                Collection<NapNode> shortyBarAssets = shortyBarResponse.getMetrics().iterator().next().getChildren().iterator().next().getChildren();

                for ( final String leafName : leafNames ) {

                  // Here, we find the ip.share_bar metrics to append to our overall totals
                  NapNode foundNode = Iterables.find(
                    dataNode.getChildren(), 
                    new Predicate<NapNode>() {
                      public boolean apply(NapNode arg) 
                        { 
                          return arg.getName().equals( leafName ); 
                        }
                      }, new NapNode());
                  
                  // Clear the mediaTime metric
                  //foundNode.getMetricsTotalsRollup().setMediaTimeStory( 0 );
                  
                  NapNode foundShareBarNode = Iterables.find(
                      shareBarAssets, 
                      new Predicate<NapNode>() {
                        public boolean apply(NapNode arg) 
                          { 
                            return arg.getName().equals( leafName ); 
                          }
                        }, new NapNode());
                      
                      if ( foundShareBarNode.getName() != null ) {
                        // Divide by 1000 to get seconds from millis
                        //foundNode.getMetricsTotalsRollup().setMediaTimeStory(
                        //    ( foundNode.getMetricsTotalsRollup().getMediaTimeStory() + foundShareBarNode.getMetricsTotalsRollup().getMediaTimeStory() ));
                                              
                        // Let's add ShareBar Metrics to the parent node (this will make numbers match the Ad level numbers, DEV-6460)
                        foundNode.getMetricsTotalsRollup().setEngagementConsumesStory( 
                            foundNode.getMetricsTotalsRollup().getEngagementConsumesStory() + foundShareBarNode.getMetricsTotalsRollup().getEngagementConsumesStory() );
                        
                        foundNode.getMetricsTotalsRollup().setEngagementAmplifiesStory(  
                            foundNode.getMetricsTotalsRollup().getEngagementAmplifiesStory() + foundShareBarNode.getMetricsTotalsRollup().getEngagementAmplifiesStory() );
                        
                        foundNode.getMetricsTotalsRollup().setEngagementSharesStory( 
                            foundNode.getMetricsTotalsRollup().getEngagementSharesStory() + foundShareBarNode.getMetricsTotalsRollup().getEngagementSharesStory() );
                      }
                      
                  // Here, we find the ip.shorty_bar metrics to append to our overall totals
                  NapNode foundShortyBarNode = Iterables.find(
                          shortyBarAssets, 
                          new Predicate<NapNode>() {
                            public boolean apply(NapNode arg) 
                              { 
                                return arg.getName().equals( leafName ); 
                              }
                            }, new NapNode());
                  
                  if ( foundShortyBarNode.getName() != null ) {
                      // Divide by 1000 to get seconds from millis
                      //foundNode.getMetricsTotalsRollup().setMediaTimeStory(
                      //    ( foundNode.getMetricsTotalsRollup().getMediaTimeStory() + foundShortyBarNode.getMetricsTotalsRollup().getMediaTimeStory() ));
                                            
                      // Let's add ShortyBar Metrics to the parent node (this will make numbers match the Ad level numbers, DEV-6460)
                      foundNode.getMetricsTotalsRollup().setEngagementConsumesStory( 
                          foundNode.getMetricsTotalsRollup().getEngagementConsumesStory() + foundShortyBarNode.getMetricsTotalsRollup().getEngagementConsumesStory() );
                      
                      foundNode.getMetricsTotalsRollup().setEngagementAmplifiesStory(  
                          foundNode.getMetricsTotalsRollup().getEngagementAmplifiesStory() + foundShortyBarNode.getMetricsTotalsRollup().getEngagementAmplifiesStory() );
                      
                      foundNode.getMetricsTotalsRollup().setEngagementSharesStory( 
                          foundNode.getMetricsTotalsRollup().getEngagementSharesStory() + foundShortyBarNode.getMetricsTotalsRollup().getEngagementSharesStory() );
                    }
      
                      if ( foundNode.getName() == null ) {
                        infSummary = new InfluenceSummary();
                        infSummary = infSummary.setToZero();
                      }
                      else {
                    	// Convert milliseconds to seconds
                	    foundNode.getMetricsTotalsRollup().setMediaTimeStory(
                              ( foundNode.getMetricsTotalsRollup().getMediaTimeStory() / 1000 ));
                              
                        infSummary = influenceSummaryTransformer.transform( foundNode );
                      }
                      
                      // TODO: This is a "not-so-pretty" injection of data in already transformed object,
                      // TODO: we might want to refactor this code and make transform method - better
                      
                      // Setting to zero to make sure we don't have story impressions from Campaign/Ad level
                      infSummary.getTotalInfluence().setStoryImpressions( 0 );
                      if (infSummary != null && infSummary.getTotalInfluence() != null && foundShareBarNode.getName() != null)
                      {
                          infSummary.getTotalInfluence().setStoryImpressions(
                        	  infSummary.getTotalInfluence().getStoryImpressions() + foundShareBarNode.getMetricsTotalsRollup().getMediaViewsStory()
                          );
                      }
                      if (infSummary != null && infSummary.getTotalInfluence() != null && foundShortyBarNode.getName() != null)
                      {
                          infSummary.getTotalInfluence().setStoryImpressions(
                        	  infSummary.getTotalInfluence().getStoryImpressions() + foundShortyBarNode.getMetricsTotalsRollup().getMediaViewsStory()
                          );
                      }

                      results.put( leafName, infSummary );
      
               }
            }
            catch (Exception e)
            {
              e.printStackTrace();
                // If we cannot navigate this graph for any reason, assume there is just no data
            }
        }
    
        return results;
    }
}
