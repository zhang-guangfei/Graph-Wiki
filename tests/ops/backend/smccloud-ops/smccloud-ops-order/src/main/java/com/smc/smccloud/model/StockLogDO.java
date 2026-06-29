package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author C18117
 * @title: StockLogDO
 * @date 2022/11/18 14:40
 */
@Data
@TableName("stocklog")
public class StockLogDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "sourceId")
    private Long sourceId;

    @TableField(value = "modelNo")
    private String modelNo;

    @TableField(value = "qty")
    private Integer qty;

    @TableField(value = "lotPrice")
    private BigDecimal lotPrice;

    @TableField(value = "out")
    private Boolean out;

    @TableField(value = "impType")
    private Integer impType;

    @TableField(value = "updateTime", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value = "InventoryId")
    private Long InventoryId;

    @TableField(value = "propertyId")
    private Long propertyId;

    @TableField(value = "orderNo")
    private String orderNo;

    @TableField(value = "itemNo")
    private Integer itemNo;

    @TableField(value = "lotQty")
    private Integer lotQty;

    @TableField(value = "lotType")
    private Integer lotType;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "shikomiNo")
    private String shikomiNo;

    @TableField(value = "splitItemNo")
    private Integer splitItemNo;
}
