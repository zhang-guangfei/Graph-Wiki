package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.service.OrderStateServiceFeignApi;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class OrderStateHandler {

    @Resource
    private OrderStateServiceFeignApi orderStateApi;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    @XxlJob(value = "importCNMOrderReply", init = "init", destroy = "destroy")
    public ReturnT<String> importCNMOrderReply() throws Exception {
        XxlJobHelper.log("==> 开始导入中国工厂订单返信");
        try {
            ResultVo<String> result = orderStateApi.importCNMOrderReply();
            XxlJobHelper.log("导入结果:" + result.getData());
        } catch (Exception ex) {
            XxlJobHelper.handleFail(ex.getMessage());
            return ReturnT.FAIL;
        }

        return ReturnT.SUCCESS;

    }

    /**
     * 从中国工厂导入所有未发货订单状态 (OPS_V_RequisitionStausOtherToSales)
     * @return
     * @throws Exception
     */
    @XxlJob(value = "businessOrderStatus", init = "init", destroy = "destroy")
    public ReturnT<String> businessOrderStatus() throws Exception {
        XxlJobHelper.log("==> 营业订单状态接口(从中国工厂导入所有未发货订单状态)");
        try {
            ResultVo<String> resultVo = orderStateApi.importCNFactoryNotSendOrderState();
            if (!resultVo.isSuccess() && resultVo.getData() == null) {
                return ReturnT.FAIL;
            }
            XxlJobHelper.log("导入结果:" + resultVo.getData());
        } catch (Exception ex) {
            XxlJobHelper.handleFail(ex.getMessage());
            return ReturnT.FAIL;
        }

        return ReturnT.SUCCESS;

    }


    /**
     * 导入中国工厂订单状态 (OPS_V_RequisitionStausToSales)
     */
    @XxlJob(value = "importCNMOPSVRequisitionStatusToSales", init = "init", destroy = "destroy")
    public void importCNMOPSVRequisitionStatusToSales() throws Exception {
        XxlJobHelper.log("==> 导入中国工厂订单状态");
        ResultVo<String> resultVo = orderStateApi.importCNMOPSVRequisitionStatusToSales();
        if (resultVo.isSuccess()) {
            XxlJobHelper.handleSuccess(resultVo.getData());
        } else {
            XxlJobHelper.handleFail(resultVo.getMessage());
        }
    }

    /**
     * 根据订单状态校对货期状态
     */
    @XxlJob(value = "syncOrderStateInfo", init = "init", destroy = "destroy")
    public void  syncOrderStateInfo() {
//        XxlJobHelper.log("==> 开始检查订单状态是否与货期状态一致");
//        ResultVo<String> resultVo = orderStateApi.checkOrderState();
//        XxlJobHelper.handleSuccess(resultVo.getMessage());
        orderStateApi.syncOrderStateInfo();
    }



    /**
     * 日本订单返信
     * http://192.168.168.4:9999/JP-CN/   JP-CN  WZBbkgwY  下载 BACKODR.ZIP 代替 delivery.dat 文件
     */
    @XxlJob(value = "downloadJPDeliveryFile", init = "init", destroy = "destroy")
    public void  downloadJPDeliveryFile() {
        ResultVo<String> resultVo = orderStateApi.downloadJPDeliveryFile();
        if (resultVo.isSuccess()) {
            XxlJobHelper.handleSuccess(resultVo.getData());
        } else {
            XxlJobHelper.handleFail(resultVo.getMessage());
        }
    }


}
