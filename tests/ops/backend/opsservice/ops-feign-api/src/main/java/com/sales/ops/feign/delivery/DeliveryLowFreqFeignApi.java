package com.sales.ops.feign.delivery;

import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author ：C14717
 * @version: 1.0$
 * @description：Opswm
 * @date ：Created in 2024/07/9 13:05
 */
@FeignClient(name = "delivery-server", contextId = "lowFreq")
public interface DeliveryLowFreqFeignApi {

    /**
     * bugid: 14635 顾客在库低频型号的自动获取及订单 拦截
     * @return
     */
    @RequestMapping(value = "/do/inv/lowFreq/last8", method = RequestMethod.GET)
    CommonResult<String> lastEightMonthsLowFreq();

}
