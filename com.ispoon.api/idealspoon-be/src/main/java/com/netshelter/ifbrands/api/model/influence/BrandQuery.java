package com.netshelter.ifbrands.api.model.influence;

import java.util.ArrayList;
import java.util.List;

public class BrandQuery
{
  private Integer brandId;
  private String query;

  public static List<BrandQuery> parse( String[] specs )
  {
    List<BrandQuery> bqList = new ArrayList<BrandQuery>();
    for( String s: specs ) bqList.add( BrandQuery.parse( s ));
    return bqList;
  }

  public static BrandQuery parse( String spec )
  {
    BrandQuery bq = new BrandQuery();
    // Break series into brandId:query at first ':'. Subsequent ':'s are part of query string
    String[] split = spec.split( ":", 2 );
    if( split.length != 2 ) throw new IllegalArgumentException( "Invalid series specifier '" + spec + ")" );
    if( split[0].length() > 0 ) bq.setBrandId( Integer.valueOf( split[0] ));
    if( split[1].length() > 0 ) bq.setQuery( split[1] );
    return bq;
  }

  public Integer getBrandId()
  {
    return brandId;
  }

  public void setBrandId( Integer brandId )
  {
    this.brandId = brandId;
  }

  public String getQuery()
  {
    return query;
  }

  public void setQuery( String query )
  {
    this.query = query;
  }

  public String getSpec()
  {
    StringBuilder sb = new StringBuilder();
    if( brandId != null ) sb.append( brandId.toString() );
    sb.append( ':' );
    if( query != null ) sb.append( query );
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return "BrandQuery [brandId=" + brandId + ", query=" + query + "]";
  }

}
