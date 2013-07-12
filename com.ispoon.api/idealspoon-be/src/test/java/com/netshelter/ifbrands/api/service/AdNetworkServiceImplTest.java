package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.campaign.AdNetwork;
import com.netshelter.ifbrands.api.model.campaign.AdSize;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Dmitriy T
 */
public class AdNetworkServiceImplTest
    extends BaseServiceTest
{

    private final Random r = new Random();

    @Autowired
    private AdNetworkService service;

    @Test
    public void testAdNetwork()
    {

        AdNetwork entity = service.create("test-" + r.nextDouble());
        assertNotNull("Entity was not created", entity);

        List<AdNetwork> list = service.all( Collections.singletonList( entity.getAdNetworkId() ) );
        for ( AdNetwork adNetwork : list )
        {
            System.out.println("Fetched entity: " + adNetwork);
            service.update(adNetwork.getAdNetworkId(), "test-" + r.nextDouble());
            System.out.println("Updated entity: " + adNetwork);
            service.delete( adNetwork.getAdNetworkId() );
            System.out.println("Deleted entity: " + adNetwork);

            AdNetwork entityDoesNotExist = service.get( adNetwork.getAdNetworkId() );
            assertNull("Entity was not deleted", entityDoesNotExist);
        }
    }
}
