package com.smc.smccloud.model.BuInterface;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BuInvoiceMaster {
    private  String invNo;
    private  String invoiceOriginal;
    private  String dlvyWay;
    private Date shipDate;
    private  String shipment;
    private  String plantMark;
    private  Integer quantity;
    private BigDecimal amount;
    private  String currency;
    private  Integer boxQty;
    private  Double gross;
    private  BigDecimal money;
    private  Date expectReceiptDate;
    private  Date receiptGoodsDate;
    /**
     * 运保费
     */
    private  BigDecimal trafficInsurance;
    private  String tax;
    private  Date receiveTime;
    private  Date operatTime;
    private BigDecimal tariffTax;
    private  BigDecimal addedTax;
    private  BigDecimal exciseTax;
    private   String supervisionCode;
    private  String customerCode;
    private  String statusCode;
    private  String billNo;
    private  Double suttle;
    private  String tradeMode;
    private  Date invDate;
    private  String bargainType;
    private  String paymentTerm;
    private  String importCustomer;
    private  BigDecimal rate;
    // bug 14223 关务发票导入时，需要增加对 到港时间赋值，同时传输给采购预到货
    private  Date arrivalPortDate; //到港时间
}
