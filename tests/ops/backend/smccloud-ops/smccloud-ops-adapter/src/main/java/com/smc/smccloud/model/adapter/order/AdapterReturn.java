package com.smc.smccloud.model.adapter.order;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "适配器队列发送结果返回实体")
public class AdapterReturn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1004909655137288556L;

	/**
	 * erpNo 与 no 的区别
	 *  用来区分是订单跟踪的修改还是合同订单发起的修改
	 *  no : 发注单号
	 *  erpNo : ops订单号
	 */
	@ApiModelProperty(value = "直销门户申请单号")
	private String no;

	@ApiModelProperty(value = "直销门户申请子项号及型号")
	private Map<Integer, String> itemModelList;

	@ApiModelProperty(value = "直销门户申请子项号")
	private Integer itemNo;

	@ApiModelProperty(value = "直销门户申请型号")
	private String modelNo;

	@ApiModelProperty(value = "ERP申请单号")
	private String ERPno;

	@ApiModelProperty(value = "预占库存删除申请类别：删除/失效")
	private String operateType;

	@ApiModelProperty(value = "处理结果：false失败；true成功")
	private boolean result;

	@ApiModelProperty(value = "错误原因")
	private String message;

	@ApiModelProperty(value = "货期修改参数")
	private OrderDelivery modiDate;

	@ApiModelProperty(value = "发货方式修改参数")
	private OrderDeliveryModel modiModel;

}
