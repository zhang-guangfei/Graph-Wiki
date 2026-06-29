package com.smc.ops.delivery.controller;

import cn.hutool.core.bean.BeanUtil;
import com.sales.ops.db.entity.IpsReceiveSignImpInfoFromAll;
import com.sales.ops.dto.ips.IpsReceiveDeliveryInfoFromAllVO;
import com.sales.ops.dto.ips.IpsSignDataBatchPushVO;
import com.sales.ops.dto.util.CommonResult;
import com.smc.ops.delivery.mapper.IpsReceiveDeliveryInfoFromOpsMapper;
import com.smc.ops.delivery.mapper.IpsReceiveSignImpInfoFromAllMapper;
import com.smc.ops.delivery.model.ips.IpsReceiveDeliveryInfoFromOpsDO;
import com.smc.ops.delivery.model.ips.IpsReceiveSignImpInfoFromAllDO;
import com.smc.ops.delivery.service.psi.IpsReceiveSignDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/ips")
@Slf4j
public class OpsDeliveryDataToManuController {

    @Autowired
    private IpsReceiveDeliveryInfoFromOpsMapper ipsReceiveDeliveryInfoFromOpsMapper;
    @Autowired
    private IpsReceiveSignImpInfoFromAllMapper ipsReceiveSignImpInfoFromAllMapper;
    @Autowired
    private IpsReceiveSignDataService ipsReceiveSignDataService;


    @RequestMapping(value = "/deliveryData/push", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<IpsReceiveDeliveryInfoFromAllVO> pushOpsDeliveryData(@RequestBody IpsReceiveDeliveryInfoFromAllVO data) {
        try {
            IpsReceiveDeliveryInfoFromOpsDO DO = BeanUtil.copyProperties(data, IpsReceiveDeliveryInfoFromOpsDO.class);
            int i = ipsReceiveDeliveryInfoFromOpsMapper.insert(DO);
            if (i == 1) {
                return CommonResult.success(data);
            }
            return CommonResult.failure();
        } catch (Exception e) {
            log.error("插入IpsReceiveDeliveryInfoFromOps失败", e);
            return CommonResult.failure(e.getMessage());
        }
    }


    @RequestMapping(value = "/posignData/push", method = RequestMethod.POST)
    public CommonResult<IpsReceiveSignImpInfoFromAll> pushOpsSignData(@RequestBody IpsReceiveSignImpInfoFromAll data) {
        try {
            IpsReceiveSignImpInfoFromAllDO DO = BeanUtil.copyProperties(data, IpsReceiveSignImpInfoFromAllDO.class);
            int i = ipsReceiveSignImpInfoFromAllMapper.insert(DO);
            if (i == 1) {
                return CommonResult.success(data);
            }
            return CommonResult.failure();
        } catch (Exception e) {
            log.error("插入IpsReceiveSignImpInfoFromAll失败", e);
            return CommonResult.failure(e.getMessage());
        }
    }

    // bug 19413.38 批量推送签收信息，发票主表和明细表使用同一个事务
    @RequestMapping(value = "/posignData/pushBatch", method = RequestMethod.POST)
    public CommonResult<Void> pushOpsSignDataBatch(@RequestBody IpsSignDataBatchPushVO data) {
        try {
            ipsReceiveSignDataService.batchInsertSignData(data);
            return CommonResult.success(null);
        } catch (Exception e) {
            log.error("批量插入IpsReceiveSignImpInfoFromAll失败", e);
            return CommonResult.failure(e.getMessage());
        }
    }

}
