package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.OrderLimitPromptFeignApi;
import com.smc.smccloud.service.OrderLimitPromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：C14717
 * @version: $
 * @description： bugid:18113
 * @date ：Created in 2025/7/14 13:56
 */
@RestController
public class OrderLimitPromptFeignClient implements OrderLimitPromptFeignApi {

    @Autowired
    private OrderLimitPromptService orderLimitPromptService;

    /**
     * 5.定时job
     * @return
     */
    @Override
    public ResultVo handle(){
        try {
            return orderLimitPromptService.handle();
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }
}
