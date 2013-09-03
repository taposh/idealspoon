CREATE DATABASE  IF NOT EXISTS `ispoondb_new` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ispoondb_new`;
-- MySQL dump 10.13  Distrib 5.5.32, for debian-linux-gnu (i686)
--
-- Host: 127.0.0.1    Database: ispoondb_new
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_address`
--

LOCK TABLES `isp_address` WRITE;
/*!40000 ALTER TABLE `isp_address` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_ambience`
--

LOCK TABLES `isp_ambience` WRITE;
/*!40000 ALTER TABLE `isp_ambience` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_cusine`
--

LOCK TABLES `isp_cusine` WRITE;
/*!40000 ALTER TABLE `isp_cusine` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_restaurant`
--

LOCK TABLES `isp_restaurant` WRITE;
/*!40000 ALTER TABLE `isp_restaurant` DISABLE KEYS */;
INSERT INTO `isp_restaurant` VALUES (1,'Test',NULL),(2,'Test2',NULL);
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_state`
--

LOCK TABLES `isp_state` WRITE;
/*!40000 ALTER TABLE `isp_state` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isp_user`
--

LOCK TABLES `isp_user` WRITE;
/*!40000 ALTER TABLE `isp_user` DISABLE KEYS */;
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

-- Dump completed on 2013-09-02 21:41:58
