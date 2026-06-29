package com.sales.ops.job.handler.kettlejob;

import com.sales.ops.feign.kettlejob.DDSKettleJobFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2025/6/30 9:46
 * @Descripton TODO
 */
@Component
public class DDSKettleExecHandler {

    @Resource
    private DDSKettleJobFeignApi ddsKettleJobFeignApi;


    /**
     * 同步ops_invntory_dds增量kettle同步任务
     */
    @XxlJob(value = "opsInventoryDdsIncrementTrans")
    public void opsInventoryDdsIncrementTrans() {

        XxlJobHelper.log("==> 同步ops_invntory_dds增量kettle同步任务");
        ResultVo<String> commonResult = ddsKettleJobFeignApi.opsInventoryDdsIncrementTrans();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("同步ops_invntory_dds增量kettle同步任务 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("同步ops_invntory_dds增量kettle同步任务 执行失败");
            return;
        }
        XxlJobHelper.handleSuccess(commonResult.getData());
    }

    /**
     * 同步ops_invntory_type_dds全量kettle同步任务
     */
    @XxlJob(value = "opsInventoryTypeDdsAllTrans")
    public void opsInventoryTypeDdsAllTrans() {

        XxlJobHelper.log("==> 同步ops_invntory_type_dds全量kettle同步任务");
        ResultVo<String> commonResult = ddsKettleJobFeignApi.opsInventoryTypeDdsAllTrans();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("同步ops_invntory_type_dds全量kettle同步任务 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("同步ops_invntory_type_dds全量kettle同步任务 执行失败");
            return;
        }
        XxlJobHelper.handleSuccess(commonResult.getData());
    }

    /**
     * 同步ops_invntory_dds全量kettle同步任务
     */
    @XxlJob(value = "opsInventoryDdsAllTrans")
    public void opsInventoryDdsAllTrans() {

        XxlJobHelper.log("==> 同步ops_invntory_dds全量kettle同步任务");
        ResultVo<String> commonResult = ddsKettleJobFeignApi.opsInventoryDdsAllTrans();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("同步ops_invntory_dds全量kettle同步任务 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("同步ops_invntory_dds全量kettle同步任务 执行失败");
            return;
        }
        XxlJobHelper.handleSuccess(commonResult.getData());
    }

    /**
     * 同步ops_invntory_property_dds全量kettle同步任务
     */
    @XxlJob(value = "opsInventoryPropertyDdsAllTrans")
    public void opsInventoryPropertyDdsAllTrans() {

        XxlJobHelper.log("==> 同步ops_invntory_property_dds全量kettle同步任务");
        ResultVo<String> commonResult = ddsKettleJobFeignApi.opsInventoryPropertyDdsAllTrans();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("同步ops_invntory_property_dds全量kettle同步任务 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("同步ops_invntory_property_dds全量kettle同步任务 执行失败");
            return;
        }
        XxlJobHelper.handleSuccess(commonResult.getData());
    }

    /**
     * 同步指定作业
     */
    @XxlJob(value = "syncTransWithPath")
    public void syncTransWithPath() {
        String param = XxlJobHelper.getJobParam();
        if(StringUtils.isBlank( param)) {
            XxlJobHelper.handleFail("请输入需要在执行的作业名称 例如需要执行abc.ktr作业 则输入参数 abc");
            return;
        }
        XxlJobHelper.log("==> 同步指定作业");
        ResultVo<String> commonResult = ddsKettleJobFeignApi.syncTransWithPath(param);
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("同步指定作业 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("同步指定作业 执行失败");
            return;
        }
        XxlJobHelper.handleSuccess(commonResult.getData());
    }

    /**
     * 全量同步inventory_supplier_dds
     */
    @XxlJob(value = "inventorySupplierDdsAllTrans")
    public void inventorySupplierDdsAllTrans() {
        XxlJobHelper.log("==> 全量同步inventory_supplier_dds");
        ResultVo<String> commonResult = ddsKettleJobFeignApi.inventorySupplierDdsAllTrans();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("全量同步inventory_supplier_dds 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("全量同步inventory_supplier_dds 执行失败");
            return;
        }
        XxlJobHelper.handleSuccess(commonResult.getData());
    }


    /**
     * 增量同步inventory_supplier_dds_increment
     */
    @XxlJob(value = "inventorySupplierDdsIncrementTrans")
    public void inventorySupplierDdsIncrementTrans() {
        XxlJobHelper.log("==> 增量同步inventory_supplier_increment_dds");
        ResultVo<String> commonResult = ddsKettleJobFeignApi.inventorySupplierDdsIncrementTrans();
        if (!commonResult.isSuccess()) {
            XxlJobHelper.log("增量同步inventory_supplier_increment_dds 执行失败 ==> {}", commonResult.getMessage());
            XxlJobHelper.handleFail("增量同步inventory_supplier_increment_dds 执行失败");
            return;
        }
        XxlJobHelper.handleSuccess(commonResult.getData());
    }

}
