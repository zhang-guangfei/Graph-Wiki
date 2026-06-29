package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.OrderDeleteReturn;
import com.smc.smccloud.model.ordermodify.ApproveOrderModifyDTO;
import com.smc.smccloud.model.ordermodify.OrderModifyVO;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.service.OrderModifyServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2022/1/13 16:15
 */
@Component
public class OrderModifyServiceFeignHystrix implements FallbackFactory<OrderModifyServiceFeignApi> {
    @Override
    public OrderModifyServiceFeignApi create(Throwable cause) {
        return new OrderModifyServiceFeignApi() {

            @Override
            public ResultVo<List<OrderDeleteReturn>> applyOrderCancel(List<OrderModifyVO> list) {
                return ResultVo.failure("服务降级");
            }

//            @Override
//            public ResultVo<String> modifyOrderEprice(Date fromTime) {
//                return ResultVo.failure("服务降级");
//            }

            @Override
            public ResultVo<String> calcImportLotEPrice() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> upTaskInfoByBatchNo(UpTaskInfoVO param) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> autoHandNotCancelData() {
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<String> calcExportLotEPrice() {
                return ResultVo.failure("服务降级");
            }
        };
    }
}
