package com.netshelter.ifbrands.api.util;

public class GeneralClientException extends GeneralException
{
  public GeneralClientException( String format, Throwable cause, Object... values )
  {
    super( format, cause, values );
  }

  public GeneralClientException( String format, Object... values )
  {
    super( format, values );
  }
}
