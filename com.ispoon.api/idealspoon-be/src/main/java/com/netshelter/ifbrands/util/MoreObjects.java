package com.netshelter.ifbrands.util;

/**
 * Yet more useful utilities for Objects.
 * @author bgray
 *
 */
public class MoreObjects
{
  /**
   * Create a typed version of a = (b != null ? b : c) construtct.
   * The value will be returned if not null, else the default value is returned.
   * @param value
   * @param ifNull
   * @return
   */
  public static <T> T ifNull( T value, T ifNull )
  {
    if( value == null ) return ifNull;
    return value;
  }

  /**
   * Check if two objects are equivalent.  Objects are equivalent if they
   * are both null, or if a.equals( b ) is true.
   * @param a
   * @param b
   * @return
   */
  public static <T> boolean equivalent( T a, T b )
  {
    // If same object, return true
    if( a == b ) return true;
    // If first is null, return false (if both were null, above would have returned
    if( a == null ) return false;
    // Else call the equals method directly (handles if b is null)
    return a.equals( b );
  }
}
