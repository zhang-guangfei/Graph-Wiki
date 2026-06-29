package com.smc.ops.delivery.inquiry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.db.entity.OpsInquiryCodeConfig;
import com.sales.ops.db.entity.OpsInquiryPoHandle;
import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.db.entity.YyLongmodelexchange;
import com.sales.ops.dto.inquiry.*;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.enums.SendStatusEnum;
import com.smc.ops.delivery.inquiry.mapper.InquiryCodeConfigMapper;
import com.smc.ops.delivery.inquiry.mapper.InquiryManuMidInsertMapper;
import com.smc.ops.delivery.inquiry.mapper.InquiryManuMidMapper;
import com.smc.ops.delivery.inquiry.service.InquiryAdapterService;
import com.smc.ops.delivery.mapper.OpsExpdetailDao;
import com.smc.ops.delivery.mapper.OpsYYLongModelMapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 采购催促适配器实现
 */
@Service
@Slf4j
public class InquiryManuServiceImpl implements InquiryAdapterService {

    @Resource
    private InquiryManuMidMapper inquiryManuMidMapper;

    @Resource
    private InquiryManuMidInsertMapper inquiryManuMidInsertMapper;

    @Resource
    private InquiryCodeConfigMapper inquiryCodeConfigMapper;

    @Resource
    private OpsYYLongModelMapper opsYYLongModelMapper;

    @Resource
    private OpsExpdetailDao opsExpdetailDao;

    @Override
    public ResultVo<String> inquirySend(List<OpsInquiryPoHandle> opsInquiryPoHandle) {
        if (CollectionUtils.isEmpty(opsInquiryPoHandle)) {
            return ResultVo.failure("发送中国制造数据为空，请检查发送数据");
        }
        List<String> failedMessages = new ArrayList<>();
        int result = 0;
        Map<String, OpsInquiryCodeConfig> codeConfig = getManuCodecinfig(InquiryCodeTypeConfigEnum.SEND.getType()); // 获取发送配置码
        OpsManuInquiry opsManuInquiry;
        List<OpsManuInquiry> opsManuInquiries = new ArrayList<>();
        for (OpsInquiryPoHandle opsInquiryPoHandle1 : opsInquiryPoHandle) {
            // 2024-01-25确认 reasoncode字段 传1
            opsManuInquiry = new OpsManuInquiry();
            opsManuInquiry.setQueryNo(opsInquiryPoHandle1.getTaskNo());
            opsManuInquiry.setQueryType(InquiryConstants.MANU_INQUIRYA_TYPE);
            opsManuInquiry.setModel(opsInquiryPoHandle1.getModelNo());
            opsManuInquiry.setOrderNumber(opsInquiryPoHandle1.getOrderNo());
            opsManuInquiry.setQuantity(opsInquiryPoHandle1.getQuantity());
            opsManuInquiry.setQueryDlvyDate(opsInquiryPoHandle1.getHopeDeliveryDate());
            opsManuInquiry.setQueryPsnid(opsInquiryPoHandle1.getInquiryUser());
            opsManuInquiry.setQueryDateTime(opsInquiryPoHandle1.getInquiryTime());
            // bug14184 INQ-A问询数据推送到制造,原因码和描述补充
            String reasonType = opsInquiryPoHandle1.getInquiryReasonType();
            OpsInquiryCodeConfig reasonDescDetail = codeConfig.get(reasonType);
            if (reasonDescDetail != null) {
                opsManuInquiry.setQueryQuestionId(Integer.parseInt(reasonDescDetail.getManuReasonCode()));
                opsManuInquiry.setQueryDetail(reasonDescDetail.getManuReasonDesc());
            } else {
                log.warn("制造分类暂时找不到对应催促原因分类码: {}", reasonType);
            }
            opsManuInquiry.setQuestionRemark(opsInquiryPoHandle1.getInquiryRemark());
            opsManuInquiry.setCustomerNo(opsInquiryPoHandle1.getCustomerNo());
            opsManuInquiry.setQueryDeptNo(opsInquiryPoHandle1.getInquiryDept());
            opsManuInquiry.setStateCode("1");
            opsManuInquiry.setReasonCode("1");

            String modelNoSend = opsInquiryPoHandle1.getModelNo();
            // bug15048  B1717，将制造发单超长替换型号
            if (StringUtils.isNotBlank(modelNoSend) && modelNoSend.getBytes().length > 30) {
                // 替换超长型号
                longModel(modelNoSend);
                List<YyLongmodelexchange>  longmodelexchanges = opsYYLongModelMapper.getLongModelExchange(modelNoSend);
                if (CollectionUtils.isEmpty(longmodelexchanges)) {
                    log.error("INQA推送制造型号超长: {}", modelNoSend);
                    // 转换失败时，直接返回错误信息，不再继续发送
                    return ResultVo.failure("中国制造催促发送失败，taskno：[" + opsInquiryPoHandle1.getTaskNo() + "], 错误原因：INQA推送制造型号超长,超长型号转换失败" );
                }
                opsManuInquiry.setModel(longmodelexchanges.get(0).getModel()); //重新赋值
            }
            opsManuInquiries.add(opsManuInquiry);
        }

        Map<Integer, List<OpsManuInquiry>> mapList = SplitBatchUtils.getInsertBatchBySqlserver(opsManuInquiries, OpsManuInquiry.class);
        for (Map.Entry<Integer, List<OpsManuInquiry>> entry : mapList.entrySet()) {
            try {
                // 写中国制造中间表
                result = result + inquiryManuMidInsertMapper.insertManuInquiry(entry.getValue());
            } catch (Exception ex) {
                log.error("中国制造催促发送失败: {}", ex.toString());
                String failedOrder = entry.getValue().stream()
                        .map(OpsManuInquiry::getQueryNo)
                        .collect(Collectors.joining(", "));
                failedMessages.add("中国制造催促发送失败，订单号：[" + failedOrder + "], 错误原因：" + ex.getMessage());

            }
        }

        if (failedMessages.isEmpty()) {
            return ResultVo.success("采购催促写入中国制造中间表成功，共计发送" + result + "项");
        } else {
            StringBuilder errMsg = new StringBuilder("采购催促写入中国制造中间表失败，部分订单失败原因：");
            failedMessages.forEach(errMsg::append);
            return ResultVo.failure(errMsg.toString());
        }
    }

    @Override
    public ResultVo<List<OpsInquiryPoHandle>> inquiryCallBack(List<OpsInquiryPoHandle> callParams) {
        try {
            // 查询回复的参数
            if (CollectionUtils.isEmpty(callParams)) {
                return ResultVo.failure("获取中国制造回复信息参数为空！");
            }
            // TODO 获取回复信息，是否可以改为用记录上次id的方式进行？这样避免轮询大量的数据
            Map<String, OpsInquiryCodeConfig> codeConfig = getManuCodecinfig(InquiryCodeTypeConfigEnum.REPLY.getType()); // 获取发送配置码
            List<OpsInquiryPoHandle> replyData = new ArrayList<>();
            OpsManuInquiry opsManuInquiryParams;
            for (OpsInquiryPoHandle params : callParams) {
                opsManuInquiryParams = new OpsManuInquiry();
                opsManuInquiryParams.setQueryNo(params.getTaskNo());
                opsManuInquiryParams.setOrderNumber(params.getOrderNo());
                opsManuInquiryParams.setQueryType(InquiryConstants.Manu_INQA_TYPE);
                opsManuInquiryParams.setModel(params.getModelNo());
                opsManuInquiryParams.setQuantity(params.getQuantity());
                opsManuInquiryParams.setQueryDlvyDate(params.getHopeDeliveryDate());
                List<OpsManuInquiry> manuReplys = inquiryManuMidMapper.getInquiryReply(opsManuInquiryParams);
                if (!CollectionUtils.isEmpty(manuReplys)) {
                    // todo 制造是否存在多次回复？
                    for (OpsManuInquiry opsManuReply : manuReplys) {
                        // 实体转换
                        params.setReplyNo(opsManuReply.getQueryNo()); // 2024-07-24 中国制造对接时，回复单号采用taskno
                        params.setReplySupplierDept(opsManuReply.getAnswerDept());
                        params.setReplyUser(opsManuReply.getAnswerOperator());
                        if (opsManuReply.getAnswerDlvyDate()!= null) {
                            // bug 16537 inq-A回复货期转换问题，日本回复货期，日期格式转换 针对99问题
                            params.setReplyDeliveryDate(opsManuReply.getAnswerDlvyDate());
                            try {
                                params.setReplyDeliveryDateSrc(DateUtil.dateToDateTimeString(opsManuReply.getAnswerDlvyDate())); // 日期转换为字符串
                            } catch (Exception e) {
                                log.warn("中国制造回复货期转换失败: {}", opsManuReply.getAnswerDlvyDate());
                            }
                        }
                        params.setSupplierOrderNo(opsManuReply.getRequestNo());
                        params.setReplyTime(opsManuReply.getAnswerDate());
                        params.setReplyRemark(opsManuReply.getAnswerRemark());
                        // 分类码转换
                        if (StringUtils.isNotBlank(opsManuReply.getDelayReason())) {
                            String answerId = StringUtils.isBlank(codeConfig.get(opsManuReply.getDelayReason()).getOpsReasonDesc()) ? opsManuReply.getDelayReason() : codeConfig.get(opsManuReply.getDelayReason()).getOpsReasonDesc();
                            params.setReplyDelayReason(answerId);
                        }
                        params.setUpdateTime(new Date());
                        replyData.add(params);
                    }
                }
            }
            if (CollectionUtils.isEmpty(replyData)) {
                return ResultVo.successMsg("暂无回复数据");
            }
            return ResultVo.success(replyData);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    @Override
    public ResultVo<List<OpsInquiryPoHandle>> inquiryCallBackIncrement(String param) {
        // 制造增量用AnswerDateTime
        // object转换实体
        InquiryCallbackIncrementParam callbackIncrementParam = JSONObject.parseObject(param, InquiryCallbackIncrementParam.class);
        // 查询回复的参数
        if (callbackIncrementParam == null || callbackIncrementParam.getCallbackIncrementLasttime() == null) {
            return ResultVo.failure("增量获取中国制造INQA结果失败，请求参数为空，请稍后错误！");
        }
        try {
            // 获取回复信息，是否可以改为用记录上次id或者updateTime的方式进行？这样避免轮询大量的数据
            List<OpsInquiryPoHandle> replyData = new ArrayList<>();
            OpsInquiryPoHandle replyEntity;
            Map<String, OpsInquiryCodeConfig> codeConfig = getManuCodecinfig(InquiryCodeTypeConfigEnum.REPLY.getType()); // 获取发送配置码
            // 查询中间表的增量数据，按照回复时间倒序
            List<OpsManuInquiry> manuIncrementReplys = inquiryManuMidMapper.getInquiryReplyIncrement(InquiryConstants.Manu_INQA_TYPE, callbackIncrementParam.getCallbackIncrementLasttime());
            if (!CollectionUtils.isEmpty(manuIncrementReplys)) {
                for (OpsManuInquiry opsManuReply : manuIncrementReplys) {
                    // 根据回复数据组装回复实体
                    replyEntity = this.getInquiryPoHandleByMid(opsManuReply);
                    if (opsManuReply.getAnswerDlvyDate() != null) {
                        // bug 16537 inq-A回复货期转换问题，日本回复货期，日期格式转换 针对99问题
                        replyEntity.setReplyDeliveryDate(opsManuReply.getAnswerDlvyDate());
                        try {
                            replyEntity.setReplyDeliveryDateSrc(DateUtil.dateToDateTimeString(opsManuReply.getAnswerDlvyDate())); // 日期转换为字符串
                        } catch (Exception e) {
                            log.warn("中国制造回复货期转换失败: {}", opsManuReply.getAnswerDlvyDate());
                        }
                    }
                    // 分类码转换
                    if (StringUtils.isNotBlank(opsManuReply.getDelayReason()) && codeConfig != null) {
                        replyEntity.setReplyDelayReason(opsManuReply.getDelayReason());
                        if (codeConfig.containsKey(opsManuReply.getDelayReason())) {
                            replyEntity.setReplyDelayReason(codeConfig.get(opsManuReply.getDelayReason()).getOpsReasonDesc());
                        } else {
                            String errorMessage = "INQA-中国制造回复原因未匹配到转换配置，请及时补充ops_inquiry_code_config配置，taskNo号为：" + replyEntity.getTaskNo()  + "，回复原因代码：" + opsManuReply.getDelayReason();
                            log.error("INQA-中国制造回复原因未匹配到转换配置，请及时补充ops_inquiry_code_config配置，taskNo号为：" + replyEntity.getTaskNo() + "，回复原因代码：" + opsManuReply.getDelayReason());
                            // todo 邮件通知业务及IT人员
                            this.sendNoReaSonErrorMail("duanxiaofeng@smc.com.cn","tangqixu@smc.com.cn","INQA-中国制造回复原因未匹配",errorMessage);
                        }
                    }
                    replyData.add(replyEntity);
                }
            }
            if (CollectionUtils.isEmpty(replyData)) {
                return ResultVo.successMsg("暂无回复数据");
            }
            // 增加排序
            replyData.sort(Comparator.comparing(OpsInquiryPoHandle::getReplyTime).reversed());
            return ResultVo.success(replyData);
        } catch (Exception e) {
            log.warn("中国制造回复数据转换失败: {}", e);
            return ResultVo.failure("中国制造回复数据转换失败"+ e.getMessage());
        }
    }


    /**
     * 获取OPS与制造code 转换配置,参数为 发送/回复配置
     * bug14184 INQ-A问询数据推送到制造,原因码和描述补充
      */
    private Map<String, OpsInquiryCodeConfig> getManuCodecinfig(String codetype) {
        List<OpsInquiryCodeConfig> list = inquiryCodeConfigMapper.getManuCodeConfig(codetype);
        Map<String,OpsInquiryCodeConfig> result = new HashMap<>();
        if (InquiryCodeTypeConfigEnum.SEND.getType().equalsIgnoreCase(codetype)) {  // 校验是否为发送配置
            for (OpsInquiryCodeConfig opsInquiryCodeConfig : list) {
                result.put(opsInquiryCodeConfig.getOpsReasonCode(),opsInquiryCodeConfig); // 构建配置map
            }
        }
        else { // 回复配置
            for (OpsInquiryCodeConfig opsInquiryCodeConfig : list) {
                result.put(opsInquiryCodeConfig.getManuReasonCode(),opsInquiryCodeConfig); // 构建配置map
            }
        }
        return result;
    }

    public void longModel(String modelNo) {
        //bug15048 取得超长型号表，超长型号写入表中
        List<YyLongmodelexchange> yys = opsYYLongModelMapper.getLongModelExchange(modelNo);
        if (CollectionUtils.isEmpty(yys)) {
            opsYYLongModelMapper.insertLongModelExchange(modelNo);
        }
    }

    /**
     * 根据
     * @param opsManuReply
     * @return
     */
    public OpsInquiryPoHandle getInquiryPoHandleByMid(OpsManuInquiry opsManuReply) {
        OpsInquiryPoHandle params = new OpsInquiryPoHandle();
        // 实体转换
        params.setQuantity(opsManuReply.getQuantity());
        params.setHopeDeliveryDate(opsManuReply.getQueryDlvyDate());
        params.setTaskNo(opsManuReply.getQueryNo()); // taskno唯一值

        params.setReplyNo(opsManuReply.getQueryNo()); // 2024-07-24 中国制造对接时，回复单号采用taskno
        params.setReplySupplierDept(opsManuReply.getAnswerDept());
        params.setReplyUser(opsManuReply.getAnswerOperator());
        params.setSupplierOrderNo(opsManuReply.getRequestNo());
        params.setReplyTime(opsManuReply.getAnswerDate());
        params.setReplyRemark(opsManuReply.getAnswerRemark());
        params.setUpdateTime(new Date());
        return params;
    }

    public void sendNoReaSonErrorMail(String mailTo,String cc,String subject,String message) {
        //保存邮件到数据库
        OpsMail opsMail = new OpsMail();
        //初始状态是0
        opsMail.setStatus(SendStatusEnum.INIT.getType());
        //收件人
        opsMail.setMailTo(mailTo);
        if (org.apache.commons.lang.StringUtils.isNotBlank(cc)) {
            opsMail.setCc(cc);
        }
        //主题
        opsMail.setSubject(subject);
        StringBuffer con = new StringBuffer();
        con.append("<h4>"+message+"</h4>\r\n ");
        //邮件内容
        opsMail.setContext(con.toString());
        opsExpdetailDao.insertMailData(opsMail);
    }
}
