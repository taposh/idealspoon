package com.netshelter.ifbrands.api.util;

import org.junit.Assert;
import org.junit.Test;

public class IFBrandsTimingFilterTest extends Assert
{
  private IFBrandsTimingFilter filter = new IFBrandsTimingFilter();

  @Test
  public void testFilter()
  {
    String a;

    a = filter.getPathInfo( "/status/foo" );
    assertNull( a );

    a = filter.getPathInfo( "/foo/bar" );
    assertEquals( "/foo/bar", a );

    a = filter.getPathInfo( "/foo/bar/-" );
    assertEquals( "/foo/bar", a );

    a = filter.getPathInfo( "/foo/bar:4/baz" );
    assertEquals( "/foo/bar/baz", a );

    a = filter.getPathInfo( "/foo/bar/baz" );
    assertEquals( "/foo/bar/baz", a );

    a = filter.getPathInfo( "/foo/bar/baz:73" );
    assertEquals( "/foo/bar/baz", a );

  }
}
