package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.PayService;
import com.hixtrip.sample.app.convertor.PayConvertor;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.strategy.PayOperationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class PayServiceImpl implements PayService {

    @Autowired
    private PayDomainService payDomainService;

    @Autowired
    private PayOperationContext payOperationContext;

    /**
     * 支付回调
     */
    public int payCallback(CommandPayDTO commandPay) throws Exception{

        //记录支付回调结果
        payDomainService.payRecord(PayConvertor.INSTANCE.toCommandPay(commandPay));

        //处理支付结果
        return payOperationContext.payAction(PayConvertor.INSTANCE.toCommandPay(commandPay));
    }
}
