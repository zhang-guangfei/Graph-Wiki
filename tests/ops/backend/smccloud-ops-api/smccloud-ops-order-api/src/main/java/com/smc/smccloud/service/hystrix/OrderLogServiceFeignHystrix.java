package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.orderlog.OrderLogRequest;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.OrderLogFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author lyc
 * @Date 2021/12/24 16:29
 * @Descripton TODO
 */
@Component
public class OrderLogServiceFeignHystrix implements FallbackFactory<OrderLogFeignApi> {
    @Override
    public OrderLogFeignApi create(Throwable throwable) {
        return new OrderLogFeignApi() {
            @Override
            public ResultVo<String> addOrderLog(@Valid OrderLogVO orderLogVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> sendOrderLogMsgToMQ(@Valid OrderLogVO orderLogVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PageInfo<OrderLogVO>> findOrderLog(OrderLogRequest orderLogRequest) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> insertOrderStateLog(OrderStateVO orderStateVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> selectLogByIds(String id) {
                return null;
            }
        };
    }
}
