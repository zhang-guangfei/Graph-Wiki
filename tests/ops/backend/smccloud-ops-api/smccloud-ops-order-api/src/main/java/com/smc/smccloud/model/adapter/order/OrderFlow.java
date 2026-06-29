package com.smc.smccloud.model.adapter.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "订单流程图连线")
public class OrderFlow implements Serializable {

	private static final long serialVersionUID = -1242493306302174690L;

	// 名称
	@ApiModelProperty(value = "name")
	private String name;

	// 标题
	@ApiModelProperty(value = "标题")
	private String label;

	// 详情
	@ApiModelProperty(value = "详情")
	private String detail;

	// 激活 : 1  ,非激活:0
	@ApiModelProperty(value = "激活")
	private String active;

}
