package com.smc.smccloud.service;


import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.borrowstock.BrwApplyDTO;
import com.smc.smccloud.service.hystrix.BorrowStockFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "shareapp-service",
        contextId = "borrowstock",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = BorrowStockFeignApiHystrix.class)
public interface BorrowStockFeignApi {

    /**
     * 借货申请
     *
     * @param brwApplyDTO
     * @return
     */
    @RequestMapping(value = "/shareapp/borrowstock/createApply", method = RequestMethod.POST)
    ResultVo<String> createBorrowStockApply(@RequestBody BrwApplyDTO brwApplyDTO);

}
