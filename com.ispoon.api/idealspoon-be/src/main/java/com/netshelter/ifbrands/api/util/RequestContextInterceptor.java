package com.netshelter.ifbrands.api.util;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;

public class RequestContextInterceptor extends HandlerInterceptorAdapter
{
  public static String PARAM_IFB_CONFIG_IN = "ifbconfig";

  public static final String REQUEST_PARAM_MAP = RequestContextInterceptor.class.getName()+".paramMap";
  protected static transient Logger logger= LoggerFactory.getLogger();

  @Override
  public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler )
  {
    @SuppressWarnings ( "unchecked")
    Map<?,?> paramMap = new Hashtable<String,String[]>( request.getParameterMap() );
    logger.debug( "Attaching %d parameters to thread Context", paramMap.size() );
    Context.get().setProperty( REQUEST_PARAM_MAP, paramMap );
    return true;
  }

  public static String[] getIfbConfig()
  {
    return getParam( PARAM_IFB_CONFIG_IN );
  }

  public static boolean doesIfbConfigContain( String item )
  {
    return doesConfigContain( PARAM_IFB_CONFIG_IN, item );
  }

  public static Map<String,String[]> getParamMap()
  {
    @SuppressWarnings( "unchecked" )
    Map<String,String[]> paramMap = (Map<String,String[]>)Context.get().getProperty( REQUEST_PARAM_MAP, Map.class );
    if( paramMap == null ) {
      paramMap = Collections.<String,String[]>emptyMap();
    }
    return paramMap;
  }

  public static String[] getParam( String name )
  {
    String[] params = getParamMap().get( name );
    if( params == null ) {
      params = new String[0];
    }
    return params;
  }

  public static boolean doesConfigContain( String name, String item )
  {
    String[] params = getParam( name );
    for( String p: params ) {
      String[] split = p.split( "," );
      for( String s: split ) {
        if( item.equalsIgnoreCase( s )) return true;
      }
    }
    return false;
  }
}
