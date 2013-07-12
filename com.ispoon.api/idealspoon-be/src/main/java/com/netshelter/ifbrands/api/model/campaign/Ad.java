package com.netshelter.ifbrands.api.model.campaign;

import com.netshelter.ifbrands.api.model.entity.Template;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Date;

public class Ad
{
    private Integer id;
    private AdType type;
    private AdStatus status;
    private AdState state;
    private Feed feed;
    private Campaign campaign;
    private String key, name;
    private String productName;
    private String productDestination;
    private String question;
    private AdNetwork adNetwork;
    private DateTime createTimestamp;
    private AdSize adSize;
    /* Being change / Taposh, June 19th 2013 / Jira 7314 */
    private LocalDate startDate;
    private LocalDate stopDate;
    /*end change */
    private String trackingSource = null; // will be filled in on service end
    private Template template;

    public Ad(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public AdType getType()
    {
        return type;
    }

    public void setType(AdType type)
    {
        this.type = type;
    }

    public AdStatus getStatus()
    {
        return status;
    }

    public void setStatus(AdStatus status)
    {
        this.status = status;
    }

    public AdState getState()
    {
        return state;
    }

    public void setState(AdState state)
    {
        this.state = state;
    }

    public Feed getFeed()
    {
        return feed;
    }

    public void setFeed(Feed feed)
    {
        this.feed = feed;
    }

    public Campaign getCampaign()
    {
        return campaign;
    }

    public void setCampaign(Campaign campaign)
    {
        this.campaign = campaign;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductDestination()
    {
        return productDestination;
    }

    public void setProductDestination(String productDestination)
    {
        this.productDestination = productDestination;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public AdNetwork getAdNetwork()
    {
        return adNetwork;
    }

    public void setAdNetwork(AdNetwork adNetwork)
    {
        this.adNetwork = adNetwork;
    }

    public DateTime getCreateTimestamp()
    {
        return createTimestamp;
    }

    public void setCreateTimestamp(DateTime createTimestamp)
    {
        this.createTimestamp = createTimestamp;
    }

    public AdSize getAdSize()
    {
        return adSize;
    }

    public void setAdSize(AdSize adSize)
    {
        this.adSize = adSize;
    }

    public Template getTemplate()
    {
        return template;
    }

    public void setTemplate(Template template)
    {
        this.template = template;
    }
/* Being change / Taposh, June 19th 2013 / Jira 7314 */

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }

    public void setStopDate(LocalDate stopDate) {
        this.stopDate = stopDate;
    }

    public String getTrackingSource()
    {
        return trackingSource;
    }

    public void setTrackingSource(String trackingSource)
    {
        this.trackingSource = trackingSource;
    }
    /*end change*/

    @Override
    public String toString()
    {
        return "Ad [id=" + id + ", key=" + key + ", type=" + type.getName() + ", status=" + status.getName() +
                ", feed=" + feed.getId() + ", campaign=" + campaign.getId() + "]";
    }

}
