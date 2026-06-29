package com.sales.ops.job.handler.dds;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.dds.DdsPullDeliveryInfoFromOpsFeignApi;
import com.sales.ops.feign.dds.DdsPullPurchaseInfoFromOpsFeignApi;
import com.sales.ops.job.handler.purchaseDeliver.PurchaseDeliverHandler;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DdsPullPsiPurchaseInfoHandler {

    private static Logger logger = LoggerFactory.getLogger(DdsPullPsiPurchaseInfoHandler.class);


    @Resource
    DdsPullPurchaseInfoFromOpsFeignApi ddsPullPurchaseInfoFromOpsFeignApi;


    @XxlJob("pullPurchaseOnthewayFromOps")
    public void pullPurchaseOnthewayFromOps() throws Exception {
        XxlJobHelper.log("开始执行 pullPsiPurchaseOnthewayFromOps ");
        try {
            ResultVo<String> resultVo = ddsPullPurchaseInfoFromOpsFeignApi.pullPsiPurchaseOnthewayFromOps();
            XxlJobHelper.log("完成 pullGzDeliveryInfoFromOps " + resultVo.getMessage());
            if (!resultVo.isSuccess()) {
                throw new Exception(resultVo.getMessage());
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行pullPsiPurchaseOnthewayFromOps 失败 : {}", e);
            XxlJobHelper.handleFail("执行pullPsiPurchaseOnthewayFromOps 失败");
            throw e;
        }
    }

}
