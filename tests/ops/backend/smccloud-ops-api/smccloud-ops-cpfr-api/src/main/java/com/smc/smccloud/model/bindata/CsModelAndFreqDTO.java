package com.smc.smccloud.model.bindata;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/4/3 16:54
 * @Descripton TODO
 */

@Data
public class CsModelAndFreqDTO {

    private Integer id;

    /**
     * BIN库存类型: 1-BIN; 2-GSS; 3-非BIN; 4-客户BIN;
     */
    private Integer stockType; //库存类型(BIN,非BIN,GSS)

    private String warehouseCode; //库房代码

    private String modelNo;  //型号

    private String customerNo;  //客户代码

    private Long propertyID;  //库存属性ID

    private Integer qtyBin;  //BIN数量

    private Integer binCell;  //BIN数

    //    @TableField(value = "ClassCode")
//    private String classCode;  //
    private String binType; //bin类型 BIN1,BIN2,BIN3

    private String caseType;  //箱型

    private String prodType;  //产品类别

    private Date createTime;  //登录日期

    private Date updateTime;  //更新日期

    private String positionNo;  //位置代码

    private String meshType;  //网筐类型

    private Integer inCaseQty;  //箱入数

    private String adjustable;  //是否可调

    private Integer delFlag;

    private Integer lastdelFlag;

    //安全在可
    private Integer safeQty;

    //频率
    private Integer freq;

    //月用量
    private Integer mean;

    private String setLevel;  //设置等级

    private String newLevel;  //新等级

     //新频率
    private Integer newFreq;

    //新用量
    private Integer newMean;

    private String poType;  //采购分类

    private String supplierCode;  //供应商

    private String orderType;  //订单类别

    private String prodSeri;  //公/英制区分

    private String stateRange;  //辖区

    private Integer minPackageQty;  //最小包装数量

    private Integer setFreq;  //设定频率

    private Integer directPurchase;  //直采1

    private Integer directDelivery;  //直送1

    private Integer autoRepl;  //自动周转补货

    private BigDecimal eprice;  //E价

    private String ecode;  //ECode

    private String modelSeries;  //产品资料

    private String ppl;  //ppl代码

    private String projectNo;  //项目号

    private String inventoryTypeCode;  //库存类型代码

    private String groupCustomerNo;  //客户群号

    private String origin;  //原产地

    private Date loginDate; //首次登记时间

    private Integer replQty;  //待补货数量

    private String SetSupplierCode; //自己设置供应商


    // --------- model_exp_freq -----------------

    private String stockCode;

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

    private String classCode;

    private String modelClass;

//    @TableField("productType")
//    private String productType;

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
    private BigDecimal ePrice;

    /**
     * ECode
     */
    private String eCode;

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
    private Integer SetMean;

    /**
     * 营业所代码
     */
    private String maxCustomerDeptOf36;

    private String maxCustomerDeptOf24;

    private String maxCustomerDeptOf12;

    private String maxCustomerDeptOf8;

}
