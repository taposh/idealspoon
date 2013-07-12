-- For more information please see "DEV-5796: inPowered Back-end for Click Tracking"
-- http://jira.netshelter.net/browse/DEV-5796

DROP TABLE IF EXISTS `ifb_campaign_tracking`;

CREATE TABLE `ifb_campaign_tracking` (
  `campaign_tracking_id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_id` int(11) DEFAULT 0,
  `tracking_set` varchar(255) NOT NULL,
  `tracking_type` varchar(255) NOT NULL,
  `text_value` text,
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`campaign_tracking_id`)
);

