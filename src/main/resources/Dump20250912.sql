-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: hotelmanagement
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `menu_items`
--

DROP TABLE IF EXISTS `menu_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `price` decimal(10,2) NOT NULL,
  `is_available` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_items`
--

LOCK TABLES `menu_items` WRITE;
/*!40000 ALTER TABLE `menu_items` DISABLE KEYS */;
INSERT INTO `menu_items` VALUES (1,'Margherita Pizza','Classic cheese pizza with tomato base',299.99,1),(2,'Veggie Burger','Grilled patty with lettuce and mayo',199.50,1),(3,'Chicken Biryani','Spicy chicken biryani served with raita',349.00,1),(4,'Caesar Salad','Fresh romaine lettuce with Caesar dressing',149.00,1),(5,'Pasta Alfredo','Creamy Alfredo pasta with mushrooms',259.99,1),(6,'French Fries','Crispy golden French fries',99.00,1),(7,'Tandoori Chicken','Grilled chicken with Indian spices',399.00,1),(8,'Paneer Tikka','Grilled cottage cheese cubes with spices',299.00,1),(9,'Cold Coffee','Chilled coffee with ice and milk',129.00,1),(10,'Lemonade','Freshly squeezed lemon drink',89.00,1),(11,'Margherita Pizza','Classic cheese pizza with tomato base',299.99,1),(12,'Veggie Burger','Grilled patty with lettuce and mayo',199.50,1),(13,'Chicken Biryani','Spicy chicken biryani served with raita',349.00,1),(14,'Caesar Salad','Fresh romaine lettuce with Caesar dressing',149.00,1),(15,'Pasta Alfredo','Creamy Alfredo pasta with mushrooms',259.99,1),(16,'French Fries','Crispy golden French fries',99.00,1),(17,'Tandoori Chicken','Grilled chicken with Indian spices',399.00,1),(18,'Paneer Tikka','Grilled cottage cheese cubes with spices',299.00,1),(19,'Cold Coffee','Chilled coffee with ice and milk',129.00,1),(20,'Lemonade','Freshly squeezed lemon drink',89.00,1),(21,'Chocolate Brownie','Rich chocolate brownie with nuts',149.00,1),(22,'Masala Dosa','Crispy dosa with spiced potato filling',179.00,1),(23,'Grilled Sandwich','Toasted sandwich with vegetables and cheese',159.00,1),(24,'Tomato Soup','Warm tomato soup served with breadsticks',99.00,1),(25,'Fish Curry','Traditional spicy fish curry',379.00,1),(26,'Mango Lassi','Sweet mango yogurt drink',119.00,1),(27,'Chicken Nuggets','Fried crispy chicken bites',149.00,1),(28,'Hakka Noodles','Stir-fried noodles with vegetables',199.00,1),(29,'Garlic Bread','Toasted bread with garlic and butter',89.00,1),(30,'Hot Chocolate','Warm chocolate drink with whipped cream',139.00,1);
/*!40000 ALTER TABLE `menu_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `menu_item_id` int NOT NULL,
  `quantity` int NOT NULL,
  `special_notes` text,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `menu_item_id` (`menu_item_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,2,1,'Less spicy'),(2,1,3,2,'Extra cheese'),(3,3,2,1,'Hard'),(4,3,3,2,'Pizza'),(5,4,1,2,''),(6,4,3,3,''),(7,5,4,2,''),(8,5,6,3,''),(9,6,1,2,''),(10,7,1,1,''),(11,7,2,2,''),(12,7,19,1,'');
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_status_history`
--

DROP TABLE IF EXISTS `order_status_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_status_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `status` enum('PENDING','IN_PROGRESS','READY','COMPLETED','CANCELLED') DEFAULT NULL,
  `changed_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_status_history_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_status_history`
--

LOCK TABLES `order_status_history` WRITE;
/*!40000 ALTER TABLE `order_status_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_status_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `chef_id` int DEFAULT NULL,
  `status` enum('PENDING','IN_PROGRESS','READY','COMPLETED','CANCELLED') DEFAULT 'PENDING',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `chef_id` (`chef_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`chef_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,4,'READY','2025-08-18 15:35:46','2025-08-19 16:54:49'),(3,1,NULL,'IN_PROGRESS','2025-08-18 15:40:14','2025-08-18 15:53:19'),(4,2,5,'COMPLETED','2025-08-19 15:37:33','2025-08-19 16:56:01'),(5,2,5,'COMPLETED','2025-08-19 17:02:24','2025-08-19 17:02:59'),(6,6,NULL,'PENDING','2025-08-19 17:46:47','2025-08-19 17:46:47'),(7,7,4,'READY','2025-08-21 03:38:26','2025-08-21 03:42:22');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('CUSTOMER','MANAGER','CHEF') NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'john_doe','securePassword123','CUSTOMER','2025-08-17 17:37:29'),(2,'anand','anand','CUSTOMER','2025-08-18 16:20:24'),(3,'bharti','bharti','MANAGER','2025-08-19 09:02:45'),(4,'rohan','rohan','CHEF','2025-08-19 10:32:46'),(5,'prakash','prakash','CHEF','2025-08-19 10:33:19'),(6,'test','test','CUSTOMER','2025-08-19 17:46:11'),(7,'Jahnavi','Jahnavi','CUSTOMER','2025-08-21 03:36:49');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-12 19:34:10
