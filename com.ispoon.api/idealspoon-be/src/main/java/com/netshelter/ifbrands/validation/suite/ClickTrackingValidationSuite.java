package com.netshelter.ifbrands.validation.suite;

import com.netshelter.ifbrands.DetailedError;
import com.netshelter.ifbrands.api.model.campaign.Tracking;
import com.netshelter.ifbrands.api.model.campaign.TrackingTypeEnum;
import com.netshelter.ifbrands.validation.UrlMapper;
import com.netshelter.ifbrands.validation.UrlValidator;
import com.netshelter.ifbrands.validation.ValidationResponse;
import com.netshelter.ifbrands.validation.Validator;
import org.apache.commons.lang.StringUtils;

/**
 * @author Dmitriy T
 */
public class ClickTrackingValidationSuite
    extends ValidationSuite
{
    private String value = null;

    public ClickTrackingValidationSuite(String value)
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
                // check for valid / known click tracking url pattern
                UrlMapper mapper = new UrlMapper();
                if (mapper.isKnownPattern(value))
                {
                    // valid known pattern do not set errors or warnings
                }
                else
                {
                    Tracking tracking = new Tracking(null, null, null, null, TrackingTypeEnum.ClickImpression, value, null);
                    response.setResponse(tracking);
                    response.setWarning(new DetailedError("Provided click tracking contains an unknown url pattern, type changed to ClickImpression", null, null, null, null));
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
