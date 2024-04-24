package com.hixtrip.sample.client.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付回调的入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPayDTO {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 付款金额
     */
    private String totalAmount;

    /**
     * 交易状态
     *
     * TRADE_SUCCESS 交易完成
     * TRADE_FINISHED 支付成功
     * WAIT_BUYER_PAY 交易创建
     * TRADE_CLOSED 交易关闭
     */
    private String tradeStatus;

}
