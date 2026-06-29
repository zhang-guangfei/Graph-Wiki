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
public class OpsShikomiFileHandler {

    @Resource
    private ProductServiceFeignApi productServiceFeignApi;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    @XxlJob(value = "autoShikomiFile", init = "init", destroy = "destroy")
    public void autoShikomiFile() throws Exception {
        XxlJobHelper.log("==> 进入自动导入shikomi文件执行器");
        ResultVo<String> stringResultVo = productServiceFeignApi.autoImportShikomiFile();
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log(stringResultVo.getMessage());
            XxlJobHelper.handleFail("自动导入shikomi文件失败"+stringResultVo.getMessage());
            return;
        }
        XxlJobHelper.log(stringResultVo.getMessage());
        XxlJobHelper.handleSuccess(stringResultVo.getData());
    }

    @XxlJob(value = "shikomiInspectionCalcNew", init = "init", destroy = "destroy")
    public void shikomiInspectionCalcNew() throws Exception {

        XxlJobHelper.log("==> shikomi预决算计算开始");

        ResultVo<String> stringResultVo =productServiceFeignApi.shikomiInspectionCalcNew();

        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.handleFail(stringResultVo.getMessage());
        } else {
            XxlJobHelper.handleSuccess("计算完毕");
        }
    }
}
