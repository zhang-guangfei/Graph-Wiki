package com.sales.ops.job.handler.delivery;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.delivery.BranchFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/12/29 13:32
 */

@Component
public class BranchHandler {

    @Autowired
    private BranchFeignApi branchFeignApi;

    // 16334
    @XxlJob("branchHandle")
    public void branchHandle() {
        CommonResult<String> commonResult = branchFeignApi.branchHandle();
        XxlJobHelper.log("执行完成");
    }


    //16354
    @XxlJob("branchZHHandle")
    public void branchZHHandle() {
        CommonResult<String> commonResult = branchFeignApi.branchZHHandle();
        XxlJobHelper.log("执行完成");
    }

}
