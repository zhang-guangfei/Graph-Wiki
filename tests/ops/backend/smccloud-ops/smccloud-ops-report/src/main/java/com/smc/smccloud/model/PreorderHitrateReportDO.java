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
 * @since 2024-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("preorder_hitrate_report")
public class PreorderHitrateReportDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 月份
     */
    @TableField("month")
    private String month;

    /**
     * 仓库代码
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 库存类型
     */
    @TableField("inventory_type")
    private String inventoryType;

    /**
     * 客户代码
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     * bom
     */
    @TableField("BOM")
    private String bom;

    @TableField("PJ")
    private String pj;

    /**
     * 客户群
     */
    @TableField("group_customer_no")
    private String groupCustomerNo;

    /**
     * 库存id
     */
    @TableField("inventory_id")
    private Long inventoryId;

    /**
     * 本月期初数量
     */
    @TableField("initial_quantity")
    private Integer initialQuantity;

    /**
     * 本月入库数量之和
     */
    @TableField("in_qty_total")
    private Integer inQtyTotal;

    /**
     * 本月出库数量之和
     */
    @TableField("out_qty_total")
    private Integer outQtyTotal;

    /**
     * E单价
     */
    @TableField("eprice")
    private BigDecimal eprice;

    /**
     * 期初E金额
     */
    @TableField("inital_eamount")
    private BigDecimal initalEamount;

    /**
     * 本月入库E金额
     */
    @TableField("in_amount_total")
    private BigDecimal inAmountTotal;

    /**
     * 本月出库E金额
     */
    @TableField("out_amount_total")
    private BigDecimal outAmountTotal;

    /**
     * 0未命中 1命中
     */
    @TableField("hit")
    private Integer hit;

    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField("create_user")
    private String createUser;
    @TableField("update_user")
    private String updateUser;

}
