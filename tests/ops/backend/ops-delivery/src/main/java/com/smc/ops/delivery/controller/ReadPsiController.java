package com.smc.ops.delivery.controller;

import com.sales.ops.dto.ips.IpsSendDeliveryInfoToOpsDto;
import com.smc.ops.delivery.service.psi.IpsSendDeliveryInfoToOpsService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/read/psi")
@Slf4j
public class ReadPsiController {

    @Resource
    private IpsSendDeliveryInfoToOpsService ipsSendDeliveryInfoToOpsService;

    @GetMapping("/queryIpsSendDeliveryInfoToOpsList")
    public ResultVo<List<IpsSendDeliveryInfoToOpsDto>> queryIpsSendDeliveryInfoToOpsList() {
        return ipsSendDeliveryInfoToOpsService.queryIpsSendDeliveryInfoToOpsList();
    }


}
