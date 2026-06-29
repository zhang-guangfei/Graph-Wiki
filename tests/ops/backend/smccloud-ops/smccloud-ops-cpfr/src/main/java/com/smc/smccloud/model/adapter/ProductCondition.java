package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "产品查询条件实体")
public class ProductCondition implements Serializable {

    private static final long serialVersionUID = -8816578494345827569L;

    @ApiModelProperty(value = "部门代码")
    private String deptNo;

    @ApiModelProperty(value = "客户代码")
    private String customerNo;

    @ApiModelProperty(value = "仓库代码")
    private String warehouseCode;

    @ApiModelProperty(value = "型号列表")
    private List<String> modelList;

    @ApiModelProperty(value = "型号列表")
    private List<ModelCalCondition> modelCalList;
}