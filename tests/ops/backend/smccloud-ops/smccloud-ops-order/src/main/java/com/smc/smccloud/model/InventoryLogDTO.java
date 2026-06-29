package com.smc.smccloud.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author C18117
 * @title: InventoryLogDTO
 * @date 2022/11/22 15:56
 */
@Data
public class InventoryLogDTO {

    private String voucherType;

    private String modelNo;

    private Integer quantity;

    private String orderNo;

    private Integer itemNo;

    private BigDecimal lotPrice;

    private Integer lotQty;
}
