package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

/**
 * 领域层定义接口，基础设施层实现具体查询方式，如查数据库、缓存、调用第三方SDK等
 */
public interface InventoryRepository {

    //获取sku当前库存
    Inventory getInventory(String skuId);

    //修改库存
    Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);
}
