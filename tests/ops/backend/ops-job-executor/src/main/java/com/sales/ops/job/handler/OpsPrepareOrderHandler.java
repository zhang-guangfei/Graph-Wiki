package com.sales.ops.job.handler;

import com.sales.ops.feign.PrepareOrderFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OpsPrepareOrderHandler {

    @Resource
    private PrepareOrderFeignApi prepareOrderFeignApi;


    //向PSI推送准备订单
    @XxlJob("pushPrepareOrderToPSI")
    public void pushPrepareOrderToPSI() throws Exception{
        XxlJobHelper.log("执行向PSI推送准备订单");
        ResultVo<String> stringResultVo = prepareOrderFeignApi.pushPrepareOrderToPSI();
        if (stringResultVo.isSuccess()) {
            XxlJobHelper.log("推送成功");
            XxlJobHelper.handleSuccess();
        } else {
            XxlJobHelper.log("推送失败");
            XxlJobHelper.handleFail();
        }
    }

    /**
     * 从PSI拉取返信信息
     */
    @XxlJob("pullPrepareOrderDeliveryInfoFromPsi")
    public void pullPrepareOrderDeliveryInfoFromPsi() throws Exception{
        XxlJobHelper.log("执行从PSI拉取返信信息");
        ResultVo<String> stringResultVo = prepareOrderFeignApi.pullPrepareOrderDeliveryInfoFromPsi();
        if (stringResultVo.isSuccess()) {
            XxlJobHelper.log("拉取成功");
            XxlJobHelper.handleSuccess();
        } else {
            XxlJobHelper.log("拉取失败");
            XxlJobHelper.handleFail();
        }
    }

    @XxlJob("prepareOrderPreHandle")
    public void prepareOrderPreHandle() throws Exception{
        XxlJobHelper.log("执行准备订单预处理");
        ResultVo<String> stringResultVo = prepareOrderFeignApi.prepareOrderPreHandle();
        if (stringResultVo.isSuccess()) {
            XxlJobHelper.log("处理成功");
            XxlJobHelper.handleSuccess();
        } else {
            XxlJobHelper.log("处理失败");
            XxlJobHelper.handleFail();
        }
    }

}
