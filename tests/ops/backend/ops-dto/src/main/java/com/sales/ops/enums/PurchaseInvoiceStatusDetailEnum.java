package com.sales.ops.enums;

import org.checkerframework.checker.units.qual.A;

public class PurchaseInvoiceStatusDetailEnum {
	// 采购业务已发单
	public static final String YIFADAN = "10";
	// 供应商已接单
	public static final String YIJIEDAN = "20";
	// 供应商接单处理
	public static final String JIEDAN_CHULI = "21";
	// 供应商生产中
	public static final String SHENGCHANZHONG = "22";
	// 供应商完工出厂（H、W）
	public static final String WANGONG_CHUCHANG = "23";
	//供应商完工发出（A）
	public static final String WANGONG_FACHU = "30";
	//已报关
	public static final String YIBAOGUAN = "31";
	//发票数据入库
	public static final String FAPINGSHURU = "32";
	//营业物流签收
	public static final String YINGYONG_WULOGUANQIANSHOU = "50";
	//已发票入库
	public static final String YIFAPIAORUKU = "3";
}
