package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.IfbSurveyAnswer;
import com.netshelter.ifbrands.data.entity.IfbSurveyResponse;
import com.netshelter.ifbrands.data.entity.IfbSurveyResponseLog;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Dmitriy T
 * Date: 2/19/13
 */
public class SurveyResponseLogDao
        extends BaseDao<IfbSurveyResponseLog>
{

    @Autowired
    SurveyAnswerDao surveyAnswerDao = null;

    @Override
    protected Class<IfbSurveyResponseLog> getEntityClass()
    {
        return IfbSurveyResponseLog.class;
    }

    @Override
    protected Serializable getIdentifier(IfbSurveyResponseLog entity)
    {
        return entity.getSurveyResponseLogId();
    }

    @Override
    protected void updateIdentifier(IfbSurveyResponseLog entity, Serializable id)
    {
        entity.setSurveyResponseLogId( (Integer) id );
    }

    @Override
    public String asString(IfbSurveyResponseLog entity) {
        return entity.toString();
    }

    @Override
    public boolean theSame(IfbSurveyResponseLog a, IfbSurveyResponseLog b) {
        return a.getSurveyResponseLogId().equals(b.getSurveyResponseLogId());
    }

    /**
     *
     * Returns a list of IfbSurveyResponseLog objects based on all optional parameters
     *
     * @param campaignKey - optional
     * @param adKey - optional
     * @param storyId - optional
     * @param surveyAnswerId - optional
     * @param startTime - optional ( for "Greater than or equals to" restriction )
     * @param stopTime - optional ( for "Less than" restriction )
     * @return
     */
    public List<IfbSurveyResponseLog> findByAny(
            String campaignKey,
            String adKey,
            Integer storyId,
            Integer surveyAnswerId,
            DateTime startTime,
            DateTime stopTime
    )
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( IfbSurveyResponseLog.class );
        addPropertyRestriction( criteria, "campaignKey", campaignKey );
        addPropertyRestriction( criteria, "adKey", adKey );
        addPropertyRestriction( criteria, "storyId", storyId );
        addPropertyRestriction( criteria, "surveyAnswerId", surveyAnswerId );

        if (startTime != null)
        {
            criteria.add( Restrictions.ge("createTime", startTime.toDate()) );
        }
        if (stopTime != null)
        {
            criteria.add( Restrictions.lt("createTime", stopTime.toDate()) );
        }
        return findByCriteria( criteria );
    }

    /**
     * Returns an aggregated list of IfbSurveyResponse based on the following rules:
     *
     * Use ProjectionList (group by element of hibernate) which is used to create a GROUP BY sql clause:
     *      SELECT surveyAnswerId, storyId, adKey, campaignKey, count(*)
     *      FROM IfbSurveyResponseLog WHERE (....)
     *      GROUP BY surveyAnswerId, adKey, campaignKey, storyId (optional)
     *
     * @param campaignKey - required
     * @param adKey - required
     * @param storyId - optional
     * @param surveyAnswerId  - optional used for criteria only
     * @param startTime - optional
     * @param stopTime - optional
     * @return
     */
    @Transactional
    public List<IfbSurveyResponse> findByAnyWithinDateRangeAggregated(
            String campaignKey,
            String adKey,
            Integer storyId, // optional
            Integer surveyAnswerId,
            DateTime startTime,
            DateTime stopTime,
            String site
    )
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( IfbSurveyResponseLog.class );
        addPropertyRestriction( criteria, "campaignKey", campaignKey );
        addPropertyRestriction( criteria, "adKey", adKey );
        addPropertyRestriction( criteria, "storyId", storyId );
        addPropertyRestriction( criteria, "site", site );
        addPropertyRestriction( criteria, "surveyAnswerId", surveyAnswerId );

        if (startTime != null)
        {
            criteria.add( Restrictions.ge("createTime", startTime.toDate()) );
        }
        if (stopTime != null)
        {
            criteria.add( Restrictions.lt("createTime", stopTime.toDate()) );
        }

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.groupProperty("site"));
        projectionList.add(Projections.groupProperty("ifbSurveyAnswer.surveyAnswerId"));
        projectionList.add(Projections.groupProperty("adKey"));
        projectionList.add(Projections.groupProperty("campaignKey"));
        projectionList.add(Projections.rowCount());
        if (storyId != null)
        {
            projectionList.add(Projections.groupProperty("storyId"));
        }

        criteria.setProjection( projectionList );
        criteria.setFetchMode("ifbSurveyAnswer", FetchMode.EAGER);
        List list = findByCriteria( criteria );
        List<IfbSurveyResponse> result = new ArrayList<IfbSurveyResponse>();
        for (int i = 0; i < list.size(); i++) {
            Object [] o = (Object[])list.get(i);
            // making sure there is correct number of elements in the array returned
            if ( o != null && o.length > 3 )
            {
                IfbSurveyResponse surveyResponse = new IfbSurveyResponse();
//                surveyResponse.setSurveyAnswerId(((Integer) o[0]));
                surveyResponse.setSite((String)o[0]);
                surveyResponse.setIfbSurveyAnswer(surveyAnswerDao.getById(((Integer)  o[1])));
                surveyResponse.setAdKey         (((String)  o[2]));
                surveyResponse.setCampaignKey   (((String)  o[3]));
                surveyResponse.setCountValue    (((Long)    o[4]).intValue());

                // if there is a story id available (if we grouped by it) get it
                if ( o.length > 5 )
                {
                    surveyResponse.setStoryId   (((Integer) o[5]));
                }
                result.add(surveyResponse);
            }
        }
        return result;
    }
}
