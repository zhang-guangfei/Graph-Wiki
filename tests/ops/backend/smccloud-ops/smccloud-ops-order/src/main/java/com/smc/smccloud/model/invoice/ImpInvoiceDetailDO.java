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
 * @author B90034
 * @since 2021-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Imp_invoice_detail")
public class ImpInvoiceDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * imp_invoice_master.id
     */
    @TableField("invoice_id")
    private Long invoiceId;

    /**
     * 发票号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 采购单号
     */
    @TableField("poNo")
    private String poNo;

    /**
     * 采购项号
     */
    @TableField("poItemNo")
    private Integer poItemNo;

    /**
     * 订单项号
     */
    @TableField("item_no")
    private Integer itemNo;

    @TableField("split_item_no")
    private Integer splitItemNo;

    @TableField("full_order_no")
    private String fullOrderNo;
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
     * 币制
     */
    @TableField("currency")
    private String currency;

    /**
     * 出港日期
     */
    @TableField("ship_date")
    private Date shipDate;

    /**
     * 运输方式
     */
    @TableField("ship_method")
    private String shipMethod;

    /**
     * 箱号
     */
    @TableField("case_no")
    private String caseNo;

    /**
     * 条码
     */
    @TableField("barcode")
    private String barcode;

    @TableField("en_name")
    private String enName;

    /***
     * 重量
     */
    @TableField("weight")
    private Double weight;

    @TableField("supplier_code")
    private String supplierCode;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 订单类型
     */
    @TableField("order_type")
    private String orderType;

    @TableField("remark")
    private String remark;

    @TableField("remarkII")
    private String remarkII;

    @TableField("status")
    private Integer status;

    @TableField("product_code")
    private String productCode;

    @TableField("roHS_code")
    private String roHSCode;

    /**
     * 原产国
     */
    @TableField("origin")
    private String origin;

    @TableField("from_code")
    private String fromCode;

    @TableField("shelf_code")
    private String shelfCode;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    /**
     * 导入的原始单号+项号
     */
    @TableField("imp_order_no")
    public String impOrderNo;

    /**
     * 关税
     */
    @TableField("customs_fee")
    public  BigDecimal customsFee;

    /**
     * 来源id
     */
    @TableField("from_id")
    public Long fromId;

    @TableField("imp_model_no")
    public String impModelNo;

    @TableField("oversea_invoice_no")
    public String overseaInvoiceNo;

    /**
     * 无商业价值
     */
    @TableField("nonCommercial")
    public String nonCommercial;


    /**
     * 运保费
     */
    @TableField("trans_fee")
    public BigDecimal transFee;

    /**
     * 消费税
     */
    @TableField("excise_tax")
    public BigDecimal exciseTax;

    /**
     * 其他费
     */
    @TableField("other_fee")
    public BigDecimal otherFee;

    @TableField("vat_fee")
    public BigDecimal vatFee;

    @TableField("sn_code")
    public String snCode;

    @TableField("amount")
    public BigDecimal amount;
}
