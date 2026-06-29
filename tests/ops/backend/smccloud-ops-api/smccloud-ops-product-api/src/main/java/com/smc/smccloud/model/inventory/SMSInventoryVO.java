package com.smc.smccloud.model.inventory;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/10/17 12:14
 * @Descripton TODO
 */
@Data
public class SMSInventoryVO {

    // 仓库代码
    private String warehouseCode;

    // 仓库名称
    private String warehouseCodeName;

    // 库存类型编码
    private String inventoryTypeCode;

    // 通用在库的可用库存数量
    private int tyAvaQty;

    // 专用在库
    private int specQty;

    // 可生产数量
    private int quantityProduce;

    // 可组装数量
    private int quantityAssembly;

    // 可订货数量
    private int quantityOrder;

    private int quantity;

    private int prepareQty;

}
