package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 重复支付 的策略
 */
@Service
public class RepeatPayService implements PayStrategy{

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public boolean isSupport(CommandPay commandPay) {
        if(PayStatusEnum.repeatPay.getCode().equals(commandPay.getStatus())){
            return true;
        }
        return false;
    }

    @Override
    public int payAction(CommandPay commandPay) throws Exception{
        // 重复支付的 处理代码
        return 1;
    }
}
