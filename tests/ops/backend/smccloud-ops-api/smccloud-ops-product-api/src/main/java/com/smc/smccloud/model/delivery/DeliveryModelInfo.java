package com.smc.smccloud.model.delivery;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "物流发货货期明细")
public class DeliveryModelInfo {

	@ApiModelProperty(value = "项号")
	private Integer itemNo;

	@ApiModelProperty(value = "型号", required = true)
	private String modelNo;

	@ApiModelProperty(value = "数量", required = true)
	private int quantity;

	@ApiModelProperty(value = "最快货期")
	private int deliveryDay;

	// 适正货期
	private int suitableDay;

	@ApiModelProperty(value = "货期描述")
	private String deliveryDayDescription;

	@ApiModelProperty(value = "客户希望交货日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date dlvDate;

	@ApiModelProperty(value = "物流发货日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date warehouseSendDate;

	// PPL代码
	private String ppl;

	// PJ代码
	private String pj;

	// 集团客户代码
	private String groupCustomer;

	// 是否BIN品
	private boolean isBin = false;

	// 可用库存
	private Integer canuseQuantity = 0;

}
