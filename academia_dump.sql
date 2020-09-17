-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.19 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para academia
CREATE DATABASE IF NOT EXISTS `academia` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `academia`;

-- Volcando estructura para tabla academia.alumnos_curso
CREATE TABLE IF NOT EXISTS `alumnos_curso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_alumno` int NOT NULL DEFAULT '0',
  `id_curso` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_id_alumno` (`id_alumno`),
  KEY `fk_id_curso` (`id_curso`),
  CONSTRAINT `fk_id_alumno` FOREIGN KEY (`id_alumno`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `fk_id_curso` FOREIGN KEY (`id_curso`) REFERENCES `cursos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla academia.alumnos_curso: ~0 rows (aproximadamente)
DELETE FROM `alumnos_curso`;
/*!40000 ALTER TABLE `alumnos_curso` DISABLE KEYS */;
INSERT INTO `alumnos_curso` (`id`, `id_alumno`, `id_curso`) VALUES
	(1, 4, 1),
	(2, 4, 2),
	(3, 5, 1),
	(4, 5, 3),
	(5, 6, 2),
	(6, 6, 3),
	(7, 7, 1),
	(8, 7, 3);
/*!40000 ALTER TABLE `alumnos_curso` ENABLE KEYS */;

-- Volcando estructura para tabla academia.cursos
CREATE TABLE IF NOT EXISTS `cursos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `curso` varchar(100) NOT NULL DEFAULT 'N/A',
  `identificador` varchar(10) NOT NULL DEFAULT 'N/A',
  `horas` int NOT NULL DEFAULT '0',
  `id_profesor` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_profesor` (`id_profesor`),
  CONSTRAINT `fk_id_profesor` FOREIGN KEY (`id_profesor`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla academia.cursos: ~2 rows (aproximadamente)
DELETE FROM `cursos`;
/*!40000 ALTER TABLE `cursos` DISABLE KEYS */;
INSERT INTO `cursos` (`id`, `curso`, `identificador`, `horas`, `id_profesor`) VALUES
	(1, 'Microsoft Office 2016', 'I001', 50, 1),
	(2, 'Experto en Desarrollo de Aplicaciones WEB y Bases de Datos', 'I002', 630, 2),
	(3, 'Desarrollo Avanzado con JAVA/JEE', 'I003', 510, 3);
/*!40000 ALTER TABLE `cursos` ENABLE KEYS */;

-- Volcando estructura para tabla academia.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `apellidos` varchar(100) NOT NULL DEFAULT '0',
  `rol` int NOT NULL DEFAULT '1' COMMENT '1: Alumno; 2: Profesor',
  `password` varchar(100) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla academia.usuarios: ~0 rows (aproximadamente)
DELETE FROM `usuarios`;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`id`, `nombre`, `apellidos`, `rol`, `password`) VALUES
	(1, 'Alain', 'Moles', 2, 'e10adc3949ba59abbe56e057f20f883e'),
	(2, 'Ander', 'Uraga', 2, 'e10adc3949ba59abbe56e057f20f883e'),
	(3, 'Dr.', 'Bacterio', 2, 'e10adc3949ba59abbe56e057f20f883e'),
	(4, 'Elier', 'Otero', 1, 'e10adc3949ba59abbe56e057f20f883e'),
	(5, 'Beatriz', 'Martinez', 1, 'e10adc3949ba59abbe56e057f20f883e'),
	(6, 'Asier', 'Mintegi', 1, 'e10adc3949ba59abbe56e057f20f883e'),
	(7, 'Lander', 'Bilbao', 1, 'e10adc3949ba59abbe56e057f20f883e');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
