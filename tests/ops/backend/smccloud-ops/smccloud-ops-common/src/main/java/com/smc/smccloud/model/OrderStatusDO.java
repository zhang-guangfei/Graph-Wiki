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
 * @since 2025-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_status")
public class OrderStatusDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户单号
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 客户单项
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
     * 拆分类型	0：整出,	1：数量拆分,	2：型号拆分
     */
    @TableField("split_type")
    private String splitType;

    /**
     * 集约仓
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 型号
     */
    @TableField("modelno")
    private String modelno;

    /**
     * 交易出库物流指令
     */
    @TableField("wm_order_id")
    private String wmOrderId;

    /**
     * 数量
     */
    @TableField("qty")
    private Integer qty;

    /**
     * 在库数
     */
    @TableField("qty_in")
    private Integer qtyIn;

    /**
     * 已发数
     */
    @TableField("qty_out")
    private Integer qtyOut;

    /**
     * 指定物流发货期
     */
    @TableField("wl_date")
    private String wlDate;

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

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

}
