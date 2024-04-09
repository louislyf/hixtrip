package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 策略模式的上下文
 */
@Component
public class PayOperationContext {

    private final List<PayStrategy> payStrategys;

    public PayOperationContext (List<PayStrategy> payStrategys) {
        this.payStrategys = payStrategys ;
    }

    public void payAction(CommandPay commandPay) {
        for (PayStrategy payStrategy: payStrategys) {
            if (payStrategy.isSupport(commandPay)){
                payStrategy.payAction(commandPay);
            }
        }
    }

}
