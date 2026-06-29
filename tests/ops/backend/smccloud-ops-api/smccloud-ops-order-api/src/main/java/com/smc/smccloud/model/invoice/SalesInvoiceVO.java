package com.smc.smccloud.model.invoice;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SalesInvoiceVO {

    private Long id;

    private String tradeCompanyId;

    private String rOrderNo;

    private String modelNo;

    private Integer quantity;

    private BigDecimal price;

    private String customerNo;

    private String userNo;

    private Integer dlvEntire;

    private BigDecimal amount;

    private BigDecimal taxAmount;

    private BigDecimal nTaxAmount;

    private Integer invFlag;

    private Date optDate;

    private String OptCode;

    private String classFlag;

    private String invoiceNo;

    private Date invDate;

    private String optState;

    private String stockNo;

    private String invFlag1;

    private String prodFlag;

    private String billNo;

    private String username;

    private Date optTime;

    private String receive_Emp;

    private String receive_Cust;

    private Date receive_date;

    private String receive_Remark;

    private String receive_OptState;

    private String purchaseNo;

    private String agentPrice_Flag;

    private String deptNo;

    private BigDecimal discountAmt;

    private String ordType;

    private String invoiceCode;

    private String invYype;

    private String invoiceNoJp;

    private BigDecimal taxRate;

    private BigDecimal nTaxDiscountAmt;

    private BigDecimal taxDiscountAmt;

    private Date insertTime;

    private Date cancelDate;

    private String extracted;

    private String extractTime;

    private String invoiceGroupKey;

    private String remark;

}
