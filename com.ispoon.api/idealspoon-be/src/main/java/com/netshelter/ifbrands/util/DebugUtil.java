package com.netshelter.ifbrands.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Properties;

public class DebugUtil
{
  public static String getChainOfBlame( Throwable th )
  {
    if( th == null ) return "";

    final String LF = "\n";
    final StringBuffer sb = new StringBuffer();
    sb.append( getChainOfBlame( th.getCause() ));
    sb.append( th.toString() ).append( LF );
    return sb.toString();
  }

  public static String getStackTraceAsString( Throwable th )
  {
    return getStackTraceAsString( th, Integer.MAX_VALUE );
  }

  public static String getStackTraceAsString( Throwable th, int maxDepth )
  {
    int depth=0;
    final String LF = "\n";
    final StringBuilder sb = new StringBuilder();
    sb.append( th.toString() ).append( LF );
    sb.append( th.getClass().getName() ).append( ':' ).append( LF );
    for( StackTraceElement ste: th.getStackTrace() ) {
      sb.append( '\t' );
      if( depth >= maxDepth ) {
        sb.append( "... " ).append( th.getStackTrace().length - depth ).append( " more levels truncated." ).append( LF );
        break;
      }
      sb.append( ste.toString() ).append( LF );
      depth++;
    }
    if( th.getCause() != null ) {
      sb.append( LF ).append(  "Caused by: ").append( getStackTraceAsString( th.getCause(), maxDepth ));
    }
    return sb.toString();
  }

  public static String prettyException( Throwable th, String s )
  {
    StringBuffer sb = new StringBuffer();
    Throwable stk = th;
    sb.append( "--->  " ).append( s ).append( '\n' );
    while( th != null ) {
      String[] split = th.toString().split( ": ", 2 );
      sb.append( "--->  " ).append( split[0] ).append( '\n' );
      s = th.getMessage();
      if( s == null ) {
        s = "(no message)";
      }
      sb.append( wrapIndent( s )).append( '\n' );
      if(( split.length > 1 )&& !split[1].equals( s )) {
        sb.append( wrapIndent( split[1] )).append( '\n' );
      }
      th = th.getCause();
    }
    sb.append( getStackTraceAsString( stk )).append( '\n' );
    return sb.toString();
  }

  protected static String wrapIndent( String s )
  {
    StringBuffer sb = new StringBuffer();
    int from = 0;
    do {
      int lfidx = s.indexOf( '\n', from );
      if( lfidx == -1 ) lfidx = s.length();
      sb.append( "        " ).append( s.substring( from,lfidx )).append( '\n' );
      from = lfidx+1;
    } while( from < s.length() );
    return sb.toString();
  }

  public static String pojoToString( Object pojo )
  {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter( sw );
    pw.println();
    pw.println( pojo.getClass().getCanonicalName() );

    for( Method m: pojo.getClass().getMethods() ) {
      if( m.getName().startsWith( "get" ) &&( m.getParameterTypes().length == 0 )) {
        // This is probably a getter
        pw.format( "... %40s = ", m.getName()+"()" );
        try {
          pw.println( m.invoke( pojo ));
        } catch( Exception e ) {
          pw.println( e.getMessage() );
        }
      }
    }
    pw.flush();
    return sw.toString();
  }

  public static String toHtml( String text )
  {
    return "<tt>" + text.replace( "\n","<br/>" ) + "</tt>";
  }

  public static String dumpSystemProperties()
  {
    StringBuffer sb = new StringBuffer();
    Properties props = System.getProperties();
    for( String key: props.stringPropertyNames() ) {
      sb.append( String.format( "%s  =  %s", key, props.getProperty( key ))).append( '\n' );
    }
    return sb.toString();
  }
}
