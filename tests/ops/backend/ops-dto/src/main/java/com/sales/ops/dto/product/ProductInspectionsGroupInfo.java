package com.sales.ops.dto.product;

import java.io.Serializable;

public class ProductInspectionsGroupInfo implements Serializable {

	/**
	 * 关务返回型号相关信息
	 */
	private static final long serialVersionUID = 5847149994765149088L;

	// 厂别
	private String plantmark;

	private String model;

	// HSCODE
	private String tariffcode;

	// 检验检疫类别
	private String inspections;

	// 产品类别：1冷干机，温控器、干燥机；2法检品/3C产品
	private String inspectionsGroupId;

	// bug10654马腾 增加监管条件
	private String credentials;

	public String getPlantmark() {
		return plantmark;
	}

	public void setPlantmark(String plantmark) {
		this.plantmark = plantmark;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getTariffcode() {
		return tariffcode;
	}

	public void setTariffcode(String tariffcode) {
		this.tariffcode = tariffcode;
	}

	public String getInspections() {
		return inspections;
	}

	public void setInspections(String inspections) {
		this.inspections = inspections;
	}

	public String getInspectionsGroupId() {
		return inspectionsGroupId;
	}

	public void setInspectionsGroupId(String inspectionsGroupId) {
		this.inspectionsGroupId = inspectionsGroupId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

}
