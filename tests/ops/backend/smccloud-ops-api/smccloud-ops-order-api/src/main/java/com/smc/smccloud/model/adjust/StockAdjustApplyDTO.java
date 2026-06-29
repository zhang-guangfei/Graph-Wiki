package com.smc.smccloud.model.adjust;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StockAdjustApplyDTO {

    private Integer id;

    private String invoiceNo;

    private String orderNo;

    private String modelNo;

    private Integer quantity;

    private String[] adjustType;

    private Date adjustDate;

    private BigDecimal price;

    private String warehouseCode;

    private BigDecimal amount;

    private String groupCustomerNo;

    private String projectNo;

    private String pplNo;

    private String inventoryTypeCode;

    private String customerNo;

    private String reason;

    private String supplierCode;

    private BigDecimal exchangeRate;

    private String currency;
}
