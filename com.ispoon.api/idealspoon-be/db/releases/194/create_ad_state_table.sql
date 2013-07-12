-- Creating a new entity table AdState (possible values: Pending, Active, Inactive)
-- http://jira.inpwrd.net/browse/DEV-7152


DROP TABLE IF EXISTS `ifb_ad_state`;
CREATE TABLE `ifb_ad_state` (
  `ad_state_id` int(11) NOT NULL AUTO_INCREMENT,
  `ad_state_name` varchar(255) NOT NULL,
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ad_state_id`),
  UNIQUE KEY `ad_state_name_UNIQUE` (`ad_state_name`)
);

INSERT INTO `ifb_ad_state` VALUES (1, 'Pending', current_timestamp());
INSERT INTO `ifb_ad_state` VALUES (2, 'Active', current_timestamp());
INSERT INTO `ifb_ad_state` VALUES (3, 'Inactive', current_timestamp());
INSERT INTO `ifb_ad_state` VALUES (4, 'Must Redeploy', current_timestamp());


ALTER TABLE `ifb_ad` ADD COLUMN `ad_state_id` INTEGER NOT NULL DEFAULT 1 AFTER `create_timestamp`, ADD INDEX fk_ifb_ad_state_idx(`ad_state_id`);
ALTER TABLE `ifb_ad` ADD CONSTRAINT `fk_ifb_ad_state` FOREIGN KEY `fk_ifb_ad_state` (`ad_state_id`) REFERENCES `ifb_ad_state` (`ad_state_id`);


