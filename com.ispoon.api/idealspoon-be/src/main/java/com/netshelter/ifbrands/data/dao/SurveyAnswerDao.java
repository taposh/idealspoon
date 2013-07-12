package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.IfbSurveyAnswer;
import com.netshelter.ifbrands.data.entity.IfbSurveyQuestionTypeAnswer;
import com.netshelter.ifbrands.data.entity.IfbSurveyResponse;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

/**
 * User: Dmitriy T
 * Date: 2/19/13
 */
public class SurveyAnswerDao
        extends BaseDao<IfbSurveyAnswer>
{

    @Override
    protected Class<IfbSurveyAnswer> getEntityClass()
    {
        return IfbSurveyAnswer.class;
    }

    @Override
    protected Serializable getIdentifier(IfbSurveyAnswer entity)
    {
        return entity.getSurveyAnswerId();
    }

    @Override
    protected void updateIdentifier(IfbSurveyAnswer entity, Serializable id)
    {
        entity.setSurveyAnswerId( (Integer) id );
    }

    @Override
    public String asString(IfbSurveyAnswer entity)
    {
        return entity.toString();
    }

    /**
     * This call is an equivalent to SQL:
     *
     *    SELECT
     *      a.*
     *    FROM
     *      IfbSurveyAnswer a,
     *      IfbSurveyQuestionTypeAnswer qta
     *    WHERE
     *      a.id = qta.answerId
     *      AND
     *      qta.questionTypeId = {questionTypeId}
     *
     *
     * @param questionTypeId
     * @return
     */
    public List<IfbSurveyAnswer> getAnswerList(int questionTypeId)
    {
        DetachedCriteria ownerCriteria = DetachedCriteria.forClass( IfbSurveyQuestionTypeAnswer.class );
        ownerCriteria.setProjection( Property.forName( "ifbSurveyAnswer.answerId" ) );
        ownerCriteria.add( Restrictions.eq( "ifbSurveyQuestionType.questionTypeId", questionTypeId ) );
        Criteria criteria = getSession().createCriteria( IfbSurveyAnswer.class );
        criteria.add( Property.forName( "ifbSurveyAnswer.surveyAnswerId" ).in( ownerCriteria ) );
        return criteria.list();
    }

    @Override
    public boolean theSame(IfbSurveyAnswer a, IfbSurveyAnswer b)
    {
        return a.getSurveyAnswerId().equals(b.getSurveyAnswerId());
    }
}