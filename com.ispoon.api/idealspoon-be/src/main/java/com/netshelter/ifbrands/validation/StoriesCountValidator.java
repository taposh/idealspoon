package com.netshelter.ifbrands.validation;

/**
 * Validates the count of the stories in specific campaign...
 * if (minStoriesCount < 0) do not validate min
 * if (axnStoriesCount < 0) do not validate max
 *
 * @author Dmitriy T
 */
public class StoriesCountValidator
    extends Validator
{

    private Integer campaignId      = null;
    private int minStoriesCount     = -1;
    private int maxStoriesCount     = -1;

    public StoriesCountValidator(Integer campaignId, int minStoriesCount, int maxStoriesCount)
    {
        this.campaignId         = campaignId;
        this.minStoriesCount    = minStoriesCount;
        this.maxStoriesCount    = maxStoriesCount;
    }

    @Override
    public boolean validate()
    {

        // TODO: At this point we check how many stories there are in a given campaign, and then validate the count

        return false;
    }
}
