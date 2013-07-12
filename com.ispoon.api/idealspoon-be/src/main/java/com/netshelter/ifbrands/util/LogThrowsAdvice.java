package com.netshelter.ifbrands.util;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;

/**
 * Utility advice which outputs a full stacktrace of any Exception to the application Logger.
 * @author bgray
 *
 */
public class LogThrowsAdvice implements ThrowsAdvice
{
  private static Logger logger = LoggerFactory.getLogger();

  /** Send stacktrace to log after Throws.  */
  public void afterThrowing( Method method, Object[] args, Object target, Exception ex ) throws Throwable
  {
    logger.error( DebugUtil.getStackTraceAsString( ex ));
    throw ex;
  }
}
