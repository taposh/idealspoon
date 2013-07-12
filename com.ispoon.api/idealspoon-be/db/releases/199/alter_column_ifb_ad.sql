---- This script updates the Ad entity to include template key for shorty template

ALTER TABLE ifb_ad ADD COLUMN template_key VARCHAR(255);

ALTER TABLE ifb_ad ADD INDEX idx_ifb_ad_template_key(template_key);

