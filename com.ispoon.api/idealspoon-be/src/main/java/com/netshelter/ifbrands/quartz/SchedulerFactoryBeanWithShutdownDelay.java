package com.netshelter.ifbrands.quartz;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * User: Dmitriy T
 * Date: 12/13/12
 *
 * Workaround for quartz bug: https://jira.terracotta.org/jira/browse/QTZ-192
 */
public class SchedulerFactoryBeanWithShutdownDelay extends SchedulerFactoryBean {

    protected Logger logger = LoggerFactory.getLogger();

    @Override
    public void destroy() throws SchedulerException {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = null;

        try {
            scheduler = schedulerFactory.getScheduler();
            if ( scheduler != null && scheduler.isStarted() ) {
                logger.info("Shutting down Quartz Scheduler (context based)");
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        super.destroy();
        try {
            Thread.sleep( 1000 );
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }
}