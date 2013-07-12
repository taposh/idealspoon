package com.netshelter.ifbrands.api.model.campaign;

import org.joda.time.DateTime;

/**
 * User: taposhdr
 * Date: 6/28/13
 */
public class Notes

{

    private Integer notesId;
    private String notesText;
    private int campaignID;
    private int userKey;
    private DateTime createTimestamp;
    private DateTime updateTimestamp;
    private String userFullName;

    public Notes(Integer notesId, String notesText, int campaignID, int userKey, DateTime createTimestamp,
                 DateTime updateTimestamp, String userFullName)
    {
        this.notesId = notesId;
        this.notesText = notesText;
        this.campaignID = campaignID;
        this.userKey = userKey;
        this.createTimestamp = createTimestamp;
        //updateTimestamp is optional column
        this.updateTimestamp= updateTimestamp;
        this.userFullName = userFullName;
    }

    @Override
    public String toString()
    {
        return "Notes{" +
                "notesId=" + notesId +
                ", notesText='" + notesText + '\'' +
                ", campaignID=" + campaignID +
                ", userKey=" + userKey +
                ", createTimestamp=" + createTimestamp +
                ", updateTimestamp=" + updateTimestamp +
                ", userFullName='" + userFullName + '\'' +
                '}';
    }

    public String getUserFullName()
    {
        return userFullName;
    }

    public void setUserFullName(String userFullName)
    {
        this.userFullName = userFullName;
    }

    public Integer getNotesId()
    {
        return notesId;
    }

    public String getNotesText()
    {
        return notesText;
    }

    public int getCampaignID()
    {
        return campaignID;
    }

    public int getUserKey()
    {
        return userKey;
    }

    public DateTime getCreateTimestamp()
    {
        return createTimestamp;
    }

    public DateTime getUpdateTimestamp()
    {
        return updateTimestamp;
    }

    public void setNotesId(Integer notesId)
    {
        this.notesId = notesId;
    }

    public void setNotesText(String notesText)
    {
        this.notesText = notesText;
    }

    public void setCampaignID(int campaignID)
    {
        this.campaignID = campaignID;
    }

    public void setUserKey(int userKey)
    {
        this.userKey = userKey;
    }

    public void setCreateTimestamp(DateTime createTimestamp)
    {
        this.createTimestamp = createTimestamp;
    }

    public void setUpdateTimestamp(DateTime updateTimestamp)
    {
        this.updateTimestamp = updateTimestamp;
    }
}
