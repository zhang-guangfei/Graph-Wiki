package com.sales.ops.web.controller.inventory;

import com.sales.ops.service.inventory.InvRiskService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：C14717
 * @version: $
 * @description： 19127
 * @date ：Created in 2025/11/4 10:56
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/invRisk")
public class InvRiskController {

    @Autowired
    private InvRiskService invRiskService;

    @GetMapping(value = "/data")
    public ResultVo<String> handleData(){
        try {
            invRiskService.handleExe();
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
        return ResultVo.success();
    }
}
