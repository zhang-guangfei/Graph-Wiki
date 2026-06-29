package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "库存查询实体")
public class InventoryCondition implements Serializable {

	private static final long serialVersionUID = 912462238377219697L;

	@ApiModelProperty(value = "客户代码")
	private String customerNo;

	@ApiModelProperty(value = "部门代码")
	private String deptNo;

	@ApiModelProperty(value = "型号")
	private List<String> modelNo;

	@ApiModelProperty(value = "型号及数量")
	private List<ModelNoQuantity> list;

}