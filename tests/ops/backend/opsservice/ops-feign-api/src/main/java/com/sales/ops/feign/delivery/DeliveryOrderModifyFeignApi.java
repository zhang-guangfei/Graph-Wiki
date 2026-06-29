package com.sales.ops.feign.delivery;

import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author ：C14717
 * @version: 1.0$
 * @description：Opswm
 * @date ：Created in 2024/07/4 13:05
 */
@FeignClient(name = "delivery-server", contextId = "orderModify")
public interface DeliveryOrderModifyFeignApi {

    /**
     * bugid: 14562 订单还原、转定数据定时发送
     * @return
     */
    @RequestMapping(value = "/do/order/send/orderModifyReport", method = RequestMethod.GET)
    CommonResult<String> orderModifyReport();

}
