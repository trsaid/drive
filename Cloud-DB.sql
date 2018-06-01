-- phpMyAdmin SQL Dump
-- version 4.4.15.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 01, 2018 at 01:55 AM
-- Server version: 5.6.34-log
-- PHP Version: 7.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cloud`
--

-- --------------------------------------------------------

--
-- Table structure for table `archives`
--

CREATE TABLE IF NOT EXISTS `archives` (
  `id` int(11) NOT NULL,
  `date_archive` datetime DEFAULT NULL,
  `nom_dos` varchar(224) NOT NULL,
  `id_dossier` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `dossier`
--

CREATE TABLE IF NOT EXISTS `dossier` (
  `id` int(11) NOT NULL,
  `nom_dossier` varchar(224) DEFAULT NULL,
  `date_upload` date DEFAULT NULL,
  `id_utilisateurs` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `droit_acces`
--

CREATE TABLE IF NOT EXISTS `droit_acces` (
  `id` int(11) NOT NULL,
  `id_utilisateurs` int(11) DEFAULT NULL,
  `id_dossier` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `est_archive`
--

CREATE TABLE IF NOT EXISTS `est_archive` (
  `id_archive` int(11) NOT NULL,
  `id_fichier` int(11) NOT NULL,
  `nom_fichier` varchar(224) NOT NULL,
  `date_archive` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `fichier`
--

CREATE TABLE IF NOT EXISTS `fichier` (
  `id` int(11) NOT NULL,
  `nom_fichier` varchar(255) NOT NULL,
  `poids_ko` int(11) NOT NULL,
  `date_upload` date NOT NULL,
  `type` char(25) DEFAULT NULL,
  `id_dossier` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `utilisateurs`
--

CREATE TABLE IF NOT EXISTS `utilisateurs` (
  `id` int(11) NOT NULL,
  `username` varchar(25) CHARACTER SET latin1 DEFAULT NULL,
  `nom` char(25) CHARACTER SET latin1 DEFAULT NULL,
  `prenom` char(25) CHARACTER SET latin1 DEFAULT NULL,
  `password` varchar(72) CHARACTER SET latin1 DEFAULT NULL,
  `email` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `niveau` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `archives`
--
ALTER TABLE `archives`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nom_dos` (`nom_dos`),
  ADD KEY `id_dossier` (`id_dossier`);

--
-- Indexes for table `dossier`
--
ALTER TABLE `dossier`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_dossier_id_utilisateurs` (`id_utilisateurs`),
  ADD KEY `nom_dossier` (`nom_dossier`);

--
-- Indexes for table `droit_acces`
--
ALTER TABLE `droit_acces`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Droit_acces_id_utilisateurs` (`id_utilisateurs`),
  ADD KEY `FK_Droit_acces_id_dossier` (`id_dossier`);

--
-- Indexes for table `est_archive`
--
ALTER TABLE `est_archive`
  ADD PRIMARY KEY (`id_archive`,`id_fichier`),
  ADD KEY `FK_est_archive_id_fichier` (`id_fichier`);

--
-- Indexes for table `fichier`
--
ALTER TABLE `fichier`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_fichier_id_dossier` (`id_dossier`);

--
-- Indexes for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `archives`
--
ALTER TABLE `archives`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `dossier`
--
ALTER TABLE `dossier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `droit_acces`
--
ALTER TABLE `droit_acces`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `fichier`
--
ALTER TABLE `fichier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `archives`
--
ALTER TABLE `archives`
  ADD CONSTRAINT `FK_archives_id_dossier` FOREIGN KEY (`id_dossier`) REFERENCES `dossier` (`id`),
  ADD CONSTRAINT `FK_archives_nom_dossier` FOREIGN KEY (`nom_dos`) REFERENCES `dossier` (`nom_dossier`);

--
-- Constraints for table `dossier`
--
ALTER TABLE `dossier`
  ADD CONSTRAINT `FK_dossier_id_utilisateurs` FOREIGN KEY (`id_utilisateurs`) REFERENCES `utilisateurs` (`id`);

--
-- Constraints for table `droit_acces`
--
ALTER TABLE `droit_acces`
  ADD CONSTRAINT `FK_Droit_acces_id_dossier` FOREIGN KEY (`id_dossier`) REFERENCES `dossier` (`id`),
  ADD CONSTRAINT `FK_Droit_acces_id_utilisateurs` FOREIGN KEY (`id_utilisateurs`) REFERENCES `utilisateurs` (`id`);

--
-- Constraints for table `est_archive`
--
ALTER TABLE `est_archive`
  ADD CONSTRAINT `FK_est_archive_id_Archives` FOREIGN KEY (`id_archive`) REFERENCES `archives` (`id`),
  ADD CONSTRAINT `FK_est_archive_id_fichier` FOREIGN KEY (`id_fichier`) REFERENCES `fichier` (`id`);

--
-- Constraints for table `fichier`
--
ALTER TABLE `fichier`
  ADD CONSTRAINT `FK_fichier_id_dossier` FOREIGN KEY (`id_dossier`) REFERENCES `dossier` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
