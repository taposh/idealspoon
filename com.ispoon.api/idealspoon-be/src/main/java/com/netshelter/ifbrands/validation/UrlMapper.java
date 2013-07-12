package com.netshelter.ifbrands.validation;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * Maps known exchange urls to updated urls
 *
 * Sample urls:
 *  http://bs.serving-sys.com/BurstingPipe/adServer.bs?cn=tf&c=20&mc=click&pli=4959208&PluID=0&ord=[timestamp]
 *  http://clk.atdmt.com/MRT/go/437742103/direct;at.CU_SEP_365_COM_NetShelterIPStories_SxSV1R1;ct.1/01/?href=http://sfgate.com
 *  http://altfarm.mediaplex.com/ad/ck/12308-164706-21584-3?mpt=[CACHEBUSTER]?mpro=http://sfgate.com
 *  http://ad.doubleclick.net/clk;266374867;93396176;g;pc=[TPAS_ID]?http://www.uscellular.com/coverage-map/index.html?utm_campaign=Q1+2013+Consumer+Brand&utm_medium=Display&utm_source=NetShelter&utm_content=CON_NetShelter_InPoweredStoriesTopicSpecific_ALL_1x1_SITE
 *  http://servedby.flashtalking.com/click/3/24551;521232;50126;211;0/?ft_width=1&ft_height=1&url=3387222
 *
 * @author Dmitriy T
 */
public class UrlMapper
{

    public static final String [] EXCHANGE_PATTERNS = {
            "(?i).*clk.atdmt.com.*",                        // 0:atlas
            "(?i).*altfarm.mediaplex.com/ad/ck.*",          // 1:mediaplex
            "(?i).*ad.doubleclick.net/clk.*",               // 2:doubleclickclk
            "(?i).*ad.doubleclick.net/ddm/trackclk.*",      // 3:doubleclickcddm
            "(?i).*flashtalking.com\\/click.+?&url=.*",     // 4:flashtalking
            "(?i).*bs.serving-sys.com.+?&mc=click.*"        // 5:mediamind
    };

    public static final String [] TIMESTAMP_TOKENS = {
            "[timestamp]",
            "[CACHEBUSTER]",
            "%%CACHEBUSTER%%",
            "[random]",
            "{random}",
            "{{cacheBust}}",
    };

    private String replaceTimestampToken(String url)
    {
        for (int patternIndex = 0; patternIndex < TIMESTAMP_TOKENS.length; patternIndex++)
        {
            url = url.replace(TIMESTAMP_TOKENS[patternIndex], "ts_placeholder");
        }
        return url; // return original url even if not found
    }

    public String map(String url)
    {
        if (StringUtils.isNotBlank(url))
        {
            for (int patternIndex = 0; patternIndex < EXCHANGE_PATTERNS.length; patternIndex++)
            {
                if ( Pattern.matches(EXCHANGE_PATTERNS[patternIndex], url) )
                {
                    if (patternIndex == 0) // atlas
                    {
                        int i = url.indexOf('?');
                        if (i > 0) {
                            url = url.substring(0, i);
                        }
                        return replaceTimestampToken(url + "?href=");
                    }
                    else
                    if (patternIndex == 1) // mediaplex
                    {
                        int i = url.indexOf('?');
                        if (i > 0) {
                            url = url.substring(0, i);
                        }
                        return replaceTimestampToken(url + "?mpt=ts_placeholder&mpro=");
                    }
                    else
                    if (patternIndex == 2) // doubleclickclk
                    {
                        int i = url.indexOf('?');
                        if (i > 0) {
                            url = url.substring(0, i);
                        }
                        return replaceTimestampToken(url + "?chain_unesc_url");
                    }
                    else
                    if (patternIndex == 3) // doubleclickcddm
                    {
                        int i = url.indexOf('?');
                        if (i > 0) {
                            url = url.substring(0, i);
                        }
                        return replaceTimestampToken(url + "?chain_unesc_url");
                    }
                    else
                    if (patternIndex == 4) // flashtalking
                    {
                        return replaceTimestampToken(url.replaceAll("(?i)&url=.*", "&url="));
                    }
                    else
                    if (patternIndex == 5) // mediamind
                    {
                        url = url.replace("&rtu=.*", "");
                        return replaceTimestampToken(url + "&rtu=");
                    }
                }
            }
        }
        return null;
    }

    public boolean isKnownPattern(String url)
    {
        if (StringUtils.isNotBlank(url))
        {
            for (int patternIndex = 0; patternIndex < EXCHANGE_PATTERNS.length; patternIndex++)
            {
                if ( Pattern.matches(EXCHANGE_PATTERNS[patternIndex], url) )
                {
                    return true;
                }
            }
        }
        return false;
    }


}
