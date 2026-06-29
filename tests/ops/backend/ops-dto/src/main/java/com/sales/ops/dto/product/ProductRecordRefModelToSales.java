package com.sales.ops.dto.product;

import java.io.Serializable;

public class ProductRecordRefModelToSales implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5385846562958771639L;

	// 正确型号
	private String model;

	// 型号名称
	private String descriptionCustoms;

	// 错误型号：3c型号没加3c或者冷干机没加G
	private String refModel;

	private String status;

	private String deleted;

	private String affirmant;

	// 3C认证品、中文说明书
	private String category;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDescriptionCustoms() {
		return descriptionCustoms;
	}

	public void setDescriptionCustoms(String descriptionCustoms) {
		this.descriptionCustoms = descriptionCustoms;
	}

	public String getRefModel() {
		return refModel;
	}

	public void setRefModel(String refModel) {
		this.refModel = refModel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getAffirmant() {
		return affirmant;
	}

	public void setAffirmant(String affirmant) {
		this.affirmant = affirmant;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
