-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: transport_db
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `parties`
--

DROP TABLE IF EXISTS `parties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parties` (
  `party_id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(500) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `gst_number` varchar(20) DEFAULT NULL,
  `name` varchar(150) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`party_id`),
  KEY `idx_parties_name` (`name`),
  KEY `idx_parties_gst_number` (`gst_number`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parties`
--

LOCK TABLES `parties` WRITE;
/*!40000 ALTER TABLE `parties` DISABLE KEYS */;
INSERT INTO `parties` VALUES (1,'Survey 88, Chakan MIDC Phase I, Pune, Maharashtra 410501','2026-11-19 11:02:41.512468','27AAJCS4321D1Z9','Sahyadri Auto Components Pvt Ltd','2026-11-19 11:02:41.512468'),(3,'Plot 19, Peenya Industrial Area, Bengaluru, Karnataka 560058','2026-11-19 11:08:51.999816','29AABCA9876H1Z1','Western Industrial Supplies','2026-11-19 11:08:51.999816'),(4,'Plot 19, Peenya Industrial Area, Bengaluru, Karnataka 560058','2026-11-19 11:12:31.323591','29AABCA9876H1Z1','Global Industrial Supplies','2026-11-19 11:12:31.323591'),(8,'Gala 7, Shiroli MIDC, Kolhapur, Maharashtra 416122','2026-11-23 06:33:49.005234','27AAFCR9204K1Z9','Kolhapur Foundry & Castings','2026-11-23 06:33:49.005234'),(9,'Plot C-104, GIDC Ankleshwar, Bharuch, Gujarat 393002','2026-11-23 06:34:01.738557','24AAACN3190H1Z4','Narmada Chemicals & Solvents Ltd','2026-11-23 06:34:01.738557'),(10,'Shed 12, Naroda GIDC, Ahmedabad, Gujarat 382330','2026-11-23 06:34:14.271509','24AAHCS7645R1Z7','Sabarmati Packaging Industries','2026-11-23 06:34:14.271509'),(11,'Plot 88, Hinjawadi Phase II, Pune, Maharashtra 411057','2026-11-23 06:34:27.747827','27AAKCI2098L1Z3','Indrayani Pharma Formulations','2026-11-23 06:34:27.747827'),(13,'Plot 305, Pandesara GIDC, Surat, Gujarat 394221','2026-11-23 06:34:47.327031','24AAACT5409F1Z1','Tapi Textiles & Yarns','2026-11-23 06:34:47.327031'),(15,'Warehouse 4, Sanaswadi Industrial Estate, Pune, Maharashtra 412208','2026-11-23 06:35:35.116151','27AAACD8921R1Z9','Deccan Logistics & Distribution Hub','2026-11-23 06:35:35.116151'),(16,'Plot 56, Pithampur Sector III, Dhar, Madhya Pradesh 454775','2026-11-23 06:35:45.868014','23AAACM3048L1Z6','Malwa Engineering & Traders','2026-11-23 06:35:45.868014'),(19,'Plot G-9, Shapar Veraval Industrial Area, Rajkot, Gujarat 360024','2026-11-23 06:36:12.079296','24AAACS6194D1Z8','Saurashtra Precision Parts Co','2026-11-23 06:36:12.079296'),(20,'Gala 22, Ambad MIDC, Nashik, Maharashtra 422010','2026-11-23 06:36:24.962296','27AAACG1852M1Z5','Godavari Retail & FMCG Depot','2026-11-23 06:36:24.962296'),(21,'Survey 118/2, Chakan MIDC Phase I, Pune, Maharashtra 410501','2026-11-23 06:50:01.648579','27AAKCD8831M1Z8','Deccan Precision Engineering Works','2026-11-23 06:50:01.648579'),(22,'Gala 22, Ambad MIDC, Nashik, Maharashtra 422010','2026-11-23 06:51:36.224124','27AAACG1852M1Z5','Charotar Packaging & Allied Works','2026-11-23 06:51:36.224124');
/*!40000 ALTER TABLE `parties` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-23 12:30:32
