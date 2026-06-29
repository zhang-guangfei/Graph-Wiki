package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryInventory {

	@ApiModelProperty(value = "型号")
	private String modelNo;

	@ApiModelProperty(value = "库房类型")
	private String stockType;

	@ApiModelProperty(value = "库房")
	private String stockCode;

	@ApiModelProperty(value = "有效库存")
	private int canuse;

	@ApiModelProperty(value = "仓库类别")
	private String warehouseType;
}
