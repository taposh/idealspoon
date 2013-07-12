package com.netshelter.ifbrands.data.entity;
// Generated Apr 12, 2013 12:26:23 PM by Hibernate Tools 3.2.4.GA


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
 * IfbCampaignStatus generated by hbm2java
 */
@Entity
@Table(name="ifb_campaign_status"
    , uniqueConstraints = @UniqueConstraint(columnNames="campaign_status_name") 
)
public class IfbCampaignStatus  implements java.io.Serializable {


     private Integer campaignStatusId;
     private String campaignStatusName;
     private Date createTimestamp;
     private Set<IfbCampaign> ifbCampaigns = new HashSet<IfbCampaign>(0);

    public IfbCampaignStatus() {
    }

	
    public IfbCampaignStatus(String campaignStatusName, Date createTimestamp) {
        this.campaignStatusName = campaignStatusName;
        this.createTimestamp = createTimestamp;
    }
    public IfbCampaignStatus(String campaignStatusName, Date createTimestamp, Set<IfbCampaign> ifbCampaigns) {
       this.campaignStatusName = campaignStatusName;
       this.createTimestamp = createTimestamp;
       this.ifbCampaigns = ifbCampaigns;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="campaign_status_id", unique=true, nullable=false)
    public Integer getCampaignStatusId() {
        return this.campaignStatusId;
    }
    
    public void setCampaignStatusId(Integer campaignStatusId) {
        this.campaignStatusId = campaignStatusId;
    }

    
    @Column(name="campaign_status_name", unique=true, nullable=false)
    public String getCampaignStatusName() {
        return this.campaignStatusName;
    }
    
    public void setCampaignStatusName(String campaignStatusName) {
        this.campaignStatusName = campaignStatusName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_timestamp", nullable=false, length=19)
    public Date getCreateTimestamp() {
        return this.createTimestamp;
    }
    
    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="ifbCampaignStatus")
    public Set<IfbCampaign> getIfbCampaigns() {
        return this.ifbCampaigns;
    }
    
    public void setIfbCampaigns(Set<IfbCampaign> ifbCampaigns) {
        this.ifbCampaigns = ifbCampaigns;
    }




}

