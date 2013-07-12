package com.netshelter.ifbrands.api.model.campaign;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * User: Dmitriy T
 */
public class AdSize
    implements java.io.Serializable
{

    private Integer adSizeId;
    private String adSizeName;
    private int width;
    private int height;
    private DateTime createTimestamp;

    public AdSize(Integer adSizeId, String adSizeName, int width, int height, DateTime createTimestamp)
    {
        this.adSizeId = adSizeId;
        this.adSizeName = adSizeName;
        this.width = width;
        this.height = height;
        this.createTimestamp = createTimestamp;
    }

    @Override
    public String toString()
    {
        return "AdSize{" +
                "adSizeId=" + adSizeId +
                ", adSizeName='" + adSizeName + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", createTimestamp=" + createTimestamp +
                '}';
    }

    public Integer getAdSizeId()
    {
        return adSizeId;
    }

    public String getAdSizeName()
    {
        return adSizeName;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public DateTime getCreateTimestamp()
    {
        return createTimestamp;
    }
}
