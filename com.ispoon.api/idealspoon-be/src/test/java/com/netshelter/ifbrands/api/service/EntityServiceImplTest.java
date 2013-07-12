package com.netshelter.ifbrands.api.service;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.api.model.entity.Author;
import com.netshelter.ifbrands.api.model.entity.Brand;
import com.netshelter.ifbrands.api.model.entity.Site;
import com.netshelter.ifbrands.api.model.entity.Story;

public class EntityServiceImplTest extends BaseServiceTest
{
  @Autowired
  private EntityService service;

  @Test
  public void getSitesTest() throws Exception
  {
    Collection<Site> l1 = service.getSites( Arrays.asList( 1 ));
    assertNotNull( l1 );
    assertTrue( l1.size() > 0 );
  }

  @Test
  public void getBrandsTest() throws Exception
  {
    Collection<Brand> l1 = service.getBrands( null );
    assertNotNull( l1 );
    assertTrue( l1.size() > 0 );
  }

  @Test
  public void getAuthorsTest() throws Exception
  {
    Collection<Author> l1 = service.getAuthors( Arrays.asList( 1 ));
    assertNotNull( l1 );
    assertTrue( l1.size() > 0 );
  }
  @Test
  public void getStoriesTest() throws Exception
  {
    Collection<Story> l1 = service.getStories( Arrays.asList( 1188610 ));
    assertNotNull( l1 );
    assertTrue( l1.size() > 0 );
  }
}
