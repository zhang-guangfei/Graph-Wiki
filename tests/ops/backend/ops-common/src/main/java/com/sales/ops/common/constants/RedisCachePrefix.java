package com.sales.ops.common.constants;

public class RedisCachePrefix {
	
	/**
	 * 模式匹配
	 */
	public final static String REDIS_PATTERN = "*";
	
	/**
	 * 拼接符
	 */
	public final static String REDIS_SPLICER = ":";
	
	/**
	 * 系统所有缓存前缀
	 */
	
	public final static String REDIS_CACHE_SYSTEM_PREFIX = "sale:";
	
	/**
	 * 系统SESSION前缀
	 */

	public final static String REDIS_CACHE_SYSTEM_SESSION = "session:";
	
	/**
	 * 系统SESSION前缀
	 */
	public final static String REDIS_CACHE_SESSION_PREFIX = REDIS_CACHE_SYSTEM_PREFIX + REDIS_CACHE_SYSTEM_SESSION;
	
	/**
	 * 系统SESSION中auth前缀
	 */
	public final static String REDIS_CACHE_SESSION_AUTH_PREFIX = REDIS_CACHE_SESSION_PREFIX + "auth:";
	
	/**
	 * 系统权限前缀（权限缓存分两种：菜单缓存、权限编码缓存）
	 */
	public final static String REDIS_CACHE_AUTHORITY_PREFIX = REDIS_CACHE_SYSTEM_PREFIX + "authority:";
	
	/**
	 * 系统权限所有缓存
	 */
	public final static String REDIS_CACHE_AUTHORITY_ALL = REDIS_CACHE_AUTHORITY_PREFIX + REDIS_PATTERN;
	
	/**
	 * 菜单缓存前缀
	 */
	public final static String REDIS_CACHE_AUTHORITY_MENU = ":menu";
	
	/**
	 * 数据缓存前缀
	 */
	public final static String REDIS_CACHE_AUTHORITY_DATA = ":data";
	
	/**
	 * 所有人的菜单缓存
	 */
	public final static String REDIS_CACHE_AUTHORITY_MENU_ALL = REDIS_CACHE_AUTHORITY_PREFIX + REDIS_PATTERN + REDIS_CACHE_AUTHORITY_MENU;
	
	/**
	 * 所有角色缓存前缀
	 */
	public final static String REDIS_CACHE_AUTHORITY_ROLE = ":role";
	
	/**
	 * 所有角色缓存前缀
	 */
	public final static String REDIS_CACHE_AUTHORITY_ROLE_ALL = REDIS_CACHE_AUTHORITY_PREFIX + "role";
	
	/**
	 * 权限编码缓存前缀
	 */
	public final static String REDIS_CACHE_AUTHORITY_CODE = ":authorityCode";
	
	/**
	 * 缓存失效时间 单位秒
	 */
	public static final int REDIS_LIMIT_TIME = 3600 * 24;
	
	/**
	 * 缓存失效时间 单位秒-12
	 */
	public static final int REDIS_LIMIT_TIME_TWELVE_HOURS = 3600 * 12;

	/**
	 * 默认分页大小
	 **/
	public static final int PAGE_SIZE = 10000;
	/**
	 * 默认查询页码
	 **/
	public static final int PAGE_NUM = 1;
	/**
	 * 缓存失效天数
	 **/
	public static final int REDIS_LIMIT_DAY = 15;
	/**
	 * 缓存永久存活
	 **/
	public static final int PERMANENT_SURVIVAL = -1;
	/**
	 * product全量表数据测试缓存失效天数
	 **/
	public static final int PRODUCT_REDIS_LIMIT_DAY = 4;
	/**
	 * 秒转换为毫秒的转换系数
	 **/
	public static final int SECONDS_TO_MILLISECONDS_RATIO = 1000;

	/**
	 * 时间往前推N年
	 **/
	public static final int EXPIRATION_YEAR = 5;
	/**
	 * 时间往前推N个月
	 **/
	public static final int EXPIRATION_MONTH = 6;
	/**
	 * 贩卖限制品申请生效状态
	 **/
	public static final String RESTRICT_APPLY_SIGNIFICANT_STATUS = "生效中";
	/**
	 * 日期转换格式
	 **/
	public static final String DATE_FORMAT = "YYYY-MM-dd HH:mm:ss";
	/**
	 * HR信息缓存 key值
	 */
	public static final String REDIS_PREFIX_KEY_HR = "hr:";

	/**
	 * 组织机构缓存 key值
	 */
	public static final String ORGAN = REDIS_PREFIX_KEY_HR + "organ:";
	
	/**
	 * 组织机构岗位缓存 key值
	 */
	public static final String POSITION = REDIS_PREFIX_KEY_HR + "position:";

	/**
	 * 组织机构岗位员工缓存 key值
	 */
	public static final String EMPLOYEE = REDIS_PREFIX_KEY_HR + "employee:";
	
	/**
	 * 员工岗位缓存 key值
	 */
	public static final String EMPLOYEE_POSITION = REDIS_PREFIX_KEY_HR + "employee:position:";

	/**
	 * 型号校验，特殊型号 key值
	 */
	public static final String SPECIAL_MODEL = REDIS_CACHE_SYSTEM_PREFIX + "product:specialModel";

	/**
	 * 客户信息，特殊型号 key值
	 */
	public static final String SALE_CUSTOMER = REDIS_CACHE_SYSTEM_PREFIX + "customer:";

	/**
	 * 计数器 key值
	 */
	public static final String SALE_COUNTER = REDIS_CACHE_SYSTEM_PREFIX + "counter:";

	/**
	 *分布式锁
	 */
	public static final String REDIS_SETNX_PREX = REDIS_CACHE_SYSTEM_PREFIX + "setnx:";

	/**
	 * 支付方式选项
	 */
	public static final String PAYMENT_METHODS = REDIS_CACHE_SYSTEM_PREFIX + "payment:methods:all";
	/**
	 * 门户系统待办任务
	 */
	public static final String SYS_TODO_URL = REDIS_CACHE_SYSTEM_PREFIX + "sysTodoUrl:all";

	/**
	 * 系统税率缓存
	 */
	public final static String REDIS_CACHE_TAXRATE = REDIS_CACHE_SYSTEM_PREFIX + "taxRate:last";


	/**
	 * 客户信息缓存前缀
	 */

	public final static String REDIS_CACHE_SYSTEM_CUSTOMER = REDIS_CACHE_SYSTEM_PREFIX + "customer:";

	/**
	 * SMC所有分公司交易主体前缀
	 */
	public final static String REDIS_CACHE_SYSTEM_CUSTOMER_COMPANY = REDIS_CACHE_SYSTEM_CUSTOMER + "groupCompany:";

	/**
	 * PA接口APIkey
	 */
	public static final String PAN_OS_API_KEY = REDIS_CACHE_SYSTEM_PREFIX + "psnos:apikey";

	/**
	 *频次
	 */
	public static final String FREQUENCY = REDIS_CACHE_SYSTEM_PREFIX + "frequency:";

}
