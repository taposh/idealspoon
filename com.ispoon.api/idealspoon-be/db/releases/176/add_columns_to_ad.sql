
-- Altering
-- databse "ifbrands"
-- table "ifb_ad"
-- adding fields: product_name and product_destination
ALTER TABLE ifb_ad ADD COLUMN `product_name`         VARCHAR(255) AFTER `ad_name`;
ALTER TABLE ifb_ad ADD COLUMN `product_destination`  VARCHAR(2048) AFTER `product_name`;