package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.campaign.AdSize;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

/**
 * User: Dmitriy T
 */
public class AdSizeServiceImplTest
    extends BaseServiceTest
{

    private final Random r = new Random();

    @Autowired
    private AdSizeService service;

    @Test
    public void testAdSize()
    {

        AdSize entity = service.create("test-" + r.nextDouble(), 100, 100);
        assertNotNull("Entity was not created", entity);

        List<AdSize> list = service.all( null );
        for ( AdSize adSize : list )
        {
            System.out.println("Fetched entity: " + adSize);
            service.update(adSize.getAdSizeId(), null, 0, 0);
            System.out.println("Updated entity: " + adSize);
            service.delete( adSize.getAdSizeId() );
            System.out.println("Deleted entity: " + adSize);

            AdSize entityDoesNotExist = service.get( adSize.getAdSizeId() );
            assertNull("Entity was not deleted", entityDoesNotExist);
        }
    }
}
