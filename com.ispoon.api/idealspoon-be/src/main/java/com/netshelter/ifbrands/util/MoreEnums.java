package com.netshelter.ifbrands.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Yet more useful utilities for Enums.
 * @author bgray
 *
 */
public class MoreEnums
{
  public static <T extends Enum<T>> T parse( String name, Class<T> type )
  {
    return Enum.valueOf( type, name.toUpperCase() );
  }

  /**
   * Parse a Collection of enum strings into their actual Enum objects.
   * @param names names to parse
   * @param type enum class
   * @return Collection of enum objects
   */
  public static <T extends Enum<T>> Collection<T> parse( Collection<String> names, Class<T> type )
  {
    if( names == null ) return null;
    Collection<T> results = new ArrayList<T>();
    for( String n : names ) {
      T t = null;
      if(( n != null )&&( n.length() > 0 )) {
        t = parse( n, type );
      }
      results.add( t );
    }
    return results;
  }

}
