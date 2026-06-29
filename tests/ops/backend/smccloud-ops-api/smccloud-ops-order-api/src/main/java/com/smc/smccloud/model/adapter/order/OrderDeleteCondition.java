package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "删单查询条件")
public class OrderDeleteCondition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8176240793547783980L;

	@ApiModelProperty(value = "查询条件：订单号")
	private String orderNo;

	@ApiModelProperty(value = "查询条件：型号")
	private String modelNo;

	@ApiModelProperty(value = "查询条件：客户")
	private String customerNo;

	@ApiModelProperty(value = "查询条件：用户")
	private String userNo;

	@ApiModelProperty(value = "查询条件：申请日期开始")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date applyDateStart;

	@ApiModelProperty(value = "查询条件：申请日期结束")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date applyDateEnd;

	@ApiModelProperty(value = "查询条件：状态")
	private String status;

	@ApiModelProperty(value = "查询条件：操作人")
	private String operator;

	@ApiModelProperty(value = "查询条件：权限")
	private DataAuthoritySearchCondition dataAuthoritySearchCondition;

	private List<String> deptCodes;

	/**
	 * 制单担当
	 */
	@ApiModelProperty(value = "制单担当")
	private String createId;

	// 1 代理店账号登录查询
	private String agentSearch = "0";
}
