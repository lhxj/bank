/*
Navicat MySQL Data Transfer

Source Server         : connection
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : bank

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2014-07-08 14:22:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account_deposit`
-- ----------------------------
DROP TABLE IF EXISTS `account_deposit`;
CREATE TABLE `account_deposit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `amount_time` datetime NOT NULL,
  `amount` bigint(32) NOT NULL DEFAULT '0',
  `year` int(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_deposit
-- ----------------------------

-- ----------------------------
-- Table structure for `account_log`
-- ----------------------------
DROP TABLE IF EXISTS `account_log`;
CREATE TABLE `account_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `draw_amount` bigint(32) NOT NULL DEFAULT '0',
  `deposit_amount` bigint(32) NOT NULL DEFAULT '0',
  `time` datetime NOT NULL,
  `remark` varchar(128) DEFAULT NULL,
  `type` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_log
-- ----------------------------

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
INSERT INTO `bank` VALUES ('1', '广发银行', '2', '10', '1.4000');

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
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(16) NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `identity_card` varchar(32) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '0--普通用户 1--柜台 2--经理',
  `password` varchar(16) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '123', '柜台账户', '123456789123456', '13312345678', '1', '123');
INSERT INTO `user` VALUES ('2', 'admin', '经理账户', '123456789123456', '13312345678', '2', '123');

-- ----------------------------
-- Table structure for `user_account`
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `state` int(2) NOT NULL DEFAULT '1',
  `amount` bigint(32) NOT NULL DEFAULT '0',
  `amount_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_account
-- ----------------------------
