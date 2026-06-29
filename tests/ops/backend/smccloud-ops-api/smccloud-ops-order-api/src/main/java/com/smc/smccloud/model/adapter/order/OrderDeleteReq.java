package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "存储删单结果信息实体")
public class OrderDeleteReq implements Serializable {

	private static final long serialVersionUID = 3829377158911379331L;

	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "订单单号")
	private String orderNo;

	// --
	@ApiModelProperty(value = "申请人代码")
	private String applyPersonNo;

	// state_date
	@ApiModelProperty(value = "申请时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date applyTime;

	@ApiModelProperty(value = "客户代码")
	private String customerNo;

	@ApiModelProperty(value = "客户名称")
	private String customerName;

	@ApiModelProperty(value = "用户代码")
	private String userNo;

	@ApiModelProperty(value = "用户名称")
	private String userName;

	@ApiModelProperty(value = "型号")
	private String modelNo;

	@ApiModelProperty(value = "数量")
	private int quantity;

	@ApiModelProperty(value = "单价")
	private BigDecimal price;

	@ApiModelProperty(value = "订单状态")
	private String orderStatus;

	private String handleId;

	private String handleName;

	// 删单原因
	private String message;

	// 处理状态 业务处理中，已删单，已否决
	private String applyStatus;

}
