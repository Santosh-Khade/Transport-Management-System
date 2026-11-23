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
-- Table structure for table `lr_master`
--

DROP TABLE IF EXISTS `lr_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lr_master` (
  `lr_id` bigint NOT NULL AUTO_INCREMENT,
  `freight` decimal(12,2) NOT NULL,
  `goods_description` varchar(500) NOT NULL,
  `labour_charge` decimal(12,2) NOT NULL,
  `lr_date` date NOT NULL,
  `lr_number` varchar(30) NOT NULL,
  `lr_received_date` date DEFAULT NULL,
  `number_of_goods_packages` int NOT NULL,
  `payment_mode` enum('PAID','TBB','TO_PAY') NOT NULL,
  `time_stamp` datetime(6) NOT NULL,
  `weight` decimal(12,3) NOT NULL,
  `consignee_id` bigint NOT NULL,
  `consigner_id` bigint NOT NULL,
  `memo_id` bigint DEFAULT NULL,
  PRIMARY KEY (`lr_id`),
  UNIQUE KEY `idx_lr_master_lr_number` (`lr_number`),
  KEY `idx_lr_master_lr_date` (`lr_date`),
  KEY `idx_lr_master_consigner_id` (`consigner_id`),
  KEY `idx_lr_master_consignee_id` (`consignee_id`),
  KEY `idx_lr_master_memo_id` (`memo_id`),
  KEY `idx_lr_master_payment_mode` (`payment_mode`),
  CONSTRAINT `fk_lr_consignee` FOREIGN KEY (`consignee_id`) REFERENCES `parties` (`party_id`),
  CONSTRAINT `fk_lr_consigner` FOREIGN KEY (`consigner_id`) REFERENCES `parties` (`party_id`),
  CONSTRAINT `fk_lr_delivery_memo` FOREIGN KEY (`memo_id`) REFERENCES `delivery_memos` (`memo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lr_master`
--

LOCK TABLES `lr_master` WRITE;
/*!40000 ALTER TABLE `lr_master` DISABLE KEYS */;
INSERT INTO `lr_master` VALUES (1,18500.00,'CNC machined steel flanges in wooden crates',1200.00,'2026-11-19','LR-2026-000001',NULL,24,'TO_PAY','2026-11-19 11:09:17.572336',1450.500,3,1,NULL),(2,18500.00,'CNC machined steel flanges in wooden crates',1200.00,'2026-11-19','LR-2026-000002',NULL,24,'TO_PAY','2026-11-19 11:13:20.975379',1450.500,3,4,NULL),(3,18500.00,'CNC machined steel flanges in wooden crates',1200.00,'2026-11-19','LR-2026-000003',NULL,24,'TO_PAY','2026-11-19 11:13:35.227412',1450.500,3,4,1),(4,4800.00,'Machined auto parts in wooden crates',350.00,'2026-11-23','LR-2026-000004',NULL,25,'TO_PAY','2026-11-23 06:48:09.594721',1450.500,3,1,NULL),(5,2650.00,'CNC turned shafts and gear assemblies',200.00,'2026-11-23','LR-2026-000005',NULL,18,'PAID','2026-11-23 06:50:09.270790',980.250,15,21,NULL),(6,3450.00,'Polyester yarn bobbins in woven bales',320.00,'2026-11-23','LR-2026-000006',NULL,40,'PAID','2026-11-23 06:51:41.287756',1640.000,22,13,NULL);
/*!40000 ALTER TABLE `lr_master` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-23 12:30:31
