package com.smc.smccloud.model.adapter.order.status;

import org.apache.commons.lang3.StringUtils;

public enum FreightPayer {

	smcBurden("0", "SMC负担"), customerBurden("1", "客户负担");
	
	private String key;
	private String value;

	private FreightPayer(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public static FreightPayer getKeyByValue(String value) {
		for (FreightPayer freightPayer : values()) {
			if (StringUtils.equals(freightPayer.getValue(), value)) {
				return freightPayer;
			}
		}
		return null;
	}
}
