package com.hixtrip.sample.client.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * 订单创建返回值
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderCreateVO {
    /**
     * 订单号
     */
    private String id;

    /**
     * 返回信息
     */
    private String msg;

}
