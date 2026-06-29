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
 * @since 2021-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("shoplist")
public class ShoplistDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商原发票
     */
    @TableField("invoice_no_org")
    private String invoiceNoOrg;

    /**
     * 供应商代码
     */
    @TableField("supplier_no")
    private String supplierNo;

    /**
     * 入库时间
     */
    @TableField("imp_date")
    private Date impDate;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 原单价(不含税)
     */
    @TableField("price_org")
    private BigDecimal priceOrg;

    /**
     * 原金额(不含税金额)
     */
    @TableField("amount_org")
    private BigDecimal amountOrg;

    /**
     * 货币
     */
    @TableField("currency")
    private String currency;

    /**
     * 人民币单价
     */
    @TableField("price_rmb")
    private BigDecimal priceRmb;

    /**
     * 人民币金额
     */
    @TableField("amount_rmb")
    private BigDecimal amountRmb;

    /**
     * 原产地
     */
    @TableField("prod_country")
    private String prodCountry;

    /**
     * 关税费用
     */
    @TableField("customs_fee")
    private BigDecimal customsFee;

    /**
     * 增值税费用
     */
    @TableField("vat_fee")
    private BigDecimal vatFee;

    /**
     * 入库类型,0-正常, 1-不入库,2-调库
     */
    @TableField("imp_type")
    private String impType;

    /**
     * 订单类型
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 总金额
     */
    @TableField("amount_total")
    private BigDecimal amountTotal;

    @TableField("model_no")
    private String modelNo;

    @TableField("other_fee")
    private BigDecimal otherFee;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value ="update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value ="create_time" , fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("trans_fee")
    private BigDecimal transFee;

    @TableField("update_user")
    private String updateUser;

    @TableField("create_user")
    private String createUser;

    @TableField("price_total")
    private BigDecimal priceTotal;

    @TableField("invoice_id")
    private Long invoiceId;


}
