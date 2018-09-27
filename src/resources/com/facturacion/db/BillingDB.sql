CREATE DATABASE  IF NOT EXISTS `BillingDB` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci */;
USE `BillingDB`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: BillingDB
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.34-MariaDB

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
-- Table structure for table `BILL`
--

DROP TABLE IF EXISTS `BILL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BILL` (
  `BILL_NUMBER` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Bill unique number',
  `CLIENT_ID` bigint(11) NOT NULL COMMENT 'Client id FK',
  `DATE` date NOT NULL COMMENT 'Bill generated date',
  `TOTAL_PRICE` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Bill total value',
  `DISCOUNT` double NOT NULL DEFAULT '0' COMMENT 'Discount percentage',
  `STATUS` varchar(1) COLLATE latin1_spanish_ci NOT NULL DEFAULT 'P' COMMENT 'Bill status',
  PRIMARY KEY (`BILL_NUMBER`),
  KEY `CLIENT_ID_FK` (`CLIENT_ID`),
  KEY `STATUS_CODE_FK` (`STATUS`),
  CONSTRAINT `CLIENT_ID_FK` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`),
  CONSTRAINT `STATUS_CODE_FK` FOREIGN KEY (`STATUS`) REFERENCES `STATUS` (`CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci COMMENT='Bill data';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BILL_HAS_SERVICES`
--

DROP TABLE IF EXISTS `BILL_HAS_SERVICES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BILL_HAS_SERVICES` (
  `BILL_CODE` int(11) NOT NULL COMMENT 'Client identification FK',
  `SERVICE_CODE` int(11) NOT NULL COMMENT 'Service id FK',
  `QUANTITY` int(11) NOT NULL COMMENT 'Cantidad del servicio',
  `TOTAL` int(11) NOT NULL COMMENT 'Precio total del servicio por cantidad',
  KEY `BILL_CODE_FK` (`BILL_CODE`),
  KEY `SERVICE_CODE_FK` (`SERVICE_CODE`),
  CONSTRAINT `BILL_CODE_FK` FOREIGN KEY (`BILL_CODE`) REFERENCES `BILL` (`BILL_NUMBER`),
  CONSTRAINT `SERVICE_CODE_FK` FOREIGN KEY (`SERVICE_CODE`) REFERENCES `SERVICE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci COMMENT='Bill and service related data';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CLIENT`
--

DROP TABLE IF EXISTS `CLIENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT` (
  `ID` bigint(20) NOT NULL COMMENT 'Client identification',
  `NAME` varchar(50) COLLATE latin1_spanish_ci NOT NULL COMMENT 'Client name',
  `ADDRESS` varchar(100) COLLATE latin1_spanish_ci DEFAULT NULL COMMENT 'Client home address',
  `PHONE` int(11) DEFAULT NULL COMMENT 'Client phone number',
  `EMAIL` varchar(50) COLLATE latin1_spanish_ci DEFAULT NULL COMMENT 'Client email',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci COMMENT='Client data';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SERVICE`
--

DROP TABLE IF EXISTS `SERVICE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SERVICE` (
  `CODE` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Service id',
  `NAME` varchar(100) COLLATE latin1_spanish_ci NOT NULL COMMENT 'Service name',
  `PRICE` bigint(20) NOT NULL COMMENT 'Service price',
  `DESCRIPTION` varchar(250) COLLATE latin1_spanish_ci DEFAULT NULL COMMENT 'Service description',
  PRIMARY KEY (`CODE`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci COMMENT='Service data';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `STATUS`
--

DROP TABLE IF EXISTS `STATUS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `STATUS` (
  `CODE` varchar(1) COLLATE latin1_spanish_ci NOT NULL COMMENT 'Status id',
  `DESCRIPTION` varchar(100) COLLATE latin1_spanish_ci NOT NULL COMMENT 'Status description',
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci COMMENT='Bill status data';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-26 21:48:19
