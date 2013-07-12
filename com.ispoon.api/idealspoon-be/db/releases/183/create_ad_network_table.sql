CREATE TABLE `ifb_ad_network` (
  `ad_network_id` int(11) NOT NULL AUTO_INCREMENT,
  `ad_network_name` varchar(255) NOT NULL,
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ad_network_id`),
  UNIQUE KEY `ad_network_name_UNIQUE` (`ad_network_name`)
);

#add network id column
ALTER TABLE ifb_ad ADD COLUMN `ad_network_id` int(11) DEFAULT 0 AFTER `feed_id`;