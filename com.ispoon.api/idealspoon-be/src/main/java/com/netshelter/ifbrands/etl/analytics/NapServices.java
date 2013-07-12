package com.netshelter.ifbrands.etl.analytics;

import org.joda.time.LocalDate;

import com.netshelter.ifbrands.etl.analytics.model.GetDimensionIdResponse;
import com.netshelter.ifbrands.etl.analytics.model.GetDimensionInfoResponse;
import com.netshelter.ifbrands.etl.analytics.model.GetMetricsResponse;
import com.netshelter.ifbrands.etl.analytics.model.NapDimension;
import com.netshelter.ifbrands.etl.analytics.model.NapSlice;

/**
 * Interface into the NetShelter Analytics Platform.
 * @author bgray
 */
public interface NapServices
{
  /** Get information on a given dimension. */
  public GetDimensionInfoResponse getDimensionInfo( NapDimension dimension, Integer id );
  /** Get the ID for a given (dimension,pair) if it exists. */
  public GetDimensionIdResponse getExistingDimensionId( NapDimension dimension, String name );
  /** Get total metrics for a given set of hypercube slices. */
  public GetMetricsResponse getTotalMetrics( LocalDate startDate, LocalDate stopDate, NapSlice... slices );
  /** Get rolled up total metrics for a given set of hypercube slices. */
  public GetMetricsResponse getRollupTotalExperienceMetrics(LocalDate startDate, LocalDate stopDate, NapSlice... slices);
  /** Get tree under the root node **/
  public GetMetricsResponse getShareBarMediaMetrics( LocalDate startDate, LocalDate stopDate, NapSlice... slices );
  public GetMetricsResponse getShortyBarMediaMetrics(LocalDate startDate, LocalDate stopDate, NapSlice... slices);
}
