package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.campaign.ObjectTypeEnum;
import com.netshelter.ifbrands.api.model.campaign.Tracking;
import com.netshelter.ifbrands.api.model.campaign.TrackingSetEnum;
import com.netshelter.ifbrands.api.model.campaign.TrackingTypeEnum;
import com.netshelter.ifbrands.validation.ValidationResponse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitriy T
 */
public interface TrackingService
{

    /**
     * Clears cache: ehcache "ifb.mvc.tracking"
     */
    public void flushCache();

    /**
     * Creates a new Tracking entity and returns the object
     * @param objectType
     * @param objectId
     * @param trackingSet
     * @param trackingType
     * @param textValue
     * @return
     */
    public Tracking create(ObjectTypeEnum objectType, Integer objectId,
                                   TrackingSetEnum trackingSet,
                                   TrackingTypeEnum trackingType,
                                   String textValue);

    /**
     * All parameters are optional (null = not to include in query criteria)
     * @param trackingId
     * @param objectType
     * @param objectId
     * @param trackingSet
     * @param trackingType
     * @return
     */
    public List<Tracking> getByAny(Integer trackingId,
                                           ObjectTypeEnum objectType,
                                           Integer objectId,
                                           TrackingSetEnum trackingSet,
                                           TrackingTypeEnum trackingType);

    /**
     * Returns a Tracking object by id if not found - null
     * @param trackingId
     * @return - Tracking object if found
     */
    public Tracking get(Integer trackingId);

    /**
     * All parameters except trackingId are optional (null = not to update)
     * @param trackingId
     * @param objectType
     * @param objectId
     * @param trackingSet
     * @param
     * @param textValue
     * @return - Updated Tracking object
     */
    public Tracking update(Integer trackingId,
                                           ObjectTypeEnum objectType,
                                           Integer objectId,
                                           TrackingSetEnum trackingSet,
                                           TrackingTypeEnum trackingType,
                                           String textValue);

    /**
     * Removes the Tracking entity returns true if successful
     * @param trackingId
     * @return
     */
    public boolean delete(Integer trackingId);

    /**
     * Returns a list of all transformed Tracking objects constrained by Ids,
     * if the trackingIds is null or empty - returns all available entities
     * @param trackingIds
     * @return
     */
    public List<Tracking> all( List<Integer> trackingIds );

    /**
     * Runs validation responses for provided tracking type / value entries:
     *
     * Entries in map: trackingType,textValue
     *
     * @param objects
     * @return
     */
    public List<ValidationResponse> validateTrackingEntries(List<Map> objects);
}
