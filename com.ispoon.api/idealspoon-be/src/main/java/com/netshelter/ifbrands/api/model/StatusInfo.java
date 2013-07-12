package com.netshelter.ifbrands.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.netshelter.ifbrands.BuildVersion;

/**
 * Holder for current system status info.
 * @author bgray
 *
 */
public class StatusInfo
{
  private String serverName;
  private boolean serverOkay = true;
  private BuildVersion buildVersion;
  private List<ServiceInfo> services = new ArrayList<ServiceInfo>();

  public StatusInfo( String serverName )
  {
    this.serverName = serverName;
  }

  public String getServerName()
  {
    return serverName;
  }

  public Date getServerDateTime()
  {
    return new Date();
  }

  public boolean isServerOkay()
  {
    return serverOkay;
  }

  public BuildVersion getBuildVersion()
  {
    return buildVersion;
  }

  public void setBuildVersion( BuildVersion buildVersion )
  {
    this.buildVersion = buildVersion;
  }

  public List<ServiceInfo> getServices()
  {
    return services;
  }

  public void addService( String name, String uri, boolean serviceOkay )
  {
    services.add( new ServiceInfo( name, uri, serviceOkay ));
    if( !serviceOkay ) serverOkay=false;  // One bad apple...
  }


  @Override
  public String toString()
  {
    return "StatusInfo [serverName=" + serverName + ", serverOkay=" + serverOkay + ", buildVersion="
        + buildVersion + ", services=" + services + "]";
  }


  /**
   * Holder for the status of a single downstream service.
   * @author bgray
   *
   */
  public static class ServiceInfo
  {
    String name, uri;
    boolean okay;

    public ServiceInfo( String name, String uri, boolean okay )
    {
      this.name = name;
      this.uri = uri;
      this.okay = okay;
    }

    public String getName()
    {
      return name;
    }
    public String getUri()
    {
      return uri;
    }
    public boolean isOkay()
    {
      return okay;
    }

    @Override
    public String toString()
    {
      return "ServiceInfo [name=" + name + ", uri=" + uri + ", okay=" + okay + "]";
    }

  }
}
