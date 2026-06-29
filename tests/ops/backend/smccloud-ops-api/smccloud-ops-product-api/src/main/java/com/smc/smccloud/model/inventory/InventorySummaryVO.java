package com.smc.smccloud.model.inventory;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 按型号可用库存汇总
 */
@Data
public class InventorySummaryVO {

    private String modelNo;

    /**
     * 在库库存
     */
    private int qtyOnHand;

    /**
     * 可用库存
     */
    private int avaQty;

    /**
     * 订货中库存
     */
    private int ordingQty;


    /**
     * 库存属性id
     */
    private Long propertyId;

    /**
     * Bin数量
     */
    private int binQty;

    /**
     * 月用量
     */
    private int monthAvgQty;

    /**
     * 可用月数
     */
    private BigDecimal canUseMonths;


}
