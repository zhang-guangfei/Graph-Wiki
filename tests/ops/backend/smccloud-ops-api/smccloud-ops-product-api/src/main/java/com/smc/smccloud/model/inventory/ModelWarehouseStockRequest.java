package com.smc.smccloud.model.inventory;

import lombok.Data;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-01-18 09:57
 * Description: 型号库存查询条件
 */
@Data
public class ModelWarehouseStockRequest {

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 型号数组
     */
    private List<String> modelNos;

    /**
     * 仓库代码
     */
    private String warehouseCode;

    private List<String> warehouseCodeList;

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

}
