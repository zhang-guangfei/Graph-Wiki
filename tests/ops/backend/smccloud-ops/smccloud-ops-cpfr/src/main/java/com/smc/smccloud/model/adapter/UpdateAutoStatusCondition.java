package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "更新客户自动补库状态")
public class UpdateAutoStatusCondition implements Serializable {

    private static final long serialVersionUID = -1413978279257930440L;

    @ApiModelProperty(value = "客户代码")
    private String customerNo;

    @ApiModelProperty(value = "型号")
    private List<String> modelNoList;

    @ApiModelProperty(value = "状态")
    private String optCode;
}