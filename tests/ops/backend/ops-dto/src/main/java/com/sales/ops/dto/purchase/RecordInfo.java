package com.sales.ops.dto.purchase;

public class RecordInfo {

	// 型号
	private String model;

	// 商品编码
	private String hsCode;

	// 监管条件
	private String supervisionCondition;

	// 检验检疫类别
	private String inspectionType;

	// 商品类型// 产品类别：1冷干机，温控器、干燥机；2法检品/3C产品
	private Integer commodityType;

	// 厂别
	private String plantMark;

	// 物料类型
	private String materialType;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	public String getSupervisionCondition() {
		return supervisionCondition;
	}

	public void setSupervisionCondition(String supervisionCondition) {
		this.supervisionCondition = supervisionCondition;
	}

	public String getInspectionType() {
		return inspectionType;
	}

	public void setInspectionType(String inspectionType) {
		this.inspectionType = inspectionType;
	}

	public Integer getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(Integer commodityType) {
		this.commodityType = commodityType;
	}

	public String getPlantMark() {
		return plantMark;
	}

	public void setPlantMark(String plantMark) {
		this.plantMark = plantMark;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

}
