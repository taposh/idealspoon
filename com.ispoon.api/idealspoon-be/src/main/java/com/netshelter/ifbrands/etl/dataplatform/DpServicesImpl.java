package com.netshelter.ifbrands.etl.dataplatform;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceAndSentimentStoriesFacetedResponse;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.netshelter.ifbrands.api.util.RequestContextInterceptor;
import com.netshelter.ifbrands.etl.BaseRestServiceImpl;
import com.netshelter.ifbrands.etl.dataplatform.model.DpCategoryType;
import com.netshelter.ifbrands.etl.dataplatform.model.DpOrderType;
import com.netshelter.ifbrands.etl.dataplatform.model.DpRollupInterval;
import com.netshelter.ifbrands.etl.dataplatform.model.DpSentiment;
import com.netshelter.ifbrands.etl.dataplatform.model.GetAuthorsResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetCategoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceAndSentimentStoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceRollupBreakdownResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceRollupTotalResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceStoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetSitesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetStoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetStoryCategorySentimentsResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetStoryEngagementsResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetSuccessStatusWithStoryResponse;
import com.netshelter.ifbrands.util.MoreObjects;

public class DpServicesImpl extends BaseRestServiceImpl implements DpServices
{
    private static final String PATH_ROOT    = "/dpservices";
    private static final String PATH_API     = "/api/v1";

    private static final String PATH_HEALTH  = "/monitoring/health-check";
    private static final String PATH_STORIES = "/stories";
    private static final String PATH_SITES   = "/publishers/sites";
    private static final String PATH_BRANDS  = "/taxonomy/brands";
    private static final String PATH_AUTHORS = "/stories/authors";

    private static final String PATH_INFLUENCE_ROLLUP_TOTAL      = "/reports/influence";
    private static final String PATH_INFLUENCE_ROLLUP_BREAKDOWN  = "/reports/influence/breakdown";

    private static final String PATH_INFLUENCE_STORIES = "/reports/influence/stories";
    private static final String PATH_INFLUENCE_AND_SENTIMENTS_STORIES_PAID = "/reports/influenceandsentiment/stories/paid";
    private static final String PATH_INFLUENCE_AND_SENTIMENTS_STORIES_TOTAL = "/reports/influenceandsentiment/stories/total";
    private static final String PATH_INFLUENCE_AND_SENTIMENTS_STORIES_FACETED = "/search/stories";
    private static final String PATH_STORY_BRAND_SENTIMENTS = "/reports/sentiment/stories";
    private static final String PATH_STORY_ENGAGEMENT_METRICS = "/reports/engagements/stories";
    private static final String PATH_SET_STORY_THUMBNAIL = "/stories/media/image";
    private static final String PATH_BUILD_STORY_THUMBNAILS = "/stories/media/thumbnails";

    private static final String PARAM_DP_CONFIG_IN = "dpconfig";

    public static final String PARAM_CONSTANT_PUB_START_DATE = "2012-05-01";

    @Override
    protected String getPathRoot()
    {
        return PATH_ROOT;
    }

    @Override
    protected String getPathApi()
    {
        return PATH_API;
    }

    @Override
    protected String getUrl( String path, MultiValueMap<String,String> queryMap )
    {
        // Append .json to all requests to force 'application/json' return
        String url = super.getUrl( path +  ".json", queryMap );

        // Check if there is a DP-specific config request param
        String c = queryMap.isEmpty() ? "?" : "&";
        String[] dpConfig = RequestContextInterceptor.getParam( PARAM_DP_CONFIG_IN );
        if( dpConfig != null ) {
            // Decode the parameters
            for( String item: dpConfig ) {
                try {
                    // Append decoded config parameter
                    String decoded = URLDecoder.decode( item, "UTF-8" );
                    url += c + decoded;
                    c="&";
                } catch( UnsupportedEncodingException e ) {
                    logger.warn( "Could not decode dpconfig param: %s", item );
                }
            }
        }
        return url;
    }

    @Override
    public void setDpMasterMode ( boolean enable )
    {
        String[] dpConfig = RequestContextInterceptor.getParamMap().get( PARAM_DP_CONFIG_IN );

        if ( dpConfig == null ) {
            dpConfig = new String[0];
        }

        if ( enable == true ) {
            dpConfig = ObjectArrays.concat( dpConfig, "config=admin" );
        }
        else {
            dpConfig = (String[])ArrayUtils.removeElement( dpConfig, "config=admin" );
        }

        RequestContextInterceptor.getParamMap().put( PARAM_DP_CONFIG_IN, dpConfig );
    }

    /** Simple request with list of "ids". */
    private <T> T getResponseFromIds( Class<T> type, String path, Collection<Integer> ids )
    {
        return getForResponse( type, path, "ids", ids );
    }

    @Override
    public boolean isAlive()
    {
        boolean alive = false;

        // Call health API
        String url = String.format( "http://%s%s%s", getAuthority(), PATH_ROOT, PATH_HEALTH );

        ResponseEntity<String> response = restWithTimeout.getForEntity( url, String.class );

        if ( response.getStatusCode() == HttpStatus.OK ) {
            alive = true;
        }

        return alive;
    }

    @Override
    public GetSitesResponse getSites( Collection<Integer> ids )
    {
        return getResponseFromIds( GetSitesResponse.class, PATH_SITES, ids );
    }

    @Override
    public GetCategoriesResponse getBrands( Collection<Integer> ids )
    {
        return getResponseFromIds( GetCategoriesResponse.class, PATH_BRANDS, ids );
    }

    @Override
    public GetAuthorsResponse getAuthors( Collection<Integer> ids )
    {
        return getResponseFromIds( GetAuthorsResponse.class, PATH_AUTHORS, ids );
    }

    @Override
    public GetStoriesResponse getStories( Collection<Integer> ids )
    {
        return getResponseFromIds( GetStoriesResponse.class, PATH_STORIES, ids );
    }

    @Override
    public GetInfluenceRollupTotalResponse getInfluenceRollupTotal( Collection<Integer> categoryIds,
                                                                    Collection<Integer> authorIds,
                                                                    Collection<DpSentiment> sentiments,
                                                                    DateTime startTime,
                                                                    DateTime endTime, String query,
                                                                    DpRollupInterval interval )
    {
        categoryIds = MoreObjects.ifNull( categoryIds, Collections.<Integer>emptySet() );
        categoryIds = removeIfNegative(categoryIds);
        GetInfluenceRollupTotalResponse r;
        try
        {
            r =  getForResponse( GetInfluenceRollupTotalResponse.class,
                    PATH_INFLUENCE_ROLLUP_TOTAL+"/"+interval.toString().toLowerCase(),
                    "categoryIds", categoryIds,
                    "sentiments", sentiments,
                    "authorIds", authorIds,
                    "startTime", startTime,
                    "endTime", endTime,
                    "timezone", DpUtils.validateAndGetDateTimeZone( startTime, endTime ).getID(),
                    "pubStartDate", PARAM_CONSTANT_PUB_START_DATE,
                    "q", encode(query)                      );
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(getExceptionDescription(e), e);
        }
        return r;
    }

    @Override
    public GetInfluenceRollupBreakdownResponse getInfluenceRollupBreakdown( Collection<Integer> categoryIds,
                                                                            Collection<String> campaignKeys,
                                                                            Collection<Integer> authorIds,
                                                                            Collection<DpSentiment> sentiments,
                                                                            DateTime startTime,
                                                                            DateTime endTime, String query,
                                                                            DpRollupInterval interval,
                                                                            Boolean campaignOnly)
    {
        categoryIds  = MoreObjects.ifNull( categoryIds, Collections.<Integer>emptySet() );
        campaignKeys = MoreObjects.ifNull( campaignKeys, Collections.<String>emptySet() );
        categoryIds = removeIfNegative(categoryIds);
        List<Object> params = null;
        GetInfluenceRollupBreakdownResponse r = null;
        try
        {
            if (!campaignOnly)
            {
                params = Lists.newArrayList( "categoryIds", categoryIds,
                        "sentiments", sentiments,
                        "authorIds", authorIds,
                        "startTime", startTime,
                        "endTime", endTime,
                        "pubStartDate", PARAM_CONSTANT_PUB_START_DATE,
                        "q", encode(query)
                );
                addMany( params, "campaignKey", campaignKeys );
            }
            else
            {
                // This clause sets up the params for "Brandless" and "Keywordless" call to DP services
                params = Lists.newArrayList(  "campaignKey", campaignKeys,
                        "startTime", startTime,
                        "pubStartDate", PARAM_CONSTANT_PUB_START_DATE,
                        "endTime", endTime);
            }

            r = getForResponse( GetInfluenceRollupBreakdownResponse.class,
                    PATH_INFLUENCE_ROLLUP_BREAKDOWN+"/"+interval.toString().toLowerCase(),
                    params.toArray() );
            if( r.getOrganic() != null ) {
                r.getOrganic().setStartTime( startTime );
                r.getOrganic().setEndTime( endTime );
            }
            if( r.getPaid() != null ) {
                r.getPaid().setStartTime( startTime );
                r.getPaid().setEndTime( endTime );
            }
            if( r.getTotal() != null ) {
                r.getTotal().setStartTime( startTime );
                r.getTotal().setEndTime( endTime );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(getExceptionDescription(e), e);
        }
        return r;
    }

    /**
     * @deprecated This call (/reports/influence/stories) is no longer used
     */
    @Override
    public GetInfluenceStoriesResponse getInfluenceStories( Collection<Integer> categoryIds,
                                                            Collection<Integer> authorIds,
                                                            Collection<DpSentiment> sentiments,
                                                            DateTime startTime, DateTime endTime,
                                                            String query, DpOrderType orderType,
                                                            Integer limit )
    {
        try
        {
            categoryIds = MoreObjects.ifNull( categoryIds, Collections.<Integer>emptySet() );
            return getForResponse( GetInfluenceStoriesResponse.class,
                    PATH_INFLUENCE_STORIES,
                    "categoryIds", categoryIds,
                    "sentiments", sentiments,
                    "authorIds", authorIds,
                    "startTime", startTime,
                    "endTime", endTime,
                    "order", orderType.toString(),
                    "limit", limit,
                    "pubStartDate", PARAM_CONSTANT_PUB_START_DATE,
                    "q", encode(query)

            );
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(getExceptionDescription(e), e);
        }
    }

    @Override
    public GetInfluenceAndSentimentStoriesResponse getInfluenceAndSentimentStoriesTotal( Collection<Integer> categoryIds,
                                                                                         Collection<Integer> authorIds,
                                                                                         Collection<DpSentiment> sentiments,
                                                                                         DateTime startTime,
                                                                                         DateTime endTime,
                                                                                         String query, DpOrderType orderType,
                                                                                         Integer limit,
                                                                                         Collection<Integer> campaignIds,
                                                                                         DateTime pubStartDate)
    {
        categoryIds = MoreObjects.ifNull( categoryIds, Collections.<Integer>emptySet() );
        categoryIds = removeIfNegative(categoryIds);
        String pubStartDateValue = PARAM_CONSTANT_PUB_START_DATE;
        if (pubStartDate != null)
        {
            pubStartDateValue = pubStartDate.toString("yyyy-MM-dd");
        }
        try
        {
            if (campaignIds == null)
            {
                return getForResponse( GetInfluenceAndSentimentStoriesResponse.class,
                        PATH_INFLUENCE_AND_SENTIMENTS_STORIES_TOTAL,
                        "categoryIds", categoryIds,
                        "sentiments", sentiments,
                        "authorIds", authorIds,
                        "startTime", startTime,
                        "endTime", endTime,
                        "order", orderType.toString(),
                        "limit", limit,
                        "pubStartDate", pubStartDateValue,
                        "q", encode(query)
                );
            }
            else
            {
                campaignIds = MoreObjects.ifNull( campaignIds, Collections.<Integer>emptySet() );
                return getForResponse( GetInfluenceAndSentimentStoriesResponse.class,
                        PATH_INFLUENCE_AND_SENTIMENTS_STORIES_TOTAL,
                        "categoryIds", categoryIds,
                        "campaignIds", campaignIds,
                        "sentiments", sentiments,
                        "authorIds", authorIds,
                        "startTime", startTime,
                        "endTime", endTime,
                        "order", orderType.toString(),
                        "limit", limit,
                        "pubStartDate", pubStartDateValue,
                        "q", encode(query)
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(getExceptionDescription(e), e);
        }
    }

    @Override
    public GetInfluenceAndSentimentStoriesFacetedResponse getInfluenceAndSentimentStoriesFaceted(
            Collection<Integer> dpStoryIds,
            Boolean providedStoryIdsOnly,
            Collection<Integer> categoryIds,
            Collection<Integer> authorIds,
            Collection<DpSentiment> sentiments,
            DateTime startTime,
            DateTime endTime,
            String query, DpOrderType orderType,
            Integer limit,
            Integer offset,
            boolean ignoreDpCache,
            DateTime pubStartDate)
    {
        categoryIds = MoreObjects.ifNull( categoryIds, Collections.<Integer>emptySet() );
        categoryIds = removeIfNegative(categoryIds);
        String pubStartDateValue = PARAM_CONSTANT_PUB_START_DATE;
        if (pubStartDate != null)
        {
            pubStartDateValue = pubStartDate.toString("yyyy-MM-dd");
        }
        try
        {
            if (dpStoryIds != null && dpStoryIds.size() > 0)
            {
                if (providedStoryIdsOnly)
                {
                    // regular search + provided dpStoryIds
                    String dpStoryIdsCsv = StringUtils.join(dpStoryIds, ",");
                    return getForResponse_v2(GetInfluenceAndSentimentStoriesFacetedResponse.class,
                                             PATH_INFLUENCE_AND_SENTIMENTS_STORIES_FACETED,
//                                             "categoryIds", categoryIds,
                                             "sentiments", sentiments,
//                                             "authorIds", authorIds,
                                             "engTime.start", startTime,
                                             "engTime.end", endTime,
                                             "order", orderType.toString(),
                                             "limit", limit,
                                             "offset", offset,
                                             "config", (ignoreDpCache ? "ignoreCache" : ""),
                                             "dispositionFacet.limit", 20,
                                             "sentimentFacet.limit", 20,
                                             "authorFacet.limit", 20,
                                             "pubDate.start", pubStartDateValue,
                                             "storyIds", dpStoryIdsCsv,
//                                             "q", encode(query),
                                             "verbosity", "HIGH"
                    );
                }
                else
                {
                    // exclusively provided dpStoryIds
                    String dpStoryIdsCsv = StringUtils.join(dpStoryIds, ",");
                    return getForResponse_v2(GetInfluenceAndSentimentStoriesFacetedResponse.class,
                                             PATH_INFLUENCE_AND_SENTIMENTS_STORIES_FACETED,
                                             "categoryIds", categoryIds,
                                             "sentiments", sentiments,
                                             "authorIds", authorIds,
                                             "engTime.start", startTime,
                                             "engTime.end", endTime,
                                             "order", orderType.toString(),
                                             "limit", limit,
                                             "offset", offset,
                                             "config", (ignoreDpCache ? "ignoreCache" : ""),
                                             "dispositionFacet.limit", 20,
                                             "sentimentFacet.limit", 20,
                                             "authorFacet.limit", 20,
                                             "pubDate.start", pubStartDateValue,
                                             "elevate.storyIds", dpStoryIdsCsv,
                                             "elevate.excludeFilter", "categoryIds",
//                                             "elevate.excludeFilter", "authorIds",
                                             "elevate.excludeFilter", "sentiments",
                                             "elevate.excludeFilter", "pubDate",
                                             "q", encode(query),
                                             "verbosity", "HIGH"

                    );
                }
            }
            else
            {
                return getForResponse_v2(GetInfluenceAndSentimentStoriesFacetedResponse.class,
                                         PATH_INFLUENCE_AND_SENTIMENTS_STORIES_FACETED,
                                         "categoryIds", categoryIds,
                                         "sentiments", sentiments,
                                         "authorIds", authorIds,
                                         "engTime.start", startTime,
                                         "engTime.end", endTime,
                                         "order", orderType.toString(),
                                         "limit", limit,
                                         "offset", offset,
                                         "config", (ignoreDpCache ? "ignoreCache" : ""),
                                         "dispositionFacet.limit", 20,
                                         "sentimentFacet.limit", 20,
                                         "authorFacet.limit", 20,
                                         "pubDate.start", pubStartDateValue,
                                         "verbosity", "HIGH",
                                         "q", encode(query)
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(getExceptionDescription(e), e);
        }
    }

    @Override
    public GetInfluenceAndSentimentStoriesResponse getInfluenceAndSentimentStoriesPaid( Collection<Integer> categoryIds,
                                                                                        Collection<String> campaignKeys,
                                                                                        Collection<Integer> authorIds,
                                                                                        Collection<DpSentiment> sentiments,
                                                                                        DateTime startTime,
                                                                                        DateTime endTime,
                                                                                        String query, DpOrderType orderType,
                                                                                        Integer limit )
    {
        try
        {
            categoryIds = MoreObjects.ifNull( categoryIds, Collections.<Integer>emptySet() );
            campaignKeys = MoreObjects.ifNull( campaignKeys, Collections.<String>emptySet() );
            List<Object> parms = Lists.newArrayList( "categoryIds", categoryIds,
                    "sentiments", sentiments,
                    "authorIds", authorIds,
                    "startTime", startTime,
                    "endTime", endTime,
                    "order", orderType.toString(),
                    "limit", limit,
                    "pubStartDate", PARAM_CONSTANT_PUB_START_DATE,
                    "q", encode(query)
            );
            addMany( parms, "campaignKey", campaignKeys );
            return getForResponse( GetInfluenceAndSentimentStoriesResponse.class,
                                   PATH_INFLUENCE_AND_SENTIMENTS_STORIES_PAID,
                                   parms.toArray() );
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(getExceptionDescription(e), e);
        }
    }

    @Override
    public GetStoryCategorySentimentsResponse getStoryBrandSentiments( Collection<Integer> storyIds,
                                                                       Collection<Integer> categoryIds,
                                                                       Collection<DpCategoryType> categoryTypes )
    {
        storyIds = MoreObjects.ifNull( storyIds, Collections.<Integer>emptySet() );
        return getForResponse( GetStoryCategorySentimentsResponse.class, PATH_STORY_BRAND_SENTIMENTS,
                "ids", storyIds,
                "categoryIds", categoryIds,
                "categoryType", categoryTypes );
    }

    @Override
    public GetSuccessStatusWithStoryResponse setCustomStoryImage( Integer storyId, String imageUrl ) throws Exception
    {
        // Follows documentation for RestTemplate: needs MultiValueMap in POST body to properly parse key/values
        MultiValueMap<String,String> map = new LinkedMultiValueMap<String,String>();
        map.put( "imageUrl", Collections.singletonList( imageUrl ) );

        return postFormForResponse( GetSuccessStatusWithStoryResponse.class,
                PATH_SET_STORY_THUMBNAIL + "/" + storyId,
                map );
    }

    @Override
    public GetStoryEngagementsResponse getStoryEngagements( Collection<Integer> storyIds, DateTime startTime, DateTime stopTime )
    {
        storyIds = MoreObjects.ifNull( storyIds, Collections.<Integer>emptySet() );
        return getForResponse( GetStoryEngagementsResponse.class,
                PATH_STORY_ENGAGEMENT_METRICS,
                "ids", storyIds,
                "pubStartDate", PARAM_CONSTANT_PUB_START_DATE,
                "startTime", startTime,
                "stopTime", stopTime );
    }

    @Override
    public GetSuccessStatusWithStoryResponse buildAllStoryThumbnails( Integer dpStoryId ) throws Exception
    {
        return postDataForResponse( GetSuccessStatusWithStoryResponse.class,
                PATH_BUILD_STORY_THUMBNAILS + "/" + dpStoryId, null );
    }


    /**
     * Checks the collection if it is size=1 and contains a negative number returns with an empty collections
     * in any other case returns the original src collection
     *
     * @param src - Collection<Integer>
     * @return
     */
    private Collection<Integer> removeIfNegative(Collection<Integer> src)
    {
        Collection<Integer> result = null;
        if (src != null && src.size() == 1)
        {
            Integer value = (Integer)src.toArray()[0];
            if (value != null && value >= 0)
            {
                result = src;
            }
            else
            {
                result = new ArrayList<Integer>();
            }
        }
        else
        {
            result = src;
        }
        return result;
    }


    /**
     * This is a special url encoder to fix the issue with
     * keywords containing "&" (like AT&T)
     *
     * @param source
     * @return
     */
    private String encode(String source)
    {
        String result = source;
        try
        {
            if (StringUtils.isNotBlank(source))
            {
//                source = source.replace("&", "\u0026"); // unicode for "&"
                source = source.replace("&", "*");
                // result = URLEncoder.encode(source, "UTF-8");
                result = source;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;

    }

    private String getExceptionDescription(Throwable t)
    {
        String result = "Server internal error";
        if (t != null && StringUtils.isNotBlank(t.getLocalizedMessage())) {
            result = t.getLocalizedMessage();
            if (t.getLocalizedMessage().contains("SearchQuerySyntaxException"))
            {
                result = "SEARCH_SYNTAX_ERROR";
            }
            else
            if (t.getLocalizedMessage().contains("HttpClientErrorException"))
            {
                result = "SEARCH_SYNTAX_ERROR";
            }
        }
        return result;
    }

}
