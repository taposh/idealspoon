package com.netshelter.ifbrands.data.entity;
// Generated May 15, 2013 11:46:26 AM by Hibernate Tools 3.2.4.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * IfbTracking generated by hbm2java
 */
@Entity
@Table(name="ifb_tracking"
)
public class IfbTracking  implements java.io.Serializable {


     private Integer trackingId;
     private Integer objectId;
     private String objectType;
     private String trackingSet;
     private String trackingType;
     private String textValue;
     private Date createTimestamp;

    public IfbTracking() {
    }

	
    public IfbTracking(String objectType, String trackingSet, String trackingType, Date createTimestamp) {
        this.objectType = objectType;
        this.trackingSet = trackingSet;
        this.trackingType = trackingType;
        this.createTimestamp = createTimestamp;
    }
    public IfbTracking(Integer objectId, String objectType, String trackingSet, String trackingType, String textValue, Date createTimestamp) {
       this.objectId = objectId;
       this.objectType = objectType;
       this.trackingSet = trackingSet;
       this.trackingType = trackingType;
       this.textValue = textValue;
       this.createTimestamp = createTimestamp;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="tracking_id", unique=true, nullable=false)
    public Integer getTrackingId() {
        return this.trackingId;
    }
    
    public void setTrackingId(Integer trackingId) {
        this.trackingId = trackingId;
    }

    
    @Column(name="object_id")
    public Integer getObjectId() {
        return this.objectId;
    }
    
    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    
    @Column(name="object_type", nullable=false)
    public String getObjectType() {
        return this.objectType;
    }
    
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    
    @Column(name="tracking_set", nullable=false)
    public String getTrackingSet() {
        return this.trackingSet;
    }
    
    public void setTrackingSet(String trackingSet) {
        this.trackingSet = trackingSet;
    }

    
    @Column(name="tracking_type", nullable=false)
    public String getTrackingType() {
        return this.trackingType;
    }
    
    public void setTrackingType(String trackingType) {
        this.trackingType = trackingType;
    }

    
    @Column(name="text_value", length=65535)
    public String getTextValue() {
        return this.textValue;
    }
    
    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_timestamp", nullable=false, length=19)
    public Date getCreateTimestamp() {
        return this.createTimestamp;
    }
    
    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }




}

