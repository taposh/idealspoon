-- This script drop-creates the "ifb_ad_size" table and inserts default values

DROP TABLE IF EXISTS `ifb_ad_size`;
  
CREATE TABLE `ifb_ad_size` (
  `ad_size_id` int(11) NOT NULL AUTO_INCREMENT,
  `ad_size_name` varchar(255) NOT NULL,
  `width`   int NOT NULL,
  `height`  int NOT NULL,
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ad_size_id`),
  UNIQUE KEY `ad_size_name_UNIQUE` (`ad_size_name`)
);

INSERT INTO `ifb_ad_size` (ad_size_id, ad_size_name, width, height, create_timestamp) VALUES
  (1, '728x90', 728, 90, current_timestamp()),
  (2, '300x250', 300, 250, current_timestamp()),
  (3, '160x600', 160, 600, current_timestamp()),
  (4, '970x90 (Billboard)', 970, 90, current_timestamp()),
  (5, '300x600', 300, 600, current_timestamp()),
  (6, '300x860 (Tower)', 300, 860, current_timestamp()),
  (7, '600x300 (Stories)', 600, 300, current_timestamp()),
  (8, '640x480', 640, 480, current_timestamp()),
  (9, '320x50', 320, 50, current_timestamp());




