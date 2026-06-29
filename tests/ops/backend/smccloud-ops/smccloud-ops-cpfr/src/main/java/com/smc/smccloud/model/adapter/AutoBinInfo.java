package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "客户在库自动补库信息")
public class AutoBinInfo implements Serializable {

	private static final long serialVersionUID = -8506188487586175784L;

	@ApiModelProperty(value = "客户代码")
	private String customerNo;

	@ApiModelProperty(value = "客户名称")
	private String customerName;

}