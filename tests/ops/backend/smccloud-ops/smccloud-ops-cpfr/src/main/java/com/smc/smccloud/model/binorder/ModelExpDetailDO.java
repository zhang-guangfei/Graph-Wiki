package com.smc.smccloud.model.binorder;

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
 * @since 2021-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("model_exp_detail")
public class ModelExpDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("dept_no")
    private String deptNo;

    @TableField("model_no")
    private String modelNo;

    @TableField("customer_no")
    private String customerNo;

    @TableField("month_date")
    private Date monthDate;

    /**
     * 数量
     */
    @TableField("qty")
    private Integer qty;

    @TableField("update_time")
    private Date updateTime;

    /**
     * 订单数量
     */
    @TableField("order_number")
    private Integer orderNumber;

    /**
     * 0-非拆分型号,1-组装父型号,2-组装子型号
     */
    @TableField("ass_type")
    private Integer assType;

    @TableField("agent_no")
    private String agentNo;

    @TableField("sub_warehouse_code")
    private String subWarehouseCode;

    private Integer cstmQty;

}
