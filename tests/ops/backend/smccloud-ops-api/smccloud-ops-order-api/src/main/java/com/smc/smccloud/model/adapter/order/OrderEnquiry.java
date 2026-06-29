package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OrderEnquiry {
	/**
	 * 问询单号
	 */
	@ApiModelProperty(value = "问询单号")
	private String queryNo;
	/**
	 * 项号
	 */
	@ApiModelProperty(value = "项号")
	private String itemNo;
	/**
	 * 销售出库单号 
	 */
	@ApiModelProperty(value = "销售出库单号 ")
	private String saleOutboundNo;
	/**
	 * ERP订单号
	 */
	@ApiModelProperty(value = "业务单号")
	private String erpOrderNo;
	/**
	 * 客户编码
	 */
	@ApiModelProperty(value = "客户编码")
	private String customerNo;
	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	private String customerName;
	/**
	 * 型号
	 */
	@ApiModelProperty(value = "型号")
	private String modelNo;
	/**
	 * 数量 
	 */
	@ApiModelProperty(value = " 数量")
	private Integer quantity;
	/**
	 * 部门代码
	 */
	@ApiModelProperty(value = "部门代码")
	private String deptCode;
	/**
	 * 部门名称
	 */
	@ApiModelProperty(value = "部门名称")
	private String deptName;
	/**
	 * 当前货期
	 */
	@ApiModelProperty(value = "当前货期")
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING,timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date currentDeliveryDate;
	/**
	 * 期望货期
	 */
	@ApiModelProperty(value = "期望货期")
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING,timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date hopeDeliveryDate;
	/**
	 * 回复货期
	 */
	@ApiModelProperty(value = "回复货期")
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING,timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date replyDeliveryDate;
	/**
	 * 采购状态
	 */
	@ApiModelProperty(value = "采购状态")
	private String purchaseStatus;
	/**
	 * 问询状态
	 */
	@ApiModelProperty(value = "问询状态")
	private String queryStatus;
	/**
	 * 问题类型
	 */
	@ApiModelProperty(value = "问题类型")
	private String questionType;
	/**
	 * 问题描述
	 */
	@ApiModelProperty(value = "问题描述")
	private String questionDesc;
	/**
	 * 货期提前原因
	 */
	@ApiModelProperty(value = "货期提前原因")
	private String advanceReason;
	/**
	 * 货期延迟原因
	 */
	@ApiModelProperty(value = "货期延迟原因")
	private String delayReason;
	/**
	 * 催促人ID
	 */
	@ApiModelProperty(value = "催促人ID")
	private String queryPersonId;
	/**
	 * 催促人姓名
	 */
	@ApiModelProperty(value = "催促人姓名")
	private String queryPersonName;
	/**
	 * 问询时间
	 */
	@ApiModelProperty(value = "问询时间")
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING,timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date queryDate;
	/**
	 * 回复时间
	 */
	@ApiModelProperty(value = "回复时间")
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING,timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date replyDate;
	/**
	 * 回复人ID
	 */
	@ApiModelProperty(value = "回复人ID")
	private String replierId;
	/**
	 * 回复人姓名
	 */
	@ApiModelProperty(value = "回复人姓名")
	private String replierName;
	/**
	 * 回复人部门名称 
	 */
	@ApiModelProperty(value = "回复人部门名称 ")
	private String replierDeptName;
	/**
	 * 回复人部门代码
	 */
	@ApiModelProperty(value = "回复人部门代码")
	private String replierDeptCode;
	/**
	 * 回复内容
	 */
	@ApiModelProperty(value = "回复内容")
	private String replierContent;
	/**
	 * 问询备注
	 */
	@ApiModelProperty(value = "问询备注")
	private String remark;
	public String getQueryNo() {
		return queryNo;
	}
	public void setQueryNo(String queryNo) {
		this.queryNo = queryNo;
	}
	public String getSaleOutboundNo() {
		return saleOutboundNo;
	}
	public void setSaleOutboundNo(String saleOutboundNo) {
		this.saleOutboundNo = saleOutboundNo;
	}
	public String getErpOrderNo() {
		return erpOrderNo;
	}
	public void setErpOrderNo(String erpOrderNo) {
		this.erpOrderNo = erpOrderNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Date getCurrentDeliveryDate() {
		return currentDeliveryDate;
	}
	public void setCurrentDeliveryDate(Date currentDeliveryDate) {
		this.currentDeliveryDate = currentDeliveryDate;
	}
	public Date getHopeDeliveryDate() {
		return hopeDeliveryDate;
	}
	public void setHopeDeliveryDate(Date hopeDeliveryDate) {
		this.hopeDeliveryDate = hopeDeliveryDate;
	}
	public Date getReplyDeliveryDate() {
		return replyDeliveryDate;
	}
	public void setReplyDeliveryDate(Date replyDeliveryDate) {
		this.replyDeliveryDate = replyDeliveryDate;
	}
	public String getPurchaseStatus() {
		return purchaseStatus;
	}
	public void setPurchaseStatus(String purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}
	public String getQueryStatus() {
		return queryStatus;
	}
	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getQuestionDesc() {
		return questionDesc;
	}
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}
	public String getAdvanceReason() {
		return advanceReason;
	}
	public void setAdvanceReason(String advanceReason) {
		this.advanceReason = advanceReason;
	}
	public String getDelayReason() {
		return delayReason;
	}
	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}
	public String getQueryPersonId() {
		return queryPersonId;
	}
	public void setQueryPersonId(String queryPersonId) {
		this.queryPersonId = queryPersonId;
	}
	public String getQueryPersonName() {
		return queryPersonName;
	}
	public void setQueryPersonName(String queryPersonName) {
		this.queryPersonName = queryPersonName;
	}
	public Date getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	public Date getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}
	public String getReplierId() {
		return replierId;
	}
	public void setReplierId(String replierId) {
		this.replierId = replierId;
	}
	public String getReplierName() {
		return replierName;
	}
	public void setReplierName(String replierName) {
		this.replierName = replierName;
	}
	public String getReplierDeptName() {
		return replierDeptName;
	}
	public void setReplierDeptName(String replierDeptName) {
		this.replierDeptName = replierDeptName;
	}
	public String getReplierDeptCode() {
		return replierDeptCode;
	}
	public void setReplierDeptCode(String replierDeptCode) {
		this.replierDeptCode = replierDeptCode;
	}
	public String getReplierContent() {
		return replierContent;
	}
	public void setReplierContent(String replierContent) {
		this.replierContent = replierContent;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	
}
