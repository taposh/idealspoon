package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.campaign.Notes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Dmitriy T
 */
public class NotesServiceImplTest
    extends BaseServiceTest
{
    private final Random r = new Random();

    @Autowired
    private NotesService service = null;

    @Test
    public void testAdNetwork()
    {
        Notes note1 = service.create(101, 105, "test1");
        Notes note2 = service.create(101, 105, "test2");
        assertNotNull("Notes entity 1 should have been created", note1);
        assertNotNull("Notes entity 2 should have been created", note2);
        Map<Integer, List<Notes>> campaignNotes = service.getListByCampaignIds(Collections.singletonList(105));
        assertEquals("Expected to receive a map with size = 1", 1, campaignNotes.size());
        assertEquals("Expected to receive a map with notes list size = 2", 2, campaignNotes.get(105).size());
    }

}
