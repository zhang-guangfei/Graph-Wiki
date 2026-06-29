package com.sales.ops.enums;

public class PurchaseOrderStatusEnum {
	public static final String DAICHULI = "0";
	public static final String YIFASONG = "1";
	// 供应商已接单
	public static final String YIJIEDAN = "2";
	// 运输中
	public static final String YUNSHUZHONG = "3";
	public static final String YIWANCHENG = "5";
	// 强制完纳状态
	public static final String QIANGZHIWANNA = "6";
	// 转定后删除状态
	public static final String ZHAUNDINGSHANCHU = "7";
	public static final String SHUJUYICHANG = "8";
	public static final String SHANCHU = "9";
	// 发票入库缺失采购补充数据
	public static final String BUCHONG = "A";
	// bug8614 发票入库时新增采购单对应多到货或无采购单
	public static final String ADDNEW = "B";
}
