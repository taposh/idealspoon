package com.netshelter.ifbrands.api.service;

import java.util.Collection;

import org.joda.time.LocalDate;

import com.netshelter.ifbrands.api.model.campaign.Campaign;
import com.netshelter.ifbrands.api.model.campaign.CampaignStatus;
import com.netshelter.ifbrands.api.model.campaign.CampaignType;
import org.springframework.web.bind.annotation.RequestParam;

public interface CampaignService
{
  public static final String CAMPAIGN_CACHE = "ifb.mvc.campaign";

  // Campaign Services
  public Campaign createCampaign( String campaignName, String userKey, Integer dpBrandId,
                                  LocalDate startDate, LocalDate stopDate,
                                  String keywords, Boolean campaignEnabled,
                                  Integer campaignStatusId, Integer campaignTypeId, String campaignKey,
                                  Integer feedId,
                                  String productName,
                                  String productDestination,
                                  String callToAction,
                                  String logoLocation,
                                  Integer actionUserKey,
                                  String goal);
  public Campaign createCampaign( String campaignName, String userKey, Integer dpBrandId,
                                  LocalDate startDate, LocalDate stopDate,
                                  String keywords, Boolean campaignEnabled,
                                  Integer campaignStatusId, Integer campaignTypeId, String campaignKey,
                                  String clientUserKey,
                                  Integer feedId,
                                  String productName,
                                  String productDestination,
                                  String callToAction,
                                  String logoLocation,
                                  Integer actionUserKey,
                                  String goal);
  public boolean deleteCampaign( Integer campaignId );
  public Campaign getCampaign( Integer campaignId );
  public Collection<Campaign> getCampaigns( Collection<Integer> ids );
  public Collection<Campaign> getCampaigns( Collection<Integer> campaignIds, Boolean enabled,
                                            Collection<Integer> brandIds, Collection<String> campaignKeys,
                                            Collection<Integer> campaignStatusIds, Collection<Integer> campaignTypeIds,
                                            Collection<String> userKeys );
  public Collection<Campaign> getCampaigns( Collection<Integer> campaignIds,
                                            Boolean enabled,
                                            Collection<Integer> brandIds,
                                            Collection<String>  campaignKeys,
                                            Collection<Integer> campaignStatusIds,
                                            Collection<Integer> campaignTypeIds,
                                            Collection<String>  userKeys,
                                            Collection<String>  clientUserKeys);
  public Campaign updateCampaign( Integer campaignId, String campaignName, String userKey,
                                  Integer dpBrandId, LocalDate startDate, LocalDate stopDate,
                                  String keywords, Boolean campaignEnabled,
                                  Integer campaginStatusId, Integer campaginTtypeId,
                                  Integer feedId,
                                  String productName,
                                  String productDestination,
                                  String callToAction,
                                  String logoLocation,
                                  Integer actionUserKey,
                                  String goal);
  public Campaign updateCampaign( Integer campaignId, String campaignName, String userKey,
                                  Integer dpBrandId, LocalDate startDate, LocalDate stopDate,
                                  String keywords, Boolean campaignEnabled,
                                  Integer campaginStatusId, Integer campaginTtypeId, String userClientKey,
                                  Integer feedId,
                                  String productName,
                                  String productDestination,
                                  String callToAction,
                                  String logoLocation,
                                  Integer actionUserKey,
                                  String goal);
  public Collection<String> getCampaignKeys( Collection<Integer> campaignIds );

  // CampaignStatus Services
  public CampaignStatus createCampaignStatus( String name );
  public boolean deleteCampaignStatus( Integer id );
  public CampaignStatus getCampaignStatus( Integer id );
  public Collection<CampaignStatus> getCampaignStatuses( Collection<Integer> ids );

  // CampaignType Services
  public CampaignType createCampaignType( String name );
  public boolean deleteCampaignType( Integer id );
  public CampaignType getCampaignType( Integer id );
  public Collection<CampaignType> getCampaignTypes( Collection<Integer> ids );
}
