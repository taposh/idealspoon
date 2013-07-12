package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.data.entity.IfbSurveyAnswer;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * User: Dmitriy T
 * Date: 2/19/13
 */
public interface SurveyService
{

    public static final List<String> TEST_SITE_VALUES = Arrays.asList(
            new String[] {"demo", "direct", "test"}
    );

    public static final String ACTIVE_SITE_VALUE = "prd";

    public static final String SURVEY_CACHE = "ifb.mvc.survey";

    /**
     * Submits the answer for specific survey question returns back true
     * if everything is correct and false is there was a failure during submission.
     * Validates the answer by provided questionTypeId
     *
     * @param questionTypeId
     * @param campaignKey
     * @param adKey
     * @param storyId
     * @param surveyAnswerId
     * @return - boolean (true when submit was successful)
     * @throws IllegalArgumentException - if the surveyAnswerId is not one of the allowed for specific survey question type
     */
    public void submitSurveyAnswer(
            Integer questionTypeId, // required
            String campaignKey,     // required
            String adKey,           // required
            Integer storyId,        // required
            Integer surveyAnswerId, // required
            String site,            // required
            HttpServletRequest request
    ) throws IllegalArgumentException;

    /**
     * Returns a list of survey result objects based on campaignId and optional adId and storyId.
     * @param campaignKey
     * @param adKey
     * @param storyId
     * @return - List of SurveyResult objects per campaign, ad (optional), story (optional)
     */
    public List<SurveyServiceImpl.SurveyResult> getSurveyResults(
            String campaignKey, // required
            String adKey,       // optional
            Integer storyId,    // optional
            String site
    );

    /**
     * Returns a list of survey result objects based on campaignId and optional adId and storyId.
     * based on a specified date range
     * @param campaignKey
     * @param adKey
     * @param storyId
     * @return - List of SurveyResult objects per campaign, ad (optional), story (optional)
     */
    public List<SurveyServiceImpl.SurveyResult> getSurveyResults(
            String campaignKey, // required
            String adKey,       // optional
            Integer storyId,     // optional
            DateTime startTime,     // required
            DateTime stopTime,    // required
            String site
    );

    /**
     * Returns a list of allowed answers based on provided questionTypeId (IfbSurveyQuestionType:id)
     * @param questionTypeId
     * @return
     */
    public List<IfbSurveyAnswer> getAnswerList(int questionTypeId);

    /**
     * Merges survey results by grouping by campaignKey+adKey+storyId+surveyAnswerId without site.
     * Aggregated and adding count values for each group
     * TODO: This method needs to change - to use "GROUP BY" (hibernate projections) rather than data merging
     *
     * @param list
     * @return
     */
    public List<SurveyServiceImpl.SurveyResult> mergeSurveyResults(List<SurveyServiceImpl.SurveyResult> list);

}
