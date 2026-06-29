package com.smc.smccloud.model.inventory;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-02-24 14:57
 * Description:
 */
@Data
public class ModelWarehouseStockDTO {

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 仓库代码
     */
    private String warehouseCode;

    /**
     * 仓库类型 TY-通用仓库 其他-专备仓库
     */
    private String inventoryTypeCode;

    /**
     * 在库可用数量
     */
    private int avaQty;
}
