-- Altering table "ifb_ad" adding a new field ad_size_id
ALTER TABLE `ifb_ad` ADD COLUMN `ad_size_id` INTEGER NOT NULL DEFAULT 0 AFTER `product_destination`;