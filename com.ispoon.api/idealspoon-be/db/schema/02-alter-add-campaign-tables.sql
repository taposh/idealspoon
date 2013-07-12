SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

USE `ifbrands` ;

-- -----------------------------------------------------
-- Table `ifbrands`.`ifb_campaign_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ifbrands`.`ifb_campaign_type` ;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_campaign_type` (
  `campaign_type_id` INT NOT NULL AUTO_INCREMENT ,
  `campaign_type_name` VARCHAR(255) NOT NULL ,
  `create_timestamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`campaign_type_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ifbrands`.`ifb_campaign_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ifbrands`.`ifb_campaign_status` ;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_campaign_status` (
  `campaign_status_id` INT NOT NULL AUTO_INCREMENT ,
  `campaign_status_name` VARCHAR(255) NOT NULL ,
  `create_timestamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`campaign_status_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ifbrands`.`ifb_campaign`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ifbrands`.`ifb_campaign` ;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_campaign` (
  `campaign_id` INT NOT NULL AUTO_INCREMENT ,
  `campaign_key` VARCHAR(255) NOT NULL ,
  `campaign_name` VARCHAR(255) NOT NULL ,
  `user_key` VARCHAR(255) NOT NULL ,
  `dp_brand_id` INT NOT NULL ,
  `start_date` DATE NOT NULL ,
  `stop_date` DATE NOT NULL ,
  `keywords` VARCHAR(1024) NOT NULL ,
  `campaign_enabled` TINYINT(1) NOT NULL ,
  `campaign_type_id` INT NOT NULL ,
  `campaign_status_id` INT NOT NULL ,
  `create_timestamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`campaign_id`) ,
  INDEX `fk_ifb_campaign_ifb_campaign_status1_idx1` (`campaign_status_id` ASC) ,
  INDEX `fk_ifb_campaign_ifb_campaign_type1_idx1` (`campaign_type_id` ASC) ,
  CONSTRAINT `fk_ifb_campaign_ifb_campaign_status1`
    FOREIGN KEY (`campaign_status_id` )
    REFERENCES `ifbrands`.`ifb_campaign_status` (`campaign_status_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ifb_campaign_ifb_campaign_type1`
    FOREIGN KEY (`campaign_type_id` )
    REFERENCES `ifbrands`.`ifb_campaign_type` (`campaign_type_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- ALTERS Create relationships to campaign table and future ad table
-- -----------------------------------------------------

ALTER TABLE `ifbrands`.`ifb_story_amplification` CHANGE COLUMN `story_amplification_id` `story_amplification_id` INT(11) NOT NULL AUTO_INCREMENT  ;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_ad` (
  `ad_id` INT(11) NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`ad_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

ALTER TABLE `ifbrands`.`ifb_story_amplification` ADD COLUMN `ad_id` INT(11) NULL  AFTER `last_amplification` , 
  ADD CONSTRAINT `fk_ifb_story_amplification_ifb_ad1`
  FOREIGN KEY (`ad_id` )
  REFERENCES `ifbrands`.`ifb_ad` (`ad_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_ifb_story_amplification_ifb_ad1_idx` (`ad_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
