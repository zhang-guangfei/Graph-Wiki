package com.smc.ops.delivery.controller;

import com.sales.ops.db.entity.OpsPoUnusualOrderLog;
import com.sales.ops.dto.util.CommonResult;
import com.smc.ops.delivery.mapper.PurchaseUnusualOrderDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/po/unusual")
@Slf4j
public class PurchaseUnusualController {


    @Autowired
    private PurchaseUnusualOrderDao purchaseUnusualOrderDao;

    @RequestMapping("/getLogs")
    public CommonResult<List<OpsPoUnusualOrderLog>> getUnusualOrderLogs(@RequestParam Integer num, @RequestParam Long maxId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate){
        try {
            List<OpsPoUnusualOrderLog> logs = purchaseUnusualOrderDao.getUnusualOrderLogs(num, maxId, startDate);
            return CommonResult.success(logs);
        } catch (Exception e) {
            log.error("查询失败",e);
            return CommonResult.failure();
        }
    }

    @RequestMapping("/getLog/{id}")
    public CommonResult<OpsPoUnusualOrderLog> getUnusualLogsById(@PathVariable Long id){
        try {
            OpsPoUnusualOrderLog log = purchaseUnusualOrderDao.getUnusualOrderLogById(id);
            return CommonResult.success(log);
        } catch (Exception e) {
            log.error("查询失败",e);
            return CommonResult.failure();
        }
    }


}
