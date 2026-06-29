package com.smc.smccloud.model.adapter.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "更改订单实体类")
public class OrderUpdateDelivery implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6742579036434228050L;

	@ApiModelProperty(value = "变更发货方式--订单管理修改订单信息时使用，整合接口后出货方式及货期使用一个接口")
	private OrderDeliveryModel changeModel;

	@ApiModelProperty(value = "变更货期--订单管理修改订单信息时使用，整合接口后出货方式及货期使用一个接口")
	private List<OrderDelivery> changeDate;

	@ApiModelProperty(value = "出货方式处理结果：false失败；true成功")
	private boolean result;

	@ApiModelProperty(value = "出货方式错误原因")
	private String message;
}
