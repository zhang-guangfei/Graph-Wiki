package com.smc.smccloud.model.purchase;

import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: OpsPurchaseOrderVO
 * @date 2022/07/03 15:58
 */
@Data
public class OpsPurchaseOrderVO {

    /**
     * 采购单号
     */
    private String orderNo;

    /**
     * 合并采购标识，0：非合并，1：合并 默认为0
     */
    private Boolean mergeflag;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 标准价
     */
    private Double stdPrice;

    /**
     * 运输方式
     */
    private String transType;

    /**
     * 采购日期
     */
    private Date purchaseDate;

    /**
     * 期望货期
     */
    private String hopeDeliveryDate;

    /**
     * 供应商代码
     */
    private String supplierId;

    /**
     * 状态
     */
    private String stateCode;

    /**
     * 订单类别
     */
    private String ordType;

    /**
     * 阀汇流板标识
     */
    private String specMark;

    /**
     * 收货库房
     */
    private String receiveWarehouseId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 指定制造出荷日
     */
    private String hopeExportDate;

    /**
     * 交货期问询号
     */
    private String inqNo;

    /**
     * SHIKOMI批复号
     */
    private String shikomiAnswerNo;

    /**
     * 采购操作者
     */
    private String operatorId;

    /**
     * 部门代码
     */
    private String deptNo;

    /**
     * 到货数量
     */
    private Integer qtyReceive;

    /**
     * 到货日期
     */
    private String finishDate;

    /**
     * 国别代码
     */
    private String smcCode;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 海关申报号
     */
    private String hsCode;

    /**
     * 绿标标识
     */
    private String greenCode;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 营业情报号
     */
    private String salesInfoNo;

    /**
     * 供应商型号
     */
    private String supplierPartNo;

    /**
     * 进口FOB价（原币种）
     */
    private Double importFobPriceOriginal;

    /**
     * 币种代码
     */
    private String importCurrencyId;

    /**
     * 2022-1-22 新增，业务人员备注信息
     */
    private String serverremark;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 客户订单号
     */
    private String corderNO;

    private Integer productType;

    private String purchaseType;

    private Integer splitItemNo;

    private Long id;

    private Integer itemNo;

    private Long inventoryPropertyId;

}
