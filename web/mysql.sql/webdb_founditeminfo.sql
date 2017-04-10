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
-- Table structure for table `founditeminfo`
--

DROP TABLE IF EXISTS `founditeminfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `founditeminfo` (
  `found_no` int(11) NOT NULL AUTO_INCREMENT,
  `st_num` char(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `kakao` varchar(45) DEFAULT NULL,
  `found_date` date NOT NULL,
  `found_place` varchar(100) NOT NULL,
  `found_classify` varchar(10) NOT NULL,
  `found_classifyDetail` varchar(45) DEFAULT NULL,
  `found_imgPath` varchar(300) NOT NULL,
  `found_more` varchar(5000) DEFAULT NULL,
  `found_updatetime` datetime NOT NULL,
  PRIMARY KEY (`found_no`),
  KEY `fk_found_table` (`st_num`),
  CONSTRAINT `fk_found_table` FOREIGN KEY (`st_num`) REFERENCES `student` (`st_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `founditeminfo`
--

LOCK TABLES `founditeminfo` WRITE;
/*!40000 ALTER TABLE `founditeminfo` DISABLE KEYS */;
INSERT INTO `founditeminfo` VALUES (1,'2012081057','채명철','01022925701','coaudcjf@naver.com','2016-08-27','세종미용실','카드','하나카드','/uploadimg/temp/2016-07-07_9343375.JPG','머리자르다 놓고왔음','2016-08-27 11:13:32'),(2,'2012081057','채명철','01022425545','asdasdasd','2016-11-06','asdasdasd','의류','asdasdasd','/uploadImg/lost/lost_No.2/prada.jpg','asdasdad','2016-11-07 00:49:04'),(3,'2012081057','채명철','','asdasdasd','2016-11-08','sfasfasfas','신발','asfasfasfa','/uploadImg/found/found_No.3/prada.jpg','','2016-11-07 00:51:00'),(4,'2012081057','채명철','','','2016-07-01','ㅁㄴㅇㄻㅇㄴㄹ','가방','ㅁㄴㅇㄻㄴㄹ','','','2016-11-19 20:57:52'),(5,'2012081057','채명철','010','','2016-08-01','asdasd','지갑','asdasdasfaf','/uploadImg/temp/BestHDWallpapersPack308_20_bender777post.jpg','','2016-11-19 21:28:44'),(6,'2012081057','채명철','01056166515','','2016-07-01','asdasdad','카드','asdadas','/uploadImg/temp/BestHDWallpapersPack308_19_bender777post.jpg','','2016-11-19 21:29:43'),(7,'2012081057','채명철','010','asdasdasdasd','2016-05-01','asdasdasd','의류','asdasdasd','','asdasd','2016-11-19 21:45:17');
/*!40000 ALTER TABLE `founditeminfo` ENABLE KEYS */;
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
