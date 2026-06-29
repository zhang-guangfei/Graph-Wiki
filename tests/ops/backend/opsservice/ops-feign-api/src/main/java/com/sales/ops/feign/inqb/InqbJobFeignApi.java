package com.sales.ops.feign.inqb;


import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * INQB-定时任务相关的feign
 */
@FeignClient(name = "delivery-server", contextId = "inqbJobFeignServer")
public interface InqbJobFeignApi {

    /**
     * 更新 INQB申请的有效性状态
     */
    @RequestMapping(value = "/do/inqb/job/refreshValidity", method = RequestMethod.POST)
    ResultVo<String> refreshValidity();

    /**
     * INQB 获取供应商回复信息
     */
    @RequestMapping(value = "/do/inqb/job/inqbReply", method = RequestMethod.POST)
    ResultVo<String> opsInqbReply();

    /**
     * INQB使用时，回传task信息
     * @param applynoList 申请单号
     * @return
     */
    @RequestMapping(value = "/do/inqb/job/inqbCallbackSalesToTask", method = RequestMethod.POST)
    ResultVo<String> callbackSalesToTask(@RequestBody List<String> applynoList);


}
