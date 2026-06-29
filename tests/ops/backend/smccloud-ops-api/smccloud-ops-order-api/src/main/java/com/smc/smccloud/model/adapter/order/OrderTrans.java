package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "查询订单号对应的快递信息")
public class OrderTrans implements Serializable {

	private static final long serialVersionUID = -202529577608765982L;

	@ApiModelProperty(value = "订单号")
	private String RorderNo;

	@ApiModelProperty(value = "详细信息")
	private String OptExpDesc;

	@ApiModelProperty(value = "订单日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private String OptTime;

	@ApiModelProperty(value = "快递单号")
	private String DeliveryNo;

	@ApiModelProperty(value = " 承运商代码")
	private String TRANSCOMP_ID;

	@ApiModelProperty(value = "承运商电话")
	private String HOTLINE_NO;

	@ApiModelProperty(value = "承运商名称")
	private String TRANSNAME;
}
