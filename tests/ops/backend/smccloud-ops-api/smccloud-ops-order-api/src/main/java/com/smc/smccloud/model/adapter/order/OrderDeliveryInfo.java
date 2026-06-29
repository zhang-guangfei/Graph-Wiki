package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "订单发货信息")
public class OrderDeliveryInfo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 8135345172434212544L;

	@ApiModelProperty(value = "10位ERP订单号")
	private String orderNo;

	@ApiModelProperty(value = "发货单号")
	private String deliveryNo;

	@ApiModelProperty(value = "型号")
	private String modelNo;

	@ApiModelProperty(value = "数量")
	private int quantity;

	@ApiModelProperty(value = "单价")
	private BigDecimal price;

	@ApiModelProperty(value = "客户希望交货日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date dlvDate;

	@ApiModelProperty(value = "物流发货日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date warehouseSendDate;

	@ApiModelProperty(value = "出库区分")
	private String expInvCode;

	@ApiModelProperty(value = "发货日期")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date deliveryDate;

	@ApiModelProperty(value = "发货状态")
	private String deliveryStatus;

	@ApiModelProperty(value = "快递单号")
	private String expressNo;

	@ApiModelProperty(value = " 承运商代码")
	private String expressCompanyNo;

	@ApiModelProperty(value = "承运商名称")
	private String expressCompanyName;

}
