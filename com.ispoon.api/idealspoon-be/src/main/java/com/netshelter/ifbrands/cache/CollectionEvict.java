package com.netshelter.ifbrands.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation enables cache eviction for Collection-to-Collection mapping methods.
 * @author bgray
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionEvict
{
  /** Constant used to indicate implicit ordering is being used. */
  public static final String IMPLICIT = CollectionCache.IMPLICIT;
  /** Name of the cache within the CacheManager. */
  String cacheName();
  /** Prefix for key in cache. */
  String keyPrefix() default "";
  /** Field in result object which has key, else assume 1:1 mapping always. */
  String keyField() default "";
  /** List implementation to use. */
  boolean removeAll() default false;
}
