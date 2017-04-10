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
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notice` (
  `notice_no` int(11) NOT NULL AUTO_INCREMENT,
  `notice_title` varchar(50) NOT NULL,
  `notice_writer` varchar(45) NOT NULL,
  `notice_content` varchar(1000) NOT NULL,
  `notice_imgPath` varchar(10000) DEFAULT NULL,
  `notice_regitDate` datetime NOT NULL,
  `notice_hit` int(11) NOT NULL,
  PRIMARY KEY (`notice_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (1,'분실물/습득물 등록하기','관리자','상단 메뉴 중 \'등록\' 메뉴를 통해 등록하실 수 있습니다.(단, 회원만 이용하실 수 있습니다.)','/uploadImg/notice/notice_No.1/KakaoTalk_20160919_013250933.jpg;/uploadImg/notice/notice_No.1/KakaoTalk_20160919_013251721.jpg;/uploadImg/notice/notice_No.1/124124124.JPG;','2016-09-21 17:13:22',49),(2,'9월 5주차 분실물 현황','관리자','습득일로부터 3개월 내 찾아가지 않을 시 폐기 처분합니다.\r\n\r\n빠른시일 내에 찾아가시길 바랍니다.','/uploadImg/notice/notice_No.2/0f92fe05ddd78dc599df4c80e955bc20_MfpSoyqB6KwAw4ciR5fcMvmUu3ZT4i.jpg;/uploadImg/notice/notice_No.2/8de588cebdfd71b7dcefefcdf067defe.jpg;/uploadImg/notice/notice_No.2/1028_162924_CH7-1_HD 2012 프로야구.jpg;','2016-10-01 02:05:18',17),(3,'10월 1주차 분실물 현황','관리자','빨리 찾아가','/uploadImg/notice/notice_No.3/BestHDWallpapersPack308_10_bender777post.jpg;/uploadImg/notice/notice_No.3/BestHDWallpapersPack308_11_bender777post.jpg;/uploadImg/notice/notice_No.3/BestHDWallpapersPack308_12_bender777post.jpg;','2016-10-01 02:07:08',17),(4,'호호호호홋!','관리자','ㅎㅎㅎ','/uploadImg/notice/notice_No.4/0f92fe05ddd78dc599df4c80e955bc20_MfpSoyqB6KwAw4ciR5fcMvmUu3ZT4i.jpg;/uploadImg/notice/notice_No.4/1028_162924_CH7-1_HD 2012 프로야구.jpg;/uploadImg/notice/notice_No.4/8de588cebdfd71b7dcefefcdf067defe.jpg;','2016-10-02 13:17:51',12),(5,'10월 1주차 분실물 현황','관리자','찾아가세요','/uploadImg/notice/notice_No.5/prada.jpg;/uploadImg/notice/notice_No.5/슬라이드바.JPG;','2016-10-09 14:41:26',16),(6,'분실물/습득물 등록하기','관리자','상단 메뉴 중 \'등록\' 메뉴를 통해 등록하실 수 있습니다.(단, 회원만 이용하실 수 있습니다.)\r\n\r\nㅎㅎㅎ','','2016-10-09 15:07:30',100),(7,'asf','관리자','asfasfa','','2016-11-20 13:48:35',27),(8,'asdas','관리자','dasdasd','','2016-11-20 15:02:13',24);
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
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
