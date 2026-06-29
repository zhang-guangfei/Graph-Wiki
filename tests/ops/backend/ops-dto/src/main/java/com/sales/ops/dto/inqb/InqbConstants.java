package com.sales.ops.dto.inqb;

public final  class InqbConstants {

    /**
     * REDIS缓存:系统前缀
     */

    public final static String REDIS_CACHE_SYSTEM_PREFIX = "ops:";
    /**
     *  INQB回复取值id字典配置
     */
    public static final String DICT_CLASSCODE_INQBID = "6002";

    public static final String DICT_CLASSCODE_INQBCODE = "inqbgroup";

    /**
     * 客户信息Redis缓存
     */
    public static final String REDIS_ALL_CUSTOMER_INFO = REDIS_CACHE_SYSTEM_PREFIX + "allCustomerInfo";

    public static final String OPS_ORGANIZATION_MASTER="ops:department:organizationMater";

    // INQB接口的超时重试机制
    public static final int TRY_COUNT = 3;
    // INQB接口的超时重试时的时间间隔
    public static final long RETRY_INTERVAL = 10000L;


}
