package com.sales.ops.job.handler;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsCustomerFeignApi;
import com.sales.ops.feign.inventory.OpsInventoryBaseFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description
 * @date 2021/10/29 16:13
 * @auther C12961
 */
@Component
public class OpsInventoryBaseHandler {

    @Resource
    OpsInventoryBaseFeignApi opsInventoryBaseFeignApi;

    @XxlJob("refreshOpsInventoryRuleAndConfigData")
    public void refreshOpsInventoryRuleAndConfigData() throws Exception {
        XxlJobHelper.log("执行 refreshOpsInventoryRuleAndConfigData ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param == null || param.trim().length() == 0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param[" + param + "] invalid.");
        }
        CommonResult<String> str = opsInventoryBaseFeignApi.refreshOpsInventoryRuleAndConfigData(param);
        XxlJobHelper.log("定时更新缓存 refreshOpsInventoryRuleAndConfigData----" + str);
    }
}
