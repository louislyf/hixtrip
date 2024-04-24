package com.hixtrip.sample.domain.pay.model;

import com.hixtrip.sample.domain.pay.strategy.PayStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPay {

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
     * 获取 支付结果
     * @return
     */
    public String getStatus(){
        if(PayStatusEnum.fail.getCode().equals(payStatus)){
            return PayStatusEnum.fail.getCode();
        }else if(PayStatusEnum.success.getCode().equals(payStatus)){
            return PayStatusEnum.success.getCode();
        }else if(repeatPay()){
            return PayStatusEnum.repeatPay.getCode();
        }
        return "";
    }

    /**
     * 保存 支付结果
     * @return
     */
    public boolean save(){

        return true;
    }

    /**
     * 判断是否重复支付
     * @return
     */
    public boolean repeatPay(){
        //查订单表，看看订单状态，以及金额是否一致，用来判断是否重复支付
        //同时也查一下回调记录表，是否存在相同的订单
        return false;
    }
}