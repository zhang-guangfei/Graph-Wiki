package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.ordermodify.PurchaseModifyVO;
import com.smc.smccloud.service.hystrix.PurchaseModifyApplyFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *  SampleOrderApplyFeignApi
 */
@FeignClient(name = "shareapp-service",
        contextId = "purchasemodify",
        fallbackFactory = PurchaseModifyApplyFeignApiHystrix.class,
        configuration = AuthFeignAutoConfiguration.class)
public interface PurchaseModifyApplyFeignApi {

    /**
     * 定时任务，执行待处理的单据清单
     * @return
     */
    @RequestMapping(value = "/shareapp/purchasemodify/batchHandelData", method = RequestMethod.POST)
    ResultVo<String> handlePurchaseModify();

    @PostMapping("/shareapp/purchasemodify/insertPurchaseModify")
    ResultVo<String> insertPurchaseModify(@RequestBody PurchaseModifyVO vo);

}
