package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取sku当前库存
     * @param skuId
     * @return
     */
    @Override
    public Inventory getInventory(String skuId) {
        Object sku = redisTemplate.opsForValue().get(skuId);
        //如果没有缓存，则从数据库查询后缓存进来
        if(null == sku){
            //查库存表，这边只是模拟
            redisTemplate.opsForValue().set(skuId, Inventory.builder()
                    .sellableQuantity(100L)
                    .withholdingQuantity(0L)
                    .occupiedQuantity(0L).build());

            sku = redisTemplate.opsForValue().get(skuId);
        }
        return (Inventory)sku;
    }


    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {

        redisTemplate.opsForValue().set(skuId, Inventory.builder()
                .sellableQuantity(sellableQuantity)
                .withholdingQuantity(withholdingQuantity)
                .occupiedQuantity(occupiedQuantity).build());

        return true;
    }
}
