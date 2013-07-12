package com.netshelter.ifbrands.api.model.campaign;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

/**
 * @author Dmitriy T
 */
public class Tracking
    implements java.io.Serializable
{
    private Integer trackingId;
    private Integer objectId;
    private ObjectTypeEnum objectType;
    private TrackingSetEnum trackingSet;
    private TrackingTypeEnum trackingType;
    private String textValue;
    private DateTime createTimestamp;

    public Tracking(Integer trackingId, Integer objectId, ObjectTypeEnum objectType,
                    TrackingSetEnum trackingSet, TrackingTypeEnum trackingType, String textValue,
                    DateTime createTimestamp)
    {
        this.trackingId = trackingId;
        this.objectId = objectId;
        this.objectType = objectType;
        this.trackingSet = trackingSet;
        this.trackingType = trackingType;
        this.textValue = textValue;
        this.createTimestamp = createTimestamp;
    }

    @Override
    public String toString()
    {
        return "Tracking{" +
                "trackingId=" + trackingId +
                ", objectId=" + objectId +
                ", objectType=" + objectType +
                ", trackingSet=" + trackingSet +
                ", trackingType=" + trackingType +
                ", textValue='" + textValue + '\'' +
                ", createTimestamp=" + createTimestamp +
                '}';
    }

    public Integer getTrackingId()
    {
        return trackingId;
    }

    public void setTrackingId(Integer trackingId)
    {
        this.trackingId = trackingId;
    }

    public Integer getObjectId()
    {
        return objectId;
    }

    public void setObjectId(Integer objectId)
    {
        this.objectId = objectId;
    }

    public ObjectTypeEnum getObjectType()
    {
        return objectType;
    }

    public void setObjectType(ObjectTypeEnum objectType)
    {
        this.objectType = objectType;
    }

    public TrackingSetEnum getTrackingSet()
    {
        return trackingSet;
    }

    public void setTrackingSet(TrackingSetEnum trackingSet)
    {
        this.trackingSet = trackingSet;
    }

    public TrackingTypeEnum getTrackingType()
    {
        return trackingType;
    }

    public void setTrackingType(TrackingTypeEnum trackingType)
    {
        this.trackingType = trackingType;
    }

    public String getTextValue()
    {
        return textValue;
    }

    public void setTextValue(String textValue)
    {
        this.textValue = textValue;
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
