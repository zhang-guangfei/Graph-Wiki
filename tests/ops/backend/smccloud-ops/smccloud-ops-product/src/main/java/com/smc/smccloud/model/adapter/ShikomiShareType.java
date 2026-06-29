package com.smc.smccloud.model.adapter;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ShikomiShareType {
	// 全球可用
	Global(0, "全球可用"),
	// 中国共享
	China(1, "中国共享"),
	// 多客户共享
	CustomerShare(2, "多客户共享"),
	// 客户专享
	CustomerPrivate(3, "客户专享"),
	// 国内不可用
	ChinaNoUse(4, "国内不可用");

	private int code;
	private String description;

	public String getDescription() {
		return description;
	}

	ShikomiShareType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@JsonValue
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public static ShikomiShareType getEnum(int code) {
		for (ShikomiShareType value : ShikomiShareType.values()) {
			if (value.code == code) {
				return value;
			}
		}
		return null;
	}

}
