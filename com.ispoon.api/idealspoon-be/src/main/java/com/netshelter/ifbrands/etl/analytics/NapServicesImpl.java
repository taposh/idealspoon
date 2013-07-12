package com.netshelter.ifbrands.etl.analytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.joda.time.LocalDate;
import org.springframework.aop.aspectj.SingletonAspectInstanceFactory;

import com.netshelter.ifbrands.etl.BaseRestServiceImpl;
import com.netshelter.ifbrands.etl.analytics.model.GetDimensionIdResponse;
import com.netshelter.ifbrands.etl.analytics.model.GetDimensionInfoResponse;
import com.netshelter.ifbrands.etl.analytics.model.GetMetricsResponse;
import com.netshelter.ifbrands.etl.analytics.model.NapDimension;
import com.netshelter.ifbrands.etl.analytics.model.NapDimensionInfo;
import com.netshelter.ifbrands.etl.analytics.model.NapNode;
import com.netshelter.ifbrands.etl.analytics.model.NapSlice;
import com.netshelter.ifbrands.util.MoreCollections;

/**
 * Implementation of NapServices.
 * @author bgray
 *
 */
public class NapServicesImpl extends BaseRestServiceImpl implements NapServices
{
  private static final String PATH_ROOT    = "/nap-clerk";
  private static final String PATH_API     = "/api/v1";

  private static final String PATH_ALIVE              = "/hyper/alive";
  private static final String PATH_DIMENSION_EXISTING = "/dimension/getExistingId";
  private static final String PATH_DIMENSION_INFO     = "/dimension/getInfo";
  private static final String PATH_HYPER_TOTALS       = "/hyper/total";
  private static final String PATH_HYPER_EXPERIENCES  = "/hyper/experience";

  @Override
  protected String getPathRoot()
  {
    return PATH_ROOT;
  }

  @Override
  protected String getPathApi()
  {
    return PATH_API;
  }

  @Override
  public boolean isAlive()
  {
    // Call the 'echo' API with a string and validate that string is returned
    String echo = UUID.randomUUID().toString();
    String url = String.format( "http://%s%s%s%s?echo=%s", getAuthority(), PATH_ROOT, PATH_API, PATH_ALIVE, echo );
    String r = restWithTimeout.getForObject( url, String.class );
    return r.equals( echo );
  }

  @Override
  public GetDimensionIdResponse getExistingDimensionId( NapDimension dimension, String name )
  {
    return getForResponse( GetDimensionIdResponse.class,
                           PATH_DIMENSION_EXISTING,
                           "dimension", dimension,
                           "name", name );
  }

  @Override
  public GetDimensionInfoResponse getDimensionInfo( NapDimension dimension, Integer id )
  {
    NapDimensionInfo[] infoList = getForResponse( NapDimensionInfo[].class,
                                                  PATH_DIMENSION_INFO,
                                                  "dimension", dimension,
                                                  "id", id );
    GetDimensionInfoResponse r = new GetDimensionInfoResponse();
    r.setDimensionInfos( Arrays.asList( infoList ));
    return r;
  }

  @Override
  public GetMetricsResponse getTotalMetrics( LocalDate startDate, LocalDate stopDate,
                                             NapSlice... slices )
  {
    // Build a path /path/dim(:id)/dim(:id)/... from the slices
    StringBuilder path = new StringBuilder();
    path.append( PATH_HYPER_TOTALS );
    for( NapSlice s: slices ) {
      path.append( '/' ).append( s.getHyperFilter() );
    }
    // Get root node of tree
    NapNode[] tree = getForResponse( NapNode[].class,
                                     path.toString(),
                                     "start", startDate,
                                     "stop", stopDate );
    // Put tree into response object
    GetMetricsResponse r = new GetMetricsResponse();
    r.setMetrics( Arrays.asList( tree ));
    return r;
  }

  @Override
  public GetMetricsResponse getRollupTotalExperienceMetrics(LocalDate startDate, LocalDate stopDate,
                                                            NapSlice... slices)
  {
    // Build a path /path/dim(:id)/dim(:id)/... from the slices
    StringBuilder path = new StringBuilder();
    path.append( PATH_HYPER_EXPERIENCES );
    for( NapSlice s: slices ) {
      path.append( '/' ).append( s.getHyperFilter() );
    }
    // Get root node of tree
    NapNode[] tree = getForResponse( NapNode[].class,
                                     path.toString(),
                                     "start", startDate,
                                     "stop", stopDate );
    // Put tree into response object
    GetMetricsResponse r = new GetMetricsResponse();
    r.setMetrics( Arrays.asList( tree ));
    return r;
  }
  
  @Override
  public GetMetricsResponse getShareBarMediaMetrics( LocalDate startDate, LocalDate stopDate,
                                                   NapSlice... slices )
  {
    // Build a path /path/dim(:id)/dim(:id)/... from the slices
    StringBuilder path = new StringBuilder();
    path.append( PATH_HYPER_TOTALS );
    for( NapSlice s: slices ) {
      path.append( '/' ).append( s.getHyperFilter() );
    }
    // Get root node of tree
    NapNode[] tree = getForResponse( NapNode[].class,
                                     path.toString(),
                                     "start", startDate,
                                     "stop", stopDate );

    if ( tree == null || tree.length == 0 ) {
      return null;
    }
    
    Collection<NapNode> napNodes = new ArrayList<NapNode>( Arrays.asList( tree ) );
    
    NapNode nn = napNodes.iterator().next();
    
    int depth = slices.length + 1;
    for( int i=1; i<=depth; i++ ) {
      if ( !nn.getName().contains( "share_bar" ) && nn.getChildren().iterator().hasNext() ) {
        nn = nn.getChildren().iterator().next();
      }
      else {
        break;
      }
    }

    // Put tree into response object
    GetMetricsResponse r = new GetMetricsResponse();
    r.setMetrics( MoreCollections.singletonNotNullList( nn ) );
    
    return r;
  }
  
  @Override
  public GetMetricsResponse getShortyBarMediaMetrics( LocalDate startDate, LocalDate stopDate,
                                                   NapSlice... slices )
  {
    // Build a path /path/dim(:id)/dim(:id)/... from the slices
    StringBuilder path = new StringBuilder();
    path.append( PATH_HYPER_TOTALS );
    for( NapSlice s: slices ) {
      path.append( '/' ).append( s.getHyperFilter() );
    }
    // Get root node of tree
    NapNode[] tree = getForResponse( NapNode[].class,
                                     path.toString(),
                                     "start", startDate,
                                     "stop", stopDate );

    if ( tree == null || tree.length == 0 ) {
      return null;
    }
    
    Collection<NapNode> napNodes = new ArrayList<NapNode>( Arrays.asList( tree ) );
    
    NapNode nn = napNodes.iterator().next();
    
    int depth = slices.length + 1;
    for( int i=1; i<=depth; i++ ) {
      if ( !nn.getName().contains( "shorty_bar" ) && nn.getChildren().iterator().hasNext() ) {
        nn = nn.getChildren().iterator().next();
      }
      else {
        break;
      }
    }

    // Put tree into response object
    GetMetricsResponse r = new GetMetricsResponse();
    r.setMetrics( MoreCollections.singletonNotNullList( nn ) );
    
    return r;
  }

}
