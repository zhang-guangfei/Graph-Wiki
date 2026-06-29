package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.service.ExpModelServiceFeignApi;
import com.smc.smccloud.service.PdServiceFeignApi;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/10/31 12:07
 * @Descripton TODO
 */
@Component
@Slf4j
public class ReportHandler {
    @Resource
    private ExpModelServiceFeignApi expModelServiceFeignApi;

    @Resource
    private PdServiceFeignApi pdServiceFeignApi;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    /**
     * 销售E差率统计报表
     * @return
     */
    @XxlJob(value = "sendSalesEDiscountReport", init = "init", destroy = "destroy")
    public ReturnT<String> sendSalesEDiscountReport() throws Exception {
        String param = XxlJobHelper.getJobParam();
        String date = "";
        if (StringUtils.isEmpty(param)) {
            Date currentDate = DateUtil.getCurrentDate();
            date = DateUtil.getYearMonth(DateUtil.addMonth(currentDate,-1));
        } else {
            if (PublicUtil.showCountByStr(param) != 1) {
                XxlJobHelper.handleFail("正确参数格式yyyy-MM,请检查.");
                return ReturnT.FAIL;
            }
            date = param;
        }
        ResultVo<String> resultVo = expModelServiceFeignApi.sendSalesEDiscountReport(date);
        if (resultVo.isSuccess()) {
            XxlJobHelper.log(resultVo.getData());
        } else {
            XxlJobHelper.handleFail(resultVo.getMessage());
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;

    }

    @XxlJob(value = "execYdPdStepByPlan", init = "init", destroy = "destroy")
    public ReturnT<String> execYdPdStepByPlan() throws Exception {

        ResultVo<String> resultVo = pdServiceFeignApi.execYdPdStepByPlan();
        if (resultVo.isSuccess()) {
            XxlJobHelper.log(resultVo.getData());
        } else {
            XxlJobHelper.handleFail(resultVo.getMessage());
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;

    }

    @XxlJob(value = "execYdOpsExtractPdStepByPlan", init = "init", destroy = "destroy")
    public ReturnT<String> execYdOpsExtractPdStepByPlan() throws Exception {

        ResultVo<String> resultVo = pdServiceFeignApi.execYdOpsExtractPdStepByPlan();
        if (resultVo.isSuccess()) {
            XxlJobHelper.log(resultVo.getData());
        } else {
            XxlJobHelper.handleFail(resultVo.getMessage());
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;

    }

    @XxlJob(value = "execYdWmsExtractPdStepByPlan", init = "init", destroy = "destroy")
    public ReturnT<String> execYdWmsExtractPdStepByPlan() throws Exception {

        ResultVo<String> resultVo = pdServiceFeignApi.execYdWmsExtractPdStepByPlan();
        if (resultVo.isSuccess()) {
            XxlJobHelper.log(resultVo.getData());
        } else {
            XxlJobHelper.handleFail(resultVo.getMessage());
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;

    }

    /**
     *
     * 先行在库命中率基础数据计算JOB
     * @return
     * @throws Exception
     */
    @XxlJob(value = "preOrderHitRate", init = "init", destroy = "destroy")
    public ReturnT<String> preOrderHitRate() throws Exception {

        String param = XxlJobHelper.getJobParam();
        ResultVo<String> resultVo = null;
        if (StringUtils.isNotBlank(param)) {
            resultVo = pdServiceFeignApi.preOrderHitRate(param);
        } else {
            resultVo = pdServiceFeignApi.preOrderHitRate("");
        }
        if (resultVo.isSuccess()) {
            XxlJobHelper.log(resultVo.getData());
        } else {
            XxlJobHelper.handleFail(resultVo.getMessage());
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;

    }
}
