package com.sales.ops.enums.invoice;


public class ImpInvoiceConstants {
    public final static String poAdapter = "poAdapter";

    public final static String poadapterKey = "ops:poadapter:";

    public final static String DICT_CLASSCODE = poadapterKey+"dict:";

    public final static String unusual_code = poadapterKey+"unusual:";

    // po作业名称 对应 ops_po_sync_midd_manage表的 job_name
    public final static String ParseShpinf = "ParseShpinf";
    public final static String ParseDelivery = "ParseDelivery";
    
    public final static String ParseOrder95012 = "ParseOrder95012";
    public final static String GetManuJPShippedInfoInfo = "GetManuJPShippedInfoInfo";
    public final static String GetInqueryDataSendingInfo = "GetInqueryDataSendingInfo";
    public final static String GetGwInvoiceInfoWithAutoJob = "GetGwInvoiceInfoWithAutoJob";

    // 根据供应商code+状态 获取 ops状态
    public final static String getOpsStatusBySupplierCodeAndStatus = poadapterKey+"getOpsStatusBySupplierCodeAndStatus:";

    public final static String gw_last_time = poadapterKey+"impGwinvoice:GWHour:";


    public final static String ops_po_trans_type = poadapterKey+"ops_po_trans_type:";

    // imp_invoice表同步 jobName
    public final static String IMP_INVOICE_SYSN = "ImpInvoiceSysn";

    public  static final String REDIS_KEY_CHECK_IMPERROR_LOCK="ops:order:checkImpError:LOCK";

    public final static  String REDIS_KEY_TRANS_DAYS="ops:order:transdays";

    public final static  String REDIS_KEY_SUPPLIER_TRANS="ops:delivery:suppliertrans:";

    public final static  String REDIS_KEY_OPS_TRANSTYPE="ops:delivery:opstrans:";

    public static final String OPS_INVOICE_PROSECC = "ops-invoice-process";

    public static final String OPS_INVOICE = "ops:rabbitmq:invoice:";
    public static final String OPS_MQ_ORDER_STATE = "ops-order-state";
    public static final String OPS_MQ_ORDER = "ops:rabbitmq:order:";

    public static final String OPS = "OPS";

    /**
     * 无商业价值SMCCode
     */
    public static final String[] NO_COMMERCIAL_VALUE = new String[] {"9501250","9501252","9501254","9501257","9501251","9501253","9501258"};

    public static final String INVOICE_SYSN_ERROR = "ImpInvoiceSysn导入错误-";

    public  static final  String REDIS_KEY_IMP_INVOICE="ops:newimpinvoice:adapter:lock";

    public  static final  String REDIS_KEY_IMP_CN_INVOICE="ops:newimpinvoice:adapter:cn:lock";
    public  static final  String REDIS_KEY_IMP_GP_INVOICE="ops:newimpinvoice:adapter:gp:lock";
    public  static final  String REDIS_KEY_IMP_JP_INVOICE="ops:newimpinvoice:adapter:jp:lock";
    public  static final  String REDIS_KEY_IMP_GW_INVOICE="ops:newimpinvoice:adapter:gw:lock";

    public  static final  String REDIS_KEY_IMP_ERROR_INVOICE_COUNT="ops:newimpinvoice:adapter:invoicecount:";

    public  static final  Integer IMP_ERROR_INVOICE_ReTry_COUNT = 10;
}
