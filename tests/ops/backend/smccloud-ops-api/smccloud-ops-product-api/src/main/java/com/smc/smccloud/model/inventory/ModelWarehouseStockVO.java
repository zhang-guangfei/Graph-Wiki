package com.smc.smccloud.model.inventory;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-02-24 14:27
 * Description:
 */
@Data
public class ModelWarehouseStockVO {

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 仓库代码
     */
    private String warehouseCode;

    /**
     * 库存属性ID
     */
    private Long propertyId;

    /**
     * 在库可用数量
     */
    private int avaQty_ty;

    /**
     * 在库专用数量
     */
    private int avaQty_zy;

    /**
     * 在途数量（订货中数量）
     */
    private int orderingQty;
}
