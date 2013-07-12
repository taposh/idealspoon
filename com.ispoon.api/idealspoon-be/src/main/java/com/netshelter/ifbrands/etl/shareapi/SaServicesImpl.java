package com.netshelter.ifbrands.etl.shareapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

public class SaServicesImpl implements SaServices {
  public static final String SHARE_API_STATS_SERVICE = "5";
  
  private String shareApiAuthority;
  
  private RestTemplate rest = new RestTemplate();
  
  public void setShareApiAuthority( String shareApiAuthority )
  {
    this.shareApiAuthority = shareApiAuthority;
  }

  @Override
  public String getShortUrl( String campaignName, String storyTitle, String storyUrl ) throws UnsupportedEncodingException 
  {  
    StringBuilder sb = new StringBuilder();
    sb.append( "http://" );
    sb.append( shareApiAuthority );
    sb.append( "/v=2;" );
    sb.append("pu=");
    sb.append( UriUtils.encodePathSegment( storyUrl, "UTF-8" ) );
    sb.append(";pt=");
    sb.append( UriUtils.encodePathSegment( storyTitle, "UTF-8" ) );
    sb.append(";cn=");
    sb.append( UriUtils.encodePathSegment( campaignName, "UTF-8" ) );
    sb.append(";rsu=1");
    
    return rest.getForObject( sb.toString(), String.class );
  }

  @Override
  public Integer getShortUrlStats( String shortUrlKey, DateTime start, DateTime stop ) throws JsonParseException, JsonMappingException, IOException 
  {    
    StringBuilder sb = new StringBuilder();
    sb.append( "http://" );
    sb.append( shareApiAuthority );
    sb.append( "/" );
    sb.append( "service=" );
    sb.append( SHARE_API_STATS_SERVICE );
    sb.append(";suk=");
    sb.append( shortUrlKey );

    if ( start != null && stop != null ) {
      DateTime startDateTime = start.toDateTime( DateTimeZone.forID( "UTC" ) );
      DateTime stopDateTime = stop.toDateTime( DateTimeZone.forID( "UTC" ) );
      
      sb.append( ";start_date=" );
      sb.append( startDateTime.toString("yyyy-MM-dd HH:mm:ss") );
      sb.append( ";stop_date=" );
      sb.append( stopDateTime.toString("yyyy-MM-dd HH:mm:ss") );
    }
 
    String response = rest.getForObject( sb.toString(), String.class );
    
    ObjectMapper mapper = new ObjectMapper();
    
    @SuppressWarnings ( "unchecked")
    Map<String, Object> clickData = mapper.readValue( response, Map.class );
    
    @SuppressWarnings ( "unchecked")
    Map<String, Object> clickDataMap = (Map<String, Object>)clickData.get( "click" );
    
    Integer totalClicks = Integer.parseInt( (String)clickDataMap.get( "total" ) );
    
    return totalClicks;
  }

}
