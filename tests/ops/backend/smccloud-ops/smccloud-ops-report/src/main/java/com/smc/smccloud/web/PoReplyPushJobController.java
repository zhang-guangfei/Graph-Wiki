package com.smc.smccloud.web;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.PurchaseReplyPushJobService;
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
public class PoReplyPushJobController {



    @Autowired
    private PurchaseReplyPushJobService purchaseReplyPushJobService;

    @GetMapping("/poReply/exportExcel")
    public ResultVo poReplyExportExcel() {
        try {
            purchaseReplyPushJobService.handle();
            return ResultVo.success("成功");
        } catch (Exception e) {
            log.error("{}", e);
            return ResultVo.failure(e.getMessage());
        }
    }
}
