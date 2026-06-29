package com.sales.ops.dto.delivery;

public class SupplierHistoryDeliveryDayDto {

	private String supplierId;

	// 产品属性：标准品、特注品
	private String designName;

	private Integer deliveryDay;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public Integer getDeliveryDay() {
		return deliveryDay;
	}

	public void setDeliveryDay(Integer deliveryDay) {
		this.deliveryDay = deliveryDay;
	}

}
