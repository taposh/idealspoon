package com.netshelter.ifbrands.api.util;

import com.netshelter.ifbrands.api.model.influence.InfluenceBreakdown;
import com.netshelter.ifbrands.api.model.influence.InfluenceTrend;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.log4j.Logger;

/**
 * User: Dmitriy T
 * Date: 2/7/13
 */
public class LiftCalculatorUtils
{

    protected Logger logger = Logger.getLogger(LiftCalculatorUtils.class);

    public void calculateLift( InfluenceBreakdown<InfluenceTrend> trend )
    {
        InfluenceTrend paidTrend = trend.getPaid();
        InfluenceTrend organicTrend = trend.getOrganic();
        trend.getTotal();
        DescriptiveStatistics peopleInfluencedPercentLiftStats = new DescriptiveStatistics();
        DescriptiveStatistics influencersPercentLiftStats = new DescriptiveStatistics();
        for (int i = 0; i < paidTrend.getTrend().size(); i++)
        {
            InfluenceTrend.TrendDetail paidTrendDetail        = paidTrend.getTrend().get(i);
            InfluenceTrend.TrendDetail organicTrendDetail     = organicTrend.getTrend().get(i);
            Double dailyPeopleInfluencedPercentLift = ((double)paidTrendDetail.getInfluence().getPeopleInfluenced()) /
                    ((double)organicTrendDetail.getInfluence().getPeopleInfluenced() +
                            (double)paidTrendDetail.getInfluence().getPeopleInfluenced());
            Double dailyInfluencersPercentLift = ((double)paidTrendDetail.getInfluence().getInfluencers()) /
                    ((double)organicTrendDetail.getInfluence().getInfluencers() +
                            (double)paidTrendDetail.getInfluence().getInfluencers());

            if (Double.isNaN(dailyPeopleInfluencedPercentLift)) {
                dailyPeopleInfluencedPercentLift = 0.00000000d;
            }
            if (Double.isNaN(dailyInfluencersPercentLift)) {
                dailyInfluencersPercentLift = 0.00000000d;
            }

            peopleInfluencedPercentLiftStats.addValue(dailyPeopleInfluencedPercentLift);
            influencersPercentLiftStats.addValue(dailyInfluencersPercentLift);
        }

        logger.debug("Daily people influenced percent lift median: "
                + peopleInfluencedPercentLiftStats.getPercentile(50));
        logger.debug("Daily influencers percent lift median: "
                + influencersPercentLiftStats.getPercentile(50));

        trend.setLift(new InfluenceBreakdown.Lift(
                peopleInfluencedPercentLiftStats.getPercentile(50),
                influencersPercentLiftStats.getPercentile(50)
        ));
    }

}
