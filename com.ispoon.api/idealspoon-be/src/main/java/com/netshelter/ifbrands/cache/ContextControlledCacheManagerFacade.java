package com.netshelter.ifbrands.cache;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class ContextControlledCacheManagerFacade implements CacheManager
{
  private CacheManager delegate;
  private Collection<String> prefixParamNames;

  public CacheManager getDelegate()
  {
    return delegate;
  }

  public void setDelegate( CacheManager delegate )
  {
    this.delegate = delegate;
  }

  public void setPrefixParamNames( Collection<String> prefixParamNames )
  {
    this.prefixParamNames = prefixParamNames;
  }

  public Collection<String> getPrefixParamNames()
  {
    return prefixParamNames;
  }

  @Override
  public Cache getCache( String name )
  {
    return ContextControlledCacheFacade.wrap( delegate.getCache( name ), prefixParamNames );
  }

  @Override
  public Collection<String> getCacheNames()
  {
    return delegate.getCacheNames();
  }

}
