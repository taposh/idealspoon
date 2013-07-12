package com.netshelter.ifbrands.api.controller;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.campaign.AdSize;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author Dmitriy T
 */
public class AdSizeControllerTest
    extends BaseControllerTest
{

    private final Random r = new Random();

    @Autowired
    AdSizeController controller;

    @Test
    public void testAllCalls()
    {

        GenericPayload<Collection<AdSize>> result = controller.getList(null);
        List<AdSize> list = (List<AdSize>)result.get("adSizes");

        int initialEntitiesSize = list.size();

        AdSize entity = controller.createAdSize("test-" + r.nextDouble(), 100, 100);
        entity = controller.createAdSize("test-" + r.nextDouble(), 101, 101);
        entity = controller.createAdSize("test-" + r.nextDouble(), 102, 102);

        result = controller.getList(null);
        list = (List<AdSize>)result.get("adSizes");
        assertEquals("Should be equal", (initialEntitiesSize + 3), list.size());
        for (AdSize adSize : list)
        {
            System.out.println( adSize );
            controller.deleteAdSize( adSize.getAdSizeId() );
        }
    }

}
