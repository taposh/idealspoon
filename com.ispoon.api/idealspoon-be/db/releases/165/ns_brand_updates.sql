USE `ifbrands`;

update ifb_campaign set dp_brand_id = 1212 where dp_brand_id = 1191 and campaign_id > 1;
update ifb_story_amplification set dp_brand_id = 1212 where dp_brand_id = 1191 and story_amplification_id > 1;