package com.smc.smccloud.model.invoice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "SalesInvoice")
public class SalesInvoiceDO {

    @TableId(value = "Id", type = IdType.AUTO)
    private String id;

    @TableField(value = "TradeCompanyId")
    private String tradeCompanyId;

    @TableField(value = "ROrderNo")
    private String rOrderNo;

    @TableField(value = "ModelNo")
    private String modelNo;

    @TableField(value = "Quantity")
    private Integer quantity;

    @TableField(value = "Price")
    private BigDecimal price;

    @TableField(value = "CustomerNo")
    private String customerNo;

    @TableField(value = "UserNo")
    private String userNo;

    @TableField(value = "DlvEntire")
    private String dlvEntire;

    @TableField(value = "Amount")
    private BigDecimal amount;

    @TableField(value = "TaxAmount")
    private BigDecimal taxAmount;

    @TableField(value = "NTaxAmount")
    private BigDecimal nTaxAmount;

    @TableField(value = "InvFlag")
    private String invFlag;

    @TableField(value = "OptDate")
    private Date optDate;

    @TableField(value = "OptCode")
    private String OptCode;

    @TableField(value = "ClassFlag")
    private String classFlag;

    @TableField(value = "InvoiceNo")
    private String invoiceNo;

    @TableField(value = "InvDate")
    private Date invDate;

    @TableField(value = "OptState")
    private String optState;

    @TableField(value = "StockNo")
    private String stockNo;

    @TableField(value = "InvFlag1")
    private String invFlag1;

    @TableField(value = "ProdFlag")
    private String prodFlag;

    @TableField(value = "BillNo")
    private String billNo;

    @TableField(value = "Username")
    private String username;

    @TableField(value = "OptTime")
    private Date optTime;

    @TableField(value = "Receive_Emp")
    private String receive_Emp;

    @TableField(value = "Receive_Cust")
    private String receive_Cust;

    @TableField(value = "Receive_date")
    private Date receive_date;

    @TableField(value = "Receive_Remark")
    private String receive_Remark;

    @TableField(value = "Receive_OptState")
    private String receive_OptState;

    @TableField(value = "PurchaseNo")
    private String purchaseNo;

    @TableField(value = "AgentPrice_Flag")
    private String agentPrice_Flag;

    @TableField(value = "DeptNo")
    private String deptNo;

    @TableField(value = "DiscountAmt")
    private BigDecimal discountAmt;

    @TableField(value = "OrdType")
    private String ordType;

    @TableField(value = "invoicecode")
    private String invoiceCode;

    @TableField(value = "invtype")
    private String invType;

    @TableField(value = "InvoiceNo_Jp")
    private String invoiceNoJp;

    @TableField(value = "TaxRate")
    private BigDecimal taxRate;

    @TableField(value = "NTaxDiscountAmt")
    private BigDecimal nTaxDiscountAmt;

    @TableField(value = "TaxDiscountAmt")
    private BigDecimal taxDiscountAmt;

    @TableField(value = "InsertTime")
    private Date insertTime;

    @TableField(value = "CancelDate")
    private Date cancelDate;

    @TableField(value = "Extracted")
    private String extracted;

    @TableField(value = "ExtractTime")
    private Date extractTime;

    @TableField(value = "InvoiceGroupKey")
    private String invoiceGroupKey;

    @TableField(value = "Remark")
    private String remark;

    @TableField(value = "orderno")
    private String orderno;

    @TableField(value = "itemno")
    private String itemno;

    @TableField(value = "ackdate")
    private Date ackdate;

    @TableField(value = "expdate")
    private Date expdate;

    @TableField(value = "price_enduser")
    private BigDecimal priceEnduser;

//    @TableField(value = "warehouse_code")
//    private String warehouseCode;
//
//    @TableField(value = "warehouse_type")
//    private String warehouseType;

    @TableField(value = "end_user")
    private String endUser;
}
