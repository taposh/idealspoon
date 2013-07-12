package com.netshelter.ifbrands.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MvcUtils
{
  public static final String ALL = "-", DELIM=",";

  /**
   * Given a comma-separated filter string, return the List
   * of Integer values contained therein.  If the filter is
   * the single character '-' then we return 'null', which
   * indicates no filtering on ID should be performed
   * @param filter comma-separated list of integer ids, or '-'
   * @return List of integer ids, or null
   */
  public static List<Integer> getIdsFromFilter( String filter )
  {
    if(( filter == null )||( filter.length() == 0 )) return null;
    if( ALL.equals( filter )) return null;
    String[] split = filter.split( DELIM );
    List<Integer> ids = new ArrayList<Integer>( split.length );
    for( String s: split ) {
      ids.add( new Integer( s ));
    }
    return ids;
  }

  /**
   * Given a comma-separated list of strings, return a List
   * of that set.
   * @param filter comma-separated list of string values
   * @return List of those values
   */
  public static List<String> getStringsFromFilter( String filter )
  {
    if( filter == null ) return null;
    return Arrays.asList( filter.split( "," ));
  }
}
