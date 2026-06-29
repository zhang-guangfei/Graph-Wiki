package com.sales.ops.feign.product;

import com.sales.ops.dto.common.BomSelectParam;
import com.sales.ops.dto.common.BomSelectResult;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: $ bugid:17799
 * @description：
 * @date ：Created in 2025/6/17 10:21
 */
@FeignClient(name = "wm-service", contextId = "BomSelector")
public interface BomSelectorFeignApi {
    @RequestMapping(value = "/wm/api/bom/bomSelector", method = RequestMethod.GET)
    ResultVo<BomSelectResult> bomSelector(@RequestBody BomSelectParam param);
}
