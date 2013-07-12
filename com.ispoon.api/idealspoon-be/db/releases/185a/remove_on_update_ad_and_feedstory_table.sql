-- This update script removes the "ON UPDATE CREATE_TIMESTAMP" from create_timestamp column
-- Then updates the ifb_feed_story with correct create_timestamp value

ALTER TABLE ifb_ad MODIFY COLUMN create_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE ifb_feed_story MODIFY COLUMN create_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

UPDATE ifb_feed_story SET create_timestamp = update_timestamp WHERE create_timestamp > update_timestamp;