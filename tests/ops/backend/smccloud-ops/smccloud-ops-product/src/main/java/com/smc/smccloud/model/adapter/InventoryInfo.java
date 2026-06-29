package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "型号库存信息实体")
public class InventoryInfo implements Serializable {

    private static final long serialVersionUID = 3333141365336479049L;

    @ApiModelProperty(value = "库存类别代码")
    public String inventoryType;

    @ApiModelProperty(value = "库存类别名称")
    public String inventoryName;

    @ApiModelProperty(value = "可用数量")
    public Integer inventoryQuantity;

    @ApiModelProperty(value = "型号")
    public List<Inventory> list;

}