package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;

public interface PayStrategy {

    /**
     * 用来判断具体执行哪一个实现类
     * @param commandPay
     * @return
     */
    boolean isSupport(CommandPay commandPay);

    /**
     * 支付结果处理
     * @param commandPay
     */
    void payAction(CommandPay commandPay);

}
