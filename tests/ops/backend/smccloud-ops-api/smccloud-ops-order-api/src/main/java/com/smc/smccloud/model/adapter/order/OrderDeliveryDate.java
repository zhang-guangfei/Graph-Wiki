package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "更改接单发货期")
public class OrderDeliveryDate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9185257851030463312L;

	@ApiModelProperty(value = "操作人代码")
	private String loginUserId;

	@ApiModelProperty(value = "部门代码")
	private String deptNo;

	// 订单号(10位)
	@ApiModelProperty(value = "订单号")
	private String orderNo;

	@ApiModelProperty(value = "物流发货日期")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date warehouseSendDate;

	@ApiModelProperty(value = "客户期望发货日期")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date dlvDate;
}
