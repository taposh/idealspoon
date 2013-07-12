
-- Altering
-- databse "ifbrands"
-- table "ifb_feed_story"
-- adding fields: update_source, update_timestamp, ignore_bot
ALTER TABLE ifb_feed_story ADD COLUMN `update_source` VARCHAR(256) AFTER `dp_story_id`;
ALTER TABLE ifb_feed_story ADD COLUMN `update_timestamp` TIMESTAMP AFTER `create_timestamp`;
ALTER TABLE ifb_feed_story ADD COLUMN `ignore_bot` TINYINT(1) DEFAULT 0 AFTER `update_source`;
CREATE INDEX update_source_index ON ifb_feed_story(update_source(100));