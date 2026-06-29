package com.smc.ops.delivery.inquiry.service;

import com.sales.ops.db.entity.OpsInquiryPoHandle;
import com.smc.ops.delivery.model.inqa.OpsInquiryReplyRuleConfigDo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;

/**
 * @author ：B91717
 * 根据规则引擎，进行催促状态、催促回复标识等功能的判断
 */
public interface InquiryRuleConfigService {

    public ResultVo<String> inquiryRuleConfig(List<OpsInquiryReplyRuleConfigDo> ruleList,String  ruleType, OpsInquiryPoHandle returnPo) throws Exception;

    public ResultVo<List<OpsInquiryReplyRuleConfigDo>> getRulesByServiceTypeAndRuleType(String serviceType,String supplierId);

    public void sendInquiryErrorMail(String mailTo,String cc,String subject,String message);
}
