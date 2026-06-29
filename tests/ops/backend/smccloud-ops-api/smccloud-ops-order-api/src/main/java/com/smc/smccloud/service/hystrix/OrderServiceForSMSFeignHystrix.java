package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.*;
import com.smc.smccloud.model.orderstate.OrderStateDetailDTO;
import com.smc.smccloud.service.OrderServiceForSMSFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author edp04
 * @title: OrderServiceForSMSFeignHystrix
 * @date 2022/05/10 17:22
 */
@Component
public class OrderServiceForSMSFeignHystrix implements FallbackFactory<OrderServiceForSMSFeignApi> {
    @Override
    public OrderServiceForSMSFeignApi create(Throwable cause) {
        return new OrderServiceForSMSFeignApi() {

            @Override
            public ResultVo<List<OrderDetailVO>> listOrderDetailForAgent(OrderDetailDTO detailDTO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<OrderStateDetailDTO> listOrderStateByNo(String order_no) {
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<List<POOrderNOVO>> listPOOrder(List<POOrderNODTO> orderNos) {
                return ResultVo.failure("服务降级");
            }
        };
    }
}
