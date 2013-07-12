-- This update script removes the "ON UPDATE CREATE_TIMESTAMP" from create_timestamp column
-- Then updates the ifb_feed_story with correct create_timestamp value

ALTER TABLE ifb_feed DROP COLUMN feed_url;