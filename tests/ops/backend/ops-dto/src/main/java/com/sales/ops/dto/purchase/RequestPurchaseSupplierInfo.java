package com.sales.ops.dto.purchase;

import java.io.Serializable;

public class RequestPurchaseSupplierInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2077805012185109100L;

	private String modelNo;
	private String supplyId;
	private String warehouseCode;
	private Integer maxProdQty;
	private Boolean enableMaxProdQty;
	private String matchPattern;
	private Integer day;
	private Integer warehousePriority;
	private Integer supplierPriority;
	private String supplierPartNo;

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public Integer getMaxProdQty() {
		return maxProdQty;
	}

	public void setMaxProdQty(Integer maxProdQty) {
		this.maxProdQty = maxProdQty;
	}

	public Boolean getEnableMaxProdQty() {
		return enableMaxProdQty;
	}

	public void setEnableMaxProdQty(Boolean enableMaxProdQty) {
		this.enableMaxProdQty = enableMaxProdQty;
	}

	public String getMatchPattern() {
		return matchPattern;
	}

	public void setMatchPattern(String matchPattern) {
		this.matchPattern = matchPattern;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getWarehousePriority() {
		return warehousePriority;
	}

	public void setWarehousePriority(Integer warehousePriority) {
		this.warehousePriority = warehousePriority;
	}

	public Integer getSupplierPriority() {
		return supplierPriority;
	}

	public void setSupplierPriority(Integer supplierPriority) {
		this.supplierPriority = supplierPriority;
	}

	public String getSupplierPartNo() {
		return supplierPartNo;
	}

	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
