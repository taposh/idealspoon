package com.netshelter.ifbrands.quartz;

/**
 * User: Dmitriy T
 * Date: 1/23/13
 */

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.api.service.CampaignService;
import com.netshelter.ifbrands.api.service.InfluenceService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * This quartz job is updating campaigns marking them completed
 * Criteria for update is: end campaign date in the past and status still "active"
 */
public class CampaignStatusUpdateJob
    extends QuartzJobBean
{
    private static Logger logger = LoggerFactory.getLogger();

    public static final String JOB_NAME                 = "campaignStatusUpdateJob";
    public static final String TRIGGER_NAME             = "CampaignStatusUpdateJobTrigger";

    private static InfluenceService influenceService    = null; // will be set once and reused every time
    private static CampaignService campaignService      = null; // will be set once and reused every time

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // TODO: ..........
    }

    // spring bean wiring
    public void setInfluenceService(InfluenceService val) {
        influenceService = val;
    }
    public void setCampaignService(CampaignService val) {
        campaignService = val;
    }
}
