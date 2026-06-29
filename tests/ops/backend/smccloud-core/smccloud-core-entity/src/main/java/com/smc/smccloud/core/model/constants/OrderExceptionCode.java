package com.smc.smccloud.core.model.constants;

public class OrderExceptionCode extends BaseExceptionCode {
	/**
	 * 已经存在相同ID
	 */
	public static String THE_SAME_ID_EXISTING = "300000001";
	/**
	 * 客户代码不存在
	 */
	public static String NOT_EXIST_CUSTOMERNO = "300000002";
	/**
	 * 此客户非有效请更改状态后使用
	 */
	public static String INVALID_CUSTOMER = "300000003";
	/**
	 * 代理必须输入最终用户
	 */
	public static String END_USER_NEED = "300000004";
	/**
	 * 最终用户不能报价
	 */
	public static String END_USER_CANOOT_QUOTE = "300000005";
	/**
	 * 用户代码不正确或者不是该客户的用户
	 */
	public static String USERNO_ERROR_BLONG = "300000006";
	/**
	 * 直销客户请勿输入最终客户
	 */
	public static String DIRECTCUSTOMER_NOT_END_USER = "300000007";
	/**
	 * 代理必须选择随到随发的出货标识
	 */
	public static String EXPFLAG_ERROR = "300000008";
	/**
	 * 客户代码必须为5位
	 */
	public static String CUSTOMER_ERROR = "300000009";
	/**
	 * 用户代码必须为5位
	 */
	public static String USERNO_ERROR = "300000010";
	/**
	 * 该客户是输出限制客户无法更新
	 */
	public static String CUSTOMER_FORBIDDEN = "300000012";
	/**
	 * 该用户是输出限制用户无法更新
	 */
	public static String USER_FORBIDDEN = "300000013";
	/**
	 * 输入的用户代码不存在
	 */
	public static String NOT_EXIST_USERNO = "300000014";
	/**
	 * 该客户有代理不允许订货
	 */
	public static String CUSTOMER_ORDADMIT = "300000015";
	/**
	 * 出货方式无效或不允许选择该出货方式
	 */
	public static String INVALID_DLVTYPE = "300000016";
	/**
	 * 不可更改出货方式
	 */
	public static String CANNOT_CHANGE_DLVTYPE = "300000018";
	/**
	 * 不可更改物流货期
	 */
	public static String CANNOT_CHANGE_DLVDATE = "300000019";
	/**
	 * 未能成功插入OrderSales
	 */
	public static String INSERT_ORDERSALES_ERROR = "300000020";
	/**
	 * 不存在对应的两位部门代码
	 */
	public static String NOT_EXIST_DEPTNO = "300000021";
	/**
	 * 部门代码为空或不是6位码
	 */
	public static String NULL_HEXA_DEPTNO = "300000022";
	/**
	 * 订单无子项
	 */
	public static String NO_ORDER_ITEM = "300000023";
	/**
	 * 状态不可修改发货信息
	 */
	public static String HAS_ITEM_INVALID = "300000024";
	/**
	 * 日期应大于今天！
	 */
	public static String DATE_SHOULD_BEFORE_TODAY = "300000025";
	/**
	 * 参数应为十位接单号！
	 */
	public static String ORDERNO_SHOULD_BE_TEN = "300000026";
	/**
	 * 交货期修改为今天的操作，需在下午两点前进行；日期不得设置为六个月后！
	 */
	public static String CHANGE_DELIVERYDATE_INVALID = "300000027";
	/**
	 * remark中间字段：营业所更改物流发货日期为
	 */
	public static String REMARK = "300000028";
	/**
	 * 订单号不可催促
	 */
	public static String CANNOT_PRESS = "300000029";
	/**
	 * 发货期不可为空
	 */
	public static String DELIVERY_CANT_EMPTY = "300000030";
	/**
	 * 订单不存在
	 */
	public static String ORDER_NOT_EXIST = "300000031";
	/**
	 * 客户代码不能为空
	 */
	public static String CUSTOMERNO_IS_EMPTY = "300000032";
	/**
	 * 部门所属区域不能为空
	 */
	public static String DEPT_AREA_IS_EMPTY = "300000033";
	/**
	 * 部门所属区域错误
	 */
	public static String DEPT_AREA_IS_ERROR = "300000034";
	/**
	 * 型号信息查询失败
	 */
	public static String MODELNO_IS_ERROR = "300000035";
}
