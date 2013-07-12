package com.netshelter.ifbrands.quartz;

/**
 * User: Dmitriy T
 * Date: 1/24/13
 */

import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.campaign.CampaignType;
import com.netshelter.ifbrands.api.model.influence.InfluenceComparison;
import com.netshelter.ifbrands.api.model.influence.InfluenceSummary;
import com.netshelter.ifbrands.api.service.CampaignService;
import com.netshelter.ifbrands.api.service.InfluenceService;
import com.netshelter.ifbrands.data.dao.CampaignDao;
import com.netshelter.ifbrands.etl.transform.campaign.CampaignTransformer;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This quartz job is updating campaigns marking them completed
 * Criteria for update is: end campaign date in the past and status is not "complete"
 */
public class CampaignStatusUpdateJob
        extends QuartzJobBean
{
    private static Logger logger = Logger.getLogger(CampaignStatusUpdateJob.class);

    private static CampaignService campaignService                  = null; // will be set once and reused every time
    private static InfluenceService influenceService                = null; // will be set once and reused every time
    private static CampaignDao.CampaignStatusDao campaignStatusDao  = null; // will be set once and reused every time
    protected void execute() throws JobExecutionException
    {
        executeInternal(null);
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        Date now = new Date();
        long startTime = now.getTime();
        int updatedCount = 0;
        Collection<Integer> statusIds = new ArrayList<Integer>(2);
        statusIds.add(campaignStatusDao.PENDING.getCampaignStatusId());
        statusIds.add(campaignStatusDao.ACTIVE.getCampaignStatusId());
        Collection<Campaign> campaigns = campaignService.getCampaigns(null, null, null, null, statusIds, null, null, null);
        for (Iterator<Campaign> iterator = campaigns.iterator(); iterator.hasNext(); )
        {
            Campaign campaign = iterator.next();
            if (campaign.getCampaignStatus().getId() != campaignStatusDao.INACTIVE.getCampaignStatusId()
                    && campaign.getStopDate().toDate().before(now))
            {
                logger.info("Need to update campaign (id:" + campaign.getId() + ") with status inactive");
                campaignService.updateCampaign(campaign.getId(), null, null, null, null, null, null, null,
                        campaignStatusDao.INACTIVE.getCampaignStatusId(), null, null, null, null, null, null, null, null);
                updatedCount++;
            }
        }

        // JIRA: Nightly check if paid COMPLETED campaigns have 0 impressions - then disable
        // JIRA: http://jira.inpwrd.net/browse/DEV-7028
        updatedCount = 0;
        campaigns = campaignService.getCampaigns(null, null, null, null, Collections.singleton(campaignStatusDao.INACTIVE.getCampaignStatusId()), null, null, null);
        for (Iterator<Campaign> iterator = campaigns.iterator(); iterator.hasNext(); )
        {
            Campaign campaign = iterator.next();
            if (campaign.isCampaignEnabled() && campaign.getCampaignType().getId() == 2) // Paid only...
            {
                InfluenceComparison<InfluenceSummary> influenceComparison = influenceService.getSummaryPaidByCampaign(
                    Collections.singleton(campaign.getId()), null, null, false);
                long totalImpressions = 0;
                if (influenceComparison.values() != null && influenceComparison.values().size() > 0)
                {


                    InfluenceSummary influenceSummary = influenceComparison.get(campaign.getId()+"");
                    if (influenceSummary != null && influenceSummary.getTotalInfluence() != null)
                    {
                        totalImpressions += influenceSummary.getTotalInfluence().getImpressions();
                    }
                }
                if (totalImpressions == 0)
                {
                    updatedCount++;
                        logger.info("Need to update campaign (id:" + campaign.getId() + ") as disabled");
                    campaignService.updateCampaign(campaign.getId(), null, null, null, null, null, null, false,
                                                   null, null, null, null, null, null, null, null, null);
                }
            }
        }
        logger.info("Finished CampaignStatusUpdateJob in " + (System.currentTimeMillis() - startTime) + "ms");
    }

    // spring bean wiring
    public void setCampaignService(CampaignService val)
    {
        campaignService = val;
    }
    public void setCampaignStatusDao(CampaignDao.CampaignStatusDao val)
    {
        campaignStatusDao = val;
    }
    public void setInfluenceService(InfluenceService val)
    {
        influenceService = val;
    }
}