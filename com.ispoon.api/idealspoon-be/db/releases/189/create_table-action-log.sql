--  This script creates data model for ActionLog entity
--  Create Log Fuctionality for inPowered BE ( http://jira.netshelter.net/browse/DEV-7044 )

DROP TABLE IF EXISTS `ifb_action_log`;

CREATE TABLE `ifb_action_log` (
  `action_log_id`   int(11) NOT NULL AUTO_INCREMENT,
  `action_type`     varchar(255) NOT NULL,
  `object_type`     varchar(255),
  `object_id`       int(11),
  `user_key`        int(11),
  `text_value`      text,
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `ifb_action_log_action_type_idx`   (`action_type`),
  KEY `ifb_action_log_user_key_idx`     (`user_key`),
  KEY `ifb_action_log_object_type_idx`  (`object_type`),
  KEY `ifb_action_log_object_id_idx`    (`object_id`),
  PRIMARY KEY (`action_log_id`)
);
