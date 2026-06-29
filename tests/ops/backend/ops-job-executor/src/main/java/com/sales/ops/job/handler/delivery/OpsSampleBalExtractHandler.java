package com.sales.ops.job.handler.delivery;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.delivery.SampleBalExtractFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * B91717
 * 样品出库订单抽取到 sample_bal
 */
@Component
public class OpsSampleBalExtractHandler {

    @Autowired
    private SampleBalExtractFeignApi sampleBalExtractFeignApi;

    /**
     * 物流发货数据自动生成样品结算数据,
     * 不拆分订单抽取
     */
    @XxlJob(value = "unProdflagSampleBalData")
    public void unProdflagSampleBalData() throws Exception {
        XxlJobHelper.log("==> 开始执行样品订单出库抽取结转数据 => 不拆分任务执行");
        try {
            CommonResult<String> commonResult = sampleBalExtractFeignApi.unprodextract();
            if (!commonResult.isSuccess()) {
                XxlJobHelper.log("【unProdflagSampleBalData】 执行失败 ==> {}", commonResult.getMessage());
                XxlJobHelper.handleFail("【unProdflagSampleBalData】 执行失败");
                throw new Exception(commonResult.getMessage());
            } else {
                XxlJobHelper.handleSuccess(commonResult.getMessage());
            }
        } catch (Exception e) {
            XxlJobHelper.log("【unProdflagSampleBalData】 执行失败 ==> {}", e.getMessage());
            XxlJobHelper.handleFail("【unProdflagSampleBalData】 执行失败");
            throw e;
        }

    }

    /**
     * 物流发货数据自动生成样品结算数据,
     * 拆分订单抽取
     */
    @XxlJob(value = "prodflagSampleBalData")
    public void prodflagSampleBalData() throws Exception {

        XxlJobHelper.log("==> 开始执行样品订单出库抽取结转数据 => 拆分任务执行");
        try {
            CommonResult<String> commonResult = sampleBalExtractFeignApi.prodextract();
            if (!commonResult.isSuccess()) {
                XxlJobHelper.log("【prodflagSampleBalData】 执行失败 ==> {}", commonResult.getMessage());
                XxlJobHelper.handleFail("【prodflagSampleBalData】 执行失败");
                throw new Exception(commonResult.getMessage());
            } else {
                XxlJobHelper.handleSuccess(commonResult.getMessage());
            }
        } catch (Exception e) {
            XxlJobHelper.log("【prodflagSampleBalData】 执行失败 ==> {}", e.getMessage());
            XxlJobHelper.handleFail("【prodflagSampleBalData】 执行失败");
            throw e;
        }
    }


}
