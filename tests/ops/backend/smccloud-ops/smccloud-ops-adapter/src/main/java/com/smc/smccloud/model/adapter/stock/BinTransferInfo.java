package com.smc.smccloud.model.adapter.stock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "客户在库调库信息实体")
public class BinTransferInfo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 960903656303102369L;

	@ApiModelProperty(value = "申请单号")
	private String no;

	@ApiModelProperty(value = "申请客户代码")
	private String customerNo;

	@ApiModelProperty(value = "申请部门代码")
	private String deptNo;

	@ApiModelProperty(value = "申请人代码")
	private String employeeNo;

	@ApiModelProperty(value = "子项")
	private List<BinTransferInfoItem> items;
}
