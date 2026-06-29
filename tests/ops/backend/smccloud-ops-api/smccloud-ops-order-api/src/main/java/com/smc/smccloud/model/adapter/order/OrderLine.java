package com.smc.smccloud.model.adapter.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "订单流程图连线")
public class OrderLine implements Serializable {

	private static final long serialVersionUID = -1242493306302174690L;
	// id
	@ApiModelProperty(value = "id")
	private String id;
	// 起点
	@ApiModelProperty(value = "起点")
	private String source;
	// 起点
	@ApiModelProperty(value = "终点")
	private String target;
	// 激活 : 1  ,非激活:0
	@ApiModelProperty(value = "激活")
	private String active;

}
