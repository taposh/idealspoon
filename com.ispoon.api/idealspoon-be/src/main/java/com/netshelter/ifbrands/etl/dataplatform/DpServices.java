package com.netshelter.ifbrands.etl.dataplatform;

import java.util.Collection;

import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceAndSentimentStoriesFacetedResponse;
import org.joda.time.DateTime;

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

/**
 * Interface into the DpServices service.
 *
 * @author bgray
 * @author ekrevets
 */
public interface DpServices
{
    public GetSitesResponse getSites(Collection<Integer> ids);

    public GetCategoriesResponse getBrands(Collection<Integer> ids);

    public GetAuthorsResponse getAuthors(Collection<Integer> ids);

    public GetStoriesResponse getStories(Collection<Integer> ids);

    /**
     * @deprecated This call (/reports/influence/stories) is no longer used
     */
    public GetInfluenceStoriesResponse getInfluenceStories(Collection<Integer> categoryIds,
                                                           Collection<Integer> authorIds,
                                                           Collection<DpSentiment> sentiments,
                                                           DateTime startTime, DateTime endTime,
                                                           String query, DpOrderType orderType,
                                                           Integer limit);

    public GetInfluenceAndSentimentStoriesResponse getInfluenceAndSentimentStoriesTotal(
            Collection<Integer> categoryIds,
            Collection<Integer> authorIds,
            Collection<DpSentiment> sentiments,
            DateTime startTime, DateTime endTime,
            String query, DpOrderType orderType,
            Integer limit,
            Collection<Integer> campaignIds,
            DateTime pubStartDate);

    public GetInfluenceAndSentimentStoriesResponse getInfluenceAndSentimentStoriesPaid(
            Collection<Integer> categoryIds,
            Collection<String> campaignKeys,
            Collection<Integer> authorIds,
            Collection<DpSentiment> sentiments,
            DateTime startTime, DateTime endTime,
            String query, DpOrderType orderType,
            Integer limit);

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
            DateTime pubStartDate);

    public GetInfluenceRollupTotalResponse getInfluenceRollupTotal(
            Collection<Integer> categoryIds,
            Collection<Integer> authorIds,
            Collection<DpSentiment> sentiments,
            DateTime startTime, DateTime endTime,
            String query, DpRollupInterval interval);

    public GetInfluenceRollupBreakdownResponse getInfluenceRollupBreakdown(
            Collection<Integer> categoryIds,
            Collection<String> campaignKeys,
            Collection<Integer> authorIds,
            Collection<DpSentiment> sentiments,
            DateTime startTime, DateTime endTime,
            String query, DpRollupInterval interval,
            Boolean campaignOnly);


    public GetStoryCategorySentimentsResponse getStoryBrandSentiments(
            Collection<Integer> storyIds,
            Collection<Integer> categoryIds,
            Collection<DpCategoryType> categoryTypes);

    public GetSuccessStatusWithStoryResponse setCustomStoryImage(Integer storyId, String imageUrl)
            throws
            Exception;

    public GetStoryEngagementsResponse getStoryEngagements(Collection<Integer> storyIds,
                                                           DateTime startTime,
                                                           DateTime stopTime);

    public GetSuccessStatusWithStoryResponse buildAllStoryThumbnails(Integer dpStoryId)
            throws
            Exception;

    public void setDpMasterMode(boolean enable);

}
