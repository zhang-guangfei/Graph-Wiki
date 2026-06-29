package com.smc.smccloud.model.inventory;

import lombok.Data;

@Data
public class WarehouseStockDO {


    /**
     * 型号
     */
    private String modelNo;

    /**
     * 仓库代码
     */
    private String warehouseCode;

    /**
     * 在库可用数量
     */
    private int tyAvaQty;

    /**
     * 在库专用数量
     */
    private int zyAvaQty;

    /**
     * 在途数量（订货中数量）
     */
    private int ordingQty;
}
