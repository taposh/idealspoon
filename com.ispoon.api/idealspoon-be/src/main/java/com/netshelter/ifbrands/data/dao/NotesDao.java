package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

//Notes DAO Author: Taposh Dutta Roy
//June 28 2013

public class NotesDao
    extends BaseDao <IfbNotes>
{
    @Override
    protected Class<IfbNotes> getEntityClass()
    {
        return IfbNotes.class;
    }

    @Override
    public String asString( IfbNotes entity )
    {
        return String.format( "IfbNotes(%d:%s,%s,%d)", entity.getNotesId(), entity.getNotesText(), entity.getUserKey(),entity.getCampaignId() );
    }

    @Override
    protected Serializable getIdentifier(IfbNotes entity)
    {
        return entity.getNotesId();
    }

    @Override
    protected void updateIdentifier(IfbNotes entity, Serializable id) {
        //To change body of implemented methods use File | Settings | File Templates.
        entity.setNotesId( (Integer)id );
    }


    @Override
    public boolean theSame(IfbNotes a, IfbNotes b) {

        // Checking if a and b are not null and notes text is different
        return (a != null) && (b != null) && a.getNotesText().equals( b.getNotesText() );
    }
}
