--  This script creates data model for Notes entity
--  Create Notes Functionality for inPowered BE ( http://jira.netshelter.net/browse/DEV-7529 )

DROP TABLE IF EXISTS `ifb_notes`;

CREATE TABLE `ifb_notes` (
  `notes_id`   int(11) NOT NULL AUTO_INCREMENT,
  `campaign_id`   int(11) NOT NULL,
  `notes_text`     text,
  `user_key`        int(11) NOT NULL,
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_timestamp` timestamp NOT NULL,
  KEY `ifb_notes_user_key_idx`     (`user_key`),
  KEY `ifb_notes_campaign_id_idx`     (`campaign_id`),
  PRIMARY KEY (`notes_id`)
);
