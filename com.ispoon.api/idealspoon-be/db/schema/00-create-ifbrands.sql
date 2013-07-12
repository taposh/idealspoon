SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `ifbrands` ;
CREATE SCHEMA IF NOT EXISTS `ifbrands` DEFAULT CHARACTER SET utf8 ;
USE `ifbrands` ;

-- -----------------------------------------------------
-- Table `ifbrands`.`ifb_user_state`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ifbrands`.`ifb_user_state` ;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_user_state` (
  `user_state_id` INT NOT NULL AUTO_INCREMENT ,
  `dp_brand_id` INT NOT NULL ,
  `user_key` VARCHAR(255) NOT NULL ,
  `last_login` DATETIME NOT NULL ,
  `create_timestamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`user_state_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ifbrands`.`ifb_story_amplification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ifbrands`.`ifb_story_amplification` ;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_story_amplification` (
  `story_amplification_id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `short_url_key` VARCHAR(16) NOT NULL ,
  `dp_brand_id` INT NOT NULL ,
  `dp_story_id` INT NOT NULL ,
  `dp_user_id` INT NOT NULL ,
  `type` VARCHAR(45) NOT NULL ,
  `created_amplification` DATETIME NOT NULL ,
  `last_amplification` DATETIME NOT NULL ,
  PRIMARY KEY (`story_amplification_id`) ,
  UNIQUE INDEX `short_url_key_UNIQUE` (`short_url_key` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ifbrands`.`ifb_user_value`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ifbrands`.`ifb_user_value` ;

CREATE  TABLE IF NOT EXISTS `ifbrands`.`ifb_user_value` (
  `user_value_id` INT NOT NULL AUTO_INCREMENT ,
  `user_key` VARCHAR(255) NOT NULL ,
  `key_name` VARCHAR(255) NOT NULL ,
  `value` VARCHAR(2000) NOT NULL ,
  `create_timestamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`user_value_id`) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
