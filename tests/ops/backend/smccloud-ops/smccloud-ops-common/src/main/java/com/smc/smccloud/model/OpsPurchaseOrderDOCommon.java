package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author lyc
 * @Date 2023/10/20 11:38
 * @Descripton TODO
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_purchaseOrder")
public class OpsPurchaseOrderDOCommon {
    private static final long serialVersionUID = 1L;

    /**
     * 采购单号
     */
    @TableField("orderNo")
    private String orderNo;

    /**
     * 合并采购标识，0：非合并，1：合并 默认为0
     */
    @TableField("mergeflag")
    private Boolean mergeflag;

    /**
     * 型号
     */
    @TableField("modelNo")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 标准价
     */
    @TableField("stdPrice")
    private Double stdPrice;

    /**
     * 运输方式
     */
    @TableField("transType")
    private String transType;

    /**
     * 采购日期
     */
    @TableField("purchaseDate")
    private String purchaseDate;

    /**
     * 期望货期
     */
    @TableField("hopeDeliveryDate")
    private String hopeDeliveryDate;

    /**
     * 供应商代码
     */
    @TableField("supplierId")
    private String supplierId;

    /**
     * 状态
     */
    @TableField("stateCode")
    private String stateCode;

    /**
     * 订单类别
     */
    @TableField("ordType")
    private String ordType;

    /**
     * 阀汇流板标识
     */
    @TableField("specMark")
    private String specMark;

    /**
     * 收货库房
     */
    @TableField("receiveWarehouseId")
    private String receiveWarehouseId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 指定制造出荷日
     */
    @TableField("hopeExportDate")
    private String hopeExportDate;

    /**
     * 交货期问询号
     */
    @TableField("inqNo")
    private String inqNo;

    /**
     * SHIKOMI批复号
     */
    @TableField("shikomiAnswerNo")
    private String shikomiAnswerNo;

    /**
     * 采购操作者
     */
    @TableField("operatorId")
    private String operatorId;

    /**
     * 部门代码
     */
    @TableField("deptNo")
    private String deptNo;

    /**
     * 到货数量
     */
    @TableField("qtyReceive")
    private Integer qtyReceive;

    /**
     * 到货日期
     */
    @TableField("finishDate")
    private String finishDate;

    /**
     * 国别代码
     */
    @TableField("smcCode")
    private String smcCode;

    /**
     * 发票号
     */
    @TableField("invoiceNo")
    private String invoiceNo;

    /**
     * 海关申报号
     */
    @TableField("hsCode")
    private String hsCode;

    /**
     * 绿标标识
     */
    @TableField("greenCode")
    private String greenCode;

    /**
     * 客户代码
     */
    @TableField("customerNo")
    private String customerNo;

    /**
     * 用户代码
     */
    @TableField("userNo")
    private String userNo;

    /**
     * 营业情报号
     */
    @TableField("salesInfoNo")
    private String salesInfoNo;

    /**
     * 供应商型号
     */
    @TableField("supplierPartNo")
    private String supplierPartNo;

    /**
     * 进口FOB价（原币种）
     */
    @TableField("importFobPriceOriginal")
    private Double importFobPriceOriginal;

    /**
     * 币种代码
     */
    @TableField("importCurrencyId")
    private String importCurrencyId;

    /**
     * 2022-1-22 新增，业务人员备注信息
     */
    @TableField("serverremark")
    private String serverremark;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private String updateTime;

    /**
     * 客户订单号
     */
    @TableField("corderNO")
    private String corderNO;

    @TableField("productType")
    private Integer productType;

    @TableField("purchaseType")
    private String purchaseType;

    @TableField("splitItemNo")
    private Integer splitItemNo;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("itemNo")
    private Integer itemNo;

    @TableField("inventoryPropertyId")
    private Long inventoryPropertyId;
}
