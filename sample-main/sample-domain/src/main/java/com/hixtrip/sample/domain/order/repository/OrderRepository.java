package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 领域层定义接口，基础设施层实现具体查询方式，如查数据库、缓存、调用第三方SDK等
 */
public interface OrderRepository {

    /**
     * 创建待付款订单
     */
    int createOrder(Order order);

    /**
     * 待付款订单支付成功
     */
    int orderPaySuccess(CommandPay commandPay) ;

    /**
     * 待付款订单支付失败
     */
    int orderPayFail(CommandPay commandPay) ;
}
