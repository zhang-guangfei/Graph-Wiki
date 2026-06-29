package com.smc.smccloud.model.inventory;

import lombok.Data;

@Data
public class WarehouseInventoryDO {

    private String modelNo;

    private Integer quantity;

    private Integer prepareQty;

    private String inventoryTypeCode;

    private String warehouseCode;
}
