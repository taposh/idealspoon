package com.netshelter.ifbrands;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;

/**
 * Holder class for info relating to the build.
 * @author bgray
 *
 */
public final class BuildVersion
{
  private Logger logger = LoggerFactory.getLogger();
  private static final String BUILD_PREFIX = "build.";

  int maxKeyLength = 0;
  private Properties properties;

  /**
   * Set all values from an existing Properties object.
   * @param properties
   */
  public void setBuildProperties( Properties properties )
  {
    this.properties = new Properties();
    for( String key: properties.stringPropertyNames() ) {
      if( key.startsWith( BUILD_PREFIX )) {
        maxKeyLength = Math.max( key.length(), maxKeyLength );
        this.properties.put( key, properties.getProperty( key ));
      }
    }
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter( sw );
    this.properties.list( pw );
    logger.info( sw.toString() );
  }

  public Properties getBuildProperties()
  {
    return properties;
  }

  @Override
  public final String toString()
  {
    // Iterate properties to get ones that start with "build."
    StringBuilder sb = new StringBuilder();
    sb.append( "----------------------------------------------------------------" );
    for( String key: properties.stringPropertyNames() ) {
      sb.append( String.format( "\n%-"+maxKeyLength+"s: %s ", key, properties.getProperty( key, "-" )));
    }
    sb.append( "\n----------------------------------------------------------------\n" );
    return sb.toString();
  }
}
