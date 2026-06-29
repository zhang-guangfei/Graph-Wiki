package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.*;
import com.smc.smccloud.model.orderstate.OrderStateDetailDTO;
import com.smc.smccloud.service.hystrix.OrderServiceForSMSFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author edp04
 * @title: OrderServiceForSMSFeignApi
 * @date 2022/05/10 17:21
 */
@FeignClient(name = "order-service",
        contextId = "smsorder",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = OrderServiceForSMSFeignHystrix.class)
public interface OrderServiceForSMSFeignApi {

    @RequestMapping(value = "/order/smsorder/listOrderDetailForAgent", method = RequestMethod.POST)
    ResultVo<List<OrderDetailVO>> listOrderDetailForAgent(@RequestBody OrderDetailDTO detailDTO);

    @RequestMapping(value = "/order/smsorder/listOrderStateByNo", method = RequestMethod.POST)
    ResultVo<OrderStateDetailDTO> listOrderStateByNo(@RequestParam(value = "order_no") String order_no);
    //<!--add by WuWeiDong 20221104 task 2089 -->
    @RequestMapping(value = "/order/smsorder/listPOOrder", method = RequestMethod.POST)
    ResultVo<List<POOrderNOVO>> listPOOrder(@RequestBody List<POOrderNODTO> orderNos);
}
