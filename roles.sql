/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 192.168.1.101:3306
 Source Schema         : vote

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 02/02/2020 09:31:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_nb4h0p6txrmfc0xbrd1kglp9t`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'ROLE_ADMIN');
INSERT INTO `roles` VALUES (5, 'ROLE_NAVY');
INSERT INTO `roles` VALUES (3, 'ROLE_PIRATES');
INSERT INTO `roles` VALUES (4, 'ROLE_PIRATE_KING');
INSERT INTO `roles` VALUES (2, 'ROLE_USER');

SET FOREIGN_KEY_CHECKS = 1;
