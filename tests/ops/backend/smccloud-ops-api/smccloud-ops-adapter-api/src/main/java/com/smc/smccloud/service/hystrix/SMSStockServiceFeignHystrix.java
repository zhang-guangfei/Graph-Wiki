package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.stock.AdapterPreStockDTO;
import com.smc.smccloud.model.stock.InventoryCondition;
import com.smc.smccloud.model.stock.InventoryListDto;
import com.smc.smccloud.service.SMSStockServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-07-12 18:07
 * Description:
 */
@Component
public class SMSStockServiceFeignHystrix implements FallbackFactory<SMSStockServiceFeignApi> {
    @Override
    public SMSStockServiceFeignApi create(Throwable cause) {
        return new SMSStockServiceFeignApi() {
            @Override
            public ResultVo<Boolean> sendPreStockDetailStatusMessage(AdapterPreStockDTO dto) {
                return ResultVo.failure("接口异常,服务降级.");
            }


            @Override
            public ResultVo<List<InventoryListDto>> findInventory(InventoryCondition condition) {
                return ResultVo.failure("接口异常,服务降级.");
            }
        };
    }
}
