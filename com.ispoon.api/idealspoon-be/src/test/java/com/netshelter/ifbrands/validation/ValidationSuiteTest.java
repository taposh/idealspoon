package com.netshelter.ifbrands.validation;

import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.service.CampaignService;
import com.netshelter.ifbrands.data.dao.DaoTest;
import com.netshelter.ifbrands.validation.suite.TestValidationSuite;
import com.netshelter.ifbrands.validation.suite.ValidationSuite;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Dmitriy T
 */
@Component
public class ValidationSuiteTest
    extends DaoTest
{

    @Autowired
    CampaignService campaignService = null;


    @Test
    public void testValidationSuite() throws Exception
    {

        ValidationSuite suite = null;
        ValidationResponse response = null;
        List<Campaign> campaignList = (List<Campaign>)campaignService.getCampaigns(null, true, null, null, null, null, null);
        for (Campaign campaign : campaignList)
        {
            suite = new TestValidationSuite(campaign.getId());
            response = suite.validate();
            assertNull("testValidationSuite.validate("+campaign.getId()+") should result in a valid (no error) response", response.getError());
            System.out.println(response);
        }

        suite = new TestValidationSuite(null);
        response = suite.validate();
        assertNotNull("testValidationSuite.validate(null) should result in an error", response.getError());
        System.out.println(response);

    }

}
