//package com.smc.smccloud.model.adapter.order.status;
//
//import org.apache.commons.lang3.StringUtils;
//
//public enum RcvclassifyEnum {
//
//	zcdd("1", "正常订单"), airtac("2", "Airtac订单"),
//	festo("4", "Festo订单"), ckd("5", "CKD订单"), gc("6", "国产订单"), qt("7", "其他订单");
//
//	private String key;
//	private String value;
//
//	private RcvclassifyEnum(String key, String value) {
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
//	public static RcvclassifyEnum getKeyByValue(String value) {
//		for (RcvclassifyEnum deliveryModeEnum : values()) {
//			if (StringUtils.equals(deliveryModeEnum.getValue(), value)) {
//				return deliveryModeEnum;
//			}
//		}
//		return null;
//	}
//
//	public static void main(String[] args) {
//		System.out.println(RcvclassifyEnum.getKeyByValue("随到随发").getKey());
//		System.out.println(RcvclassifyEnum.getKeyByValue("货齐出货").getKey());
//	}
//}
