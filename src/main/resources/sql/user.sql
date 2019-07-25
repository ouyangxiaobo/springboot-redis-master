/*
Navicat MySQL Data Transfer

Source Server         : app
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : usersdata

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-07-25 18:14:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123456', '2019-06-16');
INSERT INTO `user` VALUES ('2', 'root', '123456', '2019-06-16');
INSERT INTO `user` VALUES ('3', 'xaioxin', '123456', '2019-06-16');
INSERT INTO `user` VALUES ('4', 'xiaoming', '123456', '2019-06-16');
INSERT INTO `user` VALUES ('5', 'zhangsan', '123456', '2019-06-16');
INSERT INTO `user` VALUES ('6', 'pdd', '123456', '2019-06-16');
INSERT INTO `user` VALUES ('7', 'xdd', '123456', '2019-06-16');
