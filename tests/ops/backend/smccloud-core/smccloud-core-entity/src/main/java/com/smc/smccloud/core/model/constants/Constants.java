package com.smc.smccloud.core.model.constants;

public class Constants {

    public static final String VALID = "有效";
    /**
     * Holon在TreeInfo中的level值
     */
    public static final String HR_HOLON = "HL";

    public static final String HR_YYS = "课(营业所)";

    public static final String RESOURCE = "资源";

    public static final String MENU = "菜单";

    public static final String AUTHORITY = "权限";

    public static final String GROUP = "用户组";

    public static final String ROLE = "角色";

    public final static int CODE_DIGIT = 3;


    public final static String JWT_TOKEN_KEY = "smccn889";

    /**
     * 日期格式化yyyyMMdd
     */
    public static final String DATE_FORMATE = "yyyyMM";

    /**
     * REDIS缓存:系统前缀
     */

    public final static String REDIS_CACHE_SYSTEM_PREFIX = "ops:";

    public final static String REDIS_AUTH_CACHE = "authorityCache:";

    /**
     * HR信息缓存 key值
     */
    public static final String REDIS_PREFIX_KEY_HR = REDIS_CACHE_SYSTEM_PREFIX + "hr:";

    /**
     * 组织机构缓存 key值
     */
    public static final String REDIS_KEY_ORGAN = REDIS_PREFIX_KEY_HR + "organ:";
    public static final String REDIS_KEY_ORGAN_CODE = REDIS_PREFIX_KEY_HR + "organ:CODE:";

    public static final String REDIS_KEY_ORGAN_SALES = REDIS_PREFIX_KEY_HR + "organ:sales:";
    public static final String REDIS_KEY_ORGAN_REGION = REDIS_PREFIX_KEY_HR + "organ:region:";

    /**
     * 组织机构岗位缓存 key值
     */
    public static final String REDIS_KEY_ORGAN_POSITION = REDIS_CACHE_SYSTEM_PREFIX + "organ:position";
    /**
     * 上层层级关系缓存 key值
     */
    public static final String REDIS_KEY_PARENT_POSITION = REDIS_CACHE_SYSTEM_PREFIX + "organ:position:workflow:";

    /**
     * 组织机构岗位员工缓存 key值
     */
    public static final String REDIS_KEY_ORGAN_POSITION_EMPLOYEE = REDIS_CACHE_SYSTEM_PREFIX + "organ:position:employee";
    /**
     * 组织机构岗位员工缓存 key值
     */
    public static final String REDIS_KEY_ORGAN_BRANCH_FIRSTWAREHOUSE = REDIS_CACHE_SYSTEM_PREFIX + "organ:branch:firstwarehouse:";

    /**
     * 组织机构相关缓存失效时间 单位秒
     */
    public static final int REDIS_LIMIT_TIME_ORGAN = 3600;

    /**
     * REDIS缓存:SESSION前缀
     */

    public final static String REDIS_CACHE_SESSION_PREFIX = REDIS_CACHE_SYSTEM_PREFIX + "session:cache:";

    /**
     * REDIS缓存: 客户订单号
     */
    public final static String ORDERSALES_CUSTOMER_ORDERNO = REDIS_CACHE_SYSTEM_PREFIX + "orderSales:customerOrderNos";

    public final static String SALES_ORDER_NO = REDIS_CACHE_SYSTEM_PREFIX + "orderSales:orderNo"; // bug-8874

    /**
     * REDIS缓存: 预计到达日期
     */
    public final static String ORDER_ESARRIVADATE = REDIS_CACHE_SYSTEM_PREFIX + "orderEsArrivalDate:date";


    public static final String IMG = "iVBORw0KGgoAAAANSUhEUgAAAMkAAAA6CAIAAACVourlAAAUAklEQVR4Ae2dZZAlNw6As8kGL8zMzKkwMzMzM1eYmXNhZqYKVJiZmZmZmTm57+JXXq1sqd3dM5Pdqnk/pmy1LctuWRa5p8/ff/89SLvf77///uabb7788ssvvfTSa6+99vbbb3/wwQdffPHF999/HxAPOuigI4wwwmijjTbeeONNPPHEU0455VRTTcXfiSaaqN3Ivb0H0BX47rvvhh9++D6Neevbb7+9/fbbb7jhhvvvv/+tt96qO0sYbuqpp15ggQWWW245/g4++OB1MfS2H2BXoDlvvfjii2edddbll1/+0Ucfdcn0pphiinXWWWfDDTccZ5xxugRhL5J/dwUCbw2C3Cr/vfDCC+utt17fvn27g/QRRxxx9913/+qrr8rp6W05gKzAL7/88v777//888+BHs40CqVn4tdff33QQQedfPLJv/32W3cwVsQ54YQTHnrooWuuuWaE9BYG8BWAKz777DMULDRstOqtt966xpl49dVX77DDDu+++26PTXLVVVc97rjjxh577B4bsXegZitw2GGHwVKjjjrqcMMNx4H2+uuvjz766BtssAGnUIXc+uOPP3bccccTTzyx2cBteo011linnnrq8ssv3waJ1ReJjTH7zjvvIMnZc9988w1SncaYFCwKqxNMWv726dPHQjLQwXmbWPEY9cz6888/hydYhzjl8ccfH8udv+Xz+uSTT7bddttLL72Uc2bnnXf+7z8/yjfffPNII43kaU70XH311e+7777ywbqw5ccff7zCCivst99++++/f1ehRQ+49dZbb7nllkcffRTbNvCThXzIIYecZJJJ5pprrmWWWWbRRRcdZphhVEucLyzrjz/+WMl/f/31Fxgmm2wyhcGvXnbZZWifGNROs6DfLLXUUugSVjOQMOUbb7zxscceg7f+/PNPq+VQQw3FlOecc04QLrLIIogiq2WAoykFcTXYYIOddtpp008/PdwJSZ1egbj076uvvjrppJP6qHvmKdYDy5FSWAuClNp1112Rhc1o5s0dcMABX375pRwUrQL+K0S40UYbyb6V5VoayBVXXJFFiLtxu+22QwwXEimbjTvuuJhWiPYs5gBE+V5jjTUefvjho446CkX+hBNOoLzkkksGgyxvJ77yyitd6w7gJP6P+A09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\n";

    public final static String CSTO_USER_INFO = REDIS_CACHE_SYSTEM_PREFIX + "salesPortal:customerAndUser";


    /**
     * 根据仓库代码获取仓库名称Redis缓存
     */
    public static final String REDIS_WAREHOUSE_NAME_BY_CODE = REDIS_CACHE_SYSTEM_PREFIX + "warehouse:name";
    /**
     * 仓库信息
     */
    public static final String REDIS_WAREHOUSE_INFO_ALL= REDIS_CACHE_SYSTEM_PREFIX + "warehouse:allWarehouseInfo";
    public static final String REDIS_BRANCH_FIRST_WAREHOUSE_ALL = REDIS_CACHE_SYSTEM_PREFIX + "warehouse:allFirstWarehouse:";
    public static final String REDIS_BRANCH_FIRST_WAREHOUSE_ALL_MASTER = REDIS_CACHE_SYSTEM_PREFIX + "warehouse:allFirstWarehouseMaster:";
    /**
     * 根据仓库代码获取仓库类型Redis缓存
     */
    public static final String REDIS_WAREHOUSE_TYPE_BY_CODE = REDIS_CACHE_SYSTEM_PREFIX + "warehouse:type";
    /**
     * 根据仓库代码获取仓库类型Redis缓存
     */

    /**
     * 根据仓库类型获取仓库代码列表
     */
    public static final String REDIS_WAREHOUSE_CODES_BY_TYPE = REDIS_CACHE_SYSTEM_PREFIX + "warehouse:codes:";
    /**
     * 根据仓库类型获取分仓库代码列表
     */
    public static final String REDIS_WAREHOUSE_CODES_FOR_SUB = REDIS_CACHE_SYSTEM_PREFIX + "warehouse:subcodes:";
    /**
     * 物流中心仓库代码（顺序）集合
     */
    public static final String REDIS_MASTER_WAREHOUSE_CODES_IN_ORDER = REDIS_CACHE_SYSTEM_PREFIX + "warehouse:order:masterCodes";
    /**
     * 调拨补库（顺序）集合
     */
    public static final String REDIS_TRANS_WAREHOUSE_BY_WAREHOUSE_CODE = REDIS_CACHE_SYSTEM_PREFIX + "warehouse:trans:";
    /**
     * 客户信息Redis缓存
     */
    public static final String REDIS_ALL_CUSTOMER_INFO = REDIS_CACHE_SYSTEM_PREFIX + "allCustomerInfo";

    public static final String REDIS_CUSTOMER_NAME = REDIS_CACHE_SYSTEM_PREFIX + "customer:customerName";

    // key 编码   val 承运商名称
    public static final String REDIS_CARRIER_NAME = REDIS_CACHE_SYSTEM_PREFIX + "carrier:name";
    // key 承运商名称  val 编码
    public static final String REDIS_CARRIER_CODE = REDIS_CACHE_SYSTEM_PREFIX + "carrier:code";

    public static final String REDIS_CUSTOMER_ENAME_BY_NO = REDIS_CACHE_SYSTEM_PREFIX + "customer:customerEName:";

    public static final String REDIS_CUSTOMER_BY_NO_OR_NAME = REDIS_CACHE_SYSTEM_PREFIX + "customer:customerNoOrName";

    /**
     * 部门信息Redis缓存
     */
    public static final String REDIS_DEPARTMENT_NAME_ALL = REDIS_CACHE_SYSTEM_PREFIX + "department:deptName";

    /**
     * 员工信息Redis缓存 职工对象
     */
    public static final String REDIS_EMPLOYEE_INFO_ALL = REDIS_CACHE_SYSTEM_PREFIX + "allEmployeeInfo";

    /**
     * 员工信息Redis缓存 职工名称
     */
    public static final String REDIS_EMPLOYEE_INFO_NAME = REDIS_CACHE_SYSTEM_PREFIX + "allEmployeeName";

    /**
     * 供应商信息Redis缓存
     */
    public static final String REDIS_SUPPLIER_NAME_ALL = REDIS_CACHE_SYSTEM_PREFIX + "supplier:supplierName";

    /**
     * 根据部门编号获取运输天数
     */
    public static final String REDIS_TRANSPORTDAY_DEPTNO = REDIS_CACHE_SYSTEM_PREFIX + "transportDay:deptNo";

    public static final String REDIS_DEPARTMENT_INFO = REDIS_CACHE_SYSTEM_PREFIX + "department:info";

    /**
     * 缓存资源与角色关系
     */
    public static final String REDIS_ROLE_AUTHORITY_RESOURCE = REDIS_CACHE_SYSTEM_PREFIX + REDIS_AUTH_CACHE;

    /**
     * 根据行业代码缓存其客户编码
     */
    public static final String REDIS_INDUSTRY_CODE_CUSTOMERNO = REDIS_CACHE_SYSTEM_PREFIX + "industryMediamCode";

    /**
     * 根据库房代码获取库房类型
     */
    public static final String REDIS_WAREHOUSE_TYPE = REDIS_CACHE_SYSTEM_PREFIX + "warehousetype";

    /**
     * 根据部门获取hr_organization
     */
    public static final String REDIS_ORGAN_DEPTNO = REDIS_CACHE_SYSTEM_PREFIX + "hrOrganization";

    /**
     * 根据仓库代码获取仓库信息Redis缓存
     */
    public static final String REDIS_WAREHOUSE_BY_CODE = REDIS_CACHE_SYSTEM_PREFIX + "warehouseInfo";

    /**
     * 根据客户群缓存客户群信息
     */
    public static final String REDIS_KEY_CUSTOMER_CLUSTER_INFO = REDIS_CACHE_SYSTEM_PREFIX+"customerCluster:info";

    // 根据营业所编号和仓库代码获取营业所和仓库关联关系表Redis缓存
    public static final String REDIS_WAREHOUSE_SALESBRANCH_CONFIG_BY_BRANCHID_WAREHOUSE_CODE = REDIS_CACHE_SYSTEM_PREFIX + "warehouseSalesbranch";


     // 根据两个仓库号获取仓库之间Redis缓存
    public static final String REDIS_WAREHOUSE_DELIVERDAY_BY_WAREHOUSE_CODE = REDIS_CACHE_SYSTEM_PREFIX + "warehouseDeliveryday";

    public final static String carrier_1024 = "1024";

    public final static String SPECIAL_MODEL_NO = "ops:specialModelNo:";

    public final static String carrier_9002_25 = "9002;25";

    public Constants() {
    }
}
