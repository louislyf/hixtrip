package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;

/**
 * 支付的service层
 */
public interface PayService {

    /**
     * 支付回调
     */
    int payCallback(CommandPayDTO commandPay);

}
