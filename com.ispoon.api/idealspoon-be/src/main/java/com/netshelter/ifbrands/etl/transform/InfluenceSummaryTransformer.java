package com.netshelter.ifbrands.etl.transform;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.influence.Influence;
import com.netshelter.ifbrands.api.model.influence.InfluenceSummary;
import com.netshelter.ifbrands.etl.analytics.model.NapMetrics;
import com.netshelter.ifbrands.etl.analytics.model.NapNode;

@Component
public class InfluenceSummaryTransformer
{
    public InfluenceSummary transform(NapNode node)
    {
        NapMetrics m = node.getMetricsTotalsRollup();
        Influence inf = new Influence();
        // If impressionServes > 0 assume Ad impressions, else try mediaViews as proxy for Story Impressions
        // Changes: Share Bar impressions being rolled up to Ad traffic driver
        // Ticket: http://jira.netshelter.net/browse/DEV-7088
        if (StringUtils.isNotBlank( node.getName() ) &&
                node.getName().startsWith("story.n2ih"))
        {
            inf.setImpressions(m.getMediaViewsStory());
        }
        else
        {
            inf.setImpressions(m.getImpressionServes());
        }

        inf.setInfluencers(m.getEngagementSharesStory());

        inf.setPeopleInfluenced(m.getEngagementConsumesStory() + m.getEngagementAmplifiesStory());

        // Breakdown of PeopleInfluenced
        inf.setConsumptions(m.getEngagementConsumesStory());
        inf.setAmplifiedConsumptions(m.getEngagementAmplifiesStory());

        inf.setStoryImpressions(m.getMediaViewsStory());
        inf.setStoryTimeSpent(m.getMediaTimeStory());

        InfluenceSummary is = new InfluenceSummary();
        is.setTotalInfluence(inf);
        return is;
    }

}
