package com.smc.smccloud.model.invoice;

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
public class ImpInvoiceDetailPackDTO {

    private Long id;

    /**
     * imp_invoice_master.id
     */
    private Long invoiceId;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 采购单号
     */
    private String poNo;

    /**
     * 采购项号
     */
    private Integer poItemNo;


    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单项号
     */
    private Integer itemNo;

    private Integer splitItemNo;

    private String fullOrderNo;
    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 币制
     */
    private String currency;

    /**
     * 出港日期
     */
    private Date shipDate;

    /**
     * 运输方式
     */
    private String shipMethod;

    /**
     * 箱号
     */
    private String caseNo;

    /**
     * 条码
     */
    private String barcode;

    private String enName;

    /***
     * 重量
     */
    private Double weight;

    private String supplierCode;

    private Date createTime;

    private Date updateTime;

    /**
     * 订单类型
     */
    private String orderType;

    private String remark;

    private String remarkII;

    private Integer status;

    private String productCode;

    private String roHSCode;

    /**
     * 原产国
     */
    private String origin;

    private String fromCode;

    private String shelfCode;

    private String createUser;

    private String updateUser;
    /**
     * 订单+'-'+项号
     * 如99G29PC-30
     */
    private String impOrderNo;

    private String impModelNo;


    public String OverseaInvoiceNo;

    public String expmsg;

    private String purchaseType;

}
