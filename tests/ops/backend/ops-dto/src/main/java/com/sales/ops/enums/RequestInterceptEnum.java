package com.sales.ops.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * bug12272 请购拦截，拦截原因字典
 */
public enum RequestInterceptEnum {

	PRODUCTINFO("获取型号产品信息失败", "获取型号产品信息失败"), PRODECTPRICE("型号价格获取失败", "型号价格获取失败"), UNSUPPLIER("无法获取该型号供应商信息",
			"无法获取该型号供应商信息"), NOINFO("未登录型号", "未登录型号");

	RequestInterceptEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	// 定义需要重新计算的原因清单
	public static List<String> restoreList() {
		List<String> list = new ArrayList<String>();
		list.add(PRODUCTINFO.getCode());
		list.add(PRODECTPRICE.getCode());
		list.add(UNSUPPLIER.getCode());
		list.add(NOINFO.getCode());
		return list;
	}

	private String code;
	private String desc;

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

}
