package com.netshelter.ifbrands.api.controller;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.user.UserInfo;
import com.netshelter.ifbrands.api.service.AdService;
import com.netshelter.ifbrands.api.service.CampaignService;
import com.netshelter.ifbrands.api.service.SurveyService;
import com.netshelter.ifbrands.api.service.SurveyServiceImpl;
import com.netshelter.ifbrands.data.dao.SurveyAnswerDao;
import com.netshelter.ifbrands.data.dao.SurveyResponseDao;
import com.netshelter.ifbrands.data.entity.IfbSurveyAnswer;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Dmitriy T
 * Date: 2/19/13
 */
@Controller("surveyController")
@RequestMapping("/survey")
public class SurveyController
    extends BaseController
{
    @Autowired
    SurveyService surveyService;
    @Autowired
    CampaignService campaignService;
    @Autowired
    AdService adService;

    @RequestMapping(value = "/{campaignKey}")
    @ResponseBody
    public GenericPayload<Collection<SurveyServiceImpl.SurveyResult>> getSurveyResults(
            @PathVariable("campaignKey")                            String campaignKey,
            @RequestParam(value = "adKey",      defaultValue = "")  String adKeyStr, // optional
            @RequestParam(value = "storyId",    defaultValue = "")  String storyIdStr // optional
    ) {

        logger.debug("/survey/"+campaignKey+" (adKey:"+adKeyStr+") (storyId:"+storyIdStr+")");

        // TODO: Add logging
        List<SurveyServiceImpl.SurveyResult> list = null;
        try
        {
            String adKey = null;
            if ( StringUtils.isNotBlank(adKeyStr) )
            {
                adKey = adKeyStr;
            }
            Integer storyId = null;
            if ( StringUtils.isNotBlank(storyIdStr) )
            {
                storyId = Integer.parseInt(storyIdStr);
            }
            list = surveyService.getSurveyResults(
                    campaignKey,
                    adKey,
                    storyId,
                    null);

            list = surveyService.mergeSurveyResults(list);

        }
        catch (DataAccessResourceFailureException daoEx)
        {
            daoEx.printStackTrace();
            return new GenericPayload(
                    Collections.singletonMap("error", "Database connection is not available or broken"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return new GenericPayload(
                    Collections.singletonMap("error", "Internal server error - " +
                            ex.getLocalizedMessage()));
        }
        return new GenericPayload<Collection<SurveyServiceImpl.SurveyResult>>("surveyResults", list);
    }

    @RequestMapping(value = "/{campaignKey}/{adKey}/{storyId}/{answerId}")
    @ResponseBody
    public GenericPayload<Collection<SurveyServiceImpl.SurveyResult>> submitAnswer(
            @PathVariable("campaignKey")            String campaignKey,
            @PathVariable("adKey")                  String adKey,
            @PathVariable("storyId")                String storyIdStr,
            @PathVariable("answerId")               String answerIdStr,
            @RequestParam(value = "site", defaultValue = SurveyService.ACTIVE_SITE_VALUE) String site,
            @RequestParam(value = "questiontypeid", required = false) Integer questionTypeId,
            HttpServletRequest request
    )
    {
        logger.debug("/survey/"+campaignKey+"/"+adKey+"/"+storyIdStr+"/"+answerIdStr + " (site:"+site+") (questionTypeId:"+questionTypeId+")");
        if (StringUtils.isNotBlank(campaignKey) &&
                StringUtils.isNotBlank(adKey) &&
                StringUtils.isNotBlank(storyIdStr) &&
                StringUtils.isNotBlank(site) &&
                StringUtils.isNotBlank(answerIdStr))
        {
            Integer storyId = Integer.parseInt(storyIdStr);
            Integer answerId = Integer.parseInt(answerIdStr);
            // need to validate - existence of campaign, and existence and inheritance of ads

            boolean errorHappened = false;

            try
            {
                Campaign campaign = null;
                List<Campaign> campaigns = (List<Campaign>)campaignService.getCampaigns( null,
                    null,
                    null,
                    Collections.singleton(campaignKey),
                    null,
                    null,
                    null,
                    null);

                if (campaigns != null && campaigns.size() > 0)
                {
                    campaign = campaigns.get(0);
                }

                if ( campaign == null )
                {
                    return new GenericPayload(Collections.singletonMap("error", "Campaign not found by id: " + campaignKey));
                }

                List<Ad> adList = (List<Ad>)adService.getAds(
                        null,
                        null,
                        null,
                        null,
                        Collections.singleton(campaign.getId())
                );
                boolean adFound = false;
                if ( adList != null && adList.size() > 0 )
                {
                    for ( Ad ad : adList )
                    {
                        if ( ad.getKey().equals( adKey ) )
                        {
                            adFound = true;
                        }
                    }
                    if ( !adFound )
                    {
                        return new GenericPayload(Collections.singletonMap("error", "Ad with id: " + adKey +
                                " does not belong to campaignKey: " + campaignKey));
                    }
                    try
                    {
                        surveyService.submitSurveyAnswer(
                                questionTypeId,
                                campaignKey,
                                adKey,
                                storyId,
                                answerId,
                                site,
                                request
                        );
                    }
                    catch (IllegalArgumentException e)
                    {
                        return new GenericPayload(Collections.singletonMap("error",
                                "Provided answerId is not valid for specified survey question type"));
                    }
                }
                else
                {
                    return new GenericPayload(Collections.singletonMap("error", "Campaign with id: " + campaignKey +
                            " does not have any ads associated with it" ) );
                }
            }
            catch (Exception e)
            {
                errorHappened = true;
                e.printStackTrace();
            }

            List<SurveyServiceImpl.SurveyResult> list = surveyService.getSurveyResults(
                    campaignKey,
                    adKey,
                    storyId,
                    null
            );

            list = surveyService.mergeSurveyResults(list);

            HashMap map = new HashMap();
            map.put("surveyResults", list);
            if (errorHappened)
            {
                map.put( "message", "Failure, results from cache" );
            }
            else
            {
                map.put( "message", "Success, response recorded" );
            }
            return new GenericPayload<Collection<SurveyServiceImpl.SurveyResult>>(map);

        }
        else
        {
            return new GenericPayload(Collections.singletonMap("error",
                    "One or more required parameters are empty or missing: campaignKey, adKey, storyId, or answerId"));
        }
    }

    @RequestMapping(value = "/answers/{questionTypeId}")
    @ResponseBody
    public GenericPayload<Collection<IfbSurveyAnswer>> getAnswerList(
            @PathVariable("questionTypeId")             String questionTypeId
    )
    {
        if ( StringUtils.isNotBlank( questionTypeId ) )
        {
            List<IfbSurveyAnswer> result = surveyService.getAnswerList( Integer.parseInt( questionTypeId ) );
            if (result.size() == 0)
            {
                return new GenericPayload(Collections.singletonMap("error",
                        "Question type: " + questionTypeId + " is not valid."));
            }
            return new GenericPayload<Collection<IfbSurveyAnswer>>("answers", result);
        }
        else
        {
            return new GenericPayload(Collections.singletonMap("error",
                    "QuestionTypeId parameter can not be empty"));
        }

    }

}
