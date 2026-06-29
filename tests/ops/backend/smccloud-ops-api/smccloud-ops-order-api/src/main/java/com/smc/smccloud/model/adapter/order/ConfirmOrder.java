package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "订单确认")
public class ConfirmOrder implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 7492798772230563316L;

	@ApiModelProperty(value = "订货日期开始")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date orderDateStart;

	@ApiModelProperty(value = "订货日期结束")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date orderDateEnd;

	@ApiModelProperty(value = "营业所代码")
	private String deptNo;

	@ApiModelProperty(value = "营业所名称")
	private String deptName;

	@ApiModelProperty(value = "确认人代码")
	private String confirmPersonNo;

	@ApiModelProperty(value = "确认人名称")
	private String confirmPersonName;

	@ApiModelProperty(value = "确认人职位代码")
	private String confirmPersonPositionId;

	@ApiModelProperty(value = "确认人职位名称")
	private String confirmPersonPosition;

	@ApiModelProperty(value = "确认时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd")
	private Date confirmTime;

	/**
	 * 用户的查询权限(客户、部门、行业、用户)
	 */
	@ApiModelProperty(value = "查询条件：权限")
	private DataAuthoritySearchCondition dataAuthoritySearchCondition;

	/**
	 * 需要查询的部门条件
	 */
	@ApiModelProperty(value = "查询条件：部门")
	private List<String> deptCodes;

	/**
	 * 制单担当
	 */
	@ApiModelProperty(value = "制单担当")
	private String createId;
}
