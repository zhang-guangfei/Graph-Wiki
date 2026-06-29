package com.smc.smccloud.service.handler;

import cn.hutool.json.JSONUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.OpsSalesNoticeTaskFeignApi;
import com.smc.smccloud.service.OrderModifyServiceFeignApi;
import com.smc.smccloud.service.PdServiceFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2022/7/3 11:19
 * @Descripton TODO
 */

@Component
public class CommonHanlder {

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Resource
    private PdServiceFeignApi pdServiceFeignApi;

    @Resource
    private OpsSalesNoticeTaskFeignApi opsSalesNoticeTaskFeignApi;

    @Resource
    private OrderModifyServiceFeignApi orderModifyServiceFeignApi;


    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }


    /**
     * 每天缓存客户信息
     */
    @XxlJob(value = "cacheAllCustomerInfo", init = "init", destroy = "destroy")
    public void cacheAllCustomerInfo() throws Exception {
        XxlJobHelper.log("==> 进入每天缓存客户信息");
        commonServiceFeignApi.cacheAllCustomerInfo();
    }

    /**
     * 每天缓存职员信息
     */
    @XxlJob(value = "cacheAllEmployee", init = "init", destroy = "destroy")
    public void cacheAllEmployee() throws Exception {
        XxlJobHelper.log("==> 进入每天缓存职员信息");
        commonServiceFeignApi.cacheAllEmployee();
    }

    /**
     * 每天缓存部门信息
     */
    @XxlJob(value = "cacheAllDepartmentInfo", init = "init", destroy = "destroy")
    public void cacheAllDepartmentInfo() throws Exception {
        XxlJobHelper.log("==> 进入每天缓存部门信息");
        commonServiceFeignApi.cacheAllDepartmentInfo();
    }
    /**
     * ops_customer表 客户名称>>英文名称
     */
    @XxlJob(value = "translateCustomerNameToEnglish", init = "init", destroy = "destroy")
    public void translateCustomerNameToEnglish() {
        XxlJobHelper.log("==> ops_customer表 客户名称>>英文名称 定时作业");
        ResultVo<String> resultVo = commonServiceFeignApi.translateCustomerNameToEnglish();
        XxlJobHelper.log("执行结果 {}", JSONUtil.toJsonPrettyStr(resultVo));
    }

    /**
     * 定时执行盘点其他录入数据程序
     */
    @XxlJob(value = "execPdOtherIpm", init = "init", destroy = "destroy")
    public void execPdOtherIpm() {
        XxlJobHelper.log("==> 定时执行盘点其他录入数据程序");
        ResultVo<String> resultVo = pdServiceFeignApi.execPdImpDataTask();
        XxlJobHelper.log("执行结果 {}", JSONUtil.toJsonPrettyStr(resultVo));
    }

    /**
     * 门户共通接口 定时执行待执行的任务
     */
    @XxlJob(value = "execOpsSalesNoticeTask", init = "init", destroy = "destroy")
    public void execOpsSalesNoticeTask() {
        XxlJobHelper.log("==>  进入定时执行待执行的任务");
        ResultVo<String> resultVo = opsSalesNoticeTaskFeignApi.opsSalesNoticeTask();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("定时执行待执行任务有误 =>{}", resultVo.getMessage());
            XxlJobHelper.handleFail("定时执行待执行任务有误"+resultVo.getMessage());
            return;
        }
        XxlJobHelper.log("执行结果 {}", JSONUtil.toJsonPrettyStr(resultVo));
    }

    @XxlJob(value = "execOpsSalesNoticeTask2", init = "init", destroy = "destroy")
    public void execOpsSalesNoticeTask2() {
        XxlJobHelper.log("==>  进入定时执行待执行的任务2");
        ResultVo<String> resultVo = opsSalesNoticeTaskFeignApi.opsSalesNoticeTask2();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("定时执行待执行任务有误 =>{}", resultVo.getMessage());
            XxlJobHelper.handleFail("定时执行待执行任务有误"+resultVo.getMessage());
            return;
        }
        XxlJobHelper.log("执行结果 {}", JSONUtil.toJsonPrettyStr(resultVo));
    }


    /**
     * 门户共通接口 定时执行已执行任务回调接口
     */
    @XxlJob(value = "execCallBaclOpsSalesNoticeTask", init = "init", destroy = "destroy")
    public void execCallBaclOpsSalesNoticeTask() {
        XxlJobHelper.log("==> 进入定时执行已执行任务回调接口");
        ResultVo<String> resultVo =  opsSalesNoticeTaskFeignApi.opsSalesCallBackNoticeTask();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("定时执行已执行任务回调接口有误 =>{}", resultVo.getMessage());
            XxlJobHelper.handleFail("定时执行已执行任务回调接口有误"+resultVo.getMessage());
            return;
        }
        XxlJobHelper.log("执行结果 {}", JSONUtil.toJsonPrettyStr(resultVo));
    }

    @XxlJob(value = "execCallBaclOpsSalesNoticeTask2", init = "init", destroy = "destroy")
    public void execCallBaclOpsSalesNoticeTask2() {
        XxlJobHelper.log("==> 进入定时执行已执行任务回调接口2");
        ResultVo<String> resultVo =  opsSalesNoticeTaskFeignApi.opsSalesCallBackNoticeTask2();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("定时执行已执行任务回调接口有误 =>{}", resultVo.getMessage());
            XxlJobHelper.handleFail("定时执行已执行任务回调接口有误"+resultVo.getMessage());
            return;
        }
        XxlJobHelper.log("执行结果 {}", JSONUtil.toJsonPrettyStr(resultVo));
    }

    /**
     * 13191bug 订单修改删单优化 增加定时任务 若订单状态为已发货或者已开票，将该删单申请自动否决
     */
    @XxlJob(value = "autoHandNotCancelData", init = "init", destroy = "destroy")
    public void autoHandNotCancelData() {
        XxlJobHelper.log("==> 进入定时执行订单状态为已发货或者已开票，将该删单申请自动否决接口");
        ResultVo<String> resultVo =  orderModifyServiceFeignApi.autoHandNotCancelData();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("定时执行订单状态为已发货或者已开票，将该删单申请自动否决接口有误 =>{}", resultVo.getMessage());
            XxlJobHelper.handleFail("定时执行订单状态为已发货或者已开票，将该删单申请自动否决接口有误"+resultVo.getMessage());
            return;
        }
        XxlJobHelper.log("执行结果 {}", JSONUtil.toJsonPrettyStr(resultVo));
    }
}
