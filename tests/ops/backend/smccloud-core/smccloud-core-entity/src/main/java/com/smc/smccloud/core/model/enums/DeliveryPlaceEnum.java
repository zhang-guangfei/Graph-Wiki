package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/4/24 9:59
 * @Descripton TODO
 */

public enum DeliveryPlaceEnum {

	zfyys("2", "直发营业所"), zfkh("1", "直发客户"), zt("3", "自提");
	
	private String code;
	private String codeName;

	private DeliveryPlaceEnum(String code, String codeName) {
		this.code = code;
		this.codeName = codeName;
	}

	public String getCode() {
		return code;
	}

	public String getCodeName() {
		return codeName;
	}

	public static DeliveryPlaceEnum getKeyByValue(String codeName) {
		for (DeliveryPlaceEnum deliveryModeEnum : values()) {
			if (StringUtils.equals(deliveryModeEnum.getCode(), codeName)) {
				return deliveryModeEnum;
			}
		}
		return null;
	}

	public static String getName(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		for (DeliveryPlaceEnum codeName : DeliveryPlaceEnum.values()) {
			if (codeName.code.equals(code)) {
				return codeName.codeName;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String name = getName("1");
		System.out.println("name = " + name);
	}
}
