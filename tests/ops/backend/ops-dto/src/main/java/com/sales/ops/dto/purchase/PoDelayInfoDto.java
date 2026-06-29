package com.sales.ops.dto.purchase;

import java.io.Serializable;
import java.util.Date;

/**
 * 废弃，与延期dto合并为同一个
 */
public class PoDelayInfoDto implements Serializable {

	/**
	 * bug12769 采购返信货期延期提醒
	 */
	private static final long serialVersionUID = -8854182300398590468L;

	// 客户单号
	private String rorderFno;
	private String rorderNo;
	private Integer rorderItem;

	// 采购单号
	private String poOrderFNo;

	private String poOrderNo;

	private Integer poItemNo;

	private Integer poSplitItemNo;

	private String modelNo;

	private Integer quantity;

	// 手配号，即供应商接单号
	private String supplierOrderNo;

	// 供应商代码
	private String supplierId;

	// 出库区分
	private String produceFactory;

	// 客户所属部门
	private String deptNoCustomer;

	// 客户代码
	private String customerNo;

	// 用户代码
	private String userNo;

	// 最终用户
	private String endUser;

	// 指定出荷日
	private Date hopeExportDate;

	// 返信
	private Date replyDateMod;

	// 实际出荷日
	private Date facExpdate;

	// 客户货期
	private Date hopeDeliveryDate;

	// 订单类型
	private String orderType;

	// 订单类型名称
	private String orderTypeName;

	public String getRorderFno() {
		return rorderFno;
	}

	public void setRorderFno(String rorderFno) {
		this.rorderFno = rorderFno;
	}

	public String getRorderNo() {
		return rorderNo;
	}

	public void setRorderNo(String rorderNo) {
		this.rorderNo = rorderNo;
	}

	public Integer getRorderItem() {
		return rorderItem;
	}

	public void setRorderItem(Integer rorderItem) {
		this.rorderItem = rorderItem;
	}

	public String getPoOrderFNo() {
		return poOrderFNo;
	}

	public void setPoOrderFNo(String poOrderFNo) {
		this.poOrderFNo = poOrderFNo;
	}

	public String getPoOrderNo() {
		return poOrderNo;
	}

	public void setPoOrderNo(String poOrderNo) {
		this.poOrderNo = poOrderNo;
	}

	public Integer getPoItemNo() {
		return poItemNo;
	}

	public void setPoItemNo(Integer poItemNo) {
		this.poItemNo = poItemNo;
	}

	public Integer getPoSplitItemNo() {
		return poSplitItemNo;
	}

	public void setPoSplitItemNo(Integer poSplitItemNo) {
		this.poSplitItemNo = poSplitItemNo;
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

	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}

	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getProduceFactory() {
		return produceFactory;
	}

	public void setProduceFactory(String produceFactory) {
		this.produceFactory = produceFactory;
	}

	public String getDeptNoCustomer() {
		return deptNoCustomer;
	}

	public void setDeptNoCustomer(String deptNoCustomer) {
		this.deptNoCustomer = deptNoCustomer;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getEndUser() {
		return endUser;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public Date getHopeExportDate() {
		return hopeExportDate;
	}

	public void setHopeExportDate(Date hopeExportDate) {
		this.hopeExportDate = hopeExportDate;
	}

	public Date getReplyDateMod() {
		return replyDateMod;
	}

	public void setReplyDateMod(Date replyDateMod) {
		this.replyDateMod = replyDateMod;
	}

	public Date getFacExpdate() {
		return facExpdate;
	}

	public void setFacExpdate(Date facExpdate) {
		this.facExpdate = facExpdate;
	}

	public Date getHopeDeliveryDate() {
		return hopeDeliveryDate;
	}

	public void setHopeDeliveryDate(Date hopeDeliveryDate) {
		this.hopeDeliveryDate = hopeDeliveryDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

}
