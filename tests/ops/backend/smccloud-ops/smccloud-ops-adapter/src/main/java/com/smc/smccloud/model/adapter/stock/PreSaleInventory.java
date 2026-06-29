package com.smc.smccloud.model.adapter.stock;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "预占库存实体")
public class PreSaleInventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3639993573095917938L;

	@ApiModelProperty(value = "申请号")
	private String Id;
	
	@ApiModelProperty(value = "申请类别：删除/失效")
	private String operateType;

	@ApiModelProperty(value = "申请日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date ApplyDate;

	@ApiModelProperty(value = "预计订单日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date HopeOrderDate;

	@ApiModelProperty(value = "客户代码")
	private String CustomerNo;

	@ApiModelProperty(value = "用户代码")
	private String UserNo;

	@ApiModelProperty(value = "项目台套")
	private String CustomerProNum;

	@ApiModelProperty(value = "客户项目名称")
	private String CustomerProName;

	@ApiModelProperty(value = "申请担当")
	private String MakeEmployeeNo;

	@ApiModelProperty(value = "客户担当")
	private String SaleEmployeeNo;

	@ApiModelProperty(value = "0:编辑  1:预约；2：作废  ")
	private String State;

	@ApiModelProperty(value = "营业所代码")
	private String DeptNo;

	@ApiModelProperty(value = "上下文备注信息")
	private String ContextData;

	@ApiModelProperty(value = "备注")
	private String Remark;

	@ApiModelProperty(value = "申请子项")
	private List<PreSaleInventoryItem> items;
}