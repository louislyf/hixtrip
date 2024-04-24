--背景: 近期订单量级2000W, 不考虑增长。
--主查询场景如下:
--1. 买家频繁查询我的订单,实时性要求高。
--2. 卖家频繁查询我的订单,允许秒级延迟。

--首先，根据2000W的量级，实时性又要高，同时也考虑负载均衡的话，要增加数据库以提高连接数，采用水平分库水平分表
--分库键 采用id主键对n取余(n为数据库个数)，相同余数存同一个数据库。
--另外，将买家和卖家的订单表分开，买家订单表做分库不分表， 卖家订单表不做分库分表已降低开发维护难度。

--不分库分表ddl
CREATE TABLE `order_tbl` (
                               `id` varchar(30) NOT NULL COMMENT '订单号',
                               `user_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '购买人',
                               `sku_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'SkuId',
                               `amount` int DEFAULT NULL COMMENT '购买数量',
                               `money` decimal(15,2) DEFAULT NULL COMMENT '购买金额',
                               `pay_time` datetime DEFAULT NULL COMMENT '购买时间',
                               `pay_status` varchar(2) DEFAULT NULL COMMENT '支付状态',
                               `del_flag` int DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
                               `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_by` varchar(30) DEFAULT NULL COMMENT '修改人',
                               `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                               PRIMARY KEY (`id`),
                               KEY `idx_order_user_id` (`user_id`),
                               KEY `idx_order_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';


--以下为分库分表ddl
--第一个库的建表语句
CREATE TABLE `order_tbl_0` (
                               `id` varchar(30) NOT NULL COMMENT '订单号',
                               `user_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '购买人',
                               `sku_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'SkuId',
                               `amount` int DEFAULT NULL COMMENT '购买数量',
                               `money` decimal(15,2) DEFAULT NULL COMMENT '购买金额',
                               `pay_time` datetime DEFAULT NULL COMMENT '购买时间',
                               `pay_status` varchar(2) DEFAULT NULL COMMENT '支付状态',
                               `del_flag` int DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
                               `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_by` varchar(30) DEFAULT NULL COMMENT '修改人',
                               `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                               PRIMARY KEY (`id`),
                               KEY `idx_order_user_id` (`user_id`),
                               KEY `idx_order_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='买家订单表';
--第二个库的建表语句
CREATE TABLE `order_tbl_1` (
                         `id` varchar(30) NOT NULL COMMENT '订单号',
                         `user_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '购买人',
                         `sku_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'SkuId',
                         `amount` int DEFAULT NULL COMMENT '购买数量',
                         `money` decimal(15,2) DEFAULT NULL COMMENT '购买金额',
                         `pay_time` datetime DEFAULT NULL COMMENT '购买时间',
                         `pay_status` varchar(2) DEFAULT NULL COMMENT '支付状态',
                         `del_flag` int DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
                         `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
                         `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                         `update_by` varchar(30) DEFAULT NULL COMMENT '修改人',
                         `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                         PRIMARY KEY (`id`),
                         KEY `idx_order_user_id` (`user_id`),
                         KEY `idx_order_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='买家订单表';


--卖家订单表
CREATE TABLE `order_b` (
                               `id` varchar(30) NOT NULL COMMENT '订单号',
                               `user_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '购买人',
                               `sku_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'SkuId',
                               `amount` int DEFAULT NULL COMMENT '购买数量',
                               `money` decimal(15,2) DEFAULT NULL COMMENT '购买金额',
                               `pay_time` datetime DEFAULT NULL COMMENT '购买时间',
                               `pay_status` varchar(2) DEFAULT NULL COMMENT '支付状态',
                               `del_flag` int DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
                               `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_by` varchar(30) DEFAULT NULL COMMENT '修改人',
                               `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                               PRIMARY KEY (`id`),
                               KEY `idx_order_user_id` (`user_id`),
                               KEY `idx_order_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='卖家订单表';