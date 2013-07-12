-- This script updates the Campaign entity to include productName, callToAction, and logoLocation

ALTER TABLE ifb_campaign ADD COLUMN product_name VARCHAR(255);
ALTER TABLE ifb_campaign ADD COLUMN product_destination VARCHAR(2048);
ALTER TABLE ifb_campaign ADD COLUMN call_to_action VARCHAR(255);
ALTER TABLE ifb_campaign ADD COLUMN logo_location VARCHAR(2048);