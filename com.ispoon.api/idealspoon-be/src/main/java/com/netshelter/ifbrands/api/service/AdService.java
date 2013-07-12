package com.netshelter.ifbrands.api.service;

import java.util.Collection;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.campaign.AdState;
import org.joda.time.LocalDate;
import org.springframework.util.MultiValueMap;

import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.AdStatus;
import com.netshelter.ifbrands.api.model.campaign.AdType;

public interface AdService
{
    public static final String AD_CACHE = "ifb.mvc.ad";

    // Cache Services
    public void flushCache();

    ///////////////////
    // Ad Management //
    ///////////////////
    public Ad createAd( String key, String name, Integer typeId, Integer statusId, Integer feedId, Integer campaignId,
                        Integer adNetworkId,
                        Integer adSizeId, Integer adStateId,LocalDate start, LocalDate stop,
                        Integer actionUserKey, String templateKey);
    public boolean deleteAd( Integer adId );
    public Ad updateAd( Integer adId, String name, Integer typeId, Integer statusId,
                        Integer adNetworkId, Integer adSizeId, Integer adStateId,LocalDate start,
                        LocalDate stop, Integer actionUserKey, String templateKey);
    public Ad getAd( Integer adId );
    public Collection<Ad> getAds( Collection<Integer> adIds,
                                  Collection<String> adKeys,
                                  Collection<Integer> adTypeIds,
                                  Collection<Integer> adStatusId,
                                  Collection<Integer> campaignIds );
    public String getAdTag(Integer adId);

    ///////////////////////
    // AdType management //
    ///////////////////////
    public AdType createAdType( String name );
    public boolean deleteAdType( Integer adTypeId );
    public AdType getAdType( Integer adTypeId );
    public Collection<AdType> getAdTypes( Collection<Integer> adTypeIds );

    ///////////////////////
    // AdStatus management //
    ///////////////////////
    public AdStatus createAdStatus( String name );
    public boolean deleteAdStatus( Integer adStatusId );
    public AdStatus getAdStatus( Integer adStatusId );
    public Collection<AdStatus> getAdStatuses( Collection<Integer> adStatusIds );

    public AdState getAdState( Integer adStateId );
    public Collection<AdState> getAdStates( Collection<Integer> adStateIds );

    public Ad cloneAd( Integer adId, String name, Integer adTypeId, Integer adSizeId,
                       Integer adNetworkId, Integer actionUserKey, String templateKey );


    public MultiValueMap<Integer, Ad> getStoryContainers( Collection<Integer> storyIds, Collection<Integer> adIds, Collection<Integer> adTypeIds,
                                                          Collection<Integer> adStatusIds, Collection<Integer> campaignIds, Collection<Integer> campaignTypeIds,
                                                          Collection<Integer> campaignStatusIds, Collection<Integer> brandIds );

	
}
