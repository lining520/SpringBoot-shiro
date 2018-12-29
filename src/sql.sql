DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父菜单ID',
  `menu_name` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `menu_code` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单CODE',
  `menu_icon` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `menu_level` int(11) NOT NULL COMMENT '菜单层级',
  `order` int(11) NOT NULL DEFAULT 1 COMMENT '排序',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `tb_status` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '正常' COMMENT '状态：正常，正常；删除，删除；',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单Menu表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '权限管理', 'power', NULL, 1, 1, '2018-04-20 12:09:14', '2018-04-20 12:09:14', '正常');
INSERT INTO `sys_menu` VALUES (2, 0, '角色管理', 'power_role', NULL, 2, 1, '2018-04-20 13:36:48', '2018-11-20 16:11:41', '正常');
INSERT INTO `sys_menu` VALUES (3, 1, '菜单管理', 'power_menu', NULL, 2, 2, '2018-04-23 17:20:10', '2018-04-23 17:22:04', '正常');
INSERT INTO `sys_menu` VALUES (4, 0, '用户管理', 'power_user', NULL, 2, 3, '2018-04-23 17:21:12', '2018-09-27 10:12:51', '正常');

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_name` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `desc` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `order` int(11) NOT NULL DEFAULT 1 COMMENT '排序',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `tb_status` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '正常' COMMENT '状态：正常，正常；删除，删除；',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', NULL, 1, '2018-04-23 17:16:25', '2018-04-23 17:16:25', '正常');
INSERT INTO `sys_role` VALUES (2, '运营', 'test', 2, '2018-11-06 17:06:00', '2018-11-21 15:55:40', '正常');
INSERT INTO `sys_role` VALUES (3, '超级管理员', NULL, 1, '2018-11-21 15:53:32', '2018-11-21 15:53:32', '删除');

SET FOREIGN_KEY_CHECKS = 1;



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `tb_status` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '正常' COMMENT '状态：正常，正常；删除，删除；',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与菜单关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1, 1, '2018-11-20 16:14:07', '2018-11-20 16:14:16', '正常');
INSERT INTO `sys_role_menu` VALUES (2, 1, 2, '2018-11-20 16:14:23', '2018-11-20 16:14:23', '正常');
INSERT INTO `sys_role_menu` VALUES (3, 1, 3, '2018-11-20 16:14:32', '2018-11-20 16:14:32', '正常');
INSERT INTO `sys_role_menu` VALUES (4, 1, 4, '2018-11-20 16:14:37', '2018-11-20 16:14:37', '正常');
INSERT INTO `sys_role_menu` VALUES (5, 2, 1, '2018-11-20 16:19:37', '2018-11-20 16:19:37', '正常');
INSERT INTO `sys_role_menu` VALUES (6, 2, 4, '2018-11-20 16:19:49', '2018-11-20 16:33:09', '正常');
INSERT INTO `sys_role_menu` VALUES (7, 2, 2, '2018-11-20 17:23:28', '2018-11-20 17:23:28', '正常');

SET FOREIGN_KEY_CHECKS = 1;



DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_name` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `email` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `mobile` char(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `head_icon` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `salt` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `tb_status` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '正常' COMMENT '状态：正常，正常；删除，删除；',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`user_name`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
  UNIQUE INDEX `moble`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'ln', '9ff3814d26e958472c82f37d42acd732', '1233@qq.com', '13229763709', '232323', 'sypFf2', '2018-09-18 17:13:21', '2018-11-20 14:52:25', '正常');--密码123456 
INSERT INTO `sys_user` VALUES (3, 'boy', 'e63b4a1604cd5d341f937cda4f273d6e', '123@qq.com', '13229763708', NULL, 'KYW5rp', '2018-11-16 15:32:48', '2018-11-20 15:13:19', '正常');

SET FOREIGN_KEY_CHECKS = 1;



DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `tb_status` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '正常' COMMENT '状态：正常，正常；删除，删除；',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, '2018-04-23 17:17:35', '2018-04-23 17:17:35', '正常');
INSERT INTO `sys_user_role` VALUES (2, 3, 2, '2018-11-20 16:19:06', '2018-11-20 16:19:14', '正常');

SET FOREIGN_KEY_CHECKS = 1;
