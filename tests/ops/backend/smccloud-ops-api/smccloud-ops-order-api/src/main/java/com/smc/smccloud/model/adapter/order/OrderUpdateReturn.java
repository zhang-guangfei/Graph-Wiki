package com.smc.smccloud.model.adapter.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "出货方式、货期修改返回")
public class OrderUpdateReturn implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7959737162206119997L;

	@ApiModelProperty(value = "订单号")
	private String ERPno;

	@ApiModelProperty(value = "修改类型：0出货方式；1货期")
	private String type;

	@ApiModelProperty(value = "处理结果：false失败；true成功")
	private boolean result;

	@ApiModelProperty(value = "错误原因")
	private String message;
}
