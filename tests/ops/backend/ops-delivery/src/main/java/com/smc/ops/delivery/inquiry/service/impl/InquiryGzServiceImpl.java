package com.smc.ops.delivery.inquiry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.db.entity.OpsInquiryPoHandle;
import com.sales.ops.dto.inquiry.InquiryCallbackIncrementParam;
import com.sales.ops.dto.inquiry.InquiryGzStatusEnum;
import com.sales.ops.dto.inquiry.OpsGzInquiryReply;
import com.sales.ops.dto.util.DateUtil;
import com.smc.ops.delivery.inquiry.mapper.InquiryGzMidMapper;
import com.smc.ops.delivery.inquiry.service.InquiryAdapterService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 采购催促适配器实现
 */
@Service
public class InquiryGzServiceImpl implements InquiryAdapterService {

    @Resource
    private InquiryGzMidMapper inquiryGzMidMapper;

    @Override
    public ResultVo<String> inquirySend(List<OpsInquiryPoHandle> opsInquiryPoHandle) {
        if (CollectionUtils.isEmpty(opsInquiryPoHandle)) {
            return ResultVo.failure("发送广州制造数据为空，请检查发送数据");
        }
        int result = 0;
        StringBuilder errMsg = new StringBuilder();
        Map<Integer, List<OpsInquiryPoHandle>> mapList = SplitBatchUtils.getInsertBatchBySqlserver(opsInquiryPoHandle, OpsInquiryPoHandle.class);
        for (Map.Entry<Integer, List<OpsInquiryPoHandle>> entry : mapList.entrySet()) {
            try {
                // 写入广州制造中间表
                result = result + inquiryGzMidMapper.insertGzInquiry(entry.getValue());
            } catch (Exception ex) {
                errMsg.append("采购发单写入广州制造中间表失败").append(ex.getMessage());
            }
        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure(errMsg.toString());
        }
        return ResultVo.success("采购催促写入广州制造中间表成功，共计发送" + result + "项");
    }

    @Override
    public ResultVo<List<OpsInquiryPoHandle>> inquiryCallBack(List<OpsInquiryPoHandle> callParams) {
        try {
            // 查询回复的参数
            if (CollectionUtils.isEmpty(callParams)) {
                return ResultVo.failure("获取广州制造回复信息参数为空！");
            }
            // TODO 获取回复信息，是否可以改为用记录上次id的方式进行？这样避免轮询大量的数据
            List<OpsInquiryPoHandle> replyData = new ArrayList<>();
            OpsGzInquiryReply opsGzInquiryParams;
            for (OpsInquiryPoHandle params : callParams) {
                opsGzInquiryParams = new OpsGzInquiryReply();
                opsGzInquiryParams.setTaskNo(params.getTaskNo());
                opsGzInquiryParams.setOrderNo(params.getOrderNo());
                opsGzInquiryParams.setModelNo(params.getModelNo());
                opsGzInquiryParams.setQuantity(params.getQuantity());
                opsGzInquiryParams.setStatus(InquiryGzStatusEnum.YIHUIFU.getType());
                List<OpsGzInquiryReply> opsGzInquiryReplies = inquiryGzMidMapper.getInquiryReply(opsGzInquiryParams);
                if (!CollectionUtils.isEmpty(opsGzInquiryReplies)) {
                    // todo 广州制造是否存在多次回复？
                    for (OpsGzInquiryReply opsGzInquiryReply : opsGzInquiryReplies) {
                        // 实体转换
                        params.setReplySupplierDept(opsGzInquiryReply.getReplyDept());
                        params.setReplyNo(opsGzInquiryReply.getReplyNo());
                        if (opsGzInquiryReply.getReplyDeliveryDate()!= null) {
                            // bug 16537 inq-A回复货期转换问题，日本回复货期，日期格式转换 针对99问题
                            params.setReplyDeliveryDate(opsGzInquiryReply.getReplyDeliveryDate());
                            params.setReplyDeliveryDateSrc(DateUtil.dateToDateTimeString(opsGzInquiryReply.getReplyDeliveryDate())); // 日期转换为字符串
                        }
                        params.setReplyUser(opsGzInquiryReply.getReplyUser());
                        params.setSupplierOrderNo(opsGzInquiryReply.getSupplierOrderNo());
                        params.setReplyTime(opsGzInquiryReply.getReplyTime());
                        params.setReplyDelayReason(opsGzInquiryReply.getReplyDelayReason());
                        params.setReplyRemark(opsGzInquiryReply.getReplyRemark());
                        params.setInquiryLevel(opsGzInquiryReply.getInquiryLevel());
                        params.setUpdateTime(new Date());
                        replyData.add(params);
                    }
                }
            }
            if (CollectionUtils.isEmpty(replyData)) {
                return ResultVo.successMsg("未查到相关回复信息");
            }
            return ResultVo.success(replyData);
        }
        catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    @Override
    public ResultVo<List<OpsInquiryPoHandle>> inquiryCallBackIncrement(String param) {
        // 根据updateTime获取增量数据
        try {
            // object转换实体
            InquiryCallbackIncrementParam callbackIncrementParam = JSONObject.parseObject(param, InquiryCallbackIncrementParam.class);
            // 查询回复的参数
            if (callbackIncrementParam == null || callbackIncrementParam.getCallbackIncrementLasttime() == null) {
                return ResultVo.failure("增量获取广州制造INQA结果失败，请求参数为空，请稍后错误！");
            }
            // TODO 获取回复信息，是否可以改为用记录上次id的方式进行？这样避免轮询大量的数据
            List<OpsInquiryPoHandle> replyData = new ArrayList<>();
            OpsInquiryPoHandle opsInquiryPoHandle;
            List<OpsGzInquiryReply> opsGzInquiryReplies = inquiryGzMidMapper.getInquiryIncrementReply(InquiryGzStatusEnum.YIHUIFU.getType(),  callbackIncrementParam.getCallbackIncrementLasttime());
            for (OpsGzInquiryReply opsGzInquiryReply : opsGzInquiryReplies) {
                opsInquiryPoHandle = getInquiryPoHandleByMid(opsGzInquiryReply);
                // 实体转换
                if (opsGzInquiryReply.getReplyDeliveryDate()!= null) {
                    // bug 16537 inq-A回复货期转换问题，日本回复货期，日期格式转换 针对99问题
                    opsInquiryPoHandle.setReplyDeliveryDate(opsGzInquiryReply.getReplyDeliveryDate());
                    opsInquiryPoHandle.setReplyDeliveryDateSrc(DateUtil.dateToDateTimeString(opsGzInquiryReply.getReplyDeliveryDate())); // 日期转换为字符串
                }
                replyData.add(opsInquiryPoHandle);
            }
            if (CollectionUtils.isEmpty(replyData)) {
                return ResultVo.successMsg("未查到相关回复信息");
            }
            replyData.sort(Comparator.comparing(OpsInquiryPoHandle::getReplyTime).reversed());
            return ResultVo.success(replyData);
        }
        catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    public OpsInquiryPoHandle getInquiryPoHandleByMid(OpsGzInquiryReply opsGZReply) {
        OpsInquiryPoHandle params = new OpsInquiryPoHandle();
        // 实体转换
        params.setTaskNo(opsGZReply.getTaskNo());
        params.setModelNo(opsGZReply.getModelNo());
        params.setQuantity(opsGZReply.getQuantity());
        params.setReplySupplierDept(opsGZReply.getReplyDept());
        params.setReplyNo(opsGZReply.getReplyNo());
        params.setReplyUser(opsGZReply.getReplyUser());
        params.setSupplierOrderNo(opsGZReply.getSupplierOrderNo());
        params.setReplyTime(opsGZReply.getReplyTime());
        params.setReplyDelayReason(opsGZReply.getReplyDelayReason());
        params.setReplyRemark(opsGZReply.getReplyRemark());
        params.setInquiryLevel(opsGZReply.getInquiryLevel());
        params.setUpdateTime(new Date());
        return params;
    }
}
