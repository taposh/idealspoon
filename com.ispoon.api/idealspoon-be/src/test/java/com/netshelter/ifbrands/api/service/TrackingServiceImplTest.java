package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.campaign.ObjectTypeEnum;
import com.netshelter.ifbrands.api.model.campaign.Tracking;
import com.netshelter.ifbrands.api.model.campaign.TrackingSetEnum;
import com.netshelter.ifbrands.api.model.campaign.TrackingTypeEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

/**
 * @author Dmitriy T
 */
public class TrackingServiceImplTest
    extends BaseServiceTest
{

    private final Random r = new Random();

    @Autowired
    private TrackingService service;

    @Test
    public void testTracking()
    {

        Tracking entity = service.create(ObjectTypeEnum.Campaign,
                                         123,
                                         TrackingSetEnum.PostClickTracking,
                                         TrackingTypeEnum.JavaScript,
                                         "test-" + r.nextDouble()
        );
        assertNotNull("Entity was not created", entity);

        service.delete( entity.getTrackingId() );
        System.out.println("Deleted entity: " + entity);

        List<Tracking> entityDoesNotExist = service.getByAny( entity.getTrackingId(), null, null, null, null );
        assertTrue("Entity was not deleted", entityDoesNotExist.size()==0);
    }
}
