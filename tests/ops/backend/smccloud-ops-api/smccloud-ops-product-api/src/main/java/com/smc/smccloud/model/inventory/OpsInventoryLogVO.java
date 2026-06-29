package com.smc.smccloud.model.inventory;

import lombok.Data;

import java.util.Date;

@Data
public class OpsInventoryLogVO {

    private Long inventoryLogId;

    private Long inventoryId;

    private Integer quantity;

    private String voucherCode;

    private String voucherType;

    private String orderNo;

    private Integer itemNo;

    private String modelno;

    private String invoiceNo;

    private String warehouseCode;

    private Integer version;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private Integer type;

    private String inventoryTypeCode;

    private String customerNo;

    private String ppl;

    private String projectCode;

    private String groupCustomerNo;

    private String salesInfoNo;

    private Long propertyId;

    private String merInventoryLogId;

    // ---------------- 先行在库预决算----------
    private Integer pushQty;

    private Integer roQty;

    private Integer avaQty;

    private Date requirementDate;
}
