package com.netshelter.ifbrands.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.model.StatusInfo;
import com.netshelter.ifbrands.api.service.StatusService;

/**
 * Controller for genearl system status.
 * @author bgray
 *
 */
@Controller( "statusController" )
@RequestMapping( "/status" )
public class StatusController extends BaseController
{
  @Autowired
  private StatusService statusService;

  @RequestMapping( value = "/flush" )
  @ResponseBody
  public GenericStatus flushAllCaches()
  {
    statusService.flushAllCaches();
    return GenericStatus.okay( "All caches flushed" );
  }

  /**
   * Provide html info of system health.  This queries downstream
   * systems to ensure that the whole system is working.
   * @return
   */
  @RequestMapping( value = {"/health","/health.html"}, method = RequestMethod.GET )
  public ModelAndView healthAsHtml()
  {
    StatusInfo info = statusService.getStatusInfo();
    ModelAndView mv = new ModelAndView( "status" );
    mv.addObject( "info", info );
    return mv;
  }

  /**
   * Provide JSON info of system health.
   */
  @RequestMapping( value = {"/health","/health.json"}, method = RequestMethod.GET, produces="application/json" )
  @ResponseBody
  public StatusInfo healthAsJson()
  {
    return statusService.getStatusInfo();
  }

  @RequestMapping( value = "alive" )
  @ResponseBody
  public String alive( @RequestParam( "echo" ) String echo )
  {
    return echo;
  }
}
