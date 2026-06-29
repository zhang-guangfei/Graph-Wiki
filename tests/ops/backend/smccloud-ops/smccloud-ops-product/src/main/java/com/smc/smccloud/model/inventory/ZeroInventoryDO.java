package com.smc.smccloud.model.inventory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: ZeroInventoryDO
 * @date 2022/05/07 15:34
 */
@Data
@TableName(value = "ZeroInventory")
public class ZeroInventoryDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "ModelNo")
    private String modelNo;

    @TableField(value = "CreateTime")
    private Date createTime;

    @TableField(value = "WarehouseCode")
    private String warehouseCode;

    @TableField(value = "Days")
    private Integer days;

    @TableField(value = "FromDate")
    private Date fromDate;

    @TableField(value = "CalcDate")
    private Date calcDate;

    @TableField(value = "QtyBin")
    private Integer qtyBin;

    @TableField(value = "BinCell")
    private Integer binCell;

    @TableField(value = "Mean")
    private Integer mean;

    @TableField(value = "ModelClass")
    private String modelClass;

    @TableField(value = "Supplier")
    private String supplier;

    @TableField(value = "stockQty")
    private Integer stockQty;

    @TableField(value = "OrdingQty")
    private Integer ordingQty;

    @TableField(value = "MinOrdDate")
    private Date minOrdDate;

    @TableField(value = "MinDlvDate")
    private Date minDlvDate;

    @TableField(value = "TransQty")
    private Integer transQty;

    @TableField(value = "MinTDlvDate")
    private Date minTDlvDate;

    @TableField(value = "UpdateTime")
    private Date updateTime;

    @TableField(value = "LoginDate")
    private Date loginDate;
}
