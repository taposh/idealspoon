
-- This is a fix for the existing entries into ifb_feed_story table
UPDATE ifb_feed_story SET update_timestamp = create_timestamp WHERE update_timestamp = '0000-00-00 00:00:00';