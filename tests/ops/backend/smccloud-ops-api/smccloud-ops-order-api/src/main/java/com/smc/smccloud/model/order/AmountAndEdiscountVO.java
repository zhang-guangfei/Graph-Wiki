package com.smc.smccloud.model.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lyc
 * @Date 2023/7/31 8:57
 * @Descripton TODO
 */
@Data
public class AmountAndEdiscountVO {
    // 总金额
    private BigDecimal totalAmount;
    // E率
    private BigDecimal eDiscount;
}
