CREATE DATABASE  IF NOT EXISTS `ispoondb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ispoondb`;
-- MySQL dump 10.13  Distrib 5.5.32, for debian-linux-gnu (i686)
--
-- Host: 127.0.0.1    Database: ispoondb
-- ------------------------------------------------------
-- Server version	5.5.32-0ubuntu0.12.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `isp_address`
--

DROP TABLE IF EXISTS `isp_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `isp_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address_one` varchar(255) DEFAULT NULL,
  `address_two` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state_id` int(11) DEFAULT NULL,
  `zip_code` varchar(45) DEFAULT NULL,
  `latitude` decimal(10,0) DEFAULT NULL,
  `longitude` decimal(10,0) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_isp_address_1_idx` (`state_id`),
  CONSTRAINT `fk_isp_address_1` FOREIGN KEY (`state_id`) REFERENCES `isp_state` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_address`
--

LOCK TABLES `isp_address` WRITE;
/*!40000 ALTER TABLE `isp_address` DISABLE KEYS */;
INSERT INTO `isp_address` VALUES (1,'19 N Market',NULL,'San Jose',1,'95113',37,-122,'4082806111',1),(2,'71 N. San Pedro Street',NULL,'San Jose',1,'95110',37,-122,'4089718523',1),(3,'55 S. 1st Street',NULL,'San Jose',1,'95113',37,-122,'4082886000',1),(4,'377 Santana Row',NULL,'San Jose',1,'95128',37,-122,'4082485400',1),(5,'100 W San Carlos St.',NULL,'San Jose',1,'95113',37,-122,'4082784555',1),(6,'71 E. San Fernando Street',NULL,'San Jose',1,'95113',37,-122,'4082938482',1),(7,'99 South 1st Street',NULL,'San Jose',1,'95113',37,-122,'4082924300',1),(8,'1011 Blossom Hill Rd.',NULL,'San Jose',1,'95123',37,-122,'4082666602',1),(9,'39935 Mission Blvd',NULL,'Fremont',1,'94539',37,-122,'5104401755',1),(10,'1300 Fillmore Street',NULL,'San Francisco',1,'94115',37,-122,'4157717100',1);
/*!40000 ALTER TABLE `isp_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `isp_ambience`
--

DROP TABLE IF EXISTS `isp_ambience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `isp_ambience` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `dscr` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_ambience`
--

LOCK TABLES `isp_ambience` WRITE;
/*!40000 ALTER TABLE `isp_ambience` DISABLE KEYS */;
INSERT INTO `isp_ambience` VALUES (1,'Classy',NULL),(2,'Expensive',NULL),(3,'Kid Friendly',NULL),(4,'Excellent Service',NULL),(5,'Good Food',NULL),(6,'Pet Friendly',NULL),(7,'Formal Meetings',NULL),(8,'Hangout place',NULL),(9,'Noisy',NULL),(10,'Casual',NULL);
/*!40000 ALTER TABLE `isp_ambience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `isp_cusine`
--

DROP TABLE IF EXISTS `isp_cusine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `isp_cusine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `dscr` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_cusine`
--

LOCK TABLES `isp_cusine` WRITE;
/*!40000 ALTER TABLE `isp_cusine` DISABLE KEYS */;
INSERT INTO `isp_cusine` VALUES (1,'Asian Fusion','Asian Fusion'),(2,'Vietnamese','Vietnamese'),(3,'Indian','Indian'),(4,'American','American'),(5,'Thai','Thai'),(6,'Italian','Italian'),(7,'Afgani','Afgani'),(8,'Chinese','Chinese');
/*!40000 ALTER TABLE `isp_cusine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `isp_restaurant`
--

DROP TABLE IF EXISTS `isp_restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `isp_restaurant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `rating` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_restaurant`
--

LOCK TABLES `isp_restaurant` WRITE;
/*!40000 ALTER TABLE `isp_restaurant` DISABLE KEYS */;
INSERT INTO `isp_restaurant` VALUES (1,'19 Market','3'),(2,'71 Saint Peter Restaurant','4'),(3,'A Perfect Finish Wine Bar','5'),(4,'Amber India - Santana Row','4'),(5,'ARCADIA','3'),(6,'Azúcar Latin Bistro Mojito Bar & Lounge','4'),(7,'Billy Berk\'s','4'),(8,'1011 Blossom Hill Rd.','2'),(9,'Bijan Restaurant - Fremont','3'),(10,'1300 on Fillmore','2');
/*!40000 ALTER TABLE `isp_restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `isp_restaurant_address`
--

DROP TABLE IF EXISTS `isp_restaurant_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `isp_restaurant_address` (
  `restaurant_id` int(11) NOT NULL,
  `address_id` int(11) NOT NULL,
  PRIMARY KEY (`restaurant_id`,`address_id`),
  KEY `fk_restaurant_address_1_idx` (`restaurant_id`),
  KEY `fk_restaurant_address_2_idx` (`address_id`),
  CONSTRAINT `fk_restaurant_address_1` FOREIGN KEY (`restaurant_id`) REFERENCES `isp_restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_restaurant_address_2` FOREIGN KEY (`address_id`) REFERENCES `isp_address` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_restaurant_address`
--

LOCK TABLES `isp_restaurant_address` WRITE;
/*!40000 ALTER TABLE `isp_restaurant_address` DISABLE KEYS */;
INSERT INTO `isp_restaurant_address` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,3),(8,8),(9,5),(10,8);
/*!40000 ALTER TABLE `isp_restaurant_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `isp_restaurant_ambience`
--

DROP TABLE IF EXISTS `isp_restaurant_ambience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `isp_restaurant_ambience` (
  `restaurant_id` int(11) NOT NULL,
  `ambience_id` int(11) NOT NULL,
  PRIMARY KEY (`restaurant_id`,`ambience_id`),
  KEY `fk_isp_restaurant_ambience_1_idx` (`ambience_id`),
  KEY `fk_isp_restaurant_ambience_2_idx` (`restaurant_id`),
  CONSTRAINT `fk_isp_restaurant_ambience_1` FOREIGN KEY (`ambience_id`) REFERENCES `isp_ambience` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_isp_restaurant_ambience_2` FOREIGN KEY (`restaurant_id`) REFERENCES `isp_restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_restaurant_ambience`
--

LOCK TABLES `isp_restaurant_ambience` WRITE;
/*!40000 ALTER TABLE `isp_restaurant_ambience` DISABLE KEYS */;
INSERT INTO `isp_restaurant_ambience` VALUES (1,1),(4,1),(1,2),(3,2),(4,2),(1,3),(2,3),(3,3),(1,4),(2,4),(3,4),(1,5),(2,5),(4,5),(1,6);
/*!40000 ALTER TABLE `isp_restaurant_ambience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `isp_restaurant_cusine`
--

DROP TABLE IF EXISTS `isp_restaurant_cusine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `isp_restaurant_cusine` (
  `restaurant_id` int(11) NOT NULL,
  `cusine_id` int(11) NOT NULL,
  PRIMARY KEY (`restaurant_id`,`cusine_id`),
  KEY `fk_restaurant_cusine_1_idx` (`restaurant_id`),
  KEY `fk_restaurant_cusine_2_idx` (`cusine_id`),
  CONSTRAINT `fk_restaurant_cusine_1` FOREIGN KEY (`restaurant_id`) REFERENCES `isp_restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_restaurant_cusine_2` FOREIGN KEY (`cusine_id`) REFERENCES `isp_cusine` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_restaurant_cusine`
--

LOCK TABLES `isp_restaurant_cusine` WRITE;
/*!40000 ALTER TABLE `isp_restaurant_cusine` DISABLE KEYS */;
INSERT INTO `isp_restaurant_cusine` VALUES (1,1),(1,2),(2,3),(3,3),(4,4),(5,4),(6,4),(7,6),(8,7),(9,9),(10,9);
/*!40000 ALTER TABLE `isp_restaurant_cusine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `isp_state`
--

DROP TABLE IF EXISTS `isp_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `isp_state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `short_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_state`
--

LOCK TABLES `isp_state` WRITE;
/*!40000 ALTER TABLE `isp_state` DISABLE KEYS */;
INSERT INTO `isp_state` VALUES (1,'California','CA');
/*!40000 ALTER TABLE `isp_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `isp_user`
--

DROP TABLE IF EXISTS `isp_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `isp_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `username_canonical` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email_canonical` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `locked` tinyint(1) NOT NULL,
  `expired` tinyint(1) NOT NULL,
  `expires_at` datetime DEFAULT NULL,
  `confirmation_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_requested_at` datetime DEFAULT NULL,
  `roles` longtext COLLATE utf8_unicode_ci NOT NULL COMMENT '(DC2Type:array)',
  `credentials_expired` tinyint(1) NOT NULL,
  `credentials_expire_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_6AB0F2C192FC23A8` (`username_canonical`),
  UNIQUE KEY `UNIQ_6AB0F2C1A0D96FBF` (`email_canonical`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_user`
--

LOCK TABLES `isp_user` WRITE;
/*!40000 ALTER TABLE `isp_user` DISABLE KEYS */;
INSERT INTO `isp_user` VALUES (1,'taposh','taposh','taposh@idealspoon.com','',0,'','abc123',NULL,0,0,NULL,NULL,NULL,'',0,NULL);
/*!40000 ALTER TABLE `isp_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-09-08 12:03:20
