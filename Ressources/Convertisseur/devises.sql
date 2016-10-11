-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Mer 15 Janvier 2014 à 16:06
-- Version du serveur: 5.6.12-log
-- Version de PHP: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `devises`
--
CREATE DATABASE IF NOT EXISTS `devises` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `devises`;

-- --------------------------------------------------------

--
-- Structure de la table `monnaie`
--

CREATE TABLE IF NOT EXISTS `monnaie` (
  `monnaie` varchar(50) NOT NULL,
  `taux` double NOT NULL,
  PRIMARY KEY (`monnaie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `monnaie`
--

INSERT INTO `monnaie` (`monnaie`, `taux`) VALUES
('Dirham', 8.5656),
('Dollars CA', 1.1),
('Dollars US', 1),
('Euro', 0.7697),
('Franc', 5.049),
('Franc CFA', 503.17),
('Livre', 0.6405),
('Yen', 76.6908);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
