package com.netshelter.ifbrands.api.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityControllerTest extends BaseControllerTest
{
  @Autowired
  private EntityController controller;

  ////////////////////////////////////////
  // These just test wiring correctness //
  ////////////////////////////////////////
  @Test
  public void getSitesTest() throws Exception  { controller.getSites( "1" ); }
  @Test
  public void getBrandsTest() throws Exception  { controller.getBrands( "1" ); }
  @Test
  public void getAuthorwTest() throws Exception  { controller.getAuthors( "1" ); }
  @Test
  public void getStoriesTest() throws Exception  { controller.getStories( "2628432" ); }
}
