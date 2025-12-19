-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: parking_system
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','123456');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `date_record`
--

DROP TABLE IF EXISTS `date_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `date_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `record_date` date NOT NULL,
  `cycle_no` int NOT NULL COMMENT '第几轮30天周期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `date_record`
--

LOCK TABLES `date_record` WRITE;
/*!40000 ALTER TABLE `date_record` DISABLE KEYS */;
INSERT INTO `date_record` VALUES (1,'2025-12-18',1);
/*!40000 ALTER TABLE `date_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle_record`
--

DROP TABLE IF EXISTS `vehicle_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `plate_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '车牌号',
  `entry_time` datetime NOT NULL COMMENT '入场时间',
  `exit_time` datetime DEFAULT NULL COMMENT '离场时间',
  `parking_fee` decimal(6,2) DEFAULT '0.00' COMMENT '停车费',
  `status` int NOT NULL COMMENT '0-在场 1-已离场',
  `date_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_date_vehicle` (`date_id`),
  CONSTRAINT `fk_date_vehicle` FOREIGN KEY (`date_id`) REFERENCES `date_record` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle_record`
--

LOCK TABLES `vehicle_record` WRITE;
/*!40000 ALTER TABLE `vehicle_record` DISABLE KEYS */;
INSERT INTO `vehicle_record` VALUES (1,'京N2B945','2025-12-19 09:09:03','2025-12-19 09:10:13',2.00,1,1),(2,'沪KR9888','2025-12-19 09:09:37','2025-12-19 09:09:54',2.00,1,1),(3,'鲁B325DE','2025-12-19 10:35:40','2025-12-19 10:41:02',2.00,1,1),(4,'京N2B945','2025-12-19 10:40:14','2025-12-19 10:40:52',2.00,1,1);
/*!40000 ALTER TABLE `vehicle_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-19 11:21:15
