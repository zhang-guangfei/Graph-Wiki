package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "型号模糊查询返回数据")
public class InventoryFuzzySearch implements Serializable {

    private static final long serialVersionUID = 1778027190730497512L;

    @ApiModelProperty(value = "型号")
    private String modelNo;

    @ApiModelProperty(value = "库位")
    private String stockCode;

    @ApiModelProperty(value = "库位")
    private String stockName;

    @ApiModelProperty(value = "客户")
    private String customerNo;

    @ApiModelProperty(value = "客户")
    private String customerName;

    @ApiModelProperty(value = "有效库存")
    private int quantity;

    @ApiModelProperty(value = "库存类型")
    private String stockType;

    @ApiModelProperty(value = "库存类型名称")
    private String stockTypeName;

    private String groupCustomerNo; // 客户群代码
}