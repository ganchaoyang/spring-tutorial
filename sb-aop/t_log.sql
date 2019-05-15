/*
 Navicat Premium Data Transfer

 Source Server         : spring_tutorial
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 39.105.143.89:3306
 Source Schema         : spring_tutorial

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 15/05/2019 21:50:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operation_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口名称',
  `request` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `response` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '结果',
  `error` tinyint(4) NULL DEFAULT NULL COMMENT '是否异常',
  `stack` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常堆栈',
  `taketime` bigint(20) NULL DEFAULT NULL COMMENT '请求耗时，单位ms',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
