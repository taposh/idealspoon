package com.netshelter.ifbrands.data.entity;
// Generated Jun 3, 2013 11:00:37 AM by Hibernate Tools 3.2.4.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * IfbAdState generated by hbm2java
 */
@Entity
@Table(name="ifb_ad_state"
    , uniqueConstraints = @UniqueConstraint(columnNames="ad_state_name") 
)
public class IfbAdState  implements java.io.Serializable {


     private Integer adStateId;
     private String adStateName;
     private Date createTimestamp;
     private Set<IfbAd> ifbAds = new HashSet<IfbAd>(0);

    public IfbAdState() {
    }

	
    public IfbAdState(String adStateName, Date createTimestamp) {
        this.adStateName = adStateName;
        this.createTimestamp = createTimestamp;
    }
    public IfbAdState(String adStateName, Date createTimestamp, Set<IfbAd> ifbAds) {
       this.adStateName = adStateName;
       this.createTimestamp = createTimestamp;
       this.ifbAds = ifbAds;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="ad_state_id", unique=true, nullable=false)
    public Integer getAdStateId() {
        return this.adStateId;
    }
    
    public void setAdStateId(Integer adStateId) {
        this.adStateId = adStateId;
    }

    
    @Column(name="ad_state_name", unique=true, nullable=false)
    public String getAdStateName() {
        return this.adStateName;
    }
    
    public void setAdStateName(String adStateName) {
        this.adStateName = adStateName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_timestamp", nullable=false, length=19)
    public Date getCreateTimestamp() {
        return this.createTimestamp;
    }
    
    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="ifbAdState")
    public Set<IfbAd> getIfbAds() {
        return this.ifbAds;
    }
    
    public void setIfbAds(Set<IfbAd> ifbAds) {
        this.ifbAds = ifbAds;
    }




}


