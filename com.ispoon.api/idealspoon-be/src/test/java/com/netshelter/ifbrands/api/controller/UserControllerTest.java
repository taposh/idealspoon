package com.netshelter.ifbrands.api.controller;

import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.model.user.UserValue;
import com.netshelter.ifbrands.api.model.user.UserValueList;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserControllerTest extends BaseControllerTest
{
  @Autowired
  UserController controller;

  ///////////////////////////////////
  // Just tests wiring correctness //
  ///////////////////////////////////
  @Test
  public void testController()
  {
    controller.getUserStates( "-" );
  }

  /**
   * Tests get/set value success and fail, character limit assertions
   */
  @Test
  public void testControllerGetSetValue()
  {
    String testValue = "silver";
    String key = "value";
    String userKey = "346";
    GenericStatus status = controller.setValue( userKey, key,  testValue);
    Assert.assertEquals( "OKAY", status.getStatus() );
    Assert.assertNotSame( UserController.USER_KEY_VALUE_LIMIT_MSG, status.getStatus() );

    UserValueList list = controller.getValue( userKey, key );
    Assert.assertEquals(1, list.getValues().size());
    for ( int i = 0; i < list.getValues().size(); i++ ) {
      UserValue value =  list.getValues().get(i);
      Assert.assertEquals( testValue, value.getValue() );
    }

    // creating a string longer than limit of the value - expecting a failure
    StringBuilder sb = new StringBuilder();
    for ( int i = 0; i <= UserController.USER_KEY_VALUE_LIMIT+1; i++ ) {
      sb.append( 'a' );
    }
    status = controller.setValue( "346", key, sb.toString() );
    Assert.assertEquals( "FAIL", status.getStatus() );
    Assert.assertEquals( UserController.USER_KEY_VALUE_LIMIT_MSG, status.getMessage() );
  }
}
