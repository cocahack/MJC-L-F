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
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `st_num` char(10) NOT NULL,
  `st_pwd` varchar(25) NOT NULL,
  `st_name` varchar(20) NOT NULL,
  `st_age` int(11) NOT NULL,
  `st_gender` char(1) NOT NULL,
  `st_code` char(3) NOT NULL,
  PRIMARY KEY (`st_num`),
  KEY `lost_info_idx` (`st_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('2010081001','1234','사용자',20,'남','081'),('2010081002','1234','사용자',20,'남','081'),('2010081003','1234','사용자',20,'남','081'),('2010081004','1234','사용자',20,'남','081'),('2010081005','1234','사용자',20,'남','081'),('2010081006','1234','사용자',20,'남','081'),('2010081007','1234','사용자',20,'남','081'),('2010081008','1234','사용자',20,'남','081'),('2010081009','1234','사용자',20,'남','081'),('2010081010','1234','사용자',20,'남','081'),('2010081011','1234','사용자',20,'남','081'),('2010081012','1234','사용자',20,'남','081'),('2010081013','1234','사용자',20,'남','081'),('2010081014','1234','사용자',20,'남','081'),('2010081015','1234','사용자',20,'남','081'),('2010081016','1234','사용자',20,'남','081'),('2010081017','1234','사용자',20,'남','081'),('2010081018','1234','사용자',20,'남','081'),('2010081019','1234','사용자',20,'남','081'),('2010081020','1234','사용자',20,'남','081'),('2010081021','1234','사용자',20,'남','081'),('2010081022','1234','사용자',20,'남','081'),('2010081023','1234','사용자',20,'남','081'),('2010081024','1234','사용자',20,'남','081'),('2010081025','1234','사용자',20,'남','081'),('2010081026','1234','사용자',20,'남','081'),('2010081027','1234','사용자',20,'남','081'),('2010081028','1234','사용자',20,'남','081'),('2010081029','1234','사용자',20,'남','081'),('2010081030','1234','사용자',20,'남','081'),('2010081031','1234','사용자',20,'남','081'),('2010081032','1234','사용자',20,'남','081'),('2010081033','1234','사용자',20,'남','081'),('2010081034','1234','사용자',20,'남','081'),('2010081035','1234','사용자',20,'남','081'),('2010081036','1234','사용자',20,'남','081'),('2010081037','1234','사용자',20,'남','081'),('2010081038','1234','사용자',20,'남','081'),('2010081039','1234','사용자',20,'남','081'),('2010081040','1234','사용자',20,'남','081'),('2010081041','1234','사용자',20,'남','081'),('2010081042','1234','사용자',20,'남','081'),('2010081043','1234','사용자',20,'남','081'),('2010081044','1234','사용자',20,'남','081'),('2010081045','1234','사용자',20,'남','081'),('2010081046','1234','사용자',20,'남','081'),('2010081047','1234','사용자',20,'남','081'),('2010081048','1234','사용자',20,'남','081'),('2010081049','1234','사용자',20,'남','081'),('2010081050','1234','사용자',20,'남','081'),('2010081051','1234','사용자',20,'남','081'),('2010081052','1234','사용자',20,'남','081'),('2010081053','1234','사용자',20,'남','081'),('2010081054','1234','사용자',20,'남','081'),('2010081055','1234','사용자',20,'남','081'),('2010081056','1234','사용자',20,'남','081'),('2010081057','1234','사용자',20,'남','081'),('2010081058','1234','사용자',20,'남','081'),('2010081059','1234','사용자',20,'남','081'),('2010081060','1234','사용자',20,'남','081'),('2010081061','1234','사용자',20,'남','081'),('2010081062','1234','사용자',20,'남','081'),('2010081063','1234','사용자',20,'남','081'),('2010081064','1234','사용자',20,'남','081'),('2010081065','1234','사용자',20,'남','081'),('2010081066','1234','사용자',20,'남','081'),('2010081067','1234','사용자',20,'남','081'),('2010081068','1234','사용자',20,'남','081'),('2010081069','1234','사용자',20,'남','081'),('2010081070','1234','사용자',20,'남','081'),('2010081071','1234','사용자',20,'남','081'),('2010081072','1234','사용자',20,'남','081'),('2010081073','1234','사용자',20,'남','081'),('2010081074','1234','사용자',20,'남','081'),('2010081075','1234','사용자',20,'남','081'),('2010081076','1234','사용자',20,'남','081'),('2010081077','1234','사용자',20,'남','081'),('2010081078','1234','사용자',20,'남','081'),('2010081079','1234','사용자',20,'남','081'),('2010081080','1234','사용자',20,'남','081'),('2010081081','1234','사용자',20,'남','081'),('2010081082','1234','사용자',20,'남','081'),('2010081083','1234','사용자',20,'남','081'),('2010081084','1234','사용자',20,'남','081'),('2010081085','1234','사용자',20,'남','081'),('2010081086','1234','사용자',20,'남','081'),('2010081087','1234','사용자',20,'남','081'),('2010081088','1234','사용자',20,'남','081'),('2010081089','1234','사용자',20,'남','081'),('2010081090','1234','사용자',20,'남','081'),('2010081091','1234','사용자',20,'남','081'),('2010081092','1234','사용자',20,'남','081'),('2010081093','1234','사용자',20,'남','081'),('2010081094','1234','사용자',20,'남','081'),('2010081095','1234','사용자',20,'남','081'),('2010081096','1234','사용자',20,'남','081'),('2010081097','1234','사용자',20,'남','081'),('2010081098','1234','사용자',20,'남','081'),('2010081099','1234','사용자',20,'남','081'),('2010081100','1234','사용자',20,'남','081'),('2010081101','1234','사용자',20,'남','081'),('2010081102','1234','사용자',20,'남','081'),('2010081103','1234','사용자',20,'남','081'),('2010081104','1234','사용자',20,'남','081'),('2010081105','1234','사용자',20,'남','081'),('2010081106','1234','사용자',20,'남','081'),('2010081107','1234','사용자',20,'남','081'),('2010081108','1234','사용자',20,'남','081'),('2010081109','1234','사용자',20,'남','081'),('2010081110','1234','사용자',20,'남','081'),('2010081111','1234','사용자',20,'남','081'),('2010081112','1234','사용자',20,'남','081'),('2010081113','1234','사용자',20,'남','081'),('2010081114','1234','사용자',20,'남','081'),('2010081115','1234','사용자',20,'남','081'),('2010081116','1234','사용자',20,'남','081'),('2010081117','1234','사용자',20,'남','081'),('2010081118','1234','사용자',20,'남','081'),('2010081119','1234','사용자',20,'남','081'),('2010081120','1234','사용자',20,'남','081'),('2010081121','1234','사용자',20,'남','081'),('2010081122','1234','사용자',20,'남','081'),('2010081123','1234','사용자',20,'남','081'),('2010081124','1234','사용자',20,'남','081'),('2010081125','1234','사용자',20,'남','081'),('2010081126','1234','사용자',20,'남','081'),('2010081127','1234','사용자',20,'남','081'),('2010081128','1234','사용자',20,'남','081'),('2010081129','1234','사용자',20,'남','081'),('2010081130','1234','사용자',20,'남','081'),('2010081131','1234','사용자',20,'남','081'),('2010081132','1234','사용자',20,'남','081'),('2010081133','1234','사용자',20,'남','081'),('2010081134','1234','사용자',20,'남','081'),('2010081135','1234','사용자',20,'남','081'),('2010081136','1234','사용자',20,'남','081'),('2010081137','1234','사용자',20,'남','081'),('2010081138','1234','사용자',20,'남','081'),('2010081139','1234','사용자',20,'남','081'),('2010081140','1234','사용자',20,'남','081'),('2010081141','1234','사용자',20,'남','081'),('2010081142','1234','사용자',20,'남','081'),('2010081143','1234','사용자',20,'남','081'),('2010081144','1234','사용자',20,'남','081'),('2010081145','1234','사용자',20,'남','081'),('2010081146','1234','사용자',20,'남','081'),('2010081147','1234','사용자',20,'남','081'),('2010081148','1234','사용자',20,'남','081'),('2010081149','1234','사용자',20,'남','081'),('2010081150','1234','사용자',20,'남','081'),('2012081001','1234','사용자',20,'남','081'),('2012081002','1234','사용자',20,'남','081'),('2012081003','1234','사용자',20,'남','081'),('2012081004','1234','사용자',20,'남','081'),('2012081005','1234','사용자',20,'남','081'),('2012081006','1234','사용자',20,'남','081'),('2012081007','1234','사용자',20,'남','081'),('2012081008','1234','사용자',20,'남','081'),('2012081009','1234','사용자',20,'남','081'),('2012081010','1234','사용자',20,'남','081'),('2012081011','1234','사용자',20,'남','081'),('2012081012','1234','사용자',20,'남','081'),('2012081013','1234','사용자',20,'남','081'),('2012081014','1234','사용자',20,'남','081'),('2012081015','1234','사용자',20,'남','081'),('2012081016','1234','사용자',20,'남','081'),('2012081017','1234','사용자',20,'남','081'),('2012081018','1234','사용자',20,'남','081'),('2012081019','1234','사용자',20,'남','081'),('2012081020','1234','사용자',20,'남','081'),('2012081021','1234','사용자',20,'남','081'),('2012081022','1234','박현수',24,'남','081'),('2012081057','medusa11','채명철',24,'남','081'),('2016081001','1234','김철수',20,'남','081');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
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
