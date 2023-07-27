-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: dhinesh
-- ------------------------------------------------------
-- Server version	8.0.33-0ubuntu0.20.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dhinesh_demo`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `dhinesh_demo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `created_on` varchar(255) DEFAULT '2023-06-28',
  `modify_time` varchar(255) DEFAULT '2023-06-28',
  `age` int DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dhinesh_demo`
--

LOCK TABLES `dhinesh_demo` WRITE;
/*!40000 ALTER TABLE `dhinesh_demo` DISABLE KEYS */;
INSERT INTO `dhinesh_demo` VALUES (1,'admin','admin@123.com','2023-07-26 05:25','2023-07-26 05:25',22,'1234567890'),(2,'dhinesh','dhinesh@gmail.com','2023-07-26 05:28','2023-07-26 05:28',25,'1234567890'),(3,'nikhil','nikhil@gmail.com','2023-07-26 05:29','2023-07-26 05:29',22,'1234567890');
/*!40000 ALTER TABLE `dhinesh_demo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dhinesh_demo2`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `dhinesh_demo2` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `created_on` varchar(255) DEFAULT NULL,
  `modify_time` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dhinesh_demo2`
--

LOCK TABLES `dhinesh_demo2` WRITE;
/*!40000 ALTER TABLE `dhinesh_demo2` DISABLE KEYS */;
INSERT INTO `dhinesh_demo2` VALUES (1,'chennai','UK','2023-07-26 05:25','2023-07-26 05:25','https://localmysql-s3.s3.amazonaws.com/images/aws2.jpeg'),(2,'coimbatore','India','2023-07-26 05:28','2023-07-26 05:28',''),(3,'chennai','UK','2023-07-26 05:29','2023-07-26 05:29','https://localmysql-s3.s3.amazonaws.com/images/aws3.jpeg');
/*!40000 ALTER TABLE `dhinesh_demo2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dhinesh_demo3`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `dhinesh_demo3` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_on` varchar(255) DEFAULT NULL,
  `modify_time` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `confirmpassword` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ldha0y2cm7qcfkdl5qoavmqxh` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dhinesh_demo3`
--

LOCK TABLES `dhinesh_demo3` WRITE;
/*!40000 ALTER TABLE `dhinesh_demo3` DISABLE KEYS */;
INSERT INTO `dhinesh_demo3` VALUES (1,'2023-07-26 05:25','2023-07-26 05:25','$2a$10$SwCwdvFdEKhC4a3lCY/ZXuca/Zw1bEJjoWTGN.GeJ8Gmnw0Ip0YdO','admin','$2a$10$gMp1ZyXEolDPzNXytWuN7.ppT3YRb/JcHxKmhBA4Taa68s4/yhD0S'),(2,'2023-07-26 05:28','2023-07-26 05:28','$2a$10$QYv4rskDiITKOix/TYBIke6p3pW2KwUeJLlJqHo15dPz4HifTKtN6','dhinesh','$2a$10$vh2.9sTrydFnvCVeLwc/t.JOVtobEkd9bOdtmAv7e9J1MO78sQzki'),(3,'2023-07-26 05:29','2023-07-26 05:29','$2a$10$blVdAiYJXXdChkNpm0A4Zuu1dO72NXHW51e/h5hQU3R7rvrkPUfNK','nikhil','$2a$10$kH54YHvhMpFMQ6jb06j6Rejj.MViMiBEWcOs5Z/AnLzCu4yY5k4S6');
/*!40000 ALTER TABLE `dhinesh_demo3` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-27 10:35:42
