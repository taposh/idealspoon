package com.netshelter.ifbrands.api.util;

public class GeneralServerException extends GeneralException
{
  public GeneralServerException( String format, Throwable cause, Object... values )
  {
    super( format, cause, values );
  }

  public GeneralServerException( String format, Object... values )
  {
    super( format, values );
  }
}
