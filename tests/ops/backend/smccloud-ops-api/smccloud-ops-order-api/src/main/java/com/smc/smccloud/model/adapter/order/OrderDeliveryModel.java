package com.smc.smccloud.model.adapter.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "合同订单修改出货方式地址等")
public class OrderDeliveryModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4842633749036958738L;

	@ApiModelProperty(value = "操作人代码")
	private String loginUserId;

	@ApiModelProperty(value = "部门代码")
	private String deptNo;

	@ApiModelProperty(value = "直销门户出库单号")
	private String no;

	// 订单号(7位)
	@ApiModelProperty(value = "订单号")
	private List<String> orderNo;

	@ApiModelProperty(value = "交货地点")
	private String deliveryModeNo;

	@ApiModelProperty(value = "出货方式")
	private String deliveryEntireNo;

	@ApiModelProperty(value = "承运商")
	private String carrierNo;

	@ApiModelProperty(value = "集约方式")
	private String intensiveNo;

	@ApiModelProperty(value = "费用承担方")
	private String feeNo;

	@ApiModelProperty(value = "发货地址，若为原地址则设置其中isDefault为1")
	private DeliveryAddressInfo addressInfo;

}
