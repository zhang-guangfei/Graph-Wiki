package com.smc.ops.delivery.inquiry.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.db.entity.OpsInquiryAdapterConfig;
import com.sales.ops.db.entity.OpsInquiryApply;
import com.sales.ops.db.entity.OpsInquiryDetail;
import com.sales.ops.db.entity.OpsInquiryPoHandle;
import com.sales.ops.dto.inquiry.*;
import com.sales.ops.dto.purchase.ToDtoUtil;
import com.sales.ops.feign.inquiry.InquiryTaskFeignApi;
import com.smc.ops.delivery.inquiry.mapper.InquiryApplyMapper;
import com.smc.ops.delivery.inquiry.mapper.InquiryDetailMapper;
import com.smc.ops.delivery.inquiry.mapper.InquiryPoHandleMapper;
import com.smc.ops.delivery.inquiry.mapper.InquirySalesNoticeTaskMapper;
import com.smc.ops.delivery.inquiry.service.InquiryRuleConfigService;
import com.smc.ops.delivery.inquiry.service.InquiryTaskService;
import com.smc.ops.delivery.model.inqa.OpsInquiryReplyRuleConfigDo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 采购催促适配器实现
 */
@Service
@Slf4j
public class InquiryTaskServiceImpl implements InquiryTaskService {

    @Autowired
    private ApplicationContext applicationContext;
//    @Resource
//    private InquiryAs400Service inquiryAs400Service;
//
//    @Resource
//    private InquiryManuService inquiryManuService;
//
//    @Resource
//    private InquiryGZService inquiryGZService;

    @Resource
    private InquiryPoHandleMapper inquiryPoHandleMapper;

    @Resource
    private InquiryApplyMapper inquiryApplyMapper;

    @Resource
    private InquiryDetailMapper inquiryDetailMapper;

    @Resource
    private InquiryTaskFeignApi inquiryTaskFeignApi;

    @Resource
    private InquirySalesNoticeTaskMapper inquirySalesNoticeTaskMapper;

    @Resource
    private InquiryRuleConfigService inquiryRuleConfigService;

    /**
     * 待处理采购催促订单发送
     *
     * @return
     */
    @Override
    public ResultVo<String> execPurchaseSendTask() {
        StringBuilder errorMsg = new StringBuilder();
        StringBuilder returnMsg = new StringBuilder();
        // 查询采购催促处理表中，待处理的催促订单
        List<OpsInquiryPoHandle> unHandleList = inquiryPoHandleMapper.selectUnHandleList(InquiryStatusEnum.DAICHULI.getType());
        if (CollectionUtils.isEmpty(unHandleList)) {
            return ResultVo.success("暂无待发送的催促处理清单");
        }
        // 取出根据供应商分组的清单
        Map<String, List<OpsInquiryPoHandle>> suppilyMap = unHandleList.stream()
                .collect(Collectors.groupingBy(OpsInquiryPoHandle::getSupplierId));
        // 使用并行流来处理供应商清单以提高性能
        suppilyMap.entrySet().forEach(entry -> {
            String supplierId = entry.getKey();
            List<OpsInquiryPoHandle> dataList = entry.getValue();
            Map<Integer, List<OpsInquiryPoHandle>> mapList = SplitBatchUtils.getInsertBatchBySqlserver(dataList, OpsInquiryPoHandle.class);
            for (Map.Entry<Integer, List<OpsInquiryPoHandle>> split : mapList.entrySet()) {
                ResultVo<String> result = processSupplier(supplierId, split.getValue()); // 当超过参数限制时，分批处理供应商的清单
                if (!result.isSuccess()) {
                    errorMsg.append(result.getData());
                } else {
                    returnMsg.append(result.getData());
                }
            }
        });
        // 如果有错误消息，更新最终结果
        if (errorMsg.length() > 0) {
            return ResultVo.failure(errorMsg.toString());
        }
        return ResultVo.success(returnMsg.toString());
    }

    /**
     * 供应商实际发送的操作
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResultVo<String> processSupplier(String supplierId, List<OpsInquiryPoHandle> dataList) {
        StringBuilder errorMessage = new StringBuilder();
        Object result;
        int successCount = 0;
        int errorCount = 0;
        // 根据供应商去读取配置表，取出具体需要实现的方法
        List<OpsInquiryAdapterConfig> adapterConfigList = inquiryPoHandleMapper.selectAdapterBySupplier(supplierId);
        // 未读取到供应商配置信息时，状态置为异常，并邮件通知开发担当
        if (CollectionUtils.isEmpty(adapterConfigList)) {
            // 更新处理异常
            updateResult(InquiryStatusEnum.ERROR.getType(), "未读取到" + supplierId + "的INQ-A发送配置信息", dataList);
            errorMessage.append("未读取到" + supplierId + "的INQ-A发送配置信息");
            errorCount++;
        } else {
            // 获取配置文件
            OpsInquiryAdapterConfig inquiryAdapterConfig = adapterConfigList.get(0);
            try {
                // 自动解析 配置文件中，接口及方法名，自动进行发送
                result = autoCallInterface(inquiryAdapterConfig.getSendMethodName(), inquiryAdapterConfig.getSendClassName(), dataList);
                if (!Objects.isNull(result)) {
                    ResultVo returnResult = (ResultVo) result;
                    // 2024-09-26 更新对应的表时，需要根据催促类型区分，采购催促和订单催促，以此来判断回更主表的状态
                    if (returnResult != null && returnResult.isSuccess()) {
                        // 发送成功,更新对应表信息
                        successCount += updateResult(InquiryStatusEnum.CUICUZHONG.getType(), returnResult.getMessage(), dataList);
                    } else {
                        // 发送失败,更新对应表信息
                        errorCount += updateResult(InquiryStatusEnum.ERROR.getType(),returnResult.getMessage().length()>1000 ? returnResult.getMessage().substring(0, 1000) : returnResult.getMessage(), dataList);
                        errorMessage.append(returnResult.getMessage());
                    }
                }
            } catch (Exception e) {
                log.error("反射调用接口失败: {}. 供应商ID: {}", e.getMessage(), supplierId, e);
                errorCount++;
                errorMessage.append("反射调用接口失败: ").append(e.getMessage());
            }
        }
        if (StringUtils.isNotBlank(errorMessage.toString())) {
            return ResultVo.failure(errorMessage.toString());
        }
        return ResultVo.success(supplierId + "催促处理发送执行成功，发送成功: " + successCount + " 项,发送失败" + errorCount + "项。");
    }

    /**
     * 根据表配置文件，自动解析并调用对应接口
     *
     * @return
     */
    public Object autoCallInterface(String methodName, String className, List<OpsInquiryPoHandle> params) {
//        InquirySendCommonParam object = JSONObject.parseObject(String.valueOf(params), InquirySendCommonParam.class);
        Object result = null;
        try {
            Class<?> classObj = Class.forName(className);
            Object o = applicationContext.getBean(classObj);
            // 获取接口中的方法
            Method[] methods = classObj.getMethods();
            // 指定方法
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    // 调用方法
                    result = method.invoke(o, params);
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            log.error(className + "->" + methodName + "调用失败" + e.getMessage(), e);
        } catch (Exception e) {
            log.error(className + "->" + methodName + "调用失败" + e.getMessage(), e);
        }
        return result;
    }

    @Transactional
    public int updateResult(int code, String msg, List<OpsInquiryPoHandle> inquiryPoHandles) {
        List<Long> idList = inquiryPoHandles.stream().map(OpsInquiryPoHandle::getId).collect(Collectors.toList());
        // 更新handle表处理状态
        int info = inquiryPoHandleMapper.updateHandleResult(code, msg, idList);
        if (info > 0 && code == InquiryStatusEnum.CUICUZHONG.getType()) { // 催促出错时，暂时先不更新主表状态
            List<String> applynoList = new ArrayList<>();
                    // 2024-09-26 更新对应的表时，需要根据催促类型区分，采购催促和订单催促，以此来判断回更主表的状态
            // 根据催促类别进行筛选，采购催促及订单催促的处理方式不一致,先处理采购催促部分
            List<OpsInquiryPoHandle> purchaseList = inquiryPoHandles.stream().filter(item -> item.getInquiryType().equals(InquiryTypeEnum.PURCHASE.getType())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(purchaseList)) {
                applynoList = purchaseList.stream().map(OpsInquiryPoHandle::getInquiryApplyNo).distinct().collect(Collectors.toList());
            }
            inquiryPoHandles.removeAll(purchaseList);
            if (!CollectionUtils.isEmpty(inquiryPoHandles)) {
                // 循环进行校验更新
                for (OpsInquiryPoHandle item : inquiryPoHandles) {
                    // 查询催促表子表中，查询处理表中是否还有未发送成功的单据,没有的话，再更新主单的状态
                    if (CollectionUtils.isEmpty(inquiryPoHandleMapper.getPoHandleByApplyno(item.getInquiryApplyNo(),InquiryStatusEnum.DAICHULI.getType()))) { // 判断是部分回复还是全部回复
                        applynoList.add(item.getInquiryApplyNo());
                    }
                }
            }
            // applyno去重
            applynoList = applynoList.stream().distinct().collect(Collectors.toList());
            info = inquiryApplyMapper.updateResult(code, applynoList); // 根据申请号，批量更新 apply申请表
        }
        return info;
    }

    /**
     * 轮询供应商的回复信息，根据不同供应商来配置
     * @return
     */
    @Override
    public ResultVo<String> execPurchaseReplyTask() throws Exception {
        // 查询采购催促处理表中，催促中的催促订单，去回调结果
        List<OpsInquiryPoHandle> loadingList = inquiryPoHandleMapper.selectUnHandleList(InquiryStatusEnum.CUICUZHONG.getType());
        if (CollectionUtils.isEmpty(loadingList)) {
            return ResultVo.success("暂无待回复的催促处理清单");
        }
        // 取出根据供应商分组的清单
        Map<String, List<OpsInquiryPoHandle>> suppilyMap = loadingList.stream()
                .collect(Collectors.groupingBy(OpsInquiryPoHandle::getSupplierId));
        StringBuilder errMsg = new StringBuilder(); // 错误订单原因返回
        AtomicInteger successQty = new AtomicInteger();
        suppilyMap.forEach((supplierId, dataList) -> {
            Object result;
            List<OpsInquiryPoHandle> resultData;
            // 根据供应商去读取配置表，取出具体需要实现的方法
            List<OpsInquiryAdapterConfig> adapterConfigList = inquiryPoHandleMapper.selectAdapterBySupplier(supplierId);
            // 未读取到供应商配置信息时，状态置为异常，并邮件通知开发担当
            if (CollectionUtils.isEmpty(adapterConfigList)) {
                // 更新处理异常
                updateResult(InquiryStatusEnum.ERROR.getType(), "未读取到" + supplierId + "的回调配置信息", dataList);
                //  邮件通知担当
                return;
            }
            // 获取配置文件
            OpsInquiryAdapterConfig inquiryAdapterConfig = adapterConfigList.get(0);
            try {
                // 自动解析 配置文件中，接口及方法名，自动进行发送
                result = autoCallInterface(inquiryAdapterConfig.getCallbackMethodName(), inquiryAdapterConfig.getCallbackClassName(), dataList);
                if (!Objects.isNull(result)) {
                    ResultVo<List<OpsInquiryPoHandle>> returnResult = (ResultVo<List<OpsInquiryPoHandle>>) result;
                    if (returnResult.isSuccess()) {
                        // 获取供应商回复信息
                        resultData = returnResult.getData();
                        if (!CollectionUtils.isEmpty(resultData)) {
                            // 回调成功，更新表中供应商回复信息
                            List<OpsInquiryPoHandle> updateInquiryResult = updateReplyData(resultData,inquiryAdapterConfig);
                            // 更新成功催促相关表,回更task 回调门户信息
                            if (!CollectionUtils.isEmpty(updateInquiryResult)) {
                                successQty.addAndGet(updateInquiryResult.size());
                                ResultVo<String> salesCallBack = inqaCallbackSalesToTask(updateInquiryResult);
                            }
                        }
                    } else {
                        // 返回错误原因
                        errMsg.append("供应商：").append(supplierId).append(returnResult.getMessage());
                    }
                }
            } catch (Exception e) {
                // 返回错误原因
                errMsg.append("供应商：").append(supplierId).append("回调催促失败，原因为：").append(e.getMessage());
                log.error("反射调用接口失败: {}", e.getMessage(), e);
                return;
            }
        });
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure("回调失败失败的异常信息" + errMsg.toString());
        }
        return ResultVo.success("催促信息回调成功，共计回调用成功" + successQty.get() + "条");
    }


    /**
     * 更新供应商回复信息，到采购催促配置表，采购催促申请主子表
     *
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @DS("opsdb")
    public List<OpsInquiryPoHandle> updateReplyData(List<OpsInquiryPoHandle> returnList,OpsInquiryAdapterConfig inquiryAdapterConfig) throws Exception {
        List<OpsInquiryPoHandle> returnUpdateList = new ArrayList<>();
        // 根据
        List<OpsInquiryReplyRuleConfigDo> ruleList = new ArrayList<>();
        ResultVo<List<OpsInquiryReplyRuleConfigDo>>  ruleReturn = inquiryRuleConfigService.getRulesByServiceTypeAndRuleType("INQA",inquiryAdapterConfig.getSupplierClass());
        if (ruleReturn.isSuccess()) {
            ruleList = ruleReturn.getData();
        }
        int returnCount = 0;
        int applyCount = 0;
        // 更新到 采购催促配置表中
        OpsInquiryPoHandle poHandle;
        for (OpsInquiryPoHandle opsInquiryPoHandle : returnList) {
            // 先根据回复记录 查询OpsInquiryPoHandle表的记录
//            List<OpsInquiryPoHandle> poHandles = inquiryPoHandleMapper.selectPoHandleList(opsInquiryPoHandle);
            List<OpsInquiryPoHandle> poHandles = inquiryPoHandleMapper.selectPoHandleListByTaskNo(opsInquiryPoHandle.getTaskNo());
            if (!CollectionUtils.isEmpty(poHandles) && opsInquiryPoHandle.getReplyTime() != null) {
                poHandle = poHandles.get(0);
                // 将poHandle的信息赋值给opsInquiryPoHandle
                BeanCopyUtil.mergeObject(poHandle, opsInquiryPoHandle);
                // 2024-10-10 获取回复信息时，根据判断回复货期，判断是否更新为已回复状态 针对回复已取消、已发货、制造物流已出库、已取消、555555（国外购入品纳期不确定）等特殊返信，直接变更为已回复
                // 如果催促状态已经是已回复了，则不需要再进行更新
                if (opsInquiryPoHandle.getInquiryStatus().equals(InquiryStatusEnum.YIDAFU.getType())) {
//                    returnUpdateList.add(opsInquiryPoHandle);
                    continue; // 已回复状态的订单，无需再进行更新，防止冗余数据更新
                }
                if (!CollectionUtils.isEmpty(ruleList)) { // 如果存在状态配置
                    try {
                        // 1.根据配置规则，去获取回复的状态信息
                        ResultVo<String> inquiryStatusReply = inquiryRuleConfigService.inquiryRuleConfig(ruleList, InquiryRuleTypeEnum.STATUS.getType(), opsInquiryPoHandle);
                        if (inquiryStatusReply.isSuccess()) {
                            opsInquiryPoHandle.setInquiryStatus(InquiryStatusEnum.getCodeByDesc(inquiryStatusReply.getData()));
                            // 2.催促结果的信息，也根据配置规则进行判断，当已回复时，再进行计算该规则
                            ResultVo<String> inquiryRuleReply = inquiryRuleConfigService.inquiryRuleConfig(ruleList, InquiryRuleTypeEnum.REPLY_DESC.getType(), opsInquiryPoHandle);
                            if (inquiryRuleReply.isSuccess()) {
                                opsInquiryPoHandle.setReplyResultDesc(inquiryRuleReply.getData());
                            }
                            // 3.根据配置规则，进行异常提示的判断
                            ResultVo<String> inquiryErrorWarning = inquiryRuleConfigService.inquiryRuleConfig(ruleList, InquiryRuleTypeEnum.ERROR_WARNING.getType(), opsInquiryPoHandle);
                            if (inquiryErrorWarning.isSuccess() && StringUtils.isNotBlank(inquiryAdapterConfig.getWarningMailTo())) {
                                String inquiryErrorWarningMsg = inquiryErrorWarning.getData()+ ",异常催促申请号：" + opsInquiryPoHandle.getInquiryApplyNo() + "，催促订单号：" + opsInquiryPoHandle.getOrderNo();
                                // 增加异常邮件的列表提醒
                                inquiryRuleConfigService.sendInquiryErrorMail(inquiryAdapterConfig.getWarningMailTo(), inquiryAdapterConfig.getWarningMailCc(),"OPS系统INQA异常信息提醒",  inquiryErrorWarningMsg);
                            }
                        }
                    } catch (Exception e) {
                        log.error("INQA回复信息配置规则转换失败: {}", e.getMessage(), e);
                    }
                }
                // 更新po处理表信息
                int result = inquiryPoHandleMapper.updateReplyMsg(opsInquiryPoHandle);
                if (result > 0) {
                    // 回复信息更新到申请子表中
                    OpsInquiryDetail opsInquiryDetail = new OpsInquiryDetail();
                    // 查询申请子表信息
                    opsInquiryDetail.setInquiryApplyNo(opsInquiryPoHandle.getInquiryApplyNo());
                    opsInquiryDetail.setTaskNo(opsInquiryPoHandle.getTaskNo());
                    opsInquiryDetail.setOrderNo(opsInquiryPoHandle.getOrderNo());
                    List<OpsInquiryDetail> inquiryDetails = inquiryDetailMapper.selectInquiryDetailByTask(opsInquiryDetail);
                    if (!CollectionUtils.isEmpty(inquiryDetails)) {
                        // 取出原表id进行更新，多条明细时，进行循环更新
                        opsInquiryDetail.setId(inquiryDetails.get(0).getId());
                        opsInquiryDetail.setReplyDept(opsInquiryPoHandle.getReplyDept());
                        opsInquiryDetail.setSupplierOrderNo(opsInquiryPoHandle.getSupplierOrderNo());
                        opsInquiryDetail.setReplyNo(opsInquiryPoHandle.getReplyNo());
                        opsInquiryDetail.setReplyDeliveryDate(opsInquiryPoHandle.getReplyDeliveryDate());
                        opsInquiryDetail.setReplyDeliveryDateSrc(opsInquiryPoHandle.getReplyDeliveryDateSrc());
                        opsInquiryDetail.setReplyUser(opsInquiryPoHandle.getReplyUser());
                        opsInquiryDetail.setReplyTime(opsInquiryPoHandle.getReplyTime());
                        opsInquiryDetail.setReplyDelayReason(opsInquiryPoHandle.getReplyDelayReason());
                        opsInquiryDetail.setReplyRemark(opsInquiryPoHandle.getReplyRemark());
                        opsInquiryDetail.setInquiryLevel(inquiryDetails.get(0).getInquiryLevel());
                        opsInquiryDetail.setUpdateTime(new Date());
                        opsInquiryDetail.setUpdateUser("system");
                        opsInquiryDetail.setReplyResultDesc(opsInquiryPoHandle.getReplyResultDesc());
                        //更新到子表中
                        returnCount += inquiryDetailMapper.updateTaknoTodetail(opsInquiryDetail);
                        // 更新inquiry_apply 申请主表信息
                        if (returnCount > 0) {
                            OpsInquiryApply updateInquiryApply = inquiryApplyMapper.getInquiryApplyByHandle(opsInquiryPoHandle);
                            if (opsInquiryPoHandle.getInquiryType().equals(InquiryTypeEnum.PURCHASE.getType())) {
                                // bug14432,INQA更新回复优化，用id更新inquiry_apply表
                                opsInquiryPoHandle.setId(updateInquiryApply.getId());
                                applyCount += inquiryApplyMapper.updateReplyMsg(opsInquiryPoHandle);
                            }
                            // 2024-09-26 更新主表状态时，根据子表中明细判断，当所有明细都为已答复，则更新为已答复
                            if (opsInquiryPoHandle.getInquiryType().equals(InquiryTypeEnum.ORDER.getType())) {
                                if (opsInquiryPoHandle.getReplyDeliveryDate() != null && CollectionUtils.isEmpty(inquiryPoHandleMapper.getPoHandleByApplyno(opsInquiryPoHandle.getInquiryApplyNo(),InquiryStatusEnum.CUICUZHONG.getType()))) { // 判断是部分回复还是全部回复
                                    updateInquiryApply.setInquiryStatus(opsInquiryPoHandle.getInquiryStatus()); // 子项全部都答复完毕时，更新主表状态为已答复
                                }
                                // 主表中保留对应子项中最晚的有效截止日期,当主表中有效截止日为空，或者早于当前截止日时，则不更新主表状态
                                if (updateInquiryApply.getReplyDeliveryDate() == null || updateInquiryApply.getReplyDeliveryDate().before(opsInquiryPoHandle.getHopeDeliveryDate())) {
                                    updateInquiryApply.setReplyDept(opsInquiryPoHandle.getReplyDept());
                                    updateInquiryApply.setSupplierOrderNo(opsInquiryPoHandle.getSupplierOrderNo());
                                    updateInquiryApply.setReplyNo(opsInquiryPoHandle.getReplyNo());
                                    updateInquiryApply.setReplyDeliveryDate(opsInquiryPoHandle.getReplyDeliveryDate());
                                    updateInquiryApply.setReplyDeliveryDateSrc(opsInquiryPoHandle.getReplyDeliveryDateSrc());
                                    updateInquiryApply.setReplyUser(opsInquiryPoHandle.getReplyUser());
                                    updateInquiryApply.setReplyTime(opsInquiryPoHandle.getReplyTime());
                                    updateInquiryApply.setReplyDelayReason(opsInquiryPoHandle.getReplyDelayReason());
                                    updateInquiryApply.setReplyRemark(opsInquiryPoHandle.getReplyRemark());
                                    updateInquiryApply.setReplyResultDesc(opsInquiryPoHandle.getReplyResultDesc());
                                }
                                updateInquiryApply.setUpdateTime(new Date());
                                updateInquiryApply.setUpdateUser("system");
                                applyCount +=  inquiryApplyMapper.updateReplyMsgByApply(updateInquiryApply);
                            }
                        }
                    }
                    returnUpdateList.add(opsInquiryPoHandle);
                }
            }
        }
        return returnUpdateList;
    }
    public ResultVo<String> inqaCallbackSalesToTask(List<OpsInquiryPoHandle> opsInquiryList) throws Exception {
        StringBuilder returnMeg = new StringBuilder();
        StringBuilder errMsg = new StringBuilder();
        // 筛选出采购催促的清单，进行回传处理
        List<OpsInquiryPoHandle> purchaseList = opsInquiryList.stream().filter(item -> item.getInquiryType().equals(InquiryTypeEnum.PURCHASE.getType())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(purchaseList)) {
            // todo 测试阶段，采购催促还继续维持原先的回传，待切换测试后再开放下面代码
            ResultVo<String>  purchaseResultVo = callbackToTask(purchaseList);
            returnMeg.append(purchaseResultVo.getData());
        }
        // 筛选出订单催催的清单，回传主子表信息
        List<OpsInquiryPoHandle> orderList = opsInquiryList.stream().filter(item -> item.getInquiryType().equals(InquiryTypeEnum.ORDER.getType())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(orderList)) {
            // 筛选不同的申请号集合，以遍回传
            List<String> applynoList = opsInquiryList.stream().map(OpsInquiryPoHandle::getInquiryApplyNo).distinct().collect(Collectors.toList());
            InquirySalesApplyReurn returnOrderVo;
            InquiryOrderReurn inquiryOrderReurn;
            InquiryOrderMasterDto inquiryOrderMasterDto;
            int successNum = 0;
            int failNum = 0;
            errMsg = new StringBuilder();
            // 根据applyno查询出所有的申请的主子项明细，然后构建返回实体，返回给门户
            for (String applyNo : applynoList) {
                boolean isError = false;
                inquiryOrderReurn = new InquiryOrderReurn();
                returnOrderVo = new InquirySalesApplyReurn();
                List<InquiryOrderMasterDto> opsInqbApplies = inquiryApplyMapper.getInquiryApplyByApplyno(applyNo);
                if (!CollectionUtils.isEmpty(opsInqbApplies) && StringUtils.isNotBlank(opsInqbApplies.get(0).getBatchNo())) {
                    inquiryOrderMasterDto = opsInqbApplies.get(0);
                    returnOrderVo.setInquiryType(inquiryOrderMasterDto.getInquiryType());
                    if (inquiryOrderMasterDto.getReplyDeliveryDate() != null && inquiryOrderMasterDto.getHopeDeliveryDate() != null) { // 预期值判断
                        // 当返回的纳期>期望纳期时 就是超预期，反之为预期内
                        if (inquiryOrderMasterDto.getReplyDeliveryDate().after(inquiryOrderMasterDto.getHopeDeliveryDate())) {
                            inquiryOrderMasterDto.setInquiryResultDescription("超预期");
                        } else {
                            inquiryOrderMasterDto.setInquiryResultDescription("预期内");
                        }
                    }
                    if (inquiryOrderMasterDto.getInquiryStatus().equals(InquiryStatusEnum.YIDAFU.getType())) {
                        inquiryOrderMasterDto.setStatus(SalesInquiryReturnEnum.REPLYSUCCESS.getType());
                        inquiryOrderMasterDto.setStatusDescription(SalesInquiryReturnEnum.REPLYSUCCESS.getDesc());
                    }
                    if (inquiryOrderMasterDto.getInquiryStatus().equals(InquiryStatusEnum.CUICUZHONG.getType())) {
                        inquiryOrderMasterDto.setStatus(SalesInquiryReturnEnum.ADDSUCCESS.getType());
                        inquiryOrderMasterDto.setStatusDescription(SalesInquiryReturnEnum.ADDSUCCESS.getDesc());
                    }
                    List<InquiryOrderDetailReturnDto> opsInquiryDetails = inquiryDetailMapper.selectInquiryDetailReturn(applyNo);
                    if (!CollectionUtils.isEmpty(opsInquiryDetails)) {
                        inquiryOrderReurn.setInquiryOrderMaster(inquiryOrderMasterDto);
                        inquiryOrderReurn.setInquiryOrderDetails(opsInquiryDetails);
                        returnOrderVo.setInquiryOrderReurn(inquiryOrderReurn);
                        ResultVo<String> taskResult = sendMsgToOrderTaskNew(returnOrderVo, isError);
                        if (taskResult.isSuccess()) {
                            successNum++;
                        } else {
                            failNum++;
                            errMsg.append(taskResult.getMessage());
                        }
                    }
                }
            }
            returnMeg.append("订单催促信息回调成功，共计回写成功" + successNum + "条；回写失败" + failNum + "条，错误信息 " + errMsg.toString());
        }
        return ResultVo.success(returnMeg.toString());
    }
    /**
     * 通过feign 接口，回调shareapp 来回调 task表的事件
     */
    public ResultVo<String> callbackToTask(List<OpsInquiryPoHandle> returnList) throws Exception {
        List<InquiryApplyVerifyReurn> reurns = new ArrayList<>();
        InquiryApplyVerifyReurn returnVo;
        OpsInquiryApply opsInquiryApply;
        int successNum = 0;
        int failNum = 0;
        StringBuilder errMsg = new StringBuilder();
        for (OpsInquiryPoHandle opsInquiryPoHandle : returnList) {
            boolean isError = false;
            opsInquiryApply = inquiryApplyMapper.getInquiryApplyByHandle(opsInquiryPoHandle);
            if (opsInquiryApply != null && StringUtils.isNotBlank(opsInquiryApply.getBatchNo())) {
                ResultVo<String> oneResult;
                returnVo = new InquiryApplyVerifyReurn();
                ToDtoUtil.PoToDto(opsInquiryApply, returnVo); // 实体转换
                if (returnVo.getReplyDeliveryDate() != null && returnVo.getHopeDeliveryDate() != null) { // 预期值判断
                    // 当返回的纳期>期望纳期时 就是超预期，反之为预期内
                    // 2025-06-11 切换为直接用字段显示
                    returnVo.setInquiryResultDescription(opsInquiryPoHandle.getReplyResultDesc());
//                    if (returnVo.getReplyDeliveryDate().after(returnVo.getHopeDeliveryDate())) {
//                        returnVo.setInquiryResultDescription("超预期");
//                    } else {
//                        returnVo.setInquiryResultDescription("预期内");
//                    }
                }
                if (opsInquiryApply.getInquiryStatus().equals(InquiryStatusEnum.YIDAFU.getType())) {
                    returnVo.setStatus(SalesInquiryReturnEnum.REPLYSUCCESS.getType());
                    returnVo.setStatusDescription(SalesInquiryReturnEnum.REPLYSUCCESS.getDesc());
                }
                if (opsInquiryApply.getInquiryStatus().equals(InquiryStatusEnum.CUICUZHONG.getType())) {
                    returnVo.setStatus(SalesInquiryReturnEnum.ADDSUCCESS.getType());
                    returnVo.setStatusDescription(SalesInquiryReturnEnum.ADDSUCCESS.getDesc());
                }
                if (opsInquiryApply.getInquiryStatus().equals(InquiryStatusEnum.ERROR.getType())) {
                    returnVo.setStatus(SalesInquiryReturnEnum.REPLYERROR.getType());
                    returnVo.setStatusDescription(opsInquiryPoHandle.getInquiryHandleResult());
                    isError = true;
                }
                // 2024-03-26 门户交互时，修改为用 申请号和子项号进行,回传时拆分回传
                if (opsInquiryApply.getInquiryApplyNo().contains("-")) {
                    String[] split = opsInquiryApply.getInquiryApplyNo().split("-");
                    returnVo.setInquiryApplyNo(split[0]);
                    returnVo.setItemno(Integer.parseInt(split[1]));
                }
                oneResult = sendMsgToOrderTask(returnVo, isError);
                if (oneResult.isSuccess()) {
                    successNum++;
                } else {
                    failNum++;
                    errMsg.append(oneResult.getMessage());
                }
                reurns.add(returnVo);
            }
        }
        return ResultVo.success("催促信息回调成功，共计回写成功" + successNum + "条；回写失败" + failNum + "条，错误信息 " + errMsg.toString());
    }

    private ResultVo<String> sendMsgToOrderTaskNew(InquirySalesApplyReurn inquiryReturn, Boolean iserror) {
        InquiryUpTaskInfoVO upTaskInfoVO = new InquiryUpTaskInfoVO();
        upTaskInfoVO.setBatchNo(inquiryReturn.getInquiryOrderReurn().getInquiryOrderMaster().getBatchNo());
        upTaskInfoVO.setOptUserNo("INQA-Reply");
        if (iserror) {
            upTaskInfoVO.setErrorMsg(inquiryReturn.getInquiryOrderReurn().getInquiryOrderMaster().getStatusDescription()); // 异常信息
        }
        // 最外层实体
        InquiryDealReturnOpsParamVO dealReturnOpsParamVO = new InquiryDealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(10);
        InquiryDealReturnOpsParam param = new InquiryDealReturnOpsParam();
        param.setCommonReturnParam(inquiryReturn);
        dealReturnOpsParamVO.setDealReturnOpsParam(param);
        InquiryOpsSalesCommonParamVO vo = new InquiryOpsSalesCommonParamVO();
        vo.setData(dealReturnOpsParamVO);
        upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(vo));
        upTaskInfoVO.setReturnStatus("0");
        return upTaskInfoByBatchNo(upTaskInfoVO);
    }

    private ResultVo<String> sendMsgToOrderTask(InquiryApplyVerifyReurn inquiryReturn, Boolean iserror) {
//        List<InquiryApplyVerifyReurn> reurns = new ArrayList<>();
//        reurns.add(inquiryReturn);
        InquiryUpTaskInfoVO upTaskInfoVO = new InquiryUpTaskInfoVO();
        upTaskInfoVO.setBatchNo(inquiryReturn.getBatchNo());
        upTaskInfoVO.setOptUserNo(inquiryReturn.getReplyUser());
        if (iserror) {
            upTaskInfoVO.setErrorMsg(inquiryReturn.getStatusDescription()); // 异常信息
        }
        // 最外层实体
        InquiryDealReturnOpsParamVO dealReturnOpsParamVO = new InquiryDealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(10);
        InquiryDealReturnOpsParam param = new InquiryDealReturnOpsParam();
        param.setCommonReturnParam(inquiryReturn);
        dealReturnOpsParamVO.setDealReturnOpsParam(param);
        InquiryOpsSalesCommonParamVO vo = new InquiryOpsSalesCommonParamVO();
        vo.setData(dealReturnOpsParamVO);
        upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(vo));
        upTaskInfoVO.setReturnStatus("0");
        return upTaskInfoByBatchNo(upTaskInfoVO);
    }

    public ResultVo<String> upTaskInfoByBatchNo(InquiryUpTaskInfoVO param) {
        if (Objects.isNull((param)) || StringUtils.isBlank(param.getBatchNo())) {
            return ResultVo.failure("批次号不可为空.");
        }
        // todo 回传task时，优化为根据主键ID进行更新
        InquiryUpTaskInfoVO taskInfo = inquirySalesNoticeTaskMapper.getSalesNoticeTask(param.getBatchNo());
        if (taskInfo != null) {
            param.setId(taskInfo.getId());
            int update = inquirySalesNoticeTaskMapper.updateSalesNoticeTask(param);
            if (update > 0) {
                return ResultVo.success("操作成功.");
            }
        }
        return ResultVo.failure("操作失败.");
    }


    @Override
    public ResultVo<String> execPurchaseReplyTaskIncrement() throws Exception {
//        // 1.查询采购催促处理表中，催促中的暂未回复催促订单，去回调结果
        List<OpsInquiryAdapterConfig> incrementLoadingList = inquiryPoHandleMapper.selectIncrementList();
        int successQty = 0;
        StringBuffer  successMsg = new StringBuffer();
        StringBuffer  errMsg = new StringBuffer();
        // 循环处理各个供应商的增量
        for (OpsInquiryAdapterConfig adapterConfig : incrementLoadingList) {
            Object result;
            List<OpsInquiryPoHandle> resultData;
            try {
                // 自动解析 配置文件中，接口及方法名，自动进行发送
                result = autoCallInterfaceObject(adapterConfig.getCallbackIncrementMethodName(), adapterConfig.getCallbackIncrementClassName(), JSONUtil.toJsonStr(adapterConfig));
                if (!Objects.isNull(result)) {
                    ResultVo<List<OpsInquiryPoHandle>> returnResult = (ResultVo<List<OpsInquiryPoHandle>>) result;
                    if (returnResult.isSuccess()) {
                        // 获取供应商回复信息
                        resultData = returnResult.getData();
                        if (!CollectionUtils.isEmpty(resultData)) {
                            // 回调成功，更新表中供应商回复信息
                            List<OpsInquiryPoHandle> updateResult = updateReplyData(resultData,adapterConfig);
                            // 更新成功催促相关表,回更task 回调门户信息
                            if (!CollectionUtils.isEmpty(updateResult)) {
                                successQty = successQty + updateResult.size();
                                // 返回更新后的OpsInquiryPoHandle数据
                                ResultVo<String> salesCallBack = inqaCallbackSalesToTask(updateResult);
                                successMsg.append(salesCallBack.getData());
                                // 回调成功后，获取最大增量值，更新配置表中上次执行增量数据
                                adapterConfig.setCallbackIncrementLasttime(updateResult.get(0).getReplyTime());
                                adapterConfig.setCallbackIncrementLastid(updateResult.get(0).getId().toString());
                                adapterConfig.setUpdateUser("INQA-Increment");
                                inquiryPoHandleMapper.updateIncrementMaxTime(adapterConfig);
                            }
                        }
                    } else {
                        // 返回错误原因
                        errMsg.append("供应商：").append(adapterConfig.getSupplierId()).append(returnResult.getMessage());
                    }
                }
            } catch (Exception e) {
                // 返回错误原因
                errMsg.append("供应商：").append(adapterConfig.getSupplierId()).append("回调催促失败，原因为：").append(e.getMessage());
                log.error("反射调用接口失败: {}", e.getMessage(), e);
            }
        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure("回调失败失败的异常信息" + errMsg.toString());
        }
        return ResultVo.success("催促信息回调成功，共计回调用成功" + successQty + "条,回调信息："+ successMsg.toString());
    }

    /**
     * 将params转换为json String
     * @param methodName
     * @param className
     * @param params
     * @return
     */
    public Object autoCallInterfaceObject(String methodName, String className, String params) {
        Object result = null;
        try {
            Class<?> classObj = Class.forName(className);
            Object o = applicationContext.getBean(classObj);
            // 获取接口中的方法
            Method[] methods = classObj.getMethods();
            // 指定方法
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    // 调用方法
                    result = method.invoke(o, params);
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            log.error(className + "->" + methodName + "调用失败" + e.getMessage(), e);
        } catch (Exception e) {
            log.error(className + "->" + methodName + "调用失败" + e.getMessage(), e);
        }
        return result;
    }
}
