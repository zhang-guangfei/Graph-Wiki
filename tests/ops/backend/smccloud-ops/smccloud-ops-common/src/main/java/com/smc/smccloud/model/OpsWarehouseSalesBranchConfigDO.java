package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wuweidong
 * @create 2023/6/8 10:14
 * @description
 */
@Data
@TableName("ops_warehouse_salesbranch_config")
public class OpsWarehouseSalesBranchConfigDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 营业所代码/代理店
     */
    @TableField("sales_branch_id")
    private String salesBranchId;

    /**
     * 仓库编码
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 优先级(数字越小优先级越大)
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 交货期
     */
    @TableField("delivery_day")
    private Integer deliveryDay;

    /**
     * 删除标识：0未删除,1删除
     */
    @TableField("delflag")
    private Integer delFlag;

}
