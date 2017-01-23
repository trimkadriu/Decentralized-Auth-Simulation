--
-- Database: `dec_auth_sim`
--

-- --------------------------------------------------------

--
-- Table structure for table `blockchain`
--
CREATE TABLE IF NOT EXISTS `blockchain` (
  `id` int(10) NOT NULL,
  `status` varchar(32) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `requested_service_name` varchar(32) DEFAULT NULL,
  `sr_public_key` varchar(2048) DEFAULT NULL,
  `request_signed_data` text,
  `request_time_stamp` datetime DEFAULT NULL,
  `sr_reputation` double DEFAULT NULL,
  `provided_service_results` text,
  `sp_public_key` varchar(2048) DEFAULT NULL,
  `sp_reputation` double DEFAULT NULL,
  `result_signed_data` text,
  `result_time_stamp` datetime DEFAULT NULL,
  `confirmation_service_received` tinyint(1) NOT NULL DEFAULT '0',
  `confirmation_service_sent` tinyint(1) NOT NULL DEFAULT '0',
  `miner_public_key` varchar(2048) DEFAULT NULL,
  `miner_reputation` double DEFAULT NULL,
  `blockchain_signed_data` text,
  `blockchain_time_stamp` datetime DEFAULT NULL,
  `sr_reputation_on_blockchain` double DEFAULT NULL,
  `sp_reputation_on_blockchain` double DEFAULT NULL,
  `miner_reputation_on_blockchain` double DEFAULT NULL,
  `proof_of_work` varchar(1024) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=513988634 DEFAULT CHARSET=latin1;

--
-- Table structure for table `nodes`
--
CREATE TABLE IF NOT EXISTS `nodes` (
  `id` int(10) NOT NULL,
  `role` varchar(32) NOT NULL,
  `public_key` varchar(2048) NOT NULL,
  `private_key` varchar(2048) NOT NULL,
  `reputation` double NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

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
-- AUTO_INCREMENT for table `blockchain`
--
ALTER TABLE `blockchain`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=513988634;
--
-- AUTO_INCREMENT for table `nodes`
--
ALTER TABLE `nodes`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;