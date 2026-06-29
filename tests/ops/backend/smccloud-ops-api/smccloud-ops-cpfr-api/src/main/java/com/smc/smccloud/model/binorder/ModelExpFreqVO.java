package com.smc.smccloud.model.binorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author edp02 @Date 2021/10/25 11:11
 */
@Data
public class ModelExpFreqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String modelNo;

    private String stockCode;

    /**
     * 0-全公司 1-物流中心,2-分库
     */
    private Integer stockType;

    /**
     * 1-基础型号 2-订货型号
     */
    private String modelType;

    /**
     * 下单月数(36)
     */
    private Integer monthsOf36;

    /**
     * 客户数(36)
     */
    private Integer customersOf36;

    /**
     * 下单数量(36)
     */
    private Integer qtyOf36;

    /**
     * 月平均数量(36)
     */
    private Integer avgQtyOf36;

    /**
     * 最多下单客户(36)
     */
    private String maxCustomerOf36;

    /**
     * 最多客户下单数量(36)
     */
    private Integer maxCustomerQtyOf36;

    /**
     * 最多客户的比例(36)
     */
    private BigDecimal maxCustomerRateOf36;

    private Integer monthsOf24;

    private Integer customersOf24;

    private Integer qtyOf24;

    private BigDecimal avgQtyOf24;

    private String maxCustomerOf24;

    private Integer maxCustomerQtyOf24;

    private BigDecimal maxCustomerRateOf24;

    private Integer monthsOf12;

    private Integer customersOf12;

    private Integer qtyOf12;

    private BigDecimal avgQtyOf12;

    private String maxCustomerOf12;

    private Integer maxCustomerQtyOf12;

    private BigDecimal maxCustomerRateOf12;

    private Integer monthsOf8;

    private Integer customersOf8;

    private Integer qtyOf8;

    private BigDecimal avgQtyOf8;

    private String maxCustomerOf8;

    private Integer maxCustomerQtyOf8;

    private BigDecimal maxCustomerRateOf8;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    private String classCode;

    private String modelClass;

    /**
     * 订单项数(36)
     */
    private Integer ordersOf36;

    private Integer ordersOf24;

    private Integer ordersOf12;

    private Integer ordersOf8;

    private Integer m1;

    private Integer m2;

    private Integer m3;

    private Integer m4;

    private Integer m5;

    private Integer m6;

    private Integer m7;

    private Integer m8;

    private Integer m9;

    private Integer m10;

    private Integer m11;

    private Integer m12;

    private Integer m13;

    private Integer m14;

    private Integer m15;

    private Integer m16;

    private Integer m17;

    private Integer m18;

    private Integer m19;

    private Integer m20;

    private Integer m21;

    private Integer m22;

    private Integer m23;

    private Integer m24;

    private Integer m25;

    private Integer m26;

    private Integer m27;

    private Integer m28;

    private Integer m29;

    private Integer m30;

    private Integer m31;

    private Integer m32;

    private Integer m33;

    private Integer m34;

    private Integer m35;

    private Integer m36;

    /**
     * 产品类别
     */
    private String designType;

    /**
     * 最后下单月份
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date lastOrdMonth;

    /**
     * 产品系列
     */
    private String productSeries;

    /**
     * 主产地
     */
    private String mainOrigin;

    /**
     * 订单产地
     */
    private String secondOrigin;

    /**
     * E价
     */
    private BigDecimal eprice;

    /**
     * ECode
     */
    private String ecode;

    /**
     * 变动系数
     */
    private BigDecimal variation;

    /**
     * 移动平均变量1
     */
    private BigDecimal moveRate1;

    /**
     * 移动平均变量2
     */
    private BigDecimal moveRate2;

    /**
     * 移动平均变量3
     */
    private BigDecimal moveRate3;


    /**
     * 设定月均
     */
    private Integer setMean;

    /**
     * 营业所代码
     */
    private String maxCustomerDeptOf36;

    private String maxCustomerDeptOf24;

    private String maxCustomerDeptOf12;

    private String maxCustomerDeptOf8;

    private String deptNo;

    private Integer productType;

    private String setClassCode;  //上次设定级别

    private Integer setAvgQty;  //上次设定平均
}
