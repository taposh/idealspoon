package com.netshelter.ifbrands.quartz;

import com.netshelter.ifbrands.api.model.entity.Template;
import com.netshelter.ifbrands.api.model.usermanagement.GroupInfo;
import com.netshelter.ifbrands.api.service.TemplateService;
import com.netshelter.ifbrands.api.service.UserManagementServices;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.Map;

/**
 * User: Dmitriy T
 * Date: 2/11/13
 */
public class UserManagementListCachingJob
    extends QuartzJobBean
{

    private static Logger logger = Logger.getLogger(UserManagementListCachingJob.class);

    @Autowired
    private UserManagementServices userManagementServices       = null;

    @Autowired
    private TemplateService templateService = null;



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
        logger.info( "Starting to execute " + this.getClass().getSimpleName() );
        try
        {
            List<GroupInfo> groupInfoList = userManagementServices.getGroupInfoAll(true);
            logger.info("Received a list of group info objects size: " +
                    (groupInfoList != null?groupInfoList.size():"N/A") +
                    " items");
            Map<String, Template> templateMap = templateService.getAllMap(true); // ignore cache true
            if (templateMap != null)
            {
                logger.info("Received a map of Templates: " + templateMap.size());
            }
            else
            {
                logger.error("Did not receive any templates from remote template service");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
