package com.netshelter.ifbrands.util;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Yet more useful Collection utilities.
 * @author bgray
 *
 */
public class MoreCollections
{
  /** Truncate a list to the first n entries.
   * @param list Input list
   * @param limit Max result size.
   */
  public static <T> void headList( List<T> list, int limit )
  {
    int size = list.size();
    while( size > limit ) {
      size--;
      list.remove( size );
    }
  }

  /**
   * Return a list containing only the given item, unless it is null
   * in which case we return an empty list.
   * @param item Item for list
   * @return ImmutableList containg only the item given, or empty
   */
  @SuppressWarnings( "unchecked" )
  public static <T> List<T> singletonNotNullList( T item )
  {
    return (List<T>)(( item == null ) ? ImmutableList.of() : ImmutableList.of( item ));
  }

  /** Return the first item from the Iterable object if it exists, else null   */
  public static <T> T firstOrNull( Iterable<T> coll )
  {
    if( coll == null ) return null;
    Iterator<T> iter = coll.iterator();
    if( !iter.hasNext() ) return null;
    return iter.next();
  }
}
