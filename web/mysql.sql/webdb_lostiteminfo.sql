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
-- Table structure for table `lostiteminfo`
--

DROP TABLE IF EXISTS `lostiteminfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lostiteminfo` (
  `lost_no` int(11) NOT NULL AUTO_INCREMENT,
  `st_num` char(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `kakao` varchar(45) DEFAULT NULL,
  `lost_date` date NOT NULL,
  `lost_place` varchar(100) NOT NULL,
  `lost_classify` varchar(10) NOT NULL,
  `lost_classifyDetail` varchar(45) DEFAULT NULL,
  `lost_imgPath` varchar(300) DEFAULT NULL,
  `lost_more` varchar(5000) DEFAULT NULL,
  `lost_updatetime` datetime NOT NULL,
  PRIMARY KEY (`lost_no`),
  KEY `fk_lost_table` (`st_num`),
  CONSTRAINT `fk_lost_table` FOREIGN KEY (`st_num`) REFERENCES `student` (`st_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lostiteminfo`
--

LOCK TABLES `lostiteminfo` WRITE;
/*!40000 ALTER TABLE `lostiteminfo` DISABLE KEYS */;
INSERT INTO `lostiteminfo` VALUES (1,'2012081057','채명철','01022925701','cocahack','2016-08-19','7021버스','핸드폰','갤럭시s7','','asd','2016-08-29 19:25:36'),(2,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(3,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(4,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','구찌 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(5,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(6,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(7,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(8,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(10,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(11,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(12,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(13,'2012081057','채명철','01022925701','cocahack','2016-08-22','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주십쇼 ㅠㅠㅠ\r\n\r\n부탁입니다','2016-09-01 18:45:02'),(14,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(15,'2012081057','채명철','01022925701','cocahack','2016-08-16','본관 608호','지갑','mcm 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(17,'2012081057','채명철','01022925701','cocahack','2016-04-09','공학관 608호','지갑','구찌 지갑','/uploadImg/temp/bhu1_(6).jpg','찾아주십쇼 ㅠㅠ \r\n\r\n\r\n부탁입니다','2016-09-01 18:45:02'),(18,'2012081057','채명철','01022925701','cocahack','2016-09-05','사교관 307호','지갑','프라다 장지갑','/uploadImg/temp/prada1.jpg','찾아주세요 ㅠㅠ\r\n\r\n잉잉','2016-09-01 18:45:02'),(19,'2012081057','채명철','01044125654','kaksodo2','2016-08-31','공학관 3층','가방','에코백','','ㅁㄴㅇ','2016-09-01 18:47:39'),(20,'2012081057','채명철','010','5asd654','2016-03-07','공학관 318호','핸드폰','갤럭시 노트2','/uploadImg/temp/슬라이드바.JPG','흑흑','2016-09-21 00:40:58'),(21,'2012081057','D��','01022925701','$null','2016-11-06','6th floor','t�','shinhan card','/uploadImg/lost/lost_No.21/prada.jpg','help me!~!!','2016-11-06 21:23:08'),(22,'2012081057','D��','$null','kakaoid','2016-11-06','7th floor','t�','hana card','','help me!!!!\r\n','2016-11-06 21:38:30'),(23,'2012081057','D??','01066872245','kakaofriends','2016-11-06','5th floor','t?','gukmin card','/uploadImg/lost/lost_No.23/prada.jpg','help me... please...','2016-11-06 21:59:13'),(24,'2012081057','D��','01022925701','kakao','2016-11-06','5th floor','t�','gukmin card','/uploadImg/lost/lost_No.24/prada.jpg','helpme','2016-11-06 22:27:13'),(25,'2012081057','[B@2992442','[B@14cbc53','$null','2016-11-06','[B@a3fa990','[B@8254289','[B@119428e','','$null\r\n','2016-11-06 22:39:25'),(26,'2012081057','D��','','asdasdasd','2016-11-06','asdasdasd','카드','asdasd','','$null\n','2016-11-06 23:01:52'),(27,'2012081057','채명철','$null','asdfasdf','2016-11-06','dfasdfasddfadsf','의류','sdfsadfsadfas','','$null','2016-11-06 23:28:09'),(28,'2012081057','채명철','$null','asdasd','2016-11-06','dasdasdasd','학생증','asdasdas','/uploadImg/lost/lost_No.28/prada.jpg','$null','2016-11-06 23:56:24'),(29,'2012081057','채명철','','asdasd','2016-11-06','dasdasdasd','학생증','asdasdas','/uploadImg/lost/lost_No.29/prada.jpg','','2016-11-06 23:59:55'),(30,'2012081057','채명철','','asdasd','2016-11-06','dasdadad','카드','adasdas','','','2016-11-07 00:52:04'),(31,'2012081057','채명철','','test','2016-11-07','test','카드','test','','','2016-11-07 20:52:11'),(32,'2012081057','채명철\n','','test','2016-11-07','test','카드','test','','','2016-11-07 21:36:21'),(33,'2012081057','채명철','','test\r','2016-11-07','test\r','의류\r','test\r','','','2016-11-07 21:50:30'),(34,'2012081057','채명철','','','2016-04-01','asdasd','지갑','asdasd','','','2016-11-19 20:41:23'),(35,'2012081057','채명철','','','2016-03-01','ㅁㅇㅁㄴㅇ','usb','ㅁㄴㅇㅁㅇ','','','2016-11-19 20:54:56'),(36,'2012081057','채명철','','','2016-06-01','ㅁㄴㅇ','의류','ㅁㄴㅇㅁㄴㅇ','','ㅎㅎ','2016-11-19 20:56:53');
/*!40000 ALTER TABLE `lostiteminfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-14 11:28:13
