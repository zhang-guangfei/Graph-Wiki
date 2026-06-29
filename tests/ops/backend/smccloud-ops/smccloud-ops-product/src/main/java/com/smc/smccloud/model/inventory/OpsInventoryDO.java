package com.smc.smccloud.model.inventory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Author: B90034
 * Date: 2022-02-23 17:07
 * Description:
 */
@TableName("ops_inventory")
@Data
public class OpsInventoryDO {

    @TableId(value = "inventory_id", type = IdType.AUTO)
    private Long inventoryId;

    /**
     * 仓库代码
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 库存状态[正常在库:N, 限定:X]
     */
    @TableField("inventory_status")
    private String inventoryStatus;

    /**
     * 在库数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 数量单位
     */
    @TableField("unit")
    private String unit;

    /**
     * 质量状态: 0-良品, 1-不良品, 2-未检品
     */
    @TableField("qa_status")
    private String qaStatus;

    /**
     * 出库占用库存
     */
    @TableField("prepare_quantity")
    private Integer prepareQuantity;

    /**
     * 型号
     */
    @TableField("modelno")
    private String modelNo;

    /**
     * 归属ID
     */
    @TableField("inventory_property_id")
    private Long inventoryPropertyId;

    /**
     * 采购价格
     */
    @TableField("price")
    private String price;

    /**
     * 入库时间
     */
    @TableField("in_time")
    private Date inTime;

    @TableField("version")
    private Long version;

    /**
     * 删除标识：0未删除,1删除
     */
    @TableField("delflag")
    private Integer delFlag;

    @TableField("cre_time")
    private Date creTime;

    @TableField("creator")
    private String creator;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("modifier")
    private String modifier;

}
