package com.netshelter.ifbrands.api.model.campaign;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Dmitriy T
 */
public class AdNetwork
    implements Serializable
{
    private Integer adNetworkId;
    private String adNetworkName;
    private DateTime createTimestamp;

    public AdNetwork(Integer adNetworkId, String adNetworkName, DateTime createTimestamp)
    {
        this.adNetworkId = adNetworkId;
        this.adNetworkName = adNetworkName;
        this.createTimestamp = createTimestamp;
    }

    @Override
    public String toString()
    {
        return "AdNetwork{" +
                "adNetworkId=" + adNetworkId +
                ", adNetworkName='" + adNetworkName + '\'' +
                ", createTimestamp=" + createTimestamp +
                '}';
    }

    public Integer getAdNetworkId()
    {
        return adNetworkId;
    }

    public void setAdNetworkId(Integer adNetworkId)
    {
        this.adNetworkId = adNetworkId;
    }

    public String getAdNetworkName()
    {
        return adNetworkName;
    }

    public void setAdNetworkName(String adNetworkName)
    {
        this.adNetworkName = adNetworkName;
    }

    public DateTime getCreateTimestamp()
    {
        return createTimestamp;
    }

    public void setCreateTimestamp(DateTime createTimestamp)
    {
        this.createTimestamp = createTimestamp;
    }
}
