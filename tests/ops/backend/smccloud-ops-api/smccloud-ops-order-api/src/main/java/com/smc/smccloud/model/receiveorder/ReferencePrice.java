package com.smc.smccloud.model.receiveorder;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lyc
 * @Date 2022/3/16 11:27
 * @Descripton 参考价格
 */

@Data
public class ReferencePrice {
    // 含税价格
    private BigDecimal priceIncludingTax;
    // 不含税价格
    private BigDecimal priceExcludingTax;
}
