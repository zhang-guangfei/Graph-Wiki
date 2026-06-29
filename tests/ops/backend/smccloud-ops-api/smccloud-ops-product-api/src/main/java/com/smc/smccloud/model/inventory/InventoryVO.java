package com.smc.smccloud.model.inventory;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryVO {

    /**
     * 仓库代码
     */
    private String warehouseCode;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 可用数量
     */
    private int avaQty;

    /**
     * 在库数量
     */
    private int quantity;

    /**
     * 预约库存
     */
    private int prepareQuantity;


    /**
     * inventory_id
     */
    private Long id;

    /**
     * 库存属性ID
     */
    private Long propertyId;

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
     * 项目号
     */
    private String projectCode;

    /**
     * 客户群代码
     */
    private String groupCustomerNo;

    /**
     * 营业情报号
     */
    private String salesInfoNo;

    /**
     * 部门代码
     */
    private String deptNo;

    private Date creTime;

    private Date modifyTime;

}
