package com.sales.ops.enums;

/**
 * 请购单类别
 */
public enum PurchaseTypeEnum {

	SALE("A", "销售订单"),
	BIN("K", "BIN补库订单"),
	PRE("B", "先行在库订单"),
	SPEED("U", "加急订单");

	private String TypeCode;
	private String TypeDesc;

	PurchaseTypeEnum(String typeCode, String typeDesc) {
		TypeCode = typeCode;
		TypeDesc = typeDesc;
	}

	public String getTypeCode() {
		return TypeCode;
	}

	public void setTypeCode(String typeCode) {
		TypeCode = typeCode;
	}

	public String getTypeDesc() {
		return TypeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		TypeDesc = typeDesc;
	}

}
