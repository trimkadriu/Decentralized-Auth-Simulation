-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 19, 2017 at 07:15 AM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dec_auth_sim`
--

-- --------------------------------------------------------

--
-- Table structure for table `blockchain`
--

CREATE TABLE IF NOT EXISTS `blockchain` (
  `id` int(10) NOT NULL,
  `status` varchar(32) NOT NULL,
  `type` varchar(32) NOT NULL,
  `requested_service_name` varchar(32) NOT NULL,
  `sr_public_key` varchar(2048) NOT NULL,
  `request_signed_data` text NOT NULL,
  `request_time_stamp` datetime NOT NULL,
  `sr_reputation` double NOT NULL,
  `provided_service_results` text NOT NULL,
  `sp_public_key` varchar(2048) NOT NULL,
  `result_signed_data` text NOT NULL,
  `result_time_stamp` datetime NOT NULL,
  `miner_public_key` varchar(2048) NOT NULL,
  `blockchain_signed_data` text NOT NULL,
  `blockchain_time_stamp` datetime NOT NULL,
  `sr_reputation_on_blockchain` double NOT NULL,
  `sp_reputation_on_blockchain` double NOT NULL,
  `miner_reputation_on_blockchain` double NOT NULL,
  `proof_of_work` varchar(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `nodes`
--

CREATE TABLE IF NOT EXISTS `nodes` (
  `id` int(10) NOT NULL,
  `role` varchar(32) NOT NULL,
  `public_key` varchar(2048) NOT NULL,
  `private_key` varchar(2048) NOT NULL,
  `reputation` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blockchain`
--
ALTER TABLE `blockchain`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `nodes`
--
ALTER TABLE `nodes`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `blockchain`
--
ALTER TABLE `blockchain`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `nodes`
--
ALTER TABLE `nodes`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
