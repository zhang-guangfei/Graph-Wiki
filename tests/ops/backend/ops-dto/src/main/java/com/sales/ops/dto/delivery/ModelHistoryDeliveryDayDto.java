package com.sales.ops.dto.delivery;

public class ModelHistoryDeliveryDayDto {

	private String modelNo;

	private Integer qtyStart;

	private Integer qtyEnd;

	private Integer deliveryDay;

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public Integer getQtyStart() {
		return qtyStart;
	}

	public void setQtyStart(Integer qtyStart) {
		this.qtyStart = qtyStart;
	}

	public Integer getQtyEnd() {
		return qtyEnd;
	}

	public void setQtyEnd(Integer qtyEnd) {
		this.qtyEnd = qtyEnd;
	}

	public Integer getDeliveryDay() {
		return deliveryDay;
	}

	public void setDeliveryDay(Integer deliveryDay) {
		this.deliveryDay = deliveryDay;
	}

}
