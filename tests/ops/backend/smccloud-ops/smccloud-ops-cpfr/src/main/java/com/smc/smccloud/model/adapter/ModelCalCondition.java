package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "型号是否因为最小包装限制原因而获取库存值")
public class ModelCalCondition implements Serializable {

    private static final long serialVersionUID = -3509218813643677833L;

    @ApiModelProperty(value = "型号")
    private String modelNo;

    @ApiModelProperty(value = "是否计算库存")
    private boolean isCal;

}