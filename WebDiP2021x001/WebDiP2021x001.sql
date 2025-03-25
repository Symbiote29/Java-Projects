-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 21, 2022 at 05:25 PM
-- Server version: 5.5.62-0+deb8u1
-- PHP Version: 7.2.25-1+0~20191128.32+debian8~1.gbp108445

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `WebDiP2021x001`
--
CREATE DATABASE IF NOT EXISTS `WebDiP2021x001` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `WebDiP2021x001`;

-- --------------------------------------------------------

--
-- Table structure for table `dnevnik`
--

CREATE TABLE `dnevnik` (
  `dnevnik_id` int(11) NOT NULL,
  `korisnik_id` int(11) NOT NULL,
  `tip_radnje` varchar(50) NOT NULL,
  `upit` varchar(255) DEFAULT NULL,
  `datum_radnje` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dnevnik`
--

INSERT INTO `dnevnik` (`dnevnik_id`, `korisnik_id`, `tip_radnje`, `upit`, `datum_radnje`) VALUES
(1, 6, 'prijava', NULL, '2022-05-02 00:00:00'),
(2, 6, 'odjava', NULL, '2022-05-31 00:00:00'),
(3, 6, 'nova narudzba', NULL, '2022-07-12 00:00:00'),
(4, 7, 'prijava', NULL, '2022-06-07 00:00:00'),
(5, 7, 'dodana piva', '"INSERT INTO vrsta_piva (naziv, cijena, rokTrajanja, opis, slika, ocjena, receptID, zemlja_podrijetla_id, moderator_id) \r\n            VALUES (\'pivo25\', \'31 HRK\',\'2025-06-06\',\'Pivo 25\',\'pivo25.jpg\',\'3\',\'2\',\'7\', \'7\')";', '2022-06-07 00:00:00'),
(6, 7, 'odjava', NULL, '2022-06-11 00:00:00'),
(7, 6, 'pregled galerije', NULL, '2022-07-02 00:00:00'),
(8, 7, 'azuriran popis piva', '"UPDATE popispiva SET nazivPive = \'test1\' WHERE nazivPive = \'test\'";', '2022-06-10 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `dz4_kolacici`
--

CREATE TABLE `dz4_kolacici` (
  `idKolacici` int(11) NOT NULL,
  `Tip_kolacica` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dz4_kolacici`
--

INSERT INTO `dz4_kolacici` (`idKolacici`, `Tip_kolacica`) VALUES
(1, 'Nuzni'),
(2, 'Marketinski'),
(3, 'Analiticki');

-- --------------------------------------------------------

--
-- Table structure for table `dz4_korisnik`
--

CREATE TABLE `dz4_korisnik` (
  `idKorisnik` int(11) NOT NULL,
  `Ime` varchar(255) NOT NULL,
  `Prezime` varchar(255) NOT NULL,
  `Godina_rodenja` date NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Korisnicko_ime` varchar(255) NOT NULL,
  `Lozinka` varchar(255) NOT NULL,
  `PotvrdaLozinke` varchar(255) NOT NULL,
  `UlogaKorisnika` int(11) NOT NULL,
  `Kolacici_idKolacici` int(11) NOT NULL,
  `BrojPokusaja` int(11) NOT NULL,
  `Blokiran` int(11) NOT NULL,
  `otp` varchar(50) NOT NULL,
  `verify` int(11) NOT NULL,
  `datumRegistracije` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dz4_korisnik`
--

INSERT INTO `dz4_korisnik` (`idKorisnik`, `Ime`, `Prezime`, `Godina_rodenja`, `Email`, `Korisnicko_ime`, `Lozinka`, `PotvrdaLozinke`, `UlogaKorisnika`, `Kolacici_idKolacici`, `BrojPokusaja`, `Blokiran`, `otp`, `verify`, `datumRegistracije`) VALUES
(1, 'Filip', 'Antunovic', '2000-09-29', 'fantunovi@foi.hr', 'fantunovi', 'ftest1', 'ftest1', 4, 3, 2, 0, '1', 1, '0000-00-00 00:00:00'),
(2, 'Pero', 'Peric', '2005-05-02', 'pperic@foi.hr', 'pperic', 'pero123', 'pero123', 1, 1, 0, 0, '1Wud3', 1, '0000-00-00 00:00:00'),
(3, 'Jure', 'Juric', '2007-05-04', 'jjuric@foi.hr', 'jjuric', 'zfEcR', 'zfEcR', 2, 1, 2, 1, 'limjt', 1, '0000-00-00 00:00:00'),
(4, 'Karlo', 'Karlic', '2006-05-15', 'kkarlic@foi.hr', 'kkarlic', '4FeRb', '4FeRb', 3, 2, 0, 0, '965789', 1, '0000-00-00 00:00:00'),
(5, 'stipe', 'stipic', '2021-10-20', 'sstipic@foi.hr', 'sstipic', 'stipe123', 'stipe123', 2, 1, 1, 1, '1ftea', 1, '2022-06-14 17:14:26'),
(6, 'marko', 'markic', '2015-01-20', 'mmarkic@foi.hr', 'mmarkic', 'marko321', 'marko321', 2, 1, 0, 0, '2thcd', 1, '2022-06-14 19:11:49'),
(7, 'duje', 'dujic', '2016-09-20', 'ddujic@foi.hr', 'ddujic', 'duje942', 'duje942', 3, 1, 0, 0, 'iSEJs', 1, '2022-06-14 19:13:23'),
(8, 'maja', 'majic', '0000-00-00', 'mmajic@foi.hr', 'mmajic', 'maja382', 'maja382', 2, 1, 0, 0, '3jsv3', 1, '2022-06-14 19:43:44'),
(10, 'slavko', 'slavkic', '0000-00-00', 'sslavkic@foi.hr', 'sslavkic', 'slavko944', 'slavko944', 2, 1, 0, 0, 'jGZm0', 1, '2022-06-14 19:47:58'),
(11, 'ivo', 'ivic', '0000-00-00', 'iivic@foi.hr', 'iivic', 'ivo973', 'ivo973', 3, 1, 0, 0, 'evjhT', 1, '2022-06-14 22:09:02'),
(12, 'danijel', 'danic', '2022-06-06', 'ddanic@foi.hr', 'ddanic', 'dani456', 'dani456', 2, 2, 0, 0, '4t5ff', 0, '2022-06-22 00:00:00'),
(13, 'duro', 'duric', '2021-10-20', 'dduric@foi.hr', 'dduric', 'duro987', 'duro987', 2, 1, 0, 0, 'W52Sd', 1, '2022-06-14 23:32:57'),
(19, 'test', 'test', '2000-01-01', 'vaven70440@mahazai.com', 'test', 'test123#', 'test123#', 2, 1, 0, 0, 'Yx16D', 1, '2022-06-21 16:53:21');

-- --------------------------------------------------------

--
-- Table structure for table `dz4_novi_sadrzaj`
--

CREATE TABLE `dz4_novi_sadrzaj` (
  `idNovi_sadrzaj` int(11) NOT NULL,
  `File` varchar(255) NOT NULL,
  `Kategorija` varchar(255) NOT NULL,
  `Vrijeme_kreiranja` datetime NOT NULL,
  `Opis` varchar(255) NOT NULL,
  `Mjesec` date NOT NULL,
  `Boja` varchar(255) NOT NULL,
  `Mobitel` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dz4_novi_sadrzaj`
--

INSERT INTO `dz4_novi_sadrzaj` (`idNovi_sadrzaj`, `File`, `Kategorija`, `Vrijeme_kreiranja`, `Opis`, `Mjesec`, `Boja`, `Mobitel`) VALUES
(1, 'ace.jpg', 'One Piece', '2022-05-01 00:00:00', 'Aceova slika', '2022-05-01', 'Narancasta', '091-950-3387'),
(2, 'Grupa_5.pdf', 'One Piece', '2022-05-06 00:00:00', 'Grupa_5 pdf', '2022-05-01', 'Bijela', '099-523-4823');

-- --------------------------------------------------------

--
-- Table structure for table `dz4_ulogakorisnika`
--

CREATE TABLE `dz4_ulogakorisnika` (
  `idDZ4_UlogaKorisnika` int(11) NOT NULL,
  `Uloga` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dz4_ulogakorisnika`
--

INSERT INTO `dz4_ulogakorisnika` (`idDZ4_UlogaKorisnika`, `Uloga`) VALUES
(1, 'Neregistrirani korisnik'),
(2, 'Registrirani korisnik'),
(3, 'Moderator'),
(4, 'Administrator');

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `idkorisnik` int(11) NOT NULL,
  `korisnik_ime` varchar(45) NOT NULL,
  `korisnik_prezime` varchar(45) NOT NULL,
  `korisnik_korisnicko_ime` varchar(45) NOT NULL,
  `korisnik_lozinka` varchar(45) NOT NULL,
  `korisnik_email` varchar(45) NOT NULL,
  `korisnik_datum_pristupanja` datetime NOT NULL,
  `korisnik_datum_prihvacanja_kolacica` datetime NOT NULL,
  `korisnik_broj_prijava` int(11) NOT NULL,
  `tip_korisnika_idtip_korisnika` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`idkorisnik`, `korisnik_ime`, `korisnik_prezime`, `korisnik_korisnicko_ime`, `korisnik_lozinka`, `korisnik_email`, `korisnik_datum_pristupanja`, `korisnik_datum_prihvacanja_kolacica`, `korisnik_broj_prijava`, `tip_korisnika_idtip_korisnika`) VALUES
(1, 'Marko', 'Markic', 'mmarkic', '2016lisbon', 'mmarkic@inet.hr', '2022-04-11 05:00:00', '2022-04-11 05:02:00', 2, 1),
(2, 'Petar', 'Petric', 'ppetric', '2534santiago', 'ppetric@gmai.com', '2022-06-15 09:30:00', '2022-06-15 09:32:00', 1, 2),
(3, 'Ivan', 'Ivic', 'iivic', '7394tokyo', 'iivic@gmai.com', '2022-08-30 09:13:25', '2022-08-30 09:15:09', 3, 3),
(4, 'Jure', 'Juric', 'jjuric', '6193madrid', 'jjuric@inet.hr', '2022-02-10 15:22:33', '2022-02-10 10:26:15', 3, 4),
(5, 'Denis', 'Denisevic', 'ddenisev', '8521helsinki', 'ddenisev@foi.hr', '2022-06-22 16:30:20', '2022-06-22 16:32:41', 2, 4),
(6, 'Filip', 'Filipov', 'ffilipov', '8323nairobi', 'ffilipov@gmail.com', '2022-01-08 20:16:16', '2022-01-08 20:18:37', 1, 2),
(7, 'Andrija', 'Andric', 'aandric', '99235ghana', 'aandric@foi.hr', '2022-10-10 07:48:35', '2022-10-10 07:50:26', 1, 3),
(8, 'Duje', 'Dujic', 'ddujic', 'haiti3562', 'ddujic@inet.hr', '2022-05-10 15:30:30', '2022-05-10 15:32:15', 2, 4),
(9, 'Karlo', 'Karlic', 'kkarlic', 'myanmar9753', 'kkarlic@gmail.com', '2022-07-27 02:14:32', '2022-07-27 02:16:35', 2, 4),
(10, 'Darijo', 'Dadic', 'ddadic', '84dubai023', 'ddadic@inet.hr', '2021-12-10 08:16:19', '2021-12-10 08:17:24', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `nacin_placanja`
--

CREATE TABLE `nacin_placanja` (
  `idnacin_placanja` int(11) NOT NULL,
  `nacin_placanja_naziv` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `nacin_placanja`
--

INSERT INTO `nacin_placanja` (`idnacin_placanja`, `nacin_placanja_naziv`) VALUES
(1, 'Gotovina'),
(2, 'Kartica'),
(3, 'Paypal'),
(4, 'CryptoValute');

-- --------------------------------------------------------

--
-- Table structure for table `narudzba`
--

CREATE TABLE `narudzba` (
  `idnarudzba` int(11) NOT NULL,
  `narudzba_stavke` varchar(45) NOT NULL,
  `narudzba_status` varchar(45) NOT NULL,
  `racunStatus` varchar(50) NOT NULL,
  `narudzba_datum` datetime NOT NULL,
  `racun_idracun` int(11) NOT NULL,
  `ukupniIznosRacuna` varchar(50) NOT NULL,
  `placeniIznosRacuna` varchar(50) NOT NULL,
  `idkorisnik` int(11) NOT NULL,
  `idPivnice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `narudzba`
--

INSERT INTO `narudzba` (`idnarudzba`, `narudzba_stavke`, `narudzba_status`, `racunStatus`, `narudzba_datum`, `racun_idracun`, `ukupniIznosRacuna`, `placeniIznosRacuna`, `idkorisnik`, `idPivnice`) VALUES
(1, 'APA, C4, Fireball', 'Isporuceno', 'Placeno u potpunosti', '2022-04-20 03:31:23', 1, '170HRK', '170HRK', 1, 1),
(2, 'Brale, C4', 'U skladistu', 'Placeno u potpunosti', '2022-04-25 00:00:00', 3, '83 HRK', '83 HRK', 2, 1),
(3, 'ThroatTwister', 'U isporuci', 'Djelomicno placeno', '2022-05-01 12:00:00', 2, '50 HRK', '25 HRK', 3, 1),
(4, 'Brutislav', 'U isporuci', 'Djelomicno placeno', '2022-08-13 08:00:00', 7, '97 HRK', '60 HRK', 4, 2),
(5, 'Brale', 'U skladistu', 'Nije placeno', '2022-09-30 14:00:00', 8, '50 HRK', '0 HRK', 5, 2),
(6, 'Guiness', 'Isporuceno', 'Placeno u potpunosti', '2022-04-10 10:00:00', 9, '160 HRK', '160 HRK', 6, 2),
(7, 'Hill 438', 'Isporuceno', 'Placeno u potpunosti', '2022-05-11 08:00:00', 5, '83 HRK', '83 HRK', 7, 2),
(8, 'BOH Beginning, APA', 'U skladistu', 'Placeno u potpunosti', '2022-07-29 16:00:00', 6, '130 HRK', '130 HRK', 8, 2),
(9, 'Hladovina', 'Isporuceno', 'Djelomicno placeno', '2022-05-19 20:00:00', 4, '65 HRK', '50 HRK', 9, 3),
(10, 'Guiness, Brale, Brutislav', 'Isporuceno', 'Placeno u potpunosti', '2022-05-19 14:00:00', 10, '240 HRK', '240 HRK', 10, 3),
(11, 'ThroatTwister, Guiness', 'Isporuceno', 'Djelomicno placeno', '2021-10-13 12:18:37', 11, '95 HRK', '95 HRK', 11, 4),
(12, 'Hill 438', 'U skladistu', 'Nije placeno', '2022-06-16 00:00:00', 11, '83 HRK', '0 HRK', 13, 4),
(13, 'Brale, C4', 'U skladistu', 'Nije placeno', '2021-09-05 12:18:37', 11, '83 HRK', '0 HRK', 14, 4),
(14, 'Brale', 'Isporuceno', 'Placeno u potpunosti', '2022-06-16 00:00:00', 9, '106 HRK', '106 HRK', 6, 5),
(15, 'Guiness', 'Isporuceno', 'Placeno u potpunosti', '2022-06-17 00:00:00', 9, '55 HRK', '55 HRK', 6, 5),
(16, 'APA', 'Isporuceno', 'Placeno u potpunosti', '2022-06-01 00:00:00', 9, '30 HRK', '30 HRK', 6, 5),
(17, 'C4', 'Isporuceno', 'Placeno u potpunosti', '2022-06-08 00:00:00', 9, '25 HRK', '25 HRK', 6, 5),
(18, 'Fireball', 'Isporuceno', 'Placeno u potpunosti', '2022-05-23 00:00:00', 9, '40 HRK', '40 HRK', 14, 5),
(19, 'ThroatTwister', 'Isporuceno', 'Placeno u potpunosti', '2022-05-18 00:00:00', 9, '35 HRK', '35 HRK', 14, 1);

-- --------------------------------------------------------

--
-- Table structure for table `novosti`
--

CREATE TABLE `novosti` (
  `novosti_id` int(11) NOT NULL,
  `nazivPiva` varchar(50) NOT NULL,
  `novost` varchar(50) NOT NULL,
  `datumNovosti` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `novosti`
--

INSERT INTO `novosti` (`novosti_id`, `nazivPiva`, `novost`, `datumNovosti`) VALUES
(1, 'Karlovacko', 'novost1', '2022-06-01'),
(2, 'Guiness', 'novost2', '2022-06-08'),
(3, 'Hill 438', 'novost3', '2022-06-11'),
(4, 'ThroatTwister', 'novost4', '2022-06-12'),
(5, 'Hladovina', 'novost5', '2022-06-15'),
(6, 'Fireball', 'novost6', '2022-06-23');

-- --------------------------------------------------------

--
-- Table structure for table `pivnica`
--

CREATE TABLE `pivnica` (
  `pivnica_id` int(11) NOT NULL,
  `nazivPivnice` varchar(45) NOT NULL,
  `adresaPivnice` varchar(45) NOT NULL,
  `moderatorID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pivnica`
--

INSERT INTO `pivnica` (`pivnica_id`, `nazivPivnice`, `adresaPivnice`, `moderatorID`) VALUES
(1, 'Connaught Bar', 'London', 3),
(2, 'Dante', 'New York', 3),
(3, 'The Clumsies', 'Athens', 3),
(4, 'Atlas', 'Singapore', 5),
(5, 'Tyer', 'London', 5),
(6, 'Kwant', 'London', 5),
(7, 'Floreria Atlantico', 'Buenos Aires', 5),
(8, 'Coa', 'Hong Kong', 3),
(9, 'Jigger and Pony', 'Singapore', 3),
(10, 'The SG Club', 'Tokyo', 3),
(11, 'Maybe Sammy', 'Sydney', 5),
(12, 'test', 'test', 3),
(13, 'Licoreria Limantour', 'Mexico City', 5),
(14, 'Medvedgrad pivovara', 'Zagreb', 3);

-- --------------------------------------------------------

--
-- Table structure for table `popispiva`
--

CREATE TABLE `popispiva` (
  `popisPiva_id` int(11) NOT NULL,
  `nazivPive` varchar(50) NOT NULL,
  `cijena` varchar(50) NOT NULL,
  `ocjena` int(11) NOT NULL,
  `pivnica_id` int(11) NOT NULL,
  `moderator_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `popispiva`
--

INSERT INTO `popispiva` (`popisPiva_id`, `nazivPive`, `cijena`, `ocjena`, `pivnica_id`, `moderator_id`) VALUES
(1, 'Fireball', '45 HRK', 4, 13, 7),
(2, 'ThroatTwister', '33 HRK', 3, 7, 7),
(3, 'Guiness', '51 HRK', 5, 13, 7),
(4, 'Hill 438', '44 HRK', 5, 10, 4),
(5, 'Brutislav', '32 HRK', 3, 3, 7),
(6, 'Brown Ale', '27 HRK', 1, 13, 7),
(7, 'Guiness', '51 HRK', 5, 8, 4),
(8, 'Fireball', '45 HRK', 4, 8, 4),
(9, 'India Pale Ale', '40 HRK', 3, 10, 4),
(10, 'BOH - Beginning', '38 HRK', 3, 10, 11),
(11, 'f', 'f', 3, 3, 4);

-- --------------------------------------------------------

--
-- Table structure for table `popis_narudzbi`
--

CREATE TABLE `popis_narudzbi` (
  `idpopis_narudzbi` int(11) NOT NULL,
  `popis_narudzbi_iznos` float NOT NULL,
  `popis_narudzbi_datum` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `popis_narudzbi`
--

INSERT INTO `popis_narudzbi` (`idpopis_narudzbi`, `popis_narudzbi_iznos`, `popis_narudzbi_datum`) VALUES
(1, 175, '2022-04-20 10:19:20'),
(2, 153, '2022-05-06 13:43:19'),
(3, 263, '2022-01-28 07:29:22'),
(4, 315, '2022-06-15 16:07:21'),
(5, 270, '2022-07-24 06:40:53'),
(6, 120, '2022-08-05 03:41:19'),
(7, 195, '2022-09-10 10:09:24'),
(8, 95, '2022-10-15 17:37:26'),
(9, 155, '2022-07-27 18:37:36'),
(10, 230, '2022-11-04 12:32:30');

-- --------------------------------------------------------

--
-- Table structure for table `racun`
--

CREATE TABLE `racun` (
  `idracun` int(11) NOT NULL,
  `racun_stavke` varchar(45) NOT NULL,
  `racun_iznos` varchar(45) NOT NULL,
  `racun_status` varchar(45) NOT NULL,
  `racun_datum` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `racun`
--

INSERT INTO `racun` (`idracun`, `racun_stavke`, `racun_iznos`, `racun_status`, `racun_datum`) VALUES
(1, 'APA, C4, Fireball', '170 HRK', 'Placeno u potpunosti', '2022-04-20 03:23:48'),
(2, 'ThroatTwister, APA', '95 HRK', 'Djelomicno placeno', '2022-05-01 12:12:21'),
(3, 'Brale, C4', '83 HRK', 'Placeno u potpunosti', '2022-04-25 16:07:23'),
(4, 'Hladovina', '65 HRK', 'Placeno u potpunosti', '2022-05-19 20:43:16'),
(5, 'Hill 438', '83 HRK', 'Placeno u potpunosti', '2022-05-11 08:07:48'),
(6, 'BOH beginning, APA', '130 HRK', 'Placeno u potpunosti', '2022-07-29 16:50:30'),
(7, 'Brutislav', '97 HRK', 'Djelomicno placeno', '2022-08-13 08:35:49'),
(8, 'Brale', '50 HRK', 'Placeno u potpunosti', '2022-09-30 14:29:20'),
(9, 'Guiness', '160 HRK', 'Placeno u potpunosti', '2022-04-10 10:26:27'),
(10, 'Guiness, C4, Brutislav', '240 HRK', 'Placeno u potpunosti', '2022-05-19 14:34:37'),
(11, 'testing', '166 HRK', 'Placeno u potpunosti', '2022-04-04 12:10:37');

-- --------------------------------------------------------

--
-- Table structure for table `racun_has_nacin_placanja`
--

CREATE TABLE `racun_has_nacin_placanja` (
  `racun_idracun` int(11) NOT NULL,
  `nacin_placanja_idnacin_placanja` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `racun_has_nacin_placanja`
--

INSERT INTO `racun_has_nacin_placanja` (`racun_idracun`, `nacin_placanja_idnacin_placanja`) VALUES
(3, 1),
(7, 1),
(1, 2),
(5, 2),
(9, 2),
(2, 3),
(6, 3),
(8, 3),
(4, 4),
(10, 4);

-- --------------------------------------------------------

--
-- Table structure for table `recepti`
--

CREATE TABLE `recepti` (
  `idrecepti` int(11) NOT NULL,
  `recepti_sastojci` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `recepti`
--

INSERT INTO `recepti` (`idrecepti`, `recepti_sastojci`) VALUES
(1, 'Pale ale, Psenicni saldo, Cascade, Magnum SL, Wyeast, sjemenke korijandra, suhe narancine korice, secer za flasiranje'),
(2, 'Pale ale, Vienna, Cara 50, Citra, Wyeast, secer za flasiranje'),
(3, 'Roasted Barley, Chocolate, Cara 50, Pale ale, Bobek, Fermentis Safale US-05, secer za flasiranje'),
(4, 'Pale ale, Munich, Cara 20, Aurora, Cascade, Citra, Fermentis Safale US-05, secer za flasiranje'),
(5, 'Pilsner, Munich, Bobek, Saflager W-34, secer za flasiranje'),
(6, 'Deep Purple, RED IPA, Comet'),
(7, 'Columbus, Mosaic, Magnum, Malted Barley, secer za flasiranje, Hops'),
(8, 'Amarillo, Magnum, Cascade, Centennial, Chinook, Simcoe, Hops, Malted Barley, secer za flasiranje'),
(9, 'Centennial, Citra, Simcoe, Oktawia, Malted Barley, Hops, secer za flasiranje'),
(10, 'Magnum, Chinook, Malted Barley, Hops, secer za flasiranje');

-- --------------------------------------------------------

--
-- Table structure for table `tip_korisnika`
--

CREATE TABLE `tip_korisnika` (
  `idtip_korisnika` int(11) NOT NULL,
  `tip_korisnika_naziv` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tip_korisnika`
--

INSERT INTO `tip_korisnika` (`idtip_korisnika`, `tip_korisnika_naziv`) VALUES
(1, 'Neregistrirani korisnik'),
(2, 'Registrirani korisnik'),
(3, 'Moderator'),
(4, 'Administrator');

-- --------------------------------------------------------

--
-- Table structure for table `vrsta_piva`
--

CREATE TABLE `vrsta_piva` (
  `idvrsta_piva` int(11) NOT NULL,
  `naziv` varchar(45) NOT NULL,
  `cijena` varchar(45) NOT NULL,
  `rokTrajanja` date NOT NULL,
  `opis` varchar(255) NOT NULL,
  `slika` varchar(50) NOT NULL,
  `ocjena` int(11) NOT NULL,
  `receptID` int(11) NOT NULL,
  `zemlja_podrijetla_id` int(11) NOT NULL,
  `moderator_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vrsta_piva`
--

INSERT INTO `vrsta_piva` (`idvrsta_piva`, `naziv`, `cijena`, `rokTrajanja`, `opis`, `slika`, `ocjena`, `receptID`, `zemlja_podrijetla_id`, `moderator_id`) VALUES
(1, 'American Pale Ale', '50 HRK', '2022-05-10', 'Predstavnik Sierra Nevada Pale Ale, skracenica APA, u kaficima poznat kao Nova Runda', 'AmericanPaleAle.jpg', 3, 0, 8, 7),
(2, 'Fireball', '45 HRK', '2022-07-15', 'Kombinacija Deep Purple-a, RED IPA-e i Comet hmelja. Fireball se radi samo tijekom jesenskog i zimskog perioda.', 'Fireball.jpg', 4, 0, 7, 7),
(3, 'India Pale Ale', '40 HRK', '2022-05-23', 'Eksplozivno pivo, Drugo znacenje C4 IPA je kombinacija hmeljeva koji su koristeni u ovom pivu: Cascade, Centennial, Chinook  i Citra', 'IndiaPaleAle.jpg', 3, 0, 9, 7),
(4, 'Hladovina', '30 HRK', '2023-05-18', 'Pivo koje se pojavljuje u jednom od najpoznatijih stripova ikad - Alan Fordu', 'Hladovina.jpg', 2, 0, 1, 4),
(5, 'ThroatTwister', '33 HRK', '2023-08-29', 'ThroatTwister pivo koje je takoder poznato i pod nazivom Tetejac, je pivo koje ne smijete podcjeniti, jer ovo pivo cesto prate hashtagovi #ljudipijtevode', 'ThroatTwister.jpg', 3, 0, 7, 4),
(6, 'Brown Ale', '27 HRK', '2024-11-17', 'Pivo koje je nastalo u Splitu od svih Gradova, BrAle spada u American Brown Ale-ove, zahmeljeno tamno pivo kojim se prozima lagana przenost.', 'BrownAle.jpeg', 1, 0, 1, 11),
(7, 'Hill 438', '44 HRK', '2023-01-22', 'Hill 438 spada u India Pale Ale-ove, te je ono suho pitko pivo s gorkim zavrsetkom', 'Hill438.jpg', 5, 0, 9, 11),
(8, 'BOH - Beginning', '38 HRK', '2024-02-17', 'Ovo pivo spada u New England IPA-e, te je ovo prva piva koja je napravljena u kolaboraciji sa slovenskim proizvodacem Lobik', 'BOHBeginning.jfif', 3, 0, 11, 11),
(9, 'Brutislav', '32 HRK', '2023-09-22', 'Ovo pivo spada u Brut IPA-e, te proizlazi iz kolaboracije Hrvatske i Poljske', 'Ozujsko.jpg', 2, 0, 1, 11),
(10, 'Guiness', '51 HRK', '2025-11-24', 'Ovo je ostro tamno pivo s crvenom nijansom ,ravnotezom gorkih i slatkih nota i suhim zavrsetkom', 'Guiness.jpg', 5, 0, 3, 4),
(32, 'test1', 'd', '0000-00-00', 'd', 'd', 3, 3, 3, 4);

-- --------------------------------------------------------

--
-- Table structure for table `zemlja_podrijetla_piva`
--

CREATE TABLE `zemlja_podrijetla_piva` (
  `idzemlja_podrijetla_piva` int(11) NOT NULL,
  `nazivDrzave` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `zemlja_podrijetla_piva`
--

INSERT INTO `zemlja_podrijetla_piva` (`idzemlja_podrijetla_piva`, `nazivDrzave`) VALUES
(1, 'Hrvatska'),
(2, 'Njemacka'),
(3, 'Belgija'),
(4, 'Italija'),
(5, 'Rusija'),
(6, 'Spanjolska'),
(7, 'Mexico'),
(8, 'Amerika'),
(9, 'India'),
(10, 'Japan'),
(11, 'Engleska'),
(12, 'Grcka'),
(13, 'Singapore'),
(14, 'Kina'),
(15, 'Argentina'),
(16, 'Peru'),
(17, 'UAE'),
(25, 'd');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dnevnik`
--
ALTER TABLE `dnevnik`
  ADD PRIMARY KEY (`dnevnik_id`);

--
-- Indexes for table `dz4_kolacici`
--
ALTER TABLE `dz4_kolacici`
  ADD PRIMARY KEY (`idKolacici`);

--
-- Indexes for table `dz4_korisnik`
--
ALTER TABLE `dz4_korisnik`
  ADD PRIMARY KEY (`idKorisnik`);

--
-- Indexes for table `dz4_novi_sadrzaj`
--
ALTER TABLE `dz4_novi_sadrzaj`
  ADD PRIMARY KEY (`idNovi_sadrzaj`);

--
-- Indexes for table `dz4_ulogakorisnika`
--
ALTER TABLE `dz4_ulogakorisnika`
  ADD PRIMARY KEY (`idDZ4_UlogaKorisnika`);

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`idkorisnik`),
  ADD KEY `fk_korisnik_tip_korisnika1_idx` (`tip_korisnika_idtip_korisnika`);

--
-- Indexes for table `nacin_placanja`
--
ALTER TABLE `nacin_placanja`
  ADD PRIMARY KEY (`idnacin_placanja`);

--
-- Indexes for table `narudzba`
--
ALTER TABLE `narudzba`
  ADD PRIMARY KEY (`idnarudzba`),
  ADD KEY `fk_narudzba_racun1_idx` (`racun_idracun`),
  ADD KEY `fk_narudzba_korisnik1_idx` (`idkorisnik`);

--
-- Indexes for table `novosti`
--
ALTER TABLE `novosti`
  ADD PRIMARY KEY (`novosti_id`);

--
-- Indexes for table `pivnica`
--
ALTER TABLE `pivnica`
  ADD PRIMARY KEY (`pivnica_id`);

--
-- Indexes for table `popispiva`
--
ALTER TABLE `popispiva`
  ADD PRIMARY KEY (`popisPiva_id`);

--
-- Indexes for table `popis_narudzbi`
--
ALTER TABLE `popis_narudzbi`
  ADD PRIMARY KEY (`idpopis_narudzbi`);

--
-- Indexes for table `racun`
--
ALTER TABLE `racun`
  ADD PRIMARY KEY (`idracun`);

--
-- Indexes for table `racun_has_nacin_placanja`
--
ALTER TABLE `racun_has_nacin_placanja`
  ADD PRIMARY KEY (`racun_idracun`,`nacin_placanja_idnacin_placanja`),
  ADD KEY `fk_racun_has_nacin_placanja_nacin_placanja1_idx` (`nacin_placanja_idnacin_placanja`),
  ADD KEY `fk_racun_has_nacin_placanja_racun1_idx` (`racun_idracun`);

--
-- Indexes for table `recepti`
--
ALTER TABLE `recepti`
  ADD PRIMARY KEY (`idrecepti`);

--
-- Indexes for table `tip_korisnika`
--
ALTER TABLE `tip_korisnika`
  ADD PRIMARY KEY (`idtip_korisnika`);

--
-- Indexes for table `vrsta_piva`
--
ALTER TABLE `vrsta_piva`
  ADD PRIMARY KEY (`idvrsta_piva`);

--
-- Indexes for table `zemlja_podrijetla_piva`
--
ALTER TABLE `zemlja_podrijetla_piva`
  ADD PRIMARY KEY (`idzemlja_podrijetla_piva`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dnevnik`
--
ALTER TABLE `dnevnik`
  MODIFY `dnevnik_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `dz4_kolacici`
--
ALTER TABLE `dz4_kolacici`
  MODIFY `idKolacici` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `dz4_korisnik`
--
ALTER TABLE `dz4_korisnik`
  MODIFY `idKorisnik` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `dz4_novi_sadrzaj`
--
ALTER TABLE `dz4_novi_sadrzaj`
  MODIFY `idNovi_sadrzaj` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `dz4_ulogakorisnika`
--
ALTER TABLE `dz4_ulogakorisnika`
  MODIFY `idDZ4_UlogaKorisnika` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `narudzba`
--
ALTER TABLE `narudzba`
  MODIFY `idnarudzba` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `novosti`
--
ALTER TABLE `novosti`
  MODIFY `novosti_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `pivnica`
--
ALTER TABLE `pivnica`
  MODIFY `pivnica_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `popispiva`
--
ALTER TABLE `popispiva`
  MODIFY `popisPiva_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `vrsta_piva`
--
ALTER TABLE `vrsta_piva`
  MODIFY `idvrsta_piva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
--
-- AUTO_INCREMENT for table `zemlja_podrijetla_piva`
--
ALTER TABLE `zemlja_podrijetla_piva`
  MODIFY `idzemlja_podrijetla_piva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD CONSTRAINT `fk_korisnik_tip_korisnika1` FOREIGN KEY (`tip_korisnika_idtip_korisnika`) REFERENCES `tip_korisnika` (`idtip_korisnika`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `narudzba`
--
ALTER TABLE `narudzba`
  ADD CONSTRAINT `fk_narudzba_racun1` FOREIGN KEY (`racun_idracun`) REFERENCES `racun` (`idracun`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `racun_has_nacin_placanja`
--
ALTER TABLE `racun_has_nacin_placanja`
  ADD CONSTRAINT `fk_racun_has_nacin_placanja_nacin_placanja1` FOREIGN KEY (`nacin_placanja_idnacin_placanja`) REFERENCES `nacin_placanja` (`idnacin_placanja`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_racun_has_nacin_placanja_racun1` FOREIGN KEY (`racun_idracun`) REFERENCES `racun` (`idracun`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
