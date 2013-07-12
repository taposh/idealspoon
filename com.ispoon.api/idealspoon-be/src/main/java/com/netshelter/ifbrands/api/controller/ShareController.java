package com.netshelter.ifbrands.api.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.model.storyamplification.AmplifyList;
import com.netshelter.ifbrands.api.model.storyamplification.StoryAmplificationContainer;
import com.netshelter.ifbrands.api.model.storyamplification.StoryAmplifyDetail;
import com.netshelter.ifbrands.api.service.ShareService;
import com.netshelter.ifbrands.api.util.MvcUtils;

@Controller( "shareController" )
@RequestMapping( "/share" )
public class ShareController extends BaseController {
  @Autowired
  private ShareService shareService;

  /**
   * Amplify a story to a social service (without adId).  The UA will be redirected.
   * @param brandFilter
   * @param storyFilter
   * @param userFilter
   * @param typeFilter
   * @return
   * @throws Exception
   * @throws MalformedURLException
   */
  @RequestMapping( value = "/amplify/story:{storyId}/user:{userId}/type:{type}", method = RequestMethod.GET )
  public ModelAndView amplifyPost( @PathVariable( "storyId"     ) Integer storyId,
                                   @PathVariable( "userId"      ) String userFilter,
                                   @PathVariable( "type"        ) String typeFilter
                                  )
  {
    return amplifyPost( storyId, userFilter, typeFilter, null );
  }

  /**
   * Amplify a story to a social service (with adId).  The UA will be redirected.
   * @param brandFilter
   * @param storyFilter
   * @param userFilter
   * @param typeFilter
   * @return
   * @throws Exception
   * @throws MalformedURLException
   */
  @RequestMapping( value = "/amplify/story:{storyId}/user:{userId}/type:{type}/campaign:{campaignId}", method = RequestMethod.GET )
  public ModelAndView amplifyPost( @PathVariable( "storyId"     ) Integer storyId,
                                   @PathVariable( "userId"      ) String userFilter,
                                   @PathVariable( "type"        ) String typeFilter,
                                   @PathVariable( "campaignId"  ) Integer campaignId
                                 )
  {
    logger.info( "/share/amplify/story:%s/user:%s/type:%s/campaign:%s",
                 storyId, userFilter, typeFilter, campaignId );

    String amplifyUrl = shareService.getAmplifyUrl( storyId, userFilter, typeFilter, campaignId );

    RedirectView rv = new RedirectView( amplifyUrl );
    rv.setStatusCode(HttpStatus.MOVED_TEMPORARILY);
    rv.setUrl( amplifyUrl.toString() );
    ModelAndView mv = new ModelAndView(rv);

    return mv;
  }

  /**
   * Get amplification stats for any brand, ad, or campaign
   * @param typeFilter
   * @param id
   * @param start
   * @param stop
   * @return List of StoryAmplificationContainer containing amplification stats.
   * @throws NumberFormatException
   * @throws JsonParseException
   * @throws JsonMappingException
   * @throws IOException
   */
  @RequestMapping( value = "/stats/{type}:{id}", method = RequestMethod.GET )
  @ResponseBody
  public List<StoryAmplificationContainer> getAmplifyStats( @PathVariable( "type"    ) String typeFilter,
                                                            @PathVariable( "id"      ) Integer id,
                                                            @RequestParam( "start"   ) LocalDate start,
                                                            @RequestParam( "stop"    ) LocalDate stop )

  {
    logger.info( "/share/stats/%s:%s", typeFilter, id );

    DateTime startDateTime = start.toDateTimeAtStartOfDay( DateTimeZone.forID( "America/New_York" ) );
    DateTime stopDateTime = stop.toDateTimeAtStartOfDay( DateTimeZone.forID( "America/New_York" ) ).plusDays( 1 ).minusMillis( 1 );

    List<StoryAmplificationContainer> stats = shareService.getAmplifyStats( typeFilter, id, startDateTime, stopDateTime );

    return stats;
  }

  /**
   * Get amplification stats for stories
   * @param storyFilter
   * @return AmplifyList of amplification stats for stories requested.
   * @throws NumberFormatException
   * @throws JsonParseException
   * @throws JsonMappingException
   * @throws IOException
   */
  @RequestMapping( value = "/list/story:{storyIds}", method = RequestMethod.GET )
  @ResponseBody
  public AmplifyList getStoryAmplifyStats( @PathVariable( "storyIds" ) String storyFilter )

  {
    return getStoryAmplifyStats( null, storyFilter, null );
  }

  /**
   * Get amplification stats for stories, filtered by any brandId, adId, or campaignId
   * @param typeFilter
   * @param storyFilter
   * @param id
   * @return AmplifyList of amplification stats for stories requested.
   * @throws NumberFormatException
   * @throws JsonParseException
   * @throws JsonMappingException
   * @throws IOException
   */
  @RequestMapping( value = "/list/story:{storyIds}/{type}:{id}", method = RequestMethod.GET )
  @ResponseBody
  public AmplifyList getStoryAmplifyStats( @PathVariable( "type"     ) String typeFilter,
                                           @PathVariable( "storyIds" ) String storyFilter,
                                           @PathVariable( "id"       ) Integer id )

  {
    logger.info( "/share/list/story:%s/%s:%s", storyFilter, typeFilter, id );

    List<Integer> storyIds = MvcUtils.getIdsFromFilter( storyFilter );

    List<StoryAmplifyDetail> stats = shareService.getAmplifyStats( typeFilter, storyIds, id );
    AmplifyList amplifyList = new AmplifyList( stats );

    return amplifyList;
  }

  /**
   * Delete a set of amplifications
   * @param storyId
   * @param brandId
   * @return GenericStatus Success or failure
   * @throws NumberFormatException
   * @throws JsonParseException
   * @throws JsonMappingException
   * @throws IOException
   */
  @RequestMapping( value = "/delete/{type}:{id}/story:{storyId}", method = RequestMethod.GET )
  @ResponseBody
  public GenericStatus deleteAmplifications( @PathVariable( "type"    ) String typeFilter,
                                             @PathVariable( "id"      ) Integer id,
                                             @PathVariable( "storyId" ) Integer storyId )

  {
    logger.info( "/share/delete/%s:%s/story:%s", typeFilter, id, storyId );

    boolean status = shareService.deleteAmplifications( typeFilter, id, storyId );

    return GenericStatus.successFail( status );
  }
}
