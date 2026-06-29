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
@ApiModel(description = "查询列表实体")
public class SMSSearchListInfo implements Serializable {

	private static final long serialVersionUID = -1242493206302174690L;

	@ApiModelProperty(value = "查询条件：快捷查询类型。1：日本采购；2：制造采购；3：已拦截；4：将被拦截")
	private String searchType;

	 @ApiModelProperty(value = "查询人代码")
	 private String loginUserId;

	@ApiModelProperty(value = "查询条件：订单号")
	private String orderNo; // 模糊查询

	private String orderFulNo; // 精准匹配

	@ApiModelProperty(value = "查询条件：采购号")
	private String purchaseNo;

	@ApiModelProperty(value = "查询条件：客户物料号")
	private String custProductNo;

	@ApiModelProperty(value = "查询条件：型号")
	private String modelNo;

	@ApiModelProperty(value = "查询条件：客户")
	private String customerNo;

	@ApiModelProperty(value = "查询条件：用户")
	private String userNo;

	@ApiModelProperty(value = "查询条件：订货日期开始")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date orderDateStart;

	@ApiModelProperty(value = "查询条件：订货日期结束")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date orderDateEnd;

	private String orderDateStartStr;
	private String orderDateEndStr;

	@ApiModelProperty(value = "查询条件：客户担当")
	private String empSale;

	@ApiModelProperty(value = "查询条件：合同号")
	private String contractNo;

	@ApiModelProperty(value = "查询条件：报价单号")
	private String quotationNo;

	@ApiModelProperty(value = "查询条件：状态")
	private String status;

	@ApiModelProperty(value = "查询条件：逾期日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date overdueDate;

	@ApiModelProperty(value = "查询条件：逾期日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date overdueDateStart;

	 @ApiModelProperty(value = "查询条件：是否已确认")
	 private String isConfirm;

	@ApiModelProperty(value = "查询条件：直销0；非直销1")
	private String customerType;

	@ApiModelProperty(value = "导出查询条件：导出上限条数")
	private Integer exportNum;

	/**
	 * 操作人/登录人
	 */
	@ApiModelProperty(value = "查询条件：操作人")
	private String operator;

	/**
	 * 用户的查询权限(客户、部门、行业、用户)
	 */
	@ApiModelProperty(value = "查询条件：权限")
	private DataAuthoritySearchCondition dataAuthoritySearchCondition;

	/**
	 * 排序字段名
	 */
	@ApiModelProperty(value = "查询条件：排序字段名")
	private String property = "orderNo";

	/**
	 * 排序规则（正序/倒序）
	 */
	 @ApiModelProperty(value = "查询条件：排序规则 asc:正序, desc:倒叙")
	 private String order = "asc";

	/**
	 * 制单担当
	 */
	@ApiModelProperty(value = "制单担当")
	private String createId;

	/**
	 * 备注
	 */
	private String remark;

	private String endUserNo;

	/**
	 * 未发货状态 1: 未发货订单状态 2:已到达物流 3:已货齐,未到发货日
	 */
	private String deliveryStatus;

	private String  top;

	private List<String> deptCodes;

	// 1 代理店账号登录查询
	private String agentSearch = "0";

	// 信用拦截  1 true 0 false
	private Integer intercepter;

	// 特发1  普通0
	private Integer specFlag;

}
