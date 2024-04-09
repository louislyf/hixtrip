package com.hixtrip.sample.client.order.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 返回值
 */
@Data
@Builder
public class PayVO {
    private String code;
    private String msg;
}
