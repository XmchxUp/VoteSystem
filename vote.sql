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

 Date: 05/05/2020 19:35:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rank` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKt8o6pivur7nn124jehx7cygw5`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (11, '2020-05-05 19:30:53.241000', '计算机', 13);

-- ----------------------------
-- Table structure for choices
-- ----------------------------
DROP TABLE IF EXISTS `choices`;
CREATE TABLE `choices`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `poll_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK1i68hpih40n447wqx4lpef6ot`(`poll_id`) USING BTREE,
  CONSTRAINT `FK1i68hpih40n447wqx4lpef6ot` FOREIGN KEY (`poll_id`) REFERENCES `polls` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of choices
-- ----------------------------
INSERT INTO `choices` VALUES (89, 'Spring In Action', 13);
INSERT INTO `choices` VALUES (90, 'Spring Boot In Action', 13);
INSERT INTO `choices` VALUES (91, 'Java编程思想', 13);
INSERT INTO `choices` VALUES (92, 'C程序设计语言', 13);
INSERT INTO `choices` VALUES (93, '代码大全', 13);
INSERT INTO `choices` VALUES (94, '算法4', 13);

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `comment_status` tinyint(4) NULL DEFAULT NULL,
  `commentator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commentator_ip` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `email` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reply_body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reply_create_time` datetime(6) NULL DEFAULT NULL,
  `poll_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKshp333wo5iq9vwns7l2wp4m95`(`poll_id`) USING BTREE,
  CONSTRAINT `FKshp333wo5iq9vwns7l2wp4m95` FOREIGN KEY (`poll_id`) REFERENCES `polls` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for poll_tags
-- ----------------------------
DROP TABLE IF EXISTS `poll_tags`;
CREATE TABLE `poll_tags`  (
  `poll_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  INDEX `FKr5mf5shm4vill4qvbp90c54ry`(`tag_id`) USING BTREE,
  INDEX `FKivojvp8bmt4o9bvvij6pxr8co`(`poll_id`) USING BTREE,
  CONSTRAINT `FKivojvp8bmt4o9bvvij6pxr8co` FOREIGN KEY (`poll_id`) REFERENCES `polls` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKr5mf5shm4vill4qvbp90c54ry` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poll_tags
-- ----------------------------
INSERT INTO `poll_tags` VALUES (13, 1);
INSERT INTO `poll_tags` VALUES (13, 17);

-- ----------------------------
-- Table structure for polls
-- ----------------------------
DROP TABLE IF EXISTS `polls`;
CREATE TABLE `polls`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `limit_count` int(11) NULL DEFAULT NULL,
  `mode` tinyint(4) NULL DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `expiration_date_time` datetime(6) NOT NULL,
  `summary` varchar(140) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `category_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `count` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKngvibdputrxevf73lci9hppyb`(`category_id`) USING BTREE,
  INDEX `FKrmn4rau93pxxyqgi57dqng2rl`(`user_id`) USING BTREE,
  CONSTRAINT `FKngvibdputrxevf73lci9hppyb` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKrmn4rau93pxxyqgi57dqng2rl` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of polls
-- ----------------------------
INSERT INTO `polls` VALUES (13, -1, 2, '2020-05-05 19:32:09.923000', '2020-05-12 00:00:00.000000', '读书测试', '下面有哪些书籍是你喜欢的？', 11, 1, 1);

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_nb4h0p6txrmfc0xbrd1kglp9t`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'ROLE_ADMIN');
INSERT INTO `roles` VALUES (5, 'ROLE_NAVY');
INSERT INTO `roles` VALUES (3, 'ROLE_PIRATES');
INSERT INTO `roles` VALUES (4, 'ROLE_PIRATE_KING');
INSERT INTO `roles` VALUES (2, 'ROLE_USER');

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKt48xdq560gs3gap9g7jg36kgc`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tags
-- ----------------------------
INSERT INTO `tags` VALUES (1, '2020-05-05 19:29:42.000000', '读书');
INSERT INTO `tags` VALUES (17, '2020-05-05 19:32:09.906000', '计算机');

-- ----------------------------
-- Table structure for tags_polls
-- ----------------------------
DROP TABLE IF EXISTS `tags_polls`;
CREATE TABLE `tags_polls`  (
  `tag_id` bigint(20) NOT NULL,
  `polls_id` bigint(20) NOT NULL,
  INDEX `FKbawhyq1pj1nvafpikll052g8w`(`polls_id`) USING BTREE,
  INDEX `FKtndl3ckcx89ri3669xp2llbxl`(`tag_id`) USING BTREE,
  CONSTRAINT `FKbawhyq1pj1nvafpikll052g8w` FOREIGN KEY (`polls_id`) REFERENCES `polls` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKtndl3ckcx89ri3669xp2llbxl` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`  (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FKh8ciramu9cc9q3qcqiv4ue8a6`(`role_id`) USING BTREE,
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES (1, 1);
INSERT INTO `user_roles` VALUES (1, 2);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `email` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_login_time` datetime(6) NULL DEFAULT NULL,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `motto` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKr43af9ap4edm43mmtq01oddj6`(`username`) USING BTREE,
  UNIQUE INDEX `UK6dotkott2kjsp8vw4d0m25fb7`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '/dist/img/avatar/girl71.png', '2020-05-05 19:21:29.076000', '1394466835@qq.com', '2020-05-05 19:34:40.278000', '超级管理员', '$2a$10$O6.NclQAB3R/6LfGpjrnl.sliq2oqendPPHQug0UtdqqdW9WOFGGS', NULL, 'admin', NULL);

-- ----------------------------
-- Table structure for votes
-- ----------------------------
DROP TABLE IF EXISTS `votes`;
CREATE TABLE `votes`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `update_time` datetime(6) NULL DEFAULT NULL,
  `choice_id` bigint(20) NOT NULL,
  `poll_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKomskymhxde3qq9mcukyp1puio`(`choice_id`) USING BTREE,
  INDEX `FK7trt3uyihr4g13hva9d31puxg`(`poll_id`) USING BTREE,
  INDEX `FKli4uj3ic2vypf5pialchj925e`(`user_id`) USING BTREE,
  CONSTRAINT `FK7trt3uyihr4g13hva9d31puxg` FOREIGN KEY (`poll_id`) REFERENCES `polls` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKli4uj3ic2vypf5pialchj925e` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKomskymhxde3qq9mcukyp1puio` FOREIGN KEY (`choice_id`) REFERENCES `choices` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of votes
-- ----------------------------
INSERT INTO `votes` VALUES (70, '2020-05-05 19:34:43.899000', NULL, 91, 13, 1);
INSERT INTO `votes` VALUES (71, '2020-05-05 19:34:43.908000', NULL, 92, 13, 1);
INSERT INTO `votes` VALUES (72, '2020-05-05 19:34:43.914000', NULL, 93, 13, 1);

SET FOREIGN_KEY_CHECKS = 1;
