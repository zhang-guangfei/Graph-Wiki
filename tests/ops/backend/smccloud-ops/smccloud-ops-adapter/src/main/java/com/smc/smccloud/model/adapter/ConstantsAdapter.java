package com.smc.smccloud.model.adapter;

public class ConstantsAdapter {
	/**
	 * 日期格式化yyyyMMdd
	 */
	public static final String DATE_FORMATE = "yyyyMM";

	/**
	 * 垃圾客户标识
	 */
	public static final String LAJI = "0";
	/**
	 * 客户类型:直销，代理，经销
	 */
	// 直销
	public static final String Direct = "0";
	// 代理
	public static final String Agent = "1";
	// 经销
	public static final String Franchiser = "2";
	/**
	 * 发货方式标识
	 */
	// 随到随发
	public static final String DLVENTIRE_ANY = "0";
	// 货期出货
	public static final String DLVENTIRE_FULL = "1";

	/**
	 * 客户代码或者用户代码长度为5位
	 */
	public static final int LENGTH = 5;

	/**
	 * 新增订单，当位数不够时在前面补零
	 */
	public static final String ZEROIZE = "0";
	/**
	 * 新增订单时，反应尾数为9的订单有问题，暂时先跳过
	 */
	public static final String NINE = "9";
	
	/**
	 * map中年的key值
	 */
	public static final String MAP_KEY_YEAR = "year";
	
	/**
	 * map中销售数量的key值
	 */
	public static final String MAP_KEY_SALE_NUM = "saleNum";
	
	/**
	 * 有限公司
	 */
	public static final String CUSTOMERNAME = "有限公司";
}