package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付失败 的策略
 */
@Service
public class PayFailService implements PayStrategy{

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public boolean isSupport(CommandPay commandPay) {
        if(PayStatusEnum.fail.getCode().equals(commandPay.getStatus())){
            return true;
        }
        return false;
    }

    @Override
    public int payAction(CommandPay commandPay) {
        return orderDomainService.orderPayFail(commandPay);
    }
}
