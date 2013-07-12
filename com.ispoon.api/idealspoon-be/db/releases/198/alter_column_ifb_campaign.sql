-- This script updates the Campaign entity to include goal

ALTER TABLE ifb_campaign ADD COLUMN goal VARCHAR(1024);