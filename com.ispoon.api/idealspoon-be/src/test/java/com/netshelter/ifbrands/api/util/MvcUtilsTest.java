package com.netshelter.ifbrands.api.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MvcUtilsTest extends Assert
{
  @Test
  public void getIdsFromFilterTest() throws Exception
  {
    List<Integer> i1 = MvcUtils.getIdsFromFilter( "-" );
    assertNull( i1 );
    List<Integer> i2 = MvcUtils.getIdsFromFilter( "1" );
    assertTrue( i2.size() == 1 );
    assertTrue( i2.get( 0 ) == 1 );
    List<Integer> i3 = MvcUtils.getIdsFromFilter( "1,2,3" );
    assertTrue( i3.size() == 3 );
    assertTrue( i3.get( 0 ) == 1 );
    assertTrue( i3.get( 1 ) == 2 );
    assertTrue( i3.get( 2 ) == 3 );
  }
}
