package com.sales.ops.feign.kettlejob;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author lyc
 * @Date 2025/7/2 16:36
 * @Descripton TODO
 */
@FeignClient(name = "ops-kettle-service", contextId = "opsKettleService")
public interface OPSKettleJobFeign {

    /**
     * 同步指定作业
     */
    @GetMapping("/kettle/syncTransWithPath")
    public ResultVo<String> syncTransWithPath(@RequestParam("transName") String transName);

    /**
     * 全量同步  CRM_CUSTOMER_LIMIT
     */
    @GetMapping("/kettle/syncCrmCustomerLimitAll")
    public ResultVo<String> syncCrmCustomerLimitAll();

    /**
     * 全量同步  CRM_CUSTOMER_BASE
     *
     * @return
     */
    @GetMapping("/kettle/syncCrmCustomerBaseAll")
    public ResultVo<String> syncCrmCustomerBaseAll();

    /**
     * 全量同步  product_limit
     *
     * @return
     */
    @GetMapping("/kettle/syncProductLimitAll")
    public ResultVo<String> syncProductLimitAll();


    /**
     * 全量同步  work_day
     * @return
     */
    @GetMapping("/kettle/syncWorkDay")
    public ResultVo<String> syncWorkDay();


    /**
     * 数据库定时归档,一个月一次
     * bin_order_app,bin_order_detail_split,bin_order_detail,bin_order_calc
     */
    @GetMapping("/kettle/syncArchiveOpsTable_bin")
    public ResultVo<String> syncArchiveOpsTable_bin();

    /**
     * 数据库定时归档,一年一次
     * order_state,ops_delivery_plan_result,orderSales,rcvdetail,rcvmaster
     */
    @GetMapping("/kettle/syncArchiveOpsTable_order")
    public ResultVo<String> syncArchiveOpsTable_order();

    /**
     * 数据库定时归档,一年一次
     * expdetail_barcode,expdetail_property,expdetail
     */
    @GetMapping("/kettle/syncArchiveOpsTable_exp")
    public ResultVo<String> syncArchiveOpsTable_exp();

    /**
     * 数据库定时归档,一周一次
     * ops_wm_order_task,ops_inventory_move
     */
    @GetMapping("/kettle/syncArchiveOpsTable_wm")
    public ResultVo<String> syncArchiveOpsTable_wm();

    /**
     * 数据库定时归档,一天一次
     * ops_event_bus,ops_event_pool,
     * ops_event_order_status,ops_event_stock_assign,ops_event_delivery_plan,ops_event_delivery_push,ops_event_purchase_order
     */
    @GetMapping("/kettle/syncArchiveOpsTable_event")
    public ResultVo<String> syncArchiveOpsTable_event();

    /**
     * 数据库定时归档,一月一次
     * imp_invoice_event_log
     * ops_sales_notice_task
     * DailyInventory
     * ops_sys_log
     * order_log
     * ops_delivery_plan_detail
     *
     */
    @GetMapping("/kettle/syncArchiveOpsTable_log")
    public ResultVo<String> syncArchiveOpsTable_log();

    /**
     * 测试归档job连通性
     */
    @GetMapping("/kettle/syncArchiveOpsTable_test")
    public ResultVo<String> syncArchiveOpsTable_test();


    @GetMapping("/kettle/syncPullCnOrderSrcInfo")
    public ResultVo<String> syncPullCnOrderSrcInfo();
}
