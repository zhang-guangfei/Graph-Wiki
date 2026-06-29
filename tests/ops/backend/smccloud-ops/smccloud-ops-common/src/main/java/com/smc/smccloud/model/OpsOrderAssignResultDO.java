package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_order_assign_result")
public class OpsOrderAssignResultDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 订单itemno
     */
    @TableField("order_item")
    private Integer orderItem;

    /**
     * 分配顺序号
     */
    @TableField("seqNo")
    private Integer seqNo;

    /**
     * 型号
     */
    @TableField("modelno")
    private String modelno;

    /**
     * 分配数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * N:在库；P:生产在途； T:采购在途；CG：采购
     */
    @TableField("stock_type")
    private String stockType;

    /**
     * 在库 在途为库房代码，采购为供应商
     */
    @TableField("stock_code")
    private String stockCode;

    /**
     * 库存类别
     */
    @TableField("inventory_type_code")
    private String inventoryTypeCode;

    /**
     * 采购/在途为PO号
     */
    @TableField("associate_no")
    private String associateNo;

    /**
     * po_item号
     */
    @TableField("associate_no_item")
    private Integer associateNoItem;

    /**
     * 在库为出库库房代码；	在途为在途表的SupplierId；	采购为供应商代码；
     */
    @TableField("supplierId")
    private String supplierId;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("associate_no_split_no")
    private Integer associateNoSplitNo;

    @TableField("delflag")
    private Integer delflag;
}
