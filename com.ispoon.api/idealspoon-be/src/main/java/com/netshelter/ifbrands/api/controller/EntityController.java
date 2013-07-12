package com.netshelter.ifbrands.api.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.model.entity.Author;
import com.netshelter.ifbrands.api.model.entity.Brand;
import com.netshelter.ifbrands.api.model.entity.Site;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.service.EntityService;
import com.netshelter.ifbrands.api.util.MvcUtils;

/**
 * Controller for 'entity' API calls.  An 'entity' is a constant object within the system,
 * identified by a nigh immutable integer ID value.
 * @author bgray
 *
 */
@Controller( "entityController" )
@RequestMapping( "/entity" )
public class EntityController extends BaseController
{
  @Autowired
  private EntityService entityService;

  /** Flush the cache. */
  @RequestMapping( value = "/flush" )
  @ResponseBody
  public GenericStatus flushCache()
  {
    entityService.flushCache();
    return GenericStatus.okay( EntityService.ENTITY_CACHE + " cache flushed" );
  }
  
  /** Flush the cache for story entity. */
  @RequestMapping( value = "/story/flush/{id}" )
  @ResponseBody
  public GenericStatus flushStoryCache( @PathVariable( "id" ) Integer id )
  {
    entityService.flushStory( id );
    return GenericStatus.okay( EntityService.ENTITY_CACHE + " cache flushed" );
  }

  /** Return site info. */
  @RequestMapping( value = "/site/{sites}" )
  @ResponseBody
  public GenericPayload<Collection<Site>> getSites( @PathVariable( "sites" ) String filter )
  {
    logger.info( "/entity/site/%s", filter );
    List<Integer> ids = MvcUtils.getIdsFromFilter( filter );
    Collection<Site> sites = entityService.getSites( ids );
    logger.debug( "...%d found", sites.size() );
    return new GenericPayload<Collection<Site>>( "sites", sites );
  }

  /** Return brand info.  */
  @RequestMapping( value = "/brand/{brands}" )
  @ResponseBody
  public GenericPayload<Collection<Brand>> getBrands( @PathVariable( "brands" ) String filter )
  {
    logger.info( "/entity/brand/%s", filter );
    List<Integer> ids = MvcUtils.getIdsFromFilter( filter );
    Collection<Brand> brands = entityService.getBrands( ids );
    logger.debug( "...%d found", brands.size() );
    return new GenericPayload<Collection<Brand>>( "brands", brands );
  }

  /** Return author info. */
  @RequestMapping( value = "/author/{authors}" )
  @ResponseBody
  public GenericPayload<Collection<Author>> getAuthors( @PathVariable( "authors" ) String filter )
  {
    logger.info( "/entity/author/%s", filter );
    List<Integer> ids = MvcUtils.getIdsFromFilter( filter );
    Collection<Author> authors = entityService.getAuthors( ids );
    logger.debug( "...%d found", authors.size() );
    return new GenericPayload<Collection<Author>>( "authors", authors );
  }

  /** Return story info. This will also fetch brand sentiment info.*/
  @RequestMapping( value = "/story/{stories}" )
  @ResponseBody
  public GenericPayload<Collection<Story>> getStories( @PathVariable( "stories" ) String filter )
  {
    logger.info( "/entity/story/%s", filter );
    List<Integer> ids = MvcUtils.getIdsFromFilter( filter );
    Collection<Story> stories = entityService.getStories( ids );
    logger.debug( "...%d found", stories.size() );
    return new GenericPayload<Collection<Story>>( "stories", stories );
  }
}
