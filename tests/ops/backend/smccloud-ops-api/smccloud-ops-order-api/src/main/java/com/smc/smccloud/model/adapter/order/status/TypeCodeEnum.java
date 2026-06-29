package com.smc.smccloud.model.adapter.order.status;

import org.apache.commons.lang3.StringUtils;

public enum TypeCodeEnum {

	zxjs("7", "直销寄售"), wtzk("6", "代销"), zcdd("0", "普通");

	private String key;
	private String value;

	private TypeCodeEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public static TypeCodeEnum getKeyByValue(String value) {
		for (TypeCodeEnum deliveryModeEnum : values()) {
			if (StringUtils.equals(deliveryModeEnum.getValue(), value)) {
				return deliveryModeEnum;
			}
		}
		return null;
	}
}
