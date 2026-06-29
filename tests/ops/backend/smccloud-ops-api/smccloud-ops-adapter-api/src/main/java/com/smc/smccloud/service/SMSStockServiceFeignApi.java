package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.stock.AdapterPreStockDTO;
import com.smc.smccloud.model.stock.InventoryCondition;
import com.smc.smccloud.model.stock.InventoryListDto;
import com.smc.smccloud.service.hystrix.SMSStockServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-07-12 18:06
 * Description:
 */
@FeignClient(name = "adapter-service",
        contextId = "adapter-stock",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = SMSStockServiceFeignHystrix.class)
public interface SMSStockServiceFeignApi {

    /**
     * 发送先行在库处理结果消息队列
     *
     * @param dto 行在库处理结果
     * @return boolean
     */
    @RequestMapping(value = "/adapter/order/sendPreStockDetailStatusMessage", method = RequestMethod.POST)
    ResultVo<Boolean> sendPreStockDetailStatusMessage(@RequestBody AdapterPreStockDTO dto);


    @RequestMapping(value = "/adapter/inventory/find", method = RequestMethod.POST)
    ResultVo<List<InventoryListDto>> findInventory(@RequestBody InventoryCondition condition);

}
