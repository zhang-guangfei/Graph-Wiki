package com.sales.ops.job.handler;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.purchase.PurchaseUnusualFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpsPurchaseUnusualHandler {

    private static Logger logger = LoggerFactory.getLogger(OpsPurchaseReplyHandler.class);

    @Autowired
    private PurchaseUnusualFeignApi purchaseUnusualFeignApi;

    @XxlJob("pullUnusual")
    public void pullUnusual() throws Exception {
        XxlJobHelper.log("开始执行pullUnusual");
        try {
            CommonResult<Object> result = purchaseUnusualFeignApi.create(1000);
            XxlJobHelper.log("执行完成pullUnusual：{}", result.getData());
        } catch (Exception e) {
            XxlJobHelper.log("执行失败pullUnusual : {}", e);
            XxlJobHelper.handleFail("执行失败pullUnusual");
            throw e;
        }
    }


    @XxlJob("handleUnusualFromJP")
    public void handleUnusualFromJP() throws Exception {
        XxlJobHelper.log("执行开始");
        try {
            //处理所有数据，一次处理1000条，避免一次查询太多导致数据库查询超时
            CommonResult result = purchaseUnusualFeignApi.handleUnusualFromJP(1000);
            XxlJobHelper.log("执行完成：{}", result.getData());
        } catch (Exception e) {
            XxlJobHelper.log("执行失败：{}", e);
            XxlJobHelper.handleFail("执行失败");
            throw e;
        }
    }

    @XxlJob("handleUnusualFromCNC")
    public void handleUnusualFromCNC() throws Exception {
        XxlJobHelper.log("执行开始");
        try {
            //处理所有数据，一次处理1000条，避免一次查询太多导致数据库查询超时
            CommonResult result = purchaseUnusualFeignApi.handleUnusualFromCN(1000);
            XxlJobHelper.log("执行完成：{}", result.getData());
        } catch (Exception e) {
            XxlJobHelper.log("执行失败：{}", e);
            throw e;
        }
    }

}
