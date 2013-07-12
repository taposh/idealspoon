package com.netshelter.ifbrands.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

/**
 * This annotation enables caching for Collection-toCollection mapping methods.
 * @author bgray
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionCache
{
  /** Constant used to indicate implicit ordering is being used. */
  public static final String IMPLICIT = "##_implicit_##";
  /** Name of the cache within the CacheManager. */
  String cacheName();
  /** Prefix for key in cache. */
  String keyPrefix();
  /** Field in result object which has key, else assume 1:1 mapping always. */
  String keyField();
  /** List implementation to use. */
  Class<?> implClass() default ArrayList.class;
}
