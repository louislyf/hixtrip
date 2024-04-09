package com.hixtrip.sample.domain.inventory.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 库存信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Inventory {

    //可售库存
    private Long sellableQuantity;
    //预占库存
    private Long withholdingQuantity;
    //占用库存
    private Long occupiedQuantity;
}
