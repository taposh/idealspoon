package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.campaign.Notes;
import com.netshelter.ifbrands.data.dao.NotesDao;
import com.netshelter.ifbrands.data.entity.IfbNotes;
import com.netshelter.ifbrands.etl.transform.NotesTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: dtemesov
 * Date: 6/28/13
 */
@Component
public class NotesServiceImpl
    implements NotesService
{

    @Autowired
    NotesDao notesDao = null;
    @Autowired
    NotesTransformer notesTransformer = null;

    @Override
    public void flushCache()
    {
        // TODO: implement later
    }

    @Override
    public Notes create(Integer userKey, Integer campaignID, String notesText)
    {
        IfbNotes notes = new IfbNotes();
        notes.setUserKey(userKey);
        notes.setCampaignId(campaignID);
        notes.setNotesText(notesText);
        notes.setCreateTimestamp(new Date());
        notes.setUpdateTimestamp(new Date());
        notes = notesDao.save(notes);
        return notesTransformer.transform(notes);
    }

    @Override
    public boolean delete(Integer notesId)
    {
        IfbNotes note = notesDao.getById(notesId);
        boolean deleted = false;
        if (note != null)
        {
            notesDao.delete(note);
            deleted = true;
        }

        return deleted;
    }

    @Override
    public Notes update(Integer notesId, Integer userKey, Integer campaignID, String notesText)
    {
        IfbNotes note = notesDao.getById(notesId);
        if (note != null)
        {
            if (userKey != null)
            {
                note.setUserKey(userKey);
            }
            if (campaignID != null)
            {
                note.setCampaignId(campaignID);
            }
            if (notesText != null)
            {
                note.setNotesText(notesText);
            }
            note.setUpdateTimestamp(new Date());
            note = notesDao.update(note);
        }
        return notesTransformer.transform(note);
    }

    @Override
    @Cacheable(NOTES_CACHE)
    public Notes get(Integer notesId)
    {
        return notesTransformer.transform(notesDao.getById(notesId));
    }

    @Override
    public List<Notes> getList(List<Integer> notesIds)
    {
        return notesTransformer.transform(notesDao.getByIds(notesIds));
    }

    @Override
    public Map<Integer, List<Notes>> getListByCampaignIds(List<Integer> campaignIds)
    {
        if (campaignIds == null) return null;
        Map<Integer, List<Notes>> result = new HashMap<Integer, List<Notes>>(campaignIds.size());
        for (Integer campaignId : campaignIds)
        {
            List<IfbNotes> notesList = notesDao.findByProperty("campaignId", campaignId);
            result.put(campaignId, notesTransformer.transform(notesList));
        }
        return result;
    }
}
