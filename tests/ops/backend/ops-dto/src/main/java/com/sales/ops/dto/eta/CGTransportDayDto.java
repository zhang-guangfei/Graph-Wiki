package com.sales.ops.dto.eta;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $ @description：
 * @date ：Created in 2023/11/22 8:57
 */
public class CGTransportDayDto implements Serializable {

	private static final long serialVersionUID = -1316227041063349063L;

	// 型号
	private String modelNo;

	// 运输方式
	private String transportWayId;

	// 运输方式名称
	private String transportWayName;

	// 运输天数
	private Integer transportDay = 0;

	// 生产天数
	private Integer produceDay = 0;

	// 采购参考天数=生产+运输
	private Integer deliveryDay;

	// 供应商
	private String supplierId;

	// 供应商名称
	private String supplierName;

	// 是否出供应商库存
	private boolean isSupplierInventory;

	// 供应商库存数
	private Integer supplierInventory;

	// 采购入库天数
	private Integer roDays = 1;

	// 错误信息
	private String msg;

	// 计算详情
	private String calDesc;

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getTransportWayId() {
		return transportWayId;
	}

	public void setTransportWayId(String transportWayId) {
		this.transportWayId = transportWayId;
	}

	public String getTransportWayName() {
		return transportWayName;
	}

	public void setTransportWayName(String transportWayName) {
		this.transportWayName = transportWayName;
	}

	public Integer getTransportDay() {
		return transportDay;
	}

	public void setTransportDay(Integer transportDay) {
		this.transportDay = transportDay;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public boolean isSupplierInventory() {
		return isSupplierInventory;
	}

	public void setSupplierInventory(boolean isSupplierInventory) {
		this.isSupplierInventory = isSupplierInventory;
	}

	public Integer getSupplierInventory() {
		return supplierInventory;
	}

	public void setSupplierInventory(Integer supplierInventory) {
		this.supplierInventory = supplierInventory;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getProduceDay() {
		return produceDay;
	}

	public void setProduceDay(Integer produceDay) {
		this.produceDay = produceDay;
	}

	public Integer getDeliveryDay() {
		return deliveryDay;
	}

	public void setDeliveryDay(Integer deliveryDay) {
		this.deliveryDay = deliveryDay;
	}

	public Integer getRoDays() {
		return roDays;
	}

	public void setRoDays(Integer roDays) {
		this.roDays = roDays;
	}

	public String getCalDesc() {
		return calDesc;
	}

	public void setCalDesc(String calDesc) {
		this.calDesc = calDesc;
	}

}
