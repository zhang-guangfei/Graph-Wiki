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
@ApiModel(description = "分库补库申请主表实体")
public class ChinaRegionWarehouseSupplyApply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 228589439236061043L;
	

	@ApiModelProperty(value = "申请号")
	private String id;

	@ApiModelProperty(value = "申请日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date applyDate;
	
	@ApiModelProperty(value = "发送日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date sendDate;
	
	@ApiModelProperty(value = "客户代码")
	private String customerNo;
	
	@ApiModelProperty(value = "客户名称")
	private String customerName;
	
	@ApiModelProperty(value = "客户类型")
	private String customerType;
	
	@ApiModelProperty(value = "申请担当")
	private String makeEmployeeNo;
	
	@ApiModelProperty(value = "申请担当名称")
	private String makeEmployeeName;
	
	@ApiModelProperty(value = "客户担当")
	private String saleEmployeeNo;
	
	@ApiModelProperty(value = "客户担当名称")
	private String saleEmployeeName;
	
	@ApiModelProperty(value = "审批状态")
	private String stateCode;
	
	@ApiModelProperty(value = "营业所代码")
	private String deptNo;
	
	@ApiModelProperty(value = "申请补库库房代码")
	private String warehouseId;
	
	@ApiModelProperty(value = "备注(申请原因)")
	private String remark;

	@ApiModelProperty(value = "客户群名称")
	private String customerBaseName;

	@ApiModelProperty(value = "客户群代码")
	private String customerBaseNo;

    // 重点项目标识 true 重点项目 false 非重点项目
	private Boolean vipProjectFlag;
	
	@ApiModelProperty(value = "申请子项")
	private List<ChinaRegionWarehouseSupplyApplyItem> items;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date approveTime;
}