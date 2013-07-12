package com.netshelter.ifbrands;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Dmitriy T
 */
public class DetailedError
{

    private String message                  = null;
    private String exception                = null;
    private String exceptionMessage         = null;
    private StackTraceElement[] stackTrace  = null;
    private Map<String, String> errorFields = null;

    public DetailedError(String message, String exception, String exceptionMessage,
                         StackTraceElement[] stackTrace, Map<String, String> errorFields)
    {
        this.message = message;
        this.exception = exception;
        this.exceptionMessage = exceptionMessage;
        this.stackTrace = stackTrace;
        this.errorFields = errorFields;
    }

    @Override
    public String toString()
    {
        return "DetailedError{" +
                "message='" + message + '\'' +
                ", exception='" + exception + '\'' +
                ", exceptionMessage='" + exceptionMessage + '\'' +
                ", stackTrace=" + (stackTrace == null ? null : stackTrace.length+"items") +
                ", errorFields=" + errorFields +
                '}';
    }

    public StackTraceElement[] getStackTrace()
    {
        return stackTrace;
    }

    public void setStackTrace(StackTraceElement[] stackTrace)
    {
        this.stackTrace = stackTrace;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getException()
    {
        return exception;
    }

    public void setException(String exception)
    {
        this.exception = exception;
    }

    public String getExceptionMessage()
    {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage)
    {
        this.exceptionMessage = exceptionMessage;
    }

    public Map<String, String> getErrorFields()
    {
        return errorFields;
    }

    public void setErrorFields(Map<String, String> errorFields)
    {
        this.errorFields = errorFields;
    }
}
