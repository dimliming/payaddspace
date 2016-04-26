-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: polymer
-- ------------------------------------------------------
-- Server version	5.7.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `polymer`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `polymer` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `polymer`;

--
-- Table structure for table `acc_account`
--

DROP TABLE IF EXISTS `acc_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acc_account` (
  `account_no` varchar(20) COLLATE utf8_bin NOT NULL,
  `merchant_code` varchar(24) COLLATE utf8_bin NOT NULL,
  `open_time` datetime DEFAULT NULL,
  `balance` decimal(18,2) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `timestamp` bigint(20) NOT NULL,
  PRIMARY KEY (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_account`
--

LOCK TABLES `acc_account` WRITE;
/*!40000 ALTER TABLE `acc_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `acc_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acc_account_detail`
--

DROP TABLE IF EXISTS `acc_account_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acc_account_detail` (
  `id` bigint(20) NOT NULL,
  `account_no` varchar(20) COLLATE utf8_bin NOT NULL,
  `merchant_code` varchar(24) COLLATE utf8_bin NOT NULL,
  `trade_time` datetime NOT NULL,
  `account_time` datetime NOT NULL,
  `trade_type` int(11) DEFAULT NULL,
  `trade_no` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `amt` decimal(18,2) DEFAULT NULL,
  `balance` decimal(18,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_account_detail`
--

LOCK TABLES `acc_account_detail` WRITE;
/*!40000 ALTER TABLE `acc_account_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `acc_account_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acc_recharge`
--

DROP TABLE IF EXISTS `acc_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acc_recharge` (
  `recharge_no` varchar(20) COLLATE utf8_bin NOT NULL,
  `merchant_code` varchar(24) COLLATE utf8_bin NOT NULL,
  `recharge_time` datetime NOT NULL,
  `channel_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `amt` decimal(18,2) DEFAULT NULL,
  PRIMARY KEY (`recharge_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_recharge`
--

LOCK TABLES `acc_recharge` WRITE;
/*!40000 ALTER TABLE `acc_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `acc_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aut_channel_message`
--

DROP TABLE IF EXISTS `aut_channel_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aut_channel_message` (
  `id` bigint(20) NOT NULL,
  `trade_no` varchar(20) COLLATE utf8_bin NOT NULL,
  `channel_code` varchar(0) COLLATE utf8_bin NOT NULL,
  `merchant_code` varchar(24) COLLATE utf8_bin NOT NULL,
  `msg_type` int(11) DEFAULT NULL,
  `req_msg` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `resp_msg` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aut_channel_message`
--

LOCK TABLES `aut_channel_message` WRITE;
/*!40000 ALTER TABLE `aut_channel_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `aut_channel_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aut_merchant_message`
--

DROP TABLE IF EXISTS `aut_merchant_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aut_merchant_message` (
  `id` bigint(20) NOT NULL,
  `merchant_trade_no` varchar(32) COLLATE utf8_bin NOT NULL,
  `merchant_code` varchar(24) COLLATE utf8_bin NOT NULL,
  `msg_type` int(11) DEFAULT NULL,
  `req_msg` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `resp_msg` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aut_merchant_message`
--

LOCK TABLES `aut_merchant_message` WRITE;
/*!40000 ALTER TABLE `aut_merchant_message` DISABLE KEYS */;
INSERT INTO `aut_merchant_message` VALUES (16041562317050867,'310012301000011','310012301000011',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=310012301000011&order_no=310012301000011&phone=13428187972&signature=2BB7A03FA3ACC179E2F11236F3F9977D&trade_time=20160415051836&txn_type=00&version=1.0.0',NULL),(16041562712050666,'310012301000011','310012301000011',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=310012301000011&order_no=310012301000011&phone=13428187972&signature=56F26C27844DF0F9AABD56F4878A6873&trade_time=20160415052511&txn_type=00&version=1.0.0',NULL),(16041562848050854,'310012301000011','310012301000011',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=310012301000011&order_no=310012301000011&phone=13428187972&signature=6C6EFC569E2BA0FC30BCE76A13D76CE4&trade_time=20160415052727&txn_type=00&version=1.0.0','resp_code=110004&resp_msg=merchant_code字段格式错误'),(16041563587050599,'310012301000011','310012301000011',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=310012301000011&order_no=310012301000011&phone=13428187972&signature=5CF344E6E83F2BE676376CE4B866DB1A&trade_time=20160415053946&txn_type=00&version=1.0.0','resp_code=110004&resp_msg=merchant_code字段格式错误'),(16041563702050119,'310012301000011','310012301000011',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=310012301000011&order_no=310012301000011&phone=13428187972&signature=60CBE81D2E596113EEF85E70FFA759E3&trade_time=20160415054141&txn_type=00&version=1.0.0','resp_code=110004&resp_msg=merchant_code字段格式错误'),(16041564276050215,'310012301000011','310012301000011',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=310012301000011&order_no=310012301000011&phone=13428187972&signature=BD1D20BDC25F119347FD8621E1DADF30&trade_time=20160415055115&txn_type=00&version=1.0.0','resp_code=110004&resp_msg=merchant_code字段格式错误'),(16041567029050605,'310012301000011','310012301000011',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=310012301000011&order_no=310012301000011&phone=13428187972&signature=9762564E7EE220ACAA57EB0D48ED6E4C&trade_time=20160415063709&txn_type=00&version=1.0.0','resp_code=110004&resp_msg=merchant_code字段格式错误'),(16041567134050695,'310012301000011','310012301000011',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=310012301000011&order_no=310012301000011&phone=13428187972&signature=C05154B6D148FAEFF19775C803226369&trade_time=20160415063853&txn_type=00&version=1.0.0','resp_code=110004&resp_msg=trade_time字段格式错误'),(16041567761050996,'310012301000011','310012301000011',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=310012301000011&order_no=310012301000011&phone=13428187972&signature=8D7290D4AEC7877226F97929856E8435&trade_time=20160415184920&txn_type=00&version=1.0.0','resp_code=999999&resp_msg=业务异常'),(16041570677050546,'DD160415193756','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160415193756&phone=13428187972&signature=4CC02599D0FF09F39360A099564099FB&trade_time=20160415193756&txn_type=00&version=1.0.0','resp_code=110003&resp_msg=签名错误'),(16041570735050482,'DD160415193855','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160415193855&phone=13428187972&signature=1C7E8D4BC6D11983B4888C5CF04A2008&trade_time=20160415193855&txn_type=00&version=1.0.0',NULL),(16041571202050701,'DD160415194641','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160415194641&phone=13428187972&signature=94A29F631F2525C47879482724C12D60&trade_time=20160415194641&txn_type=00&version=1.0.0',NULL),(16041571321050422,'DD160415194840','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160415194840&phone=13428187972&signature=9A2A13D0AFD446E4C8B7AB8F3B98C952&trade_time=20160415194840&txn_type=00&version=1.0.0',NULL),(16041571480050551,'DD160415195119','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160415195119&phone=13428187972&signature=DBBE1749012EE223494435D9F1968278&trade_time=20160415195119&txn_type=00&version=1.0.0',NULL),(16041638412063588,'DD160416104011','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160416104011&phone=13428187972&signature=101D78080FB83FDA7D1F4BE0941D52AF&trade_time=20160416104011&txn_type=00&version=1.0.0',NULL),(16041641742063244,'DD160416113540','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160416113540&phone=13428187972&signature=72CB10A4EC7EBD48995BEAACF68E55C6&trade_time=20160416113540&txn_type=00&version=1.0.0',NULL),(16041641922063514,'DD160416113841','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160416113841&phone=13428187972&signature=EC902DEDB1BA61A3B7D97FC242615155&trade_time=20160416113841&txn_type=00&version=1.0.0',NULL),(16041642230063921,'DD160416114348','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160416114348&phone=13428187972&signature=6D6810610A10D8DC9655F267C9CBF081&trade_time=20160416114348&txn_type=00&version=1.0.0',NULL),(16041642306063283,'DD160416114506','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160416114506&phone=13428187972&signature=720DCF743CB0A460083C4C26097307EB&trade_time=20160416114506&txn_type=00&version=1.0.0','resp_code=120001&resp_msg=商户状态异常或商户不存在'),(16041642666063894,'DD160416115105','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160416115105&phone=13428187972&signature=C6012A34DBC20E650F595F1034BB4D71&trade_time=20160416115105&txn_type=00&version=1.0.0','resp_code=120001&resp_msg=商户状态异常或商户不存在'),(16041645607063810,'DD160416124007','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160416124007&phone=13428187972&signature=F1500BB1DECD7DF4C8C097C22162032B&trade_time=20160416124007&txn_type=00&version=1.0.0','resp_code=120001&resp_msg=商户状态异常或商户不存在'),(16041646046063921,'DD160416124724','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=N&merchant_code=80075501010001&order_no=DD160416124724&phone=13428187972&signature=FEFD56319CC91C0BB72DE6D311188FEF&trade_time=20160416124724&txn_type=00&version=1.0.0',NULL),(16041646156063534,'DD160416124914','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=N&merchant_code=80075501010001&order_no=DD160416124914&phone=13428187972&signature=00C4C8052306AE962BD7B6F643AA3792&trade_time=20160416124914&txn_type=00&version=1.0.0',NULL),(16041835197050130,'DD160418094635','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=N&merchant_code=80075501010001&order_no=DD160418094635&phone=13428187972&signature=C8B3A44754BC8B8586F5E94FAB57A694&trade_time=20160418094635&txn_type=00&version=1.0.0',NULL),(16041835256050946,'DD160418094736','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160418094736&phone=13428187972&signature=9AE77B04929BBB35445BA8CDC3B7802F&trade_time=20160418094736&txn_type=00&version=1.0.0','resp_code=000000&resp_msg=交易成功'),(16041836792050237,'DD160418101310','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160418101310&phone=13428187972&signature=5C5F39BA441C6DB5AAD377C40EFA0D2E&trade_time=20160418101310&txn_type=00&version=1.0.0','resp_code=000000&resp_msg=交易成功'),(16041837385050324,'DD160418102303','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160418102303&phone=13428187972&signature=4E3AFEE7687AB088D315EC1BBF15D443&trade_time=20160418102303&txn_type=00&version=1.0.0','resp_code=000000&resp_msg=交易成功'),(16041837987050159,'DD160418103305','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160418103305&phone=13428187972&signature=B234F8135370FD8794BA772D137452AD&trade_time=20160418103305&txn_type=00&version=1.0.0','resp_code=000000&resp_msg=交易成功'),(16041838789050466,'DD160418104628','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160418104628&phone=13428187972&signature=1A03FF30CA1FF45D46C21F6C8679EE7C&trade_time=20160418104628&txn_type=00&version=1.0.0','resp_code=000000&resp_msg=交易成功'),(16041840185050274,'DD160418110943','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160418110943&phone=13428187972&signature=1CA42C5BA6E1DB93D5B7AEDCFA408430&trade_time=20160418110943&txn_type=00&version=1.0.0','resp_code=000000&resp_msg=交易成功'),(16041840303050327,'DD160418111141','80075501010001',1,'account_no=6227003321740369152&account_type=01&auth_type=1111&cert_no=440902199307090413&cert_type=01&custom_name=黎铭&encoding=UTF-8&is_test=Y&merchant_code=80075501010001&order_no=DD160418111141&phone=13428187972&signature=328CFD8CF63FD7F6D1C9E093C951149E&trade_time=20160418111141&txn_type=00&version=1.0.0','resp_code=000000&resp_msg=交易成功');
/*!40000 ALTER TABLE `aut_merchant_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aut_trade`
--

DROP TABLE IF EXISTS `aut_trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aut_trade` (
  `trade_no` varchar(20) COLLATE utf8_bin NOT NULL,
  `product_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `merchant_code` varchar(24) COLLATE utf8_bin NOT NULL,
  `full_name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `merchant_trade_no` varchar(32) COLLATE utf8_bin NOT NULL,
  `channel_trade_no` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `agency_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `trade_time` datetime DEFAULT NULL,
  `auth_type` varchar(10) COLLATE utf8_bin NOT NULL,
  `account_no` varchar(21) COLLATE utf8_bin NOT NULL,
  `cert_type` char(2) COLLATE utf8_bin DEFAULT NULL,
  `cert_no` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `custom_name` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `fee` decimal(18,2) DEFAULT NULL,
  `is_write_off` char(1) COLLATE utf8_bin DEFAULT NULL,
  `resp_code` varchar(6) COLLATE utf8_bin DEFAULT NULL,
  `resp_msg` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `is_test` char(1) COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `notify_url` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `notify_status` int(11) DEFAULT NULL,
  `notify_num` int(11) DEFAULT NULL,
  `notify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aut_trade`
--

LOCK TABLES `aut_trade` WRITE;
/*!40000 ALTER TABLE `aut_trade` DISABLE KEYS */;
INSERT INTO `aut_trade` VALUES ('16041642306063363','common','80075501010001',NULL,'DD160416114506',NULL,NULL,'2016-04-16 11:45:06','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N',NULL,NULL,'Y',0,NULL,0,0,NULL),('16041642667063689','common','80075501010001',NULL,'DD160416115105',NULL,NULL,'2016-04-16 11:51:05','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N',NULL,NULL,'Y',0,NULL,0,0,NULL),('16041645607063919','common','80075501010001',NULL,'DD160416124007',NULL,NULL,'2016-04-16 12:40:07','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N',NULL,NULL,'Y',0,NULL,0,0,NULL),('16041646157063386','common','80075501010001',NULL,'DD160416124914',NULL,NULL,'2016-04-16 12:49:14','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N',NULL,NULL,'N',0,NULL,0,0,NULL),('16041835198050118','common','80075501010001',NULL,'DD160418094635',NULL,NULL,'2016-04-18 09:46:35','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N',NULL,NULL,'N',0,NULL,0,0,NULL),('16041835257050002','common','80075501010001',NULL,'DD160418094736','10000000001','shengda','2016-04-18 09:47:36','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N','000000','成功','Y',3,NULL,1,0,NULL),('16041836793050606','common','80075501010001',NULL,'DD160418101310','10000000001','shengda','2016-04-18 10:13:10','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N','000000','成功','Y',3,NULL,1,0,NULL),('16041837386050475','common','80075501010001',NULL,'DD160418102303','10000000001','shengda','2016-04-18 10:23:03','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N','000000','成功','Y',3,NULL,1,0,NULL),('16041837988050373','common','80075501010001',NULL,'DD160418103305','10000000001','shengda','2016-04-18 10:33:05','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N','000000','成功','Y',3,NULL,1,0,NULL),('16041838790050508','common','80075501010001',NULL,'DD160418104628','10000000001','shengda','2016-04-18 10:46:28','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N','000000','成功','Y',3,NULL,1,0,NULL),('16041840186050344','common','80075501010001',NULL,'DD160418110943','10000000001','shengda','2016-04-18 11:09:43','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N','000000','成功','Y',3,NULL,1,0,NULL),('16041840304050402','common','80075501010001',NULL,'DD160418111141','10000000001','shengda','2016-04-18 11:11:41','1111','6227003321740369152','01','440902199307090413','黎铭','13428187972',0.20,'N','000000','成功','Y',3,NULL,1,0,NULL);
/*!40000 ALTER TABLE `aut_trade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bdm_merchant`
--

DROP TABLE IF EXISTS `bdm_merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bdm_merchant` (
  `merchant_code` varchar(24) COLLATE utf8_bin NOT NULL,
  `full_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `short_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `status` char(1) COLLATE utf8_bin NOT NULL,
  `list_flag` char(1) COLLATE utf8_bin NOT NULL,
  `level` char(2) COLLATE utf8_bin NOT NULL,
  `site_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `site_address` varchar(50) COLLATE utf8_bin NOT NULL,
  `business_license` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `org_code` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `id_duration_start` int(8) DEFAULT NULL,
  `id_duration_end` int(8) DEFAULT NULL,
  `represent_name` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `represent_id_code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `business_scope` char(2) COLLATE utf8_bin DEFAULT NULL,
  `registered_capital` decimal(18,2) DEFAULT NULL,
  `registered_address` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `industry_class` char(2) COLLATE utf8_bin DEFAULT NULL,
  `company_email` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `company_address` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `company_phone` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `company_tax` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `contact_name` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `contact_position` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `contact_sex` char(1) COLLATE utf8_bin DEFAULT NULL,
  `contact_email` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `tel` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `qq` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `create_user_id` varchar(24) COLLATE utf8_bin NOT NULL,
  `update_user_id` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `last_update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`merchant_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bdm_merchant`
--

LOCK TABLES `bdm_merchant` WRITE;
/*!40000 ALTER TABLE `bdm_merchant` DISABLE KEYS */;
INSERT INTO `bdm_merchant` VALUES ('16042666657104525','汇银通',NULL,'1','1','00','支付系统','http://www.tclpay.cn',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'admin','admin','2016-04-26 18:30:58','2016-04-26 18:31:03'),('80075501010001','测试商户','测试商户','1','1','00','www.payadd.cn','www.payadd.cn',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'admin','admin','2016-04-15 19:24:00','2016-04-26 17:41:13');
/*!40000 ALTER TABLE `bdm_merchant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bdm_merchant_bank_account`
--

DROP TABLE IF EXISTS `bdm_merchant_bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bdm_merchant_bank_account` (
  `merchant_code` varchar(24) COLLATE utf8_bin NOT NULL,
  `account_no` varchar(32) COLLATE utf8_bin NOT NULL,
  `account_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `bank_code` varchar(10) COLLATE utf8_bin NOT NULL,
  `bank_name` varchar(20) COLLATE utf8_bin NOT NULL,
  `bank_address` varchar(50) COLLATE utf8_bin NOT NULL,
  `settle_code` varchar(12) COLLATE utf8_bin NOT NULL,
  `pc_flag` char(1) COLLATE utf8_bin NOT NULL,
  `is_default` char(1) COLLATE utf8_bin NOT NULL,
  `purpose` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `create_user_id` varchar(24) COLLATE utf8_bin NOT NULL,
  `update_user_id` varchar(24) COLLATE utf8_bin NOT NULL,
  `create_time` datetime NOT NULL,
  `last_update_time` datetime NOT NULL,
  PRIMARY KEY (`merchant_code`,`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bdm_merchant_bank_account`
--

LOCK TABLES `bdm_merchant_bank_account` WRITE;
/*!40000 ALTER TABLE `bdm_merchant_bank_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `bdm_merchant_bank_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bdm_merchant_security`
--

DROP TABLE IF EXISTS `bdm_merchant_security`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bdm_merchant_security` (
  `id` bigint(20) NOT NULL,
  `merchant_code` varchar(24) COLLATE utf8_bin NOT NULL,
  `protocol_code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `sign_alg` char(1) COLLATE utf8_bin DEFAULT NULL,
  `sign_key` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `crypto_alg` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `crypto_key` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `public_key` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `private_key` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bdm_merchant_security`
--

LOCK TABLES `bdm_merchant_security` WRITE;
/*!40000 ALTER TABLE `bdm_merchant_security` DISABLE KEYS */;
INSERT INTO `bdm_merchant_security` VALUES (16041562317050867,'80075501010001','common','M','111111',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `bdm_merchant_security` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bdm_merchant_settle_cycle`
--

DROP TABLE IF EXISTS `bdm_merchant_settle_cycle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bdm_merchant_settle_cycle` (
  `merchant_code` varchar(24) COLLATE utf8_bin NOT NULL,
  `settle_rule` char(1) COLLATE utf8_bin NOT NULL,
  `retain_days` int(2) DEFAULT NULL,
  `min_amt` decimal(18,2) DEFAULT NULL,
  `regular_type` char(1) COLLATE utf8_bin DEFAULT NULL,
  `regular_time` int(2) DEFAULT NULL,
  `holiday_flag` char(1) COLLATE utf8_bin DEFAULT NULL,
  `next_settletime` int(8) DEFAULT NULL,
  `create_user_id` varchar(24) COLLATE utf8_bin NOT NULL,
  `update_user_id` varchar(24) COLLATE utf8_bin NOT NULL,
  `create_time` datetime NOT NULL,
  `last_update_time` datetime NOT NULL,
  PRIMARY KEY (`merchant_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bdm_merchant_settle_cycle`
--

LOCK TABLES `bdm_merchant_settle_cycle` WRITE;
/*!40000 ALTER TABLE `bdm_merchant_settle_cycle` DISABLE KEYS */;
/*!40000 ALTER TABLE `bdm_merchant_settle_cycle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bdm_merchant_user`
--

DROP TABLE IF EXISTS `bdm_merchant_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bdm_merchant_user` (
  `id` bigint(20) NOT NULL,
  `merchant_code` varchar(24) COLLATE utf8_bin NOT NULL,
  `user_name` varchar(20) COLLATE utf8_bin NOT NULL,
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `login_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(32) COLLATE utf8_bin NOT NULL,
  `pay_password` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `welcome` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `secure_question` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `secure_answer` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `auth_agency` char(1) COLLATE utf8_bin DEFAULT NULL,
  `auth_check` char(1) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `last_update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bdm_merchant_user`
--

LOCK TABLES `bdm_merchant_user` WRITE;
/*!40000 ALTER TABLE `bdm_merchant_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `bdm_merchant_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `login_name` varchar(20) COLLATE utf8_bin NOT NULL,
  `user_name` varchar(30) COLLATE utf8_bin NOT NULL,
  `password` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('admin','管理员','111111');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-26 18:48:20
