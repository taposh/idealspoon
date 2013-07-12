package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.IfbCampaign;
import com.netshelter.ifbrands.data.entity.IfbSurveyResponse;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * User: Dmitriy T
 * Date: 2/19/13
 */
public class SurveyResponseDao
    extends BaseDao<IfbSurveyResponse>
{

    @Override
    protected Class<IfbSurveyResponse> getEntityClass()
    {
        return IfbSurveyResponse.class;
    }

    @Override
    protected Serializable getIdentifier(IfbSurveyResponse entity)
    {
        return entity.getSurveyResponseId();
    }

    @Override
    protected void updateIdentifier(IfbSurveyResponse entity, Serializable id)
    {
        entity.setSurveyResponseId( (Integer) id );
    }

    @Override
    public String asString(IfbSurveyResponse entity) {
        return entity.toString();
    }

    @Override
    public boolean theSame(IfbSurveyResponse a, IfbSurveyResponse b) {
        return a.getSurveyResponseId().equals(b.getSurveyResponseId());
    }

    public List<IfbSurveyResponse> findByAny(
            String campaignKey,
            String adKey,
            Integer storyId,
            Integer surveyAnswerId,
            String site
    )
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( IfbSurveyResponse.class );
        addPropertyRestriction( criteria, "campaignKey", campaignKey );
        addPropertyRestriction( criteria, "adKey", adKey );
        addPropertyRestriction( criteria, "storyId", storyId );
        addPropertyRestriction( criteria, "ifbSurveyAnswer.surveyAnswerId", surveyAnswerId );
        addPropertyRestriction( criteria, "site", site );
        // lazy fetch workaround
        criteria.setFetchMode("ifbSurveyAnswer", FetchMode.EAGER);

        return findByCriteria( criteria );
    }
}
