package com.sales.ops.feign.inqb;

import com.sales.ops.db.entity.OpsInqbCodeConfig;
import com.sales.ops.dto.inqb.OpsInqbHandle;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * inqb 对接集团采购项目，通用接口
 * , url = "${gps.url: }"
 */
@FeignClient(name = "smccloud-gps-service", contextId = "groupPurchase")
public interface InqbGroupPurchaseFeignApi {

    @RequestMapping(value = "/inqb/handle/addInqbHandle", method = RequestMethod.POST)
    ResultVo<String> addInqbHandle(@RequestBody OpsInqbHandle opsInqbHandle);


    @RequestMapping(value = "/inqb/handle/getInqbReason", method = RequestMethod.POST)
    ResultVo<List<OpsInqbCodeConfig>> getInqbReason();
}
