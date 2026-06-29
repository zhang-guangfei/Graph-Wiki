package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: model_exp_detail
 * @date 2022/05/11 12:15
 */
@Data
@TableName(value = "model_exp_detail")
public class ExpModelDetailDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "warehouse_code")
    private String warehouseCode;

    @TableField(value = "dept_no")
    private String deptNo;

    @TableField(value = "model_no")
    private String modelNo;

    @TableField(value = "customer_no")
    private String customerNo;

    @TableField(value = "month_date")
    private Date monthDate;

    @TableField(value = "qty")
    private int qty;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "order_number")
    private int orderNumber;

    @TableField(value = "ass_type")
    private Integer assType;

    @TableField(value = "agent_no")
    private String agentNo;

    @TableField(value = "sub_warehouse_code")
    private String subWarehouseCode;

    @TableField(value = "bom_id")
    private Integer bomId;

    @TableField(value = "order_line")
    private Integer orderLine;

    @TableField(value = "trade_company")
    private String tradeCompany;

    @TableField(exist = false)
    private String oldModelNo;
}
