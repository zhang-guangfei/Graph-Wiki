package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "产品实体")
public class Product implements Serializable {

    private static final long serialVersionUID = -1159975023876201189L;

    @ApiModelProperty(value = "型号")
    private String modelNo;

    @ApiModelProperty(value = "G:GSS;K:BIN")
    private String modelType;

    @ApiModelProperty(value = "产品类别：GSS品、Bin品")
    private String modelTypeDesc;

    @ApiModelProperty(value = "安全库存")
    private int safeQuantity;

    @ApiModelProperty(value = "产地类别")
    private String madeType;

    @ApiModelProperty(value = "有效库存")
    private Integer canUseQuantity;

    @ApiModelProperty(value = "仓库")
    private String stock;

    @ApiModelProperty(value = "仓库代码")
    private String stockCode;
}