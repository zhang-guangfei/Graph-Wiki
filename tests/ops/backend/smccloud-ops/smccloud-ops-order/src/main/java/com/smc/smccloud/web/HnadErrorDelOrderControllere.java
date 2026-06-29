package com.smc.smccloud.web;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.OrderStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2022/9/8 13:53
 * @Descripton TODO
 */
@RestController
@Slf4j
@RequestMapping("/order/handDelOrder")
public class HnadErrorDelOrderControllere {

    @Resource
    private OrderStateService orderStateService;

    @GetMapping("/handDelOrder")
    public ResultVo<String> handErrorDelOrder() {
       return orderStateService.handDelErrorOrder();
    }


}
