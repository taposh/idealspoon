package com.netshelter.ifbrands.api.util;

import org.springframework.core.NestedRuntimeException;

public abstract class GeneralException extends NestedRuntimeException
{
  public GeneralException( String format, Throwable cause, Object... values )
  {
    super( String.format( format, values ), cause );
  }

  public GeneralException( String format, Object... values )
  {
    super( String.format( format, values ) );
  }
}
