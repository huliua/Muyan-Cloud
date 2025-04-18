CREATE TABLE `t_code_share_template`
(
    `id`          bigint NOT NULL COMMENT 'ID',
    `infoId`      bigint NOT NULL COMMENT 'infoID',
    `name`        varchar(255) DEFAULT NULL COMMENT '字段名称',
    `type`        varchar(255) DEFAULT NULL COMMENT '类型',
    `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
    `required`    int          DEFAULT NULL COMMENT '是否必填',
    `sort`        int          DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;