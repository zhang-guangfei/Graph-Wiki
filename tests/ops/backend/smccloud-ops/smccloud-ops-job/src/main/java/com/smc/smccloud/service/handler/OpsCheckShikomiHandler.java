package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.service.ProductServiceFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class OpsCheckShikomiHandler {


    @Resource
    private ProductServiceFeignApi productServiceFeignApi;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    @XxlJob(value = "checkShikomiStock", init = "init", destroy = "destroy")
    public void checkShikomiStock() throws Exception {

        System.out.println("==> 进入推送shikomi警告执行器");
        XxlJobHelper.log("==> 进入推送shikomi警告执行器");

        try {
            Boolean aBoolean = productServiceFeignApi.checkShikomiStcokInsufficient();
            if (!aBoolean) {
                XxlJobHelper.handleFail("推送shikomi警告失败");
                return;
            }
        } catch (Exception e) {
            XxlJobHelper.handleFail(e.getMessage());
            return;
        }

        XxlJobHelper.handleSuccess("推送shikomi警告成功");
    }


    @XxlJob(value = "ShikomiWarningSend", init = "init", destroy = "destroy")
    public void ShikomiWarningSend() throws Exception {

        System.out.println("==> 进入推送shikomi警告执行器");
        XxlJobHelper.log("==> 进入推送shikomi警告执行器");

        try {
            ResultVo<String> resultVo = productServiceFeignApi.shikomiWarningSendMH();
            if (!resultVo.isSuccess()) {
                XxlJobHelper.handleFail("推送shikomi警告失败");
                return;
            }
        } catch (Exception e) {
            XxlJobHelper.handleFail(e.getMessage());
            return;
        }

        XxlJobHelper.handleSuccess("推送shikomi警告成功");
    }
}
