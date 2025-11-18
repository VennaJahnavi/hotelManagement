CREATE DATABASE  IF NOT EXISTS `hotelmanagement` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hotelmanagement`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
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
  `category` varchar(50) NOT NULL DEFAULT 'Uncategorized',
  `display_order` int DEFAULT '0',
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_items`
--

LOCK TABLES `menu_items` WRITE;
/*!40000 ALTER TABLE `menu_items` DISABLE KEYS */;
INSERT INTO `menu_items` VALUES (31,'Pancake Stack','Fluffy pancakes served with maple syrup and butter.',5.99,1,'Breakfast',1,'images/pancake.jpg'),(32,'Omelette Deluxe','Three-egg omelette with cheese, mushrooms, and spinach.',6.49,1,'Breakfast',2,'images/omelette.jpg'),(33,'Avocado Toast','Toasted sourdough topped with smashed avocado and poached egg.',7.25,1,'Breakfast',3,'images/avocado_toast.jpg'),(34,'French Toast','Classic French toast served with powdered sugar and berries.',6.75,1,'Breakfast',4,'images/french_toast.jpg'),(35,'Classic Beef Burger','Juicy grilled beef patty with lettuce, tomato, and cheese.',8.99,1,'Burger',1,'images/classic_beef_burger.jpg'),(36,'Crispy Chicken Burger','Crispy fried chicken fillet with spicy mayo and pickles.',8.49,1,'Burger',2,'images/crispy_chicken_burger.jpg'),(37,'Veggie Delight Burger','Grilled plant-based patty with lettuce and avocado.',7.99,1,'Burger',3,'images/veggie_delight_burger.jpg'),(38,'Double Cheese Burger','Double beef patties, double cheddar, extra flavor.',10.49,1,'Burger',4,'images/double_cheese_burger.jpg'),(39,'Margherita Pizza','Classic pizza topped with tomato, mozzarella, and basil.',9.99,1,'Pizza',1,'images/margherita_pizza.jpg'),(40,'Pepperoni Feast','Loaded with pepperoni and mozzarella cheese.',11.49,1,'Pizza',2,'images/pepperoni_pizza.jpg'),(41,'BBQ Chicken Pizza','Grilled chicken, BBQ sauce, red onions, and mozzarella.',12.25,1,'Pizza',3,'images/bbq_chicken_pizza.jpg'),(42,'Veg Supreme Pizza','Topped with bell peppers, olives, mushrooms, and corn.',10.99,1,'Pizza',4,'images/veg_supreme_pizza.jpg'),(43,'Chocolate Lava Cake','Warm chocolate cake with molten center and vanilla ice cream.',5.75,1,'Desserts',1,'images/chocolate_lava_cake.jpg'),(44,'Cheesecake Slice','Classic New York-style cheesecake with strawberry sauce.',4.99,1,'Desserts',2,'images/cheesecake_slice.jpg'),(45,'Brownie Sundae','Chocolate brownie topped with ice cream and fudge sauce.',5.25,1,'Desserts',3,'images/brownie_sundae.jpg'),(46,'Fruit Salad Bowl','Fresh seasonal fruits served chilled.',3.99,1,'Desserts',4,'images/fruit_salad_bowl.jpg'),(47,'Tomato Basil Soup','Creamy tomato soup with a hint of basil.',4.49,1,'Soup',1,'images/tomato_basil_soup.jpg'),(48,'Chicken Noodle Soup','Comforting soup with chicken, noodles, and vegetables.',4.99,1,'Soup',2,'images/chicken_noodle_soup.jpg'),(49,'Sweet Corn Soup','Mildly spiced soup made from sweet corn kernels.',3.99,1,'Soup',3,'images/sweet_corn_soup.jpg'),(50,'Mushroom Cream Soup','Rich and creamy mushroom soup with herbs.',4.75,1,'Soup',4,'images/mushroom_cream_soup.jpg'),(51,'Grilled Chicken with Veggies','Tender grilled chicken breast served with steamed vegetables.',11.99,1,'Main Course',1,'images/grilled_chicken_with_veggies.jpg'),(52,'Paneer Butter Masala','Cottage cheese cooked in a creamy tomato gravy.',10.49,1,'Main Course',2,'images/paneer_butter_masala.jpg'),(53,'Beef Steak','Grilled beef steak served with mashed potatoes and gravy.',14.99,1,'Main Course',3,'images/beef_steak.jpg'),(54,'Veg Fried Rice','Stir-fried rice with fresh vegetables and soy sauce.',8.49,1,'Main Course',4,'images/veg_fried_rice.jpg'),(55,'Butter Chicken','Classic Indian dish with chicken cooked in buttery tomato sauce.',12.49,1,'Main Course',5,'images/butter_chicken.jpg');
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
  `price` double NOT NULL DEFAULT '0',
  `line_total` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `menu_item_id` (`menu_item_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (74,44,43,1,'',5.75,5.75),(75,44,44,1,'',4.99,4.99),(76,44,46,1,'',3.99,3.99),(77,44,43,1,'',5.75,5.75),(78,45,37,1,'',7.99,7.99),(79,45,37,1,'',7.99,7.99),(80,45,31,1,'',5.99,5.99),(81,46,51,1,'',11.99,11.99),(82,46,52,1,'',10.49,10.49),(83,46,54,1,'',8.49,8.49),(84,47,33,1,'',7.25,7.25),(85,47,34,1,'',6.75,6.75),(86,48,31,1,'',5.99,5.99),(87,48,31,1,'',5.99,5.99),(88,48,31,1,'',5.99,5.99);
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
  `status` varchar(70) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sub_total` decimal(10,2) NOT NULL DEFAULT '0.00',
  `tax_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `address_full_name` varchar(100) NOT NULL,
  `address_phone` varchar(20) NOT NULL,
  `address_line1` varchar(255) NOT NULL,
  `address_line2` varchar(255) DEFAULT NULL,
  `address_city` varchar(100) NOT NULL,
  `address_zip` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `chef_id` (`chef_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`chef_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (44,13,NULL,'CANCELLED','2025-11-16 17:37:12','2025-11-16 17:40:04',20.48,1.02,21.50,'Anand Dwivedi','9538433708','10\\157 gayatri nagar, Anantpur, Arun Nagar, Rewa','Gayatri nagar Rewa','Rewa','486003'),(45,13,15,'READY','2025-11-16 17:40:32','2025-11-16 17:41:45',21.97,1.10,23.07,'Anand Dwivedi','9538433708','10\\157 gayatri nagar, Anantpur, Arun Nagar, Rewa','Gayatri nagar Rewa','Rewa','486003'),(46,13,15,'READY','2025-11-16 17:41:07','2025-11-16 17:41:47',30.97,1.55,32.52,'Anand Dwivedi','9538433708','10\\157 gayatri nagar, Anantpur, Arun Nagar, Rewa','Gayatri nagar Rewa','Rewa','486003'),(47,13,NULL,'CANCELLED','2025-11-16 17:48:59','2025-11-16 17:49:09',14.00,0.70,14.70,'Anand Dwivedi','9538433708','10\\157 gayatri nagar, Anantpur, Arun Nagar, Rewa','Gayatri nagar Rewa','Rewa','486003'),(48,13,16,'READY','2025-11-16 17:49:21','2025-11-16 17:49:39',17.97,0.90,18.87,'Anand Dwivedi','9538433708','10\\157 gayatri nagar, Anantpur, Arun Nagar, Rewa','Gayatri nagar Rewa','Rewa','486003');
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (13,'anand','anand','CUSTOMER','2025-11-16 17:36:21'),(14,'bharti','bharti','MANAGER','2025-11-16 17:36:28'),(15,'rohan','rohan','CHEF','2025-11-16 17:36:35'),(16,'abhay','abhay','CHEF','2025-11-16 17:36:43');
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

-- Dump completed on 2025-11-18 10:02:09
