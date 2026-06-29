package com.smc.smccloud.model.adapter.stock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "客户在库调库子项实体")
public class BinTransferInfoItem implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6514778886127172208L;

	@ApiModelProperty(value = "子项号")
	private int itemId;

	@ApiModelProperty(value = "型号")
	private String modelNo;

	@ApiModelProperty(value = "数量")
	private int quantity;

	@ApiModelProperty(value = "调出客户代码")
	private String customerNoTrans;

	@ApiModelProperty(value = "调出库房代码")
	private String stockTrans;

	@ApiModelProperty(value = "库房类型")
	private String inventoryTypeCode;

	@ApiModelProperty(value = "设备BOM号(PPL号)")
	private String bomNo;

	@ApiModelProperty(value = "项目号")
	private String projectCode;

}
