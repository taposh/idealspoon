package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.IfbCampaign;
import com.netshelter.ifbrands.data.entity.IfbSurveyResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * User: Dmitriy T
 * Date: 2/19/13
 */
public class SurveyResponseDaoTest
    extends BaseDaoTest<IfbSurveyResponse>
{

    @Autowired
    SurveyResponseDao dao;

    @Autowired
    SurveyAnswerDao surveyAnswerDao;

    @Override
    protected BaseDao<IfbSurveyResponse> getDao()
    {
        return dao;
    }

    @Override
    protected IfbSurveyResponse makeEntity()
    {
        IfbSurveyResponse r = new IfbSurveyResponse();
        r.setSurveyResponseId( 1 );
        r.setAdKey("bla");
        r.setCampaignKey("bla");
        r.setStoryId(4);
        r.setIfbSurveyAnswer( surveyAnswerDao.getById( 1 ) );
        r.setCountValue(999);
        r.setLastUpdate( new Date() );
        r.setCreateTime( new Date() );
        return dao.save( r );
    }
}
