package com.smc.smccloud.model.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author edp02 @Date 2021/12/25 14:28
 */
@Data
public class OpsPoInvoiceVO {

    private Long id;

    /**
     * 发票id,来源imp_invoice_master
     */
    private Long invoiceId;

    /**
     * 供应商原发票号
     */
    private String invoiceNo;

    /**
     * 状态代码，0:待收货；1：已签收；2：成本已推送
     */
    private Integer status;

    /**
     * 发票日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date invoiceDate;

    /**
     * 货币代码
     */
    private String currencyCode;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 原币金额(不含税)
     */
    private BigDecimal amount;

    /**
     * 原金额调整金额
     */
    private BigDecimal amountadjust;

    /**
     * 人民币金额
     */
    private BigDecimal amountRmb;

    /**
     * 通关时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd、")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date customsDate;

    /**
     * 出港日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date shipDate;

    /**
     * 报关单号
     */
    private String declarationNo;

    /**
     * 毛重
     */
    private Double grossWeight;

    /**
     * 净重
     */
    private Double weight;

    /**
     * 关税
     */
    private BigDecimal customsFee;

    /**
     * 增值税
     */
    private BigDecimal vatFee;

    /**
     * 运费
     */
    private BigDecimal transFee;

    /**
     * 到货仓库
     */
    private String arrivedWarehouseCode;

    /**
     * 签收仓库代码
     */
    private String receiveWarehouseCode;

    /**
     * 箱数
     */
    private Integer boxQty;

    /**
     * 订单数
     */
    private Integer orderQty;

    /**
     * 签收时间
     */
    private Date receiveTime;

    /**
     * 成本结算日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date costTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    private String createUser;

    private BigDecimal otherFee;

    private Integer totalQty;

    private String supplierCode;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date impDate;

    private String updateUser;

    private Boolean isFinishCost;

    private BigDecimal amounttotal;

    private BigDecimal excisetax;

    private Integer payDay;

    private Integer invoiceType;
    // bug 14230,成本入库前端需要-新增交易成交方式字段显示
    private String bargainType;

}
