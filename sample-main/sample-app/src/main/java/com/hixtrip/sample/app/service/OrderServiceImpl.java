package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.vo.OrderCreateVO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.strategy.PayStatusEnum;
import com.hixtrip.sample.infra.db.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private CommodityDomainService commodityDomainService;



    @Override
    @Transactional
    public OrderCreateVO createOrder(CommandOderCreateDTO commandOderCreateDTO){

        //查询库存
        Inventory inventory = inventoryDomainService.getInventory(commandOderCreateDTO.getSkuId());

        //判断是否可以下单
        Assert.notNull(inventory, "商品信息不存在！");
        Assert.isTrue(inventory.getSellableQuantity() > 0, "可售库存不足");
        Assert.isTrue(inventory.getSellableQuantity()-commandOderCreateDTO.getAmount() > 0, "可售库存不足");

        //修改库存
        inventoryDomainService.changeInventory(commandOderCreateDTO.getSkuId(),
                inventory.getSellableQuantity()-commandOderCreateDTO.getAmount(),
                commandOderCreateDTO.getAmount()+inventory.getWithholdingQuantity(),
                inventory.getOccupiedQuantity());

        //创建订单
        Order order = OrderConvertor.INSTANCE.toOrder(commandOderCreateDTO);
        order.toBuilder().id(SnowFlake.nextId()+"")
                .createBy(commandOderCreateDTO.getUserId())
                .createTime(LocalDateTime.now())
                .payStatus(PayStatusEnum.draft.getCode()) //待支付
                .money(commodityDomainService.getSkuPrice(commandOderCreateDTO.getSkuId()).multiply(BigDecimal.valueOf(commandOderCreateDTO.getAmount())))
                .delFlag(0L);

        orderDomainService.createOrder(order);

        return OrderConvertor.INSTANCE.toOrderCreateVO(order);
    }
}
