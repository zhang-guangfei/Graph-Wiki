package com.sales.ops.job.handler.inquiry;

import com.sales.ops.feign.inquiry.InquiryJobFeignApi;
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
public class OpsInquiryHandler {

    @Autowired
    private InquiryJobFeignApi inquiryJobFeignApi;

    /**
     * 待处理的催促单，发送到供应商
     */
    @XxlJob(value = "purchaseInquirySend")
    public void purchaseInquirySend() throws Exception {
        try {
            XxlJobHelper.log("==> 开始执行催促处理任务 => 未处理数据发送到供应商执行");
            ResultVo<String> commonResult = inquiryJobFeignApi.purchaseSendJob();
            if (commonResult.isSuccess()) {
                XxlJobHelper.handleSuccess(commonResult.getMessage());
            } else {
                XxlJobHelper.log("催促订单发送供应商 执行失败 ==> {}", commonResult.getMessage());
                XxlJobHelper.handleFail("催促订单发送供应商 执行失败");
                throw new Exception(commonResult.getMessage());
            }
        } catch (Exception e) {
            XxlJobHelper.log("催促订单发送供应商 执行失败 ==> {}", e.getMessage());
            XxlJobHelper.handleFail("催促订单发送供应商 执行失败");
            throw e;
        }
    }


    /**
     * 待回复催促单，轮询供应商回复表，查找回复结果
     */
    @XxlJob(value = "purchaseInquiryReply")
    public void purchaseInquiryReply() throws Exception {
        try {
            XxlJobHelper.log("==> 开始执行催促处理任务 => 待回复催促信息查询供应商");
            ResultVo<String> commonResult = inquiryJobFeignApi.purchaseReplyJob();
            if (!commonResult.isSuccess()) {
                XxlJobHelper.log("日次获取供应商待回复信息 执行失败 ==> {}", commonResult.getMessage());
                XxlJobHelper.handleFail("日次获取供应商待回复信息 执行失败");
                throw new Exception(commonResult.getMessage());
            } else {
                XxlJobHelper.log("日次获取供应商待回复信息 执行成功 ==> {}", commonResult.getMessage());
            }
        } catch (Exception e) {
            XxlJobHelper.log("日次获取供应商待回复信息 执行失败 ==> {}", e.getMessage());
            XxlJobHelper.handleFail("日次获取供应商待回复信息 执行失败");
            throw e;
        }
    }


    /**
     * 增量获取回复信息，记录上次执行的最大值，查找回复结果
     */
    @XxlJob(value = "purchaseInquiryReplyIncrement")
    public void purchaseInquiryReplyIncrement() throws Exception {
        try {
            XxlJobHelper.log("==> 开始执行催促处理任务 => 增量执行供应商回复数据");
            ResultVo<String> commonResult = inquiryJobFeignApi.purchaseReplyIncrementJob();
            if (!commonResult.isSuccess()) {
                XxlJobHelper.log("增量执行供应商回复数据\" 执行失败 ==> {}", commonResult.getMessage());
                XxlJobHelper.handleFail("增量执行供应商回复数据\" 执行失败");
                throw new Exception(commonResult.getMessage());
            } else {
                XxlJobHelper.log("增量执行供应商回复数据\" 执行成功 ==> {}", commonResult.getMessage());
            }
        } catch (Exception e) {
            XxlJobHelper.log("增量执行供应商回复数据 执行失败 ==> {}", e.getMessage());
            XxlJobHelper.handleFail("增量执行供应商回复数据 执行失败");
            throw e;
        }
    }




}
