package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.IfbNotes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Random;

/**
 * @author Dmitriy T
 */
public class NotesDaoTest
        extends BaseDaoTest<IfbNotes>
{
    private final Random r = new Random();

    @Autowired
    NotesDao dao;

    @Override
    protected BaseDao<IfbNotes> getDao()
    {
        return dao;
    }

    @Override
    protected IfbNotes makeEntity()
    {
        IfbNotes entity = new IfbNotes();
        entity.setNotesText("test-" + r.nextDouble());
        entity.setCampaignId(105);
        entity.setUpdateTimestamp(new Date());
        entity.setCreateTimestamp(new Date());
        entity.setUserKey(101);
        return dao.save( entity );
    }

    // testBaseDao will test the CRUD...

}
