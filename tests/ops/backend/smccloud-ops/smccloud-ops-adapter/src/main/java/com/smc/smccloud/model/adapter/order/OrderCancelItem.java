package com.smc.smccloud.model.adapter.order;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "存储删单信息子项实体")
public class OrderCancelItem implements Serializable {

	private static final long serialVersionUID = 1273163240737175453L;

	// 仅门户使用，区分申请类型，传过去什么返回什么，不要变动
	// 如果是明细删单则传合同订单号，发注单删单则不传
	@ApiModelProperty(value = "直销门户出库单号")
	private String no;

	@ApiModelProperty(value = "10位ERP订单号")
	private String orderNo;

	@ApiModelProperty(value = "子项号")
	private String itemNo;
}
