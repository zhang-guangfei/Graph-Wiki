package com.smc.smccloud.web;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.DelayOrderFeignApi;
import com.smc.smccloud.service.OpsDelayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/report")
public class DelayOrderController implements DelayOrderFeignApi {

    @Autowired
    private OpsDelayOrderService opsDelayOrderService;

    @GetMapping("/delayOrder")
    public ResultVo calDelayOrderList() {
        try {
            String fileName = opsDelayOrderService.exportDelayOrderInfo();
            opsDelayOrderService.sendEmail(fileName);
            return ResultVo.success("计算成功");
        } catch (Exception e) {
            log.error("{}", e);
            return ResultVo.failure(e.getMessage());
        }
    }


}
