package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.api.PayService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderCreateVO;
import com.hixtrip.sample.util.Result;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result<?> order(@RequestBody CommandOderCreateDTO commandOderCreateDTO){
        //参数校验
        //登录信息可以在这里模拟
        var userId = "lyf";
        commandOderCreateDTO.setUserId(userId);

        OrderCreateVO vo = orderService.createOrder(commandOderCreateDTO);
        if(vo == null || StringUtils.isNotBlank(vo.getMsg())){
            Result.fail(vo);
        }
        return Result.success(vo);
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public Result<String> payCallback(@RequestBody CommandPayDTO commandPayDTO) {
        //参数校验
        if(commandPayDTO == null || StringUtils.isBlank(commandPayDTO.getOrderId())){
            return Result.fail("参数不能为空");
        }

        try {
            payService.payCallback(commandPayDTO);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }

        return Result.success();
    }

}
