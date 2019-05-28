-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Mar 28 Mai 2019 à 23:42
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `leadelmarche`
--
CREATE DATABASE IF NOT EXISTS `leadelmarche` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `leadelmarche`;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `prenom` varchar(64) NOT NULL,
  `numCarteFidelite` int(11) NOT NULL,
  `mail` varchar(128) NOT NULL,
  `codePostal` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `client`
--

INSERT INTO `client` (`id`, `nom`, `prenom`, `numCarteFidelite`, `mail`, `codePostal`, `active`) VALUES
(1, 'WILFRIED', 'Isabella', 481489699, 'isabella.wil@gmail.com', 41460, 1),
(2, 'BEN MABROUK', 'Houssem', 722114292, 'cjhoussem@gmail.com', 78218, 1),
(3, 'ALFRED', 'Bernstein', 266391009, 'alfred007@hotmail.com', 78665, 1);

-- --------------------------------------------------------

--
-- Structure de la table `lignevente`
--

CREATE TABLE `lignevente` (
  `id` int(11) NOT NULL,
  `vente` int(11) NOT NULL,
  `produit` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `lignevente`
--

INSERT INTO `lignevente` (`id`, `vente`, `produit`) VALUES
(4, 5, 1),
(5, 6, 2),
(6, 6, 3),
(7, 7, 2),
(8, 8, 3),
(9, 8, 3),
(10, 8, 3),
(11, 9, 1),
(12, 9, 2),
(13, 10, 1),
(14, 11, 4),
(15, 11, 4),
(16, 12, 5),
(17, 13, 4),
(18, 14, 1),
(19, 14, 4),
(20, 14, 5);

-- --------------------------------------------------------

--
-- Structure de la table `personnel`
--

CREATE TABLE `personnel` (
  `id` int(11) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `prenom` varchar(64) NOT NULL,
  `adressePerso` varchar(128) NOT NULL,
  `adresseTravail` varchar(128) NOT NULL,
  `poste` varchar(64) NOT NULL,
  `superieur` int(11) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `numBadge` int(11) NOT NULL,
  `mdp` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `personnel`
--

INSERT INTO `personnel` (`id`, `nom`, `prenom`, `adressePerso`, `adresseTravail`, `poste`, `superieur`, `active`, `numBadge`, `mdp`) VALUES
(1, 'BEN MABROUK', 'Houssem', '12 Clos de la scandinavie', 'Rue Paul Pierre', 'Ingénieur', NULL, 1, 453656953, '09aaa3d717d2bb9cbc0d0141b84ec228'),
(2, 'BEN SALAH', 'Firas', '4 Tijani Mhamdi, EL OMRANE', 'Charguia', 'Ingénieur', NULL, 1, 1111606048, 'ab4f63f9ac65152575886860dde480a1'),
(3, 'KTATA', 'Hamza', 'Nabeul', 'La goulette', 'Technicien Supérieur', 2, 0, 1403623776, 'ab4f63f9ac65152575886860dde480a1'),
(4, 'FAIZAN', 'Taylor', '4457 Rhapsody Street', '533 Kennedy Court', 'Caissier', 2, 1, 639937544, 'ab4f63f9ac65152575886860dde480a1'),
(5, 'M DAWSON', 'Justin ', '2605 Pooz Street', '533 Kennedy Court', 'Caissier', 2, 1, 630557517, 'f1d11d95f9709c44f58b504c642d7378'),
(6, 'DJIKISTRA', 'Emma', 'Velizy', '533 Kennedy Court', 'Caissier', 1, 1, 1350254971, 'e7fa2996468522cdcc16e77ee98eea58'),
(7, 'BLANC', 'Émilie', 'Velizy 2', '533 Kennedy Court', 'Caissier', 1, 1, 352127675, 'c5a9bda335c14dd41ad9229111ca52fd');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `description` varchar(256) NOT NULL,
  `type` enum('ALIMENT','MEDECINE','APPAREIL_ELECTRONIQUE','AUTRE') NOT NULL,
  `prix` float NOT NULL,
  `pays` varchar(18) NOT NULL,
  `tva` float NOT NULL,
  `qteStock` int(11) NOT NULL,
  `nomFournisseur` varchar(64) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `produit`
--

INSERT INTO `produit` (`id`, `nom`, `description`, `type`, `prix`, `pays`, `tva`, `qteStock`, `nomFournisseur`, `active`) VALUES
(1, 'MegaPC2000', 'Ordinateur', 'APPAREIL_ELECTRONIQUE', 2500, 'Tunisie', 3, 97, 'Mega PC', 1),
(2, 'Lampe', 'Lampe à Acro en marbre', 'AUTRE', 150, 'France', 5, 49, 'MyFaktory FR', 1),
(3, 'Banane', 'Banane', 'ALIMENT', 3, 'Inde', 1.5, 997, 'Agidra', 0),
(4, 'AORUS K7 - Cherry MX Red', 'Clavier mécanique', 'APPAREIL_ELECTRONIQUE', 149.95, 'France', 6.25, 96, 'Mega PC', 1),
(5, 'AORUS RFB AIC NVMe SSD 1 To', 'Disque Dur SSD 1 To', 'APPAREIL_ELECTRONIQUE', 299.95, 'France', 6.25, 248, 'Mega PC', 1);

-- --------------------------------------------------------

--
-- Structure de la table `vente`
--

CREATE TABLE `vente` (
  `id` int(11) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `caissier` int(11) DEFAULT NULL,
  `client` int(11) DEFAULT NULL,
  `sous_total` float NOT NULL,
  `total` float NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `vente`
--

INSERT INTO `vente` (`id`, `nom`, `caissier`, `client`, `sous_total`, `total`, `active`) VALUES
(5, 'PT. 01', 4, NULL, 2000, 2060, 1),
(6, 'PT. 01', 4, NULL, 186.89, 196.185, 1),
(7, 'PT. 01', 4, 1, 184.9, 194.145, 1),
(8, 'PT. 01', 4, NULL, 5.97, 6.11925, 1),
(9, 'PT. 01', 4, NULL, 2650, 2732.5, 1),
(10, 'PT. 01', 4, 2, 2500, 2575, 1),
(11, 'PT. 01', 4, 3, 299.9, 318.644, 1),
(12, 'PT. 01', 4, 3, 299.95, 318.697, 1),
(13, 'PT. 01', 4, NULL, 149.95, 159.322, 1),
(14, 'PT. 01', 4, NULL, 2949.9, 3053.02, 1);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `lignevente`
--
ALTER TABLE `lignevente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `vente` (`vente`),
  ADD KEY `produit` (`produit`);

--
-- Index pour la table `personnel`
--
ALTER TABLE `personnel`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `numBadge` (`numBadge`),
  ADD KEY `superieur` (`superieur`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `vente`
--
ALTER TABLE `vente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `caissier` (`caissier`),
  ADD KEY `client` (`client`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `lignevente`
--
ALTER TABLE `lignevente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT pour la table `personnel`
--
ALTER TABLE `personnel`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `vente`
--
ALTER TABLE `vente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `lignevente`
--
ALTER TABLE `lignevente`
  ADD CONSTRAINT `lignevente_ibfk_1` FOREIGN KEY (`vente`) REFERENCES `vente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `lignevente_ibfk_2` FOREIGN KEY (`produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `personnel`
--
ALTER TABLE `personnel`
  ADD CONSTRAINT `personnel_ibfk_1` FOREIGN KEY (`superieur`) REFERENCES `personnel` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `vente`
--
ALTER TABLE `vente`
  ADD CONSTRAINT `vente_ibfk_1` FOREIGN KEY (`caissier`) REFERENCES `personnel` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `vente_ibfk_2` FOREIGN KEY (`client`) REFERENCES `client` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
