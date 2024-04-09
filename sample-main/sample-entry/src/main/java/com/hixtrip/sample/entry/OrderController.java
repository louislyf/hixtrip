package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.api.PayService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderCreateVO;
import com.hixtrip.sample.client.order.vo.PayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo 这是你要实现的
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    /**
     * todo 这是你要实现的接口
     *
     * @param commandOderCreateDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public OrderCreateVO order(@RequestBody CommandOderCreateDTO commandOderCreateDTO){

        Assert.notNull(commandOderCreateDTO, "request can not null");
        Assert.notNull(commandOderCreateDTO.getSkuId(), "request can not null");
        Assert.notNull(commandOderCreateDTO.getAmount(), "request can not null");

        //登录信息可以在这里模拟
        var userId = "lyf";
        commandOderCreateDTO.setUserId(userId);

        return orderService.createOrder(commandOderCreateDTO);
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(@RequestBody CommandPayDTO commandPayDTO) {
        Assert.notNull(commandPayDTO, "request can not null");
        Assert.notNull(commandPayDTO.getOrderId(), "request can not null");
        Assert.notNull(commandPayDTO.getPayStatus(), "request can not null");
        payService.payCallback(commandPayDTO);
        return "success";
    }

}
