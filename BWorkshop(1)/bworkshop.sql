-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 22, 2023 at 04:51 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bworkshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `sparepart`
--

CREATE TABLE `sparepart` (
  `SparePartID` char(5) NOT NULL,
  `SparePartName` varchar(255) NOT NULL,
  `Brand` varchar(255) NOT NULL,
  `Price` int(11) NOT NULL,
  `Stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sparepart`
--

INSERT INTO `sparepart` (`SparePartID`, `SparePartName`, `Brand`, `Price`, `Stock`) VALUES
('SP001', 'shock breaker ohlins', 'ohlins', 100000, 5),
('SP002', 'r9 misano exhaust', 'r9', 200000, 4),
('SP003', 'rizoma rear view mirror', 'rizoma', 150000, 2),
('SP004', 'brembo brakes', 'brembo', 1350000, 4),
('SP005', 'akrapopikskz exhaust', 'akrapovic', 145000, 3);

-- --------------------------------------------------------

--
-- Table structure for table `sparepartcart`
--

CREATE TABLE `sparepartcart` (
  `SparePartID` char(5) NOT NULL,
  `UserID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sparepartcart`
--

INSERT INTO `sparepartcart` (`SparePartID`, `UserID`, `Quantity`) VALUES
('SP001', 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `transactiondetail`
--

CREATE TABLE `transactiondetail` (
  `TransactionID` int(11) NOT NULL,
  `SparePartID` char(5) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactiondetail`
--

INSERT INTO `transactiondetail` (`TransactionID`, `SparePartID`, `Quantity`) VALUES
(1, 'SP003', 3),
(2, 'SP001', 2),
(3, 'SP003', 1),
(4, 'SP004', 5),
(5, 'SP002', 1),
(6, 'SP003', 2),
(7, 'SP005', 1);

-- --------------------------------------------------------

--
-- Table structure for table `transactionheader`
--

CREATE TABLE `transactionheader` (
  `TransactionID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `TransactionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactionheader`
--

INSERT INTO `transactionheader` (`TransactionID`, `UserID`, `TransactionDate`) VALUES
(1, 1, '2022-06-05'),
(2, 1, '2022-06-03'),
(3, 1, '2022-05-12'),
(4, 1, '2022-05-25'),
(5, 1, '2022-06-14'),
(6, 1, '2022-06-14'),
(7, 1, '2022-06-14');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL,
  `Username` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `Username`, `Email`, `Password`, `Role`) VALUES
(1, 'budbud', 'budbud@gmail.com', 'budbud123', 'customer'),
(2, 'admin', 'admin@gmail.com', 'admin123', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `sparepart`
--
ALTER TABLE `sparepart`
  ADD PRIMARY KEY (`SparePartID`);

--
-- Indexes for table `sparepartcart`
--
ALTER TABLE `sparepartcart`
  ADD PRIMARY KEY (`SparePartID`,`UserID`),
  ADD KEY `FKuserID` (`UserID`);

--
-- Indexes for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD PRIMARY KEY (`TransactionID`,`SparePartID`),
  ADD KEY `sparepartidFK` (`SparePartID`);

--
-- Indexes for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `useridFK` (`UserID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `sparepartcart`
--
ALTER TABLE `sparepartcart`
  ADD CONSTRAINT `FKsparepartID` FOREIGN KEY (`SparePartID`) REFERENCES `sparepart` (`SparePartID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKuserID` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD CONSTRAINT `sparepartidFK` FOREIGN KEY (`SparePartID`) REFERENCES `sparepart` (`SparePartID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transactionidFK` FOREIGN KEY (`TransactionID`) REFERENCES `transactionheader` (`TransactionID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD CONSTRAINT `useridFK` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
