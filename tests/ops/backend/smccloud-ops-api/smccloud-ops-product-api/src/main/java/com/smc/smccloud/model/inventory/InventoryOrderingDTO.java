package com.smc.smccloud.model.inventory;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-01-24 16:14
 * Description:
 */
@Data
public class InventoryOrderingDTO {

    private Long propertyId;

    private String modelNo;

    private Integer quantity;

    private Integer prepareQuantity;

    private String warehouseCode;

    private String inventoryTypeCode;

    private String customerNo;

    private String ppl;

    private String projectCode;

    private String groupCustomerNo;

    private String salesInfoNo;

    /**
     * 到货未入库
     */
    private Integer qtyW;

    /**
     * 到货未入库预占
     */
    private Integer pqtyW;

    /**
     * 调拨在途
     */
    private Integer qtyD;

    /**
     * 调拨在库预占
     */
    private Integer pqtyD;

    /**
     * 采购在途
     */
    private Integer qtyC;

    /**
     * 采购在途预占
     */
    private Integer pqtyC;

    /**
     * 生产在途
     */
    private Integer qtyP;

    /**
     * 生产在途预占
     */
    private Integer pqtyP;
}
