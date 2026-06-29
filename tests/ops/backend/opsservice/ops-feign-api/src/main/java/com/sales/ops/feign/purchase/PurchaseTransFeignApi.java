package com.sales.ops.feign.purchase;

import com.sales.ops.db.entity.OpsPoTranstype;
import com.sales.ops.dto.po.core.TransTypeParam;
import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/4/15 15:00
 */
@FeignClient(name = "po-service", contextId = "PurchaseTransFeignApi")
public interface PurchaseTransFeignApi {


    @RequestMapping(value = "/purchase/getTransIds", method = RequestMethod.POST)
    CommonResult<List<OpsPoTranstype>> getTransIds(@RequestBody TransTypeParam param);
}
