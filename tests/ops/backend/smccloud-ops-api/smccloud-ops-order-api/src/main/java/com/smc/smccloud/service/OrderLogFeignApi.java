package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.orderlog.OrderLogRequest;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.hystrix.OrderLogServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author lyc
 * @Date 2021/12/24 16:28
 * @Descripton TODO
 */
@FeignClient(name = "order-service",
        contextId = "orderlog",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = OrderLogServiceFeignHystrix.class)
public interface OrderLogFeignApi {

    /**
     * 增加订单操作日志
     *
     * @param orderLogVO
     * @return
     */
    @RequestMapping(value = "/order/log/add", method = RequestMethod.POST)
    ResultVo<String> addOrderLog(@Valid @RequestBody OrderLogVO orderLogVO);

    /**
     * 推送订单日志 至 MQ
     * @return
     */
    @RequestMapping(value = "/order/log/sendOrderLogMsgToMQ", method = RequestMethod.POST)
    ResultVo<String> sendOrderLogMsgToMQ(@Valid @RequestBody OrderLogVO orderLogVO);


    /**
     * 查询日志
     */
    @RequestMapping(value = "/order/log/findOrderLog", method = RequestMethod.POST)
    ResultVo<PageInfo<OrderLogVO>> findOrderLog(@RequestBody OrderLogRequest orderLogRequest);

    @RequestMapping(value = "/order/log/insertOrderStateLog", method = RequestMethod.POST)
    ResultVo<String> insertOrderStateLog(@RequestBody OrderStateVO orderStateVO);

    /**
     * test ops门户共通接口
     */
    @RequestMapping(value = "/order/log/selectLogByIds", method = RequestMethod.POST)
    ResultVo<String> selectLogByIds(@RequestBody String id);

}
