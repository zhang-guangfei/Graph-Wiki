package com.smc.smccloud.model.adapter;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "在库补库申请主表实体")
public class BinDataApply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5199297718511060315L;

	@ApiModelProperty(value = "申请号")
	private String id;

	@ApiModelProperty(value = "申请日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date applyDate;

	@ApiModelProperty(value = "客户代码")
	private String customerNo;

	@ApiModelProperty(value = "申请担当")
	private String makeEmployeeNo;

	@ApiModelProperty(value = "客户担当")
	private String saleEmployeeNo;

	@ApiModelProperty(value = "审批状态")
	private String status;

	@ApiModelProperty(value = "销售部门代码")
	private String deptNo;

	@ApiModelProperty(value = "申请类别")
	private int applyType;

	@ApiModelProperty(value = "是否自动补库")
	private Boolean autoSupply;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "上年销售额(需求中未提及)")
	private BigDecimal lastYearSaleAmount;

	@ApiModelProperty(value = "本年度销售额(需求中未提及)")
	private BigDecimal thisYearSaleAmount;

	@ApiModelProperty(value = "申请子项")
	private List<BinDataApplyItem> items;
}
