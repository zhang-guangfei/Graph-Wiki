package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 接单查询需要的实时查询状态
 * </p>
 *
 * @author smc
 * @since 2024-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_status_item")
public class OrderStatusItemDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户单号
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 客户单行号
     */
    @TableField("order_item")
    private Integer orderItem;

    /**
     * 拆分序号
     */
    @TableField("split_no")
    private Integer splitNo;

    /**
     * 型号拆分序号
     */
    @TableField("pco_item")
    private Integer pcoItem;

    /**
     * 数量
     */
    @TableField("qty")
    private Integer qty;

    /**
     * 库存id
     */
    @TableField("inventory_id")
    private Long inventoryId;

    /**
     * 在库数
     */
    @TableField("inventory_table")
    private String inventoryTable;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 状态描述-4501
     */
    @TableField("status_desc")
    private String statusDesc;

    /**
     * 库存地点
     */
    @TableField("status_info")
    private String statusInfo;

    /**
     * 库存类别：顾客在库，战略在库、通用在库;出库区分
     */
    @TableField("inventory_type")
    private String inventoryType;

    /**
     * 发票号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 关联单号
     */
    @TableField("associate_no")
    private String associateNo;

    /**
     * 创建时间
     */
    @TableField("cre_time")
    private String creTime;

    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private String modifyTime;

    /**
     * 修改人
     */
    @TableField("modifier")
    private String modifier;

    @TableField("qty_out")
    private Integer qtyOut;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("qty_in")
    private Integer qtyIn;


}
