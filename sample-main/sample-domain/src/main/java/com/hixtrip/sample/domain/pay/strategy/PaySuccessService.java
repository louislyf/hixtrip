package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * 支付成功 的策略
 */
@Service
public class PaySuccessService implements PayStrategy{

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public boolean isSupport(CommandPay commandPay) {
        if(PayStatusEnum.success.getCode().equals(commandPay.getStatus())){
            return true;
        }
        return false;
    }

    @Override
    public int payAction(CommandPay commandPay) throws Exception{
        int result = orderDomainService.orderPaySuccess(commandPay);
        if(result == 0){
            throw new SQLException("支付成功回调处理失败");
        }
        return result;
    }
}
