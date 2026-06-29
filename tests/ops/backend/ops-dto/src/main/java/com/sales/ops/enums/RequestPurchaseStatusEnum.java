package com.sales.ops.enums;

public class RequestPurchaseStatusEnum {

	// 待处理
	public static final String DAICHULI = "0";

	// 预处理完毕，处理中
	public static final String CHULIZHONG = "1";

	// 进入待采购状态，不可删除
	public static final String DAICAIGOU = "2";

	// 已发送采购
	public static final String YIFASONG = "3";

	// 业务拦截状态，可放行至采购
	public static final String LANJIE = "4";

	// shikomi拦截，放行后进入待处理状态，重新处理
	public static final String SHIKOMILANJIE = "5";

	// 已完成
	public static final String YIWANCHENG = "6";

	// bug11473自动发单 状态:暂不处理
	public static final String ZANBUCHULI = "7";

	// 取消
	public static final String QUXIAO = "9";

	// bug7519转订删除
	public static final String ZHAUNDINGSHANCHU = "8";

	// bug8614 发票入库时新增采购单对应多到货或无采购单
	public static final String ADDNEW = "B";

	// bug11473自动发单 增加状态：已进入自动发单流程，不可删除
	public static final String AUTOREADY = "C";

}
