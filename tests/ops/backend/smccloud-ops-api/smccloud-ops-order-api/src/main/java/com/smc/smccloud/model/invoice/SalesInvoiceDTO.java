package com.smc.smccloud.model.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SalesInvoiceDTO {

    private String orderNo;

    private String modelNo;

    private String invoiceNo;

    private Integer quantity;

    private BigDecimal price;

    private String productRiskLevel;

    private BigDecimal returnFeeAmt;

    private BigDecimal returnFeeRate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date invoiceDate;

    private Integer freq;

    private Integer customersOf12;

    private List<String> invoiceNoList;

    private Date shipTime; //发货时间

    // 出库区分
    private String stockCodeDescription;
}
