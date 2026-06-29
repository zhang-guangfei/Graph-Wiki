package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.orderlog.OrderLogRequest;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.OrderLimitPromptFeignApi;
import com.smc.smccloud.service.OrderLogFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @Author lyc
 * @Date 2021/12/24 16:29
 * @Descripton TODO
 */
@Component
public class OrderLimitPromptFeignHystrix implements FallbackFactory<OrderLimitPromptFeignApi> {
    @Override
    public OrderLimitPromptFeignApi create(Throwable throwable) {
        return new OrderLimitPromptFeignApi() {
            @Override
            public ResultVo<String> handle() {
                return ResultVo.failure("服务降级");
            }
        };
    }
}
