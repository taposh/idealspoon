SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

USE `ifbrands` ;

ALTER TABLE `ifbrands`.`ifb_story_amplification` DROP FOREIGN KEY `fk_ifb_story_amplification_ifb_ad1` ;
ALTER TABLE `ifbrands`.`ifb_story_amplification` DROP COLUMN `ad_id` , ADD COLUMN `campaign_id` INT(11) NULL DEFAULT NULL  AFTER `last_amplification` , 
  ADD CONSTRAINT `fk_ifb_story_amplification_ifb_campaign1`
  FOREIGN KEY (`campaign_id` )
  REFERENCES `ifbrands`.`ifb_campaign` (`campaign_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_ifb_story_amplification_ifb_campaign1_idx` (`campaign_id` ASC) 
, DROP INDEX `fk_ifb_story_amplification_ifb_ad1_idx` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;