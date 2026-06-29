package com.smc.smccloud.model.csstock;

import cn.hutool.db.DaoTemplate;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 月结信息
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/13 13:03
 */
@Data
@TableName("cs_balance")
public class CsModelBalanceDO implements Serializable {
    private static final long serialVersionUID = 1020930424676938408L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 代理代码
     */
    @TableField(value = "agent_no")
    private String agentNo;

    /**
     * 库房代码
     */
    @TableField(value = "warehouse_code")
    private String warehouseCode;

    /**
     * 型号
     */
    @TableField(value = "model_no")
    private String modelNo;

    /**
     * 月结月份
     */
    @TableField(value = "month_date")
    private Date monthDate;

    /**
     * 预约数
     */
    @TableField(value = "qty_opening")
    private Integer openingQty;

    /**
     * 入库数
     */
    @TableField(value = "qty_in")
    private Integer inQty;

    /**
     * 出库数
     */
    @TableField(value = "qty_out")
    private Integer outQty;

    /**
     * 退货数
     */
    @TableField(value = "qty_return")
    private Integer returnQty;

    /**
     * 月末结余数量
     */
    @TableField(value = "qty_balance")
    private Integer balanceQty;

    /**
     * 把当前的库存记录下,不用显示出来
     */
    @TableField(value = "qty_onhand")
    private Integer onhandQty;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "confirm_time")
    private Date confirmTime;

    @TableField("operator")
    private String operator;

    @TableField("qty_invoice")
    private Integer qtyInvoice;

    @TableField("qty_ship")
    private Integer qtyShip;

    @TableField("qty_balance_cost")
    private Integer qtyBalanceCost;

    @TableField("qty_opening_cost")
    private Integer qtyOpeningCost;

    @TableField("confirm_user")
    private String confirmUser;
}