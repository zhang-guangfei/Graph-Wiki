package com.smc.smccloud.model.invoice;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2021/12/7 9:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Imp_invoice_detail_pack")
public class ImpInvoiceDetailPackDO {
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
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

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
     * 订单+'-'+项号
     * 如99G29PC-30
     */
    @TableField("imp_order_no")
    private String impOrderNo;

    @TableField("imp_model_no")
    private String impModelNo;


    @TableField("oversea_invoice_no")
    public String OverseaInvoiceNo;

    @TableField("exp_msg")
    public String expmsg;

    @TableField(exist=false)
    private String purchaseType;

    @TableField("sn_code")
    public String snCode;

    @TableField("end_user")
    public String endUser;
}
