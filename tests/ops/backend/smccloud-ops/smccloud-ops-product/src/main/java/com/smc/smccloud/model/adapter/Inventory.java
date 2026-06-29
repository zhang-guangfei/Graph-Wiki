package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "库存实体")
public class Inventory implements Serializable {

    private static final long serialVersionUID = 4627803503465104754L;

    @ApiModelProperty(value = "型号")
    private String modelNo;

    @ApiModelProperty(value = "场所")
    private String stock;

    @ApiModelProperty(value = "所属的部门名称")
    private String stockHandler;

    @ApiModelProperty(value = "6位部门编码")
    private String stockCode;

    @ApiModelProperty(value = "客户")
    private String customerNo;

    @ApiModelProperty(value = "有效库存")
    private int quantity;

    @ApiModelProperty(value = "实际库存")
    private int qtyOnHand;

    @ApiModelProperty(value = "预约数量")
    private int qtyPrePare;

    @ApiModelProperty(value = "预测占用数量")
    private int qtyForecast;

    @ApiModelProperty(value = "库房类型")
    private String inventoryTypeCode;

    @ApiModelProperty(value = "库房类型名称")
    private String inventoryTypeName;

    @ApiModelProperty(value = "仓库代码")
    private String warehouseCode;

    @ApiModelProperty(value = "仓库代码名称")
    private String warehouseName;

    @ApiModelProperty(value = "设备BOM号(PPL号)")
    private String bomNo;

    @ApiModelProperty(value = "项目号")
    private String projectCode;

    @ApiModelProperty(value = "客户群代码")
    private String groupCustomerNo;

    @ApiModelProperty(value = "部门代码")
    private String deptNo;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    private String binFlagStr;
}