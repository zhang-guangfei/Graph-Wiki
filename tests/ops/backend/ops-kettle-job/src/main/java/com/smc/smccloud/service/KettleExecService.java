package com.smc.smccloud.service;


import com.smc.smccloud.util.ResultVo;

import java.util.Map;

/**
 * @Author lyc
 * @Date 2025/6/25 16:10
 * @Descripton TODO
 */
public interface KettleExecService {


    ResultVo<String> execKettleJob(String jobPath);

    ResultVo<String> execKettleTrans(String transPath, Map<String, String> parameters);

    /**
     * 同步指定作业
     */
    ResultVo<String> syncTransWithPath(String transName);


    /**
     * 全量同步  CRM_CUSTOMER_LIMIT
     *
     */
    ResultVo<String> syncCrmCustomerLimitAll();

    /**
     * 全量同步  CRM_CUSTOMER_BASE
     * @return
     */
    ResultVo<String> syncCrmCustomerBaseAll();

    /**
     * 全量同步  product_limit
     * @return
     */
    ResultVo<String> syncProductLimitAll();


    /**
     * 工作日同步
     */
    ResultVo<String> syncWorkDay();

    ResultVo<String> syncArchiveOpsTable_bin();

    ResultVo<String> syncArchiveOpsTable_order();

    ResultVo<String> syncArchiveOpsTable_exp();

    ResultVo<String> syncArchiveOpsTable_wm();

    ResultVo<String> syncArchiveOpsTable_event();

    ResultVo<String> syncArchiveOpsTable_log();

    ResultVo<String> syncArchiveOpsTable_test();

    ResultVo<String> syncPullCnOrderSrcInfo();
}
