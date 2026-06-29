package com.smc.smccloud.model.inventory;

import lombok.Data;

/**
 * @author wuweidong
 * @create 2023/7/31 14:36
 * @description
 */
@Data
public class ModelOrderExpFreqVO {
    /**
     * 型号
     */
    private String modelNo;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 订单数量
     */
    private Integer orderCount;

    /**
     * 频率
     */
    private Integer freq;
}
