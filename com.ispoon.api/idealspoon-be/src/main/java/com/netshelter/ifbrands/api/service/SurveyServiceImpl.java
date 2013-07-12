package com.netshelter.ifbrands.api.service;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.data.dao.SurveyAnswerDao;
import com.netshelter.ifbrands.data.dao.SurveyQuestionTypeAnswerDao;
import com.netshelter.ifbrands.data.dao.SurveyResponseDao;
import com.netshelter.ifbrands.data.dao.SurveyResponseLogDao;
import com.netshelter.ifbrands.data.entity.IfbSurveyAnswer;
import com.netshelter.ifbrands.data.entity.IfbSurveyQuestionTypeAnswer;
import com.netshelter.ifbrands.data.entity.IfbSurveyResponse;
import com.netshelter.ifbrands.data.entity.IfbSurveyResponseLog;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: Dmitriy T
 * Date: 2/19/13
 */
public class SurveyServiceImpl
    implements SurveyService
{

    public static final int DEFAULT_QUESTION_TYPE = 1;
    public static final String KEY_PREFIX = "survey.";


    @Autowired
    private SurveyResponseDao   surveyResponseDao;
    @Autowired
    private SurveyAnswerDao     surveyAnswerDao;
    @Autowired
    private SurveyResponseLogDao surveyResponseLogDao;
    @Autowired
    private SurveyQuestionTypeAnswerDao surveyQuestionTypeAnswerDao;

    protected transient Logger logger = LoggerFactory.getLogger();


    @Override
    public void submitSurveyAnswer(Integer questionTypeId, String campaignKey, String adKey, Integer storyId,
                                   Integer surveyAnswerId, String site,
                                   HttpServletRequest request) throws IllegalArgumentException
    {
        List<IfbSurveyQuestionTypeAnswer> possibleAnswers;
        if (questionTypeId != null)
        {
            possibleAnswers = surveyQuestionTypeAnswerDao.findByQuestionTypeId( questionTypeId );
        }
        else
        {
            possibleAnswers = surveyQuestionTypeAnswerDao.findByQuestionTypeId( DEFAULT_QUESTION_TYPE );
        }

        IfbSurveyAnswer answerFound = null;
        for (IfbSurveyQuestionTypeAnswer questionTypeAnswer: possibleAnswers)
        {
            if (questionTypeAnswer.getIfbSurveyAnswer().getSurveyAnswerId().equals(surveyAnswerId)) {
                answerFound = questionTypeAnswer.getIfbSurveyAnswer();
            }
        }

        if (answerFound != null)
        {

            // creating a log entry into IfbSurveyResponseLog table / entity
            IfbSurveyResponseLog log = new IfbSurveyResponseLog();
            log.setAdKey(adKey);
            log.setCampaignKey(campaignKey);
            log.setStoryId(storyId);
            log.setSite(site);
            log.setIfbSurveyAnswer(answerFound);
            log.setCreateTime( new Date() );
            if ( request != null )
            {
                // if the x-forwarded-for header is not there - use standard request.getRemoteAddr()
                String forwardedFor = request.getHeader("X-Forwarded-For");
                if ( StringUtils.isNotBlank(forwardedFor) )
                {
                    log.setRemoteIp(forwardedFor);
                    log.setRemoteUserAgent(request.getHeader("User-Agent"));
                }
                else
                {
                    log.setRemoteIp(request.getRemoteAddr());
                    log.setRemoteUserAgent(request.getHeader("User-Agent"));
                }
            }
            // make sure "site" is not one of the known test/demo values
            if (!TEST_SITE_VALUES.contains(site))
            {
                surveyResponseLogDao.save(log);
            }

            List<IfbSurveyResponse> list = surveyResponseDao.findByAny(campaignKey, adKey, storyId, surveyAnswerId, site);
            IfbSurveyResponse response = null;
            if ( list != null && list.size() == 1 )
            {
                response = list.get( 0 );
                response.setCountValue( response.getCountValue() + 1 );
                response.setLastUpdate( new Date() );
                // make sure "site" is not one of the known test/demo values
                if (!TEST_SITE_VALUES.contains(site))
                {
                    surveyResponseDao.update( response );
                }
            }
            else
            {
                response = new IfbSurveyResponse();
                response.setAdKey(adKey);
                response.setCampaignKey(campaignKey);
                response.setCountValue(1);
                response.setLastUpdate(new Date());
                response.setStoryId(storyId);
                response.setIfbSurveyAnswer(answerFound);
                response.setSite(site);
                response.setCreateTime( new Date() );
                // make sure "site" is not one of the known test/demo values
                if (!TEST_SITE_VALUES.contains(site))
                {
                    surveyResponseDao.save( response );
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("Provided answerId is not valid for specified question type");
        }
    }

    @Override
    public List<SurveyResult> getSurveyResults(String campaignKey, String adKey, Integer storyId, String site) {
        List<SurveyResult> result = null;


        CacheManager manager = CacheManager.getInstance();
        Cache cache = manager.getCache(SURVEY_CACHE);

        String key = KEY_PREFIX + ("_"+campaignKey+"_" + adKey + "_" + storyId).hashCode();

        // TODO: http://localhost:8080/api/survey/au4a094dq31a
        if (cache != null) {
            Element element = null;
            element = cache.get(key);
            if (element != null && element.getObjectValue() instanceof List) {
                logger.debug("Getting from cache, key:" + key);
                result = (List<SurveyResult>)element.getObjectValue();
            }
        }
        try
        {
            List<IfbSurveyResponse> list = surveyResponseDao.findByAny(campaignKey, adKey, storyId, null, site);
            if (list != null && list.size() > 0)
            {
                result = new ArrayList<SurveyResult>();
                for ( int i = 0; i < list.size(); i++ )
                {
                    IfbSurveyResponse response = list.get( i );
                    String displayName = "N/A";
                    if ( surveyAnswerDao.getById(response.getIfbSurveyAnswer().getSurveyAnswerId()) != null )
                    {
                        displayName = surveyAnswerDao.getById(response.getIfbSurveyAnswer().getSurveyAnswerId())
                                .getDisplayName();
                    }
                    SurveyResult sr = new SurveyResult(
                            response.getCampaignKey(),
                            response.getAdKey(),
                            response.getStoryId(),
                            response.getIfbSurveyAnswer().getSurveyAnswerId(),
                            displayName,
                            response.getCountValue(),
                            response.getSite()
                    );
                    result.add( sr );
                }
            }
            // update the cache
            if ( cache != null && result != null )
            {
                cache.put(new Element(key, result));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public List<SurveyServiceImpl.SurveyResult> getSurveyResults(
            String campaignKey, // required
            String adKey,       // optional
            Integer storyId,    // optional
            DateTime startTime, // required
            DateTime stopTime,   // required
            String  site        // optional
    )
    {
        List<SurveyServiceImpl.SurveyResult> result = null;
        List<IfbSurveyResponse> list = surveyResponseLogDao.findByAnyWithinDateRangeAggregated(campaignKey, adKey,
                storyId, null, startTime, stopTime, site);
        if (list != null)
        {
            result = new ArrayList<SurveyResult>();
            for ( int i = 0; i < list.size(); i++ )
            {
                IfbSurveyResponse response = list.get( i );
                String displayName = "N/A";
                if ( response.getIfbSurveyAnswer() != null &&
                        surveyAnswerDao.getById(response.getIfbSurveyAnswer().getSurveyAnswerId()) != null )
                {
                    displayName = surveyAnswerDao.getById(response.getIfbSurveyAnswer().getSurveyAnswerId()).getDisplayName();
                }
                SurveyResult sr = new SurveyResult(
                        response.getCampaignKey(),
                        response.getAdKey(),
                        response.getStoryId(),
                        response.getIfbSurveyAnswer().getSurveyAnswerId(),
                        displayName,
                        response.getCountValue(),
                        response.getSite()
                );
                result.add( sr );
            }
        }
        return result;
    }

    public List<IfbSurveyAnswer> getAnswerList( int questionTypeId )
    {
        List<IfbSurveyAnswer> result = new ArrayList<IfbSurveyAnswer>();
        List<IfbSurveyQuestionTypeAnswer> surveyQuestionTypeAnswers =
                surveyQuestionTypeAnswerDao.findByQuestionTypeId(questionTypeId);
        for (int i = 0; i < surveyQuestionTypeAnswers.size(); i++)
        {
            IfbSurveyQuestionTypeAnswer ifbSurveyQuestionTypeAnswer = surveyQuestionTypeAnswers.get(i);
            result.add(ifbSurveyQuestionTypeAnswer.getIfbSurveyAnswer());
        }
        return result;
    }

    public List<SurveyResult> mergeSurveyResults(List<SurveyResult> list)
    {
        List<SurveyResult> result = new ArrayList<SurveyResult>();
        if (list != null && list.size() > 0)
        {
            Map<String, SurveyResult> tmpMap = new HashMap<String, SurveyResult>();
            for (SurveyResult surveyResult : list)
            {
                String key =
                        surveyResult.getCampaignKey()+":"+
                                surveyResult.getAdKey()+":"+
                                surveyResult.getStoryId()+":"+
                                surveyResult.getSurveyAnswerId();
                if (tmpMap.containsKey(key))
                {
                    tmpMap.get(key).countValue += surveyResult.countValue;
                }
                else
                {
                    tmpMap.put(key, surveyResult);
                }
            }
            result.addAll(tmpMap.values());
        }
        return result;
    }


    public static class SurveyResult
    {
        private String campaignKey;
        private String adKey;
        private Integer storyId;
        private Integer surveyAnswerId;
        private long countValue;
        private String surveyAnswerName;
        private String site;

        public SurveyResult( String campaignKey, String adKey, Integer storyId, Integer surveyAnswerId,
                            String surveyAnswerName, long countValue, String site ) {
            this.campaignKey = campaignKey;
            this.adKey = adKey;
            this.storyId = storyId;
            this.surveyAnswerId = surveyAnswerId;
            this.surveyAnswerName = surveyAnswerName;
            this.countValue = countValue;
            this.site = site;
        }

        @Override
        public String toString() {
            return "SurveyResult{" +
                    "campaignKey=" + campaignKey +
                    ", adKey=" + adKey +
                    ", storyId=" + storyId +
                    ", surveyAnswerId=" + surveyAnswerId +
                    ", countValue=" + countValue +
                    ", site=" + site +
                    ", surveyAnswerName='" + surveyAnswerName + '\'' +
                    '}';
        }

        public String getCampaignKey() {
            return campaignKey;
        }

        public String getAdKey() {
            return adKey;
        }

        public Integer getStoryId() {
            return storyId;
        }

        public Integer getSurveyAnswerId() {
            return surveyAnswerId;
        }

        public long getCountValue() {
            return countValue;
        }

        public String getSurveyAnswerName() {
            return surveyAnswerName;
        }

        public String getSite()
        {
            return site;
        }
    }

}
