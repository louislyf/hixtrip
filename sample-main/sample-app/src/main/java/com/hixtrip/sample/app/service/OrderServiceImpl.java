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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private CommodityDomainService commodityDomainService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    @Transactional
    public OrderCreateVO createOrder(CommandOderCreateDTO commandOderCreateDTO){
        String lockKey = "lock:order";
        String lockVal = UUID.randomUUID().toString();
        boolean takeLock = redisTemplate.opsForValue().setIfAbsent(lockKey, lockVal, 20, TimeUnit.SECONDS);
        if(!takeLock){
            //不让创建锁，即未抢到锁，返回失败
            return OrderCreateVO.builder().msg("error").build();
        }

        try {

            //查询库存
            Inventory inventory = inventoryDomainService.getInventory("skuId:"+commandOderCreateDTO.getSkuId());

            //判断是否可以下单
            Assert.notNull(inventory, "商品信息不存在！");
            Assert.isTrue(inventory.getSellableQuantity() > 0, "可售库存不足");
            Assert.isTrue(inventory.getSellableQuantity() - commandOderCreateDTO.getAmount() > 0, "可售库存不足");

            //创建订单
            Order order = OrderConvertor.INSTANCE.toOrder(commandOderCreateDTO);
            order = order.toBuilder().id(SnowFlake.nextId() + "")
                    .createBy(commandOderCreateDTO.getUserId())
                    .payStatus(PayStatusEnum.draft.getCode()) //待支付
                    .money(commodityDomainService.getSkuPrice(commandOderCreateDTO.getSkuId()).multiply(BigDecimal.valueOf(commandOderCreateDTO.getAmount())))
                    .delFlag(0L)
                    .build();

            //保存订单
            int save_result = orderDomainService.createOrder(order);
            OrderCreateVO orderCreateVO = OrderConvertor.INSTANCE.toOrderCreateVO(order);
            if(save_result > 0) {
                //保存成功才修改库存
                inventoryDomainService.changeInventory("skuId:" + commandOderCreateDTO.getSkuId(),
                        inventory.getSellableQuantity() - commandOderCreateDTO.getAmount(),
                        commandOderCreateDTO.getAmount() + inventory.getWithholdingQuantity(),
                        inventory.getOccupiedQuantity());
                orderCreateVO.setMsg("save success");
            }else{
                orderCreateVO.setMsg("save fail");
            }
            return orderCreateVO;

        } finally {
            log.info("----无论失败成功都要释放分布式锁----");
            //无论失败成功都要释放分布式锁
            //且确保本线程加的锁只能本线程才能释放
            if(lockVal.equals(redisTemplate.opsForValue().get(lockKey))){
                redisTemplate.delete(lockKey);
            }
        }
    }
}
