package com.smc.smccloud.model.inventory;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-02-11 14:42
 * Description:
 */
@Data
public class SpecStockVO {

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 仓库代码
     */
    private String warehouseCode;

    /**
     * 库存类型
     */
    private String inventoryTypeCode;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * ppl
     */
    private String ppl;

    /**
     * 项目代码
     */
    private String projectCode;

    /**
     * 集团代码
     */
    private String groupCustomerNo;

    /**
     * 客户专备数量
     */
    private int avaQty;
}
