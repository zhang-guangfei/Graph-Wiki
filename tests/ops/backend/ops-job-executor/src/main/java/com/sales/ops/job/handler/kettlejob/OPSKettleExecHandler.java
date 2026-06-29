package com.sales.ops.job.handler.kettlejob;

import com.sales.ops.feign.kettlejob.OPSKettleJobFeign;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2025/7/2 16:35
 * @Descripton TODO
 */
@Component
public class OPSKettleExecHandler {

    @Resource
    private OPSKettleJobFeign opsKettleJobFeign;


    /**
     * 全量同步  CRM_CUSTOMER_LIMIT
     */
    @XxlJob(value = "syncCrmCustomerLimitAll")
    public void syncCrmCustomerLimitAll() {
        XxlJobHelper.log("==> 同步CRM_CUSTOMER_LIMIT全量kettle同步任务");
        ResultVo<String> commonResult = opsKettleJobFeign.syncCrmCustomerLimitAll();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("同步CRM_CUSTOMER_LIMIT全量kettle同步任务 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("同步CRM_CUSTOMER_LIMIT全量kettle同步任务 执行失败");
            return;
        }
        XxlJobHelper.handleSuccess(commonResult.getMessage());
    }

    /**
     * 全量同步  CRM_CUSTOMER_BASE
     */
    @XxlJob(value = "syncCrmCustomerBaseAll")
    public void syncCrmCustomerBaseAll() {
        XxlJobHelper.log("==> 同步CRM_CUSTOMER_BASE全量kettle同步任务");
        ResultVo<String> commonResult = opsKettleJobFeign.syncCrmCustomerBaseAll();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("同步CRM_CUSTOMER_BASE全量kettle同步任务 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("同步CRM_CUSTOMER_BASE全量kettle同步任务 执行失败");
            return;
        }
        XxlJobHelper.handleSuccess(commonResult.getMessage());
    }

    /**
     * 全量同步 product_limit
     */
    @XxlJob(value = "syncProductLimitAll")
    public void syncProductLimitAll() {
        XxlJobHelper.log("==> 同步product_limit全量kettle同步任务");
        ResultVo<String> commonResult = opsKettleJobFeign.syncProductLimitAll();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("同步product_limit全量kettle同步任务 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("同步product_limit全量kettle同步任务 执行失败");
            return;
        }
        XxlJobHelper.handleSuccess(commonResult.getMessage());
    }


    /**
     * 全量同步 work_day
     */
    @XxlJob(value = "syncHrWorkDay")
    public void syncWorkDay() {
        XxlJobHelper.log("==> 同步work_day全量kettle同步任务");
        ResultVo<String> commonResult =  opsKettleJobFeign.syncWorkDay();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("同步work_day全量kettle同步任务 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("同步work_day全量kettle同步任务 执行失败");
            return;
        }
        XxlJobHelper.handleSuccess(commonResult.getMessage());
    }


    /**
     * 数据库定时归档,一个月一次
     * bin_order_app,bin_order_detail_split,bin_order_detail,bin_order_calc
     */
    @XxlJob(value = "syncArchiveOpsTable_bin")
    public void syncArchiveOpsTable_bin() {
        XxlJobHelper.log("==> 数据库归档bin相关表");
        ResultVo<String> commonResult = opsKettleJobFeign.syncArchiveOpsTable_bin();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("数据库归档bin相关表 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("数据库归档bin相关表 执行失败");
        }
    }


    /**
     * 数据库定时归档,一年一次
     * order_state,ops_delivery_plan_result,orderSales,rcvdetail,rcvmaster
     */
    @XxlJob(value = "syncArchiveOpsTable_order")
    public void syncArchiveOpsTable_order() {
        XxlJobHelper.log("==> 数据库归档order相关表");
        ResultVo<String> commonResult = opsKettleJobFeign.syncArchiveOpsTable_order();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("数据库归档order相关表 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("数据库归档order相关表 执行失败");
        }
    }

    /**
     * 数据库定时归档,一年一次
     * expdetail_barcode,expdetail_property,expdetail
     */
    @XxlJob(value = "syncArchiveOpsTable_exp")
    public void syncArchiveOpsTable_exp() {
        XxlJobHelper.log("==> 数据库归档exp相关表");
        ResultVo<String> commonResult = opsKettleJobFeign.syncArchiveOpsTable_exp();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("数据库归档exp相关表 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("数据库归档exp相关表 执行失败");
        }
    }

    /**
     * 数据库定时归档,一周一次
     * ops_wm_order_task,ops_inventory_move
     */
    @XxlJob(value = "syncArchiveOpsTable_wm")
    public void syncArchiveOpsTable_wm() {
        XxlJobHelper.log("==> 数据库归档wm相关表");
        ResultVo<String> commonResult = opsKettleJobFeign.syncArchiveOpsTable_wm();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("数据库归档wm相关表 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("数据库归档wm相关表 执行失败");
        }
    }

    /**
     * 数据库定时归档,一天一次
     * ops_event_bus,ops_event_pool,
     * ops_event_order_status,ops_event_stock_assign,ops_event_delivery_plan,ops_event_delivery_push,ops_event_purchase_order
     */
    @XxlJob(value = "syncArchiveOpsTable_event")
    public void syncArchiveOpsTable_event() {
        XxlJobHelper.log("==> 数据库归档event相关表");
        ResultVo<String> commonResult = opsKettleJobFeign.syncArchiveOpsTable_event();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("数据库归档event相关表 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("数据库归档event相关表 执行失败");
        }
    }

    /**
     * 数据库定时归档,一月一次
     * imp_invoice_event_log
     * ops_sales_notice_task
     * DailyInventory
     * ops_sys_log
     * order_log
     * ops_delivery_plan_detail
     * ops_log_wms_dispatch
     */
    @XxlJob(value = "syncArchiveOpsTable_log")
    public void syncArchiveOpsTable_log() {
        XxlJobHelper.log("==> 数据库归档log相关表");
        ResultVo<String> commonResult = opsKettleJobFeign.syncArchiveOpsTable_log();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("数据库归档log相关表 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("数据库归档log相关表 执行失败");
        }
    }

    @XxlJob(value = "syncArchiveOpsTable_test")
    public void syncArchiveOpsTable_test() {
        XxlJobHelper.log("==> 数据库归档test相关表");
        ResultVo<String> commonResult = opsKettleJobFeign.syncArchiveOpsTable_test();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("数据库归档test相关表 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("数据库归档test相关表 执行失败");
        }
    }

    @XxlJob(value = "syncPullCnOrderSrcInfo")
    public void syncPullCnOrderSrcInfo() {
        XxlJobHelper.log("==> 拉取生产环境CN订单数据");
        ResultVo<String> commonResult = opsKettleJobFeign.syncPullCnOrderSrcInfo();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("拉取生产环境CN订单数据 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("拉取生产环境CN订单数据 执行失败");
        }
    }
}
