package com.netshelter.ifbrands.data.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.api.model.user.UserValue;

public class UserValueDaoTest extends DaoTest
{
  @Autowired
  private UserValueDao dao;

  @Test
  public void testUserValue() throws Exception
  {
    UserValue uv;

    dao.setValue( "bill", "name", "gray" );
    uv = dao.getValue( "bill",  "name" );
    assertEquals( uv.getKey(), "name" );
    assertEquals( uv.getValue(), "gray" );
    dao.setValue( "bill", "name", null );
    uv = dao.getValue( "bill",  "name" );
    assertEquals( uv.getKey(), "name" );
    assertNull( uv.getValue() );
  }
}
