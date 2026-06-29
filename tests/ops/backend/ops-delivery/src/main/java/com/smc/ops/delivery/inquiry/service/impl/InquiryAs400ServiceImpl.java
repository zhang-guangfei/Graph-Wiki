package com.smc.ops.delivery.inquiry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inquiry.*;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.enums.SendStatusEnum;
import com.smc.ops.delivery.inquiry.mapper.InquiryAs400MidMapper;
import com.smc.ops.delivery.inquiry.mapper.InquiryCodeConfigMapper;
import com.smc.ops.delivery.inquiry.mapper.InquiryReasonParamConfigMapper;
import com.smc.ops.delivery.inquiry.service.InquiryAdapterService;
import com.smc.ops.delivery.mapper.OpsExpdetailDao;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 采购催促适配器实现
 */
@Service
@Slf4j
public class InquiryAs400ServiceImpl implements InquiryAdapterService {


    @Resource
    private InquiryAs400MidMapper inquiryAs400MidMapper;

    @Resource
    private InquiryCodeConfigMapper inquiryCodeConfigMapper;

    @Resource
    private InquiryReasonParamConfigMapper inquiryReasonParamConfigMapper;

    @Resource
    private OpsExpdetailDao opsExpdetailDao;

    /**
     * 2024-06-18 新增AS400修改
     * 1. subsidiary_code字段请填写smccode ,取ops_purchaseinvoice smccode字段，新加字段
     * 2. subsidiary_no中存放单号时，请在前面增加发日本单时的订单类型ABKU。 采购没有存储该字段，自己来进行拼接
     * 3. subsidiary_item字段请填写三位，不足补0，如下格式。  长度三位，不足往前添加0，如001
     * 4.part_number型号字段请按照发送日本时的型号填写，如有些需要带星   没有特殊要求，取采购表对应的型号就可以
     * 5.inquiry_message_code字段请按下记格式发送AAA+两位代码（不足补0）
     * 6.inquiry_message分纳发货的筛选框是否可以按照日本要求的方式转换后再推给日本呢？
     * @param opsInquiryPoHandle
     * @return
     */
    @Override
    public ResultVo<String> inquirySend(List<OpsInquiryPoHandle> opsInquiryPoHandle) {
        if (CollectionUtils.isEmpty(opsInquiryPoHandle)) {
            return ResultVo.failure("发送AS400数据为空，请检查发送数据");
        }
        int result = 0;
        StringBuilder errMsg = new StringBuilder();
        Map<String, OpsInquiryCodeConfig> codeConfig = getManuCodecinfig(InquiryCodeTypeConfigEnum.SEND.getType());
        OpsAs400Inquiry opsAs400Inquiry;
        List<OpsAs400Inquiry> opsAs400Inquiries = new ArrayList<>();
        for (OpsInquiryPoHandle opsInquiryPoHandleOne : opsInquiryPoHandle) {
            opsAs400Inquiry = new OpsAs400Inquiry();
            // bug14490，subsidiary_code字段请填写smccode
            opsAs400Inquiry.setSubsidiaryCode(opsInquiryPoHandleOne.getSmccode());
            //bug14435 INQA对接日本修改
            if (StringUtils.isNotBlank(opsInquiryPoHandleOne.getPono())) {
                //bug14490 subsidiary_no中存放单号时，请在前面增加发日本单时的订单类型ABKU。
                opsAs400Inquiry.setSubsidiaryNo(opsInquiryPoHandleOne.getPurchasetype()+opsInquiryPoHandleOne.getPono());
                // subsidiary_item字段请填写三位，不足补0，如下格式
                opsAs400Inquiry.setSubsidiaryItem(String.format("%03d", opsInquiryPoHandleOne.getLineitem()));
            }
            opsAs400Inquiry.setType(InquiryConstants.AS400_INQUIRYA_TYPE);
            opsAs400Inquiry.setPartNumber(opsInquiryPoHandleOne.getModelNo());
            opsAs400Inquiry.setQty(opsInquiryPoHandleOne.getQuantity());
            // 写入供应商接单号
            if (StringUtils.isNotBlank(opsInquiryPoHandleOne.getSupplierOrderNo())) {
                String jpReplyNo = opsInquiryPoHandleOne.getSupplierOrderNo();
                if (jpReplyNo.contains("-")) {
                    String[] split = jpReplyNo.split("-");
                    if (split.length == 2 && StringUtils.isNumeric(split[1].trim())) {
                        opsAs400Inquiry.setSmcjpNo(split[0].trim());
                        opsAs400Inquiry.setSmcjpItem(Integer.parseInt(split[1].trim()));
                    } else {
                        opsAs400Inquiry.setSmcjpNo(jpReplyNo);
                        opsAs400Inquiry.setSmcjpItem(1);
                    }
                } else {
                    opsAs400Inquiry.setSmcjpNo(jpReplyNo);
                    opsAs400Inquiry.setSmcjpItem(1);
                }
            }
            opsAs400Inquiry.setDueDate(opsInquiryPoHandleOne.getHopeDeliveryDate()); //inqA 催促货期
            opsAs400Inquiry.setInquiryMessageContents(opsInquiryPoHandleOne.getInquiryRemark()); // todo 限制日本备注内容15个字符的话，门户前端是否也需要限制
//            String quaId = codeConfig.get(opsInquiryPoHandleOne.getInquiryReasonType());
            // 分类码转换
            if (codeConfig != null) {
                OpsInquiryCodeConfig reasonDescDetail = codeConfig.get(opsInquiryPoHandleOne.getInquiryReasonType());
                if (reasonDescDetail != null) {
                    // inquiry_message_code字段请按下记格式发送AAA+两位代码（不足补0）
                    opsAs400Inquiry.setInquiryMessageCode(InquiryConstants.AS400_MESSAGE_CODE.concat(String.format("%02d", Integer.parseInt(reasonDescDetail.getAs400ReasonCode()))));
                    opsAs400Inquiry.setInquiryMessage(reasonDescDetail.getAs400ReasonDesc());
                    // 针对-PLEASE SHIP (*2*)PCS PARTIALLY-原因的催促，需要按照InquiryRemark 提取InquiryRemark中2PCS前的数字,替换*2*为里面的数字，重新给InquiryMessage赋值
                    List<OpsInquiryReasonParamConfig> inquiryReasonParamConfigs = inquiryReasonParamConfigMapper.getReasonParamConfigBySuppily(opsInquiryPoHandleOne.getInquiryReasonType(),opsInquiryPoHandleOne.getSupplierId());
                    if (!inquiryReasonParamConfigs.isEmpty() && StringUtils.isNotBlank(opsInquiryPoHandleOne.getInquiryReasonParam())) {
                        String paramValue = opsInquiryPoHandleOne.getInquiryReasonParam();
                        //BUG14649 【INQA催促对接日本】对接日本的申请表中日期格式修改,格式为YYMMDD
                        if (inquiryReasonParamConfigs.get(0).getParamName().equals(InquiryConstants.AS400_3PARAM_NAME)) {
                            paramValue = DateUtil.getYearMonthDayByString(paramValue);
                        }
                        // 进行替换操作
                        String newMessage = opsAs400Inquiry.getInquiryMessage().replace(inquiryReasonParamConfigs.get(0).getParamName(), paramValue);
                        opsAs400Inquiry.setInquiryMessage(newMessage);
                    }
//                    if (reasonDescDetail.getAs400ReasonCode().equalsIgnoreCase(InquiryConstants.AS400_FENNA_CODE) && StringUtils.isNotBlank(opsInquiryPoHandleOne.getInquiryRemark())) {
//                        String inquiryRemark = opsInquiryPoHandleOne.getInquiryRemark();
//                        int pcsIndex = inquiryRemark.indexOf(InquiryConstants.AS400_PCS_CODE);
//                        if (pcsIndex > 0 && Character.isDigit(inquiryRemark.charAt(pcsIndex - 1))) {
//                            // 使用正则表达式提取数字
//                            String numberString = inquiryRemark.substring(0, pcsIndex).replaceAll("\\D+", "");
//                            if (!numberString.isEmpty()) {
//                                // 进行替换操作
//                                String newMessage = opsAs400Inquiry.getInquiryMessage().replace("*2*", numberString);
//                                opsAs400Inquiry.setInquiryMessage(newMessage);
//                            }
//                        }
//                    }
                } else {
                    log.warn("AS400分类暂时找不到对应催促原因分类码: {}", opsInquiryPoHandleOne.getInquiryReasonType());
                }
            }
            //bug14435 INQA对接日本修改
            opsAs400Inquiry.setSubsidiaryAppLyNo(opsInquiryPoHandleOne.getTaskNo());
            opsAs400Inquiry.setSubsidiaryAppLyItem("1");
            opsAs400Inquiry.setCreateTime(new Date());
            // bug14826 INQA对接日本修改
            if (StringUtils.isNotBlank(opsInquiryPoHandleOne.getInquiryLevel()) && !(opsInquiryPoHandleOne.getInquiryLevel().equalsIgnoreCase(InquiryALevel.INQ_A.getType()))) {
                InquiryALevel aLevel = InquiryALevel.parse(opsInquiryPoHandleOne.getInquiryLevel());
                if (aLevel != null) {
                    // 2024-09-5,修改推送日本hotline级别，INO-A时推送日本hotline字段为空:HOT-1时推送日本hotline字段为1:HOT-2时推送日本hotline字段为2;
                    opsAs400Inquiry.setHotlineLevel(aLevel.getDesc());
                }
            }
            opsAs400Inquiries.add(opsAs400Inquiry);
        }
        // 参数超2100时，进行分批
        Map<Integer, List<OpsAs400Inquiry>> mapList = SplitBatchUtils.getInsertBatchBySqlserver(opsAs400Inquiries, OpsAs400Inquiry.class);
        for (Map.Entry<Integer, List<OpsAs400Inquiry>> entry : mapList.entrySet()) {
            try {
                // 写入AS400中间表
                result = result + inquiryAs400MidMapper.insertAs400Inquiry(entry.getValue());
            } catch (Exception ex) {
                errMsg.append("发送AS400催促数据失败").append(ex.getMessage());
            }
        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure(errMsg.toString());
        }
        return ResultVo.success("采购催促写入AS400中间表成功，共计发送" + result + "项");
    }

    @Override
    public ResultVo<List<OpsInquiryPoHandle>> inquiryCallBack(List<OpsInquiryPoHandle> callParams) {
        try {
            // 查询回复的参数
            if (CollectionUtils.isEmpty(callParams)) {
                return ResultVo.failure("获取AS400回复信息参数为空！");
            }
            // 根据供应商去读取配置表，取出具体需要实现的方法
            String mailTo = "duanxiaofeng@smc.com.cn";
            String mailCc = "tangqixu@smc.com.cn";
            List<OpsInquiryAdapterConfig> adapterConfigList = inquiryCodeConfigMapper.getAdapterBySupplier(callParams.get(0).getSupplierId());
            if (!CollectionUtils.isEmpty(adapterConfigList)) {
                OpsInquiryAdapterConfig inquiryAdapterConfig = adapterConfigList.get(0);
                if (StringUtils.isNotBlank(inquiryAdapterConfig.getWarningMailTo())) {
                    mailTo = inquiryAdapterConfig.getWarningMailTo();
                }
                if (StringUtils.isNotBlank(inquiryAdapterConfig.getWarningMailCc())) {
                    mailCc = inquiryAdapterConfig.getWarningMailCc();
                }
            }
            //  获取回复信息，是否可以改为用记录上次id或者updateTime的方式进行？这样避免轮询大量的数据
            List<OpsInquiryPoHandle> replyData = new ArrayList<>();
            OpsAs400InquiryReply opsAs400InquiryReply;
            Map<String, OpsInquiryCodeConfig> codeConfig = getManuCodecinfig(InquiryCodeTypeConfigEnum.REPLY.getType());
            for (OpsInquiryPoHandle params : callParams) {
                // bug16423 判断回复原因是否为'CANCELLED ON ABOVE DATE','DISPATCHED FROM FACTORY ON ABOVE DATE'，如果是，则不再去取回复信息了
                Set<String> as400ErrorMsgList = new HashSet<String>(InquiryReturnErrorMsgEnum.AS400ErrorMsg());
                if (StringUtils.isNotBlank(params.getReplyDelayReason()) && as400ErrorMsgList.contains(params.getReplyDelayReason())) {
                    continue;
                }
                opsAs400InquiryReply = new OpsAs400InquiryReply();
                opsAs400InquiryReply.setSubsidiaryCode(params.getSmccode());
                // bug14435 INQA对接日本修改
                opsAs400InquiryReply.setSubsidiaryAppLyNo(params.getTaskNo());
                opsAs400InquiryReply.setSubsidiaryAppLyItem("1");
//                if (StringUtils.isNotBlank(params.getPono())) {
//                    opsAs400InquiryReply.setSubsidiaryNo(params.getPono());
//                    opsAs400InquiryReply.setSubsidiaryItem(params.getLineitem().toString());
//                }
                opsAs400InquiryReply.setType(InquiryConstants.AS400_INQUIRYA_TYPE);
                opsAs400InquiryReply.setPartNumber(params.getModelNo());
                opsAs400InquiryReply.setQty(params.getQuantity());
                opsAs400InquiryReply.setSmcjpNo(params.getSupplierOrderNo());
                opsAs400InquiryReply.setDueDate(params.getHopeDeliveryDate());
                List<OpsAs400InquiryReply> opsAs400InquiryReplies = inquiryAs400MidMapper.getInquiryReply(opsAs400InquiryReply);
//                List<OpsAs400InquiryReply> opsAs400InquiryReplies =   new ArrayList<>();
//                 try {
//                     opsAs400InquiryReplies = getAs400InquiryReply(opsAs400InquiryReply);
//                 } catch (Exception e) {
//                     log.error("INQA查询发生死锁，taskno为：" + params.getTaskNo());
//                     continue;
//                 }
                if (!CollectionUtils.isEmpty(opsAs400InquiryReplies)) {
                    for (OpsAs400InquiryReply reply : opsAs400InquiryReplies) {
                        // 2024-10-10 日本目前是分批回复，先回复回复号，再回复具体的货期，
                        // bug16423 INQ催促,判断回复号的规则注释掉，都去读取回复信息
                        // 校验handle中是否已经存在回复号，且回复货期为空，则不再处理，如果回复号不为空再进行更新
//                        if (StringUtils.isNotBlank(params.getReplyNo()) && (StringUtils.isBlank(reply.getReturnADate()) || reply.getReturnADate().trim().startsWith("0"))) {
//                            continue;
//                        }
                        // 针对错误回复的信息，取一次第二次不再取
                        if (StringUtils.isNotBlank(params.getReplyDelayReason()) && reply.getReturnMessageCode().trim().startsWith("EEE")) {
                            continue;
                        }
                        // 实体转换
                        params.setReplyNo(reply.getReturnReferenceNo().trim());
                        // 14649 【INQA催促对接日本】对接日本的申请表中日期格式修改,有值且不是0，则进行赋值,同时限制，回复的货期格式需要为8位 && !reply.getReturnADate().trim().startsWith("0")
                        if (StringUtils.isNotBlank(reply.getReturnADate()) && reply.getReturnADate().trim().length() == 8) {
                            // bug 16537 inq-A回复货期转换问题，日本回复货期，日期格式转换 针对99问题
                            String dateStr = reply.getReturnADate().trim();
                            params.setReplyDeliveryDateSrc(dateStr);
                            String monthStr = dateStr.substring(4, 6);
                            // 只需要判断月份，符合正常月份的话，再进行解析实际返回纳期，否则只写入src不进行日期解析,判断回复货期的月份，是否为正常的月份，01、02、03、04、05、06、07、08、09、10、11、12范围内
                            if (monthStr.matches(InquiryConstants.AS400_Month_Regex)) {
                                if (!dateStr.startsWith("99") && dateStr.endsWith("99")) { //开头不是99，结尾是99的，取当月的最后一天
                                    String yearStr = Integer.toString(DateUtil.getYear(new Date())).substring(0, 2) + dateStr.substring(2, 4);
                                    if (monthStr.equals("99")) {
                                        monthStr = "12";
                                    }
                                    int daysInMonth = PublicUtil.getDaysInMonth(Integer.parseInt(yearStr), Integer.parseInt(monthStr));
                                    String latestDeliveryTime = yearStr + "-" + monthStr + "-" + daysInMonth;
                                    params.setReplyDeliveryDate(DateUtil.stringToDate(latestDeliveryTime));
                                } else {// 正常的日期格式，转换为日期
                                    List<String> specialDateList = InquiryDlvDateEnum.specialDateList();
                                    if (!specialDateList.contains(dateStr)) {
                                        params.setReplyDeliveryDate(DateUtil.stringToDate(dateStr, "yyyyMMdd"));
                                    }
                                }
                            }
                        }
                        // 日本的回复时间，根据时差减一小时
                        if (reply.getReturnDate() != null) {
                            params.setReplyTime(DateUtil.addHour(reply.getReturnDate(), -1));
                        }
                        if (StringUtils.isNotBlank(reply.getReturnMessage())) {
                            params.setReplyDelayReason(reply.getReturnMessage().trim());
                        }
                        // 回复分类码转换
                        if (codeConfig != null && StringUtils.isNotBlank(reply.getReturnMessage())) {
                            // 根据日本回复的分类码进行转换，当未匹配到对应转换规则时，提示异常
                            String returnMessage = reply.getReturnMessage().trim();
                            // 使用Optional来优雅地处理可能的null值
                            String answerReason = Optional.ofNullable(codeConfig.get(returnMessage))
                                    .map(OpsInquiryCodeConfig::getOpsReasonDesc)
                                    .orElse(returnMessage);
                            if (answerReason.equals(returnMessage)) {
                                String errorMessage = "INQA-AS400回复原因未匹配到转换配置，请及时补充，申请号为：" + params.getInquiryApplyNo()  + "，回复原因：" + returnMessage;
                                log.error("INQA-AS400回复原因转换异常，taskno为：" + params.getTaskNo() + "，回复原因：" + returnMessage);
                                // 邮件通知业务及IT人员
                                this.sendNoReaSonErrorMail(mailTo,mailCc, "INQA-AS400回复原因转换异常", errorMessage);
                            }
                            params.setReplyDelayReason(answerReason);
                        }
//                        params.setReplyDelayReasonType(reply.getReturnMessageCode().toString()); //答复代码
                        params.setReplyRemark(reply.getReturnMessageContents());
//                        params.setInquiryLevel(reply.getHotlineLevel());
                        params.setReplySupplierDept("JP");
                        params.setUpdateTime(new Date());
                        replyData.add(params);
                    }
                }
            }
            if (CollectionUtils.isEmpty(replyData)) {
                return ResultVo.successMsg("未查到相关回复信息");
            }
            return ResultVo.success(replyData);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }




    /**
     * 催促分类码的转换
     */
    private Map<String, OpsInquiryCodeConfig> getManuCodecinfig(String codetype) {
        List<OpsInquiryCodeConfig> list = inquiryCodeConfigMapper.getAs400CodeConfig(codetype);
        Map<String,OpsInquiryCodeConfig> result = new HashMap<>();
        if (InquiryCodeTypeConfigEnum.SEND.getType().equalsIgnoreCase(codetype)) {  // 校验是否为发送配置
            for (OpsInquiryCodeConfig opsInquiryCodeConfig : list) {
                result.put(opsInquiryCodeConfig.getOpsReasonCode(),opsInquiryCodeConfig); // 构建配置map
            }
        }
        else { // 回复配置
            for (OpsInquiryCodeConfig opsInquiryCodeConfig : list) {
                result.put(opsInquiryCodeConfig.getAs400ReasonDesc(),opsInquiryCodeConfig); // 构建配置map
            }
        }
        return result;
    }

    public List<OpsAs400InquiryReply>  getAs400InquiryReply(OpsAs400InquiryReply opsAs400InquiryReply) throws Exception {
        int retryCount = 0;
        List<OpsAs400InquiryReply> opsAs400InquiryReplies = null;
        while (retryCount < 3) { // 设置最大重试次数为3
            try {
                // 执行查询逻辑
                opsAs400InquiryReplies = inquiryAs400MidMapper.getInquiryReply(opsAs400InquiryReply);
                break; // 查询成功后退出循环
            } catch (Exception e) {
                // 检查是否为死锁异常
                if (e instanceof SQLServerException && e.getMessage().contains("被选作死锁牺牲品")) {
                    log.error("INQA查询死锁发生，正在重试...");
                    retryCount++; // 增加重试计数
                    Thread.sleep(100); // 等待100毫秒后重试
                } else {
                    // 非死锁异常，直接抛出
                    throw e;
                }
            }
        }
        return opsAs400InquiryReplies;
    }


    /**
     * 增量获取回复信息，根据lastID or lastUpdateTime
     * @return
     */
    @Override
    public ResultVo<List<OpsInquiryPoHandle>> inquiryCallBackIncrement(String param) {
        // as400通过上次获取的最大更新时间计算增量，注意时区的转换 减少一小时
        try {
            // object转换实体
            InquiryCallbackIncrementParam callbackIncrementParam = JSONObject.parseObject(param, InquiryCallbackIncrementParam.class);
            if (callbackIncrementParam == null || callbackIncrementParam.getCallbackIncrementLasttime() == null) {
                return ResultVo.failure("INQA获取AS400增量回复数据，请求参数为空，请稍后错误！");
            }
            // 根据供应商去读取配置表，取出具体需要实现的方法
            String mailTo = "duanxiaofeng@smc.com.cn";
            String mailCc = "tangqixu@smc.com.cn";
            List<OpsInquiryAdapterConfig> adapterConfigList = inquiryCodeConfigMapper.getAdapterBySupplier("JP");
            if (!CollectionUtils.isEmpty(adapterConfigList)) {
                OpsInquiryAdapterConfig inquiryAdapterConfig = adapterConfigList.get(0);
                if (StringUtils.isNotBlank(inquiryAdapterConfig.getWarningMailTo())) {
                    mailTo = inquiryAdapterConfig.getWarningMailTo();
                }
                if (StringUtils.isNotBlank(inquiryAdapterConfig.getWarningMailCc())) {
                    mailCc = inquiryAdapterConfig.getWarningMailCc();
                }
            }
            // 获取回复信息，是否可以改为用记录上次id或者updateTime的方式进行？这样避免轮询大量的数据
            List<OpsInquiryPoHandle> replyData = new ArrayList<>();
            OpsInquiryPoHandle replyEntity;
            Map<String, OpsInquiryCodeConfig> codeConfig = getManuCodecinfig(InquiryCodeTypeConfigEnum.REPLY.getType());
            // 日本的回复时间，根据时差加一小时
            Date lastTimestamp = DateUtil.addHour(callbackIncrementParam.getCallbackIncrementLasttime(), 1);
            // 查询中间表的增量数据，按照回复时间倒序
            List<OpsAs400InquiryReply> opsAs400InquiryReplies = inquiryAs400MidMapper.getInquiryReplyIncrement(InquiryConstants.AS400_INQUIRYA_TYPE, lastTimestamp);
            Set<String> replyTasknoSet = new HashSet<>();
            for (OpsAs400InquiryReply as400Reply : opsAs400InquiryReplies) {
                if (replyTasknoSet.contains(as400Reply.getSubsidiaryAppLyNo())) {
                    continue; //筛选重复集合，避免重复更新
                }
                // 根据回复数据组装回复实体
                replyEntity = this.getInquiryPoHandleByMid(as400Reply);
                // 14649 【INQA催促对接日本】对接日本的申请表中日期格式修改,有值且不是0，则进行赋值
                if (StringUtils.isNotBlank(as400Reply.getReturnADate()) && !as400Reply.getReturnADate().trim().startsWith("0")) {
                    // bug 16537 inq-A回复货期转换问题，日本回复货期，日期格式转换 针对99问题
                    String dateStr = as400Reply.getReturnADate().trim();
                    replyEntity.setReplyDeliveryDateSrc(dateStr);
                    // 还会出现20999999的情况
                    if (!dateStr.startsWith("99") && dateStr.endsWith("99")) { //开头不是99，结尾是99的，取当月的最后一天
                        String yearStr = Integer.toString(DateUtil.getYear(new Date())).substring(0, 2) + dateStr.substring(2, 4);
                        String monthStr = dateStr.substring(4, 6);
                        if (monthStr.equals("99")) {
                            monthStr = "12";
                        }
                        int daysInMonth = PublicUtil.getDaysInMonth(Integer.parseInt(yearStr), Integer.parseInt(monthStr));
                        String latestDeliveryTime = yearStr + "-" + monthStr + "-" + daysInMonth;
                        replyEntity.setReplyDeliveryDate(DateUtil.stringToDate(latestDeliveryTime));
                    } else {// 正常的日期格式，转换为日期
                        List<String> specialDateList = InquiryDlvDateEnum.specialDateList();
                        if (!specialDateList.contains(dateStr)) {
                            replyEntity.setReplyDeliveryDate(DateUtil.stringToDate(dateStr,"yyyyMMdd"));
                        }
                    }
                }
                // 日本的回复时间，根据时差减一小时
                if (as400Reply.getReturnDate() != null) {
                    replyEntity.setReplyTime(DateUtil.addHour(as400Reply.getReturnDate(), -1));
                }
                if (StringUtils.isNotBlank(as400Reply.getReturnMessage())) {
                    replyEntity.setReplyDelayReason(as400Reply.getReturnMessage().trim());
                }
                // 回复分类码转换
                if (codeConfig != null && StringUtils.isNotBlank(as400Reply.getReturnMessage())) {
                    // 根据日本回复的分类码进行转换，当未匹配到对应转换规则时，提示异常
                    String returnMessage = as400Reply.getReturnMessage().trim();
                    // 使用Optional来优雅地处理可能的null值
                    String answerReason = Optional.ofNullable(codeConfig.get(returnMessage))
                            .map(OpsInquiryCodeConfig::getOpsReasonDesc)
                            .orElse(returnMessage);
                    if (answerReason.equals(returnMessage)) {
                        String errorMessage = "INQA-AS400回复原因未匹配到转换配置，请及时补充，taskNo号为：" + replyEntity.getTaskNo()  + "，回复原因：" + returnMessage;
                        log.error("INQA-AS400回复原因转换异常，taskno为：" + replyEntity.getTaskNo() + "，回复原因：" + returnMessage);
                        // 邮件通知业务及IT人员
                        this.sendNoReaSonErrorMail(mailTo,mailCc,"INQA-AS400回复原因未匹配",errorMessage);
                    }
                    replyEntity.setReplyDelayReason(answerReason);
                }
                replyData.add(replyEntity);
                replyTasknoSet.add(replyEntity.getTaskNo());
            }
            if (CollectionUtils.isEmpty(replyData)) {
                return ResultVo.successMsg("未查到相关回复信息");
            }
            // 根据回复时间进行倒序排序
            replyData.sort(Comparator.comparing(OpsInquiryPoHandle::getReplyTime).reversed());
            return ResultVo.success(replyData);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 根据AS400中间表的数据，组装成回复实体内容
     * @param reply
     * @return
     */
    public OpsInquiryPoHandle getInquiryPoHandleByMid(OpsAs400InquiryReply reply) {
        OpsInquiryPoHandle params = new OpsInquiryPoHandle();
        params.setModelNo(reply.getPartNumber().trim());
        params.setQuantity(reply.getQty());
        params.setHopeDeliveryDate(reply.getDueDate());
        params.setTaskNo(reply.getSubsidiaryAppLyNo()); // taskno唯一值
        params.setReplyNo(reply.getReturnReferenceNo().trim());
        params.setReplyRemark(reply.getReturnMessageContents());
        params.setReplySupplierDept("JP");
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

//    // 获取OPS与AS400 code 转换配置
//    private Map<String, String> getManuCodecinfig(String codetype) {
//        List<OpsInquiryCodeConfig> list = inquiryCodeConfigMapper.getAs400CodeConfig(codetype);
//        return list.stream().collect(Collectors.toMap(
//                OpsInquiryCodeConfig::getOpsReasonCode, OpsInquiryCodeConfig::getAs400ReasonCode,
//                (val1, val2) -> val2
//        ));
//    }
}
