package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author C18117
 * @title: DailyInventoryDO
 * @date 2022/11/21 14:39
 */
@Data
@TableName("DailyInventory")
public class DailyInventoryDO {

    @TableField(value = "inventory_id")
    private Long inventoryId;

    @TableField(value = "warehouse_code")
    private String warehouseCode;

    @TableField(value = "quantity")
    private Integer quantity;

    @TableField(value = "prepare_quantity")
    private Integer prepareQuantity;

    @TableField(value = "modelno")
    private String modelNo;

    @TableField(value = "inventory_property_id")
    private Long inventoryPropertyId;

    @TableField(value = "in_time")
    private Date inTime;

    @TableField(value = "out_time")
    private Date outTime;

    @TableField(value = "cre_time")
    private Date creTime;

    @TableField(value = "modify_time")
    private Date modifyTime;

    @TableField(value = "modifier")
    private String modifier;

    @TableField(value = "saveTime")
    private Date saveTime;

}
