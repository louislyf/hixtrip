package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 支付成功 处理
 */
@Service
public class PaySuccessService implements PayStrategy{

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public boolean isSupport(CommandPay commandPay) {
        if(PayStatusEnum.success.getCode().equals(commandPay.getPayStatus())){
            return true;
        }
        return false;
    }

    @Override
    public void payAction(CommandPay commandPay) {
        orderDomainService.orderPaySuccess(commandPay);
    }
}
