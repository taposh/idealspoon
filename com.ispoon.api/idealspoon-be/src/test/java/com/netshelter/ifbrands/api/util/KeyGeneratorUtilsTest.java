package com.netshelter.ifbrands.api.util;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.netshelter.ifbrands.util.KeyGeneratorUtils;
import com.netshelter.ifbrands.util.KeyGeneratorUtils.KeyGenerator;

public class KeyGeneratorUtilsTest extends Assert
{
  @Test
  public void testSimpleKeyGenerator() throws Exception
  {
    int n = 10000;
    Set<String> keys = new HashSet<String>();
    KeyGenerator kg = KeyGeneratorUtils.getSimpleGenerator();
    System.out.println( kg.toString() );
    for( int i=0; i<n; i++ ) {
      String k1 = kg.generateKey();
      String k2 = kg.generateKey();
      //System.out.println( k1 +" "+ k2 );
      assertTrue( keys.add( k1 ));
      assertTrue( keys.add( k2 ));
      Thread.sleep( 2 );
    }
    assertEquals( n<<1, keys.size() );
  }
}
