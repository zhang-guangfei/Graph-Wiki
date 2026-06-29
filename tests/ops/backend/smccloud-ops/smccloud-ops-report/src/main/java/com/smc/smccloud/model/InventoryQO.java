package com.smc.smccloud.model;

import lombok.Data;

@Data
public class InventoryQO {

    //库存表类别： 在库、在途
    private String inventoryStatus;
    private String modelno;

    private String warehouseCode;
    private String warehouseName;
    private String warehouseType;

    //库存分类：顾客在库，战略在库、通用在库
    private String inventoryTypeCode;
    private String customerNo;
    private String ppl;
    private String projectCode;
    private String groupCustomerNo;
    private String salesInfoNo;

}