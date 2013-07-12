package com.netshelter.ifbrands.util;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

public class MoreStrings
{
  public final static char C_LF = '\n';
  public final static String S_LF = "\n";

  /**
   * Truncate a string to less than or equal to a given character count, ensuring that
   * only whole lines are truncated.  An optional message will be appended to
   * the result string if truncation occurred.  The optional msg parameter is not factored
   * into the truncation length, so the result string will be greater than maxChars if a
   * message is given. Lines are delimited by the LF character '\n' (0x0a)
   * @param input string to truncate
   * @param maxChars maximum number of characters in truncated string
   * @param msg if not null, this will be appended to string if truncated
   * @return result string
   */
  public static String truncateLinesByChars( String input, int maxChars, String msg )
  {
    int origLen = input.length();
    if( maxChars <= 0 ) {
      input="";
    } else {
      int lastLf = input.lastIndexOf( C_LF, maxChars );
      if( lastLf >= 0 ) {
        input = input.substring( 0, lastLf+1 );
      } else {
        // No lines at all so just truncate by chars
        input = input.substring( 0, maxChars-1 );
      }
    }
    if(( origLen > input.length() )&&( msg != null )) input += msg;
    return input;
  }

  /**
   * Truncate a string to less than or equal to a given line count.  An optional message
   * will be appended to the result string if truncation occurred.  The optional msg
   * parameter is not factored into the truncation length, so the result string will be
   * greater than maxLines if a message is given. Lines are delimited by the LF
   * character '\n' (0x0a).  The trailing LF on a truncation is always included in the
   * result string.
   * @param input string to truncate
   * @param maxLines maximum number of lines in truncated string; no limit if < 0
   * @param msg if not null, this will be appended to string if truncated
   * @return result string
   */
  public static String truncateLinesByLines( String input, int maxLines, String msg )
  {
    int idx;
    if( maxLines <= 0 ) {
      // Truncate to 0 length
      idx = 0;
    } else {
      // Get index of Nth linefeed
      idx = StringUtils.ordinalIndexOf( input, S_LF, maxLines );
      if( idx < 0 ) {
        // There are less than N linefeeds, so return whole string
        idx = input.length();
      }
    }
    return truncateLinesByChars( input, idx+1, msg );
  }
  /**
   * Return the number of characters 'c' within the string 's'.
   * @param s String to search
   * @param c Character to count
   * @return number of occurrences of the character
   */

  public static int countChars( String s, char c )
  {
    int cnt=0;
    for( int i=0; i<s.length(); i++ ) {
      if( s.charAt( i ) == c ) cnt++;
    }
    return cnt;
  }


  /**
   * Convert an array of objects f[0]...f[n] into a comma-separated list of strings
   * surrounded by parentheses, using String.valueOf() on each object.
   * (f[0],f[1],...,f[n])
   *
   * @param open string to prepend
   * @param close string to append
   * @param fields Set of fields
   * @return Formatted list
   */
  public static String makeEnclosedCsv( String open, String close,  Object[] fields )
  {
    StringBuffer sb = new StringBuffer();
    sb.append( open );
    sb.append( makeCsv( fields ) );
    sb.append( close );
    return sb.toString();
  }

  /**
   * Convert an array of objects f[0]...f[n] into a comma-separated list of strings
   * using String.valueOf() on each object.
   * f[0],f[1],...,f[n]
   *
   * @param fields Set of fields
   * @return Formatted list
   */
  public static String makeCsv( Object[] fields )
  {
    StringBuffer sb = new StringBuffer();
    for( Object f : fields ) {
      sb.append( String.valueOf( f )).append( ',' );
    }
    sb.deleteCharAt( sb.length() - 1 );
    return sb.toString();
  }

  /**
   * Convert a Collection of objects into a comma-separated list of strings
   * surrounded by parantheses, using String.valueOf() on each object.
   * @param fields
   * @return
   */
  public static String makeEnclosedCsv( String open, String close, Collection<? extends Object> fields )
  {
    return makeEnclosedCsv( open, close, fields.toArray() );
  }

  /**
   * Convert a Collectino of objects into a comma-separated list of strings
   * using String.valueOf() on each object.
   * @param fields
   * @return
   */
  public static String makeCsv( Collection<? extends Object> fields )
  {
    return makeCsv( fields.toArray() );
  }

  /**
   * Create a typed version of a = (b != null ? b : c) construtct.
   * The value will be returned if not null, else the default value is returned.
   * @param value
   * @param ifNull
   * @deprecated Replaced by {@link MoreObjects#ifNull(Object, Object)}
   * @return
   */
  @Deprecated
  public static <T> T isNull( T value, T ifNull )
  {
    if( value == null ) return ifNull;
    return value;
  }
}
