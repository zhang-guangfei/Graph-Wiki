package com.smc.smccloud.model.adapter.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "删单返回")
public class OrderDeleteReturn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -154297340212589222L;

	@ApiModelProperty(value = "直销门户出库单号")
	private String no;

	@ApiModelProperty(value = "子项号")
	private String itemNo;

	@ApiModelProperty(value = "ERP订单号")
	private String orderNo;

	@ApiModelProperty(value = "直销门户出库单号")
	private String saleOrderNo;

	@ApiModelProperty(value = "处理结果：0申请成功；1申请失败；2删除成功；3删除失败")
	private String result;

	@ApiModelProperty(value = "错误原因")
	private String message;

	/**
	 * 处理人
	 */
	private String handlePsnNo;

	private String handlePsnName;

	/**
	 * 处理备注
	 */
	private String handleRemark;
}
