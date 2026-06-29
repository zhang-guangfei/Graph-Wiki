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
 * @since 2024-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_po_invoice_price_detail_log_from_supplier")
public class OpsPoInvoicePriceDetailLogFromSupplierDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原产国
     */
    @TableField("country_origin")
    private String countryOrigin;

    /**
     * 发给供应商的订单号
     */
    @TableField("pono")
    private String pono;

    /**
     * 发给供应商的订单行号
     */
    @TableField("line_item")
    private Integer lineItem;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 海关品名
     */
    @TableField("description_customs")
    private String descriptionCustoms;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 单价
     */
    @TableField("unit_price")
    private BigDecimal unitPrice;

    /**
     * 人民币金额
     */
    @TableField("rmb_money")
    private BigDecimal rmbMoney;

    /**
     * 净重
     */
    @TableField("weight")
    private BigDecimal weight;

    /**
     * 运保费
     */
    @TableField("traffic_insurance")
    private BigDecimal trafficInsurance;

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
     * 消费税
     */
    @TableField("excise_tax")
    private BigDecimal exciseTax;

    /**
     * 其他税
     */
    @TableField("other_tax")
    private BigDecimal otherTax;

    /**
     * 无商业价值
     */
    @TableField("non_commercial")
    private String nonCommercial;

    /**
     * 供应商系统提供数据时间
     */
    @TableField("supplier_system_exec_time")
    private Date supplierSystemExecTime;




    @TableField("update_user")
    private String updateUser;

    @TableField("create_time")
    private Date createTime;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("create_user")
    private String createUser;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("del_flag")
    private Integer delFlag;



    @TableField("pid")
    private Long pid;

    @TableField("update_time")
    private Date updateTime;

    @TableField("unit_money")
    private BigDecimal unitMoney;


    @TableField("from_id")
    private Long fromId;
    @TableField("imp_order_no")
    private String impOrderNo;

    @TableField("invoice_original")
    private String invoiceOriginal;


}
