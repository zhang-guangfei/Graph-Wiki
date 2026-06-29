package com.smc.smccloud.model.adapter.order.status;

import org.apache.commons.lang3.StringUtils;

public enum AssembleEnum {

	fa("1", "阀"), ban("2", "板"), zc("0","正常");
	
	private String key;
	private String value;

	private AssembleEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public static AssembleEnum getKeyByValue(String value) {
		for (AssembleEnum assembleEnum : values()) {
			if (StringUtils.equals(assembleEnum.getValue(), value)) {
				return assembleEnum;
			}
		}
		return null;
	}
}
