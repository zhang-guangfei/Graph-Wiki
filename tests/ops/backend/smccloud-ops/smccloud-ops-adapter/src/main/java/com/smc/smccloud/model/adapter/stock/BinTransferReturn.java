package com.smc.smccloud.model.adapter.stock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "客户在库调库返回类")
public class BinTransferReturn implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -7187252211606657235L;

	@ApiModelProperty(value = "客户在库调库申请单号")
	private String no;

	@ApiModelProperty(value = "子项号")
	private int itemId;

	@ApiModelProperty(value = "型号")
	private String modelNo;

	@ApiModelProperty(value = "处理结果：false失败；true成功")
	private boolean result;

	@ApiModelProperty(value = "错误原因")
	private String message;
}
