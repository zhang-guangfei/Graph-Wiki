package com.sales.ops.feign.delivery;

import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/12/29 13:30
 */

@FeignClient(name = "delivery-server", contextId = "branchApi")
public interface BranchFeignApi {

    /**
     * bugid: 16334
     * @return
     */
    @RequestMapping(value = "/do/branch/handle", method = RequestMethod.GET)
    CommonResult<String> branchHandle();


    //16354
    @RequestMapping(value = "/do/branch/zh/handle", method = RequestMethod.GET)
    CommonResult<String> branchZHHandle();


}
