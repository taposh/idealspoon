package com.netshelter.ifbrands.api.controller;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.campaign.AdNetwork;
import com.netshelter.ifbrands.api.model.campaign.AdSize;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author Dmitriy T
 */
public class AdNetworkControllerTest
    extends BaseControllerTest
{

    private final Random r = new Random();

    @Autowired
    AdNetworkController controller;

    @Test
    public void testAllCalls()
    {

        GenericPayload<Collection<AdNetwork>> result = controller.getList(null);
        List<AdNetwork> list = (List<AdNetwork>)result.get("adNetworks");

        int initialEntitiesSize = list.size();

        AdNetwork entity = controller.createAdNetwork("test-" + r.nextDouble());
        entity = controller.createAdNetwork("test-" + r.nextDouble());
        entity = controller.createAdNetwork("test-" + r.nextDouble());

        result = controller.getList(null);
        list = (List<AdNetwork>)result.get("adNetworks");
        assertEquals("Should be equal", (initialEntitiesSize + 3), list.size());
        for (AdNetwork adNetwork : list)
        {
            System.out.println( adNetwork );
            controller.deleteAdNetwork( adNetwork.getAdNetworkId() );
        }
    }

}