package com.sales.ops.dto.delivery;

import java.math.BigDecimal;

public class OpsCalPoModelBase {

	private String modelNo;

	private String supplierId;

	private String supplierClass;

	private BigDecimal netWeight;

	private String nonstandardSizedProduct;

	private Integer productType;

	private Boolean air;

	private Boolean ship;

	private Integer shipQty;

	private String designId;

	// 标准品，特注品
	private String designName;

	private String supplierName;

	private String typeClassId;

	private Boolean comparegreater;

	private Integer comparequantity;

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierClass() {
		return supplierClass;
	}

	public void setSupplierClass(String supplierClass) {
		this.supplierClass = supplierClass;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	public String getNonstandardSizedProduct() {
		return nonstandardSizedProduct;
	}

	public void setNonstandardSizedProduct(String nonstandardSizedProduct) {
		this.nonstandardSizedProduct = nonstandardSizedProduct;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Boolean getAir() {
		return air;
	}

	public void setAir(Boolean air) {
		this.air = air;
	}

	public Boolean getShip() {
		return ship;
	}

	public void setShip(Boolean ship) {
		this.ship = ship;
	}

	public Integer getShipQty() {
		return shipQty;
	}

	public void setShipQty(Integer shipQty) {
		this.shipQty = shipQty;
	}

	public String getDesignId() {
		return designId;
	}

	public void setDesignId(String designId) {
		this.designId = designId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

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

	public Boolean getComparegreater() {
		return comparegreater;
	}

	public void setComparegreater(Boolean comparegreater) {
		this.comparegreater = comparegreater;
	}

	public Integer getComparequantity() {
		return comparequantity;
	}

	public void setComparequantity(Integer comparequantity) {
		this.comparequantity = comparequantity;
	}

}
