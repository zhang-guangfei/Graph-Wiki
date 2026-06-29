package com.smc.smccloud.model.invoice;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2021-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_po_invoice")
public class OpsPoInvoiceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发票唯一号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发票id,来源imp_invoice_master
     */
    @TableField("invoice_id")
    private Long invoiceId;

    /**
     * 供应商原发票号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 状态代码，0:待收货；1：已签收；2：成本已推送
     */
    @TableField("status")
    private Integer status;

    /**
     * 发票日期
     */
    @TableField("invoice_date")
    private Date invoiceDate;

    /**
     * 货币代码
     */
    @TableField("currency_code")
    private String currencyCode;

    /**
     * 汇率
     */
    @TableField("exchange_rate")
    private BigDecimal exchangeRate;

    /**
     * 原币金额(不含税)
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 原金额调整金额
     */
    @TableField("amount_adjust")
    private BigDecimal amountadjust;

    /**
     * 人民币金额
     */
    @TableField("amount_rmb")
    private BigDecimal amountRmb;

    /**
     * 通关时间
     */
    @TableField("customs_date")
    private Date customsDate;

    /**
     * 出港日期
     */
    @TableField("ship_date")
    private Date shipDate;

    /**
     * 报关单号
     */
    @TableField("declaration_no")
    private String declarationNo;

    /**
     * 毛重
     */
    @TableField("gross_weight")
    private Double grossWeight;

    /**
     * 净重
     */
    @TableField("weight")
    private Double weight;

    /**
     * 关税
     */
    @TableField("customs_fee")
    private BigDecimal customsFee;

    /**
     * 增值税
     */
    @TableField("vat_fee")
    private BigDecimal vatFee;

    /**
     * 运费
     */
    @TableField("trans_fee")
    private BigDecimal transFee;

    /**
     * 到货仓库
     */
    @TableField("arrived_warehouse_code")
    private String arrivedWarehouseCode;

    /**
     * 签收仓库代码
     */
    @TableField("receive_warehouse_code")
    private String receiveWarehouseCode;

    /**
     * 箱数
     */
    @TableField("box_qty")
    private Integer boxQty;

    /**
     * 订单数
     */
    @TableField("order_qty")
    private Integer orderQty;

    /**
     * 签收时间
     */
    @TableField("receive_time")
    private Date receiveTime;

    /**
     * 成本结算日期
     */
    @TableField("cost_time")
    private Date costTime;

    @TableField(value ="create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("other_fee")
    private BigDecimal otherFee;

    @TableField("total_qty")
    private Integer totalQty;

    @TableField("supplier_code")
    private String supplierCode;

    @TableField(value ="update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("imp_date")
    private Date impDate;

    @TableField("update_user")
    private String updateUser;

    @TableField("amount_total")
    private BigDecimal amounttotal;

    @TableField("excise_tax")
    private BigDecimal excisetax;
    @TableField("bargain_type")
    private String bargainType;
    @TableField("pay_day")
    private Integer payDay;
    @TableField("invoice_type")
    private Integer invoiceType;


}
