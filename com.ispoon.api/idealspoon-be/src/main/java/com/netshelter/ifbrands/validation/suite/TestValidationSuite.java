package com.netshelter.ifbrands.validation.suite;

import com.netshelter.ifbrands.DetailedError;
import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.campaign.FeedStory;
import com.netshelter.ifbrands.api.model.campaign.ObjectTypeEnum;
import com.netshelter.ifbrands.api.model.campaign.Tracking;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.service.AdService;
import com.netshelter.ifbrands.api.service.CampaignService;
import com.netshelter.ifbrands.api.service.FeedService;
import com.netshelter.ifbrands.api.service.TrackingService;
import com.netshelter.ifbrands.validation.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dmitriy T
 */
public class TestValidationSuite
    extends ValidationSuite
{

    private CampaignService campaignService = (CampaignService)getBean("campaignService");
    private AdService adService             = (AdService)getBean("adService");
    private FeedService feedService         = (FeedService)getBean("feedService");
    private TrackingService trackingService = (TrackingService)getBean("trackingService");

    private Integer campaignId              = null; // entered during construction
    private Campaign campaign               = null; // resolved on validate
    private List<Ad> adList                 = null; // resolved on validate
    private List<FeedStory> feedStoryList   = null; // resolved on validate


    public TestValidationSuite(Integer campaignId)
    {
        this.campaignId = campaignId;
    }

    @Override
    public ValidationResponse validate()
    {
        ValidationResponse response = new ValidationResponse();
        if (campaignId != null)
        {
            try {
                campaign = campaignService.getCampaign(campaignId);
                if (campaign != null)
                {
                    adList = (List)adService.getAds(null, null, null, null, Collections.singleton(campaign.getId()));
                    List<Integer> feedIds = new ArrayList<Integer>();
                    for (Ad ad : adList)
                    {
                        List<Tracking> trackingList =
                                trackingService.getByAny(null, ObjectTypeEnum.Ad, ad.getId(), null, null);
                        System.err.println("Ad["+ad.getId()+"]: "+ trackingList.size());
                        // TODO: Validate tracking entries found...
                    }
                }
                else
                {
                    response.setError(new DetailedError("Campaign not found by id: " + campaignId, null, null, null, null));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                response.setError(new DetailedError(
                        "Exception during validation suite execution",
                        e.getClass().getCanonicalName(),
                        e.getMessage(),
                        e.getStackTrace(),
                        null));
            }
        }
        else
        {
            response.setError(new DetailedError("Campaign id can not be null", null, null, null, null));
        }
        return response;
    }
}
