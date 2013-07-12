package com.netshelter.ifbrands.api.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.BuildVersion;
import com.netshelter.ifbrands.api.model.StatusInfo;
import com.netshelter.ifbrands.etl.analytics.NapServicesImpl;
import com.netshelter.ifbrands.etl.dataplatform.DpServicesImpl;

public class StatusServiceImpl
    implements StatusService
{
    private Logger logger = LoggerFactory.getLogger();

    private String jdbcUrl;
    private BuildVersion buildVersion;

    // Services with caches
    @Autowired(required = false)
    private AdService adService;
    @Autowired(required = false)
    private FeedService feedService;
    @Autowired(required = false)
    private UserService userService;
    @Autowired(required = false)
    private EntityService entityService;
    @Autowired(required = false)
    private InfluenceService influenceService;
    @Autowired
    private ShareService shareService;
    @Autowired(required = false)
    private DataSource dataSource;
    @Autowired(required = false)
    private DpServicesImpl dpServicesImpl;
    @Autowired(required = false)
    private NapServicesImpl napServicesImpl;
    @Autowired
    private TrackingService trackingService = null;

    @Required
    public void setJdbcUrl(String jdbcUrl)
    {
        this.jdbcUrl = jdbcUrl;
    }

    @Required
    public void setBuildVersion(BuildVersion buildVersion)
    {
        this.buildVersion = buildVersion;
    }

    @Override
    public void flushAllCaches()
    {
        adService.flushCache();
        feedService.flushCache();
        userService.flushCache();
        entityService.flushCache();
        influenceService.flushCache();
        trackingService.flushCache();
    }

    @Override
    public StatusInfo getStatusInfo()
    {
        StatusInfo info = new StatusInfo("inPowered for Brands");
        info.setBuildVersion(buildVersion);

        boolean status;
        // Test db access
        try
        {
            JdbcTemplate jdbc = new JdbcTemplate(dataSource);
            jdbc.setQueryTimeout(15);
            jdbc.queryForInt("select count(*) from ifb_ad");
            status = true;
        }
        catch (Exception e)
        {
            logger.warn("JDBC status check failed", e.getMessage());
            status = false;
        }
        info.addService("jdbc.url", jdbcUrl, status);

        // Test dpapi access
        try
        {
            status = dpServicesImpl.isAlive();
        }
        catch (Exception e)
        {
            logger.warn("DataPlatform status check failed", e.getMessage());
            status = false;
        }
        info.addService("dpservices.authority", dpServicesImpl.getAuthority(), status);

        // Test nap access
        try
        {
            status = napServicesImpl.isAlive();
        }
        catch (Exception e)
        {
            logger.warn("Analytics status check failed", e.getMessage());
            status = false;
        }
        info.addService("napservices.authority", napServicesImpl.getAuthority(), status);


        // Test share api service
        try
        {
            status = shareService.isAlive();
        }
        catch (Exception e)
        {
            logger.warn("ShareAPI status check failed", e.getMessage());
            status = false;
        }
        info.addService("shareservice.authority", shareService.getShareApiAuthority(), status);

        return info;

    }
}
