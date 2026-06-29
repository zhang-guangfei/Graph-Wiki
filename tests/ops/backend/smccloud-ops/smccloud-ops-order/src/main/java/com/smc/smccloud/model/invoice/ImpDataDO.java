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
@TableName("impdata")
public class ImpDataDO implements Serializable {

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
     * 客户代码
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     *
     */
    @TableField("prod_country")
    private String prodcountry;
    /**
     *
     */
    @TableField("imp_date")
    private Date impdate;

    @TableField("opt_code")
    private Integer optCode;

    @TableField("rohs")
    private String rohs;

    @TableField("imp_type")
    private Integer imptype;

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
     * 箱号
     */
    @TableField("case_no")
    private String caseNo;

    /**
     * 条码
     */
    @TableField("barcode")
    private String barcode;

    @TableField("supplier_code")
    private String supplierCode;


    /**
     * 订单类型
     */
    @TableField("order_type")
    private Integer orderType;

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

    @TableField("oversea_invoice_no")
    public String OverseaInvoiceNo;

    @TableField("poNo")
    public String poNo;

    @TableField("poItemNo")
    public Integer poItem;

    @TableField("warehouse_code")
    private String warehouseCode;

}
