package com.netshelter.ifbrands.validation;

import com.netshelter.ifbrands.DetailedError;

import java.io.Serializable;

/**
 * @author Dmitriy T
 */
public class ValidationResponse
    implements Serializable
{
    private Object response; // Whatever object is placed in response - it should be Serializable
    private DetailedError error;
    private DetailedError warning;

    @Override
    public String toString()
    {
        return "ValidationResponse{" +
                "responseCode=" + getResponseCode() +
                ", error=" + error +
                ", warning=" + warning +
                '}';
    }

    public ValidationResponseCode getResponseCode()
    {
        if (error == null && warning == null)
        {
            return ValidationResponseCode.OK;
        }
        else
        if (error == null && warning != null) // Interesting! by this line the warning is guaranteed not null
        {
            return ValidationResponseCode.WARNING;
        }
        else
        {
            return ValidationResponseCode.ERROR;
        }
    }



    public Object getResponse()
    {
        return response;
    }

    public void setResponse(Object response)
    {
        this.response = response;
    }

    public DetailedError getError()
    {
        return error;
    }

    public void setError(DetailedError error)
    {
        this.error = error;
    }

    public DetailedError getWarning()
    {
        return warning;
    }

    public void setWarning(DetailedError warning)
    {
        this.warning = warning;
    }
}
