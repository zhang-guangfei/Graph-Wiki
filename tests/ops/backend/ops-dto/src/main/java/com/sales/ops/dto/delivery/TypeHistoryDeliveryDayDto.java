package com.sales.ops.dto.delivery;

public class TypeHistoryDeliveryDayDto {

	// 产品系列
	private String typeClassId;

	// 产品属性：标准品、特注品
	private String designName;

	private Integer qtyStart;

	private Integer qtyEnd;

	private Integer deliveryDay;

	public String getTypeClassId() {
		return typeClassId;
	}

	public void setTypeClassId(String typeClassId) {
		this.typeClassId = typeClassId;
	}

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
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
