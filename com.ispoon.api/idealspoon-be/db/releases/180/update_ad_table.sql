
-- Altering
-- databse "ifbrands"
-- table "ifb_ad"
-- adding fields: question
ALTER TABLE ifb_ad ADD COLUMN `question` VARCHAR(2048) AFTER `product_name`;