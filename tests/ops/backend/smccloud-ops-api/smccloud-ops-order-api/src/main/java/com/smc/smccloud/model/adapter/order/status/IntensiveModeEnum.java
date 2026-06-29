//package com.smc.smccloud.model.adapter.order.status;
//
//import org.apache.commons.lang3.StringUtils;
//
//public enum IntensiveModeEnum {
//
//	dzjy("0", "地址集约"), ddjy("1", "订单集约");
//
//	private String key;
//	private String value;
//
//	private IntensiveModeEnum(String key, String value) {
//		this.key = key;
//		this.value = value;
//	}
//
//	public String getKey() {
//		return key;
//	}
//
//	public String getValue() {
//		return value;
//	}
//
//	public static IntensiveModeEnum getKeyByValue(String value) {
//		for (IntensiveModeEnum deliveryModeEnum : values()) {
//			if (StringUtils.equals(deliveryModeEnum.getValue(), value)) {
//				return deliveryModeEnum;
//			}
//		}
//		return null;
//	}
//
//
//
//	public static IntensiveModeEnum getValueByKey(String key) {
//		for (IntensiveModeEnum deliveryModeEnum : values()) {
//			if (StringUtils.equals(deliveryModeEnum.getKey(), key)) {
//				return deliveryModeEnum;
//			}
//		}
//		return null;
//	}
//
//	public static void main(String[] args) {
//		System.out.println(IntensiveModeEnum.getKeyByValue("随到随发").getKey());
//		System.out.println(IntensiveModeEnum.getKeyByValue("货齐出货").getKey());
//	}
//}
