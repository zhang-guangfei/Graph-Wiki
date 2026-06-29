package com.smc.smccloud.model.invoice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author edp02 @Date 2022/7/12 9:48
 */
@Data
@TableName("SalesImp")
public class SalesImpDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField("Impdate")
    private Date impdate;

    @TableField("OwnerCompanyId")
    private String ownerCompanyId;

    @TableField("InvoiceNo")
    private String invoiceNo;

    @TableField("InvoiceId")
    private Long InvoiceId;

    @TableField("ECode")
    private String ecode;

    @TableField("OrderNo")
    private String orderNo;

    @TableField("ModelNo")
    private String modelNo;

    @TableField("Quantity")
    private Integer quantity;

    @TableField("Price")
    private BigDecimal price;

    @TableField("Amount")
    private BigDecimal amount;

    @TableField("OptCode")
    private String optCode;

    @TableField("OptDate")
    private Date optDate;

    @TableField("priceJP")
    private BigDecimal priceJP;

    @TableField("AmountJP")
    private BigDecimal amountJP;

    @TableField("StockCode")
    private String stockCode;

    @TableField("ProcessCode")
    private String processCode;

    @TableField("DataSource")
    private String dataSource;

    @TableField("SupplierId")
    public String supplierId;

    @TableField("customsFee")
    public BigDecimal customsFee;

    @TableField("otherFee")
    public BigDecimal otherFee;

    @TableField("transFee")
    public BigDecimal transFee;

    @TableField("excise_tax")
    public BigDecimal exciseTax;

    @TableField("exchange_rate")
    public BigDecimal exchangeRate;

    @TableField("OriginalInvoiceNo")
    public String originalInvoiceNo;
    // bug 14230,成本Salesimp表需要新增交易成交方式字段
    @TableField("bargain_type")
    private String bargainType;

}
