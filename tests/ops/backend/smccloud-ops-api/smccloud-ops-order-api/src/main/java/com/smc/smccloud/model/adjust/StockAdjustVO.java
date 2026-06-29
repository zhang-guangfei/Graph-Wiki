package com.smc.smccloud.model.adjust;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StockAdjustVO {

    private Long id;

    private String invoiceNo;

    private String fullOrderNo;

    private String orderNo;

    private Integer itemNo;

    private Integer splitItemNo;

    private String modelNo;

    private Integer quantity;

    private Integer adjustType;

    private Integer optCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private BigDecimal price;

    private BigDecimal amount;

    private String warehouseCode;

    private String pplNo;

    private String projectNo;

    private String customerNo;

    private String groupCustomerNo;

    private String inventoryTypeCode;

    private Long inventoryPropertyId;

    private String reason;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date adjustDate;

    private String supplierCode;

    private BigDecimal exchangeRate;

    private String currency;
    private String resultMsg;

}
