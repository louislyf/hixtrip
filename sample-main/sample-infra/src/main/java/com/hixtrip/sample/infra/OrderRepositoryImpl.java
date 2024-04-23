package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayStatusEnum;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.toOrderDO(order);
        return orderMapper.insert(orderDO);
    }

    @Override
    public int orderPaySuccess(CommandPay commandPay) {
        OrderDO order = orderMapper.selectById(commandPay.getOrderId());
        if(null != order){
            order.setPayStatus(PayStatusEnum.success.getCode());
            order.setPayTime(new Date());
            return orderMapper.updateById(order);
        }
        return 0;
    }

    @Override
    public int orderPayFail(CommandPay commandPay) {
        OrderDO order = orderMapper.selectById(commandPay.getOrderId());
        if(null != order){
            order.setPayStatus(PayStatusEnum.fail.getCode());
            order.setPayTime(new Date());
            return orderMapper.updateById(order);
        }
        return 0;
    }
}
