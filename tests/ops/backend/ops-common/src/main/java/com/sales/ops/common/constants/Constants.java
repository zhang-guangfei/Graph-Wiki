package com.sales.ops.common.constants;

public class Constants {

    public static final String SystemName = "OPS";

    // BUG9306马腾 新采购单号计数器
    public static final String COUNTER_NEWPURCHASENO = "ops:counter:purchase:newpurchaseno";

    // BUG9306马腾 新采购单号计数器
    public static final String COUNTER_MANUACCEPTER = "ops:counter:purchase:manuaccepter";

    //ops系统缓存前缀
    public final static String REDIS_CACHE_SYSTEM_PREFIX = "ops:";
    //订单状态数据前缀
    public final static String ORDER_STATUS_DATAS = "1001";
    //订单状态能否删单
    public final static String CAN_DEL_STATUS = REDIS_CACHE_SYSTEM_PREFIX + "canDelCodes:" + ORDER_STATUS_DATAS;
}
