package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.data.dao.SurveyAnswerDao;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * User: Dmitriy T
 * Date: 2/19/13
 */
public class SurveyServiceImplTest
    extends BaseServiceTest
{

    @Autowired
    private SurveyService surveyService;

    @Test
    public void testSurveyServiceSubmit() throws Exception
    {

        DateTime now = new DateTime();
        List<SurveyServiceImpl.SurveyResult> result = surveyService.getSurveyResults(
                "at99eicf0ukk", // campaignKey
                "at99enlkn79m", // adKey
                null,//3367203, // storyId
                now.minusDays(365),
                now,
                null
        );
        for (int i = 0; i < result.size(); i++) {
            SurveyServiceImpl.SurveyResult surveyResult = result.get(i);
            System.out.println(surveyResult);
        }

//        surveyService.submitSurveyAnswer(1, 2, 3, 1, null);
//        surveyService.submitSurveyAnswer(1, 2, 3, 1, null);
//        surveyService.submitSurveyAnswer(1, 2, 3, 1, null);
//        surveyService.submitSurveyAnswer(1, 2, 3, 1, null);
//        surveyService.submitSurveyAnswer(1, 2, 3, 1, null);
//        surveyService.submitSurveyAnswer(1, 2, 3, 2, null);
//        surveyService.submitSurveyAnswer(1, 2, 3, 2, null);
//        List<SurveyServiceImpl.SurveyResult> resultList = surveyService.getSurveyResults(1, 2, 3);
//        for (int i = 0; i < resultList.size(); i++) {
//            SurveyServiceImpl.SurveyResult surveyResult = resultList.get(i);
//            System.out.println(surveyResult);
//        }
    }

}
