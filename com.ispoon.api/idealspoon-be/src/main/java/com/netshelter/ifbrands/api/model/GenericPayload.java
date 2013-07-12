package com.netshelter.ifbrands.api.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Container for a generic named object.  This allows arbitray top-level Java objects
 * to be wrapped as a named object, useful for serialization to JSON.  The most common
 * use will be to wrap an unnamed Collection or Array so that the top-level serialized
 * entity is an 'object' and not an 'array'.
 *
 * Collection<Cat> cats = getKittens();
 * GenericPayload<Collection<Cat>> response = new GenericPayload<Collection<Cat>>( "cats", cats " );
 *
 * @author bgray
 *
 * @param <T> Contained type.
 */
public class GenericPayload<T> extends LinkedHashMap<String,T>
{
  /**
   * Create an empty payload -- can use standard Map methods to populate.
   */
  public GenericPayload() {}

  /**
   * Create a payload from an existing map.
   * @param data existing map of data
   */
  public GenericPayload( Map<String,T> data )
  {
    putAll( data );
  }

  /**
   * Construct a new GenericPayload with the given named object.
   * @param name name of object
   * @param data object
   */
  public GenericPayload( String name, T data )
  {
    put( name, data );
  }
  

  
}
