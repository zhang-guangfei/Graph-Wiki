package com.smc.smccloud.model.adapter;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "型号数量")
public class ModelNoQuantity implements Serializable {

    private static final long serialVersionUID = -6565827433550481204L;

    @ApiModelProperty(value = "型号", required = true)
    private String modelno;

    @ApiModelProperty(value = "数量", required = true)
    private Integer quantity;
}
