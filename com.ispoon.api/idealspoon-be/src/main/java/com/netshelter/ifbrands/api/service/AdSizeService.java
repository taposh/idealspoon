package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.campaign.AdSize;

import java.util.List;

/**
 * User: Dmitriy T
 */
public interface AdSizeService
{
    /**
     * Creates an IfbAdSize entity and returns a transformed AdSize object
     *
     * @param adSizeName
     * @param width
     * @param height
     * @return
     */
    public AdSize create( String adSizeName, Integer width, Integer height );

    /**
     * Returns a transformed AdSize object
     *
     * @param id
     * @return
     */
    public AdSize get( Integer id );

    /**
     * Updates the IfbAdSize entity (found by adSizeId) with provided values (all optional)
     *
     * @param adSizeId
     * @param adSizeName - optional
     * @param width - optional
     * @param height - optional
     * @return
     */
    public AdSize update( Integer adSizeId, String adSizeName, Integer width, Integer height );

    /**
     * Removes an existing IfbAdSize entity by id
     *
     * @param id
     * @return - returns true if the entity was removed
     */
    public boolean delete( Integer id );

    /**
     * Returns a list of all transformed AdSize objects constrained by Ids,
     * if the adSizeIds is null or empty - returns all available entities
     *
     * @param adSizeIds
     * @return
     */
    public List<AdSize> all( List<Integer> adSizeIds );

}
