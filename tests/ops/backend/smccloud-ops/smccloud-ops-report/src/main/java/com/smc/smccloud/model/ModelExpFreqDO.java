package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("model_exp_freq")
public class ModelExpFreqDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @TableId(value = "ModelNo", type = IdType.AUTO)
    private String modelNo;

    @TableField("StockCode")
    private String stockCode;

    /**
     * 0-全公司 1-物流中心,2-分库
     */
    @TableField("StockType")
    private Integer stockType;

    /**
     * 1-基础型号 2-订货型号
     */
    @TableField("ModelType")
    private String modelType;

    /**
     * 下单月数(36)
     */
    @TableField("MonthsOf36")
    private Integer monthsOf36;

    /**
     * 客户数(36)
     */
    @TableField("CustomersOf36")
    private Integer customersOf36;

    /**
     * 下单数量(36)
     */
    @TableField("QtyOf36")
    private Integer qtyOf36;

    /**
     * 月平均数量(36)
     */
    @TableField("AvgQtyOf36")
    private Integer avgQtyOf36;

    /**
     * 最多下单客户(36)
     */
    @TableField("MaxCustomerOf36")
    private String maxCustomerOf36;

    /**
     * 最多客户下单数量(36)
     */
    @TableField("MaxCustomerQtyOf36")
    private Integer maxCustomerQtyOf36;

    /**
     * 最多客户的比例(36)
     */
    @TableField("MaxCustomerRateOf36")
    private BigDecimal maxCustomerRateOf36;

    @TableField("MonthsOf24")
    private Integer monthsOf24;

    @TableField("CustomersOf24")
    private Integer customersOf24;

    @TableField("QtyOf24")
    private Integer qtyOf24;

    @TableField("AvgQtyOf24")
    private BigDecimal avgQtyOf24;

    @TableField("MaxCustomerOf24")
    private String maxCustomerOf24;

    @TableField("MaxCustomerQtyOf24")
    private Integer maxCustomerQtyOf24;

    @TableField("MaxCustomerRateOf24")
    private BigDecimal maxCustomerRateOf24;

    @TableField("MonthsOf12")
    private Integer monthsOf12;

    @TableField("CustomersOf12")
    private Integer customersOf12;

    @TableField("QtyOf12")
    private Integer qtyOf12;

    @TableField("AvgQtyOf12")
    private BigDecimal avgQtyOf12;

    @TableField("MaxCustomerOf12")
    private String maxCustomerOf12;

    @TableField("MaxCustomerQtyOf12")
    private Integer maxCustomerQtyOf12;

    @TableField("MaxCustomerRateOf12")
    private BigDecimal maxCustomerRateOf12;

    @TableField("MonthsOf8")
    private Integer monthsOf8;

    @TableField("CustomersOf8")
    private Integer customersOf8;

    @TableField("QtyOf8")
    private Integer qtyOf8;

    @TableField("AvgQtyOf8")
    private BigDecimal avgQtyOf8;

    @TableField("MaxCustomerOf8")
    private String maxCustomerOf8;

    @TableField("MaxCustomerQtyOf8")
    private Integer maxCustomerQtyOf8;

    @TableField("MaxCustomerRateOf8")
    private BigDecimal maxCustomerRateOf8;

    @TableField("UpdateTime")
    private Date updateTime;

    @TableField("classCode")
    private String classCode;

    @TableField("modelClass")
    private String modelClass;

    /**
     * 订单项数(36)
     */
    @TableField("OrdersOf36")
    private Integer ordersOf36;

    @TableField("OrdersOf24")
    private Integer ordersOf24;

    @TableField("OrdersOf12")
    private Integer ordersOf12;

    @TableField("OrdersOf8")
    private Integer ordersOf8;

    @TableField("M1")
    private Integer m1;

    @TableField("M2")
    private Integer m2;

    @TableField("M3")
    private Integer m3;

    @TableField("M4")
    private Integer m4;

    @TableField("M5")
    private Integer m5;

    @TableField("M6")
    private Integer m6;

    @TableField("M7")
    private Integer m7;

    @TableField("M8")
    private Integer m8;

    @TableField("M9")
    private Integer m9;

    @TableField("M10")
    private Integer m10;

    @TableField("M11")
    private Integer m11;

    @TableField("M12")
    private Integer m12;

    @TableField("M13")
    private Integer m13;

    @TableField("M14")
    private Integer m14;

    @TableField("M15")
    private Integer m15;

    @TableField("M16")
    private Integer m16;

    @TableField("M17")
    private Integer m17;

    @TableField("M18")
    private Integer m18;

    @TableField("M19")
    private Integer m19;

    @TableField("M20")
    private Integer m20;

    @TableField("M21")
    private Integer m21;

    @TableField("M22")
    private Integer m22;

    @TableField("M23")
    private Integer m23;

    @TableField("M24")
    private Integer m24;

    @TableField("M25")
    private Integer m25;

    @TableField("M26")
    private Integer m26;

    @TableField("M27")
    private Integer m27;

    @TableField("M28")
    private Integer m28;

    @TableField("M29")
    private Integer m29;

    @TableField("M30")
    private Integer m30;

    @TableField("M31")
    private Integer m31;

    @TableField("M32")
    private Integer m32;

    @TableField("M33")
    private Integer m33;

    @TableField("M34")
    private Integer m34;

    @TableField("M35")
    private Integer m35;

    @TableField("M36")
    private Integer m36;

    /**
     * 产品类别
     */
    @TableField("DesignType")
    private String designType;

    /**
     * 最后下单月份
     */
    @TableField("LastOrdMonth")
    private Date lastOrdMonth;

    /**
     * 产品系列
     */
    @TableField("ProductSeries")
    private String productSeries;

    /**
     * 主产地
     */
    @TableField("MainOrigin")
    private String mainOrigin;

    /**
     * 订单产地
     */
    @TableField("SecondOrigin")
    private String secondOrigin;

    /**
     * E价
     */
    @TableField("EPrice")
    private BigDecimal eprice;

    /**
     * ECode
     */
    @TableField("ECode")
    private String ecode;

    /**
     * 变动系数
     */
    @TableField("Variation")
    private BigDecimal variation;

    /**
     * 移动平均变量1
     */
    @TableField("MoveRate1")
    private BigDecimal moveRate1;

    /**
     * 移动平均变量2
     */
    @TableField("MoveRate2")
    private BigDecimal moveRate2;

    /**
     * 移动平均变量3
     */
    @TableField("MoveRate3")
    private BigDecimal moveRate3;


    /**
     * 设定月均
     */
    @TableField("SetMean")
    private Integer setMean;

    /**
     * 营业所代码
     */
    @TableField("MaxCustomerDeptOf36")
    private String maxCustomerDeptOf36;

    @TableField("MaxCustomerDeptOf24")
    private String maxCustomerDeptOf24;

    @TableField("MaxCustomerDeptOf12")
    private String maxCustomerDeptOf12;

    @TableField("MaxCustomerDeptOf8")
    private String maxCustomerDeptOf8;

    /**
     * 设定月均
     */
    @TableField("DeptNo")
    private String deptNo;

    @TableField("ProductType")
    private Integer productType;

    @TableField("SetClassCode")
    private String setClassCode;  //上次设定级别

    @TableField("SetAvgQty")
    private Integer setAvgQty;  //上次设定平均


}
