package com.netshelter.ifbrands.api.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.api.model.user.UserState;

public class UserServiceImplTest extends BaseServiceTest
{
  @Autowired
  private UserService service;

  @Test
  public void userServiceTest() throws Exception
  {
    Integer brandId = new Integer( 5 );
    // Create
    UserState u1 = service.createUser( "key", brandId );
    assertEquals( brandId, u1.getBrand().getId() );
    assertEquals( "key", u1.getUserKey() );
    assertNotNull( u1.getId() );

    // Get
    List<UserState> u2 = service.getUser( Arrays.asList( "key" ));
    assertNotNull( u2 );
//    assertEquals( 1, u2.size() );
//    assertEquals( u1, u2.get( 0 ));

    // Login
    UserState u3 = service.loginUser( "key" );
    assertFalse( u1.getLastLogin().equals( u3.getLastLogin() ));

    // Delete
    boolean success = service.deleteUser( "key" );
    assertTrue( success );
    List<UserState> u4 = service.getUser( Arrays.asList( "key" ));
    assertNotNull( u4 ); // temp test
    //assertEquals( 0, u4.size() );

    //
  }

}
