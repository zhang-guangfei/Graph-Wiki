package com.smc.ops.delivery.model.poImps;

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
 * @since 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_po_invoice_price_log_from_supplier")
public class OpsPoInvoicePriceLogFromSupplierDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("shipment")
    private String shipment;

    @TableField("create_time")
    private Date createTime;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("update_user")
    private String updateUser;

    @TableField("supplier_system_exec_time")
    private Date supplierSystemExecTime;

    @TableField("source_type")
    private String sourceType;

    @TableField("customs_fee")
    private BigDecimal customsFee;

    @TableField("invoice_date")
    private String invoiceDate;

    @TableField("gw_invoice_no")
    private String gwInvoiceNo;

    @TableField("smccode")
    private String smccode;

    @TableField("gw_status_code")
    private String gwStatusCode;



    @TableField("source_id")
    private Long sourceId;

    @TableField("payment_term")
    private String paymentTerm;

    @TableField("box_qty")
    private Integer boxQty;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("amount_rmb")
    private BigDecimal amountRmb;

    @TableField("vat_fee")
    private BigDecimal vatFee;

    @TableField("excise_tax")
    private BigDecimal exciseTax;

    @TableField("pid")
    private Long pid;

    @TableField("trans_fee")
    private BigDecimal transFee;

    @TableField("trade_method")
    private String tradeMethod;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("currency")
    private String currency;

    @TableField("create_user")
    private String createUser;

    @TableField("plant_mark")
    private String plantMark;

    @TableField("gross_weight")
    private BigDecimal grossWeight;

    @TableField("update_time")
    private Date updateTime;

    @TableField("exchange_rate")
    private BigDecimal exchangeRate;

    @TableField("weight")
    private BigDecimal weight;

    @TableField("total_qty")
    private Integer totalQty;

    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("import_customer")
    private String importCustomer;

    @TableField("trans_type")
    private String transType;

    @TableField("bargain_type")
    private String bargainType; //新加贸易方式

}
