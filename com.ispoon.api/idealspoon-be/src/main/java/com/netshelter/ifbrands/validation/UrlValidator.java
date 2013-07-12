package com.netshelter.ifbrands.validation;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Validator for URLs:
 *   Checks if the url is not blank
 *   if not tries to validate the response error code 2xx
 *
 * @author Dmitriy T
 */
public class UrlValidator
    extends Validator
{
    private String      urlAddress;
    private boolean     followRedirects;

    public UrlValidator(String urlAddress, boolean followRedirects)
    {
        this.urlAddress         = urlAddress;
        this.followRedirects    = followRedirects;
    }

    @Override
    public boolean validate()
    {
        int code = 0;
        org.apache.commons.validator.UrlValidator v = new org.apache.commons.validator.UrlValidator();
        if (StringUtils.isNotBlank(urlAddress) && v.isValid(urlAddress))
        {
            URL url = null;
            try
            {
                url = new URL(urlAddress);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setInstanceFollowRedirects(followRedirects);
                connection.connect();

                code = connection.getResponseCode();
                connection.disconnect();
            }
            catch (MalformedURLException e) {}
            catch (ProtocolException e) {}
            catch (IOException e) {}
        }
        return (code >= 200 && code <= 299); // any 2xx code is fine
    }
}
