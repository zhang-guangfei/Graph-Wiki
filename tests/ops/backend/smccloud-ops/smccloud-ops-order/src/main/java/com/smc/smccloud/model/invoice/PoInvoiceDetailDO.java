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
 * @author B28029
 * @since 2021-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_po_invoice_detail")
public class PoInvoiceDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 入库日期
     */
    @TableField("imp_date")
    private Date impDate;

    /**
     * imp_invoice_master.id
     */
    @TableField("invoice_id")
    private Long invoiceId;

    /**
     * 供应商代码
     */
    @TableField("supplier_code")
    private String supplierCode;

    /**
     * 供应商的原发票号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;


    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 单价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 原币金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 人民币单价(未税)
     */
    @TableField("price_rmb")
    private BigDecimal priceRmb;

    /**
     * 调整金额
     */
    @TableField("amount_adjust")
    private BigDecimal amountAdjust;

    /**
     * 人民币金额(未税)
     */
    @TableField("amount_rmb")
    private BigDecimal amountRmb;

    /**
     * 型号名称
     */
    @TableField("product_name")
    private String productName;

    /***
     * 重量
     */
    @TableField("weight")
    private Double weight;

    /**
     * 原产地
     */
    @TableField("prod_country")
    private String prodCountry;

    /**
     * 入库类型,0-正常, 1-不入库
     */
    @TableField("imp_type")
    private Integer impType;

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
     * 运输费用
     */
    @TableField("trans_fee")
    private BigDecimal transFee;

    /**
     * 其他费用
     */
    @TableField("other_fee")
    private BigDecimal otherFee;

    /**
     * 总单价-包括全部费用后(不含税)
     */
    @TableField("price_total")
    private BigDecimal priceTotal;

    /**
     * 总金额-包括全部费用后(不含税)
     */
    @TableField("amount_total")
    private BigDecimal amountTotal;

    @TableField("remark")
    private String remark;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 订单类别
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 货币
     */
    @TableField("currency")
    private String currency;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 无商业价值
     */
    @TableField("nonCommercial")
    public String nonCommercial;
    /**
     * 消费税
     */
    @TableField("excise_tax")
    public BigDecimal exciseTax;

    @TableField("oversea_invoice_no")
    public String overseaInvoiceNo;

    @TableField("ecode")
    public String ecode;


    @TableField("rorder_no")
    private String rorderNo;

    @TableField("item_no")
    private Integer itemNo;

    @TableField("split_item_no")
    private Integer SplitItemNo;
}
