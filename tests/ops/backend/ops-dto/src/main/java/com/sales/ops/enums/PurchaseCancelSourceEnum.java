package com.sales.ops.enums;

import java.util.Arrays;
import java.util.List;

public enum PurchaseCancelSourceEnum {
	CANCEL_REQUEST_PURCHASE("-2", "请购单删单页面删除"),
	CANCEL_PURCHASE("-1", "采购单删单页面删除"),
	CANCEL_CUSTOMER_ORDER("0", "客单删单处理界面，自动删除采购单"),
	CANCEL_CUSTOMER_ORDER_AUTO("1", "客单删单处理界面，手动删除采购单"),
	RESET_CUSTOMER_ORDER("2", "订单还原删除采购单"),
	ZD_CUSTOMER_ORDER("3", "转定删除采购单"),
	FINISH_PURCHASE("4", "完纳删除采购单");

	private String type;
	private String desc;

	PurchaseCancelSourceEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	// 需要重置客单的操作
	public static boolean needResetRcv(PurchaseCancelSourceEnum source) {
		List<PurchaseCancelSourceEnum> list = Arrays.asList(CANCEL_REQUEST_PURCHASE, CANCEL_PURCHASE);
		return list.contains(source);
	}


	public static PurchaseCancelSourceEnum parse(String type){
		return Arrays.stream(PurchaseCancelSourceEnum.values()).filter(item -> item.getType().equals(type)).findFirst().orElse(null);
	}

}
