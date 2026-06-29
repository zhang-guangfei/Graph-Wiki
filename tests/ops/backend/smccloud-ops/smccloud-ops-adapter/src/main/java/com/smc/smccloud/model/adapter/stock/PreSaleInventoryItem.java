package com.smc.smccloud.model.adapter.stock;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "专备申请子表实体")
public class PreSaleInventoryItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2099943563463457733L;

	@ApiModelProperty(value = "申请号")
	private String id;

	@ApiModelProperty(value = "申请明细ID")
	private int itemId;

	@ApiModelProperty(value = "型号")
	private String modelNo;

	@ApiModelProperty(value = "申请数量")
	private int applyQuantity;

	@ApiModelProperty(value = "交货日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date deliveryDate;

	@ApiModelProperty(value = "过期日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date expiryDate;

	@ApiModelProperty(value = "更新日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date UpdateTime;

	@ApiModelProperty(value = "审批数量")
	private int auditQuantity;

	@ApiModelProperty(value = "库房代码")
	private String stockCode;

	@ApiModelProperty(value = "原产国")
	private String produceCountry;

	@ApiModelProperty(value = "状态：1可用，4已用，6取消")
	private String itemState;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "取消原因")
	private String cancelReason;

	@ApiModelProperty(value = "日志")
	private String updateLog;

	@ApiModelProperty(value = "日本回复原因")
	private String return_Code;

	@ApiModelProperty(value = "日本回复原因")
	private String return_Description;

}