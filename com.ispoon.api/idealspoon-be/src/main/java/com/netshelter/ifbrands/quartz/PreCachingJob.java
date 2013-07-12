package com.netshelter.ifbrands.quartz;

import com.netshelter.ifbrands.api.model.ActionType;
import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.influence.InfluenceBreakdown;
import com.netshelter.ifbrands.api.model.influence.InfluenceTrend;
import com.netshelter.ifbrands.api.service.ActionLogService;
import com.netshelter.ifbrands.api.service.CampaignService;
import com.netshelter.ifbrands.api.service.InfluenceService;
import com.netshelter.ifbrands.api.util.MvcUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Statistics;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitriy T
 */
public class PreCachingJob
        extends QuartzJobBean
{

    private static Logger logger = Logger.getLogger(PreCachingJob.class);

    public static final String JOB_NAME                 = "summaryPaidByCampaignCachingJob";
    public static final String TRIGGER_NAME             = "summaryPaidByCampaignCachingJobTrigger";
    public static final int CAMPAIGN_TYPE               = 2; // TODO: hardcoded for now...
    public static final int CAMPAIGN_STATUS_ID_LIVE     = 2;
    public static final int CAMPAIGN_STATUS_ID_PENDING  = 1;
    public static final int CAMPAIGN_STATUS_ID_COMPLETED= 3;

    private static int delayBetweenJobsSeconds          = 30 * 60; // can be set as parameter in xml binding of the bean

    private static String cachingCategorySentiment      = "POSITIVE,NEUTRAL";


    private static InfluenceService influenceService    = null; // will be set once and reused every time
    private static CampaignService campaignService      = null; // will be set once and reused every time
    private static ActionLogService actionLogService    = null; // will be set once and reused every time



    /**
     * Main execution initial entry point, all future executions will be
     * scheduled in
     */
    public void execute() {
        try {
            executeInternal( null );
        } catch ( JobExecutionException e ) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeInternal( JobExecutionContext jobExecutionContext ) throws JobExecutionException {
        logger.info( "Executing [execute getSummaryPaidByCampaign] " + this.getClass().getSimpleName() );
        long startTime = System.currentTimeMillis();
        // Displaying statistics for all caches named: "ifb.*"
        CacheManager manager = CacheManager.getInstance();
        if (manager != null)
        {
            String [] cacheNames = manager.getCacheNames();
            for (int i = 0; i < cacheNames.length; i++)
            {
                String cacheName = cacheNames[i];
                if (StringUtils.isNotBlank(cacheName) && cacheName.startsWith("ifb."))
                {
                    Cache cache = manager.getCache(cacheName);
                    if (cache != null)
                    {
                        Statistics stats = cache.getStatistics();
                        logger.debug(stats);
                    }
                }
            }
        }

        try {
            long ms = System.currentTimeMillis();

            List<Campaign> campaignLiveList = (List<Campaign>)campaignService.getCampaigns(
                    null, true, null, null,
                    Collections.singleton(CAMPAIGN_STATUS_ID_LIVE),
                    Collections.singleton(CAMPAIGN_TYPE),
                    null);

            Collection<Integer> statusList = new ArrayList<Integer>();
            statusList.add(CAMPAIGN_STATUS_ID_PENDING);
            statusList.add(CAMPAIGN_STATUS_ID_LIVE);
            statusList.add(CAMPAIGN_STATUS_ID_COMPLETED);
            List<Campaign> campaignLivePendingCompletedList = (List<Campaign>)campaignService.getCampaigns(
                    null, true, null, null,
                    statusList,
                    Collections.singleton(CAMPAIGN_TYPE),
                    null );

            Collection<Integer> campaignLiveIds = new ArrayList<Integer>();
            for (Campaign campaign : campaignLiveList)
            {
                campaignLiveIds.add( campaign.getId() );
            }
            Collection<Integer> campaignLivePendingAndCompletedIds = new ArrayList<Integer>();
            for (Campaign campaign : campaignLivePendingCompletedList)
            {
                campaignLivePendingAndCompletedIds.add(campaign.getId());
            }
            // caching happens inside of the method getSummaryPaidByCampaign
            influenceService.getSummaryPaidByCampaign(campaignLivePendingAndCompletedIds, null, null, true);

            logger.info("Finished caching influenceService.getSummaryPaidByCampaign(" + campaignLivePendingAndCompletedIds.size() +
                                " campaigns) in " + (System.currentTimeMillis() - ms) + "ms");
            ms = System.currentTimeMillis();
            influenceService.searchNewStories_v2( (List)campaignLivePendingAndCompletedIds,
                                               InfluenceService.DEFAULT_DAYS_BACK_SEARCH,
                                               true );
            logger.debug("Finished caching influenceService.searchNewStories_v2(...) call with " + campaignLivePendingAndCompletedIds.size() +
                                 " campaigns in " + (System.currentTimeMillis() - ms) + "ms" );

            executeInfluenceBreakdownByCampaignCachingJob();
            actionLogService.makeLogEntry(
                    ActionType.PRE_CACHER_FINISHED,
                    null,
                    null,
                    null,
                    "took: " + (System.currentTimeMillis() - startTime) + "ms"
            );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        reScheduleJob();
    }

    private void executeInfluenceBreakdownByCampaignCachingJob() {
        logger.info( "Executing [execute getBreakdownByCampaign] " + this.getClass().getSimpleName() );
        try {
            long ms = System.currentTimeMillis();
            Collection<Integer> statusIds = new ArrayList<Integer>();
            statusIds.add(CAMPAIGN_STATUS_ID_LIVE);
            Collection<Campaign> campaignList = campaignService.getCampaigns( null, true, null, null,
                                                                              statusIds, Collections.singleton(CAMPAIGN_TYPE), null );
            List<Integer> campaignIds = new ArrayList<Integer>( campaignList.size() );
            for ( Iterator<Campaign> iterator = campaignList.iterator(); iterator.hasNext(); )
            {
                Campaign next = iterator.next();
                campaignIds.add( next.getId() );
            }

            List<String> sentiments = MvcUtils.getStringsFromFilter(cachingCategorySentiment);

            influenceService.getBreakdownByCampaign( campaignIds, null, sentiments, InfluenceService.Goal.TREND, true, true );
            influenceService.getBreakdownByCampaign( campaignIds, null, sentiments, InfluenceService.Goal.TREND, false, true );
            logger.info( "Finished caching 2x"+(campaignIds.size())+" campaigns executing getBreakdownByCampaign in " + (System.currentTimeMillis() - ms) + "ms" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Schedules InfluenceBreakdownByCampaignCachingJob.class job to
     * be executed in delayBetweenJobsSeconds, this will create a
     * new Scheduler object first time it is ran, and then reuse it.
     * Shutdown of the Scheduler will happen in SchedulerFactoryBean -> SchedulerFactoryBeanWithShutdownDelay
     */
    private void reScheduleJob()
    {
        try {
            StringBuilder scheduleNamePostFix = new StringBuilder();
            scheduleNamePostFix
                    .append( "-" )
                    .append( System.currentTimeMillis() );
            Date nextTriggerDate = new Date( System.currentTimeMillis() + (1000 * delayBetweenJobsSeconds) );
            logger.debug( "Scheduling next run of " + this.getClass().getSimpleName() + " for " + nextTriggerDate );
            JobDetail job = new JobDetail();
            job.setName( JOB_NAME + scheduleNamePostFix.toString() );
            job.setJobClass( PreCachingJob.class );

            SimpleTrigger trigger = new SimpleTrigger();
            trigger.setName( TRIGGER_NAME + scheduleNamePostFix );
            trigger.setStartTime( nextTriggerDate );

            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            // Retrieve a scheduler from schedule factory
            Scheduler scheduler = null;

            scheduler = schedulerFactory.getScheduler();
            if ( !scheduler.isStarted() )
            {
                scheduler.start();
            }
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // spring bean wiring
    public void setInfluenceService(InfluenceService val) {
        influenceService = val;
    }
    public void setDelayBetweenJobsSeconds(int val) {
        delayBetweenJobsSeconds = val;
    }
    public void setCampaignService(CampaignService val) {
        campaignService = val;
    }
    public void setActionLogService(ActionLogService actionLogService) {
        PreCachingJob.actionLogService = actionLogService;
    }
}
