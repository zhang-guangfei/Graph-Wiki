package com.sales.ops.feign.notify;

import com.sales.ops.dto.replacement.NotifyShipmentPlanResult;
import com.sales.ops.dto.replacement.NotifyShipmentPlanSaveDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/9/23 10:14
 */
@FeignClient(name = "wm-service", contextId = "NotifyShipmentPlanSMSFeignAPi")
public interface NotifyShipmentPlanSMSFeignAPi {

    @RequestMapping(value = "/notify/shipment/create/save/List", method = RequestMethod.POST)
    ResultVo<NotifyShipmentPlanResult> saveList(@RequestBody List<NotifyShipmentPlanSaveDto> dataList, @RequestParam(value = "userName") String userName );

}
