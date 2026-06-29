package com.sales.ops.enums;

import java.util.HashMap;
import java.util.Map;

public class OrderTypeEnum {
	public static final String YANGPIN = "9";//样品订单
	public static final String KEHU = "1"; 	//销售订单
	public static final String YIBANMAOYI = "12";	//一般贸易订单
	public static final String PRE = "21";	//先行补库订单
	public static final String BIN = "20";	//BIN补库订单
	public static final String DR = "24";	//DR补库订单
	public static final String CR = "25";	//CR补库订单
	public static final String WT = "3";	//委托在库补货订单

	public static final String JITUAN = "11";	//国内集团销售订单


	public static final Map<String, Boolean> KUCUNDINGDAN = new HashMap<String, Boolean>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6144909248180538486L;

		{
			put("20", true);
			put("21", true);
		}
	};
}
