package com.smc.smccloud.model.inventory;

import lombok.Data;

@Data
public class WarehouseInventoryVO {
    /**
     * 型号
     */
    private String modelNo;

    /**
     * 仓库代码
     */
    private String warehouseCode;

    /**
     * 通用在库可用数量
     */
    private int tyAvaQty;

    /**
     * 专用数量
     */
    private int zyAvaQty;

    /**
     * 订货中
     */
    private  int ordQty;
}
