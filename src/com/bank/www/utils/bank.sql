/*
Navicat MySQL Data Transfer

Source Server         : connection
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : bank

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2014-07-07 16:08:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account_deposit`
-- ----------------------------
DROP TABLE IF EXISTS `account_deposit`;
CREATE TABLE `account_deposit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `amount_time` datetime NOT NULL,
  `amount` bigint(32) NOT NULL DEFAULT '0',
  `year` int(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_deposit
-- ----------------------------
INSERT INTO `account_deposit` VALUES ('14', '4', '2014-07-07 15:33:13', '500000', '1');
INSERT INTO `account_deposit` VALUES ('15', '4', '2014-07-07 15:33:22', '25000000', '3');
INSERT INTO `account_deposit` VALUES ('16', '5', '2014-07-07 15:33:33', '15000000', '3');
INSERT INTO `account_deposit` VALUES ('17', '5', '2014-07-07 15:33:40', '10000000', '3');
INSERT INTO `account_deposit` VALUES ('18', '6', '2014-07-07 16:05:14', '15000000', '1');
INSERT INTO `account_deposit` VALUES ('19', '6', '2014-07-07 16:05:28', '5000000', '1');

-- ----------------------------
-- Table structure for `account_log`
-- ----------------------------
DROP TABLE IF EXISTS `account_log`;
CREATE TABLE `account_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `draw_amount` bigint(32) NOT NULL DEFAULT '0',
  `deposit_amount` bigint(32) NOT NULL DEFAULT '0',
  `time` datetime NOT NULL,
  `remark` varchar(128) DEFAULT NULL,
  `type` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_log
-- ----------------------------
INSERT INTO `account_log` VALUES ('15', '4', '0', '500000', '2014-07-07 15:33:13', '定期存款:500000', '0');
INSERT INTO `account_log` VALUES ('16', '4', '0', '25000000', '2014-07-07 15:33:22', '定期存款:25000000', '0');
INSERT INTO `account_log` VALUES ('17', '5', '0', '15000000', '2014-07-07 15:33:33', '定期存款:15000000', '0');
INSERT INTO `account_log` VALUES ('18', '5', '0', '10000000', '2014-07-07 15:33:40', '定期存款:10000000', '0');
INSERT INTO `account_log` VALUES ('19', '6', '0', '15000000', '2014-07-07 16:05:14', '定期存款:15000000', '0');
INSERT INTO `account_log` VALUES ('20', '6', '0', '5000000', '2014-07-07 16:05:28', '定期存款:5000000', '0');

-- ----------------------------
-- Table structure for `bank`
-- ----------------------------
DROP TABLE IF EXISTS `bank`;
CREATE TABLE `bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bank_name` varchar(32) DEFAULT NULL,
  `same_cost` bigint(32) NOT NULL DEFAULT '0',
  `inter_cost` bigint(32) NOT NULL DEFAULT '0',
  `rate` float(11,4) DEFAULT '0.0000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank
-- ----------------------------
INSERT INTO `bank` VALUES ('1', '银行1', '2', '10', '1.4000');

-- ----------------------------
-- Table structure for `bank_rate`
-- ----------------------------
DROP TABLE IF EXISTS `bank_rate`;
CREATE TABLE `bank_rate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bank_id` int(11) NOT NULL,
  `year` int(2) NOT NULL DEFAULT '1',
  `rate` float(11,4) NOT NULL DEFAULT '0.0000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_rate
-- ----------------------------
INSERT INTO `bank_rate` VALUES ('1', '1', '1', '2.4000');
INSERT INTO `bank_rate` VALUES ('2', '1', '3', '2.8000');
INSERT INTO `bank_rate` VALUES ('3', '1', '5', '3.6000');

-- ----------------------------
-- Table structure for `user_account`
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL,
  `no` varchar(16) NOT NULL,
  `state` int(2) NOT NULL DEFAULT '1',
  `amount` bigint(32) NOT NULL DEFAULT '0',
  `amount_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_account
-- ----------------------------
INSERT INTO `user_account` VALUES ('4', 't1', '123', '1', '0', '2014-07-07 15:32:49');
INSERT INTO `user_account` VALUES ('5', 't2', '234', '1', '0', '2014-07-07 15:32:57');
INSERT INTO `user_account` VALUES ('6', 'test4', '121', '1', '0', '2014-07-07 16:05:04');
