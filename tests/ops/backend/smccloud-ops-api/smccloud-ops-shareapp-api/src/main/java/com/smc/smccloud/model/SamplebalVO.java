package com.smc.smccloud.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SamplebalVO {
    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 接单号
     */
    private String rorderNo; // orderNo

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 子项型号
     */
    private String subModelNo;

    /**
     * 子项数量
     */
    private Integer subQty;

    /**
     * 结转部门
     */
    private String deptDesc; //部门名称

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 处理日期
     */
    private Date optDate;

    /**
     * 样品申请方式
     */
    private String appType;

    /**
     * 结转方式
     */
    private String balType;

    /**
     * 拆分标识
     */
    private String prodFlag;

    /**
     * 工厂产品标识
     */
    private String prodCode;

    /**
     * 处理状态
     */
    private String optCode;

    /**
     * E分类码
     */
    private String eCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 营业所
     */
    private String deptNo;

    /**
     * 出库日期
     */
    private Date expDate;

    /**
     * 中文名称
     */
    private String modelinchn;

    /**
     * 入库日期
     */
    private Date inDate;

    /**
     * 订单类别
     */
    private String ordType;

    /**
     * 申请号
     */
    private String applyCode; // applyNo

    private Date createtime;

    private Date optTime;

    private Long id;

    private String userName;

    private BigDecimal priceApply;

    private String warehouseCode;
}
