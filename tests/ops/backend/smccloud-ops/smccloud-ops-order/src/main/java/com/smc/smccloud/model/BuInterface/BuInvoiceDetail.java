package com.smc.smccloud.model.BuInterface;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BuInvoiceDetail {
    private String invNo;
    private String countryOrigin;
    private String orderNo;
    private String itemNo;
    private String model;
    private String descriptionCustoms;
    private BigDecimal quantity;
    private BigDecimal quantityCustoms;
    private Integer cancelQty;
    private BigDecimal applyQty;
    /**
     * 原单价
     */
    private BigDecimal unitPrice;
    /**
     * 原金额
     */
    private BigDecimal amount;
    private Double weight;
    private BigDecimal trafficInsurance;
    private BigDecimal tax;
    private Date operatTime;
    private String nonCommercial;
    private Double gross;
    private String customsDeclarationNo;
    private String docNo;
    private Integer billSerialNoZh;
    //private  Integer sort;
    private Long serialId;

    private String invoiceOriginal;

    /**
     * 关税
     */
    private BigDecimal tariffTax;
    /**
     * 增值税
     */
    private BigDecimal addedTax;
    /**
     * 消费税
     */
    private BigDecimal exciseTax;

    /**
     * 其他税
     */
    private BigDecimal otherTax;


}
