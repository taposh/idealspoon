package com.netshelter.ifbrands.api.service;

import java.util.Collection;
import java.util.Collections;

import com.netshelter.ifbrands.data.dao.CampaignDaoTest;
import com.netshelter.ifbrands.data.entity.IfbCampaign;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.AdStatus;
import com.netshelter.ifbrands.api.model.campaign.AdType;
import com.netshelter.ifbrands.data.dao.FeedDaoTest;
import com.netshelter.ifbrands.data.entity.IfbFeed;

public class AdServiceImplTest
    extends BaseServiceTest
{
    @Autowired
    private AdService service;
    @Autowired
    private FeedDaoTest feedDaoTest;
    @Autowired
    private CampaignDaoTest campaignDaoTest;


    @Test
    public void testAd()
    {
        // Create
        IfbFeed f = feedDaoTest.makeEntity();
        IfbCampaign c = campaignDaoTest.makeEntity();
        AdType t = service.createAdType("foo");
        AdStatus s = service.createAdStatus("bar");
        Ad a0 = service.createAd("key", "name", t.getId(), s.getId(), f.getFeedId(), c.getCampaignId(),
                0, 0, 1,null,null, 101, null);
        assertNotNull(a0);
        // Get
        Ad a1 = service.getAd(a0.getId());
        assertEquals(a0.getKey(), a1.getKey());

        // Make sure the product details are persisted and fetched correctly...
        assertEquals("PRODUCT_NAME_HERE", a1.getProductName());
        assertEquals("PRODUCT_URL_HERE", a1.getProductDestination());

        // Get Collection by ID
        Collection<Ad> a2 = service.getAds(Collections.singleton(a0.getId()), null, null, null, null);
        assertEquals(1, a2.size());
        assertEquals(a0.getKey(), a2.iterator().next().getKey());
        // Get Collection by Type
        a2 = service.getAds(null, null, Collections.singleton(t.getId()), null, null);
        assertEquals(1, a2.size());
        assertEquals(a0.getKey(), a2.iterator().next().getKey());
        // Get Collection by Status
        a2 = service.getAds(null, null, null, Collections.singleton(s.getId()), null);
        assertEquals(1, a2.size());
        assertEquals(a0.getKey(), a2.iterator().next().getKey());
        // Get Collection by Campaign
        a2 = service.getAds(null, null, null, null, Collections.singleton(c.getCampaignId()));
        assertEquals(1, a2.size());
        assertEquals(a0.getKey(), a2.iterator().next().getKey());
        // Get Collection by ALL
        a2 = service.getAds(Collections.singleton(a0.getId()), Collections.singleton(a0.getKey()),
                Collections.singleton(t.getId()),
                Collections.singleton(s.getId()),
                Collections.singleton(c.getCampaignId()));
        assertEquals(1, a2.size());
        assertEquals(a0.getKey(), a2.iterator().next().getKey());
        // Update
        Ad a4 = service.updateAd(a0.getId(), "newName", null, null, 0, null, 1,null,null, 101, null);
        Ad a5 = service.getAd(a0.getId());
        assertEquals("newName", a4.getName());
        assertEquals("newName", a5.getName());
        // Delete
        boolean b = service.deleteAd(a0.getId());
        assertTrue(b);
        Ad a6 = service.getAd(a0.getId());
        assertNull(a6);
    }

    @Test
    public void testAdType()
    {
        // Create
        AdType t0 = service.createAdType("type");
        assertNotNull(t0);
        // Get
        AdType t1 = service.getAdType(t0.getId());
        assertEquals(t0.getName(), t1.getName());
        // Get Collection
        Collection<AdType> t2 = service.getAdTypes(Collections.singleton(t0.getId()));
        assertEquals(1, t2.size());
        assertEquals(t0.getName(), t2.iterator().next().getName());
        // Delete
        boolean b = service.deleteAdType(t0.getId());
        assertTrue(b);
        AdType t3 = service.getAdType(t0.getId());
        assertNull(t3);
    }

    @Test
    public void testAdStatus()
    {
        // Create
        AdStatus s0 = service.createAdStatus("type");
        assertNotNull(s0);
        // Get
        AdStatus s1 = service.getAdStatus(s0.getId());
        assertEquals(s0.getName(), s1.getName());
        // Get Collection
        Collection<AdStatus> s2 = service.getAdStatuses(Collections.singleton(s0.getId()));
        assertEquals(1, s2.size());
        assertEquals(s0.getName(), s2.iterator().next().getName());
        // Delete
        boolean b = service.deleteAdStatus(s0.getId());
        assertTrue(b);
        AdStatus s3 = service.getAdStatus(s0.getId());
        assertNull(s3);
    }


}
