package com.sales.ops.enums;

import java.util.*;

/**
 * @author B91717
 * @date 2023-09-11
 *  采购删单，枚举类
 */
public enum PurchaseDeleteEnum {

	LOWRISK(1, "采购低风险"),
	HIGHRISK(2, "采购高风险"),
	MAXDATE(30,"限制天数");

	PurchaseDeleteEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static InventoryStatusEnum getEnumByType(String type) {
		return Arrays.stream(InventoryStatusEnum.values()).filter(Enum -> Enum.getCode().equals(type)).findFirst().orElse(null);
	}

	// 可以进行风险判断的状态
	public static Set<String> statusSet() {
		Set set = new HashSet();
		set.add(PurchaseOrderStatusEnum.YIJIEDAN);
		set.add(PurchaseOrderStatusEnum.YUNSHUZHONG);
		set.add(PurchaseOrderStatusEnum.YIWANCHENG);
		return set;
	}

	private Integer code;
	private String desc;

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

}
