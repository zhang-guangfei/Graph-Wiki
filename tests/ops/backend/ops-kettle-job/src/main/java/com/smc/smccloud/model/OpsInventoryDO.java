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
 * 库存表
 * </p>
 *
 * @author smc
 * @since 2025-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_inventory_dds")
public class OpsInventoryDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "inventory_id", type = IdType.AUTO)
    private Long inventoryId;

    /**
     * 仓库代码
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 库存状态[	正常在库:N;	限定:X]
     */
    @TableField("inventory_status")
    private String inventoryStatus;

    /**
     * 期初数量
     */
    @TableField("initial_quantity")
    private Integer initialQuantity;

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
     * 质量状态：0良品  1不良品   2 未检品
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
    private String modelno;

    /**
     * 批属性ID
     */
    @TableField("inventory_property_id")
    private Long inventoryPropertyId;

    /**
     * 采购价格
     */
    @TableField("price")
    private Double price;

    /**
     * 最近入库时间
     */
    @TableField("in_time")
    private Date inTime;

    /**
     * 最近出库时间
     */
    @TableField("out_time")
    private Date outTime;

    /**
     * 增加版本号更新库存，防止并发数量异常更新
     */
    @TableField("version")
    private Long version;

    /**
     * 删除标识：0未删除,1删除
     */
    @TableField("delflag")
    private Integer delflag;

    /**
     * 创建时间
     */
    @TableField("cre_time")
    private Date creTime;

    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private Date modifyTime;

    /**
     * 修改人
     */
    @TableField("modifier")
    private String modifier;

    /**
     * 更新期初时间
     */
    @TableField("initial_time")
    private Date initialTime;


}
