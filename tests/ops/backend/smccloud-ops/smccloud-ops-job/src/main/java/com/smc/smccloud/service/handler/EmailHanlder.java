package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.EmailParseNew;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EmailHanlder {
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;


    @Resource
    private EmailParseNew emailParseNew;

    public void init() {
        // SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    @XxlJob(value = "parseEmailFile", init = "init", destroy = "destroy")
    public ResultVo<String> parseEmailFile() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);

        /**
         * 新邮箱解析
         */
        ResultVo<String> stringResultVo = emailParseNew.emailParseNew();
        if(stringResultVo.isSuccess()) {
            XxlJobHelper.log("[新邮箱解析] ==> {}", stringResultVo.getData());
            XxlJobHelper.handleSuccess(stringResultVo.getData());
            return ResultVo.success(stringResultVo.getData());
        } else {
            XxlJobHelper.log("[新邮箱解析] ==> {}", stringResultVo.getMessage());
            XxlJobHelper.handleFail(stringResultVo.getMessage());
            return ResultVo.failure(stringResultVo.getMessage());
        }
    }


}
