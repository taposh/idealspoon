package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.campaign.AdNetwork;

import java.util.List;

/**
 * @author Dmitriy T
 */
public interface AdNetworkService
{
    /**
     * Creates an IfbAdNetwork entity and returns a transformed AdNetwork object
     *
     * @param adNetworkName
     * @return
     */
    public AdNetwork create( String adNetworkName);

    /**
     * Returns a transformed AdNetwork object
     *
     * @param id
     * @return
     */
    public AdNetwork get( Integer id );

    /**
     * Updates the IfbAdNetwork entity (found by AdNetworkId) with provided values (all optional)
     *
     * @param adNetworkId
     * @param adNetworkName - optional
     * @return
     */
    public AdNetwork update( Integer adNetworkId, String adNetworkName );

    /**
     * Removes an existing IfbAdNetwork entity by id
     *
     * @param id
     * @return - returns true if the entity was removed
     */
    public boolean delete( Integer id );

    /**
     * Returns a list of all transformed AdNetwork objects constrained by Ids,
     * if the AdNetworkIds is null or empty - returns all available entities
     *
     * @param adNetworkIds
     * @return
     */
    public List<AdNetwork> all( List<Integer> adNetworkIds );
}
