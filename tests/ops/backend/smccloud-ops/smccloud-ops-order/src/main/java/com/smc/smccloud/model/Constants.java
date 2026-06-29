package com.smc.smccloud.model;

public final  class Constants {

    public final static String REDIS_CACHE_SYSTEM_PREFIX = "ops:";

    public static  String CURRENTCY_CNY="CNY";

    public  static String BJ_SUPPLIER_CODES="CN;CT;CM;TZ;";

    public  static final String REDIS_KEY_IMP_CONFIRM_LOCK="ops:order:impconfirm:LOCK";
    public  static final String REDIS_KEY_IMP_CONFIRM_UPD_LOCK="ops:impinvoice:LOCK:";
    public  static final String REDIS_KEY_IMP_CONFIRM_ADDPO_LOCK="ops:impinvoice:addpo:LOCK:";
    public  static final  String REDIS_KEY_IMP_CN_INVOICE="ops:impinvoice:cn:lock";
    public  static final  String REDIS_KEY_IMP_GP_INVOICE="ops:impinvoice:gp:lock";

    public  static final String REDIS_KEY_ORDER_MODIFY_LOCK="ops:orderModify:Cancel:LOCK";

    public static final String[] COUNTRY_FACTORY = new String[]{"CM", "CN", "GZ", "TZ", "CT", "YZ"};

    public static final String REDIS_ORDERSTATE_MQ_ERROR = REDIS_CACHE_SYSTEM_PREFIX + "errorOrderStateMqMsg:";

    public  static final String REDIS_KEY_CONFIRM_POINVOICE_LOCK="ops:order:confirmPOInv:LOCK";

    public  static final String REDIS_KEY_CHECK_IMPERROR_LOCK="ops:order:checkImpError:LOCK";

    public static  final  String REDIS_KEY_ALLOW_UPDATE_ORDER_STATE="ops:order:allowstate";

    public  static  final  String REDIS_KEY_TRANS_DAYS="ops:order:transdays";

    /**
     * 无商业价值SMCCode
     */
    public static final String[] NO_COMMERCIAL_VALUE = new String[] {"9501250","9501252","9501254","9501257","9501251","9501253","9501258"};

    /**
     * 交货期字典配置classCode
     */
    public static final String DICT_CLASSCODE_ORDERSTATE = "1004";

    /**
     * 根据发货方式获取可变更的发货方式key
     */
    public static final String DLV_TYPE = REDIS_CACHE_SYSTEM_PREFIX+"order:dlvtype:";

    /**
     * 接单状态字典配置classCode
     */
    public static final String DICT_CLASSCODE_RCVORDERSTATUS = "1001";
    public  static final String REDIS_KEY_STOCK_ADJUST_LOCK="ops:order:stockAdjust:LOCK";

    // mq消息队列推送的时间
    public static final String sendMQDate = REDIS_CACHE_SYSTEM_PREFIX+"orderMQ:pushDate:";

    public static final String SECONDARY_APPROVAL = "C";

    public static final String ips_to_ops_max_id = "9002;25";


    public static final String OPS_NOTICE_BUSINESSCODE_BYGROUP = "ops:notice:businessCode:bygroup:";
}
