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

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("OPS_ImpdataForManu")
public class OpsImpdataformanuDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 发票号
     */
    @TableField("InvoiceNo")
    private String InvoiceNo;

    /**
     * 箱号
     */
    @TableField("CaseNo")
    private String CaseNo;

    /**
     * 订单号
     */
    @TableField("OrderNo")
    private String OrderNo;

    /**
     * 客户代码
     */
    @TableField("CustomerNo")
    private String CustomerNo;

    /**
     * 型号
     */
    @TableField("ModelNo")
    private String ModelNo;

    /**
     * 数量
     */
    @TableField("Quantity")
    private Integer Quantity;

    /**
     * 运输方式
     */
    @TableField("TransType")
    private String TransType;

    /**
     * 营业物流出库日期
     */
    @TableField("ImpDate")
    private Date ImpDate;

    /**
     * 状态,0:数据发布，制造不可更新，故为默认值
     */
    @TableField("OptCode")
    private String OptCode;

    /**
     * 说明
     */
    @TableField("ExpDesc")
    private String ExpDesc;

    /**
     * BarCode
     */
    @TableField("BarCode")
    private String BarCode;

    /**
     * 操作者
     */
    @TableField("Username")
    private String Username;

    /**
     * 处理时间
     */
    @TableField("Opttime")
    private Date Opttime;

    /**
     * 操作记录
     */
    @TableField("OptRecord")
    private String OptRecord;

    /**
     * 拍号
     */
    @TableField("PalletNo")
    private String PalletNo;

    /**
     * 单价
     */
    @TableField("Price")
    private BigDecimal Price;

    /**
     * 外部单号
     */
    @TableField("ext_orderno")
    private String extOrderno;

    /**
     * 外部项号
     */
    @TableField("ext_order_item")
    private String extOrderItem;

    /**
     * 价值（未含税）
     */
    @TableField("Amount")
    private Double Amount;

    /**
     * 价值（含税）
     */
    @TableField("TaxAmount")
    private BigDecimal TaxAmount;

    /**
     * 税率
     */
    @TableField("TaxRate")
    private Double TaxRate;

    @TableField("taxPrice")
    private BigDecimal taxPrice;

    @TableField("FromId")
    private Long FromId;


}
