package com.sales.ops.job.handler.delivery;

import com.sales.ops.dto.util.CommonResult;
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
public class OpsOrderModifyRecordHandler {

    @Autowired
    private DeliveryOrderModifyFeignApi deliveryOrderModifyFeignApi;


    @XxlJob("deliverOrderModifyReportSend")
    public void getBar() {
        CommonResult<String> commonResult = deliveryOrderModifyFeignApi.orderModifyReport();
        XxlJobHelper.log("执行结果:"+commonResult.isSuccess());
    }
}
