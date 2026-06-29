package com.smc.smccloud.model.Manufacture;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: OpsTModelAvailableToSales
 * @date 2022/04/28 11:39
 */
@Data
@TableName(value = "Manufacture.dbo.OPS_T_ModelAvailableToSales")
public class OpsTModelAvailableToSales {

    @TableField(value = "plantmark")
    private String plantmark;

    @TableField(value = "SerialID")
    private Integer serialID;

    @TableField(value = "ProcessCode")
    private String processCode;

    @TableField(value = "KOGONo")
    private String kOGONo;

    @TableField(value = "Model")
    private String model;

    @TableField(value = "BINManage")
    private String bINManage;

    @TableField(value = "DescriptionChn")
    private String descriptionChn;

    @TableField(value = "mark")
    private Integer mark;

    @TableField(value = "Description")
    private String description;

    @TableField(value = "Property")
    private String property;

    @TableField(value = "UpdateTime")
    private Date updateTime;

    @TableField(value = "Activity")
    private Integer activity;

    @TableField(value = "RequestType")
    private Integer requestType;

    @TableField(value = "TradeMode")
    private String tradeMode;
}
