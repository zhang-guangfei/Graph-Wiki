package com.sales.ops.dto.purchase;

public class PurchaseDayDto {

	//bugid:17143 请购状态未生产供应商代码
	private Boolean existSupplier = true;

	// 生产天数
	private Integer produceDay;

	// 生产天数是否为工作日
	private Boolean produceWorkDay;

	// 运输天数
	private Integer transDay;

	// 运输天数是否为工作日
	private Boolean transWorkDay;

	public Integer getProduceDay() {
		return produceDay;
	}

	public void setProduceDay(Integer produceDay) {
		this.produceDay = produceDay;
	}

	public Boolean getProduceWorkDay() {
		return produceWorkDay;
	}

	public void setProduceWorkDay(Boolean produceWorkDay) {
		this.produceWorkDay = produceWorkDay;
	}

	public Integer getTransDay() {
		return transDay;
	}

	public void setTransDay(Integer transDay) {
		this.transDay = transDay;
	}

	public Boolean getTransWorkDay() {
		return transWorkDay;
	}

	public void setTransWorkDay(Boolean transWorkDay) {
		this.transWorkDay = transWorkDay;
	}

	public Boolean getExistSupplier() {
		return existSupplier;
	}

	public void setExistSupplier(Boolean existSupplier) {
		this.existSupplier = existSupplier;
	}
}
