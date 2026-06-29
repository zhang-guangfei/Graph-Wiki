package com.smc.smccloud.model.bindata;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BindataExcelVO {

    /**
     * 库存类别
     */
    @ExcelProperty(value = "库存类别", index = 0)
    private Integer stockType;

    /**
     * 库房代码
     */
    @ExcelProperty(value = "库房代码", index = 1)
    private String warehouseCode;

    /**
     * 型号
     */
    @ExcelProperty(value = "型号", index = 2)
    private String modelNo;

    /**
     * 客户代码
     */
    @ExcelProperty(value = "客户代码", index = 3)
    private String customerNo;

    /**
     * 库存属性ID
     */
    @ExcelProperty(value = "库存属性ID", index = 4)
    private Long propertyID;

    /**
     * 库存类型代码
     */
    @ExcelProperty(value = "库存类型代码", index = 5)
    private String inventoryTypeCode;

    /**
     * 客户群号
     */
    @ExcelProperty(value = "客户群号", index = 6)
    private String groupCustomerNo;

    /**
     * ppl
     */
    @ExcelProperty(value = "ppl", index = 7)
    private String ppl;

    /**
     * 项目号
     */
    @ExcelProperty(value = "项目号", index = 8)
    private String projectNo;

    /**
     * Bin数量
     */
    @ExcelProperty(value = "Bin数量", index = 9)
    private Integer qtyBin;

    /**
     * Bin数
     */
    @ExcelProperty(value = "Bin数", index = 10)
    private Integer binCell;

    /**
     * 箱型
     */
    @ExcelProperty(value = "箱型", index = 11)
    private String caseType;

    /**
     * 产品类别
     */
    @ExcelProperty(value = "产品类别", index = 12)
    private String prodType;

    /**
     * 网框类别
     */
    @ExcelProperty(value = "网框类别", index = 13)
    private String meshType;

    /**
     * 箱入数
     */
    @ExcelProperty(value = "箱入数", index = 14)
    private Integer inCaseQty;

    /**
     * 暂停补库
     */
    @ExcelProperty(value = "暂停补库", index = 15)
    private String adjustable;

    /**
     * 安全在库
     */
    @ExcelProperty(value = "安全在库", index = 16)
    private Integer safeQty;

    /**
     * 8频率
     */
    @ExcelProperty(value = "8频率", index = 17)
    private Integer freq;

    /**
     * 8平均
     */
    @ExcelProperty(value = "8平均", index = 18)
    private Integer mean;

    /**
     * 等级
     */
    @ExcelProperty(value = "等级", index = 19)
    private String setLevel;

    /**
     * 采购分类
     */
    @ExcelProperty(value = "采购分类", index = 20)
    private String poType;

    /**
     * 订单类别
     */
    @ExcelProperty(value = "订单类别", index = 21)
    private String orderType;

    /**
     * 公/英制区分
     */
    @ExcelProperty(value = "公/英制区分", index = 22)
    private String prodSeri;

    /**
     * 辖区
     */
    @ExcelProperty(value = "辖区", index = 23)
    private String stateRange;

    /**
     * 最小包装数量
     */
    @ExcelProperty(value = "最小包装数量", index = 24)
    private Integer minPackageQty;

    /**
     * 设定平均
     */
    @ExcelProperty(value = "设定平均", index = 25)
    private Integer setFreq;

    /**
     * 直采1
     */
    @ExcelProperty(value = "直采1", index = 26)
    private Integer directPurchase;

    /**
     * 直送1
     */
    @ExcelProperty(value = "直送1", index = 27)
    private Integer directDelivery;

    /**
     * 自动周转补货
     */
    @ExcelProperty(value = "自动周转补货", index = 28)
    private Integer autoRepl;

    /**
     * 运营区分
     */
    @ExcelProperty(value = "运营区分", index = 29)
    private String binType;

    /**
     * 设置供应商
     */
    @ExcelProperty(value = "#设置供应商", index = 30)
    private String setSupplierCode;

    /**
     * 供应商
     */
    @ExcelProperty(value = "#供应商", index = 31)
    private String supplierCode;

    /**
     * 原产地
     */
    @ExcelProperty(value = "#原产地", index = 32)
    private String origin;

    /**
     * E价
     */
    @ExcelProperty(value = "#E价", index = 33)
    private String eprice;

    /**
     * ecode
     */
    @ExcelProperty(value = "#ecode", index = 34)
    private String ecode;

    /**
     * 产品系列
     */
    @ExcelProperty(value = "#产品系列", index = 35)
    private String modelSeries;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除", index = 36)
    private String delFlag;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间", index = 37)
    private Date updateTime;

    /**
     * 登录时间
     */
    @ExcelProperty(value = "登录时间", index = 38)
    private Date loginDate;

    /**
     * 首次登录时间
     */
    @ExcelProperty(value = "首次登录时间", index = 39)
    private Date createTime;

    /**
     * 中央仓标识
     */
    @ExcelProperty(value = "中央仓标识", index = 40)
    private Integer centreFlag;

    /**
     * 被集约方
     */
    @ExcelProperty(value = "被集约方（逗号隔开）", index = 41)
    private String client;

}
