package com.smc.smccloud.model.salesinvoice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SalesInvoice")
public class SalesInvoiceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出票主体
     */
    @TableField("TradeCompanyId")
    private String TradeCompanyId;

    /**
     * 接单号
     */
    @TableField("ROrderNo")
    private String ROrderNo;

    /**
     * 型号
     */
    @TableField("ModelNo")
    private String ModelNo;

    /**
     * 数量
     */
    @TableField("Quantity")
    private Integer Quantity;

    /**
     * 单价
     */
    @TableField("Price")
    private BigDecimal Price;

    /**
     * 客户代码
     */
    @TableField("CustomerNo")
    private String CustomerNo;

    /**
     * 用户代码
     */
    @TableField("UserNo")
    private String UserNo;

    /**
     * 货齐出货标识
     */
    @TableField("DlvEntire")
    private String DlvEntire;

    /**
     * 金额
     */
    @TableField("Amount")
    private BigDecimal Amount;

    /**
     * 税额
     */
    @TableField("TaxAmount")
    private BigDecimal TaxAmount;

    /**
     * 不含税金额
     */
    @TableField("NTaxAmount")
    private BigDecimal NTaxAmount;

    /**
     * 是否开票（0：生成，1：确认；2：转出
     */
    @TableField("InvFlag")
    private String InvFlag;

    /**
     * 处理日期
     */
    @TableField("OptDate")
    private Date OptDate;

    /**
     * 处理状态1
     */
    @TableField("OptCode")
    private String OptCode;

    /**
     * 合开发票标识
     */
    @TableField("ClassFlag")
    private String ClassFlag;

    /**
     * 发票号
     */
    @TableField("InvoiceNo")
    private String InvoiceNo;

    /**
     * 开票日期
     */
    @TableField("InvDate")
    private Date InvDate;

    /**
     * 处理状态2
     */
    @TableField("OptState")
    private String OptState;

    /**
     * 库房代码
     */
    @TableField("StockNo")
    private String StockNo;

    /**
     * 发票作废标识
     */
    @TableField("InvFlag1")
    private String InvFlag1;

    /**
     * 工厂拆分标识
     */
    @TableField("ProdFlag")
    private String ProdFlag;

    /**
     * 票据号
     */
    @TableField("BillNo")
    private String BillNo;

    /**
     * 操作担当
     */
    @TableField("Username")
    private String Username;

    /**
     * 操作日期
     */
    @TableField("OptTime")
    private Date OptTime;

    /**
     * 收货担当
     */
    @TableField("Receive_Emp")
    private String receiveEmp;

    /**
     * 收货客户
     */
    @TableField("Receive_Cust")
    private String receiveCust;

    /**
     * 收货日期
     */
    @TableField("Receive_date")
    private Date receiveDate;

    /**
     * 收货备注
     */
    @TableField("Receive_Remark")
    private String receiveRemark;

    /**
     * 收货状态
     */
    @TableField("Receive_OptState")
    private String receiveOptstate;

    /**
     * 客户采购单号
     */
    @TableField("PurchaseNo")
    private String PurchaseNo;

    /**
     * 代理客户是否运算国返利单价
     */
    @TableField("AgentPrice_Flag")
    private String agentpriceFlag;

    /**
     * 营业所代码
     */
    @TableField("DeptNo")
    private String DeptNo;

    /**
     * 折扣金额
     */
    @TableField("DiscountAmt")
    private BigDecimal DiscountAmt;

    /**
     * 订单类别
     */
    @TableField("OrdType")
    private String OrdType;

    /**
     * 发票代码
     */
    @TableField("invoicecode")
    private String invoicecode;

    /**
     * 发票类别
     */
    @TableField("invtype")
    private String invtype;

    /**
     * 日本发票号
     */
    @TableField("InvoiceNo_Jp")
    private String invoicenoJp;

    @TableField("TaxRate")
    private BigDecimal TaxRate;

    @TableField("ExtractTime")
    private Date ExtractTime;

    @TableField("InsertTime")
    private Date InsertTime;

    @TableField("NTaxDiscountAmt")
    private BigDecimal NTaxDiscountAmt;

    @TableField("CancelDate")
    private Date CancelDate;

    @TableField("InvoiceGroupKey")
    private String InvoiceGroupKey;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("TaxDiscountAmt")
    private BigDecimal TaxDiscountAmt;

    @TableField("Remark")
    private String Remark;

    @TableField("Extracted")
    private String Extracted;


}
