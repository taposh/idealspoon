package com.netshelter.ifbrands.etl.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.api.model.influence.InfluenceBreakdown;
import com.netshelter.ifbrands.api.model.influence.InfluenceTrend;
import com.netshelter.ifbrands.api.model.influence.InfluenceTrend.TrendDetail;
import com.netshelter.ifbrands.etl.dataplatform.model.DpInfluence;
import com.netshelter.ifbrands.etl.dataplatform.model.DpInfluenceRollup;
import com.netshelter.ifbrands.etl.dataplatform.model.DpRollupInterval;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceRollupBreakdownResponse;

@Component
public class InfluenceTrendTransformer
{
  private Logger logger = LoggerFactory.getLogger();

  @Autowired
  private InfluenceTransformer influenceTransformer;

  public InfluenceTrend transform( DpInfluenceRollup response )
  {
    // Catch unsupported interval types... no current requirements to support anything other than DAILY,INFINITE
    DpRollupInterval interval = response.getRollupInterval();
    switch( interval ) {
      case DAY:
      case INFINITE:
        break;
      default:
        throw new IllegalStateException( interval+" rollups not yet supported.  Need to adjust TrendDetail, etc." );
    }

    // Get timezone adjusted start/stop times from response
    DateTimeZone timezone = response.getTimezone();
    DateTime startTime = response.getStartTime().toDateTime( timezone );
    DateTime stopTime = response.getEndTime().toDateTime( timezone );

    // Set up result object
    InfluenceTrend trend = new InfluenceTrend();
    trend.setStartDate( startTime.toLocalDate() );
    trend.setStopDate( stopTime.toLocalDate() );
    trend.setTotalInfluence( influenceTransformer.transform( response.getTotalInfluence() ));

    // Only pull trend data for non-infinite intervals
    List<TrendDetail> details = new ArrayList<TrendDetail>();
    if( !interval.equals( DpRollupInterval.INFINITE )) {
      // Convert the RollupMap to a more friendly version based on request params
      // Lemma: The Rollup map will use DateTime ISO String values as keys
      // Lemma: Each key represents the beginning instant of the interval data in the requested timezone
      // Lemma: The Timezone of each key is not necessarily unspecified
      // Lemma: Each key should be exactly start + n*interval

      // Iterate on all Entries within Rollup, converting to proper timezone
      Map<DateTime,DpInfluence> dateMap = new TreeMap<DateTime,DpInfluence>();
      for( Map.Entry<String,DpInfluence> entry: response.getRollup().entrySet() ) {
        // Convert map DateTime string to timezone adjusted DateTime string and add to map
        DateTime tzAdjustedKey = new DateTime( entry.getKey() ).toDateTime( timezone );
        dateMap.put( tzAdjustedKey, entry.getValue() );
      }

      // Now that we have all known values with keys adjusted to proper timezone
      // iterate on all possible values and fill in zero objects
      DateTime key = startTime;
      while( !key.isAfter( stopTime )) {
        // Check reponse map for entry and remove; else use empty entry
        DpInfluence dpi = dateMap.remove( key );
        if( dpi == null ) dpi = new DpInfluence();
        // Add to output map
        TrendDetail detail = new TrendDetail();
        // wvg: Here is where DateTime support fails -- only LocalDate currently available
        // wvg: Could make TrendDetail generic to support any key type
        detail.setDate( key.toLocalDate() );
        detail.setInfluence( influenceTransformer.transform( dpi ));
        details.add( detail );
        key = interval.addInterval( key );
      }
      // Check for unexpected data in Rollup response and print warnings
      for( Map.Entry<DateTime, DpInfluence> entry: dateMap.entrySet() ) {
        logger.warn( "Unexpected Rollup entry for key %s", entry.getKey() );
      }
    }
    trend.setTrend( details );
    return trend;
  }

  public InfluenceBreakdown<InfluenceTrend> transform( GetInfluenceRollupBreakdownResponse response )
  {
    InfluenceBreakdown<InfluenceTrend> breakdown = new InfluenceBreakdown<InfluenceTrend>();
    breakdown.setPaid   ( transform( response.getPaid()   ));
    breakdown.setTotal  ( transform( response.getTotal()  ));
    breakdown.setOrganic( transform( response.getOrganic()));
    return breakdown;
  }
}
