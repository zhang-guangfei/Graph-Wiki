package com.sales.ops.job.handler.delivery;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.delivery.DeliveryLowFreqFeignApi;
import com.sales.ops.feign.delivery.DeliveryOrderModifyFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/7/4 11:20
 */

@Component
public class OpsLastEightMonthLowFreqHandler {

    @Autowired
    private DeliveryLowFreqFeignApi deliveryLowFreqFeignApi;


    /**
     * bugid: 14635 顾客在库低频型号的自动获取及订单 拦截
     */
    @XxlJob("lastEightMonthsLowFreq")
    public void lastEightMonthsLowFreq() {
        CommonResult<String> commonResult = deliveryLowFreqFeignApi.lastEightMonthsLowFreq();
        if(commonResult.isSuccess()){
            XxlJobHelper.log("执行结果:"+commonResult.getData());
        }else {
            // 报错发邮件
            XxlJobHelper.handleFail(commonResult.getMessage());
        }

    }
}
