package com.netshelter.ifbrands.validation;

import com.netshelter.ifbrands.data.dao.DaoTest;
import org.junit.Test;
import org.springframework.stereotype.Component;

/**
 * @author Dmitriy T
 */
@Component
public class UrlMapperTest
    extends DaoTest
{

    @Test
    public void testUrlMapper() throws Exception
    {
        UrlMapper mapper = new UrlMapper();
        String url = "http://bs.serving-sys.com/BurstingPipe/adServer.bs?cn=tf&c=20&mc=click&pli=4959208&PluID=0&ord=[timestamp]";
        String newUrl = mapper.map(url);
        System.out.println("Original url: " + url);
        System.out.println("Updated  url: " + newUrl);
        assertNotNull("Resulting url should not be null, something wrong with the UrlMapper", newUrl);


        mapper = new UrlMapper();
        url = "http://clk.atdmt.com/MRT/go/437742103/direct;at.CU_SEP_365_COM_NetShelterIPStories_SxSV1R1;ct.1/01/?href=http://sfgate.com";
        newUrl = mapper.map(url);
        System.out.println("Original url: " + url);
        System.out.println("Updated  url: " + newUrl);
        assertNotNull("Resulting url should not be null, something wrong with the UrlMapper", newUrl);

        mapper = new UrlMapper();
        url = "http://altfarm.mediaplex.com/ad/ck/12308-164706-21584-3?mpt=[CACHEBUSTER]?mpro=http://sfgate.com";
        newUrl = mapper.map(url);
        System.out.println("Original url: " + url);
        System.out.println("Updated  url: " + newUrl);
        assertNotNull("Resulting url should not be null, something wrong with the UrlMapper", newUrl);

        mapper = new UrlMapper();
        url = "http://ad.doubleclick.net/clk;266374867;93396176;g;pc=[TPAS_ID]?http://www.uscellular.com/coverage-map/index.html?utm_campaign=Q1+2013+Consumer+Brand&utm_medium=Display&utm_source=NetShelter&utm_content=CON_NetShelter_InPoweredStoriesTopicSpecific_ALL_1x1_SITE";
        newUrl = mapper.map(url);
        System.out.println("Original url: " + url);
        System.out.println("Updated  url: " + newUrl);
        assertNotNull("Resulting url should not be null, something wrong with the UrlMapper", newUrl);

        mapper = new UrlMapper();
        url = "http://servedby.flashtalking.com/click/3/24551;521232;50126;211;0/?ft_width=1&ft_height=1&url=3387222";
        newUrl = mapper.map(url);
        System.out.println("Original url: " + url);
        System.out.println("Updated  url: " + newUrl);
        assertNotNull("Resulting url should not be null, something wrong with the UrlMapper", newUrl);

        mapper = new UrlMapper();
        url = "http://ad.doubleclick.net/ddm/trackclk/N2998.447081.NETSHELTER.COM/B7674693.4;dc_trk_aid=272713013;dc_trk_cid=54477258;ord=[timestamp]?";
        newUrl = mapper.map(url);
        System.out.println("Original url: " + url);
        System.out.println("Updated  url: " + newUrl);
        assertNotNull("Resulting url should not be null, something wrong with the UrlMapper", newUrl);
    }
}
