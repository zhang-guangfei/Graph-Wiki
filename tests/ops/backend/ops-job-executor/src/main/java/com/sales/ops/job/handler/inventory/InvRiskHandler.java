package com.sales.ops.job.handler.inventory;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsAdjustFeignApi;
import com.sales.ops.feign.inventory.InvRiskFeign;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：C14717
 * @version: $ 19127
 * @description：
 * @date ：Created in 2025/11/4 11:01
 */
@Component
public class InvRiskHandler {

    @Autowired
    private InvRiskFeign invRiskFeign;

    /**
     * 风险在库数据收集执行器
     */
    @XxlJob("invRisk")
    public void invRisk() {
        XxlJobHelper.log("开始执行 " );
        ResultVo<String> resultVo = invRiskFeign.handleExe();
        XxlJobHelper.log("执行结束 ");
        if (!resultVo.isSuccess()) {
            XxlJobHelper.handleFail("执行失败：" + resultVo.getMessage());
            XxlJobHelper.log("执行失败 ");
        }
    }
}
