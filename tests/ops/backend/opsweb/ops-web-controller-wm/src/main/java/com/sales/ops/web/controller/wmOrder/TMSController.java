package com.sales.ops.web.controller.wmOrder;

import cn.hutool.json.JSONUtil;
import com.sales.ops.dto.tms.OrderRouterParam;
import com.sales.ops.dto.tms.TmsTrackingResult;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.serviceimpl.annotation.SysLog;
import com.sales.ops.serviceimpl.dispatch.dodispatch.service.TMSRouteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order/tms")
public class TMSController {

    @Autowired
    private TMSRouteHandler tmsRouteHandler;

    @SysLog("tms路由信息回传")
    @PostMapping("/route/send")
    public CommonResult sendOrderRoute(@RequestBody List<OrderRouterParam> header) {
        log.info("tms路由推送：{}", JSONUtil.toJsonPrettyStr(header));
        List<String> batchNos = new ArrayList<>();
        for (OrderRouterParam param : header) {
            tmsRouteHandler.sendOrderRoute(param);
            batchNos.add(param.getBatchNo());
        }
        return CommonResult.success("处理成功", batchNos);
    }

    @GetMapping("/route/get")
    public CommonResult getOrderRoute(@RequestParam String expressNo) {
        TmsTrackingResult orderRoute = tmsRouteHandler.getOrderRoute(expressNo);
        return CommonResult.success("处理成功", orderRoute);
    }


}
