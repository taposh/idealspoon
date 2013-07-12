SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

USE `ifbrands` ;

ALTER TABLE `ifbrands`.`ifb_user_state` CHANGE COLUMN `create_timestamp` `create_timestamp` TIMESTAMP NOT NULL  ;

ALTER TABLE `ifbrands`.`ifb_user_value` CHANGE COLUMN `create_timestamp` `create_timestamp` TIMESTAMP NOT NULL  ;

ALTER TABLE `ifbrands`.`ifb_campaign` CHANGE COLUMN `campaign_type_id` `campaign_type_id` INT(11) NOT NULL  AFTER `campaign_id` , CHANGE COLUMN `campaign_status_id` `campaign_status_id` INT(11) NOT NULL  AFTER `campaign_type_id` , CHANGE COLUMN `create_timestamp` `create_timestamp` TIMESTAMP NOT NULL  
, ADD UNIQUE INDEX `campaign_key_UNIQUE` (`campaign_key` ASC) ;

ALTER TABLE `ifbrands`.`ifb_campaign_type` CHANGE COLUMN `create_timestamp` `create_timestamp` TIMESTAMP NOT NULL  
, ADD UNIQUE INDEX `campaign_type_name_UNIQUE` (`campaign_type_name` ASC) ;

ALTER TABLE `ifbrands`.`ifb_campaign_status` CHANGE COLUMN `create_timestamp` `create_timestamp` TIMESTAMP NOT NULL  
, ADD UNIQUE INDEX `campaign_status_name_UNIQUE` (`campaign_status_name` ASC) ;

ALTER TABLE `ifbrands`.`ifb_ad` ADD COLUMN `ad_type_id` INT(11) NOT NULL  AFTER `ad_id` , ADD COLUMN `ad_status_id` INT(11) NOT NULL  AFTER `ad_type_id` , ADD COLUMN `campaign_id` INT(11) NOT NULL  AFTER `ad_status_id` , ADD COLUMN `feed_id` INT(11) NOT NULL  AFTER `campaign_id` , ADD COLUMN `ad_key` VARCHAR(255) NOT NULL  AFTER `feed_id` , ADD COLUMN `ad_name` VARCHAR(255) NOT NULL  AFTER `ad_key` , ADD COLUMN `create_timestamp` TIMESTAMP NOT NULL  AFTER `ad_name` , 
  ADD CONSTRAINT `fk_ifb_ad_ifb_campaign1`
  FOREIGN KEY (`campaign_id` )
  REFERENCES `ifbrands`.`ifb_campaign` (`campaign_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_ifb_ad_ifb_feed1`
  FOREIGN KEY (`feed_id` )
  REFERENCES `ifbrands`.`ifb_feed` (`feed_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_ifb_ad_ifb_ad_type1`
  FOREIGN KEY (`ad_type_id` )
  REFERENCES `ifbrands`.`ifb_ad_type` (`ad_type_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_ifb_ad_ifb_ad_status1`
  FOREIGN KEY (`ad_status_id` )
  REFERENCES `ifbrands`.`ifb_ad_status` (`ad_status_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_ifb_ad_ifb_campaign1_idx` (`campaign_id` ASC) 
, ADD INDEX `fk_ifb_ad_ifb_feed1_idx` (`feed_id` ASC) 
, ADD INDEX `fk_ifb_ad_ifb_ad_type1_idx` (`ad_type_id` ASC) 
, ADD UNIQUE INDEX `ad_key_UNIQUE` (`ad_key` ASC) 
, ADD INDEX `fk_ifb_ad_ifb_ad_status1_idx` (`ad_status_id` ASC) ;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_ad_type` (
  `ad_type_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `ad_type_name` VARCHAR(255) NOT NULL ,
  `create_timestamp` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`ad_type_id`) ,
  UNIQUE INDEX `ad_type_name_UNIQUE` (`ad_type_name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_feed` (
  `feed_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `campaign_id` INT(11) NOT NULL ,
  `ordering` ENUM('ORDINAL','RANDOM','AUTO') NOT NULL ,
  `feed_key` VARCHAR(255) NOT NULL ,
  `feed_name` VARCHAR(255) NOT NULL ,
  `last_modified` DATETIME NOT NULL ,
  `create_timestamp` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`feed_id`) ,
  INDEX `fk_ifb_feed_ifb_campaign1_idx` (`campaign_id` ASC) ,
  UNIQUE INDEX `feed_key_UNIQUE` (`feed_key` ASC) ,
  CONSTRAINT `fk_ifb_feed_ifb_campaign1`
    FOREIGN KEY (`campaign_id` )
    REFERENCES `ifbrands`.`ifb_campaign` (`campaign_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_feed_story` (
  `feed_story_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `feed_id` INT(11) NOT NULL ,
  `feed_story_status_id` INT(11) NOT NULL ,
  `ordinal` INT(11) NOT NULL ,
  `dp_story_id` INT(11) NOT NULL ,
  `create_timestamp` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`feed_story_id`) ,
  INDEX `fk_ifb_feed_story_ifb_feed_story_status1_idx` (`feed_story_status_id` ASC) ,
  INDEX `fk_ifb_feed_story_ifb_feed1_idx` (`feed_id` ASC) ,
  UNIQUE INDEX `feed_story_UNIQUE` (`feed_id` ASC, `dp_story_id` ASC),
  CONSTRAINT `fk_ifb_feed_story_ifb_feed_story_status1`
    FOREIGN KEY (`feed_story_status_id` )
    REFERENCES `ifbrands`.`ifb_feed_story_status` (`feed_story_status_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ifb_feed_story_ifb_feed1`
    FOREIGN KEY (`feed_id` )
    REFERENCES `ifbrands`.`ifb_feed` (`feed_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_feed_story_status` (
  `feed_story_status_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `feed_story_status_name` VARCHAR(255) NOT NULL ,
  `create_timestamp` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`feed_story_status_id`) ,
  UNIQUE INDEX `feed_story_status_name_UNIQUE` (`feed_story_status_name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_ad_status` (
  `ad_status_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `ad_status_name` VARCHAR(255) NOT NULL ,
  `create_timestamp` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`ad_status_id`) ,
  UNIQUE INDEX `ad_status_name_UNIQUE` (`ad_status_name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
