package com.smc.ops.delivery.inquiry.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.googlecode.aviator.AviatorEvaluator;
import com.sales.ops.db.entity.OpsInquiryPoHandle;
import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.enums.SendStatusEnum;
import com.smc.ops.delivery.inquiry.mapper.OpsInquiryReplyRuleConfigMapper;
import com.smc.ops.delivery.inquiry.service.InquiryRuleConfigService;
import com.smc.ops.delivery.mapper.OpsExpdetailDao;
import com.smc.ops.delivery.model.inqa.OpsInquiryReplyRuleConfigDo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: B91717
 * @time: 2025/6/5 10:40
 */
@Service
public class InquiryRuleConfigServiceImpl implements InquiryRuleConfigService {

    @Resource
    private OpsInquiryReplyRuleConfigMapper opsInquiryReplyRuleConfigMapper;

    @Resource
    private OpsExpdetailDao opsExpdetailDao;

    /**
     * 通过规则配置，进行数据的配置判断
     *             // 直接比较且处理空值
     * //            !nil?(F1) && !nil?(F2) && F1.after(F2)
     * @param ruleList
     * @param returnPo
     * @return
     * @throws Exception
     */
    @Override
    public ResultVo<String> inquiryRuleConfig(List<OpsInquiryReplyRuleConfigDo> ruleList,String  ruleType, OpsInquiryPoHandle returnPo) throws Exception {
        List<OpsInquiryReplyRuleConfigDo> finalRuleList =this.getRulesByServiceTypeAndRuleType(ruleList, ruleType);
        if (CollectionUtils.isEmpty(finalRuleList)) {
            return ResultVo.failure("该供应商暂未配置对应的规则！");
        }
        // bug 18419 当回复货期不为空时，将回复货期字段转换为日期格式YYYY-MM-DD
        if (returnPo.getReplyDeliveryDate()!= null) {
            try {
                returnPo.setReplyDeliveryDate(DateUtil.getBeginTime(returnPo.getReplyDeliveryDate()));
            } catch (Exception e) {
                return ResultVo.failure("回复货期转换失败！");
            }
        }
        // 将实体对象转换为 Map
//        BeanMap poHandleMap = new BeanMap(returnPo);
        Map<String, Object> variables = BeanUtils.beanToMap(returnPo);
        // 循环处理规则数据
        for (OpsInquiryReplyRuleConfigDo rule : finalRuleList) {
            // 使用 AviatorScript 解析表达式
            // todo 优化点 回复货期对比时，将时间转换为日期格式进行对比
            Boolean result = (Boolean) AviatorEvaluator.execute(rule.getExpression(), variables);
            // 如果表达式结果为 true，返回对应的 result
            if (result != null && result) {
                // 针对 result 进行特殊的处理，当条件为真时，去匹配result中 是否存储的字段名称，并返回对应的字段结果，当result中存储的是固定值时直接返回
                if (variables.containsKey(rule.getResult())) {
                    return ResultVo.success(variables.get(rule.getResult()).toString());
                }
                return ResultVo.success(rule.getResult());
            }
        }
        return ResultVo.failure("未匹配到对应规则");
    }


    // 根据供应商，查询规则配置表中的数据
    public ResultVo<List<OpsInquiryReplyRuleConfigDo>> getRulesByServiceTypeAndRuleType(String serviceType,String supplierId) {
        // 联查supplier表中的信息，制造体系包含5个供应商
        // 根据 serviceType 和 ruleType 查询规则，并按 priority 排序
//        List<OpsInquiryReplyRuleConfigDo> completeRuleList = opsInquiryReplyRuleConfigMapper.selectList(
//                new QueryWrapper<OpsInquiryReplyRuleConfigDo>()
//                        .eq("service_type", serviceType)
//                        .eq("supplier_id", supplierId)
//                        .eq("is_deleted", 0)
//                        .orderByAsc("priority")
//        );
        // 判断提供的是实际供应商，还是配置的供
        List<OpsInquiryReplyRuleConfigDo> completeRuleList = opsInquiryReplyRuleConfigMapper.getRulesByServiceTypeAndRuleType(serviceType,supplierId);
        if (CollectionUtils.isEmpty(completeRuleList)) {
            return ResultVo.failure("该供应商暂未配置对应的规则！");
        }
        return ResultVo.success(completeRuleList);
    }



    // 根据催促类别，从完整的清单中，筛选出该类别的规则
    public List<OpsInquiryReplyRuleConfigDo> getRulesByServiceTypeAndRuleType(List<OpsInquiryReplyRuleConfigDo> ruleList,String ruleType) {
        // 1. 检查 ruleList 是否为 null
        if (ruleList == null) {
            return Collections.emptyList(); // 返回空列表，避免 NPE
        }
        // 从清单中，筛选出来该类型的规则,并且根据优先级进行排序
        return ruleList.stream().filter(item ->Objects.equals(ruleType, item.getRuleType())).sorted(Comparator.comparingInt(OpsInquiryReplyRuleConfigDo::getPriority)).collect(Collectors.toList());
    }

    public void sendInquiryErrorMail(String mailTo,String cc,String subject,String message) {
        //保存邮件到数据库
        OpsMail opsMail = new OpsMail();
        //初始状态是0
        opsMail.setStatus(SendStatusEnum.INIT.getType());
        //收件人
        opsMail.setMailTo(mailTo);
        if (StringUtils.isNotBlank(cc)) {
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
