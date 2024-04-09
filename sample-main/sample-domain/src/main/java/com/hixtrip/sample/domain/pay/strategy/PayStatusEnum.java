package com.hixtrip.sample.domain.pay.strategy;

import lombok.Getter;

/**
 * 支付状态的枚举
 */
@Getter
public enum PayStatusEnum {

    draft("0","待支付"),
    success("1", "支付成功"),
    fail("2", "支付失败");

    private final String code;
    private final String status;

    PayStatusEnum(String code, String status) {
        this.code = code;
        this.status = status;
    }

}
