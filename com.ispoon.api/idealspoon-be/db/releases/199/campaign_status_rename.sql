-- rename campaign status from LIVE / COMPLETE to ACTIVE / INACTIVE

update ifbrands.ifb_campaign_status set campaign_status_name = 'Active' where campaign_status_name = 'Live';
update ifbrands.ifb_campaign_status set campaign_status_name = 'Inactive' where campaign_status_name = 'Complete';

update ifbrandsdemo.ifb_campaign_status set campaign_status_name = 'Active' where campaign_status_name = 'Live';
update ifbrandsdemo.ifb_campaign_status set campaign_status_name = 'Inactive' where campaign_status_name = 'Complete';