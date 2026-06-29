package com.sales.ops.dto.delivery;

public class OpsCalPoSupplierWarehouseTransBase {

	private String supplierClass;

	private String warehouseCode;

	private String transType;

	private Integer transDay;

	private String transName;

	public String getSupplierClass() {
		return supplierClass;
	}

	public void setSupplierClass(String supplierClass) {
		this.supplierClass = supplierClass;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Integer getTransDay() {
		return transDay;
	}

	public void setTransDay(Integer transDay) {
		this.transDay = transDay;
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

}
