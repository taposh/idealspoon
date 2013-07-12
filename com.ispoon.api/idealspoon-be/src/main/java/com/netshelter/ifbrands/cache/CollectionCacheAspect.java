package com.netshelter.ifbrands.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;

/**
 * This class provides AOP support for mapping individual elements for idempotent statless Collectio-to-Collection mapping methods.
 * @author bgray
 *
 */
@Aspect
public class CollectionCacheAspect
{
  private Logger logger = LoggerFactory.getLogger();
  // Object used as placeholder when weaving new and cached results
  private static final Object HOLDER = new Object();
  // Object used as part of the key when caching the 'null' object.
  private static final Serializable NULL_KEY = new Long( Long.MIN_VALUE );

  // CacheManager, configured elsewhere
  private CacheManager cacheManager;

  @Required
  public void setCacheManager( CacheManager cacheManager )
  {
    this.cacheManager = cacheManager;
  }

  /**
   * Advice for any "Collection foo( Collection bar )" method with proper annotation.
   * This will select the appropriate caching method based upon run-time scenario.
   * @param pjp context object
   * @param config config object from annotation
   * @param arg argument from method
   * @return result object
   * @throws Throwable
   */
  @Around("@annotation(config) && args(arg) ")
  public Object doCollectionCache( ProceedingJoinPoint pjp, CollectionCache config, Object arg ) throws Throwable
  {
    // Get annotation configuration
    @SuppressWarnings( "unchecked" )
    Class<?> implClass = (Class<Collection<Object>>)config.implClass();
    String cacheName = config.cacheName();
    String keyPrefix = config.keyPrefix();
    String keyField = config.keyField();
    logger.debug( "Intercepted CollectionCacheable [%s:%s:%s] on method %s", cacheName, keyPrefix, keyField, pjp.getSignature().getName() );

    // Get Cache
    Cache cache = cacheManager.getCache( cacheName );
    if( cache == null ) {
      throw new AopInvocationException( "CollectionCachePut:  Cache '"+ cacheName +"' does not seem to exist?" );
    }

    // Call appropriate implementation based on run-time scenario
    Object result;
    if( CollectionCache.IMPLICIT.equals( keyField )) {
      if( List.class.isInstance( arg ) && List.class.isAssignableFrom( implClass )) {
        // IMPLICIT mode (special handling for List->List)
        Class<List<Object>> listClass = cast( implClass );
        result = cacheOrdered( pjp, cache, keyPrefix, listClass, (List<?>) arg );
      } else {
        // Normal single-item cache where the arg is the key
        result = cacheSingle( pjp, cache, keyPrefix, keyField, arg );
      }
    } else if( Collection.class.isInstance( arg )) {
      // UNORDERED mode (uses explicit field from result objects)
      Class<Collection<Object>> collClass = cast( implClass );
      result = cacheUnordered( pjp, cache, keyPrefix, keyField, collClass, (Collection<?>)arg );
    } else {
      // SINGLE mode
      result = cacheSingle( pjp, cache, keyPrefix, keyField, arg );
    }
    return result;
  }

  /** Perform caching on a single object.  In this case, the argument itself is the key.  */
  private Object cacheSingle( ProceedingJoinPoint pjp, Cache cache, String keyPrefix, String keyField, Object input ) throws Throwable
  {
    // Determine key
    Object value;
    Object suffix = ( input == null ) ? NULL_KEY : input;
    Serializable key = generateKey( keyPrefix, suffix );
    // Check cache
    ValueWrapper wrapper = cache.get( key );
    // Return cached, or fetch actual value
    if( wrapper != null ) {
      logger.debug( "..Found single element in cache" );
      value = wrapper.get();
    } else {
      logger.debug( "..Requesting single element from target method" );
      value = pjp.proceed( new Object[] { input } );
      // Cache fetched value if not null
      if( value != null ) {
        cache.put( key, value );
      }
    }
    return value;
  }

  /** Perform caching on an ordered List.  Key/Value pairing is done via implicit ordering of the Lists. */
  private List<?> cacheOrdered( ProceedingJoinPoint pjp, Cache cache, String keyPrefix,
                                      Class<List<Object>> implClass, List<?> input ) throws Throwable
  {
    // Holder for intermediary results
    List<Object> hits = new ArrayList<Object>( input.size() );
    // Holder for our misses, which we'll pass on to the original target method
    List<Object> misses = implClass.newInstance();
    for( int i=0; i<input.size(); i++ ) {
      // Search cache for each element; nulls always miss
      ValueWrapper wrapper = null;
      Object in = input.get( i );
      if( in != null ) {
        // Check cache for this object
        Serializable key = generateKey( keyPrefix, in );
        wrapper = cache.get( key );
      }
      if( wrapper == null ) {
        // If element is not found, put HOLDER Object and load the 'misses' list
        hits.add( HOLDER );
        misses.add( in );
      } else {
        // If element is found, then add cached value to intermediary results
        hits.add( wrapper.get() );
      }
    }
    logger.debug( "..Found %d of %d elements", input.size() - misses.size(), input.size() );

    // Pass our cache misses to original target
    List<Object> results = Collections.<Object>emptyList();
    if( misses.size() > 0 ) {
      results = cast( (List<?>)pjp.proceed( new Object[] { misses } ));
    }

    if( results.size() != misses.size() ) {
      // If our result size does not match input size, we cannot cache these new values
      // as we do not know the associated key.  Just merge the lists and return.
      logger.warn( "Non 1:1 mapping target method response detected. Cannot cache results." );
      for( Object h: hits ) {
        if( h != HOLDER ) {
          results.add( h );
        }
      }
      return results;

    } else {
      // We'll reuse this list for our output
      misses.clear();
      // Iterate intermediary results
      Iterator<?> iter = results.iterator();
      for( int i=0; i<hits.size(); i++ ) {
        Object h = hits.get( i );
        if( h == HOLDER ) {
          if( iter.hasNext() ) {
            // Each place-holder will have its actual value in the results list
            // at the same location (ie. Nth HOLDER is in results[N]
            Object value = iter.next();
            misses.add( value );
            // Cache new non-null values
            if( input.get( i ) != null ) {
              Serializable key=generateKey( keyPrefix, input.get( i ));
              cache.put( key, value );
            }
          }
        } else {
          // This was a cache hit earlier so just use it
          misses.add( h );
        }
      }
    }
    return misses;
  }

  /** Perform caching on unordered collection.  Key values are pulled from an explicit field in the result object. */
  private Collection<?> cacheUnordered( ProceedingJoinPoint pjp, Cache cache, String keyPrefix, String keyField,
                                        Class<Collection<Object>> implClass, Collection<?> input ) throws Throwable
  {
    // Holder for intermediary results
    Collection<Object> hits = new ArrayList<Object>( input.size() );
    // Holder for our misses, which we'll pass on to the original target
    Collection<Object> misses = implClass.newInstance();
    for( Object in: input ) {
      // Search cache for each element; nulls always miss
      ValueWrapper wrapper = null;
      // Put found value in "hits", else put missed key in "misses"
      if( in != null ) {
        Serializable key = generateKey( keyPrefix, in );
        wrapper = cache.get( key );
        if( wrapper == null ) {
          misses.add( in );
        } else {
          hits.add( wrapper.get() );
        }
      } else {
        misses.add( in );
      }
    }
    logger.debug( "..Found %d of %d elements", hits.size(), input.size() );

    // Pass our cache misses to original target
    Collection<Object> results = Collections.<Object>emptyList();
    if( misses.size() > 0 ) {
      results = cast( (List<?>)pjp.proceed( new Object[] { misses } ));
    }

    // Cache results
    for( Object value: results ) {
      // Pull key from explicit field
      Object suffix = PropertyAccessorFactory.forBeanPropertyAccess( value ).getPropertyValue( keyField );
      if( suffix != null ) {
        Serializable key=generateKey( keyPrefix, suffix );
        cache.put( key, value );
      }
      // Merge new values into result collection
      hits.add( value );
    }
    return hits;
  }

  @SuppressWarnings( "unchecked" )
  public static <T> List<T> cast(List<?> p)
  {
    return (List<T>) p;
  }

  @SuppressWarnings( "unchecked" )
  public static <T> Class<T> cast(Class<?> p)
  {
    return (Class<T>) p;
  }

  /** Advice to evict all items from the cache.  This separate PointCut is required to advise
   * methods with no arguments, but in such a case the annotation must specify "removeAll=true"
   * or the operation of this Advice is ambiguous.*/
  @Before("@annotation(config)" )
  public void doCollectionEvict( CollectionEvict config ) throws Throwable
  {
    if( !config.removeAll() ) {
      logger.debug( "Intercepted CollectionEvict [%s] with no keys and (removeAll == false)?  Returning.",
                    config.cacheName() );
      return;  // Nothind to do?
    }
    doCollectionEvict( config, null );
  }

  /** Advice to evict items from the cache. The appropriate eviction strategy is chosen based
   * upon run-time scenario.  */
  @Before("@annotation(config) && args(arg) ")
  public void doCollectionEvict( CollectionEvict config, Object arg ) throws Throwable
  {
    // Get annotation configuration
    String cacheName = config.cacheName();
    String keyPrefix = config.keyPrefix();
    String keyField  = config.keyField();
    boolean removeAll = config.removeAll();
    logger.debug( "Intercepted CollectionEvict [%s:%s:%s:%s]", cacheName, keyPrefix, keyField, removeAll );

    // Get Cache
    Cache cache = cacheManager.getCache( cacheName );
    if( cache == null ) {
      throw new AopInvocationException( "CollectionCacheEvict:  Cache '"+ cacheName +"' does not seem to exist?" );
    }

    if( removeAll ) {
      // Evict all items
      cache.clear();

    } else if( List.class.isInstance( arg )) {
      // Evict as list
      for( Object in: (List<?>)arg ) {
        evict( cache, keyPrefix, keyField, in );
      }

    } else {
      // Evict as object
      if( arg == null ) {
        evict( cache, keyPrefix, keyField, NULL_KEY );
      } else {
        evict( cache, keyPrefix, keyField, arg );
      }
    }
  }

  /** Evict a single item from the cache. */
  private void evict( Cache cache, String keyPrefix, String keyField, Object input )
  {
    // Key is based upon strategy marked by presence of keyField parameter
    // If parameter is present (ie. is not "" ) then cache by explicit field
    final boolean implicitKey = CollectionCache.IMPLICIT.equals( keyField );
    if( input != null ) {
      Object suffix = implicitKey ? input : PropertyAccessorFactory.forBeanPropertyAccess( input ).getPropertyValue( keyField );
      Serializable key = generateKey( keyPrefix, suffix );
      cache.evict( key );
    }
  }

  /** Generate a somewhat unique key based upon an input prefix and object. */
  public Serializable generateKey( String keyPrefix, Object input )
  {
    return 31 * (long)keyPrefix.hashCode() + input.hashCode();
  }
}
