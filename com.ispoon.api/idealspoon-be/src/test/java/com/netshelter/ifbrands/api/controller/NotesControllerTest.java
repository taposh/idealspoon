package com.netshelter.ifbrands.api.controller;

import com.netshelter.ifbrands.api.model.campaign.Notes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dmitriy T
 */
public class NotesControllerTest
    extends BaseControllerTest
{

    @Autowired
    NotesController controller;

    @Test
    public void testCRUD()
    {
        Notes note = controller.create("test", 105, 101);
        System.err.println("note: " + note);
        assertNotNull("Notes entity created, should not be null");
        note = controller.update(note.getNotesId(), "other-text", null, null);
        Boolean removed = controller.delete(note.getNotesId());
        assertTrue("Notes entity was not removed on delete", removed);
    }

}
