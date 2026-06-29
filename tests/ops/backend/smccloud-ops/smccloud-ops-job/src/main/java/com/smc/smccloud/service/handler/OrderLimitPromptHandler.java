package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.service.OrderLimitPromptFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：C14717
 * @version: $
 * @description： bugid:18113
 * @date ：Created in 2025/7/14 14:05
 */
@Component
public class OrderLimitPromptHandler {
    @Autowired
    private OrderLimitPromptFeignApi orderLimitPromptFeignApi;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    /**
     * 每天缓存客户信息
     */
    @XxlJob(value = "orderLimitPromptHandle", init = "init", destroy = "destroy")
    public void orderLimitPromptHandle() throws Exception {
        try {
            ResultVo<String> handle = orderLimitPromptFeignApi.handle();
            XxlJobHelper.log("==>执行： "+handle.isSuccess());
            if (handle.isSuccess()) {
                XxlJobHelper.handleSuccess("执行成功");
            } else {
                XxlJobHelper.handleFail("执行失败");
            }
        } catch (Exception e) {
            XxlJobHelper.log("==> "+e.getMessage());
            XxlJobHelper.handleFail("执行失败"+e.getMessage());
        }
    }


}
