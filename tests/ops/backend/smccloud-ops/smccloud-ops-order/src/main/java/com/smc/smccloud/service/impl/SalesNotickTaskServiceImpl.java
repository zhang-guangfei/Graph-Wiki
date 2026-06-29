package com.smc.smccloud.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sales.ops.common.errorEnum.OrderCancelCodeEnum;
import com.sales.ops.dto.inqb.InqbSalesApplyAddParam;
import com.sales.ops.dto.inqb.InqbSalesApplyAddReturn;
import com.sales.ops.dto.inquiry.InquiryApplyAddParam;
import com.sales.ops.dto.inquiry.InquiryApplyVerifyReurn;
import com.sales.ops.dto.inquiry.InquirySalesApplyReurn;
import com.sales.ops.dto.order.AutoOrderChangeToInitStatusDto;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.order.OpsOrderModifyDto;
import com.sales.ops.dto.purchase.ModifyPurchaseDto;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.purchase.PurchaseModifyApplyInfoDto;
import com.sales.ops.dto.replacement.NotifyShipmentPlanImportErrorDto;
import com.sales.ops.dto.replacement.NotifyShipmentPlanResult;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.feign.OpsOrderFeignApi;
import com.sales.ops.feign.OpsWmFeignApi;
import com.sales.ops.feign.PurchaseModifyFeignApi;
import com.sales.ops.feign.RequestPurchaseFeignApi;
import com.sales.ops.feign.inqb.InqbAdapterFeignApi;
import com.sales.ops.feign.inquiry.InquiryAdapterFeignApi;
import com.sales.ops.feign.notify.NotifyShipmentPlanSMSFeignAPi;
import com.sales.ops.feign.purchase.PurchaseBatchEditFeignApi;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.OpsOrderResultBean;
import com.smc.smccloud.core.model.enums.OrderSplitTypeEnum;
import com.smc.smccloud.core.model.enums.*;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.log.annotation.SysLog;
import com.smc.smccloud.log.mapper.OpsSysLogMapper;
import com.smc.smccloud.log.model.OpsSysLogDO;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.*;
import com.smc.smccloud.model.OrderModify.OrderModifyDO;
import com.smc.smccloud.model.Purchase.OpsPurchaseOrderDO;
import com.smc.smccloud.model.aboutsms.OpsDlvPromptingVO;
import com.smc.smccloud.model.aboutsms.OpsRemindDO;
import com.smc.smccloud.model.adapter.order.OrderDeleteItem;
import com.smc.smccloud.model.enums.BalTypeEnum;
import com.smc.smccloud.model.enums.CallBackSMSApplyTypeEnum;
import com.smc.smccloud.model.enums.OpsSalesTaskHandStatus;
import com.smc.smccloud.model.enums.OpsSalesTaskReturnStatus;
import com.smc.smccloud.model.invoice.SalesInvoiceMidInfoDO;
import com.smc.smccloud.model.notice.NoticeShipmentPlanTaskVO;
import com.smc.smccloud.model.notice.OpsSalesNoticeConfigDO;
import com.smc.smccloud.model.notice.OpsSalesNoticeTaskDO;
import com.smc.smccloud.model.order.*;
import com.smc.smccloud.model.order.orderEdit.OrderModifyUpInfoRequest;
import com.smc.smccloud.model.order.orderEdit.OrderModifyUpInfoVO;
import com.smc.smccloud.model.order.orderEdit.UpOrderDlvDateInfo;
import com.smc.smccloud.model.order.orderEdit.UpOrderDlvDateVo;
import com.smc.smccloud.model.order.orderdel.SalesErpOrderDeleteResultVO;
import com.smc.smccloud.model.order.orderdel.SalesErpOrderDeleteTaskBean;
import com.smc.smccloud.model.order.orderdel.SecondProcessWithUiVO;
import com.smc.smccloud.model.ordermodify.OrderModifyVO;
import com.smc.smccloud.model.ordermodify.PurchaseModifyVO;
import com.smc.smccloud.model.ordermodify.SpecialVO;
import com.smc.smccloud.model.preaccount.PreAccountOrderSalesCallBackStatusEnum;
import com.smc.smccloud.model.preaccount.PreAccountStatusEnum;
import com.smc.smccloud.model.prestock.PreOrderAccountDetailVO;
import com.smc.smccloud.model.prestock.PreStockApplyDetailDTO;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import com.smc.smccloud.model.receiveorder.RcvDetailVO;
import com.smc.smccloud.model.receiveorder.RcvMasterVO;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.model.salestask.ReleaseOrderParamVO;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.model.sampleorder.CheckRcvQtyVO;
import com.smc.smccloud.model.sampleorder.FindHandSampleBalHandVO;
import com.smc.smccloud.model.sampleorder.SampleBalApplySpecialVO;
import com.smc.smccloud.model.sampleorder.SampleBalApplyVO;
import com.smc.smccloud.model.shikomi.ShikomiBudgetVO;
import com.smc.smccloud.model.shikomi.ShikomiWarnCallBackDTO;
import com.smc.smccloud.model.stock.DealReturnOpsParam;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author lyc
 * @Date 2023/7/12 15:06
 * @Descripton TODO
 */
@Service
@Slf4j
public class SalesNotickTaskServiceImpl implements SalesNotickTaskService {

    @Resource
    private OpsSalesNoticeTaskMapper opsSalesNoticeTaskMapper;
    @Resource
    private OpsSalesNoticeConfigMapper opsSalesNoticeConfigMapper;
    @Resource
    private PlatformTransactionManager transactionManager;
    @Value("${menhu.url}")
    private String menHuUrl;

    @Value("${outsidesys.yingye-system-auth-url}")
    private String yingyeSysAuthUrl;
    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private OpsSysLogMapper opsSysLogMapper;

    @Resource
    private OrderModifyMapper orderModifyMapper;

    @Autowired
    @Lazy
    private ReceiveOrderService receiveOrderService;
    @Resource
    private OrderModifyService orderModifyService;

    @Resource
    private OpsWmFeignApi opsWmFeignApi;

    @Resource
    private PreStockFeignApi preStockFeignApi;

    @Resource
    private OpsOrderFeignApi opsOrderFeignApi;

    @Resource
    private RcvDetailMapperReadOnlyMapper rcvDetailMapperReadOnlyMapper;

    @Resource
    private CreateTokenForOtherServer createTokenForOtherServer;

    @Resource
    private ProductServiceFeignApi productServiceFeignApi;

    @Resource
    private PurchaseModifyFeignApi purchaseModifyFeignApi;

    @Resource
    private OpsCommonService opsCommonService;

    @Resource
    private SampleOrderApplyFeignApi sampleOrderApplyFeignApi;
    @Resource
    private OpsAttachedFileManageService opsAttachedFileManageService;

    @Resource
    private CommonOutSideInterfaceAuthService commonOutSideInterfaceAuthService;

    @Resource
    private SMSOrderServiceFeignApi smsOrderServiceFeignApi;

    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private OpsRemindMapper opsRemindMapper;

    @Resource
    private OrderEditService orderEditService;

    @Resource
    private SalesInvoiceMidInfoMapper salesInvoiceMidInfoMapper;

    @Resource
    private PurchaseModifyApplyFeignApi purchaseModifyApplyFeignApi;

    @Resource
    private PurchaseBatchEditFeignApi purchaseBatchEditFeignApi;

    @Resource
    private InquiryAdapterFeignApi inquiryAdapterFeignApi;

    @Resource
    private InqbAdapterFeignApi inqbAdapterFeignApi;

    @Resource
    private OpsPurchaseOrderMapper opsPurchaseOrderMapper;

    @Resource
    private CommonMapper commonMapper;
    @Resource
    private OrderStatusMapper orderStatusMapper;
    @Resource
    private RequestPurchaseFeignApi requestPurchaseFeignApi;
    @Resource
    private OpsOrderAssignResultMapper opsAssignResultMapper;

    @Resource
    private NotifyShipmentPlanSMSFeignAPi notifyShipmentPlanSMSFeignAPi;

    @Resource
    private RedisManager redisManager;

    /**
     * 回调门户处理结果接口
     */
    private static final String RETURN_DEAL_INFO_URL = "/saleManageSystem/opsReturnInfo/dealReturnInfo";

    @Override
    @SysLog("门户共通接口")
    @Transactional(rollbackFor = Exception.class)
    @DS("sharedb")
    public ResultVo<String> execInsertOpsSalesNoticeTask(String paramJson) {
        if (StringUtils.isBlank(paramJson)) {
            return ResultVo.failure("40001","入参不可为空");
        }
        log.info("门户共通接口 data: {}",paramJson);
        List<OpsSalesNoticeTaskDO> list = new ArrayList<>();
        StringBuilder batchNoMsg = new StringBuilder();
        batchNoMsg.append("");
        try {
            list = JSONArray.parseArray(paramJson, OpsSalesNoticeTaskDO.class);
        } catch (Exception e) {
            return ResultVo.failure("40001","入参格式不正确,解析失败.");
        }
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure("40001","参数不可为空");
        }
        List<OpsSalesNoticeTaskDO> arr = new ArrayList<>();
        OpsSalesNoticeTaskDO taskDO;
        for (OpsSalesNoticeTaskDO item : list) {
            if (item == null) {
                return ResultVo.failure("40001","入参数为空");
            }
            if (StringUtils.isBlank(item.getBusinessCode())) {
                return ResultVo.failure("40001","业务编码不可存在空值");
            }
            if (StringUtils.isBlank(item.getParameter())) {
                return ResultVo.failure("40001","调用参数不可存在空值");
            }
            taskDO =  BeanCopyUtil.copy(item,OpsSalesNoticeTaskDO.class);
            // taskDO.setBatchNo(PublicUtil.getRandomBatchNo("T", new Date()));
            taskDO.setBatchNo(UIDGenerator.generateUID());
            taskDO.setHandleStatus(OpsSalesTaskHandStatus.notHand.getCode());
            taskDO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
            taskDO.setCreateUser(CommonConstants.COMMON_USER_SALES);
            taskDO.setTryCount(0);
            taskDO.setErrorHandCount(0);
            taskDO.setSource(CommonConstants.COMMON_USER_SALES);
            taskDO.setCreateTime(new Date());
            arr.add(taskDO);
        }
        StringBuilder errMsg = new StringBuilder();
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.execute(transactionStatus -> {
            try {
                for (OpsSalesNoticeTaskDO item : arr) {
                    batchNoMsg.append(item.getBatchNo()).append(";");
                    opsSalesNoticeTaskMapper.insert(item);
                }
            } catch (Exception e) {
                log.error("写入对接任务表失败 参数:{} 异常信息 {}",paramJson,e.getMessage(),e);
                errMsg.append("系统内部异常:").append(e.getMessage()).append(";");
                transactionStatus.setRollbackOnly(); // 手动回滚
                return false;
            }
            return true;
        });
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure("-1",errMsg.toString());
        }
        return ResultVo.successDataByCode("0","成功推送"+arr.size()+"条数据",batchNoMsg.toString());
    }

    @Override
    public ResultVo<String> execCallInterface(String groupCode) {

        List<OpsSalesNoticeConfigDO> businessCodeList = null;

        String key = Constants.OPS_NOTICE_BUSINESSCODE_BYGROUP+groupCode;

        Object o = redisManager.get(key);
        if (o == null) {
            // 获取需要执行的业务编码
            businessCodeList = getBusinessCodeList(groupCode);
            redisManager.set(key,JSONArray.toJSONString(businessCodeList),60*60*24);
        } else {
            businessCodeList = JSONArray.parseArray(o.toString(), OpsSalesNoticeConfigDO.class);
        }

        if (CollectionUtils.isEmpty(businessCodeList)) {
            return ResultVo.failure(groupCode+"组未找到需要执行的业务编码");
        }
        // 提取bussinessCode
        List<String> businessCodeList1 = businessCodeList.stream().map(OpsSalesNoticeConfigDO::getBusinessCode).collect(Collectors.toList());

        // 查询待执行的任务列表
        List<OpsSalesNoticeTaskDO> opsSalesNoticeTaskDOS = opsSalesNoticeTaskMapper.getCanHandleList(businessCodeList1);
        if (CollectionUtils.isEmpty(opsSalesNoticeTaskDOS)) {
            return ResultVo.success("暂无可执行的任务列表");
        }
        OpsSalesNoticeConfigDO configByCode;
        LambdaUpdateWrapper<OpsSalesNoticeTaskDO> updateWrapper;
        for (OpsSalesNoticeTaskDO item : opsSalesNoticeTaskDOS) {
            boolean isInsertModifySuccess = false;
            configByCode = getConfigByCode(item.getBusinessCode());
            Date startHandDate = new Date();
            Object result = null;
            try {
                if(Objects.isNull(configByCode)) {
                    UpTaskInfoVO info = new UpTaskInfoVO();
                    info.setBatchNo(item.getBatchNo());
                    info.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
                    info.setErrorMsg("未找到业务编码对应配置");
                    info.setOptUserNo(CommonConstants.COMMON_USER_OPS_JOB);
                    upTaskInfoByBatchNo(info);
                    continue;
                }

                OpsSalesCommonParamVO opsSalesCommonParamVO = JSONObject.parseObject(item.getParameter(), OpsSalesCommonParamVO.class);
                if (Objects.isNull(opsSalesCommonParamVO)) {
                    UpTaskInfoVO info = new UpTaskInfoVO();
                    info.setBatchNo(item.getBatchNo());
                    info.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
                    info.setErrorMsg("参数解析异常");
                    info.setOptUserNo(CommonConstants.COMMON_USER_OPS_JOB);
                    upTaskInfoByBatchNo(info);
                    continue;
                }

                // 写入order_modify
                // 变更采购运输方式、变更工厂指定出荷日、变更供应商这三个接入采购修改
                if (!Objects.isNull(configByCode.getIsInsertOrderModify()) && configByCode.getIsInsertOrderModify()) {
                    String dataJson = JSONUtil.toJsonStr(opsSalesCommonParamVO.getData());
                    OrderModifyVO data = JSONObject.parseObject(dataJson, OrderModifyVO.class);

                    if(OrderModifyTypeEnum.ddhy.getCode().equals(item.getBusinessCode())) {
                        /**
                         * 订单还原 18506bug 数量拆分和型号拆分订单不允许申请还原
                         */
                        ResultVo<RcvDetailVO> orderDetail = receiveOrderService.findOrderDetail(data.getOrderNo());
                        if (!orderDetail.isSuccess() || orderDetail.getData() == null) {
                            ddhyCheckReturnSales(data,item,"订单不存在,还原失败");
                            continue;
                        }
                        if (!"0".equals(orderDetail.getData().getProdFlag())) {
                            ddhyCheckReturnSales(data,item,"数量拆分和型号拆分订单不允许申请还原");
                            continue;
                        }
                    }

                    if (!isInsertPurchaseOrderModify(configByCode.getBusinessCode(),data.getChangeType())) {
                        isInsertModifySuccess = insertOrderModify(data, item.getBatchNo(), configByCode.getBusinessCode());
                    } else if (isInsertPurchaseOrderModify(configByCode.getBusinessCode(),data.getChangeType())) {
                        isInsertModifySuccess = insertPurchaseOrderModify(data, item.getBatchNo(), item.getBusinessCode());
                    }
                }

                if (StringUtils.isBlank(configByCode.getMethodName()) || StringUtils.isBlank(configByCode.getClassName())) {
                    UpTaskInfoVO info = new UpTaskInfoVO();
                    info.setBatchNo(item.getBatchNo());
                    info.setErrorMsg("需要手动处理");
                    info.setOptUserNo("job");
                    info.setHandleStatus(OpsSalesTaskHandStatus.hand_success.getCode());
                    upTaskInfoByBatchNo(info);
                    continue;
                }
                // shikomi残数不足维护 / 信用拦截申请放行
                if (configByCode.getBusinessCode().equals("I") || configByCode.getBusinessCode().equals("K")) {
                    item.setBatchNo(item.getApplyNo());
                }
                result = callInterface(configByCode.getMethodName(), configByCode.getClassName(), item.getParameter(),item.getBatchNo(),configByCode.getBusinessCode(),item.getApplyNo());
            } catch (Exception e) {
                log.error("反射调用接口失败: {}",e.getMessage(),e);
                UpTaskInfoVO info = new UpTaskInfoVO();
                info.setBatchNo(item.getBatchNo());
                if (item.getErrorHandCount() == null) {
                    item.setErrorHandCount(0);
                }
                info.setErrorHandCount(item.getErrorHandCount()+1);
                info.setHandleStatus(OpsSalesTaskHandStatus.system_error.getCode());
                info.setErrorMsg("接口响应失败信息: "+e.getMessage());
                info.setOptUserNo(CommonConstants.COMMON_USER_OPS_JOB);
                upTaskInfoByBatchNo(info);
                if (isInsertModifySuccess) {
                    OrderModifyVO orderModifyVO = new OrderModifyVO();
                    orderModifyVO.setBatchNo(info.getBatchNo());
                    orderModifyVO.setUpdateUser(CommonConstants.COMMON_USER_OPS_JOB);
                    orderModifyVO.setStatus(Integer.parseInt(OrderModifyEnum.notHand.getCode()));
                    orderModifyVO.setRemark("接口响应失败信息: "+e.getMessage());
                    orderModifyService.updateOrderModifyInfo(orderModifyVO);
                }
                continue;
            }
            Date endHandDate = new Date();
            if (item.getErrorHandCount() == null) {
                item.setErrorHandCount(0);
            }
            if (Objects.isNull(result)) {
                item.setErrorMsg(configByCode.getClassName()+"->"+configByCode.getMethodName()+"执行失败无返回");
                item.setHandleStatus(OpsSalesTaskHandStatus.system_error.getCode());
                item.setErrorHandCount(item.getErrorHandCount()+1);
            } else {
                ResultVo returnResult =  (ResultVo)result;
                if (returnResult.isSuccess()) {
                    item.setErrorMsg(null);
                    item.setHandleStatus(OpsSalesTaskHandStatus.hand_success.getCode());
                } else {
                    item.setErrorMsg(returnResult.getMessage());
                    item.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
                }
                item.setReturnResult(JSONUtil.toJsonStr(returnResult));
                if(StringUtils.isNotBlank(configByCode.getCallbackClassName())
                        && StringUtils.isNotBlank(configByCode.getCallbackMethodName())
                        && !Objects.isNull(returnResult.getData())) {
                    OpsSalesCommonParamVO bean = new OpsSalesCommonParamVO();
                    bean.setData(returnResult.getData());
                    // 战略在库申请需要申请处理完回改task的回调参数 这里不进行设置
                    if (!configByCode.getBusinessCode().equals("1")) {
                        item.setCallBackParameter(JSONUtil.toJsonStr(bean));
                    }
                }
            }
            updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(OpsSalesNoticeTaskDO::getId,item.getId())
                    .set(OpsSalesNoticeTaskDO::getReturnResult,item.getReturnResult())
                    .set(OpsSalesNoticeTaskDO::getHandleStartTime,startHandDate)
                    .set(OpsSalesNoticeTaskDO::getHandleEndTime,endHandDate)
                    .set(OpsSalesNoticeTaskDO::getHandleStatus,item.getHandleStatus())
                    .set(OpsSalesNoticeTaskDO::getErrorMsg,item.getErrorMsg())
                    .set(item.getErrorHandCount() != 0,OpsSalesNoticeTaskDO::getErrorHandCount,item.getErrorHandCount())
                    .set(StringUtils.isNotBlank(item.getCallBackParameter()), OpsSalesNoticeTaskDO::getCallBackParameter,item.getCallBackParameter())
                    .set(OpsSalesNoticeTaskDO::getUpdateTime,new Date())
                    .set(OpsSalesNoticeTaskDO::getUpdateUser,CommonConstants.COMMON_USER_OPS_JOB);
            opsSalesNoticeTaskMapper.update(null,updateWrapper);
        }
        return ResultVo.success("执行完毕");
    }

    public void ddhyCheckReturnSales(OrderModifyVO data, OpsSalesNoticeTaskDO item, String descMsg ) {
        UpTaskInfoVO info = new UpTaskInfoVO();
        info.setBatchNo(item.getBatchNo());
        info.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
        info.setErrorMsg(descMsg);
        info.setOptUserNo(CommonConstants.COMMON_USER_OPS_JOB);
        upTaskInfoByBatchNo(info);

        // 修改回调参数
        OpsSalesCommonParamVO bean = new OpsSalesCommonParamVO();
        DealReturnOpsParamVO dealReturnOpsParamVO =conventCallBackParam(false, data.getApplyNo(), descMsg);
        bean.setData(dealReturnOpsParamVO);

        LambdaUpdateWrapper<OpsSalesNoticeTaskDO> updateWrapperTask = new LambdaUpdateWrapper<>();
        updateWrapperTask
                .eq(OpsSalesNoticeTaskDO::getId, item.getId())
                .set(OpsSalesNoticeTaskDO::getCallBackParameter, JSONObject.toJSONString(bean))
                .set(OpsSalesNoticeTaskDO::getHandleStatus, OpsSalesTaskHandStatus.hand_success.getCode())
                .set(OpsSalesNoticeTaskDO::getReturnStatus, OpsSalesTaskReturnStatus.notReturn.getCode())
                .set(OpsSalesNoticeTaskDO::getHandleEndTime, new Date())
                .set(OpsSalesNoticeTaskDO::getUpdateTime, new Date());
        opsSalesNoticeTaskMapper.update(null, updateWrapperTask);
    }

    public boolean isInsertPurchaseOrderModify(String businessCode,String changeType) {
        if ("T".equals(businessCode) || "D".equals(businessCode) || "A3".equals(changeType)) {
            return true;
        }
        return false;
    }

    public boolean insertPurchaseOrderModify(OrderModifyVO data,String batchNo,String code) {
       if(data == null) {
           return false;
       }

        PurchaseModifyVO purchaseModifyVO = new PurchaseModifyVO();
        purchaseModifyVO.setModifyType(code);
        purchaseModifyVO.setStatus(0);
        purchaseModifyVO.setBatchNo(batchNo);
        purchaseModifyVO.setApplyNo(data.getApplyNo());
        purchaseModifyVO.setApplyContent(data.getChangeMessage());
        purchaseModifyVO.setApplyReason(data.getReason());
        purchaseModifyVO.setApplyPersonNo(data.getApplyPersonNo());
        purchaseModifyVO.setApplyDeptNo(data.getApplyDeptNo());
        purchaseModifyVO.setApplyTime(data.getApplyTime());
        purchaseModifyVO.setOrderFno(data.getOrderNo());
        List<String> list = new ArrayList<>();
        list.add(data.getOrderNo());
        CommonResult<List<PurchaseModifyApplyInfoDto>> requestInfo = purchaseBatchEditFeignApi.getRequestInfo(list);
        if(requestInfo == null || CollectionUtils.isEmpty(requestInfo.getData())) {
            String des = "查不到采购数据";
            if (requestInfo != null && requestInfo.getMessage() != null) {
                des = requestInfo.getMessage();
            }
            DealReturnOpsParamVO dealReturnOpsParamVO = conventCallBackParam(false, data.getApplyNo(), des);
            OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();
            vo.setData(dealReturnOpsParamVO);
            UpTaskInfoVO param = new UpTaskInfoVO();
            param.setBatchNo(batchNo);
            param.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
            param.setCallBackParameter(JSONUtil.toJsonStr(vo));
            upTaskInfoByBatchNo(param);
            return false;
        }
        PurchaseModifyApplyInfoDto purchaseInfo = requestInfo.getData().get(0);
        purchaseModifyVO.setModelno(purchaseInfo.getModelno());
        purchaseModifyVO.setQuantity(purchaseInfo.getQuantity());
        purchaseModifyVO.setDeptNo(purchaseInfo.getDeptno());
        purchaseModifyVO.setPurchaseStatecode(purchaseInfo.getStatecode());
        purchaseModifyVO.setSupplierId(purchaseInfo.getSupplierid());
        purchaseModifyVO.setNetweight(purchaseInfo.getNetweight());
        purchaseModifyVO.setTransType(purchaseInfo.getTranstype());
        purchaseModifyVO.setHopeExportDate(purchaseInfo.getHopeexportdate());
        purchaseModifyVO.setCustomerNo(purchaseInfo.getCustomerno());
        purchaseModifyVO.setUserNo(purchaseInfo.getUserno());
        purchaseModifyVO.setOrderNo(purchaseInfo.getOrderno());
        purchaseModifyVO.setItemNo(purchaseInfo.getItemno());
        purchaseModifyVO.setSplitItemNo(purchaseInfo.getSplititemno());
        purchaseModifyVO.setHandler(data.getAnswerPns());
        purchaseModifyVO.setHandleTime(data.getAnswerTime());
        purchaseModifyVO.setRemark(data.getRemark());
        purchaseModifyVO.setCreateTime(new Date());
        purchaseModifyVO.setCreateUser(CommonConstants.COMMON_USER_SALES);
        purchaseModifyVO.setUpdateTime(new Date());
        purchaseModifyVO.setSourceType("1"); // 0为ops导入，1为门户接入
        OpsPurchaseOrderDO purchaseOrderInfo = getPurchaseOrderInfo(data.getOrderNo());
        if (purchaseOrderInfo != null) {
            purchaseModifyVO.setSupplierOrderno(purchaseOrderInfo.getReplyOrderNo());
        }
        ResultVo<String> resultVo = purchaseModifyApplyFeignApi.insertPurchaseModify(purchaseModifyVO);
        return resultVo.isSuccess();
    }



    public Object callInterface(String methodName,String className, String params,String batchNo,String businessCode,String applyNo) {
        OpsSalesCommonParamVO object = JSONObject.parseObject(params, OpsSalesCommonParamVO.class);
        object.setBatchNo(batchNo);
        object.setBusinessCode(businessCode);
        object.setApplyNo(applyNo);
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
                    result =  method.invoke(o, object);
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            log.error(className+"->"+methodName+"调用失败"+e.getMessage(),e);
        }
        return result;
    }


    public boolean insertOrderModify(OrderModifyVO data,String batchNo,String code) {
        OrderModifyDO orderModifyDO = new OrderModifyDO();
        if (Objects.isNull(data)) {
            return false;
        }
        orderModifyDO.setApplyTime(data.getApplyTime());
        orderModifyDO.setBatchNo(batchNo);
        orderModifyDO.setStatus(Integer.parseInt(OrderModifyEnum.waitHand.getCode()));
        orderModifyDO.setModifyType(code);
        orderModifyDO.setApplyNo(data.getApplyNo());
        orderModifyDO.setOrderNo(data.getOrderNo());
        orderModifyDO.setApplyPersonNo(data.getApplyPersonNo());
        orderModifyDO.setApplyDeptNo(data.getApplyDeptNo());
        orderModifyDO.setReason(data.getReason());
        orderModifyDO.setChangeMessage(data.getChangeMessage());
        orderModifyDO.setChangeType(data.getChangeType());
        orderModifyDO.setCreateTime(new Date());
        orderModifyDO.setCreateUser(CommonConstants.COMMON_USER_SALES);

        SpecialVO specialVO = new SpecialVO();
        specialVO.setTransferSpecial(data.getTransfer());
        specialVO.setEndUserForTransferSpecial(data.getEndUserForTransferSpecial());
        orderModifyDO.setSpecial(JSONUtil.toJsonStr(specialVO));


        OpsPurchaseOrderDO purchaseOrderInfo = getPurchaseOrderInfo(data.getOrderNo());
        if (purchaseOrderInfo != null) {
            orderModifyDO.setSupplierOrderNo(purchaseOrderInfo.getReplyOrderNo());
        }

        if (StringUtils.isBlank(data.getCustomerNo()) || StringUtils.isBlank(data.getDeptNo())) {
            if (code.equals(OrderModifyTypeEnum.bgysfs.getCode()) || code.equals(OrderModifyTypeEnum.bgccz.getCode()))
            {
                CommonResult<List<ModifyPurchaseDto>> purchaseT = purchaseModifyFeignApi.getPurchaseT(Collections.singletonList(data.getOrderNo()));
                if (purchaseT.isSuccess() && purchaseT.getData() != null) {
                    ModifyPurchaseDto modifyPurchaseDto = purchaseT.getData().get(0);
                    String endUser = "";
                    if (StringUtils.isNotBlank(modifyPurchaseDto.getCustomerNo())) {
                        endUser = modifyPurchaseDto.getCustomerNo();
                    }
                    if (StringUtils.isNotBlank(modifyPurchaseDto.getUserNo())) {
                        endUser = modifyPurchaseDto.getUserNo();
                    }
                    CustomerVO customerByCustomerNo = opsCommonService.getCustomerByCustomerNo(endUser);
                    String deptNo = "";
                    if (StringUtils.isNotBlank(customerByCustomerNo.getHRUnitId())) {
                        deptNo = customerByCustomerNo.getHRUnitId();
                    }
                    if (StringUtils.isNotBlank(customerByCustomerNo.getHlCode())) {
                        deptNo = customerByCustomerNo.getHlCode();
                    }
                    orderModifyDO.setDeptNo(deptNo);
                    orderModifyDO.setCustomerNo(modifyPurchaseDto.getCustomerNo());
                }
            } else {
                OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(data.getOrderNo());
                if (!Objects.isNull(orderNoInfo)) {
                    ResultVo<RcvMasterVO> orderMaster = receiveOrderService.findOrderMaster(orderNoInfo.getOrderNo());
                    if (orderMaster.isSuccess() && orderMaster.getData() != null) {
                        orderModifyDO.setDeptNo(orderMaster.getData().getDeptNo());
                        orderModifyDO.setCustomerNo(orderMaster.getData().getCustomerNo());
                    }
                }
            }
        }
        int insert = orderModifyMapper.insert(orderModifyDO);
        return insert == 1;
    }

    @Override
    public ResultVo<String> testCallInterface(OpsSalesCommonParamVO parm) {
        log.info("成功进入接口testCallInterface:  参数:{}",parm.toString());
        String dataJson = JSONUtil.toJsonStr(parm.getData());
        OrderModifyVO data = JSONObject.parseObject(dataJson, OrderModifyVO.class);
        log.info("参数取data: {}",JSONUtil.toJsonStr(data));
        LambdaQueryWrapper<OpsSysLogDO> queryWrapper = new LambdaQueryWrapper<>();
        Integer count = opsSysLogMapper.selectCount(queryWrapper);
        log.info("条数:"+count);
        return ResultVo.success("testCallInterface响应成功.");
    }

    @Override
    public ResultVo<DealReturnOpsParamVO> releaseOrder(OpsSalesCommonParamVO info) {
        if (info == null) {
            return ResultVo.failure("入参不可为空.");
        }
        String dataJson = JSONUtil.toJsonStr(info.getData());
        ReleaseOrderParam releaseOrderParam = JSONObject.parseObject(dataJson, ReleaseOrderParam.class);
        ReleaseOrderParamVO releaseOrderParamVO = new ReleaseOrderParamVO();
        List<String> list = new ArrayList<>();
        if(releaseOrderParam == null || StringUtils.isBlank(releaseOrderParam.getRorderFno())) {
            return ResultVo.failure("订单号不可为空.");
        }
        list.add(new OrderNoInfo().releaseOldNo(releaseOrderParam.getRorderFno()));
        releaseOrderParamVO.setOrderNos(list);
        releaseOrderParamVO.setReason(releaseOrderParam.getDescription());
        // 获取token
        String token = commonOutSideInterfaceAuthService.getyingyeSysToken();
        String url = yingyeSysAuthUrl+"/api/credit/infoOPS/order/releaseAll";
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        try {
            HttpResponse response = HttpUtil.createPost(url)
                    .header("Authorization", token)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(JSONUtil.toJsonStr(releaseOrderParamVO))
                    .execute();
            log.info("信用拦截放行接口-> 响应: {}", response);
            dealReturnOpsParamVO = conventCallBackParam(true, releaseOrderParam.getApplyNoItemNo(), "放行成功");
            dealReturnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.ORDER_RELEASE.getCode());
            return ResultVo.success(dealReturnOpsParamVO);
        } catch (Exception e) {
            log.error("信用拦截放行接口-> 响应: {}", e.getMessage());
            dealReturnOpsParamVO = conventCallBackParam(false, releaseOrderParam.getApplyNoItemNo(), e.getMessage());
            dealReturnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.ORDER_RELEASE.getCode());
            return ResultVo.success(dealReturnOpsParamVO);
        }
    }

    //不拆分订单，订单删除提交后，自动删除失败待人工处理201时，如果是客户订单等待出库状态，或者采购单是供应商运输中状态，直接回复门户需二次审批
    public boolean needReplySecondApproval(RcvDetailVO rcvDetailVO) {
        if (!OrderSplitTypeEnum.noSplit.getCode().equals(rcvDetailVO.getProdFlag())) {
            return false;
        }
        if (RcvOrderStatusEnum.WAITCK.getType() == rcvDetailVO.getStatus()) {
            return true;
        }
        CommonResult<List<PurchaseInfoForCancel>> result = requestPurchaseFeignApi.getPurchase(rcvDetailVO.getRorderNo(), rcvDetailVO.getRorderItem());
        if (result.isSuccess()) {
            List<PurchaseInfoForCancel> purchaseInfoList = result.getData();
            for (PurchaseInfoForCancel info : purchaseInfoList) {
                if (PurchaseOrderStatusEnum.YUNSHUZHONG.equals(info.getStatus())) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public ResultVo<DealReturnOpsParamVO> commonDelOrder(OpsSalesCommonParamVO info) {

        if (info == null) {
            return ResultVo.failure("入参为空");
        }

        String dataJson = JSONUtil.toJsonStr(info.getData());
        SalesErpOrderDeleteTaskBean bean = JSONObject.parseObject(dataJson, SalesErpOrderDeleteTaskBean.class);
        // 转换modify实体 写入order_modify
        OrderModifyVO orderModifyVO = conventModifyData(bean, info.getBatchNo());

        if (StringUtils.isNotBlank(orderModifyVO.getReason()) && orderModifyVO.getReason().length() > 300) {
            DealReturnOpsParamVO dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(), orderModifyVO, bean.getOrderDeleteItem().getSecondApproval(), CancelOrderToSalesStatus.apply_fail.getCode(), "申请原因过长,请重新填写");
            return ResultVo.failure(dealReturnOpsParamVO,"400","申请失败，字段超长");
        }

        OrderModifyDO orderModifyDO = BeanCopyUtil.copy(orderModifyVO, OrderModifyDO.class);

        ResultVo<RcvDetailVO> orderDetail = receiveOrderService.findOrderDetail(orderModifyDO.getOrderNo());
        if(!orderDetail.isSuccess() || orderDetail.getData() == null) {
            DealReturnOpsParamVO dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(), orderModifyVO, bean.getOrderDeleteItem().getSecondApproval(), CancelOrderToSalesStatus.apply_fail.getCode(), "订单号不存在");
            return ResultVo.failure(dealReturnOpsParamVO,"400","订单号不存在");
        }
        //子项已发货或已分纳发货的
        RcvDetailVO rcvDetailVO = orderDetail.getData();
        if ( rcvDetailVO.getEPrice() != null) {
            BigDecimal eprice = rcvDetailVO.getEPrice().multiply(new BigDecimal(rcvDetailVO.getQuantity()));
            SpecialVO specialVO = JSONObject.parseObject(orderModifyDO.getSpecial(), SpecialVO.class);
            // eprice < 5000000
            if (eprice.compareTo(CommonConstants.fiveHundredW) < 0) {
                specialVO.setIsGt500w(false);
            } else {
                specialVO.setIsGt500w(true);
            }
            orderModifyDO.setSpecial(JSONUtil.toJsonStr(specialVO));
        }

        String msg = "";

        // 删单校验
        Boolean aBoolean = canDelete(rcvDetailVO.getStatus(), rcvDetailVO.getExpQty());
        if(!aBoolean) {
            msg = "订单状态为: " + RCVOrderStatusEnum.getName(rcvDetailVO.getStatus()) + ",不可删除";
            DealReturnOpsParamVO dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(), orderModifyVO, bean.getOrderDeleteItem().getSecondApproval(), CancelOrderToSalesStatus.del_fail.getCode(), msg);

            orderModifyDO.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode()));
            orderModifyDO.setAnswerText(OrderCancelCodeEnum.STATUS_NOT_DEL.getDesc());
            orderModifyDO.setAnswerPns(CommonConstants.COMMON_USER_SYS);
            orderModifyDO.setAnswerTime(new Date());
            orderModifyMapper.insert(orderModifyDO);
            return ResultVo.failure(dealReturnOpsParamVO,"400",msg);
        }

        // 订单状态-已删除
        if (rcvDetailVO.getStatus() == RcvOrderStatusEnum.CANCEL.getType()) {
            msg = "订单状态为:已删除";
            DealReturnOpsParamVO dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(), orderModifyVO, bean.getOrderDeleteItem().getSecondApproval(), CancelOrderToSalesStatus.apply_fail.getCode(), msg);

            orderModifyDO.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode()));
            orderModifyDO.setAnswerText(OrderCancelCodeEnum.ALREADY_DEL.getDesc());
            orderModifyDO.setAnswerPns(CommonConstants.COMMON_USER_SYS);
            orderModifyDO.setAnswerTime(new Date());
            orderModifyMapper.insert(orderModifyDO);
            return ResultVo.failure(dealReturnOpsParamVO,"400",msg);
        }

        // 调用删单接口
        ResultVo<String> approveResult = null;
        try {
            approveResult = cancelRcvOrder(rcvDetailVO.getRorderNo(), rcvDetailVO.getRorderItem().toString(),
                    "门户提交自动删除", orderModifyDO.getApplyPersonNo(), bean.getOrderDeleteItem().getSecondApproval(),bean.getOrderDeleteItem().getTransferSpecial(),bean.getOrderDeleteItem().getEndUserForTransferSpecial());
        } catch (Exception e) {
//            DealReturnOpsParamVO dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(), orderModifyVO, bean.getOrderDeleteItem().getSecondApproval(), CancelOrderToSalesStatus.apply_fail.getCode(), "删单异常"+e.getMessage());
//            return ResultVo.failure(dealReturnOpsParamVO,"400","删单异常"+e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        log.info("{} 删单接口返回结果: {}",orderModifyDO.getOrderNo(), JSONUtil.toJsonStr(approveResult));
        if (approveResult.isSuccess()) {
            msg = "BJ自动删除成功";
            DealReturnOpsParamVO dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(), orderModifyVO, bean.getOrderDeleteItem().getSecondApproval(), CancelOrderToSalesStatus.del_success.getCode(), msg);
            orderModifyDO.setStatus(Integer.parseInt(OrderModifyEnum.finish.getCode()));
            orderModifyDO.setAnswerText(approveResult.getData());
            orderModifyDO.setAnswerTime(new Date());
            orderModifyDO.setAnswerPns(CommonConstants.COMMON_USER_SYS);
            orderModifyMapper.insert(orderModifyDO);

            // 是二次审批 且删单接口返回成功时  写入salesinvoice_mid_info表
            if(bean.getOrderDeleteItem().getSecondApproval()) {
                addSalesMidInfoWithSecondDelOrder(bean,rcvDetailVO);
            }
            return ResultVo.success(dealReturnOpsParamVO);
        } else if ("201".equals(approveResult.getCode())) {  //北京返回需要手动删除的才处理
            //非二次审批 且 满足回复门户二次审批条件，则回复门户需二次审批
            if (needReplySecondApproval(rcvDetailVO)) {
                msg = "门户需要二次审批";
                DealReturnOpsParamVO dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(),
                        orderModifyVO, bean.getOrderDeleteItem().getSecondApproval(), CancelOrderToSalesStatus.second_process.getCode(), msg);
                orderModifyDO.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode())); // 不能变更
                orderModifyDO.setAnswerText(msg);
                orderModifyDO.setAnswerTime(new Date());
                orderModifyDO.setAnswerPns(CommonConstants.COMMON_USER_SYS);
                orderModifyMapper.insert(orderModifyDO);
                return ResultVo.success(dealReturnOpsParamVO);
            }
            msg = approveResult.getMessage() + " 请联系相关业务人员.";
            DealReturnOpsParamVO dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(), orderModifyVO, bean.getOrderDeleteItem().getSecondApproval(), CancelOrderToSalesStatus.apply_success.getCode(), msg);
            orderModifyDO.setStatus(Integer.parseInt(OrderModifyEnum.waitHand.getCode())); // 待处理
            if (bean.getOrderDeleteItem().getSecondApproval()) {
                orderModifyDO.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode())); // 不能变更
            }
            orderModifyDO.setAnswerText(approveResult.getMessage());
            orderModifyDO.setAnswerTime(new Date());
            orderModifyDO.setAnswerPns(CommonConstants.COMMON_USER_SYS);
            orderModifyMapper.insert(orderModifyDO);
            return ResultVo.success(dealReturnOpsParamVO);
        }  else if ("202".equals(approveResult.getCode()) || "203".equals(approveResult.getCode())) {
            msg = "不能变更BJ:" + approveResult.getMessage();
            DealReturnOpsParamVO dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(), orderModifyVO, bean.getOrderDeleteItem().getSecondApproval(), CancelOrderToSalesStatus.del_fail.getCode(), msg);
            orderModifyDO.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode())); // 不能变更
            orderModifyDO.setAnswerText(approveResult.getMessage());
            orderModifyDO.setAnswerTime(new Date());
            orderModifyDO.setAnswerPns(CommonConstants.COMMON_USER_SYS);
            orderModifyMapper.insert(orderModifyDO);
            return ResultVo.success(dealReturnOpsParamVO);
        } else {
            msg = approveResult.getMessage();
            DealReturnOpsParamVO dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(), orderModifyVO, bean.getOrderDeleteItem().getSecondApproval(), CancelOrderToSalesStatus.confirming.getCode(), msg);
            orderModifyDO.setStatus(Integer.parseInt(OrderModifyEnum.notHand.getCode())); // 暂不处理
            orderModifyDO.setAnswerText(approveResult.getMessage());
            orderModifyDO.setAnswerTime(new Date());
            orderModifyDO.setAnswerPns(CommonConstants.COMMON_USER_SYS);
            orderModifyMapper.insert(orderModifyDO);
            return ResultVo.success(dealReturnOpsParamVO);
        }
    }

    public void addSalesMidInfoWithSecondDelOrder(SalesErpOrderDeleteTaskBean bean,RcvDetailVO rcvDetailVO) {
        OrderDeleteItem orderDeleteItem = bean.getOrderDeleteItem();
        // bug 14253 二次审批删单无退货手续费不写入开票中间表
        if(orderDeleteItem.getProcessingFee() == null || orderDeleteItem.getProcessingFee().compareTo(BigDecimal.ZERO) == 0) {
            return;
        }

        ResultVo<RcvMasterVO> orderMaster = receiveOrderService.findOrderMaster(rcvDetailVO.getRorderNo());

        SalesInvoiceMidInfoDO salesinvoiceMidInfo = new SalesInvoiceMidInfoDO();

        salesinvoiceMidInfo.setOrderNo(orderDeleteItem.getOrderNo());
        salesinvoiceMidInfo.setCustomerNo(orderDeleteItem.getCustomerNo());
        salesinvoiceMidInfo.setUserNo(orderDeleteItem.getUserNo());
        salesinvoiceMidInfo.setDeptNo(orderDeleteItem.getDeptNo());
        salesinvoiceMidInfo.setModelNo(orderDeleteItem.getModelNo());
        salesinvoiceMidInfo.setQuantity(orderDeleteItem.getQuantity());
        salesinvoiceMidInfo.setPrice(rcvDetailVO.getPrice());
        salesinvoiceMidInfo.setFeeRate(orderDeleteItem.getProcessingFeeRate());
        salesinvoiceMidInfo.setFeeamount(orderDeleteItem.getProcessingFee());
        salesinvoiceMidInfo.setTradeCompanyId(orderMaster.getData().getTradeCompanyId());
        salesinvoiceMidInfo.setType("DF");
        salesinvoiceMidInfo.setRemark("二次审批删单");
        salesinvoiceMidInfo.setIsNew("0");
        salesinvoiceMidInfo.setApplyNo(bean.getApplyNo());
        String[] split = bean.getApplyNo().split("-");
        if (split.length > 1) {
            salesinvoiceMidInfo.setApplynoItem(Integer.parseInt(split[1]));
        }
        salesinvoiceMidInfo.setUpdateTime(new Date());
        salesinvoiceMidInfo.setEndUser(orderMaster.getData().getEndUser());
        salesInvoiceMidInfoMapper.insert(salesinvoiceMidInfo);

    }

    @Override
    public ResultVo<String> callBackInterfaceWithDelOrder(OpsSalesCommonParamVO info) {

        return  callBackInterface(info);
//        LambdaQueryWrapper<OpsSalesNoticeTaskDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(OpsSalesNoticeTaskDO::getBatchNo,info.getBatchNo());
//        OpsSalesNoticeTaskDO taskDO = opsSalesNoticeTaskMapper.selectOne(queryWrapper);
//
//        if (CommonConstants.COMMON_USER_OPS.equals(taskDO.getSource())) {
//            String jsonStr = JSONUtil.toJsonStr(info.getData());
//            log.info("ops删单回调参数打印=> {}",jsonStr);
//            List<OrderCancelResult> orderCancelResults = JSONArray.parseArray(jsonStr, OrderCancelResult.class);
//            ResultVo<Boolean> resultVo = smsOrderServiceFeignApi.sendOrderCancelReturnMessage(orderCancelResults);
//             log.info("删单[客户单] 回调给门户队列的接口参数 => {},  响应结果: {} ", jsonStr, JSONObject.toJSONString(resultVo));
//            return ResultVo.success("回调成功");
//        } else {
//           return  callBackInterface(info);
//        }
    }

    @Override
    public ResultVo<String> secondProcessWithUi(SecondProcessWithUiVO info) {
        if (info == null || StringUtils.isBlank(info.getId())) {
            return ResultVo.failure("请选择需要操作的数据");
        }
        if (StringUtils.isBlank(info.getOptUser())) {
            return ResultVo.failure("当前登录信息失效,请重新登录尝试.");
        }

        if (StringUtils.isBlank(info.getAnswerText())) {
            return ResultVo.failure("请输入需要二次审批的理由");
        }

        LambdaQueryWrapper<OrderModifyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderModifyDO::getId,info.getId());
        OrderModifyDO orderModifyDO = orderModifyMapper.selectOne(queryWrapper);
        if (orderModifyDO == null) {
            return ResultVo.failure("未找到此记录");
        }

        if(!OrderModifyTypeEnum.cancel_order.getCode().equals(orderModifyDO.getModifyType())) {
            return ResultVo.failure("请选择变更类型为删除订单");
        }

        if (OrderModifyEnum.bnbg.getCode().equals(String.valueOf(orderModifyDO.getStatus())) || OrderModifyEnum.finish.getCode().equals(String.valueOf(orderModifyDO.getStatus())) ) {
            return ResultVo.failure("该申请已处理结束，不可再次处理");
        }

        LambdaQueryWrapper<OpsSalesNoticeTaskDO> queryWrapperTask = new LambdaQueryWrapper<>();
        queryWrapperTask.eq(OpsSalesNoticeTaskDO::getBatchNo,orderModifyDO.getBatchNo());
        OpsSalesNoticeTaskDO taskDO = opsSalesNoticeTaskMapper.selectOne(queryWrapperTask);

        if (taskDO == null) {
            return ResultVo.failure("未找到task任务");
        }

        OpsSalesCommonParamVO opsSalesCommonParamVO = new OpsSalesCommonParamVO();

        DealReturnOpsParamVO dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(), BeanCopyUtil.copy(orderModifyDO, OrderModifyVO.class), false,
                CancelOrderToSalesStatus.second_process.getCode(), info.getAnswerText());

        opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
        opsSalesCommonParamVO.setBatchNo(taskDO.getBatchNo());

        // 修改task回调参数和回调处理状态
        LambdaUpdateWrapper<OpsSalesNoticeTaskDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OpsSalesNoticeTaskDO::getBatchNo,taskDO.getBatchNo())
                .set(OpsSalesNoticeTaskDO::getReturnStatus,OpsSalesTaskReturnStatus.notReturn.getCode())
                .set(OpsSalesNoticeTaskDO::getCallBackParameter,JSONUtil.toJsonStr(opsSalesCommonParamVO))
                .set(OpsSalesNoticeTaskDO::getUpdateTime,new Date());
        opsSalesNoticeTaskMapper.update(null,updateWrapper);

        // 修改order_modify的状态为不可变更
        LambdaUpdateWrapper<OrderModifyDO> updateOrderModify = new LambdaUpdateWrapper<>();
        updateOrderModify.eq(OrderModifyDO::getId,orderModifyDO.getId())
                .set(OrderModifyDO::getAnswerText,info.getAnswerText())
                .set(OrderModifyDO::getAnswerPns,info.getOptUser())
                .set(OrderModifyDO::getAnswerTime,new Date())
                .set(OrderModifyDO::getUpdateTime,new Date())
                .set(OrderModifyDO::getStatus,OrderModifyEnum.bnbg.getCode());
        orderModifyMapper.update(null, updateOrderModify);
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> insertOpsRemindWithDelay(OpsSalesCommonParamVO info) {
        if (Objects.isNull(info) || Objects.isNull(info.getData())) {
            return ResultVo.failure("入参为空.");
        }
        log.info("OPS推送中间表提醒报文 {} ", JSONUtil.toJsonStr(info));
        String jsonStr = JSONUtil.toJsonStr(info.getData());
        OpsDlvPromptingVO opsDlvPromptingVO = JSONObject.parseObject(jsonStr, OpsDlvPromptingVO.class);
        opsDlvPromptingVO.setSupplierCodeName(opsCommonService.getSupplierNameByCode(opsDlvPromptingVO.getSupplierId()));
        String objJsonStr = JSONUtil.toJsonStr(opsDlvPromptingVO);
        return insertRemind(info.getBatchNo(),info.getBusinessCode(),objJsonStr);
    }


    public ResultVo<String> insertRemind(String batchNo,String remindType,String remindContent) {
        OpsRemindDO opsRemindDO = new OpsRemindDO();
        opsRemindDO.setBatchNo(batchNo);
        opsRemindDO.setRemindType(remindType);

        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(CommonConstants.REMIND_CODE);
        if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
            return ResultVo.failure("获取提醒字典编码失败");
        }
        Map<String, DataCodeVO> map = dataCodes.getData().stream().collect(Collectors.toMap(DataCodeVO::getCode, Function.identity()));
        DataCodeVO dataCodeVO = map.get(remindType);
        opsRemindDO.setRemindTypeName(dataCodeVO.getCodeName());
        opsRemindDO.setRemindContent(remindContent);
        opsRemindDO.setCreateUser(CommonConstants.COMMON_USER_OPS);
        opsRemindDO.setCreateTime(new Date());
        opsRemindMapper.insert(opsRemindDO);
        return ResultVo.success("写入提醒中间表成功");
    }

    @Override
    public ResultVo<DealReturnOpsParamVO> commonbatchUpDlvDate(OpsSalesCommonParamVO info) {
        if (Objects.isNull(info) || Objects.isNull(info.getData())) {
            return ResultVo.failure("入参为空.");
        }
        log.info("共通接口批量变更货期报文 {} ", JSONUtil.toJsonStr(info));
        String jsonStr = JSONArray.toJSONString(info.getData());

        log.info("批量变更货期的参数JSON {}, ",jsonStr);

        List<UpOrderDlvDateInfo> orderDeliveryDates = JSONArray.parseArray(jsonStr, UpOrderDlvDateInfo.class);

        List<UpOrderDlvDateVo>  upOrderDlvDateVoList = new ArrayList<>();

        ResultVo<Map<String, UpdateOrderInfoResultVO>> mapResultVo = orderEditService.batchUpDlvDate(orderDeliveryDates);
        log.info("批量变更货期变更结果 {}",JSONUtil.toJsonPrettyStr(mapResultVo));

        if (mapResultVo == null || mapResultVo.getData() == null) {
            return ResultVo.failure("修改接口返回空");
        }

        Map<String, UpdateOrderInfoResultVO> data = mapResultVo.getData();

        for (String item : data.keySet() ) {
            UpOrderDlvDateVo vo = new UpOrderDlvDateVo();
            vo.setOrderNo(item);
            UpdateOrderInfoResultVO updateOrderInfoResultVO = data.get(item);
            if (updateOrderInfoResultVO.getSuccess()) {
                vo.setStatus("成功");
            } else {
                vo.setStatus("失败");
                vo.setStatusDescription(updateOrderInfoResultVO.getMessage());
            }
            upOrderDlvDateVoList.add(vo);
        }
        DealReturnOpsParamVO returnOpsParamVO = new DealReturnOpsParamVO();
        returnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.BATCH_UP_DLV.getCode());
        DealReturnOpsParam dealReturnOpsParam = new DealReturnOpsParam();
        dealReturnOpsParam.setUpOrderDlvDateVoList(upOrderDlvDateVoList);
        returnOpsParamVO.setDealReturnOpsParam(dealReturnOpsParam);
        return  ResultVo.success(returnOpsParamVO);
    }

    @Override
    public ResultVo<DealReturnOpsParamVO> inqAadd(OpsSalesCommonParamVO info) {
        // 调用新增接口
        if(info == null || info.getData() == null) {
            return ResultVo.failure("入参不可为空");
        }
        // 设置回调门户报文主体
        String jsonStr = JSONUtil.toJsonStr(info.getData());
        InquiryApplyAddParam inquiryApplyAddParam = JSONObject.parseObject(jsonStr, InquiryApplyAddParam.class);
        inquiryApplyAddParam.setBatchNo(info.getBatchNo());
        // 调用采购催促新增接口
        ResultVo<InquiryApplyVerifyReurn> reurnResultVo = inquiryAdapterFeignApi.addInquiry(inquiryApplyAddParam);
        log.info("调用采购催促新增接口返回结果 {}",JSONUtil.toJsonStr(reurnResultVo));
        if (reurnResultVo.isSuccess()) {
            DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
            dealReturnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.INQA_BACK.getCode());

            DealReturnOpsParam dealReturnOpsParam = new DealReturnOpsParam();
            dealReturnOpsParam.setCommonReturnParam(reurnResultVo.getData());
            dealReturnOpsParamVO.setDealReturnOpsParam(dealReturnOpsParam);
            return ResultVo.success(dealReturnOpsParamVO);
        }
        throw new BusinessException("接口无返回,调用采购催促新增接口异常");
    }

    /**
     *  20260413 C14717 对接门户-返回值新增，报文增加类型 和 订单返回信息
     *  客单催促和采购催促新增接口
     * @param info
     * @return
     */
    @Override
    public ResultVo<DealReturnOpsParamVO> inqAaddNew(OpsSalesCommonParamVO info) {
        // 调用新增接口
        if(info == null || info.getData() == null) {
            return ResultVo.failure("入参不可为空");
        }
        // 设置回调门户报文主体
        String jsonStr = JSONUtil.toJsonStr(info.getData());
        InquiryApplyAddParam inquiryApplyAddParam = JSONObject.parseObject(jsonStr, InquiryApplyAddParam.class);
        inquiryApplyAddParam.setBatchNo(info.getBatchNo());
        // 调用采购催促新增接口 c14717 20260113 替换为销售订单催促新增接口
        ResultVo<InquirySalesApplyReurn> reurnResultVo = inquiryAdapterFeignApi.addInquiryApply(inquiryApplyAddParam);
        log.info("调用采购催促新增接口返回结果 {}",JSONUtil.toJsonStr(reurnResultVo));
        if (reurnResultVo.isSuccess()) {
            DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
            dealReturnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.INQA_BACK.getCode());

            DealReturnOpsParam dealReturnOpsParam = new DealReturnOpsParam();
            dealReturnOpsParam.setCommonReturnParam(reurnResultVo.getData());
            dealReturnOpsParamVO.setDealReturnOpsParam(dealReturnOpsParam);
            return ResultVo.success(dealReturnOpsParamVO);
        }
        throw new BusinessException("接口无返回,调用采购催促新增接口异常");
    }

    @Override
    public ResultVo<DealReturnOpsParamVO> inqBadd(OpsSalesCommonParamVO info) {
        // 调用新增接口
        if(info == null || info.getData() == null) {
            return ResultVo.failure("入参不可为空");
        }
        // 设置回调门户报文主体
        String jsonStr = JSONUtil.toJsonStr(info.getData());
        InqbSalesApplyAddParam salesApplyAddParam = JSONObject.parseObject(jsonStr, InqbSalesApplyAddParam.class);
        salesApplyAddParam.setBatchNo(info.getBatchNo());
        // 调用采购催促新增接口
        ResultVo<InqbSalesApplyAddReturn> reurnResultVo = inqbAdapterFeignApi.addInqb(salesApplyAddParam);
        log.info("调用采购催促新增接口返回结果 {}",JSONUtil.toJsonStr(reurnResultVo));
        if (reurnResultVo.isSuccess()) {
            DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
            dealReturnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.INQB_BACK.getCode());

            DealReturnOpsParam dealReturnOpsParam = new DealReturnOpsParam();
            dealReturnOpsParam.setCommonReturnParam(reurnResultVo.getData());
            dealReturnOpsParamVO.setDealReturnOpsParam(dealReturnOpsParam);
            return ResultVo.success(dealReturnOpsParamVO);
        }
        throw new BusinessException("接口无返回,inqB问询新增接口异常");
    }

    @Override
    public ResultVo<String> autoHandNotCancelData() {
        // 查询删单待处理的数据
        LambdaQueryWrapper<OrderModifyDO> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrderModifyDO::getStatus,OrderModifyEnum.waitHand.getCode())
                .eq(OrderModifyDO::getModifyType,OrderModifyTypeEnum.cancel_order.getCode());
        List<OrderModifyDO> orderModifyDOS = orderModifyMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(orderModifyDOS)) {
            return ResultVo.success("暂无待处理的删单数据");
        }
        ResultVo<RcvDetailVO> orderDetail;
        OrderModifyVO orderModifyVO;
        UpTaskInfoVO upTaskInfoVO;
        SpecialVO specialVO;
        DealReturnOpsParamVO dealReturnOpsParamVO;
        // 判断允许自动删单   upTaskInfoByBatchNo
        for (OrderModifyDO item: orderModifyDOS) {
            boolean isUpFlag = true;
            String msg = "删单申请自动否决";
            try {
                orderModifyVO = BeanCopyUtil.copy(item, OrderModifyVO.class);
                String special = orderModifyVO.getSpecial();
                specialVO = JSONObject.parseObject(special, SpecialVO.class);
                orderDetail = receiveOrderService.findOrderDetail(item.getOrderNo());
                if(!orderDetail.isSuccess() || orderDetail.getData() == null) {
                    msg = "订单号接单表不存在";
                    dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(),
                            orderModifyVO, specialVO.getSecondApproval(), CancelOrderToSalesStatus.apply_fail.getCode(), msg);
                    OpsSalesCommonParamVO bean = new OpsSalesCommonParamVO();
                    bean.setData(dealReturnOpsParamVO);
                    String jsonStr = JSONUtil.toJsonStr(bean);
                    upTaskInfoVO = new UpTaskInfoVO();
                    upTaskInfoVO.setCallBackParameter(jsonStr);
                    upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
                    upTaskInfoVO.setOptUserNo("system");
                    upTaskInfoVO.setBatchNo(item.getBatchNo());
                    upTaskInfoByBatchNo(upTaskInfoVO);
                    continue;
                }
                RcvDetailVO rcvDetailVO = orderDetail.getData();
                log.info("自动处理删单,接单状态信息{} ", JSONUtil.toJsonStr(rcvDetailVO));
                if(rcvDetailVO.getStatus()== RcvOrderStatusEnum.INVOICE.getType()) {
                    msg = "订单状态为已开票,不可删除";
                    isUpFlag = false;
                } else if (rcvDetailVO.getStatus()== RcvOrderStatusEnum.CKED.getType()) {
                    msg = "订单状态为已发货,不可删除";
                    isUpFlag = false;
                } else if (rcvDetailVO.getStatus()== RcvOrderStatusEnum.CANCEL.getType()) {
                    msg = "订单状态为已删除,自动否决";
                    isUpFlag = false;
                }

                if (!isUpFlag) {
                    if (StringUtils.isNotBlank(item.getBatchNo())) {
                        dealReturnOpsParamVO = conventDelOrderCallBackParam(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(),
                                orderModifyVO, specialVO.getSecondApproval(), CancelOrderToSalesStatus.del_fail.getCode(), msg);
                        OpsSalesCommonParamVO bean = new OpsSalesCommonParamVO();
                        bean.setData(dealReturnOpsParamVO);
                        String jsonStr = JSONUtil.toJsonStr(bean);
                        upTaskInfoVO = new UpTaskInfoVO();
                        upTaskInfoVO.setCallBackParameter(jsonStr);
                        upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
                        upTaskInfoVO.setOptUserNo(CommonConstants.COMMON_USER_SYS);
                        upTaskInfoVO.setBatchNo(item.getBatchNo());
                        upTaskInfoByBatchNo(upTaskInfoVO);
                    } else {
                        List<OrderCancelResult> cancelResultList = new ArrayList<>(1);
                        OrderCancelResult cancelResult = new OrderCancelResult();
                        cancelResult.setOrderNo(item.getOrderNo());
                        cancelResult.setResult(CancelOrderToSalesStatus.del_fail.getCode());
                        cancelResult.setMessage(msg);
                        cancelResult.setHandlePsnNo(CommonConstants.COMMON_USER_SYS);
                        cancelResult.setHandlePsnName(CommonConstants.COMMON_USER_SYS);
                        cancelResult.setHandleRemark(msg);
                        cancelResultList.add(cancelResult);
                        ResultVo<Boolean> resultVo = smsOrderServiceFeignApi.sendOrderCancelReturnMessage(cancelResultList);
                        log.info("自动否决不能删掉的申请 回调给门户队列的接口参数 => {},  响应结果: {} ", JSONObject.toJSONString(cancelResult), JSONObject.toJSONString(resultVo));
                    }

                    // 修改order_modify的状态为不能变更
                    LambdaUpdateWrapper<OrderModifyDO> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(OrderModifyDO::getId,item.getId())
                            .set(OrderModifyDO::getStatus,OrderModifyEnum.bnbg.getCode())
                            .set(OrderModifyDO::getAnswerText,msg)
                            .set(OrderModifyDO::getAnswerTime,new Date())
                            .set(OrderModifyDO::getAnswerPns,CommonConstants.COMMON_USER_SYS);
                    orderModifyMapper.update(null, updateWrapper);
                }
            } catch (Exception e) {
                log.error("自动否决不能删掉的申请发生异常: {}",e.getMessage(),e);
            }
        }
        return ResultVo.success("自动处理成功");
    }

    /**
     * 待审批、审批通过：唯一标识+销售门户填写的字段+状态
     * 作废、驳回：唯一标识+状态
     * @param info
     * @return
     */
    @Override
    public ResultVo<String> preAccountOrderHandApply(OpsSalesCommonParamVO info) {

        if (info == null) {
            throw new BusinessException("入参不可为空");
        }

        log.info("preAccountOrderHandApply 入参: {}", JSONUtil.toJsonStr(info));
        String jsonStr = JSONUtil.toJsonStr(info.getData());
        PreOrderAccountDetailVO preOrderAccountDetailVO = JSONObject.parseObject(jsonStr, PreOrderAccountDetailVO.class);
        preOrderAccountDetailVO.setAccountApplyNo(info.getApplyNo());

        if (preOrderAccountDetailVO.getSureAccountQty() == null) {
            preOrderAccountDetailVO.setSureAccountQty(0);
        }
        if (preOrderAccountDetailVO.getDelayQty() == null) {
            preOrderAccountDetailVO.setDelayQty(0);
        }

        if(PreAccountOrderSalesCallBackStatusEnum.dsp.getCode() == preOrderAccountDetailVO.getHandStatus()) {
            preOrderAccountDetailVO.setStatus(PreAccountStatusEnum.dsp.getCode());
            preOrderAccountDetailVO.setApproveQty(preOrderAccountDetailVO.getSureAccountQty()+preOrderAccountDetailVO.getDelayQty());
        } else if (PreAccountOrderSalesCallBackStatusEnum.sptg.getCode() == preOrderAccountDetailVO.getHandStatus()) {
            preOrderAccountDetailVO.setStatus(PreAccountStatusEnum.dqs.getCode());
            preOrderAccountDetailVO.setApproveQty(0);
        } else if (PreAccountOrderSalesCallBackStatusEnum.zf.getCode() == preOrderAccountDetailVO.getHandStatus() ||
                PreAccountOrderSalesCallBackStatusEnum.bh.getCode() == preOrderAccountDetailVO.getHandStatus()) {
            preOrderAccountDetailVO.setStatus(PreAccountStatusEnum.djs.getCode());
            preOrderAccountDetailVO.setSureAccountQty(0);
            preOrderAccountDetailVO.setDelayQty(0);
            preOrderAccountDetailVO.setDelayDesc(null);
            preOrderAccountDetailVO.setDelayDate(null);
            preOrderAccountDetailVO.setFactDelayQty(0);
            preOrderAccountDetailVO.setApproveQty(0);
        } else {
            preOrderAccountDetailVO.setStatus(null);
        }

        if (preOrderAccountDetailVO.getInventoryId() == null || com.alibaba.cloud.commons.lang.StringUtils.isBlank(info.getBatchNo()) || preOrderAccountDetailVO.getInventoryIdItem() == null) {
            return ResultVo.failure("唯一标识不完整.");
        }

        int i = commonMapper.checkPreAccountDetail(preOrderAccountDetailVO.getInventoryId(), preOrderAccountDetailVO.getInventoryIdItem(),
                preOrderAccountDetailVO.getBatchNo());
        if (i > 0) {
            log.error("preAccountOrderHandApply 门户推送数据重复: {}", JSONUtil.toJsonStr(info));
            return ResultVo.failure("门户推送数据重复.");
        }
        // 更子表
        preStockFeignApi.updatePreAccountDetail(preOrderAccountDetailVO);
        // 更主表
        preStockFeignApi.updatePreOrderAccountByInventoryId(preOrderAccountDetailVO.getInventoryId());

        if(preOrderAccountDetailVO.getStatus() == PreAccountStatusEnum.dqs.getCode()) {
            // 记录申请记录
            preStockFeignApi.insertPreOrderAccountApplyDetailData(preOrderAccountDetailVO);
        }
        return ResultVo.success("处理成功");
    }

    @Override
    public OpsPurchaseOrderDO getPurchaseOrderInfo(String purchaseNo) {
        if (StringUtils.isBlank(purchaseNo)) {
            return null;
        }
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(purchaseNo);
        LambdaQueryWrapper<OpsPurchaseOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsPurchaseOrderDO::getOrderNo, orderNoInfo.getOrderNo())
                .eq(OpsPurchaseOrderDO::getItemNo, orderNoInfo.getItemNo())
                .eq(orderNoInfo.getSplitItem() != null,OpsPurchaseOrderDO::getSplitItemNo, orderNoInfo.getSplitItem());

        List<OpsPurchaseOrderDO> opsPurchaseOrderDOS = opsPurchaseOrderMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(opsPurchaseOrderDOS)) {
            return null;
        }
        return opsPurchaseOrderDOS.get(0);
    }

    @Override
    public ResultVo<String> updateShikomiApprovalInfo(OpsSalesCommonParamVO info) {

        if(info == null || info.getData() == null) {
            return ResultVo.failure("入参不可为空");
        }
        String jsonStr = JSONUtil.toJsonStr(info.getData());
        ShikomiBudgetVO shikomiBudgetVO = JSONObject.parseObject(jsonStr, ShikomiBudgetVO.class);
        if(StringUtils.isBlank(shikomiBudgetVO.getBatchNo()) || StringUtils.isBlank(shikomiBudgetVO.getShikomiNo())) {
            return ResultVo.failure("决算批次号/shikomiNo为空.");
        }
        productServiceFeignApi.upShikomiBudjet(shikomiBudgetVO);

        // 点检完成
        if (3 == shikomiBudgetVO.getInspectStatus()) {
            if (shikomiBudgetVO.getLastUseDate() != null) {
                // 更新total表的最晚使用月份
                commonMapper.updateShikomiTotal(shikomiBudgetVO.getShikomiNo(),DateUtil.dateToDateString(shikomiBudgetVO.getLastUseDate()));
            }
        }

        return ResultVo.success("处理成功");
    }

    @Override
    public ResultVo<DealReturnOpsParamVO> createNotifyShipmentPlan(OpsSalesCommonParamVO info) {
        if (info == null) {
            return ResultVo.failure("入参不可为空");
        }
        String dataJson = JSONUtil.toJsonStr(info.getData());
        NoticeShipmentPlanTaskVO noticeShipmentPlanTaskVO = JSONObject.parseObject(dataJson, NoticeShipmentPlanTaskVO.class);
        ResultVo<NotifyShipmentPlanResult> notifyShipmentPlanResultResultVo = notifyShipmentPlanSMSFeignAPi.saveList(noticeShipmentPlanTaskVO.getDataList(), noticeShipmentPlanTaskVO.getUserName());
        // 最外层实体
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.NOTIFY_SHIP_PLAN.getCode());
        OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();
        if (notifyShipmentPlanResultResultVo.getData() != null) {
            NotifyShipmentPlanResult data = notifyShipmentPlanResultResultVo.getData();
            if (CollectionUtils.isNotEmpty(data.getErrList())) {
                List<NotifyShipmentPlanDto> list = new ArrayList<>();
                for (NotifyShipmentPlanImportErrorDto item : data.getErrList()) {
                    NotifyShipmentPlanDto dto = new NotifyShipmentPlanDto();
                    dto.setOrderFno(item.getOrderFno());
                    dto.setModelNo(item.getModelNo());
                    dto.setRemark(item.getRemark());
                    dto.setApplyNo(info.getApplyNo());
                    list.add(dto);
                }
                // 最外层实体
                vo.setData(list);
            } else {
                vo.setData(new ArrayList<>());
            }
        }
        DealReturnOpsParam param = new DealReturnOpsParam();
        param.setOpsSalesCommonParamVo(vo);
        dealReturnOpsParamVO.setDealReturnOpsParam(param);
        return ResultVo.success(dealReturnOpsParamVO);
    }


    private ResultVo<String> cancelRcvOrder(String orderNo, String itemNo, String reason, String optUser,Boolean secondProcess,Boolean isAdjust,String customerNo) {
        try {
            CancelForOrderDto dto = new CancelForOrderDto();
            dto.setOrderId(orderNo);
            dto.setOrderItem(itemNo);
            UserDto userDTO = new UserDto();
            userDTO.setUserName(optUser);
            dto.setUserDto(userDTO);
            dto.setReason(reason);
            dto.setSecondProcess(secondProcess);
            dto.setAdjust(isAdjust);
            dto.setEndUser(customerNo);
            CommonResult<String> result = opsOrderFeignApi.autoCancelRcvOrder(dto);
            log.info("opsOrderFengnApi.autoCancelRcvOrder result = {}, params = {}", JSON.toJSONString(result), JSON.toJSONString(dto));
            if (result.isSuccess()) {
                return ResultVo.success(result.getData(), result.getMessage());
            } else {
                return ResultVo.failure(String.valueOf(result.getCode()), result.getMessage());
            }
        } catch (Exception e) {
            log.error("BJ取消订单接口失败：" + e.getMessage());
            throw new BusinessException("BJ取消订单接口失败: " + e.getMessage(), e);
        }
    }


    public DealReturnOpsParamVO conventDelOrderCallBackParam(int applyType, OrderModifyVO orderModifyVO, Boolean secondProcess,String status,String remark) {
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(applyType);

        DealReturnOpsParam param = new DealReturnOpsParam();

        SalesErpOrderDeleteResultVO vo = new SalesErpOrderDeleteResultVO();
        vo.setApplyItemNo(orderModifyVO.getApplyNo());
        vo.setOrderNo(orderModifyVO.getOrderNo());
        vo.setSecondProcess(secondProcess);
        vo.setStatus(status);
        vo.setStatusDescription(remark);

        param.setSalesErpOrderDeleteResultVo(vo);

        dealReturnOpsParamVO.setDealReturnOpsParam(param);

        return dealReturnOpsParamVO;
    }

    public OrderModifyVO conventModifyData(SalesErpOrderDeleteTaskBean bean,String batchNo) {

        OrderDeleteItem orderDeleteItem = bean.getOrderDeleteItem();

        OrderModifyVO orderModifyVO = new OrderModifyVO();
        orderModifyVO.setBatchNo(batchNo);
        orderModifyVO.setApplyNo(bean.getApplyNo());
        orderModifyVO.setModifyType(OrderModifyTypeEnum.cancel_order.getCode());
        orderModifyVO.setStatus(Integer.valueOf(OrderModifyEnum.waitHand.getCode()));
        orderModifyVO.setOrderNo(orderDeleteItem.getOrderNo());
        orderModifyVO.setCustomerNo(orderDeleteItem.getCustomerNo());
        orderModifyVO.setChangeType(OrderModifyTypeEnum.cancel_order.getCode());
        // orderModifyVO.setReason(bean.getApplyReason());
        orderModifyVO.setApplyPersonNo(bean.getApplyPersonNo());
        orderModifyVO.setApplyTime(bean.getApplyTime());
        orderModifyVO.setApplyDeptNo(bean.getApplyDeptNo());
        orderModifyVO.setCreateTime(new Date());
        orderModifyVO.setUpdateTime(new Date());
        orderModifyVO.setCreateUser(CommonConstants.COMMON_USER_SALES);
        orderModifyVO.setReason(orderDeleteItem.getRemark());
        orderModifyVO.setResponsibleParty(orderDeleteItem.getResponsibleParty());
        orderModifyVO.setCancelReason(orderDeleteItem.getCancelReason());
        orderModifyVO.setProcessingFeeRate(orderDeleteItem.getProcessingFeeRate());

        if(StringUtils.isNotBlank(orderDeleteItem.getDeptNo())) {
            ResultVo<String> deptNoByHRSalesDeptNo = opsCommonService.getDeptNoByHRSalesDeptNo(orderDeleteItem.getDeptNo());
            if (deptNoByHRSalesDeptNo.isSuccess()) {
                orderModifyVO.setDeptNo(deptNoByHRSalesDeptNo.getData());
            }
        }

        OpsPurchaseOrderDO purchaseOrderInfo = getPurchaseOrderInfo(orderDeleteItem.getOrderNo());
        if (purchaseOrderInfo != null) {
            orderModifyVO.setSupplierOrderNo(purchaseOrderInfo.getReplyOrderNo());
        }

        SpecialVO specialVO = new SpecialVO();
        specialVO.setSecondApproval(orderDeleteItem.getSecondApproval());
        specialVO.setTransferSpecial(orderDeleteItem.getTransferSpecial());
        specialVO.setProcessingFee(orderDeleteItem.getProcessingFee());
        specialVO.setEndUserForTransferSpecial(orderDeleteItem.getEndUserForTransferSpecial());
        orderModifyVO.setSpecial(JSONUtil.toJsonStr(specialVO));
        orderModifyVO.setTransfer(orderDeleteItem.getTransferSpecial());
        orderModifyVO.setCancelStrategy(orderDeleteItem.getCancelStrategy());
        return orderModifyVO;
    }



    /**
     * 1.执行业务接口
     * 2.回改任务表信息 回调参数 执行状态 操作人
     * 3.回改订单修改表信息 状态 操作人
     */
    @Override
    public ResultVo<OpsSalesCommonParamVO> orderChangeToInitStatus(OrderModifyOrderChangeVO info) {
        if(Objects.isNull(info) || Objects.isNull(info.getBatchNo())) {
            return ResultVo.failure("批次号为空.");
        }
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        // 执行业务接口
        // CommonResult<String> stringCommonResult = opsWmFeignApi.orderChangeToInitStatus(info);
        // 回改任务表信息
        UpTaskInfoVO upTaskInfoVO = new UpTaskInfoVO();
        OpsSalesCommonParamVO opsSalesCommonParamVO = new OpsSalesCommonParamVO();
        // 回调参数
        upTaskInfoVO.setBatchNo(info.getBatchNo());
        upTaskInfoVO.setOptUserNo(info.getOptUserNo());
        OrderModifyVO orderModifyVO = new OrderModifyVO();
        orderModifyVO.setBatchNo(info.getBatchNo());
        orderModifyVO.setUpdateUser(info.getOptUserNo());
        orderModifyVO.setAnswerPns(info.getOptUserNo());
        orderModifyVO.setAnswerTime(new Date());
        if (Objects.isNull(info.getHandStatus())) {
            dealReturnOpsParamVO = conventCallBackParam(false, info.getApplyNo(), "订单还原接口无返回结果");
            orderModifyVO.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode()));
            orderModifyVO.setAnswerText("订单还原接口无返回结果");;
            upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
            upTaskInfoVO.setReturnResult("订单还原接口无返回结果");
        } else {
            // upTaskInfoVO.setReturnResult(JSONUtil.toJsonStr(stringCommonResult));
            if ("1".equals(info.getHandStatus())) {
                orderModifyVO.setStatus(Integer.parseInt(OrderModifyEnum.finish.getCode()));
                orderModifyVO.setAnswerText(info.getHandDesc());
                dealReturnOpsParamVO = conventCallBackParam(true, info.getApplyNo(), info.getHandDesc());
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_success.getCode());
            } else {
                orderModifyVO.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode()));
                orderModifyVO.setAnswerText(info.getHandDesc());
                dealReturnOpsParamVO = conventCallBackParam(false, info.getApplyNo(), info.getHandDesc());
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
            }
        }
        opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
        upTaskInfoVO.setErrorMsg("");
        upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(opsSalesCommonParamVO));
        upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
        upTaskInfoByBatchNo(upTaskInfoVO);
        orderModifyService.updateOrderModifyInfo(orderModifyVO);
        return ResultVo.success(opsSalesCommonParamVO);
    }

    @Override
    public ResultVo<DealReturnOpsParamVO> autoCommonOrderChangeToInitStatus(OpsSalesCommonParamVO info) {
        if (info == null) {
            return ResultVo.failure("入参为空");
        }
        String dataJson = JSONUtil.toJsonStr(info.getData());
        OrderModifyVO orderModifyVO = JSONObject.parseObject(dataJson, OrderModifyVO.class);
        AutoOrderChangeToInitStatusDto bean = new AutoOrderChangeToInitStatusDto();
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderModifyVO.getOrderNo());
        bean.setOrderId(orderNoInfo.getOrderNo());
        bean.setOrderItem(String.valueOf(orderNoInfo.getItemNo()));
        bean.setTransfer(orderModifyVO.getTransfer());
        bean.setEndUser(orderModifyVO.getEndUser());
        bean.setUserName(orderModifyVO.getApplyPersonNo());
        ResultVo<String> resultVo = null;
        try {
            resultVo = opsWmFeignApi.autoOrderChangeToInitStatus(bean);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();

        if (resultVo.isSuccess()) {
            // 修改order_modify为完成
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.finish.getCode(),"自动还原成功");
            dealReturnOpsParamVO = conventCallBackParam(true, info.getApplyNo(), "自动还原成功");
            return ResultVo.success(dealReturnOpsParamVO);
        } else if ("500".equals(resultVo.getCode())) { // 还原失败  ==> order_modify 不能变更
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.bnbg.getCode(),resultVo.getMessage());
            dealReturnOpsParamVO = conventCallBackParam(false, info.getApplyNo(), resultVo.getMessage());

        } else if ("501".equals(resultVo.getCode())) { // 需要人工处理 ==> order_modify 待处理
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.waitHand.getCode(),resultVo.getMessage());
            dealReturnOpsParamVO = conventCallBackParam(false, info.getApplyNo(), "需要人工处理:"+resultVo.getMessage());
        }
        return ResultVo.failure(dealReturnOpsParamVO,"500",resultVo.getMessage());

    }

    public void updateOrderModifyInfo(String batchNo,String code,String answerText) {
        // 修改order_modify为完成
        OrderModifyVO orderModifyVO = new OrderModifyVO();
        orderModifyVO.setBatchNo(batchNo);
        orderModifyVO.setStatus(Integer.valueOf(code));
        orderModifyVO.setUpdateUser(CommonConstants.COMMON_USER_OPS_JOB);
        orderModifyVO.setAnswerText(answerText);
        orderModifyVO.setAnswerPns(CommonConstants.COMMON_USER_OPS_JOB);
        orderModifyVO.setAnswerTime(new Date());
        orderModifyService.updateOrderModifyInfo(orderModifyVO);
    }

    @Override
    public ResultVo<OpsSalesCommonParamVO> zdOrderModifyHand(ZdWithOrderModifyVO info) {
        if(Objects.isNull(info) || Objects.isNull(info.getBatchNo())) {
            return ResultVo.failure("批次号为空.");
        }
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        // 执行业务接口
        CommonResult<String> stringCommonResult = opsOrderFeignApi.handleZD(info);
        // 回改任务表信息
        UpTaskInfoVO upTaskInfoVO = new UpTaskInfoVO();
        OpsSalesCommonParamVO opsSalesCommonParamVO = new OpsSalesCommonParamVO();
        // 回调参数
        upTaskInfoVO.setBatchNo(info.getBatchNo());
        upTaskInfoVO.setOptUserNo(info.getOptUserNo());
        OrderModifyVO orderModifyVO = new OrderModifyVO();
        orderModifyVO.setBatchNo(info.getBatchNo());
        orderModifyVO.setUpdateUser(info.getOptUserNo());
        orderModifyVO.setAnswerPns(info.getOptUserNo());
        orderModifyVO.setAnswerTime(new Date());
        if (Objects.isNull(stringCommonResult)) {
            dealReturnOpsParamVO = conventCallBackParam(false, info.getApplyNo(), "订单转订接口无返回结果");
            orderModifyVO.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode()));
            orderModifyVO.setAnswerText("订单转订接口无返回结果");
            upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
            upTaskInfoVO.setReturnResult("订单转订接口无返回结果");
        } else {
            upTaskInfoVO.setReturnResult(JSONUtil.toJsonStr(stringCommonResult));
            if (stringCommonResult.isSuccess()) {
                orderModifyVO.setStatus(Integer.parseInt(OrderModifyEnum.finish.getCode()));
                orderModifyVO.setAnswerText(stringCommonResult.getMessage());
                dealReturnOpsParamVO = conventCallBackParam(true, info.getApplyNo(), stringCommonResult.getData());
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_success.getCode());
            } else {
                orderModifyVO.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode()));
                orderModifyVO.setAnswerText(stringCommonResult.getMessage());
                dealReturnOpsParamVO = conventCallBackParam(false, info.getApplyNo(), stringCommonResult.getMessage());
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
            }
        }
        opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
        upTaskInfoVO.setErrorMsg("");
        upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(opsSalesCommonParamVO));
        upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
        upTaskInfoByBatchNo(upTaskInfoVO);
        orderModifyService.updateOrderModifyInfo(orderModifyVO);
        return ResultVo.success(opsSalesCommonParamVO);
    }


    public DealReturnOpsParamVO conventCallBackParam(Boolean isSuccess,String applyNo,String desc) {
        if (Objects.isNull(isSuccess)) {
            return null;
        }
        // 最外层实体
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.ORDER_MODIFY.getCode());

        OpsOrderResultBean bean = new OpsOrderResultBean();
        bean.setApplyNo(applyNo);
        bean.setResultDescription(desc);
        if (isSuccess) {
            bean.setResult("成功");
        } else {
            bean.setResult("失败");
        }
        OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();
        vo.setData(bean);
        DealReturnOpsParam param = new DealReturnOpsParam();
        param.setOpsSalesCommonParamVo(vo);
        dealReturnOpsParamVO.setDealReturnOpsParam(param);
        return dealReturnOpsParamVO;
    }

    /**
     * 11729bug
     * @param info
     * @return
     */
    @Override
    public ResultVo<List<OrderModifyUpInfoVO>> findOrderModifyUpInfo(OrderModifyUpInfoRequest info) {

        log.info("查询订单修改是否可变更的信息参数 {}",JSONUtil.toJsonStr(info));

        if (Objects.isNull(info)) {
            return ResultVo.failure("入参不可为空");
        }
        if (StringUtils.isBlank(info.getModifyType())) {
            return ResultVo.failure("变更类型不可为空");
        }

        if (CollectionUtils.isEmpty(info.getOrderNoList())) {
            return ResultVo.failure("订单号不可为空");
        }

        List<String> filteredList = info.getOrderNoList().stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(filteredList)) {
            return ResultVo.failure("订单号不可为空");
        }

        if (info.getOrderNoList().size() > 200) {
            return ResultVo.failure("订单号麻烦控制在200个以内");
        }

        List<OrderModifyUpInfoVO> list = new ArrayList<>();
        int prodFlag = 1;
        switch (OrderModifyTypeEnum.toType(info.getModifyType())) {
            case ddhy : // 订单还原
                prodFlag = 0;
                list = rcvDetailMapperReadOnlyMapper.findOrderInfoByOrderNos(info.getOrderNoList());
                if (CollectionUtils.isEmpty(list)) {
                    list = isEmmtyHand(info.getOrderNoList());
                } else {
                    list = ddhyHand(list,prodFlag);
                }
                break;
            case ddzd: // 订单转订
            case zdckc: // 转订出库存
            case zdczt: // 转订出在途
            case bggys: // 变更供应商
                list = getPurOrderInfoList(info.getOrderNoList());
                if (CollectionUtils.isEmpty(list)) {
                    list = isEmmtyHand(info.getOrderNoList());
                } else {
                    list = ddhyHand(list,prodFlag);
                }
                break;
            case bgpo: // 变更po
                list = rcvDetailMapperReadOnlyMapper.findOrderInfoByOrderNos(info.getOrderNoList());
                if (CollectionUtils.isEmpty(list)) {
                    list = isEmmtyHand(info.getOrderNoList());
                } else {
                    list = cgdbgHand(list,prodFlag);
                }
                break;
            case bgwlh: // 变更物料号
                list = rcvDetailMapperReadOnlyMapper.findOrderInfoByOrderNos(info.getOrderNoList());
                if (CollectionUtils.isEmpty(list)) {
                    list = isEmmtyHand(info.getOrderNoList());
                } else {
                    list = cgdbgHand(list,prodFlag);
                }
                break;
            case cancel_order:
                list = rcvDetailMapperReadOnlyMapper.findOrderInfoByOrderNos(info.getOrderNoList());
                if (CollectionUtils.isEmpty(list)) {
                    list = isEmmtyHand(info.getOrderNoList());
                } else {
                    list = cancelOrderHandData(list,prodFlag);
                }
                break;
//            case bgysfs: // 日本采购的变更运输方式
//                list = rcvDetailMapperReadOnlyMapper.findPurOrderInfoByOrderNos(info.getOrderNoList());
//                list = bgcgHand(list);
//                break;
//            case bgccz: // 日本采购的变更指定出荷日
//                list = rcvDetailMapperReadOnlyMapper.findPurOrderInfoByOrderNos(info.getOrderNoList());
//                list = bgcgHand(list);
//                break;
            default:
                break;
        }
        return ResultVo.success(list);
    }


    public List<OrderModifyUpInfoVO> cancelOrderHandData(List<OrderModifyUpInfoVO> list,int prodFlag) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        for(OrderModifyUpInfoVO item: list) {
            Boolean aBoolean = canDelete(item.getStatus(), item.getExpQty());
            if(aBoolean){
                //17423.6 查询子项,查询出子项的出库数量，如果子项的出库数量大于0，则不允许删单
                LambdaQueryWrapper<OrderStatusDO> statusQueryWrapper = new LambdaQueryWrapper<>();
                statusQueryWrapper.eq(OrderStatusDO::getOrderId, item.getOrderNo())
                        .eq(OrderStatusDO::getOrderItem, item.getOrderItem());
                List<OrderStatusDO> orderStatusDOS = orderStatusMapper.selectList(statusQueryWrapper);
                long outItemCount = orderStatusDOS.stream().filter(orderStatusDO -> orderStatusDO.getQtyOut() != null && orderStatusDO.getQtyOut() > 0).count();
                if (outItemCount > 0) {
                    aBoolean = false;
                    item.setRemark("子项已发货，不允许删单");
                }
            }else{
                item.setRemark(RCVOrderStatusEnum.getName(item.getStatus())+"不允许删单");
            }
            item.setIsReset(aBoolean);
            item.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
        }
        return list;
    }
    /**
     * 是否可删单
     */
    public Boolean canDelete(Integer status,Integer expQty) {
        if (status == null) {
            return false;
        }
//        // 出库处理,已发货,已退货,已开票,已删单 ,异常订单,暂不处理  [是否可删单]
//        List<Integer> canNotDelivery = Arrays.asList((int) RcvOrderStatusEnum.CKING.getType(),
//                (int) RcvOrderStatusEnum.CKED.getType(), (int) RcvOrderStatusEnum.RETURN.getType(),
//                (int) RcvOrderStatusEnum.INVOICE.getType(),(int) RcvOrderStatusEnum.CANCEL.getType(),
//                (int) RcvOrderStatusEnum.EXCEPT.getType(),(int) RcvOrderStatusEnum.UNDEAL.getType());

        // 2 3 4 5 6 12 14
        List<String> canDelOrderStatus = opsCommonService.canDelOrderStatus();
        // 是否可删单
        if (canDelOrderStatus.contains(status.toString())) {
            if (expQty != null && expQty != 0 && status == (int) RcvOrderStatusEnum.CKING.getType()) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public List<OrderModifyUpInfoVO> isEmmtyHand(List<String> orderNoList) {
        List<OrderModifyUpInfoVO> list = new ArrayList<>();
        OrderModifyUpInfoVO item;
        for (String orderNo : orderNoList) {
            item = new OrderModifyUpInfoVO();
            item.setOrderFno(orderNo);
            item.setIsReset(false);
            item.setRemark("请输入正确单号");
            list.add(item);
        }
        return list;
    }

    //订单分配表排序原则
    public static final Comparator<OpsOrderAssignResultDO> STOCK_TYPE_COMPARATOR = Comparator.comparing(OpsOrderAssignResultDO::getStockType,
            Comparator.comparingInt(Arrays.asList(
                    OrderStockTypeEnum.ZK.getType(),
                    OrderStockTypeEnum.ZT.getType(),
                    InventoryStatusEnum.PRODUCE.getCode(), InventoryStatusEnum.CGTRANS.getCode(),
                    InventoryStatusEnum.DBTRANS.getCode(), InventoryStatusEnum.W.getCode(),
                    OrderStockTypeEnum.CG.getType(),
                    "EXCEPTION"
            )::indexOf));

    /**
     *  2025-08-20 C12961依据方案18359修改
     * 根据十三位单号，查询子项状态和出库区分，并拼在一起，
     * 注意：此方法的出库区分不稳定，当13位单号有多条拆分项时，可能会不导致查询assignResult定位不准确，此风险需告知调用方
     * @param orderNoList
     * @return
     */
    public List<OrderModifyUpInfoVO> getPurOrderInfoList(List<String> orderNoList) {
        //创建返回结果
        List<OrderModifyUpInfoVO> OrderModifyList = new ArrayList<>();
        for (String orderNo : orderNoList) {
            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderNo);
            if (PublicUtil.showCountByStr(orderNo) == 1) {
                orderNoInfo.setSplitItem(0);
            }
            if (orderNoInfo.getSplitItem() == null) {
                orderNoInfo.setSplitItem(0);
            }
            LambdaQueryWrapper<RcvDetailDO> qw = new LambdaQueryWrapper<>();
            qw.eq(RcvDetailDO::getRorderNo, orderNoInfo.getOrderNo())
                    .eq(RcvDetailDO::getRorderItem, orderNoInfo.getItemNo());
            List<RcvDetailDO> rcvdetailList = rcvDetailMapperReadOnlyMapper.selectList(qw);
            if (!rcvdetailList.isEmpty()) {
                //查询rcvdetail，判断是否为型号拆分，
                RcvDetailDO rcvDetailDO = rcvdetailList.get(0);
                String prodFlag = rcvDetailDO.getProdFlag();
                boolean assModelShip = OrderSpecExpType.include(rcvDetailDO.getExpDlvType(), OrderSpecExpType.AssChildToExport);
                //型号拆分,且不拆分子型号出库
                if (OrderSplitTypeEnum.modelNoSplit.getCode().equals(prodFlag) && !assModelShip) {
                    //如果是型号拆分
                    List<OrderModifyUpInfoVO> list = rcvDetailMapperReadOnlyMapper.findOrderInfoWithOrderStatusAssModel(orderNoInfo.getOrderNo(),
                            String.valueOf(orderNoInfo.getItemNo()), String.valueOf(orderNoInfo.getSplitItem()));
                    if (CollectionUtils.isNotEmpty(list)) {
                        String stockCode = "";
                        OrderModifyUpInfoVO infoVO = list.get(0);
                        String modelNo = infoVO.getModelNo();
                        //则用result+子型号 获取assignResult的出库区分
                        List<OpsOrderAssignResultDO> resultList = opsAssignResultMapper.findOrderAssignResultList(orderNoInfo.getOrderNo(), orderNoInfo.getItemNo());
                        for (OpsOrderAssignResultDO result : resultList) {
                            if (modelNo.equals(result.getModelno())) {
                                stockCode = result.getStockCode();
                                break;
                            }
                        }
                        for (OrderModifyUpInfoVO orderModifyUpInfoVO : list) {
                            orderModifyUpInfoVO.setSupplier(stockCode);
                            orderModifyUpInfoVO.setOrderFno(orderNo);
                        }
                    }
                    OrderModifyList.addAll(list);
                } else {
                    //如果是数量拆分，则用排序原则确定出库区分
                    //18359 用三位单号查询订单拆分子项
                    List<OrderModifyUpInfoVO> list = rcvDetailMapperReadOnlyMapper.findOrderInfoWithOrderStatusAssQty(orderNoInfo.getOrderNo(),
                            String.valueOf(orderNoInfo.getItemNo()), String.valueOf(orderNoInfo.getSplitItem()));
                    if (CollectionUtils.isNotEmpty(list)) {
                        OrderModifyUpInfoVO infoVO = list.get(0);
                        String stockCode = "";
                        List<OpsOrderAssignResultDO> resultList = opsAssignResultMapper.findOrderAssignResultList(orderNoInfo.getOrderNo(), orderNoInfo.getItemNo());
                        //先用型号过滤
                        resultList = resultList.stream().filter(r -> r.getModelno().equals(infoVO.getModelNo())).collect(Collectors.toList());
                        List<OpsOrderAssignResultDO> resultListByQty = resultList.stream().filter(r -> r.getQuantity().equals(Integer.valueOf(infoVO.getQty()))).collect(Collectors.toList());
                        //如果有数量相等的AssignResult，定义排序原则
                        if (!resultListByQty.isEmpty()) {
                            //排序取第一条
                            if (resultListByQty.size() > 1) {
                                resultListByQty.sort(STOCK_TYPE_COMPARATOR);
                                OpsOrderAssignResultDO result = resultListByQty.get(0);
                                stockCode = result.getStockCode();
                            } else {
                                //只有一条则直接取
                                stockCode = resultListByQty.get(0).getStockCode();
                            }
                        } else {
                            //没有数量相等的，直接排序
                            resultList.sort(STOCK_TYPE_COMPARATOR);
                            stockCode = resultList.get(0).getStockCode();
                        }
                        //保底，如果为空，则取rcv的stockCode
                        if (StringUtils.isBlank(stockCode)) {
                            stockCode = rcvDetailDO.getStockCode();
                        }
                        //给返回信息填写出库区分
                        for (OrderModifyUpInfoVO orderModifyUpInfoVO : list) {
                            orderModifyUpInfoVO.setSupplier(stockCode);
                            orderModifyUpInfoVO.setOrderFno(orderNo);
                        }
                    }
                    OrderModifyList.addAll(list);
                }
            }
        }
        return OrderModifyList;
    }

    @Override
    public ResultVo<String> createPreStockApply(OpsSalesCommonParamVO info) {
        if (Objects.isNull(info) || Objects.isNull(info.getData())) {
            return ResultVo.failure("入参不可为空");
        }
        PreStockApplyDetailDtoTemp preStockApplyDetailDtoTemp = JSONObject.parseObject(JSONObject.toJSONString(info.getData()), PreStockApplyDetailDtoTemp.class);
        log.info("战略在库申请参数转换前 => " +JSONUtil.toJsonPrettyStr(preStockApplyDetailDtoTemp));
        if (StringUtils.isNotEmpty(preStockApplyDetailDtoTemp.getApplyTimeTemp())) {
            preStockApplyDetailDtoTemp.setApplyTime(DateUtil.stringToDateTime(preStockApplyDetailDtoTemp.getApplyTimeTemp()));
        }

        if (CollectionUtils.isNotEmpty(preStockApplyDetailDtoTemp.getDetails())) {
            for (PreStockDetailDtoTemp item : preStockApplyDetailDtoTemp.getDetails()) {
                if (StringUtils.isNotEmpty(item.getDlvDateTemp())) {
                    item.setDlvDate(DateUtil.stringToDateTime(item.getDlvDateTemp()));
                }
                item.setBatchNo(info.getBatchNo());
            }
        }

        PreStockApplyDetailDTO copy = BeanCopyUtil.copy(preStockApplyDetailDtoTemp, PreStockApplyDetailDTO.class);
        if (copy != null && copy.getApproveTime() == null) {
            copy.setApproveTime(new Date());
        }

        log.info("战略在库申请参数转换后 => " +JSONUtil.toJsonPrettyStr(copy));

        ResultVo<String> preStockApply = preStockFeignApi.createPreStockApply(copy);
        return preStockApply;
    }

    @Override
    public ResultVo<String> callBackCreatePreStockApply(OpsSalesCommonParamVO info) {
        if (Objects.isNull(info) || Objects.isNull(info.getData())) {
            return ResultVo.failure("入参为空.");
        }
        String batchNo = info.getData().toString();
        return preStockFeignApi.callBackResultToPortalByBatchNo(batchNo);
    }

    @Override
    public ResultVo<DealReturnOpsParamVO> upCproductNo(OpsSalesCommonParamVO info) {
        if (Objects.isNull(info) || Objects.isNull(info.getData())) {
            return ResultVo.failure("入参为空.");
        }
        log.info("变更物料号cproductNo参数 {} ", JSONUtil.toJsonStr(info));
        String jsonStr = JSONUtil.toJsonStr(info.getData());
        OrderModifyVO orderModifyVO = JSONObject.parseObject(jsonStr, OrderModifyVO.class);

        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();

        if (StringUtils.isBlank(orderModifyVO.getChangeMessage())) {
            dealReturnOpsParamVO = conventCallBackParam(false, orderModifyVO.getApplyNo(), "变更物料号cproductNo为空");
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.bnbg.getCode(),"变更物料号cproductNo为空");
            return ResultVo.failure(dealReturnOpsParamVO,"500","变更物料号cproductNo为空");
        }

        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderModifyVO.getOrderNo());
        OpsOrderModifyDto opsOrderModifyDto = new OpsOrderModifyDto();
        opsOrderModifyDto.setOrderId(orderNoInfo.getOrderNo());

        opsOrderModifyDto.setOrderItem(orderNoInfo.getItemNo());
        opsOrderModifyDto.setMaster(false);
        opsOrderModifyDto.setCproductNo(orderModifyVO.getChangeMessage());
        opsOrderModifyDto.setReason("门户申请变更物料号cproductNo");

        UserDto userDto = new UserDto();
        userDto.setUserName(orderModifyVO.getApplyPersonNo());
        opsOrderModifyDto.setUserDto(userDto);
        CommonResult commonResult = null;
        try {
            commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);
        } catch (Exception e) {
            dealReturnOpsParamVO = conventCallBackParam(false, orderModifyVO.getApplyNo(), "请求接口异常"+e.getMessage());
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.bnbg.getCode(),"请求接口异常"+e.getMessage());
            return ResultVo.failure(dealReturnOpsParamVO,"500","请求接口异常"+e.getMessage());
        }
        if (!Objects.isNull(commonResult)) {
            log.info("变更物料号结果=> {}",JSONUtil.toJsonStr(commonResult));
        }
        if(Objects.isNull(commonResult)) {
            dealReturnOpsParamVO = conventCallBackParam(false, orderModifyVO.getApplyNo(), "变更物料号返回结果为空");
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.bnbg.getCode(),"变更物料号返回结果为空");
            return ResultVo.failure(dealReturnOpsParamVO,"500","变更物料号返回结果为空");
        } else if (commonResult.isSuccess() && commonResult.getData() != null) {
            dealReturnOpsParamVO = conventCallBackParam(true, orderModifyVO.getApplyNo(), commonResult.getData().toString());
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.finish.getCode(),commonResult.getData().toString());
            return ResultVo.success(dealReturnOpsParamVO);
        } else if (!commonResult.isSuccess() && commonResult.getMessage() != null) {
            dealReturnOpsParamVO = conventCallBackParam(false, orderModifyVO.getApplyNo(), commonResult.getData().toString());
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.bnbg.getCode(),commonResult.getData().toString());
            return ResultVo.failure(dealReturnOpsParamVO,"500",commonResult.getData().toString());
        } else {
            dealReturnOpsParamVO = conventCallBackParam(false, orderModifyVO.getApplyNo(), "接口未返回失败原因");
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.bnbg.getCode(),"接口未返回失败原因");
            return ResultVo.failure(dealReturnOpsParamVO,"500","接口未返回失败原因");
        }
    }

    /**
     *  门户申请过来
     *  执行待执行任务 写入orderModify
     *  反射调用接口实现变更
     *  返回回调参数
     *  执行回调
     * @param info
     * @return
     */
    @Override
    public ResultVo<DealReturnOpsParamVO> upPurchaseNo(OpsSalesCommonParamVO info) {
        if (Objects.isNull(info) || Objects.isNull(info.getData())) {
            return ResultVo.failure("入参为空.");
        }
        log.info("变更po号参数 {} ", JSONUtil.toJsonStr(info));
        String jsonStr = JSONUtil.toJsonStr(info.getData());
        OrderModifyVO orderModifyVO = JSONObject.parseObject(jsonStr, OrderModifyVO.class);

        if (StringUtils.isBlank(orderModifyVO.getChangeMessage())) {
            return ResultVo.failure("变更的po号为空");
        }

        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderModifyVO.getOrderNo());
        OpsOrderModifyDto opsOrderModifyDto = new OpsOrderModifyDto();
        opsOrderModifyDto.setOrderId(orderNoInfo.getOrderNo());
        opsOrderModifyDto.setMaster(true);
        opsOrderModifyDto.setPurchaseNo(orderModifyVO.getChangeMessage());
        opsOrderModifyDto.setReason("门户申请变更po");

        UserDto userDto = new UserDto();
        userDto.setUserName(orderModifyVO.getApplyPersonNo());
        opsOrderModifyDto.setUserDto(userDto);

        CommonResult commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);
        if (!Objects.isNull(commonResult)) {
            log.info("变更po号结果=> {}",JSONUtil.toJsonStr(commonResult));
        }
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        if(Objects.isNull(commonResult)) {
            dealReturnOpsParamVO = conventCallBackParam(false, orderModifyVO.getApplyNo(), "变更po返回结果为空");
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.bnbg.getCode(),"变更po返回结果为空");
            return ResultVo.failure(dealReturnOpsParamVO,"500","变更po返回结果为空");
        } else if (commonResult.isSuccess() && commonResult.getData() != null) {
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.finish.getCode(),commonResult.getData().toString());
            dealReturnOpsParamVO = conventCallBackParam(true, orderModifyVO.getApplyNo(), commonResult.getData().toString());
            return ResultVo.success(dealReturnOpsParamVO);
        } else if (!commonResult.isSuccess() && commonResult.getData() != null) {
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.bnbg.getCode(),commonResult.getData().toString());
            dealReturnOpsParamVO = conventCallBackParam(false, orderModifyVO.getApplyNo(), commonResult.getData().toString());
            return ResultVo.failure(dealReturnOpsParamVO,"500",commonResult.getData().toString());
        } else {
            updateOrderModifyInfo(info.getBatchNo(),OrderModifyEnum.bnbg.getCode(),"接口未返回失败原因");
            dealReturnOpsParamVO = conventCallBackParam(false, orderModifyVO.getApplyNo(), "接口未返回失败原因");
            return ResultVo.failure(dealReturnOpsParamVO,"500","接口未返回失败原因");
        }
    }

    @Override
    public ResultVo<DealReturnOpsParamVO> upShikomiWarnQty(OpsSalesCommonParamVO info) {
        if (Objects.isNull(info) || Objects.isNull(info.getData())) {
            return ResultVo.failure("入参为空.");
        }
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        log.info("shikomi参数不足维护参数: {}",JSONUtil.toJsonStr(info));
        String jsonStr = JSONUtil.toJsonStr(info.getData());
        ShikomiWarnCallBackDTO shikomiWarnCallBackDTO = JSONObject.parseObject(jsonStr, ShikomiWarnCallBackDTO.class);
        ResultVo<String> resultVo = productServiceFeignApi.updateShikomiWarnQty(shikomiWarnCallBackDTO);
        // 回调
        if (resultVo.isSuccess()) {
            dealReturnOpsParamVO = conventCallBackParam(true, info.getBatchNo(), resultVo.getData());
        } else {
            dealReturnOpsParamVO = conventCallBackParam(false, info.getBatchNo(), resultVo.getMessage());
        }
        dealReturnOpsParamVO.setApplyType(OpsCommonoSalesApplyTypeEnum.shikomi_modify.getCode());

        return ResultVo.success(dealReturnOpsParamVO);

    }

    @Override
    public ResultVo<List<ModifyPurchaseDto>> getUpPurchaseTAndDInfo(OrderModifyUpInfoRequest info)
    {
        if (Objects.isNull(info) || StringUtils.isBlank(info.getModifyType()) || CollectionUtils.isEmpty(info.getOrderNoList())) {
            return ResultVo.failure("变更类型/订单号,不可为空");
        }
        CommonResult<List<ModifyPurchaseDto>> purchaseT = null;
        if (info.getModifyType().equals(OrderModifyTypeEnum.bgysfs.getCode())) {
            purchaseT = purchaseModifyFeignApi.getPurchaseT(info.getOrderNoList());
            if (!Objects.isNull(purchaseT)) {
                if (purchaseT.isSuccess()) {
                    return ResultVo.success(purchaseT.getData());
                } else {
                    return ResultVo.failure(purchaseT.getMessage());
                }
            }
        } else if (info.getModifyType().equals(OrderModifyTypeEnum.bgccz.getCode())) {
            purchaseT = purchaseModifyFeignApi.getPurchaseD(info.getOrderNoList());
            if (!Objects.isNull(purchaseT)) {
                if (purchaseT.isSuccess()) {
                return ResultVo.success(purchaseT.getData());
            } else {
                return ResultVo.failure(purchaseT.getMessage());
              }
            }
        }
        return ResultVo.failure("变更类型不是T/D");
    }

    @Override
    public ResultVo<List<QueryOrderIntercepterVO>> queryOrderIntercepterInfo(OrderIntercepterInfoVO infoVO) {

        if (infoVO == null) {
            return ResultVo.failure("入参不可为空");
        }

        if (StringUtils.isBlank(infoVO.getCustomerNo()) && StringUtils.isBlank(infoVO.getOrderNo())) {
            return ResultVo.failure("客户代码/订单号,需保证其中一个不为空.");
        }
        List<QueryOrderIntercepterVO> list = rcvDetailMapperReadOnlyMapper.getOrderIntercpterInfo(infoVO);
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success();
        }
        for (QueryOrderIntercepterVO item : list) {

            if (!Objects.isNull(item.getIntercept())) {
                item.setHandleStatus(item.getIntercept() ? 1: 0);
            }

            if(!Objects.isNull(item.getIntercept())) {
                if(item.getIntercept()) {
                    item.setStatusName("信用拦截");
                } else {
                    if(StringUtils.isNotBlank(item.getStatus())) {
                        item.setStatusName(RCVOrderStatusEnum.getName(Integer.valueOf(item.getStatus())));
                    }
                }
            }

        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<DealReturnOpsParamVO> insertSampleBalApply(OpsSalesCommonParamVO info) {
        if (info == null) {
            return ResultVo.failure("参数不可为空");
        }

        SmsSendOpsDetailTaskBean bean = JSONObject.parseObject(JSONUtil.toJsonStr(info.getData()), SmsSendOpsDetailTaskBean.class);

        if (StringUtils.isBlank(bean.getTradeSubject())) {
            return ResultVo.failure("交易主体不可为空");
        }

//        if (StringUtils.isNotBlank(bean.getCarryOverType()) && bean.getCarryOverType().equals(BalTypeEnum.XSKP.getCode())) {
//            if (StringUtils.isBlank(bean.getCustomerType())) {
//                return ResultVo.failure("当结转方式为转销售开票时,客户类型不可为空");
//            }
//        }


        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();

        OpsSalesCommonParamVO opsSalesCommonParamVO = new OpsSalesCommonParamVO();

        // SampleBalApplyVO vo = new SampleBalApplyVO();
        SampleBalApplyCallBackVO callBackVO = new SampleBalApplyCallBackVO();
        try {
            FindHandSampleBalHandVO findHandSampleBalHandVO = new FindHandSampleBalHandVO();
            findHandSampleBalHandVO.setOrderFno(bean.getOrderNo());
            findHandSampleBalHandVO.setSampleBalId(bean.getSampleBalId());
            findHandSampleBalHandVO.setApplyNo(bean.getApplyNo());
            // 查询是否存在受理中的 && 判断是否同一申请是否申请过
            ResultVo<String> handSampleBalApply = sampleOrderApplyFeignApi.findHandSampleBalApply(findHandSampleBalHandVO);
            if (!handSampleBalApply.isSuccess()) {
                throw new BusinessException(handSampleBalApply.getMessage());
            }
            CheckRcvQtyVO checkRcvQtyVO = new CheckRcvQtyVO();
            checkRcvQtyVO.setOrderFno(bean.getOrderNo());
            checkRcvQtyVO.setApplyBalQty(bean.getCarryOverQuantity());

            // 获取订单数量
            ResultVo<RcvDetailVO> orderDetail = receiveOrderService.findOrderDetail(bean.getOrderNo());
            if (!orderDetail.isSuccess()) {
                throw new BusinessException("校验数量异常,未找到此订单信息");
            }

            int orderQty = orderDetail.getData().getQuantity();

            checkRcvQtyVO.setOrderQty(orderQty);
            checkRcvQtyVO.setSampleBalId(bean.getSampleBalId());
            checkRcvQtyVO.setBalFlag(bean.isAlreadyBal());

            // 校验数量以及结转标识是否正确
            ResultVo<String> resultVo = sampleOrderApplyFeignApi.checkRcvQty(checkRcvQtyVO);
            if (!resultVo.isSuccess()) {
                throw new BusinessException(resultVo.getMessage());
            }

            //  保存附件信息
            if (CollectionUtils.isNotEmpty(bean.getAttachmentList())) {
                opsAttachedFileManageService.insertFileInfo(bean.getApplyNo(),bean.getAttachmentList(),CommonConstants.COMMON_USER_OPS,
                        FileUploadTypeEnum.SAMPLEORDERBAL.getFileType(),FileUploadTypeEnum.SAMPLEORDERBAL.getBusinessType());
            }

            // 数据转换 写入样品结转申请表
            SampleBalApplyVO vo = conventEntityData(bean);
            vo.setBatchNo(info.getBatchNo());
            sampleOrderApplyFeignApi.insertSampleBalApply(vo);

            // 良返时写入退货表
            if (bean.getCarryOverType().equals(BalTypeEnum.LPFH.getCode())) {
                ResultVo<String> stringResultVo = sampleOrderApplyFeignApi.insertReturnOrder(bean);
                if (!stringResultVo.isSuccess()) {
                    throw new BusinessException(stringResultVo.getMessage());
                }
            }

            callBackVO.setSampleBalApplyNo(bean.getApplyNo());
            callBackVO.setOrderNo(bean.getOrderNo());
            callBackVO.setModelNo(bean.getModelNo());
            callBackVO.setApplyBalQty(bean.getCarryOverQuantity());


            SampleBalApplySpecialVO specialVO = JSONObject.parseObject(vo.getSpecial(), SampleBalApplySpecialVO.class);

            // 强制结转标识 自动结转
            if (specialVO.getForceBalFlag()) {
                ResultVo<SampleBalApplyVO> sampleBalApplyInfo = sampleOrderApplyFeignApi.getSampleBalApplyInfo(vo);
                if (!sampleBalApplyInfo.isSuccess()) {
                    throw new BusinessException(vo.getSampleBalApplyNo()+" "+vo.getOrderNo()+"门户申请结转写入申请失败");
                }
                sampleOrderApplyFeignApi.sureApplySampleBalApi(Collections.singletonList(String.valueOf(sampleBalApplyInfo.getData().getId())));

                callBackVO.setHandStatus(SampleBalApplyHandStatusEnum.alreadyBal.getCode());
                callBackVO.setHandStatusName(SampleBalApplyHandStatusEnum.alreadyBal.getCodeName());
            } else {
                // 如果转销售开票 自动确认结转
                if (BalTypeEnum.XSKP.getCode().equals(vo.getApplyBalType())) {
                    ResultVo<SampleBalApplyVO> sampleBalApplyInfo = sampleOrderApplyFeignApi.getSampleBalApplyInfo(vo);
                    if (!sampleBalApplyInfo.isSuccess()) {
                        throw new BusinessException(vo.getSampleBalApplyNo()+" "+vo.getOrderNo()+"门户申请转销售开票申请写入申请失败");
                    }
                    sampleOrderApplyFeignApi.sureApplySampleBalApi(Collections.singletonList(String.valueOf(sampleBalApplyInfo.getData().getId())));

                    callBackVO.setHandStatus(SampleBalApplyHandStatusEnum.alreadyBal.getCode());
                    callBackVO.setHandStatusName(SampleBalApplyHandStatusEnum.alreadyBal.getCodeName());

                } else {
                    callBackVO.setHandStatus(SampleBalApplyHandStatusEnum.alreadyHand.getCode());
                    callBackVO.setHandStatusName(SampleBalApplyHandStatusEnum.alreadyHand.getCodeName());
                }
            }
            callBackVO.setBalType(bean.getCarryOverType());

            opsSalesCommonParamVO.setData(callBackVO);
            // 最外层实体
            dealReturnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.SAMPLE_CARRY_OVER.getCode());
            DealReturnOpsParam param = new DealReturnOpsParam();
            param.setOpsSalesCommonParamVo(opsSalesCommonParamVO);
            dealReturnOpsParamVO.setDealReturnOpsParam(param);
        } catch (Exception e) {
            log.info("可结转清单task任务处理发生异常 {}",e.getMessage(),e);
            callBackVO.setSampleBalApplyNo(bean.getApplyNo());
            callBackVO.setOrderNo(bean.getOrderNo());
            callBackVO.setModelNo(bean.getModelNo());
            callBackVO.setApplyBalQty(bean.getCarryOverQuantity());
            callBackVO.setHandStatus(SampleBalApplyHandStatusEnum.returnApply.getCode());
            callBackVO.setHandStatusName(SampleBalApplyHandStatusEnum.returnApply.getCodeName());
            callBackVO.setBalType(bean.getCarryOverType());
            callBackVO.setHandRemark("异常:"+e.getMessage());

            opsSalesCommonParamVO.setData(callBackVO);
            // 最外层实体
            dealReturnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.SAMPLE_CARRY_OVER.getCode());
            DealReturnOpsParam param = new DealReturnOpsParam();
            param.setOpsSalesCommonParamVo(opsSalesCommonParamVO);
            dealReturnOpsParamVO.setDealReturnOpsParam(param);

        }
       if (SampleBalApplyHandStatusEnum.returnApply.getCode().equals(callBackVO.getHandStatus())) {
            return ResultVo.failure(dealReturnOpsParamVO,"400",callBackVO.getHandRemark());
       }
        return ResultVo.success(dealReturnOpsParamVO);
    }

    public SampleBalApplyVO conventEntityData(SmsSendOpsDetailTaskBean bean) {
        SampleBalApplyVO vo = new SampleBalApplyVO();
        vo.setSampleBalApplyNo(bean.getApplyNo()); // 结转申请号
        vo.setApplyNo(bean.getSampleAppleNo()); // 样品申请号
        vo.setOrderNo(bean.getOrderNo());
        vo.setCustomerNo(bean.getCustomerNo());
        vo.setUserNo(bean.getUserNo());
        vo.setModelNo(bean.getModelNo());
        vo.setQuantity(bean.getOrderQuantity()); // 订单数量
        if(StringUtils.isNotBlank(bean.getCarryOverType()) && BalTypeEnum.LPFH.getCode().equals(bean.getCarryOverType())) {
            vo.setApplyBalQty(bean.getReturnQuantity());
        } else {
            vo.setApplyBalQty(bean.getCarryOverQuantity());
        }
        vo.setPrice(bean.getOrderUnitPrice());
        vo.setApplyBalPrice(bean.getBillingUnitPrice());
        vo.setUserPrice(bean.getUserUnitPrice());
        vo.setSpecOfferNo(bean.getSpecialPriceNo());
        vo.setApplyDeptNo(bean.getApplyDeptNo());
        vo.setApplyPsnNo(bean.getApplyPersonNo());
        vo.setApplyTime(bean.getApplyTime());
        vo.setApplyType(bean.getSampleApplyType());
        vo.setApplyBalType(bean.getCarryOverType());
        vo.setBalDeptNo(bean.getCarryOverDeptNo());
        vo.setBackWarehource(bean.getReturnLogisticsCenter()); // 返回物流中心
        vo.setBackTime(bean.getReturnDate());
        // vo.setCarrierId();  寄送承运商
        // 寄送运单号
        vo.setApplyReason(vo.getApplyReason());
        if (BalTypeEnum.XSKP.getCode().equals(vo.getApplyBalType())) {
            vo.setHandStatus(Integer.parseInt(SampleBalApplyHandStatusEnum.alreadyBal.getCode()));
        } else {
            vo.setHandStatus(Integer.parseInt(SampleBalApplyHandStatusEnum.alreadyHand.getCode()));
        }
        Date nowDate = new Date();
        vo.setHandTime(nowDate);
        vo.setCreateTime(nowDate);
        vo.setCreateUser(CommonConstants.COMMON_USER_SALES);
        vo.setToUserStock(false);
        vo.setSampleBalId(bean.getSampleBalId());
        vo.setIsAlreadyBal(bean.isAlreadyBal());
        SampleBalApplySpecialVO specialVO = new SampleBalApplySpecialVO();
        specialVO.setMailingDate(bean.getMailingDate());
        specialVO.setTrackingNo(bean.getTrackingNo());
        specialVO.setForceBalFlag(bean.isAutoCarryOverFlag());
        vo.setSpecial(JSONUtil.toJsonStr(specialVO));
        vo.setTradeCompanyId(bean.getTradeSubject());
        vo.setCustomerType(bean.getCustomerType());
        return vo;
    }

    public List<OrderModifyUpInfoVO> ddhyHand(List<OrderModifyUpInfoVO> list, int prodFlag) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        for (OrderModifyUpInfoVO item : list) {
            if (item == null) {
                continue;
            }
            if (prodFlag == 0 && prodFlag != item.getProdFlag()) {
                item.setIsReset(false);
                item.setRemark("拆分型号不可进行订单还原.");
                continue;
            }
            if ( item.getStatus() < 6 || item.getIntercept() || item.getStatus() == 14 ) {
                item.setIsReset(true);
            } else {
                item.setIsReset(false);
                item.setRemark(RCVOrderStatusEnum.getName(item.getStatus())+"不可变更,订单状态为出库处理之前才可变更");
            }
            item.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
        }
        return list;
    }

    public List<OrderModifyUpInfoVO> cgdbgHand(List<OrderModifyUpInfoVO> list,int prodFlag) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        for (OrderModifyUpInfoVO item : list) {
            if (item.getStatus() < 6 || item.getIntercept() || item.getStatus() == 14) {
                item.setIsReset(true);
            } else {
                item.setIsReset(false);
                item.setRemark(RCVOrderStatusEnum.getName(item.getStatus())+"不可变更,订单状态在出库处理之前才可变更");
            }
        }
        return list;
    }

    public List<OrderModifyUpInfoVO> bgcgHand(List<OrderModifyUpInfoVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        for (OrderModifyUpInfoVO item : list) {
            if (item.getStateCode() < 3) {
                item.setIsReset(true);
            } else {
                item.setIsReset(false);
                item.setRemark(RCVOrderStatusEnum.getName(item.getStatus())+"不可变更,采购单状态在运输中之前才可变更");
            }
        }
        return list;
    }

    @Override
    public ResultVo<String> execCallBackInterface(String groupCode) {
        List<OpsSalesNoticeConfigDO> businessCodeList = null;

        String key = Constants.OPS_NOTICE_BUSINESSCODE_BYGROUP+groupCode;

        Object o = redisManager.get(key);
        if (o == null) {
            // 获取需要执行的业务编码
            businessCodeList = getBusinessCodeList(groupCode);
            redisManager.set(key,JSONArray.toJSONString(businessCodeList),60*60*24);
        } else {
            businessCodeList = JSONArray.parseArray(o.toString(), OpsSalesNoticeConfigDO.class);
        }

        if (CollectionUtils.isEmpty(businessCodeList)) {
            return ResultVo.failure(groupCode+"组未找到需要执行的业务编码");
        }
        // 提取bussinessCode
        List<String> codeList = businessCodeList.stream().map(OpsSalesNoticeConfigDO::getBusinessCode).collect(Collectors.toList());

        // 查询待执行的任务列表
        List<String> status = new ArrayList<>();
        status.add(OpsSalesTaskReturnStatus.notReturn.getCode());
        status.add(OpsSalesTaskReturnStatus.return_error.getCode());
        LambdaQueryWrapper<OpsSalesNoticeTaskDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .ne(OpsSalesNoticeTaskDO::getHandleStatus,OpsSalesTaskHandStatus.notHand.getCode())
                .in(OpsSalesNoticeTaskDO::getBusinessCode,codeList)
                .in(OpsSalesNoticeTaskDO::getReturnStatus,status)
                .lt(OpsSalesNoticeTaskDO::getTryCount,3)
                .isNotNull(OpsSalesNoticeTaskDO::getCallBackParameter);
        queryWrapper.orderByAsc(OpsSalesNoticeTaskDO::getId);
        List<OpsSalesNoticeTaskDO> opsSalesNoticeTaskDOS = opsSalesNoticeTaskMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(opsSalesNoticeTaskDOS)) {
            return ResultVo.success("暂无可执行的任务列表");
        }
        OpsSalesNoticeConfigDO configByCode;
        LambdaUpdateWrapper<OpsSalesNoticeTaskDO> updateWrapper;
        OrderModifyVO orderModifyVO;
        for (OpsSalesNoticeTaskDO item : opsSalesNoticeTaskDOS) {

            // 当业务类型为删单时,增加一次实时查询
            if (Constants.SECONDARY_APPROVAL.equals(item.getBusinessCode())) {
                LambdaQueryWrapper<OpsSalesNoticeTaskDO> taskDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
                taskDOLambdaQueryWrapper.eq(OpsSalesNoticeTaskDO::getBatchNo,item.getBatchNo());
                item = opsSalesNoticeTaskMapper.selectOne(taskDOLambdaQueryWrapper);
            }

            configByCode = getConfigByCode(item.getBusinessCode());
            orderModifyVO = new OrderModifyVO();
            orderModifyVO.setBatchNo(item.getBatchNo());
            if(Objects.isNull(configByCode)) {
                UpTaskInfoVO info = new UpTaskInfoVO();
                info.setBatchNo(item.getBatchNo());
                info.setReturnStatus(OpsSalesTaskReturnStatus.return_error.getCode());
                info.setErrorMsg("未找到业务编码对应配置");
                info.setOptUserNo(CommonConstants.COMMON_USER_OPS_JOB);
                if (OpsSalesTaskReturnStatus.return_error.getCode().equals(info.getReturnStatus())) {
                    info.setTryCount(item.getTryCount()+1);
                }
                upTaskInfoByBatchNo(info);
                continue;
            }
            if(StringUtils.isBlank(configByCode.getCallbackMethodName()) || StringUtils.isBlank(configByCode.getCallbackClassName())) {
                UpTaskInfoVO info = new UpTaskInfoVO();
                info.setBatchNo(item.getBatchNo());
                info.setReturnStatus(OpsSalesTaskReturnStatus.return_error.getCode());
                info.setErrorMsg("未找到回调方法,请检查配置");
                info.setOptUserNo(CommonConstants.COMMON_USER_OPS_JOB);
                if (OpsSalesTaskReturnStatus.return_error.getCode().equals(info.getReturnStatus())) {
                    info.setTryCount(item.getTryCount()+1);
                }
                upTaskInfoByBatchNo(info);
                if (configByCode.getIsInsertOrderModify() != null && configByCode.getIsInsertOrderModify()) {
                    orderModifyVO.setRemark("回调门户失败:"+info.getErrorMsg());
                    orderModifyService.updateOrderModifyInfo(orderModifyVO);
                }
                continue;
            }
            Object result = null;
            try {
                result = callInterface(configByCode.getCallbackMethodName(), configByCode.getCallbackClassName(),item.getCallBackParameter(),
                        item.getBatchNo(),configByCode.getBusinessCode(),item.getApplyNo());
            } catch (Exception e) {
                log.error("回调反射调用接口失败: {}",e.getMessage(),e);
                UpTaskInfoVO info = new UpTaskInfoVO();
                info.setBatchNo(item.getBatchNo());
                info.setReturnStatus(OpsSalesTaskReturnStatus.return_error.getCode());
                info.setErrorMsg("回调失败: "+e.getMessage());
                info.setOptUserNo(CommonConstants.COMMON_USER_OPS_JOB);
                if (OpsSalesTaskReturnStatus.return_error.getCode().equals(info.getReturnStatus())) {
                    info.setTryCount(item.getTryCount()+1);
                }
                upTaskInfoByBatchNo(info);
                if (configByCode.getIsInsertOrderModify() != null && configByCode.getIsInsertOrderModify()) {
                    orderModifyVO.setRemark("回调门户失败:"+e.getMessage());
                    orderModifyService.updateOrderModifyInfo(orderModifyVO);
                }
                continue;
            }
            if (Objects.isNull(result)) {
                item.setErrorMsg("回调执行失败,无返回结果");
                item.setReturnStatus(OpsSalesTaskReturnStatus.return_error.getCode());
            } else {
                ResultVo returnResult =  (ResultVo)result;
                if (returnResult.isSuccess()) {
                    item.setReturnStatus(OpsSalesTaskReturnStatus.return_success.getCode());
                } else {
                    item.setReturnStatus(OpsSalesTaskReturnStatus.return_error.getCode());
                }
                item.setCallBackReturnResult(JSONUtil.toJsonStr(returnResult));
            }
            if (OpsSalesTaskReturnStatus.return_error.getCode().equals(item.getReturnStatus())) {
                item.setTryCount(item.getTryCount()+1);
            }
            updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(OpsSalesNoticeTaskDO::getId,item.getId())
                    .set(StringUtils.isNotBlank(item.getCallBackReturnResult()),OpsSalesNoticeTaskDO::getCallBackReturnResult,item.getCallBackReturnResult())
                    .set(OpsSalesNoticeTaskDO::getReturnStatus,item.getReturnStatus())
                    .set(OpsSalesNoticeTaskDO::getTryCount,item.getTryCount())
                    .set(OpsSalesNoticeTaskDO::getUpdateUser,CommonConstants.COMMON_USER_OPS_JOB)
                    .set(StringUtils.isNotBlank(item.getErrorMsg()), OpsSalesNoticeTaskDO::getErrorMsg,item.getErrorMsg())
                    .set(OpsSalesNoticeTaskDO::getUpdateTime,new Date());
            opsSalesNoticeTaskMapper.update(null,updateWrapper);
            if (OpsSalesTaskReturnStatus.return_error.getCode().equals(item.getReturnStatus())) {
                if (configByCode.getIsInsertOrderModify() != null && configByCode.getIsInsertOrderModify()) {
                    orderModifyVO.setRemark("回调门户失败:"+item.getCallBackReturnResult());
                    orderModifyService.updateOrderModifyInfo(orderModifyVO);
                }
            }
        }
        return ResultVo.success("执行完毕");
    }

    @Override
    public ResultVo<String> upTaskInfoByBatchNo(UpTaskInfoVO param) {
        if (Objects.isNull((param)) || StringUtils.isBlank(param.getBatchNo())) {
            return ResultVo.failure("批次号不可为空.");
        }
        LambdaUpdateWrapper<OpsSalesNoticeTaskDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OpsSalesNoticeTaskDO::getBatchNo,param.getBatchNo());
        updateWrapper
                .set(StringUtils.isNotBlank(param.getCallBackParameter()),OpsSalesNoticeTaskDO::getCallBackParameter,param.getCallBackParameter())
                .set(param.getTryCount() != 0,OpsSalesNoticeTaskDO::getTryCount,param.getTryCount())
                .set(param.getErrorHandCount() != 0,OpsSalesNoticeTaskDO::getErrorHandCount,param.getErrorHandCount())
                .set(StringUtils.isNotBlank(param.getHandleStatus()),OpsSalesNoticeTaskDO::getHandleStatus,param.getHandleStatus())
                .set(StringUtils.isNotBlank(param.getReturnStatus()),OpsSalesNoticeTaskDO::getReturnStatus,param.getReturnStatus())
                .set(StringUtils.isNotBlank(param.getOptUserNo()),OpsSalesNoticeTaskDO::getUpdateUser,param.getOptUserNo())
                .set(StringUtils.isNotBlank(param.getReturnResult()),OpsSalesNoticeTaskDO::getReturnResult,param.getReturnResult())
                .set(StringUtils.isNotBlank(param.getErrorMsg()),OpsSalesNoticeTaskDO::getErrorMsg,param.getErrorMsg())
                .set(OpsSalesNoticeTaskDO::getUpdateTime,new Date());
        int update = opsSalesNoticeTaskMapper.update(null, updateWrapper);
        if (update > 0) {
            return ResultVo.success("操作成功.");
        }
        return ResultVo.failure("操作失败.");
    }

    @Override
    public ResultVo callBackInterface(OpsSalesCommonParamVO info) {
        String authorization = "";
        Object o = redisManager.get("ops:mh_token:");
        if (o != null) {
            authorization = o.toString();
        } else {
            authorization = createTokenForOtherServer.getMHToken();
            redisManager.set("ops:mh_token:", authorization,3600*5);
        }
        log.info("回调门户-参数 = {} auth {}", JSONUtil.toJsonStr(info.getData()), authorization);
        String url = menHuUrl + RETURN_DEAL_INFO_URL;
        // String url = "http://10.116.1.102:9204/saleManageSystem/opsReturnInfo/dealReturnInfo";
        HttpResponse response = HttpUtil.createPost(url)
                .header("Authorization", authorization)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(JSONUtil.toJsonStr(info.getData()))
                .execute();
        log.info("共通接口->回调门户 响应: {}", response.body());
        ResultVo resultVo = JSONUtil.toBean(response.body(), ResultVo.class);
        if (resultVo != null && StringUtils.isNotBlank(resultVo.getMessage()) && resultVo.getMessage().contains("token失效")) {
            authorization = createTokenForOtherServer.getMHToken();
            redisManager.set("ops:mh_token:", authorization,3600*5);
        }
        log.info("共通接口->回调结果{}", resultVo.toString());
        return resultVo;
    }

    public OpsSalesNoticeConfigDO getConfigByCode(String code) {
        if(StringUtils.isBlank(code)) {
            return null;
        }
        LambdaQueryWrapper<OpsSalesNoticeConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsSalesNoticeConfigDO::getBusinessCode,code).eq(OpsSalesNoticeConfigDO::getIsDeleted,false);
        return opsSalesNoticeConfigMapper.selectOne(queryWrapper);
    }

    public List<OpsSalesNoticeConfigDO> getBusinessCodeList(String groupName) {
        LambdaQueryWrapper<OpsSalesNoticeConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpsSalesNoticeConfigDO::getIsDeleted,false)
                .eq(OpsSalesNoticeConfigDO::getGroupName,groupName);
        return opsSalesNoticeConfigMapper.selectList(queryWrapper);
    }

}
