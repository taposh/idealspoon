package com.netshelter.ifbrands.validation;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

/**
 * Url content type validator: validates the url response for required and disallowed content types
 * @author Dmitriy T
 */
public class UrlContentTypeValidator
    extends Validator
{
    private String      urlAddress;
    List<String>        requiredContentType;
    List<String>        disallowedContentType;
    private boolean     followRedirects;

    public UrlContentTypeValidator(String urlAddress,
                                   List<String> requiredContentType,
                                   List<String> disallowedContentType,
                                   boolean followRedirects)
    {
        this.urlAddress             = urlAddress;
        this.requiredContentType    = requiredContentType;
        this.disallowedContentType  = disallowedContentType;
        this.followRedirects        = followRedirects;
    }

    @Override
    public boolean validate()
    {
        boolean valid = true;
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
                if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 299)
                {
                    String contentType = connection.getContentType();
                    if (StringUtils.isNotBlank(contentType))
                    {
                        if (contentType.indexOf(";") > 0)
                        {
                            contentType = contentType.substring(0, contentType.indexOf(";"));
                        }
                    }
                    if (disallowedContentType != null && disallowedContentType.contains(contentType))
                    {
                        valid = false;
                    }
                    if (valid && requiredContentType != null && !requiredContentType.contains(contentType))
                    {
                        valid = false;
                    }
                }
                connection.disconnect();
            }
            catch (MalformedURLException e) {}
            catch (ProtocolException e) {}
            catch (IOException e) {}
        }
        return valid;
    }
}
