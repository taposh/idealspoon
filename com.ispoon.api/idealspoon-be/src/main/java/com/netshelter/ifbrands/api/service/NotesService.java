package com.netshelter.ifbrands.api.service;


import com.netshelter.ifbrands.api.model.campaign.Notes;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.Map;

/**
 * User: taposhdr
 * Date: 6/28/13
 */
public interface NotesService
{

    public static final String NOTES_CACHE = "ifb.mvc.notes";

    public static final String NOTE_TEMPLATE_CAMPAIGN_CREATED           = "Campaign created";
    public static final String NOTE_TEMPLATE_AD_CREATED                 = "%s ad has been created";
    public static final String NOTE_TEMPLATE_CAMPAIGN_STATUS_CHANGED    = "Campaign status changed to %s";
    public static final String NOTE_TEMPLATE_AD_STATE_CHANGED           = "%s ad status changed to %s";

    // Cache Services
    public void flushCache();

    ///////////////////
    // Notes Management //
    ///////////////////
    public Notes create( Integer userKey, Integer campaignID, String notesText);

    public boolean delete( Integer notesId );

    public Notes update( Integer notesId, Integer userKey, Integer campaignID, String notesText);

    public Notes get( Integer notesId );

    public List<Notes> getList( List<Integer> notesIds );

    public Map<Integer, List<Notes>> getListByCampaignIds( List<Integer> campaignIds );

}
