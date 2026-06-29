package com.sales.ops.dto.purchase;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * bug11808采购单变更申请返回内容
 * 
 * @author SMC892N
 *
 */
public class ModifyPurchaseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5941382823438862592L;

	public String orderNo;

	public Integer itemNo;

	public Integer splitNo;

	public String orderFno;

	public String modelNo;

	public Integer qty;

	// 采购日期
	public Date purchaseDate;

	// 总净重
	public BigDecimal netWeight;

	// 运输方式
	public String transType;

	// 返信纳期
	public Date dlvModDate;

	// 指定出荷日
	public Date hopeExportDate;

	// 客户货期
	public Date hopeDeliveryDate;

	// 是否可以变更
	public boolean isReset;

	// 不可变更原因
	public String remark;

	// 可用运输方式
	public List<BaseDataDto> availableTransType;

	public String customerNo;

	public String userNo;

	// 供应商
	public String supplierId;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public Integer getSplitNo() {
		return splitNo;
	}

	public void setSplitNo(Integer splitNo) {
		this.splitNo = splitNo;
	}

	public String getOrderFno() {
		return orderFno;
	}

	public void setOrderFno(String orderFno) {
		this.orderFno = orderFno;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getDlvModDate() {
		return dlvModDate;
	}

	public void setDlvModDate(Date dlvModDate) {
		this.dlvModDate = dlvModDate;
	}

	public Date getHopeExportDate() {
		return hopeExportDate;
	}

	public void setHopeExportDate(Date hopeExportDate) {
		this.hopeExportDate = hopeExportDate;
	}

	public boolean isReset() {
		return isReset;
	}

	public void setReset(boolean isReset) {
		this.isReset = isReset;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<BaseDataDto> getAvailableTransType() {
		return availableTransType;
	}

	public void setAvailableTransType(List<BaseDataDto> availableTransType) {
		this.availableTransType = availableTransType;
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

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Date getHopeDeliveryDate() {
		return hopeDeliveryDate;
	}

	public void setHopeDeliveryDate(Date hopeDeliveryDate) {
		this.hopeDeliveryDate = hopeDeliveryDate;
	}

}
