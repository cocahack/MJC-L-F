-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: webdb
-- ------------------------------------------------------
-- Server version	5.7.11-log

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
-- Table structure for table `faq`
--

DROP TABLE IF EXISTS `faq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faq` (
  `faq_no` int(11) NOT NULL AUTO_INCREMENT,
  `faq_title` varchar(45) NOT NULL,
  `faq_writer` varchar(20) NOT NULL,
  `faq_content` varchar(1000) NOT NULL,
  `faq_imgPath` varchar(1000) DEFAULT NULL,
  `faq_regitDate` datetime NOT NULL,
  `faq_hit` varchar(45) NOT NULL,
  PRIMARY KEY (`faq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faq`
--

LOCK TABLES `faq` WRITE;
/*!40000 ALTER TABLE `faq` DISABLE KEYS */;
INSERT INTO `faq` VALUES (1,'테스트','관리자','테스트','/uploadImg/faq/faq_No.1/0f92fe05ddd78dc599df4c80e955bc20_MfpSoyqB6KwAw4ciR5fcMvmUu3ZT4i.jpg;/uploadImg/faq/faq_No.1/1028_162924_CH7-1_HD 2012 프로야구.jpg;/uploadImg/faq/faq_No.1/123124.JPG;/uploadImg/faq/faq_No.1/8de588cebdfd71b7dcefefcdf067defe.jpg;/uploadImg/faq/faq_No.1/97.jpg;','2016-10-17 21:05:11','4'),(2,'테스트2','관리자','테스트2','/uploadImg/faq/faq_No.2/prada.jpg;/uploadImg/faq/faq_No.2/슬라이드바.JPG;','2016-10-17 21:06:33','2'),(3,'검색용','관리자','테스트','/uploadImg/faq/faq_No.3/marker.png;/uploadImg/faq/faq_No.3/슬라이드바.JPG;','2016-10-22 13:10:37','2'),(4,'JSON테스트','관리자','테스트중입니다. ㅎㅎ',NULL,'2016-11-09 19:33:50','1'),(5,'safasf','관리자','asfasfasf','','2016-11-20 15:21:26','0'),(6,'asfas','관리자','fasfasf','/uploadImg/faq/faq_No.6/BestHDWallpapersPack308_13_bender777post.jpg;','2016-11-20 15:21:34','1');
/*!40000 ALTER TABLE `faq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-14 11:28:12
