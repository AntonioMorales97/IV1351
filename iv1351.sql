-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: iv1351
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.18.10.1

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
-- Current Database: `iv1351`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `iv1351` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `iv1351`;

--
-- Table structure for table `biljettkategori`
--

DROP TABLE IF EXISTS `biljettkategori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `biljettkategori` (
  `namn` varchar(32) NOT NULL,
  UNIQUE KEY `namn` (`namn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biljettkategori`
--

LOCK TABLES `biljettkategori` WRITE;
/*!40000 ALTER TABLE `biljettkategori` DISABLE KEYS */;
INSERT INTO `biljettkategori` VALUES ('barn'),('pensionär'),('vuxen');
/*!40000 ALTER TABLE `biljettkategori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `biljettpris`
--

DROP TABLE IF EXISTS `biljettpris`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `biljettpris` (
  `utställning` int(11) NOT NULL,
  `kategori` varchar(32) NOT NULL,
  `pris` double DEFAULT NULL,
  PRIMARY KEY (`utställning`,`kategori`),
  KEY `kategori` (`kategori`),
  CONSTRAINT `biljettpris_ibfk_1` FOREIGN KEY (`utställning`) REFERENCES `utställning` (`id`),
  CONSTRAINT `biljettpris_ibfk_2` FOREIGN KEY (`kategori`) REFERENCES `biljettkategori` (`namn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biljettpris`
--

LOCK TABLES `biljettpris` WRITE;
/*!40000 ALTER TABLE `biljettpris` DISABLE KEYS */;
INSERT INTO `biljettpris` VALUES (1,'barn',50),(1,'pensionär',50),(1,'vuxen',100),(2,'barn',70),(2,'pensionär',75),(2,'vuxen',120),(3,'barn',0),(3,'pensionär',0),(3,'vuxen',0);
/*!40000 ALTER TABLE `biljettpris` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `färg`
--

DROP TABLE IF EXISTS `färg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `färg` (
  `namn` varchar(32) NOT NULL,
  UNIQUE KEY `namn` (`namn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `färg`
--

LOCK TABLES `färg` WRITE;
/*!40000 ALTER TABLE `färg` DISABLE KEYS */;
INSERT INTO `färg` VALUES ('blå'),('grön'),('gul'),('röd');
/*!40000 ALTER TABLE `färg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guide`
--

DROP TABLE IF EXISTS `guide`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guide` (
  `personnr` varchar(10) NOT NULL,
  `fnamn` varchar(32) DEFAULT NULL,
  `enamn` varchar(32) DEFAULT NULL,
  `telefonnr` varchar(32) DEFAULT NULL,
  `epost` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`personnr`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guide`
--

LOCK TABLES `guide` WRITE;
/*!40000 ALTER TABLE `guide` DISABLE KEYS */;
INSERT INTO `guide` VALUES ('0510221344','Anna','Anka','0701234510','anna@hotmail.com'),('7510221344','Pelle','Anka','0701234567','mailainte@hotmail.com'),('8510221344','Kalle','Anka','0701234568','mailasnalla@hotmail.com'),('9510221344','Stina','Andersson','0701234569','stina@hotmail.com');
/*!40000 ALTER TABLE `guide` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `konstnär`
--

DROP TABLE IF EXISTS `konstnär`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `konstnär` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `namn` varchar(64) DEFAULT NULL,
  `nationalitet` varchar(32) DEFAULT NULL,
  `födelseår` int(11) DEFAULT NULL,
  `dödsår` int(11) DEFAULT NULL,
  `bild` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `konstnär`
--

LOCK TABLES `konstnär` WRITE;
/*!40000 ALTER TABLE `konstnär` DISABLE KEYS */;
INSERT INTO `konstnär` VALUES (1,'Pablo Picasso','Spanien',1881,1973,NULL),(2,'Leonardo Da Vinci','Italien',1452,1519,NULL),(3,'Fadil Galjic','Sverige',1955,NULL,NULL);
/*!40000 ALTER TABLE `konstnär` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `konstverk`
--

DROP TABLE IF EXISTS `konstverk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `konstverk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `namn` varchar(64) DEFAULT NULL,
  `bild` varchar(512) DEFAULT NULL,
  `årtal` varchar(32) DEFAULT NULL,
  `kategori` varchar(32) DEFAULT NULL,
  `konstnär` int(11) DEFAULT NULL,
  `ytbehov` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `kategori` (`kategori`),
  KEY `konstnär` (`konstnär`),
  CONSTRAINT `konstverk_ibfk_1` FOREIGN KEY (`kategori`) REFERENCES `konstverkskategori` (`namn`),
  CONSTRAINT `konstverk_ibfk_2` FOREIGN KEY (`konstnär`) REFERENCES `konstnär` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `konstverk`
--

LOCK TABLES `konstverk` WRITE;
/*!40000 ALTER TABLE `konstverk` DISABLE KEYS */;
INSERT INTO `konstverk` VALUES (2,'Don Quixote',NULL,NULL,'målning',1,2),(3,'Femme',NULL,NULL,'målning',1,4),(4,'Fredsduva',NULL,NULL,'målning',1,4),(5,'Hunden',NULL,NULL,'skulptur',1,5),(6,'Saxen',NULL,NULL,'skulptur',2,4),(7,'Mona Lisa',NULL,NULL,'målning',2,1),(8,'The Last Supper',NULL,NULL,'målning',2,2),(9,'Programmerings principer i Java',NULL,'2000','annat',3,3);
/*!40000 ALTER TABLE `konstverk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `konstverkskategori`
--

DROP TABLE IF EXISTS `konstverkskategori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `konstverkskategori` (
  `namn` varchar(32) NOT NULL,
  UNIQUE KEY `namn` (`namn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `konstverkskategori`
--

LOCK TABLES `konstverkskategori` WRITE;
/*!40000 ALTER TABLE `konstverkskategori` DISABLE KEYS */;
INSERT INTO `konstverkskategori` VALUES ('annat'),('målning'),('skulptur');
/*!40000 ALTER TABLE `konstverkskategori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `konstvisning`
--

DROP TABLE IF EXISTS `konstvisning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `konstvisning` (
  `konstverk` int(11) NOT NULL,
  `utställning` int(11) NOT NULL,
  PRIMARY KEY (`konstverk`,`utställning`),
  KEY `utställning` (`utställning`),
  CONSTRAINT `konstvisning_ibfk_1` FOREIGN KEY (`konstverk`) REFERENCES `konstverk` (`id`),
  CONSTRAINT `konstvisning_ibfk_2` FOREIGN KEY (`utställning`) REFERENCES `utställning` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `konstvisning`
--

LOCK TABLES `konstvisning` WRITE;
/*!40000 ALTER TABLE `konstvisning` DISABLE KEYS */;
INSERT INTO `konstvisning` VALUES (2,1),(3,1),(4,1),(5,1),(6,2),(7,2),(2,3),(3,3),(8,3),(9,3);
/*!40000 ALTER TABLE `konstvisning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produkt`
--

DROP TABLE IF EXISTS `produkt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produkt` (
  `EAN` varchar(32) NOT NULL,
  `produktkategori` varchar(32) DEFAULT NULL,
  `färg` varchar(32) DEFAULT NULL,
  `pris` double DEFAULT NULL,
  `storlek` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`EAN`),
  KEY `produktkategori` (`produktkategori`),
  KEY `färg` (`färg`),
  CONSTRAINT `produkt_ibfk_1` FOREIGN KEY (`produktkategori`) REFERENCES `produktkategori` (`namn`),
  CONSTRAINT `produkt_ibfk_2` FOREIGN KEY (`färg`) REFERENCES `färg` (`namn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produkt`
--

LOCK TABLES `produkt` WRITE;
/*!40000 ALTER TABLE `produkt` DISABLE KEYS */;
INSERT INTO `produkt` VALUES ('1','vykort',NULL,10,NULL),('2','tshirt','grön',100,'M'),('3','tshirt','blå',120,'L'),('4','nyckelring',NULL,20,NULL),('5','nyckelring',NULL,25,NULL),('6','tshirt',NULL,80,'S'),('7','vykort',NULL,15,NULL),('8','tshirt','gul',200,'M');
/*!40000 ALTER TABLE `produkt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produktkategori`
--

DROP TABLE IF EXISTS `produktkategori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produktkategori` (
  `namn` varchar(32) NOT NULL,
  UNIQUE KEY `namn` (`namn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produktkategori`
--

LOCK TABLES `produktkategori` WRITE;
/*!40000 ALTER TABLE `produktkategori` DISABLE KEYS */;
INSERT INTO `produktkategori` VALUES ('nyckelring'),('tshirt'),('vykort');
/*!40000 ALTER TABLE `produktkategori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produktkonst`
--

DROP TABLE IF EXISTS `produktkonst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produktkonst` (
  `konstid` int(11) NOT NULL,
  `EAN` varchar(32) NOT NULL,
  PRIMARY KEY (`konstid`,`EAN`),
  KEY `EAN` (`EAN`),
  CONSTRAINT `produktkonst_ibfk_1` FOREIGN KEY (`konstid`) REFERENCES `konstverk` (`id`),
  CONSTRAINT `produktkonst_ibfk_2` FOREIGN KEY (`EAN`) REFERENCES `produkt` (`EAN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produktkonst`
--

LOCK TABLES `produktkonst` WRITE;
/*!40000 ALTER TABLE `produktkonst` DISABLE KEYS */;
INSERT INTO `produktkonst` VALUES (2,'1'),(3,'2'),(2,'3'),(5,'4'),(7,'5'),(9,'6'),(2,'7'),(5,'8'),(9,'8');
/*!40000 ALTER TABLE `produktkonst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `språk`
--

DROP TABLE IF EXISTS `språk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `språk` (
  `namn` varchar(32) NOT NULL,
  UNIQUE KEY `namn` (`namn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `språk`
--

LOCK TABLES `språk` WRITE;
/*!40000 ALTER TABLE `språk` DISABLE KEYS */;
INSERT INTO `språk` VALUES ('engelska'),('spanska'),('svenska'),('tyska');
/*!40000 ALTER TABLE `språk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `språkkunskap`
--

DROP TABLE IF EXISTS `språkkunskap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `språkkunskap` (
  `guide` varchar(10) NOT NULL,
  `språk` varchar(32) NOT NULL,
  PRIMARY KEY (`guide`,`språk`),
  KEY `språk` (`språk`),
  CONSTRAINT `språkkunskap_ibfk_1` FOREIGN KEY (`guide`) REFERENCES `guide` (`personnr`),
  CONSTRAINT `språkkunskap_ibfk_2` FOREIGN KEY (`språk`) REFERENCES `språk` (`namn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `språkkunskap`
--

LOCK TABLES `språkkunskap` WRITE;
/*!40000 ALTER TABLE `språkkunskap` DISABLE KEYS */;
INSERT INTO `språkkunskap` VALUES ('0510221344','engelska'),('7510221344','engelska'),('8510221344','engelska'),('9510221344','engelska'),('7510221344','spanska'),('9510221344','spanska'),('7510221344','svenska'),('9510221344','svenska'),('7510221344','tyska'),('8510221344','tyska');
/*!40000 ALTER TABLE `språkkunskap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tur`
--

DROP TABLE IF EXISTS `tur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tur` (
  `guide` varchar(10) NOT NULL,
  `tid` time NOT NULL,
  `datum` date NOT NULL,
  `utställning` int(11) DEFAULT NULL,
  `språk` varchar(32) DEFAULT NULL,
  `längd` time DEFAULT NULL,
  PRIMARY KEY (`guide`,`tid`,`datum`),
  KEY `språk` (`språk`),
  KEY `utställning` (`utställning`),
  CONSTRAINT `tur_ibfk_1` FOREIGN KEY (`guide`) REFERENCES `guide` (`personnr`),
  CONSTRAINT `tur_ibfk_2` FOREIGN KEY (`språk`) REFERENCES `språk` (`namn`),
  CONSTRAINT `tur_ibfk_3` FOREIGN KEY (`utställning`) REFERENCES `utställning` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tur`
--

LOCK TABLES `tur` WRITE;
/*!40000 ALTER TABLE `tur` DISABLE KEYS */;
INSERT INTO `tur` VALUES ('0510221344','11:00:00','2018-11-25',2,'engelska','00:30:00'),('7510221344','10:00:00','2018-11-22',1,'engelska','00:30:00'),('7510221344','11:00:00','2018-11-22',1,'svenska','00:30:00'),('7510221344','12:00:00','2018-11-22',1,'tyska','00:30:00'),('7510221344','13:00:00','2018-11-22',1,'spanska','00:30:00'),('8510221344','10:00:00','2018-11-23',1,'engelska','00:30:00'),('8510221344','11:00:00','2018-11-23',1,'tyska','00:30:00'),('9510221344','10:00:00','2018-11-24',2,'engelska','00:30:00'),('9510221344','10:00:00','2018-12-01',3,'engelska','01:30:00'),('9510221344','12:00:00','2018-11-24',2,'svenska','00:30:00'),('9510221344','12:00:00','2018-12-01',3,'spanska','01:30:00'),('9510221344','14:00:00','2018-12-01',3,'tyska','01:30:00');
/*!40000 ALTER TABLE `tur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utställning`
--

DROP TABLE IF EXISTS `utställning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utställning` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `namn` varchar(64) DEFAULT NULL,
  `startdatum` date DEFAULT NULL,
  `slutdatum` date DEFAULT NULL,
  `yta` int(11) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utställning`
--

LOCK TABLES `utställning` WRITE;
/*!40000 ALTER TABLE `utställning` DISABLE KEYS */;
INSERT INTO `utställning` VALUES (1,'The Opening','2018-11-22','2018-11-29',500),(2,'The Second','2018-11-24','2018-12-01',1000),(3,'The December Opening','2018-12-01','2018-12-08',300);
/*!40000 ALTER TABLE `utställning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utställningskompetens`
--

DROP TABLE IF EXISTS `utställningskompetens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utställningskompetens` (
  `guide` varchar(10) NOT NULL,
  `utställning` int(11) NOT NULL,
  PRIMARY KEY (`guide`,`utställning`),
  KEY `utställning` (`utställning`),
  CONSTRAINT `utställningskompetens_ibfk_1` FOREIGN KEY (`guide`) REFERENCES `guide` (`personnr`),
  CONSTRAINT `utställningskompetens_ibfk_2` FOREIGN KEY (`utställning`) REFERENCES `utställning` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utställningskompetens`
--

LOCK TABLES `utställningskompetens` WRITE;
/*!40000 ALTER TABLE `utställningskompetens` DISABLE KEYS */;
INSERT INTO `utställningskompetens` VALUES ('7510221344',1),('8510221344',1),('9510221344',1),('0510221344',2),('9510221344',2),('9510221344',3);
/*!40000 ALTER TABLE `utställningskompetens` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-18 10:17:40
