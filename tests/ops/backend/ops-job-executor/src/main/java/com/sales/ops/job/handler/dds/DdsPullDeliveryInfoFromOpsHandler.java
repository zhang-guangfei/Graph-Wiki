package com.sales.ops.job.handler.dds;

import com.sales.ops.feign.dds.DdsPullDeliveryInfoFromOpsFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DdsPullDeliveryInfoFromOpsHandler {

    @Resource
    DdsPullDeliveryInfoFromOpsFeignApi ddsPullDeliveryInfoFromOpsFeignApi;

    @XxlJob("pullGZDeliveryInfoFromOps")
    public void pullDeliveryInfoFromOps() throws Exception {
        XxlJobHelper.log("开始执行 pullDeliveryInfoFromOps ");
        String param = XxlJobHelper.getJobParam();//获得任务参数
        ResultVo<String> resultVo = ddsPullDeliveryInfoFromOpsFeignApi.pullGzDeliveryInfoFromOps(param);
        XxlJobHelper.log("完成 pullDeliveryInfoFromOps " + resultVo.getMessage());
        if (!resultVo.isSuccess()) {
            XxlJobHelper.handleFail("执行 pullDeliveryInfoFromOps失败");
        }
    }
    @XxlJob("pullCNDeliveryInfoFromOps")
    public void pullCNDeliveryInfoFromOps() throws Exception {
        XxlJobHelper.log("开始执行 pullDeliveryInfoFromOps ");
        String param = XxlJobHelper.getJobParam();//获得任务参数
        ResultVo<String> resultVo = ddsPullDeliveryInfoFromOpsFeignApi.pullCNDeliveryInfoFromOps();
        XxlJobHelper.log("完成 pullDeliveryInfoFromOps " + resultVo.getMessage());
        if (!resultVo.isSuccess()) {
            XxlJobHelper.handleFail("执行 pullDeliveryInfoFromOps失败");
        }
    }

}
