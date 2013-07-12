/* $Id: AppListener.java,v 1.11 2010/07/26 23:52:02 bill.gray Exp $ */
package com.netshelter.ifbrands;

import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.api.model.ActionType;
import com.netshelter.ifbrands.api.service.ActionLogService;
import com.netshelter.ifbrands.api.service.InfluenceService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Registered AppListener class. This class is called when the webapp is loaded for the first time. It is used
 * to do one-time static initialisation of objects, such as Hibernate, Logging, etc..
 *
 * @author bill.gray
 */
public class AppListener
    implements ServletContextListener
{
    public static final String P_BASE_TOMCAT = "catalina.base", P_BASE_JETTY = "jetty.home",
            P_BASE_SERVER = "server.base", P_BASE_ALL_YOUR = "belong.to.us";

    private Logger logger = LoggerFactory.getLogger();


    @Override
    public void contextInitialized(ServletContextEvent ce)
    {
        logger.info("Initialising context...");
        try
        {
            validateTimezone();
            guessServerImplementation();

            WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
            if (context != null)
            {
                ActionLogService actionLogService = (ActionLogService)context.getBean("actionLogService");
                if (actionLogService != null)
                {
                    String localHostname = null;
                    try
                    {
                        localHostname = java.net.InetAddress.getLocalHost().getHostName();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    actionLogService.makeLogEntry(
                            ActionType.SYSTEM_STARTED,
                            null,
                            null,
                            null,
                            localHostname
                    );
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("Could not initialize servlet", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        logger.info("Destroying context...");
    }

    /**
     * Abort server launch if TimeZone has not been set properly.
     */
    private void validateTimezone()
    {
        // Fail if we are not in the right timezone
        TimeZone tz = TimeZone.getDefault();
        if (!tz.hasSameRules(TimeZone.getTimeZone("UTC")))
        {
            throw new IllegalStateException("Server TimeZone set to '" + tz.getDisplayName() + "' but should be 'UTC'."
                                                    + " Add '-Duser.timezone=UTC' to Tomcat VM launch parameters.");
        }
    }

    /**
     * Check 'well-known' system properties to determine if we are being run on Tomcat or Jetty. Create a
     * generic BASE_APP property which we can then use to find things within our server's file structure.
     */
    private void guessServerImplementation()
    {
        // Guess if we're Jetty or Tomcat
        String appServerHome = System.getProperty(P_BASE_TOMCAT);
        if (appServerHome == null)
        {
            appServerHome = System.getProperty(P_BASE_JETTY);
        }

        // If befuddled, assume we're running an embedded instance
        if (appServerHome == null)
        {
            appServerHome = ".";
        }

        // Set generic property
        System.setProperty(P_BASE_SERVER, appServerHome);
    }
}
