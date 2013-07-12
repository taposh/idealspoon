package com.netshelter.ifbrands.api.service;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;

/**
 * User: Dmitriy T
 * Date: 1/29/13
 */
public class MathTest
        extends BaseServiceTest
{

    @Test
    /**
     * Making sure the median function ( stats.getPercentile(50) ) works as expected
     */
    public void testMedian()
    {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        stats.addValue(2);
        stats.addValue(3);
        stats.addValue(876);
        assertEquals(3, stats.getPercentile(50), 1);
        stats.addValue(4);
        assertEquals(3.5, stats.getPercentile(50), 1);
    }

}
