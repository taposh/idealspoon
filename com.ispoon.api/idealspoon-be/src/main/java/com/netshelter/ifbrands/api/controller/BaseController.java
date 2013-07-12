package com.netshelter.ifbrands.api.controller;

import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.api.model.CustomStatus;
import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.util.GeneralClientException;
import com.netshelter.ifbrands.api.util.GenerateThumbnailsException;
import com.netshelter.ifbrands.api.util.SetCustomStoryImageException;
import com.netshelter.ifbrands.util.JodaUtils;

/**
 * Useful base for Controllers.  Offers JODA PropertyEditor support plus default ExceptionHandler
 * @author bgray
 *
 */
public abstract class BaseController
{
  public static final TimeZone TZ_DEFAULT = TimeZone.getTimeZone( "America/New_York" );

  protected  Logger logger = LoggerFactory.getLogger();

  /** Bind PropertyEditors for Joda objects. */
  @InitBinder
  public void initBinder( WebDataBinder binder )
  {
    // Custom PropertyEditors for JODA classes
    binder.registerCustomEditor( DateTime.class, JodaUtils.getSpringDateTimePropertyEditor() );
    binder.registerCustomEditor( LocalDate.class, JodaUtils.getSpringLocalDatePropertyEditor() );
    binder.registerCustomEditor( DateTimeZone.class, JodaUtils.getSpringDateTimeZonePropertyEditor() );
  }

  /** Exception handler. */
  @ExceptionHandler(
                    {
                      GenerateThumbnailsException.class
                    } )
  @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
  @ResponseBody
  public CustomStatus generateThumbnailsExceptionHandler( GenerateThumbnailsException ex )
  {
    logger.warn( "Exception caught", ex );
    return CustomStatus.fail( ex, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getFeedStoryFailures(), ex.getReturnObject() );
  }
  
  /** Exception handler. */
  @ExceptionHandler(
                    {
                      SetCustomStoryImageException.class
                    } )
  @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
  @ResponseBody
  public CustomStatus setCustomStoryImageExceptionHandler( SetCustomStoryImageException ex )
  {
    logger.warn( "Exception caught", ex );
    return CustomStatus.fail( ex, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getFeedStoryFailures(), null );
  }
  
  /** Exception handler. */
  @ExceptionHandler(
                    {
                      GeneralClientException.class,
                      ServletRequestBindingException.class
                    } )
  @ResponseStatus( HttpStatus.BAD_REQUEST )
  @ResponseBody
  public GenericStatus clientClientExceptionHandler( Exception ex )
  {
    logger.warn( "Exception caught", ex );
    return GenericStatus.fail( ex, HttpStatus.BAD_REQUEST.value() );
  }

  /** Exception handler. */
  @ExceptionHandler( Exception.class )
  @ResponseStatus( HttpStatus.SERVICE_UNAVAILABLE )
  @ResponseBody
  public GenericStatus clientServerExceptionHandler( Exception ex )
  {
    logger.warn( "Exception caught", ex );
    return GenericStatus.fail( ex, HttpStatus.SERVICE_UNAVAILABLE.value() );
  }
}
