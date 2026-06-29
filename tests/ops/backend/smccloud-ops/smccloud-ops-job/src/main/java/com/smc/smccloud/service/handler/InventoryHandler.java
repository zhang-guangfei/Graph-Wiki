package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.service.ConsignmentStockFeignApi;
import com.smc.smccloud.service.InventoryServiceFeignApi;
import com.smc.smccloud.service.InvoiceServiceFeignApi;
import com.smc.smccloud.service.ProductServiceFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2022/1/27 14:01
 * @Descripton TODO
 */

@Component
public class InventoryHandler {

    @Resource
    private InventoryServiceFeignApi inventoryServiceFeignApi;
    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private InvoiceServiceFeignApi invoiceServiceFeignApi;
    @Resource
    private ConsignmentStockFeignApi consignmentStockFeignApi;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    @XxlJob(value = "autoUpdateStock", init = "init", destroy = "destroy")
    public void autoUpdateStock() throws Exception{
        XxlJobHelper.log("==> 进入自动更新CN各工厂库存执行器");
        ResultVo<String> stringResultVo = inventoryServiceFeignApi.autoUpdateStock();
        ResultVo<String> resultVo = inventoryServiceFeignApi.impGPInventory();
        XxlJobHelper.log(resultVo.getData());
        if (!stringResultVo.isSuccess()){
            XxlJobHelper.handleFail("更新CN各工厂库存执行失败");
        } else {
            XxlJobHelper.handleSuccess("更新CN各工厂库存执行完毕");
        }
    }

    /**
     * 保存每天无库存的bin型号
     * @throws Exception
     */
    @XxlJob(value = "updateZeroInventory", init = "init", destroy = "destroy")
    public void updateZeroInventory() throws Exception{

        XxlJobHelper.log("==> 进入保存每天无库存的bin型号执行器");
        try {
            inventoryServiceFeignApi.updateZeroInventory();
        } catch (Exception e) {
            XxlJobHelper.handleFail("执行失败"+e.getMessage());
            return;
        }
        XxlJobHelper.handleSuccess("执行成功");

    }


    @XxlJob(value = "importCNMProductModel", init = "init", destroy = "destroy")
    public void importCNMProductModel() throws Exception{

        XxlJobHelper.log("==> 进入导入中国工厂可生产清单执行器");
        ResultVo<String> stringResultVo = productServiceFeignApi.importCNMProductModel();
        if (!stringResultVo.isSuccess()){
            XxlJobHelper.handleFail("导入中国工厂可生产清单失败");
        } else {
            XxlJobHelper.handleSuccess("导入中国工厂可生产清单完毕");
        }
    }

    /**
     * 根据入库数量和库存数量 反算impData表的出库数量
     */
    @XxlJob(value = "calCsImpExpQty", init = "init", destroy = "destroy")
    public void calCsImpExpQty() {
        ResultVo<String> resultVo = consignmentStockFeignApi.calCsImpExpQty();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.handleFail(resultVo.getMessage());
        } else {
            XxlJobHelper.handleSuccess(resultVo.getMessage());
        }
    }

    /**
     * 定时调整明细金额
     * @return
     */
    @XxlJob(value = "synAdjustDetailDifferentAmt", init = "init", destroy = "destroy")
    public void synAdjustDetailDifferentAmt(){
        ResultVo<String> resultVo = invoiceServiceFeignApi.synAdjustDetailDifferentAmt();
        if (!resultVo.isSuccess()){
            XxlJobHelper.handleFail(resultVo.getMessage());
        } else {
            XxlJobHelper.handleSuccess(resultVo.getMessage());
        }
    }

    @XxlJob(value = "updateSlowModelOnHnad", init = "init", destroy = "destroy")
    public void updateSlowModelOnHnad(){
        ResultVo<String> resultVo = productServiceFeignApi.calcUpdateSlowModelOnHnad();
        if (!resultVo.isSuccess()){
            XxlJobHelper.handleFail(resultVo.getMessage());
        } else {
            XxlJobHelper.handleSuccess(resultVo.getMessage());
        }
    }

    @XxlJob(value = "calcOPSInventoryForManu", init = "init", destroy = "destroy")
    public void calcOPSInventoryForManu(){
        ResultVo<String> resultVo = inventoryServiceFeignApi.calcOPSInventoryForManu();
        if (!resultVo.isSuccess()){
            XxlJobHelper.handleFail(resultVo.getMessage());
        } else {
            XxlJobHelper.handleSuccess(resultVo.getMessage());
        }
    }


    /**
     * 解析http://192.168.168.4:9999/JP-CN/JPSTOCK.ZIP 日本供应商库存
     */
    @XxlJob(value = "paseJPInventorySupplier", init = "init", destroy = "destroy")
    public void paseJPInventorySupplier(){
        ResultVo<String> resultVo = inventoryServiceFeignApi.paseJPInventorySupplier();
        if (!resultVo.isSuccess()){
            XxlJobHelper.handleFail(resultVo.getMessage());
        } else {
            XxlJobHelper.handleSuccess(resultVo.getMessage());
        }
    }

}
