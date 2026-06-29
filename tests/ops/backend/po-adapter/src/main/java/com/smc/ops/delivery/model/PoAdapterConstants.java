package com.smc.ops.delivery.model;

/**
 * @Author lyc
 * @Date 2024/2/23 8:38
 * @Descripton TODO
 */
public class PoAdapterConstants {
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

    public final static String ops_po_common_config = poadapterKey+"ops_po_common_config:";

    public final static String ops_po_trans_type_classcode = "1001";

    public final static String ops_po_not_need_createBarcode_classcode = "1002";


}
