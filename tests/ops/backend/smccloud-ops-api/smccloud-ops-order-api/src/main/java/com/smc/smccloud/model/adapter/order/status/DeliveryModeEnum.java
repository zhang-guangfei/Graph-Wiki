//package com.smc.smccloud.model.adapter.order.status;
//
//import org.apache.commons.lang3.StringUtils;
//
//public enum DeliveryModeEnum {
//
//	SDSF("0", "随到随发"), HQCH("1", "货齐出货");
//
//	private String key;
//	private String value;
//
//	private DeliveryModeEnum(String key, String value) {
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
//	public static DeliveryModeEnum getKeyByValue(String value) {
//		for (DeliveryModeEnum deliveryModeEnum : values()) {
//			if (StringUtils.equals(deliveryModeEnum.getValue(), value)) {
//				return deliveryModeEnum;
//			}
//		}
//		return null;
//	}
//
//	public static DeliveryModeEnum getValueByKey(String key) {
//		for (DeliveryModeEnum deliveryModeEnum : values()) {
//			if (StringUtils.equals(deliveryModeEnum.getKey(), key)) {
//				return deliveryModeEnum;
//			}
//		}
//		return null;
//	}
//
//	public static void main(String[] args) {
//		System.out.println(DeliveryModeEnum.getKeyByValue("随到随发").getKey());
//		System.out.println(DeliveryModeEnum.getKeyByValue("货齐出货").getKey());
//	}
//}
