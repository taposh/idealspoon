package com.netshelter.ifbrands.validation.suite;

import com.netshelter.ifbrands.DetailedError;
import com.netshelter.ifbrands.validation.UrlContentTypeValidator;
import com.netshelter.ifbrands.validation.UrlValidator;
import com.netshelter.ifbrands.validation.ValidationResponse;
import com.netshelter.ifbrands.validation.Validator;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitriy T
 */
public class ImpressionTrackingValidationSuite
    extends ValidationSuite
{
    private String value = null;

    public ImpressionTrackingValidationSuite(String value)
    {
        this.value = value;
    }

    @Override
    public ValidationResponse validate()
    {
        ValidationResponse response = new ValidationResponse();
        if (StringUtils.isNotBlank(value))
        {
            Validator validator = new UrlValidator(value, true);
            if (!validator.validate())
            {
                response.setError(new DetailedError("Tracking value is not a valid url - does not return 2xx HTTP response code", null, null, null, null));
            }
            else
            {
                List<String> disallowedContentTypes = new ArrayList<String>();
                disallowedContentTypes.add("text/plain");
                disallowedContentTypes.add("text/javascript");
                disallowedContentTypes.add("application/javascript");
                disallowedContentTypes.add("application/x-javascript");
                disallowedContentTypes.add("text/html");
                validator = new UrlContentTypeValidator(value, null, disallowedContentTypes, true);
                if (!validator.validate())
                {
                    response.setError(new DetailedError("Tracking value url response has content type which is disallowed", null, null, null, null));
                }
                else
                {
                    // valid url do not set errors or warnings
                }
            }
        }
        else
        {
            response.setError(new DetailedError("Tracking value can not be empty", null, null, null, null));
        }
        return response;
    }
}
