--  This script removes a column "campaign_id" in a table "ifb_feed"
--  the data structure was deprecated and is no longer used
ALTER TABLE ifb_feed
  DROP COLUMN campaign_id,
  DROP INDEX fk_ifb_feed_ifb_campaign1_idx,
  DROP FOREIGN KEY fk_ifb_feed_ifb_campaign1;

