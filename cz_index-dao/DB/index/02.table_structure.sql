SET NAMES 'utf8';
CREATE DATABASE IF NOT EXISTS db_cz_cms DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

USE db_cz_cms;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `address`;
-- ----------------------------
-- 地址表
-- ----------------------------
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `person_id` bigint(20) DEFAULT NULL COMMENT '基本身份信息id',
  `mpi_id` varchar(32) DEFAULT NULL COMMENT '主索引id',
  `address_type_code` varchar(10) DEFAULT NULL COMMENT '地址类别代码',
  `address` varchar(50) DEFAULT NULL COMMENT '详细地址',
  `postal_code` varchar(6) DEFAULT NULL COMMENT '邮编',
  `del_falg` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记,(0,未删除1,已删除)',
  `deleteor` bigint(20) DEFAULT NULL COMMENT '删除人',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_unit` varchar(20) DEFAULT NULL COMMENT '创建机构',
  `last_modify_user` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `last_modify_unit` varchar(32) DEFAULT NULL COMMENT '最后修改机构',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地址表';

-- ----------------------------
-- 卡
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mpi_id` varchar(32) DEFAULT NULL COMMENT '主索引id',
  `person_id` bigint(20) DEFAULT NULL COMMENT '基本身份信息id',
  `card_type_code` varchar(5) DEFAULT NULL COMMENT '卡类别代码',
  `card_no` varchar(20) DEFAULT NULL COMMENT '卡号',
  `card_code` varchar(20) DEFAULT NULL COMMENT '卡内号',
  `create_unit` varchar(20) DEFAULT NULL COMMENT '发卡机构',
  `valid_time` date DEFAULT NULL COMMENT '卡有效期',
  `status` int(1) DEFAULT NULL COMMENT '卡状态:0:正常1:挂失2:注销3:失效          ',
  `del_falg` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记,(0,未删除1,已删除)',
  `deleteor` bigint(20) DEFAULT NULL COMMENT '删除人',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡';
-- ----------------------------
-- 证件表
-- ----------------------------
DROP TABLE IF EXISTS `certificate`;
CREATE TABLE `certificate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mpi_id` varchar(32) DEFAULT NULL COMMENT '主索引id',
  `person_id` bigint(20) DEFAULT NULL COMMENT '基本身份信息id',
  `certificate_type_code` varchar(10) DEFAULT NULL COMMENT '证件类别代码',
  `certificate_no` varchar(20) DEFAULT NULL COMMENT '证件号码',
  `del_falg` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记,(0,未删除1,已删除)',
  `deleteor` bigint(20) DEFAULT NULL COMMENT '删除人',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='证件表';
-- ----------------------------
-- 联系人表
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mpi_id` varchar(32) DEFAULT NULL COMMENT '主索引id',
  `person_id` bigint(20) DEFAULT NULL COMMENT '基本身份信息id',
  `certificate_type_code` varchar(5) DEFAULT NULL COMMENT '联系人证件类别代码',
  `certificate_no` varchar(20) DEFAULT NULL COMMENT '联系人证件号码',
  `contact_name` varchar(20) DEFAULT NULL COMMENT '联系人姓名',
  `contact_no` varchar(12) DEFAULT NULL COMMENT '联系人电话',
  `del_falg` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记,(0,未删除1,已删除)',
  `deleteor` bigint(20) DEFAULT NULL COMMENT '删除人',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人表';
-- ----------------------------
-- 联系方式
-- ----------------------------
DROP TABLE IF EXISTS `contact_way`;
CREATE TABLE `contact_way` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mpi_id` varchar(32) DEFAULT NULL COMMENT '主索引id',
  `contact_type_code` varchar(11) DEFAULT NULL COMMENT '联系人方式',
  `contact_no` varchar(32) DEFAULT NULL COMMENT '联系号码',
  `del_falg` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记,(0,未删除1,已删除)',
  `deleteor` bigint(20) DEFAULT NULL COMMENT '删除人',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系方式';
-- ----------------------------
-- 基本身份信息
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mpi_id` varchar(32) DEFAULT NULL COMMENT '主索引id',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `blood_type_code` varchar(10) DEFAULT NULL COMMENT '血型代码',
  `education_code` varchar(10) DEFAULT NULL COMMENT '文化程度代码',
  `address` varchar(255) DEFAULT NULL COMMENT '户籍地址',
  `insurance_code` varchar(10) DEFAULT NULL COMMENT '保险类别',
  `marital_status_code` varchar(10) DEFAULT NULL COMMENT '婚姻状况代码',
  `person_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `id_card` varchar(32) DEFAULT NULL COMMENT '身份证',
  `card_no` varchar(32) DEFAULT NULL COMMENT '一卡通号码',
  `nationality_code` varchar(10) DEFAULT NULL COMMENT '国籍代码',
  `nation_code` varchar(10) DEFAULT NULL COMMENT '民族代码',
  `registered_permanent` varchar(10) DEFAULT NULL COMMENT '户籍标志',
  `deceased_ind` int(11) DEFAULT NULL COMMENT '死亡标记0:表示未死亡1:表示已死亡',
  `deceased_time` datetime DEFAULT NULL COMMENT '死亡时间',
  `rh_blood_code` varchar(10) DEFAULT NULL COMMENT 'RH血型代码',
  `sex_code` varchar(10) DEFAULT NULL COMMENT '性别代码',
  `start_work_date` datetime DEFAULT NULL COMMENT '开始工作日期',
  `work_code` varchar(10) DEFAULT NULL COMMENT '工作类别代码',
  `work_place` varchar(255) DEFAULT NULL COMMENT '工作单位',
  `insurance_type` varchar(10) DEFAULT NULL COMMENT '医保类类别',
  `contact_no` varchar(32) DEFAULT NULL COMMENT '本人电话',
  `status` char(1) DEFAULT NULL COMMENT '状态 0-正常 1-注销  ',
  `del_falg` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记,(0,未删除1,已删除)',
  `deleteor` bigint(20) DEFAULT NULL COMMENT '删除人',
  `delete_date` datetime DEFAULT NULL COMMENT '删除时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基本身份信息';

-- ----------------------------
-- 身份信息推送记录
-- ----------------------------
DROP TABLE IF EXISTS `person_send_info`;
CREATE TABLE `person_send_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `person_id` bigint(20) DEFAULT NULL COMMENT '身份信息主键',
  `front_id` bigint(20) DEFAULT NULL COMMENT '前置机id',
  `batch_number` varchar(200) DEFAULT NULL COMMENT '推送批次号',
  `send_time` datetime DEFAULT NULL COMMENT '推送时间',
  `send_status` char(1) DEFAULT NULL COMMENT '推送状态 1-成功 0-失败',
  `remark` varchar(2000) DEFAULT NULL COMMENT '推送备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='身份信息推送记录';

-- ----------------------------
-- front_end_machine
-- ----------------------------
DROP TABLE IF EXISTS `front_end_machine`;
CREATE TABLE `front_end_machine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `front_end_machineCode` varchar(50) DEFAULT NULL COMMENT '前置机编码',
  `front_end_machineAddress` varchar(200) DEFAULT NULL COMMENT '前置机地址',
  `state` int(11) DEFAULT NULL COMMENT '状态，0：启用，1：禁用,3:异常',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='前置机管理';


-- ----------------------------
-- Table structure for address_sync_log
-- ----------------------------
DROP TABLE IF EXISTS `address_sync_log`;
CREATE TABLE `address_sync_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `address_id` bigint(20) DEFAULT NULL COMMENT '地址信息主键id',
  `front_id` bigint(20) DEFAULT NULL COMMENT '前置机id',
  `sync_status` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '0:未同步;1:已同步',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地址同步记录';

-- ----------------------------
-- Table structure for card_sync_log
-- ----------------------------
DROP TABLE IF EXISTS `card_sync_log`;
CREATE TABLE `card_sync_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `card_id` bigint(20) DEFAULT NULL COMMENT '卡信息主键id',
  `front_id` bigint(20) DEFAULT NULL COMMENT '前置机id',
  `sync_status` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '0:未同步;1:已同步',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡信息同步记录';

-- ----------------------------
-- Table structure for certificate_sync_log
-- ----------------------------
DROP TABLE IF EXISTS `certificate_sync_log`;
CREATE TABLE `certificate_sync_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `certificate_id` bigint(20) DEFAULT NULL COMMENT '证件信息主键id',
  `front_id` bigint(20) DEFAULT NULL COMMENT '前置机id',
  `sync_status` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '0:未同步;1:已同步',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='证件同步记录';

-- ----------------------------
-- Table structure for contact_sync_log
-- ----------------------------
DROP TABLE IF EXISTS `contact_sync_log`;
CREATE TABLE `contact_sync_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `contact_id` bigint(20) DEFAULT NULL COMMENT '联系人信息主键id',
  `front_id` bigint(20) DEFAULT NULL COMMENT '前置机id',
  `sync_status` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '0:未同步;1:已同步',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人信息同步记录';

-- ----------------------------
-- Table structure for contact_way_sync_log
-- ----------------------------
DROP TABLE IF EXISTS `contact_way_sync_log`;
CREATE TABLE `contact_way_sync_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `contact_way_id` bigint(20) DEFAULT NULL COMMENT '联系方式主键id',
  `front_id` bigint(20) DEFAULT NULL COMMENT '前置机id',
  `sync_status` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '0:未同步;1:已同步',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系方式同步记录';

-- ----------------------------
-- Table structure for person_sync_log
-- ----------------------------
DROP TABLE IF EXISTS `person_sync_log`;
CREATE TABLE `person_sync_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `person_id` bigint(20) DEFAULT NULL COMMENT '身份信息主键id',
  `front_id` bigint(20) DEFAULT NULL COMMENT '前置机id',
  `sync_status` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '0:未同步;1:已同步',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updator` bigint(20) DEFAULT NULL COMMENT '最后更新者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新时间',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基本身份信息同步记录';
-- ----------------------------
-- Table structure for hospital
-- ----------------------------
DROP TABLE IF EXISTS `hospital`;
CREATE TABLE `hospital` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) DEFAULT NULL COMMENT '医院名称',
  `address` varchar(200) DEFAULT NULL COMMENT '医院地址',
  `level` varchar(24) DEFAULT NULL COMMENT '医院等级',
  `h_key` varchar(20) DEFAULT NULL COMMENT '医院key',
  `pyCode` varchar(32) DEFAULT NULL COMMENT '医院的pyCode',
  `type` varchar(10) DEFAULT NULL COMMENT '医院的类型',
  `type_name` varchar(50) DEFAULT NULL COMMENT '医院类型名称',
  `status` tinyint(1) DEFAULT NULL COMMENT '医院的状态(0启用,1禁用，)',
  `phone` varchar(16) DEFAULT NULL COMMENT '医院电话',
  `del_falg` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记,(0,未删除1,已删除)',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '修改者id',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医院机构表';

alter table address change create_user  creator bigint(20);
alter table address change last_modify_user  updator bigint(20);
alter table address change create_time  create_date datetime;
alter table address change last_modify_time  update_date datetime;
ALTER TABLE contact ADD create_unit varchar(20) COMMENT'创建机构', ADD last_modify_unit varchar(32) COMMENT'最后修改机构';
ALTER TABLE contact_way ADD create_unit varchar(20) COMMENT'创建机构', ADD last_modify_unit varchar(32) COMMENT'最后修改机构';

-- certificate表中增加'create_unit'字段 和'last_modify_unit'
alter table certificate add column create_unit  varchar(20) DEFAULT NULL COMMENT'创建机构';
alter table certificate add column last_modify_unit varchar(20) DEFAULT NULL COMMENT '最后修改机构';
-- 联系方式表添加personId
alter table contact_way add column  person_id bigint(20) DEFAULT NULL COMMENT '基本身份信息id';
-- 卡片表 添加最后修改机构
alter table card add column last_modify_unit varchar(20) DEFAULT NULL COMMENT '最后修改机构';

-- 卡同步日志表 添加联合唯一约束
ALTER TABLE card_sync_log ADD UNIQUE KEY(`card_id`,`front_id`); 
-- 地址同步日志表 添加联合唯一约束
ALTER TABLE address_sync_log ADD UNIQUE KEY(`address_id`,`front_id`);
-- 联系人同步日志表 添加联合唯一约束
ALTER TABLE contact_sync_log ADD UNIQUE KEY(`contact_id`,`front_id`); 
-- 联系方式同步日志表 添加联合唯一约束
ALTER TABLE contact_way_sync_log ADD UNIQUE KEY(`contact_way_id`,`front_id`); 
-- 个人信息同步日志表 添加联合唯一约束
ALTER TABLE person_sync_log  ADD UNIQUE KEY(`person_id`, `front_id`);
-- 证件信息同步日志表 添加联合唯一约束
ALTER TABLE certificate_sync_log ADD UNIQUE KEY(`certificate_id`, `front_id`);

SET FOREIGN_KEY_CHECKS=1;
