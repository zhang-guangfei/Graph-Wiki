package com.smc.smccloud.model.stock;

import lombok.Data;

@Data
public class InventoryDto {

    private String inventoryScope;
    private String warehouseCode;
    private String warehouseName;
    private String modelNo;
    private Integer quantity;
    private Integer prepareQuantity;
    private Integer availableQuantity;

    private String inventoryStatus;
    private Long inventoryPropertyId;
    private String inventoryTypeCode;
    private String inventoryTypeName;
    private String customerNo;
    private String ppl;
    private String projectCode;
    private String groupCustomerNo;
    private String salesInfoNo;
    private Boolean riskFlag = false;
    private Integer availableRiskQuantity;

}

