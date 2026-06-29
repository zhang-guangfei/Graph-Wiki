package com.smc.smccloud.model.adjust;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author edp04
 * @title: StockAdjustDTO
 * @date 2022/04/22 10:04
 */
@Data
public class StockAdjustDTO {

    private String modelNo;

    private Integer quantity;

    private BigDecimal price;

    private String warehouseCode;
}
