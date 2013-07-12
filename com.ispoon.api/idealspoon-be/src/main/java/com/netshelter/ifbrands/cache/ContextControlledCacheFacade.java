package com.netshelter.ifbrands.cache;

import java.util.Collection;

import org.springframework.cache.Cache;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.api.util.RequestContextInterceptor;

public class ContextControlledCacheFacade implements Cache
{
  private transient Logger logger = LoggerFactory.getLogger();

  public static String CFG_IGNORE_CACHE = "ignoreCache";

  private Cache delegate;
  private Collection<String> prefixParamNames;

  public static Cache wrap( Cache delegate, Collection<String> prefixParamNames )
  {
    return new ContextControlledCacheFacade( delegate, prefixParamNames );
  }

  protected ContextControlledCacheFacade( Cache delegate, Collection<String> prefixParamNames )
  {
    this.delegate = delegate;
    this.prefixParamNames = prefixParamNames;
    logger.info( "Initialized cache facade (%s,%s)", delegate.getName(), prefixParamNames.toString() );
  }

  @Override
  public String getName()
  {
    return delegate.getName();
  }

  @Override
  public Object getNativeCache()
  {
    return delegate.getNativeCache();
  }

  @Override
  public ValueWrapper get( Object key )
  {
    return ignoreCache() ? null : delegate.get( getRealKey( key ) );
  }

  @Override
  public void put( Object key, Object value )
  {
    if( !ignoreCache() ) delegate.put( getRealKey( key ), value );
  }

  @Override
  public void evict( Object key )
  {
    if( !ignoreCache() ) delegate.evict( getRealKey( key ));
  }

  @Override
  public void clear()
  {
    if( !ignoreCache() ) delegate.clear();
  }

  protected String getRealKey( Object key )
  {
    StringBuilder sb = new StringBuilder();
    for( String s: prefixParamNames ) {
      sb.append( '/' );
      String[] params = RequestContextInterceptor.getParam( s );
      for( String p:params ) {
        sb.append( String.valueOf( p )).append( ':' );
      }
    }
    sb.append( String.valueOf( key ));
    String real = sb.toString();
    logger.debug( "RealKey: %s", real );
    return real;
  }

  protected boolean ignoreCache()
  {
    // Check context for ignore config
    return RequestContextInterceptor.doesIfbConfigContain( CFG_IGNORE_CACHE );
  }

}
