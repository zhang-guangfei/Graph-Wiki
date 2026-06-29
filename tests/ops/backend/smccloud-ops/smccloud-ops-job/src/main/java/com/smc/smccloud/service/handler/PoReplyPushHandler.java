package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.service.DelayOrderFeignApi;
import com.smc.smccloud.service.PoReplyPushJobFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PoReplyPushHandler {

    @Autowired
    private PoReplyPushJobFeignApi poReplyPushJobFeignApi;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    @XxlJob(value = "poReplyExcelExport", init = "init")
    public void demoJobHandler() throws Exception {
        ResultVo resultVo = poReplyPushJobFeignApi.poReplyExportExcel();
        XxlJobHelper.handleSuccess(resultVo.getMessage());
    }
}
