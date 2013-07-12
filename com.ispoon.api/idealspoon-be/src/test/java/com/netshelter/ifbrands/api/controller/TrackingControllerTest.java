package com.netshelter.ifbrands.api.controller;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.campaign.ObjectTypeEnum;
import com.netshelter.ifbrands.api.model.campaign.Tracking;
import com.netshelter.ifbrands.api.model.campaign.TrackingSetEnum;
import com.netshelter.ifbrands.api.model.campaign.TrackingTypeEnum;
import com.netshelter.ifbrands.validation.ValidationResponse;
import com.netshelter.ifbrands.validation.ValidationResponseCode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Dmitriy T
 */
public class TrackingControllerTest
    extends BaseControllerTest
{

    private final Random r = new Random();

    @Autowired
    TrackingController controller;

    @Test
    public void testCRUD()
    {

        GenericPayload<Collection<Tracking>> result = controller.listByAny(null, null, null, null, null);
        List<Tracking> list = (List<Tracking>)result.get("trackingList");

        int initialEntitiesSize = list.size();

        Tracking entity = (Tracking)controller.create(123,
                                            ObjectTypeEnum.Campaign.name(),
                                          TrackingSetEnum.PreClickTracking.name(),
                                          TrackingTypeEnum.JavaScript.name(),
                                          "<script>alert('Works meau...');</script>",
                                          false
        );
        result = controller.listByAny(null, null, null, null, null);
        list = (List<Tracking>)result.get("trackingList");
        assertEquals("Should be equal", (initialEntitiesSize + 1), list.size());
        for (Tracking tracking : list)
        {
            System.out.println(tracking);
            controller.delete( tracking.getTrackingId() );
        }
    }

    @Test
    public void testValidation()
    {
        List<Map> objects = new ArrayList<Map>();
        objects.add(createTestValidationObject("ClickImpression", "https://www.google.com")); // expected error
        objects.add(createTestValidationObject("Impression", "http://www.textfiles.com/100/914bbs.txt")); // expected error
        objects.add(createTestValidationObject("Impression", "http://thatgamecompany.com/wp-content/themes/thatgamecompany/_include/img/flower/flower-game-screenshot-2.jpg")); // expected ok
        objects.add(createTestValidationObject("Click", "http://thatgamecompany.com/wp-content/themes/thatgamecompany/_include/img/flower/flower-game-screenshot-2.jpg")); // expected warning
        objects.add(createTestValidationObject("JavaScript", "https://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js")); // expected ok
        objects.add(createTestValidationObject("JavaScript", "https://ajax.googleapis.com/ajax/libs/jquery/1.8.0/THIS-IS-A-BAD-URL")); // expected error
        objects.add(createTestValidationObject("NonExistingTrackingType", "bla bla bla")); // expected error

        List<ValidationResponse> responseList = controller.validate(objects);
        int errors = 0;
        int warnings = 0;
        int successes = 0;
        for (int i = 0; i < responseList.size(); i++)
        {
            ValidationResponse response = responseList.get(i);
            if (response.getResponseCode() == ValidationResponseCode.OK)
            {
                successes++;
            }
            else
            if (response.getResponseCode() == ValidationResponseCode.ERROR)
            {
                errors++;
            }
            else
            if (response.getResponseCode() == ValidationResponseCode.WARNING)
            {
                warnings++;
            }
            System.out.println(response);
        }
        assertEquals("There should be 4 expected errors.", 4, errors);
        assertEquals("There should be 1 expected warnings.", 1, warnings);
        assertEquals("There should be 2 expected successes.", 2, successes);

        System.out.println("Expected errors: " + errors);
        System.out.println("Expected warnings: " + warnings);
        System.out.println("Expected successes: " + successes);
    }

    private Map createTestValidationObject(String type, String value)
    {
        Map result = new HashMap();
        result.put("trackingType", type);
        result.put("textValue", value);
        return result;
    }

}
