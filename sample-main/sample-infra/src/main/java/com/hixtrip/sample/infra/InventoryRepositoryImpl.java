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
        try {
            Inventory inventory = (Inventory)redisTemplate.opsForValue().get(skuId);
            return inventory;
        } catch (Exception e) {
            return null;
        }
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
        List<Object> result =null;
        int count = 0;
        do {
            redisTemplate.setEnableTransactionSupport(true);
            redisTemplate.watch(skuId); //观察是否改变
            redisTemplate.multi(); //开始事务
            redisTemplate.opsForValue().set(skuId, Inventory.builder()
                    .sellableQuantity(sellableQuantity)
                    .withholdingQuantity(withholdingQuantity)
                    .occupiedQuantity(occupiedQuantity).build());
            try {
                result = redisTemplate.exec();//执行事务
            } catch (Exception e) {
                count ++;
            }
        } while ((result == null || result.size() <= 0) && count<3);
        if(result == null || result.size() <= 0){
            return false;
        }
        return true;
    }
}
