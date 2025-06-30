CREATE DATABASE  IF NOT EXISTS `confecontrol` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `confecontrol`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: confecontrol
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
-- Table structure for table `detalle_descuentos`
--

DROP TABLE IF EXISTS `detalle_descuentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_descuentos` (
  `id_detalle_descuento` bigint NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) DEFAULT NULL,
  `monto` decimal(38,2) DEFAULT NULL,
  `tipo_descuento_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_detalle_descuento`),
  KEY `FKfmkwwfx9i690lkhwpppl1asaj` (`tipo_descuento_id`),
  KEY `FKe1dx9snyxcwpdrokxhu42u8y1` (`usuario_id`),
  CONSTRAINT `FKe1dx9snyxcwpdrokxhu42u8y1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `FKfmkwwfx9i690lkhwpppl1asaj` FOREIGN KEY (`tipo_descuento_id`) REFERENCES `tipo_descuento` (`id_detalle_descuento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_descuentos`
--

LOCK TABLES `detalle_descuentos` WRITE;
/*!40000 ALTER TABLE `detalle_descuentos` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_descuentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_paquete_lote`
--

DROP TABLE IF EXISTS `detalle_paquete_lote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_paquete_lote` (
  `id_detalle_paquete_lote` bigint NOT NULL AUTO_INCREMENT,
  `descripcion_observacion` varchar(255) DEFAULT NULL,
  `is_terminado` bit(1) DEFAULT NULL,
  `operacion_prenda_id` bigint DEFAULT NULL,
  `paquete_lote_id` bigint DEFAULT NULL,
  `tipo_descuento_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_detalle_paquete_lote`),
  KEY `FKoobrteuayqbeo7gq1lye8dv4y` (`operacion_prenda_id`),
  KEY `FKpn39uyfbktj4qbtgqcaoxmh2i` (`paquete_lote_id`),
  KEY `FKmestmu9pn69pfaoj22cyou9gm` (`tipo_descuento_id`),
  KEY `FKbsvp3edd4u6t4meyeyalij1ax` (`usuario_id`),
  CONSTRAINT `FKbsvp3edd4u6t4meyeyalij1ax` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `FKmestmu9pn69pfaoj22cyou9gm` FOREIGN KEY (`tipo_descuento_id`) REFERENCES `tipo_descuento` (`id_detalle_descuento`),
  CONSTRAINT `FKoobrteuayqbeo7gq1lye8dv4y` FOREIGN KEY (`operacion_prenda_id`) REFERENCES `operacion_prenda` (`id_operacion_prenda`),
  CONSTRAINT `FKpn39uyfbktj4qbtgqcaoxmh2i` FOREIGN KEY (`paquete_lote_id`) REFERENCES `paquete_lote` (`id_paquete_lote`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_paquete_lote`
--

LOCK TABLES `detalle_paquete_lote` WRITE;
/*!40000 ALTER TABLE `detalle_paquete_lote` DISABLE KEYS */;
INSERT INTO `detalle_paquete_lote` VALUES (1,NULL,_binary '\0',1,1,NULL,NULL),(2,NULL,_binary '\0',2,1,NULL,3),(3,NULL,_binary '\0',3,1,NULL,NULL),(4,NULL,_binary '\0',1,2,NULL,NULL),(5,NULL,_binary '\0',2,2,NULL,3),(6,NULL,_binary '\0',3,2,NULL,NULL),(7,NULL,_binary '\0',1,3,NULL,NULL),(8,NULL,_binary '\0',2,3,NULL,3),(9,NULL,_binary '\0',3,3,NULL,NULL),(10,NULL,_binary '\0',1,4,NULL,NULL),(11,NULL,_binary '\0',2,4,NULL,3),(12,NULL,_binary '\0',3,4,NULL,NULL),(13,NULL,_binary '\0',1,5,NULL,NULL),(14,NULL,_binary '\0',2,5,NULL,3),(15,NULL,_binary '\0',3,5,NULL,NULL),(16,NULL,_binary '\0',10,6,NULL,NULL),(17,NULL,_binary '\0',11,6,NULL,NULL),(18,NULL,_binary '\0',12,6,NULL,NULL),(19,NULL,_binary '\0',10,7,NULL,NULL),(20,NULL,_binary '\0',11,7,NULL,NULL),(21,NULL,_binary '\0',12,7,NULL,NULL),(22,NULL,_binary '\0',10,8,NULL,NULL),(23,NULL,_binary '\0',11,8,NULL,NULL),(24,NULL,_binary '\0',12,8,NULL,NULL),(25,NULL,_binary '\0',7,9,NULL,NULL),(26,NULL,_binary '\0',8,9,NULL,NULL),(27,NULL,_binary '\0',9,9,NULL,NULL),(28,NULL,_binary '\0',7,10,NULL,NULL),(29,NULL,_binary '\0',8,10,NULL,NULL),(30,NULL,_binary '\0',9,10,NULL,NULL),(31,NULL,_binary '\0',7,11,NULL,NULL),(32,NULL,_binary '\0',8,11,NULL,NULL),(33,NULL,_binary '\0',9,11,NULL,NULL);
/*!40000 ALTER TABLE `detalle_paquete_lote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_trabajo`
--

DROP TABLE IF EXISTS `detalle_trabajo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_trabajo` (
  `id_detalle_trabajo` bigint NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) DEFAULT NULL,
  `monto` decimal(38,2) DEFAULT NULL,
  `detalle_paquete_lote_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_detalle_trabajo`),
  KEY `FKth6nj4d272n50k8vrq4ckpun6` (`detalle_paquete_lote_id`),
  KEY `FKly67kb2ekaan0udtktlfd25ls` (`usuario_id`),
  CONSTRAINT `FKly67kb2ekaan0udtktlfd25ls` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `FKth6nj4d272n50k8vrq4ckpun6` FOREIGN KEY (`detalle_paquete_lote_id`) REFERENCES `detalle_paquete_lote` (`id_detalle_paquete_lote`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_trabajo`
--

LOCK TABLES `detalle_trabajo` WRITE;
/*!40000 ALTER TABLE `detalle_trabajo` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_trabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lote`
--

DROP TABLE IF EXISTS `lote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lote` (
  `id_lote` bigint NOT NULL AUTO_INCREMENT,
  `cantidad_prenda` int DEFAULT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `hora_creacion` time(6) DEFAULT NULL,
  `hora_fin` time(6) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `is_terminado` bit(1) DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `prenda_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_lote`),
  KEY `FKkige82m6ssg4uuuxvukl8owly` (`usuario_id`),
  KEY `FK55buij99etq84t0lw1upwh1yw` (`prenda_id`),
  CONSTRAINT `FK55buij99etq84t0lw1upwh1yw` FOREIGN KEY (`prenda_id`) REFERENCES `prenda` (`id_prenda`),
  CONSTRAINT `FKkige82m6ssg4uuuxvukl8owly` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lote`
--

LOCK TABLES `lote` WRITE;
/*!40000 ALTER TABLE `lote` DISABLE KEYS */;
INSERT INTO `lote` VALUES (1,100,'LOTE-090625-01','2025-06-09',NULL,'15:05:30.934000',NULL,_binary '',_binary '\0',1,1),(2,50,'LOTE-090625-02','2025-06-09',NULL,'15:28:16.589000',NULL,_binary '',_binary '\0',1,4),(3,100,'LOTE-090625-03','2025-06-09',NULL,'15:36:06.408000',NULL,_binary '',_binary '\0',1,3);
/*!40000 ALTER TABLE `lote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marcacion`
--

DROP TABLE IF EXISTS `marcacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marcacion` (
  `id_marcacion` bigint NOT NULL AUTO_INCREMENT,
  `estado_llegada` bit(1) DEFAULT NULL,
  `estado_salida` bit(1) DEFAULT NULL,
  `fecha` date NOT NULL,
  `hora_entrada` time(6) DEFAULT NULL,
  `hora_salida` time(6) DEFAULT NULL,
  `id_usuario` bigint DEFAULT NULL,
  PRIMARY KEY (`id_marcacion`),
  KEY `FK116uakg3ribxfiiw3ahgydg7p` (`id_usuario`),
  CONSTRAINT `FK116uakg3ribxfiiw3ahgydg7p` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marcacion`
--

LOCK TABLES `marcacion` WRITE;
/*!40000 ALTER TABLE `marcacion` DISABLE KEYS */;
INSERT INTO `marcacion` VALUES (1,_binary '\0',_binary '\0','2025-06-09','15:40:01.648000','15:40:06.006000',1),(2,_binary '\0',_binary '\0','2025-06-09','15:40:16.424000','15:40:22.071000',2);
/*!40000 ALTER TABLE `marcacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operacion_prenda`
--

DROP TABLE IF EXISTS `operacion_prenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operacion_prenda` (
  `id_operacion_prenda` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `precio_feriado` decimal(38,2) NOT NULL,
  `precio_horas_extra` decimal(38,2) NOT NULL,
  `precio_normal` decimal(38,2) NOT NULL,
  `prenda_id` bigint NOT NULL,
  PRIMARY KEY (`id_operacion_prenda`),
  KEY `FKklol07yi09828569sg283ori0` (`prenda_id`),
  CONSTRAINT `FKklol07yi09828569sg283ori0` FOREIGN KEY (`prenda_id`) REFERENCES `prenda` (`id_prenda`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operacion_prenda`
--

LOCK TABLES `operacion_prenda` WRITE;
/*!40000 ALTER TABLE `operacion_prenda` DISABLE KEYS */;
INSERT INTO `operacion_prenda` VALUES (1,_binary '','Cosido de costados',2.50,2.00,1.50,1),(2,_binary '','Unión de mangas',2.00,1.80,1.20,1),(3,_binary '','Pegado de cuello',1.50,1.30,1.00,1),(4,_binary '','Pegado de cuello',3.00,2.50,2.00,2),(5,_binary '','Costura de mangas',2.50,2.20,1.80,2),(6,_binary '','Colocación de botones',2.00,1.80,1.50,2),(7,_binary '','Costura de piernas',3.50,3.00,2.50,3),(8,_binary '','Colocación de bragueta',2.50,2.00,1.70,3),(9,_binary '','Colocación de bolsillos',2.80,2.40,2.00,3),(10,_binary '','Costura de cintura',2.00,1.80,1.40,4),(11,_binary '','Unión de forro',2.20,2.00,1.60,4),(12,_binary '','Cierre de falda',2.50,2.20,1.90,4);
/*!40000 ALTER TABLE `operacion_prenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago_trabajo`
--

DROP TABLE IF EXISTS `pago_trabajo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago_trabajo` (
  `id_pago_trabajo` bigint NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `subtotal_descuento` decimal(38,2) DEFAULT NULL,
  `subtotal_pago` decimal(38,2) DEFAULT NULL,
  `totalapagar` decimal(38,2) DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_pago_trabajo`),
  KEY `FKhx1dhpl9gw39gfnt0g9g6lm26` (`usuario_id`),
  CONSTRAINT `FKhx1dhpl9gw39gfnt0g9g6lm26` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago_trabajo`
--

LOCK TABLES `pago_trabajo` WRITE;
/*!40000 ALTER TABLE `pago_trabajo` DISABLE KEYS */;
/*!40000 ALTER TABLE `pago_trabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paquete_lote`
--

DROP TABLE IF EXISTS `paquete_lote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paquete_lote` (
  `id_paquete_lote` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `is_terminado` bit(1) DEFAULT NULL,
  `lote_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_paquete_lote`),
  KEY `FKnnfc6ywtv0gmjncktv8i6icwh` (`lote_id`),
  KEY `FK9xw8jmsrmbiui1w2yfx7pxak0` (`usuario_id`),
  CONSTRAINT `FK9xw8jmsrmbiui1w2yfx7pxak0` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `FKnnfc6ywtv0gmjncktv8i6icwh` FOREIGN KEY (`lote_id`) REFERENCES `lote` (`id_lote`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paquete_lote`
--

LOCK TABLES `paquete_lote` WRITE;
/*!40000 ALTER TABLE `paquete_lote` DISABLE KEYS */;
INSERT INTO `paquete_lote` VALUES (1,20,'LOTE-090625-01-PAQ01',_binary '\0',1,2),(2,20,'LOTE-090625-01-PAQ02',_binary '\0',1,2),(3,20,'LOTE-090625-01-PAQ03',_binary '\0',1,2),(4,20,'LOTE-090625-01-PAQ04',_binary '\0',1,2),(5,20,'LOTE-090625-01-PAQ05',_binary '\0',1,2),(6,20,'LOTE-090625-02-PAQ01',_binary '\0',2,2),(7,20,'LOTE-090625-02-PAQ02',_binary '\0',2,2),(8,10,'LOTE-090625-02-PAQ03',_binary '\0',2,2),(9,30,'LOTE-090625-03-PAQ01',_binary '\0',3,2),(10,30,'LOTE-090625-03-PAQ02',_binary '\0',3,2),(11,40,'LOTE-090625-03-PAQ03',_binary '\0',3,2);
/*!40000 ALTER TABLE `paquete_lote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pieza_prenda`
--

DROP TABLE IF EXISTS `pieza_prenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pieza_prenda` (
  `id_pieza_prenda` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `is_active` bit(1) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `prenda_id` bigint NOT NULL,
  PRIMARY KEY (`id_pieza_prenda`),
  KEY `FKdlloyk177kp3p3ey8ap24fqh2` (`prenda_id`),
  CONSTRAINT `FKdlloyk177kp3p3ey8ap24fqh2` FOREIGN KEY (`prenda_id`) REFERENCES `prenda` (`id_prenda`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pieza_prenda`
--

LOCK TABLES `pieza_prenda` WRITE;
/*!40000 ALTER TABLE `pieza_prenda` DISABLE KEYS */;
INSERT INTO `pieza_prenda` VALUES (1,1,_binary '','Cuello',1),(2,2,_binary '','Costado',1),(3,2,_binary '','Mangas',1),(4,1,_binary '','Espalda',1),(5,1,_binary '','Frente',1),(6,1,_binary '','Cuello',2),(7,2,_binary '','Mangas',2),(8,1,_binary '','Espalda',2),(9,1,_binary '','Frente',2),(10,1,_binary '','Puños',2),(11,2,_binary '','Pierna',3),(12,2,_binary '','Bolsillos',3),(13,1,_binary '','Cintura',3),(14,1,_binary '','Bragueta',3),(15,1,_binary '','Cintura',4),(16,1,_binary '','Cuerpo',4),(17,1,_binary '','Forro',4);
/*!40000 ALTER TABLE `pieza_prenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prenda`
--

DROP TABLE IF EXISTS `prenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prenda` (
  `id_prenda` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL,
  `costo_total` decimal(38,2) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_prenda`),
  UNIQUE KEY `UK5ff9calexcg0wpjp0m8n0xo4u` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prenda`
--

LOCK TABLES `prenda` WRITE;
/*!40000 ALTER TABLE `prenda` DISABLE KEYS */;
INSERT INTO `prenda` VALUES (1,'P001',25.50,_binary '','Polo'),(2,'C001',40.00,_binary '','Camisa'),(3,'P002',55.00,_binary '','Pantalón'),(4,'F001',30.00,_binary '','Falda');
/*!40000 ALTER TABLE `prenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol_usuario`
--

DROP TABLE IF EXISTS `rol_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol_usuario` (
  `id_rol` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol_usuario`
--

LOCK TABLES `rol_usuario` WRITE;
/*!40000 ALTER TABLE `rol_usuario` DISABLE KEYS */;
INSERT INTO `rol_usuario` VALUES (1,'Administrador'),(2,'Supervisor'),(3,'Operario');
/*!40000 ALTER TABLE `rol_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_descuento`
--

DROP TABLE IF EXISTS `tipo_descuento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_descuento` (
  `id_detalle_descuento` bigint NOT NULL AUTO_INCREMENT,
  `monto` decimal(38,2) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_detalle_descuento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_descuento`
--

LOCK TABLES `tipo_descuento` WRITE;
/*!40000 ALTER TABLE `tipo_descuento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_descuento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `changed_pass` bit(1) NOT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `dni` varchar(255) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `num_celular` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `rol_usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UKma71x4n4tydibsd9qt0m71le7` (`dni`),
  UNIQUE KEY `UK863n1y3x0jalatoir4325ehal` (`username`),
  KEY `FKhldivvrmovo1lkxebk495cecv` (`rol_usuario_id`),
  CONSTRAINT `FKhldivvrmovo1lkxebk495cecv` FOREIGN KEY (`rol_usuario_id`) REFERENCES `rol_usuario` (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,_binary '','admin@sistema.com','10000001',_binary '','Administrador General','900000001','$2a$12$7SW6dd16qcrYSdV0L4Uzp.qzCEe6ricYOH9fdr1r/bGlF2ItBun4a','admin',1),(2,_binary '','supervisor@sistema.com','10000002',_binary '','Supervisor de Producción','900000002','$2a$12$7SW6dd16qcrYSdV0L4Uzp.qzCEe6ricYOH9fdr1r/bGlF2ItBun4a','supervisor',2),(3,_binary '','operario@sistema.com','10000003',_binary '\0','Operario de Línea','900000003','$2a$12$7SW6dd16qcrYSdV0L4Uzp.qzCEe6ricYOH9fdr1r/bGlF2ItBun4a','operario',3);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-09 18:12:45
