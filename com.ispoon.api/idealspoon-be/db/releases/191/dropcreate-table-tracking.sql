-- For more information please see "DEV-7075: Create Back-end WS DB Support for Ad-level Tracking Management"
-- http://jira.netshelter.net/browse/DEV-7075

DROP TABLE IF EXISTS `ifb_campaign_tracking`;
DROP TABLE IF EXISTS `ifb_tracking`;

CREATE TABLE `ifb_tracking` (
  `tracking_id` int(11) NOT NULL AUTO_INCREMENT,
  `object_id` int(11) DEFAULT 0,
  `object_type` varchar(255) NOT NULL,
  `tracking_set` varchar(255) NOT NULL,
  `tracking_type` varchar(255) NOT NULL,
  `text_value` text,
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`tracking_id`)
);

