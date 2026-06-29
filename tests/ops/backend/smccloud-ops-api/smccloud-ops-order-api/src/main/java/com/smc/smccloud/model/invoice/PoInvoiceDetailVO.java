package com.smc.smccloud.model.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author edp02 @Date 2021/12/22 16:41
 */
@Data
public class PoInvoiceDetailVO {

    private Long id;

    /**
     * 入库日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date impDate;

    /**
     * imp_invoice_master.id
     */
    private Long invoiceId;

    /**
     * 供应商代码
     */
    private String supplierCode;

    /**
     * 供应商的原发票号
     */
    private String invoiceNo;

    /**
     * 订单号
     */
    private String orderNo;


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
     * 原币金额
     */
    private BigDecimal amount;

    /**
     * 调整金额
     */
    private BigDecimal amountAdjust;

    /**
     * 人民币单价(未税)
     */
    private BigDecimal priceRmb;

    /**
     * 人民币金额(未税)
     */
    private BigDecimal amountRmb;

    /**
     * 型号英文名称
     */
    private String productNameEn;

    /***
     * 重量
     */
    private Double weight;

    /**
     * 原产地
     */
    private String prodCountry;

    /**
     * 入库类型,0-正常, 1-不入库
     */
    private Integer impType;

    /**
     * 关税费用
     */
    private BigDecimal customsFee;

    /**
     * 增值税费用
     */
    private BigDecimal vatFee;

    /**
     * 运输费用
     */
    private BigDecimal transFee;

    /**
     * 其他费用
     */
    private BigDecimal otherFee;

    /**
     * 总单价-包括全部费用后(不含税)
     */
    private BigDecimal priceTotal;

    /**
     * 总金额-包括全部费用后(不含税)
     */
    private BigDecimal amountTotal;
    /**
     * 消费税
     */
    public BigDecimal exciseTax;
    private String remark;

    private String createUser;

    private String updateUser;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    /**
     * 订单类别
     */
    private Integer orderType;

    /**
     * 货币
     */
    private String currency;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 无商业价值
     */
    public String nonCommercial;

    public String overseaInvoiceNo;

    public String ecode;

}
