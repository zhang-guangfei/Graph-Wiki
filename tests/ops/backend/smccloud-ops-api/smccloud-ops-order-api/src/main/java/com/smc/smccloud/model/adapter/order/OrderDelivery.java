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
@ApiModel(description = "更改订单实体类")
public class OrderDelivery implements Serializable {

	private static final long serialVersionUID = -1242493206302174690L;

	@ApiModelProperty(value = "操作人代码")
	private String loginUserId;

	@ApiModelProperty(value = "部门代码")
	private String deptNo;

	// 订单号(10位)
	@ApiModelProperty(value = "订单号")
	private String orderNo;

	@ApiModelProperty(value = "直销门户申请单号")
	private String no;

	@ApiModelProperty(value = "物流发货日期")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date warehouseSendDate;

	@ApiModelProperty(value = "变更前物流发货日")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date warehouseSendDate_old;

	@ApiModelProperty(value = "客户期望发货日期")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date dlvDate;

	@ApiModelProperty(value = "变更前物流发货日")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date dlvDate_old;

	@ApiModelProperty(value = "变更客户货期")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date customerChangeDate;

	// 特发/普通
	private String specialNormal;

	// 物流自选，顺丰特快
	private String optCarrier;

	//子项特发,组装指令
	private String assemble;

	// 收货地址信息实体
	private DeliveryAddressInfo deliveryAddressInfo;

	@ApiModelProperty(value = "是否更新成功")
	private Boolean isSuccess;

	@ApiModelProperty(value = "更新返回")
	private String message;

}
