package com.sales.ops.job.handler.inqb;

import com.sales.ops.feign.inqb.InqbJobFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * B91717
 * 样品出库订单抽取到 sample_bal
 */
@Component
public class OpsInqbHandler {

    @Autowired
    private InqbJobFeignApi inqbJobFeignApi;

    /**
     * 更新 INQB申请的有效性状态
     */
    @XxlJob(value = "inqbRefreshValidity")
    public void inqbRefreshValidity() throws Exception {
        XxlJobHelper.log("==> 开始执行INQB定时任务 => 更新申请有效性状态");
       try {
           ResultVo<String> commonResult = inqbJobFeignApi.refreshValidity();
           if (commonResult.isSuccess()) {
               XxlJobHelper.handleSuccess(commonResult.getData());
           } else {
               XxlJobHelper.log("更新申请有效性状态 执行失败 ==> {}", commonResult.getMessage());
               XxlJobHelper.handleFail("更新申请有效性状态 执行失败");
               throw new Exception(commonResult.getMessage());
           }
       } catch (Exception e) {
           XxlJobHelper.log("更新申请有效性状态 执行失败 ==> {}", e.getMessage());
           XxlJobHelper.handleFail("更新申请有效性状态 执行失败");
           throw e;
       }
    }


    /**
     * INQB 获取供应商回复信息
     */
    @XxlJob(value = "inqbGetReply")
    public void inqbGetReply() throws  Exception{
        XxlJobHelper.log("==> 开始执行INQB定时任务 => 获取供应商回复信息");
        try {
            ResultVo<String> commonResult = inqbJobFeignApi.opsInqbReply();

            if (commonResult.isSuccess()) {
                XxlJobHelper.handleSuccess(commonResult.getData());
            }
            else {
                XxlJobHelper.log("获取供应商回复信息 执行失败 ==> {}", commonResult.getMessage());
                XxlJobHelper.handleFail("获取供应商回复信息 执行失败");
                throw new Exception(commonResult.getMessage());
            }
        } catch (Exception e) {
            XxlJobHelper.log("获取供应商回复信息 执行失败 ==> {}", e.getMessage());
            XxlJobHelper.handleFail("获取供应商回复信息 执行失败");
            throw e;
        }
    }



}
