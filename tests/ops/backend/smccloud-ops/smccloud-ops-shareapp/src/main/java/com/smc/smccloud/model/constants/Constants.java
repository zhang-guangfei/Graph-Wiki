package com.smc.smccloud.model.constants;

/**
 * Author: B90034
 * Date: 2022-07-22 16:36
 * Description:
 */
public class Constants {

    /**
     * 门户接口地址
     */
//    public static final String PORTAL_URL = "http://10.116.2.26:8081";

    /**
     * 发货数据写入结转表的起始时间
     */
    public static final String PARSE_EXPDETAIL_STARTtIMECODE = "9002;10";

    /**
     * 样品申请类型字典表classCode编码
     */
    public static final String DICT_CODE_SAMPLEORDER_APPTYPE = "1006";

    /**
     * 样品结转状态字典表classCode编码
     */
    public static final String DICT_CODE_SAMPLEORDER_BALSTATUS = "1011";

    /**
     * 记录已发送逾期未结转通知的营业所
     */
    public static final String OVER_TIME_NOBAL_DEPTNOS = "ops:overTimeNoBalDEptNos:";

    public static final String SAVEFILEPATH_SAMPLEVERNOBAL = "overTimeNoBalFiles/";

    // 记录展览展示品导出时间
    public static final String ZLZS_EXPORT_TIME = "ops:zlzsExportTime";


    /**
     * 记录已发送展示品盘点票清单的营业所
     */
    public static final String ZLZS_SEND_DEPTNOS = "ops:zlzsSendMailDeptNos:";

    /**
     * 展览展示品销账模板文件名
     */
    public static final String ZLZS_WRITEOFF_FINENAME = "展览展示品销账模板.xlsx";

    public static final String ZLZS_UPRCVDEPTNO_FINENAME = "批量变更展示品实物所在部门模板.xlsx";

    /**
     * 批量导入历史盘点票数据模板
     */
    public static final String ZLZS_IMP_HISTORYDATA = "批量导入历史盘点票数据模板.xlsx";
    /**
     * 展示品盘点表管理-状态
     */
    public static final String ZLZS_MANAGE_STATUS = "1092";

    public static final String find_last_inventory_time = "ops:preOrderAccount:find_inventory_time:";

    public static final int max_export_num = 200000;

    public static final String ui_view_id = "ops:view:";

    public static final String PRE_ORDER_ACCOUNT_INVENTORY_ID = "ops:preOrderAccount:inventoryId:";

}
