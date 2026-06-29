package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.service.OrderService;
import com.smc.smccloud.service.SMSOrderServiceFeignApi;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-04-02 13:33
 * Description:
 */
@RestController
public class SMSOrderServiceFeignClient implements SMSOrderServiceFeignApi {

    @Resource
    private OrderService orderService;

    @Override
    public ResultVo<Boolean> sendOrderCancelReturnMessage(List<OrderCancelResult> resultList) {
        return orderService.sendOrderCancelReturnMessage(resultList);
    }
}
