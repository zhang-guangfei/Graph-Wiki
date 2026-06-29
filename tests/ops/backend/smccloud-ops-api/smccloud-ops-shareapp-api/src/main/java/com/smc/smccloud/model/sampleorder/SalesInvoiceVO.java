package com.smc.smccloud.model.sampleorder;

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
public class SalesInvoiceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出票主体
     */
    private String TradeCompanyId;

    /**
     * 接单号
     */
    private String ROrderNo;

    /**
     * 型号
     */
    private String ModelNo;

    /**
     * 数量
     */
    private Integer Quantity;

    /**
     * 单价
     */
    private BigDecimal Price;

    /**
     * 客户代码
     */
    private String CustomerNo;

    /**
     * 用户代码
     */
    private String UserNo;

    /**
     * 货齐出货标识
     */
    private String DlvEntire;

    /**
     * 金额
     */
    private BigDecimal Amount;

    /**
     * 税额
     */
    private BigDecimal TaxAmount;

    /**
     * 不含税金额
     */
    private BigDecimal NTaxAmount;

    /**
     * 是否开票（0：生成，1：确认；2：转出
     */
    private String InvFlag;

    /**
     * 处理日期
     */
    private Date OptDate;

    /**
     * 处理状态1
     */
    private String OptCode;

    /**
     * 合开发票标识
     */
    private String ClassFlag;

    /**
     * 发票号
     */
    private String InvoiceNo;

    /**
     * 开票日期
     */
    private Date InvDate;

    /**
     * 处理状态2
     */
    private String OptState;

    /**
     * 库房代码
     */
    private String StockNo;

    /**
     * 发票作废标识
     */
    private String InvFlag1;

    /**
     * 工厂拆分标识
     */
    private String ProdFlag;

    /**
     * 票据号
     */
    private String BillNo;

    /**
     * 操作担当
     */
    private String Username;

    /**
     * 操作日期
     */
    private Date OptTime;

    /**
     * 收货担当
     */
    private String receiveEmp;

    /**
     * 收货客户
     */
    private String receiveCust;

    /**
     * 收货日期
     */
    private Date receiveDate;

    /**
     * 收货备注
     */
    private String receiveRemark;

    /**
     * 收货状态
     */
    private String receiveOptstate;

    /**
     * 客户采购单号
     */
    private String PurchaseNo;

    /**
     * 代理客户是否运算国返利单价
     */
    private String agentpriceFlag;

    /**
     * 营业所代码
     */
    private String DeptNo;

    /**
     * 折扣金额
     */
    private BigDecimal DiscountAmt;

    /**
     * 订单类别
     */
    private String OrdType;

    /**
     * 发票代码
     */
    private String invoicecode;

    /**
     * 发票类别
     */
    private String invtype;

    /**
     * 日本发票号
     */
    private String invoicenoJp;

    private Double TaxRate;

    private Date ExtractTime;

    private Date InsertTime;

    private BigDecimal NTaxDiscountAmt;

    private Date CancelDate;

    private String InvoiceGroupKey;

    private Long id;

    private BigDecimal TaxDiscountAmt;

    private String Remark;

    private String Extracted;


}
