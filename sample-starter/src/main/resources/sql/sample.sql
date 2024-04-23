#todo 你的建表语句,包含索引
DROP TABLE IF EXISTS `sample`;
CREATE TABLE `sample` (
                          `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
                          `del_flag` int DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
                          `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
                          `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                          `update_by` varchar(30) DEFAULT NULL COMMENT '修改人',
                          `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                          PRIMARY KEY (`id`),
                          KEY `idx_sample_name` (`name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='DO示例表';

