package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.IfbSurveyQuestionTypeAnswer;
import com.netshelter.ifbrands.data.entity.IfbSurveyResponse;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * User: Dmitriy T
 * Date: 2/19/13
 */
public class SurveyQuestionTypeAnswerDao
        extends BaseDao<IfbSurveyQuestionTypeAnswer>
{

    @Override
    protected Class<IfbSurveyQuestionTypeAnswer> getEntityClass()
    {
        return IfbSurveyQuestionTypeAnswer.class;
    }

    @Override
    protected Serializable getIdentifier(IfbSurveyQuestionTypeAnswer entity)
    {
        return entity.getSurveyQuestionTypeAnswerId();
    }

    @Override
    protected void updateIdentifier(IfbSurveyQuestionTypeAnswer entity, Serializable id)
    {
        entity.setSurveyQuestionTypeAnswerId( (Integer) id );
    }

    @Override
    public String asString(IfbSurveyQuestionTypeAnswer entity) {
        return entity.toString();
    }

    @Override
    public boolean theSame(IfbSurveyQuestionTypeAnswer a, IfbSurveyQuestionTypeAnswer b) {
        return a.getSurveyQuestionTypeAnswerId().equals(b.getSurveyQuestionTypeAnswerId());
    }

    public List<IfbSurveyQuestionTypeAnswer> findByQuestionTypeId(Integer questionTypeId)
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( IfbSurveyQuestionTypeAnswer.class );
        // lazy fetch workaround
        criteria.setFetchMode("ifbSurveyQuestionTypeAnswer", FetchMode.EAGER);
        criteria.setFetchMode("ifbSurveyQuestionType", FetchMode.EAGER);
        criteria.setFetchMode("ifbSurveyAnswer", FetchMode.EAGER);
        addPropertyRestriction(criteria, "ifbSurveyQuestionType.surveyQuestionTypeId", questionTypeId);
        return findByCriteria( criteria );
    }
}