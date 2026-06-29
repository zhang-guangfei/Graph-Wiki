package com.smc.smccloud.controller;

import com.smc.smccloud.service.KettleExecService;
import com.smc.smccloud.util.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2025/6/30 9:31
 * @Descripton TODO
 */
@RestController
@RequestMapping("/kettle")
public class KettleExecController {

    @Resource
    private KettleExecService kettleExecService;


    /**
     * 同步指定作业
     */
    @GetMapping("/syncTransWithPath")
    public ResultVo<String> syncTransWithPath(@RequestParam("transName") String transName) {
        return kettleExecService.syncTransWithPath(transName);
    }

    /**
     * 全量同步  CRM_CUSTOMER_LIMIT
     *
     */
    @GetMapping("/syncCrmCustomerLimitAll")
    public ResultVo<String> syncCrmCustomerLimitAll() {
        return kettleExecService.syncCrmCustomerLimitAll();
    }

    /**
     * 全量同步  CRM_CUSTOMER_BASE
     * @return
     */
    @GetMapping("/syncCrmCustomerBaseAll")
    public ResultVo<String> syncCrmCustomerBaseAll() {
        return kettleExecService.syncCrmCustomerBaseAll();
    }

    /**
     * 全量同步  product_limit
     * @return
     */
    @GetMapping("/syncProductLimitAll")
    public ResultVo<String> syncProductLimitAll() {
        return kettleExecService.syncProductLimitAll();
    }


    /**
     * 工作日同步
     */
    @GetMapping("/syncWorkDay")
    public ResultVo<String> syncWorkDay() {
        return kettleExecService.syncWorkDay();
    }

    @GetMapping("/syncArchiveOpsTable_bin")
    public ResultVo<String> syncArchiveOpsTable_bin() {
        return kettleExecService.syncArchiveOpsTable_bin();
    }

    @GetMapping("/syncArchiveOpsTable_order")
    public ResultVo<String> syncArchiveOpsTable_order() {
        return kettleExecService.syncArchiveOpsTable_order();
    }

    @GetMapping("/syncArchiveOpsTable_exp")
    public ResultVo<String> syncArchiveOpsTable_exp() {
        return kettleExecService.syncArchiveOpsTable_exp();
    }

    @GetMapping("/syncArchiveOpsTable_wm")
    public ResultVo<String> syncArchiveOpsTable_wm() {
        return kettleExecService.syncArchiveOpsTable_wm();
    }

    @GetMapping("/syncArchiveOpsTable_event")
    public ResultVo<String> syncArchiveOpsTable_event() {
        return kettleExecService.syncArchiveOpsTable_event();
    }

    @GetMapping("/syncArchiveOpsTable_log")
    public ResultVo<String> syncArchiveOpsTable_log() {
        return kettleExecService.syncArchiveOpsTable_log();
    }
    @GetMapping("/syncArchiveOpsTable_test")
    public ResultVo<String> syncArchiveOpsTable_test() {
        return kettleExecService.syncArchiveOpsTable_test();
    }
    @GetMapping("/syncPullCnOrderSrcInfo")
    public ResultVo<String> syncPullCnOrderSrcInfo() {
        return kettleExecService.syncPullCnOrderSrcInfo();
    }
}
