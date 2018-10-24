SET NAMES 'utf8';
CREATE DATABASE IF NOT EXISTS db_cz_cms DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

USE db_cz_cms;

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
create table `role`
(
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name` varchar(100) COMMENT '角色名',
    `mark` varchar(50) COMMENT '权限标识',
    `description` varchar(250) COMMENT '角色描述',
    `status` int COMMENT '状态，0：启用，1：禁用',
    `show_index` int COMMENT '显示顺序',
    `creator` bigint COMMENT '创建者',
    `updator` bigint COMMENT '最后更新者',
    `create_date` datetime COMMENT '创建时间',
    `update_date` datetime COMMENT '最后更新时间',
    primary key(id)
)COMMENT '角色表兼顾权限的表示';

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
create table `role_menu`
(
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `role_id` bigint COMMENT '角色(权限)id',
    `system_menu_id` bigint COMMENT '菜单id',
    primary key(id)
)COMMENT '权限与菜单';


-- ----------------------------
-- Table structure for `system_menu`
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
create table `system_menu`
(
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单id',
    `parent_id` bigint COMMENT '父级id',
    `sn` varchar(50) COMMENT '编号',
    `name` varchar(50) COMMENT '菜单名称',
    `description` varchar(500) COMMENT '菜单描述',
    `target_url` varchar(500) COMMENT '菜单url链接，支持spring antpathmatcher表达式',
    `icon_url` varchar(500) COMMENT '菜单图标路径',
    `show_index` int COMMENT '显示顺序',
    `type` int COMMENT '类型，0：菜单，1：按钮',
    `method` int COMMENT 'HTTP方法(用于权限控制)，0：GET，1：POST，2：PUT，3：DELETE，...',
    `status` int COMMENT '状态，0：启用，1：禁用',
    `creator` bigint COMMENT '创建者',
    `updator` bigint COMMENT '创建者',
    `create_date` datetime COMMENT '创建时间',
    `update_date` datetime COMMENT '最后更新时间',
    `default_open` tinyint default 0 COMMENT '是否默认展开，0：否，1：是',
    primary key(id)
)COMMENT '系统菜单';

-- ----------------------------
-- Table structure for `system_user`
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
create table `system_user`
(
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `auth_id` bigint COMMENT '用户认证ID',
    `name` varchar(50) COMMENT '真实姓名',
    `creator` bigint COMMENT '创建者',
    `phone` varchar(50) COMMENT '联系电话',
    `email` varchar(200) COMMENT '电子邮箱',
    `user_type` int COMMENT '类型（1：系统管理员；2：业务操作员）',
    `organization_id` bigint DEFAULT NULL COMMENT '机构ID',
    `sn` varchar(50) DEFAULT NULL COMMENT '员工工号',
    primary key(id)
)COMMENT '系统用户信息';


-- ----------------------------
-- Table structure for `system_user_auth`
-- ----------------------------
DROP TABLE IF EXISTS `system_user_auth`;
create table `system_user_auth`
(
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `login_name` varchar(50) COMMENT '登录账号',
    `password` varchar(100) COMMENT '密码',
    `status` int COMMENT '状态，0：启用，1：禁用',
    `creator` bigint COMMENT '创建者',
    `create_date` datetime COMMENT '创建时间',
    `login_date` datetime COMMENT '最近登录时间',
    `update_pass_date` datetime COMMENT '最近修改密码时间',
    primary key(id),
    UNIQUE KEY `UK_LOGIN_NAME` (`login_name`) USING BTREE
)COMMENT '系统用户认证信息';



-- ----------------------------
-- Table structure for `system_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
create table `system_user_role`
(
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `auth_id` bigint COMMENT '用户认证ID',
    `role_id` bigint COMMENT '角色ID',
    primary key(id)
)COMMENT '系统用户角色';

-- ----------------------------
-- Table structure for `system_config`
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
create table `system_config`
(
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `system_key` varchar(50) COMMENT '参数-键',
    `system_value` varchar(2000) COMMENT '参数-值',
    `description` varchar(500) COMMENT '参数-描述',
    `creator` bigint COMMENT '创建者',
    `create_date` datetime COMMENT '创建时间',
    `updator` bigint COMMENT '创建者',
    `update_date` datetime COMMENT '最后更新时间',
    primary key(id)
)COMMENT '系统参数';

-- ----------------------------
-- Table structure for `operation_log`
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
create table `operation_log`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `user_type` int(11) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `login_name` varchar(50) DEFAULT NULL,
  `form_name` varchar(50) DEFAULT NULL COMMENT '表单名称',
  `record_id` varchar(100) DEFAULT NULL COMMENT '表单记录id',
  `type` varchar(50) DEFAULT NULL COMMENT '操作类型: 增 , 删 , 改;',
  `detail` varchar(2000) DEFAULT NULL COMMENT '详细操作信息',
  `operation_date` datetime DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
)COMMENT '操作日志';

-- ----------------------------
-- Table structure for `user_login_fail`
-- ----------------------------
DROP TABLE IF EXISTS `user_login_fail`;
CREATE TABLE `user_login_fail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(50) DEFAULT NULL COMMENT '登陆名称',
  `fail_times` int(11) DEFAULT NULL COMMENT '登陆失败次数',
  `last_fail_time` datetime DEFAULT NULL COMMENT '最后登陆失败时间',
  `reverse` varchar(50) DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户登陆失败表';



-- ----------------------------
-- Table structure for `up_file`
-- ----------------------------
DROP TABLE IF EXISTS `up_file`;
create table `up_file`
(
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `old_file_name` varchar(255) COMMENT '原文件名',
    `new_file_name` varchar(255) COMMENT '新文件名',
    `file_size` int COMMENT '文件大小',
    `file_type` int COMMENT '文件类型',
    `createor` bigint COMMENT '上传者',
    `state` int COMMENT '状态',
    `file_path` varchar(255) COMMENT '文件路径',
    `create_date` datetime COMMENT '上传时间',
    primary key(id)
)COMMENT '上传文件';


-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字段id',
  `parentid` bigint(20) DEFAULT '0' COMMENT '上级字典id号',
  `name` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `dictkey` varchar(50) DEFAULT NULL COMMENT '字典key',
  `value` varchar(50) DEFAULT NULL COMMENT '字典值',
  `description` varchar(512) DEFAULT NULL COMMENT '描述',
  `status` int(11) DEFAULT NULL COMMENT '状态，0：禁用，1：启用',
  `isdefault` int(11) DEFAULT NULL COMMENT '是否默认, 0:不是, 1:是',
  `showindex` int(11) DEFAULT NULL COMMENT '显示顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=248 DEFAULT CHARSET=utf8 COMMENT='字典';

-- ----------------------------
-- Table structure for administrative_management
-- ----------------------------
DROP TABLE IF EXISTS `administrative_management`;
CREATE TABLE `administrative_management` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '行政id',
  `administrative_name` varchar(50) DEFAULT NULL COMMENT '行政名称',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话',
  `zip_code` varchar(50) DEFAULT NULL COMMENT '邮编',
  `person_charge` varchar(50) DEFAULT NULL COMMENT '负责人',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `is_delete` int(11) DEFAULT NULL COMMENT '是否删除（0：未删除；1：已删除；）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='行政机构管理';


-- ----------------------------
-- Table structure for administrative_division
-- ----------------------------
DROP TABLE IF EXISTS `administrative_division`;
CREATE TABLE `administrative_division` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '区域id',
  `parentid` bigint(20) DEFAULT '0' COMMENT '上级区域id号',
  `name` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话',
  `zip_code` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `person_charge` varchar(50) DEFAULT NULL COMMENT '负责人',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `is_delete` int(11) DEFAULT NULL COMMENT '是否删除（0：未删除；1：已删除；）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='行政区域管理';

-- 健康卡居民基本信息表
DROP TABLE IF EXISTS `resident_baseinfo`;
CREATE TABLE `resident_baseinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `real_name` varchar(30) DEFAULT NULL COMMENT '居民姓名',
  `health_number` varchar(30) DEFAULT NULL COMMENT '居民健康卡号',
  `social_number` varchar(20) DEFAULT NULL COMMENT '社保卡号',
  `id_number` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `period_validity_date` date DEFAULT NULL COMMENT '证件有效期',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `nation` varchar(10) DEFAULT NULL COMMENT '民族',
  `house_address` varchar(200) DEFAULT NULL COMMENT '户籍地址',
  `now_address` varchar(200) DEFAULT NULL COMMENT '新住地址',
  `post_code` varchar(10) DEFAULT NULL COMMENT '邮编',
  `phone` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `wrok_unit` varchar(50) DEFAULT NULL COMMENT '工作单位',
  `education` varchar(8) DEFAULT NULL COMMENT '学历',
  `office_id` bigint(20) DEFAULT NULL COMMENT '行政机构',
  `area_id` bigint(20) DEFAULT NULL COMMENT '区域机构',
  `del_falg` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记,(0,未删除1,已删除)',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleteor` bigint(20) DEFAULT NULL COMMENT '删除人',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='健康卡居民基本信息表';

-- 健康卡居民扩展信息表
   DROP TABLE IF EXISTS `resident_extendinfo`;
CREATE TABLE `resident_extendinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `base_id` bigint(20) NOT NULL COMMENT '居民基本信息表id',
  `health_number` varchar(30) NOT NULL COMMENT '居民健康卡号',
  `insurance_type` varchar(20) DEFAULT "无" COMMENT '医保类型',
  `illness_type` varchar(20) DEFAULT "无" COMMENT '病种类型',
  `disability_type` varchar(10)DEFAULT "无" COMMENT '残疾类型',
  `union_feature` varchar(8) DEFAULT "无" COMMENT '工会特征',
  `retired_cadres` varchar(8) DEFAULT "无" COMMENT '离休干部',
  `help_house` varchar(10) DEFAULT "无" COMMENT '扶贫户',
  `low_type` varchar(20) DEFAULT "无" COMMENT '低保类型',
  `del_falg` tinyint(1) DEFAULT "1" COMMENT '删除标记,(1,未删除,0,已删除)',
  `creator` bigint(20) DEFAULT null COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleteor` bigint(20) DEFAULT NULL COMMENT '删除人',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='健康卡居民扩展信息表';
alter table resident_extendinfo add column is_disable_federation varchar(20)  DEFAULT "无" COMMENT '性质残联';
alter table resident_extendinfo add column is_civil_affairs varchar(20)  DEFAULT "无" COMMENT '民政性质';
-- 用户操作基本数据记录表
DROP TABLE IF EXISTS `update_resident_log`;
CREATE TABLE `update_resident_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '修改居民信息id',
  `resident_id` bigint(20) NOT NULL COMMENT '居民基本信息id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '修改居民信息人的ID',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户操作基本数据记录表';

-- ----------------------------
-- Table structure for platform
-- ----------------------------
CREATE TABLE `platform` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `appid` varchar(100) DEFAULT NULL COMMENT '平台id',
  `app_name` varchar(50) DEFAULT NULL COMMENT '应用名称',
  `app_secret` varchar(500) DEFAULT NULL COMMENT 'app对应公钥',
  `app_private_key` varchar(500) DEFAULT NULL COMMENT 'app对应私钥',
  `plat_name` varchar(50) DEFAULT NULL COMMENT '平台名称',
  `exchange_rule` int(11) DEFAULT NULL COMMENT '兑换规则：1K币=多少积分',
  `public_key` varchar(500) DEFAULT NULL COMMENT '交付公钥(第三方平台)',
  `private_key` varchar(1000) DEFAULT NULL COMMENT '交付私钥(ukepay)',
  `status` int(11) DEFAULT NULL COMMENT '状态:0启用,1禁用',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `updator` bigint(20) DEFAULT NULL COMMENT '修改者',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='接入平台信息';
-- 在系统用户表中添加area_id区域机构id字段
alter table system_user add column area_id bigint(20) null COMMENT '区域机构ID';



-- ----------------------------
-- Table structure for `field`
-- ----------------------------
DROP TABLE IF EXISTS `field`;
CREATE TABLE `field` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `field_name` varchar(20) DEFAULT NULL COMMENT '字段名称',
  `alias` varchar(50) DEFAULT NULL COMMENT '字段别名',
  `table_name` varchar(20) DEFAULT NULL COMMENT '该字段来源表',
  `describe` varchar(40) DEFAULT NULL COMMENT '字段描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='字段信息表';



-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '公告',
  `title` varchar(60) DEFAULT NULL COMMENT '公告标题',
  `content` varchar(2000) DEFAULT NULL COMMENT '公告内容',
  `start_time` datetime DEFAULT NULL COMMENT '公告开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '公告结束时间',
  `status` int(3) DEFAULT NULL COMMENT '公告状态：0：禁用 ，1：启用，2：删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- --------------------------------------
-- Table structure for `notice_link_role`
-- -------------------------------------
DROP TABLE IF EXISTS `notice_link_role`;
CREATE TABLE `notice_link_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '公告',
  `nid` bigint(20) NOT NULL COMMENT '公告ID',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `is_read` bigint(5) DEFAULT '0' COMMENT '是否已读0：未读，1 已读',
  `is_delete` bigint(5) DEFAULT '0' COMMENT '是否删除：0未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- --------------------------------------
-- Table structure for `info_management_log`
-- -------------------------------------
DROP TABLE IF EXISTS `info_management_log`;
CREATE TABLE `info_management_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '操作人姓名',
  `creator` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `create_date` datetime DEFAULT NULL COMMENT '操作时间',
  `type` int(2) DEFAULT NULL COMMENT '操作类型。1：添加；-1：删除; 0：修改',
  `form_name` varchar(20) DEFAULT NULL COMMENT '操作表单',
  `record_id` bigint(20) DEFAULT NULL COMMENT '操作信息ID',
  `details` text DEFAULT NULL COMMENT '操作详情',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='信息日志';

-- ----------------------------
-- 健康卡居民基本信息表
-- ----------------------------
DROP TABLE IF EXISTS `role_field`;
CREATE TABLE `role_field` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `field_id` bigint(20) DEFAULT NULL COMMENT '字段ID',
  `update_date` datetime DEFAULT NULL COMMENT '最新修改时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后修改人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='角色字段关联表，关联行政机构机构表与字段信息表field';

-- --------------------------------------
-- Table structure for `notice_link_role`
-- -------------------------------------
DROP TABLE IF EXISTS `residents_info`;
CREATE TABLE `residents_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bid_util` varchar(20) DEFAULT NULL COMMENT '申办单位',
  `bank_card_number` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `card_type` varchar(1) DEFAULT NULL COMMENT '卡的类别',
  `issuers_card_name` varchar(20) DEFAULT NULL COMMENT '发卡机构名称',
  `issuers_card_code` varchar(50) DEFAULT NULL COMMENT '发卡机构代码',
  `issuing_serial_number` varchar(50) DEFAULT NULL COMMENT '发卡序列号',
  `issuers_card_certificate` varchar(30) DEFAULT NULL COMMENT '发卡机构证书',
  `issuing_time` varchar(20) DEFAULT NULL COMMENT '发卡时间',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别',
  `national_code` varchar(50) DEFAULT NULL COMMENT '民族代码',
  `birthday` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `identity_number` varchar(20) DEFAULT NULL COMMENT '居民身份证号码',
  `card_validity_period` varchar(20) DEFAULT NULL COMMENT '卡有效期',
  `self_phone` varchar(20) DEFAULT NULL COMMENT '本人电话',
  `medical_payment` varchar(1) DEFAULT NULL COMMENT '医疗支付方式',
  `house_address` varchar(150) DEFAULT NULL COMMENT '户籍地址',
  `now_address` varchar(150) DEFAULT NULL COMMENT '现住地址',
  `contact_name` varchar(20) DEFAULT NULL COMMENT '联系人姓名',
  `contact_relation` varchar(20) DEFAULT NULL COMMENT '联系人关系',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系人电话',
  `education_level_code` varchar(50) DEFAULT NULL COMMENT '文化程度代码',
  `marital_status_code` varchar(50) DEFAULT NULL COMMENT '婚姻状况代码',
  `professional_code` varchar(50) DEFAULT NULL COMMENT '职业代码',
   `is_floating` bigint(4) DEFAULT '0' COMMENT '人员性质 0：常住人口，1：流动人口',
  `social_security_num` varchar(50) DEFAULT NULL COMMENT '社保卡号',
  `issuing_bank` varchar(50) DEFAULT NULL COMMENT '发卡银行',
  `card_status` varchar(10) DEFAULT NULL COMMENT '卡状态',
  `chip_num` varchar(50) DEFAULT NULL COMMENT '芯片号',
  `office_id` bigint(20) DEFAULT NULL COMMENT '行政机构',
  `area_id` bigint(20) DEFAULT NULL COMMENT '区域机构',
  `card_sync_status1` varchar(255) DEFAULT NULL COMMENT '成都银行健康卡同步状态:(0:未同步 1:同步成功 2:同步失败)',
  `card_sync_status2` varchar(255) DEFAULT NULL COMMENT '工商银行健康卡同步状态:(0:未同步 1:同步成功 2:同步失败)',
  `card_sync_status3` varchar(255) DEFAULT NULL,
  `del_falg` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记,(0,未删除1,已删除)',
  `deleteor` bigint(20) DEFAULT NULL COMMENT '删除人',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '修改者id',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='健康卡居民基本信息表';
-- ----------------------------
-- 居民信息采集表
-- ----------------------------
DROP TABLE IF EXISTS `resident_acquisition`;
CREATE TABLE `resident_acquisition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bid_util` varchar(20) DEFAULT NULL COMMENT '申办单位',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `identity_number` varchar(20) DEFAULT NULL COMMENT '居民身份证号码',
  `issuers_certificate_organ` varchar(20) DEFAULT NULL COMMENT '发证机关',
  `certificate_validity_period` varchar(20) DEFAULT NULL COMMENT '证件有效期',
  `national` varchar(10) DEFAULT NULL COMMENT '民族',
  `education_level` varchar(20) DEFAULT NULL COMMENT '文化程度',
  `house_address` varchar(150) DEFAULT NULL COMMENT '户籍地址',
  `now_address` varchar(150) DEFAULT NULL COMMENT '现住地址',
  `post_code` varchar(10) DEFAULT NULL COMMENT '邮编',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `New_rural_number` varchar(50) DEFAULT NULL COMMENT '新农合号',
  `social_security_num` varchar(20) DEFAULT NULL COMMENT '社保卡号',
  `salary_card_bank` varchar(20) DEFAULT NULL COMMENT '工资卡发放银行',
  `health_card_bank` varchar(20) DEFAULT NULL COMMENT '健康卡发放银行',
  `professional` varchar(30) DEFAULT NULL COMMENT '职业',
  `industry` varchar(30) DEFAULT NULL COMMENT '行业',
  `del_falg` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记,(0,未删除1,已删除)',
  `deleteor` bigint(20) DEFAULT NULL COMMENT '删除人',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '修改者id',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='居民信息采集表';

-- 为residents_info建立索引
ALTER TABLE `residents_info` ADD INDEX index_del_falg ( `del_falg` ); 
ALTER TABLE `residents_info` ADD INDEX index_update_date ( `update_date` );
ALTER TABLE `residents_info` ADD INDEX index_create_date ( `create_date` );
ALTER TABLE `residents_info` ADD INDEX index_name ( `name` );
ALTER TABLE `residents_info` ADD INDEX index_issuing_time ( `issuing_time` ); 
ALTER TABLE `residents_info` ADD INDEX index_identity_number ( `identity_number` );
ALTER TABLE `residents_info` ADD INDEX index_bid_util ( `bid_util` ); 
ALTER TABLE `residents_info` ADD INDEX index_bank_card_number ( `bank_card_number` ); 
ALTER TABLE `residents_info` ADD INDEX index_social_security_num ( `social_security_num` );

-- 为resident_extendinfo建立索引
ALTER TABLE `resident_extendinfo` ADD INDEX index_base_id ( `base_id` );
ALTER TABLE `resident_extendinfo` ADD INDEX index_health_number ( `health_number` );
ALTER TABLE `resident_extendinfo` ADD INDEX index_update_date ( `update_date` );
ALTER TABLE `resident_extendinfo` ADD INDEX index_create_date ( `create_date` );
ALTER TABLE `resident_extendinfo` ADD INDEX index_is_disable_federation ( `is_disable_federation` );
ALTER TABLE `resident_extendinfo` ADD INDEX index_is_civil_affairs ( `is_civil_affairs` ); 



SET FOREIGN_KEY_CHECKS=1;
