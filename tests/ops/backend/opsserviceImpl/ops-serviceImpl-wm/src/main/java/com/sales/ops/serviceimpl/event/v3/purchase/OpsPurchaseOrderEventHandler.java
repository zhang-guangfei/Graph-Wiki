package com.sales.ops.serviceimpl.event.v3.purchase;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.PurchaseReplyPushJobMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.order.OrderInventoryQueryDto;
import com.sales.ops.dto.purchase.PoReplyInfoDto;
import com.sales.ops.dto.purchase.PurchaseCancelEventParam;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.enums.PurchaseCancelSourceEnum;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.service.inventory.AdjustInventoryService;
import com.sales.ops.service.log.OpsOrderModiDataService;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.OpsChangeOrderService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.event.v3.stockassign.OpsOrderAssignResultService;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.OpsSalesNoticeTaskFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author C12961
 * @date 2022/4/16 16:50
 */
@Slf4j
@Service
public class OpsPurchaseOrderEventHandler implements OpsPurchaseOrderEventService {


    @Resource
    private OpsChangeOrderService opsChangeOrderService;
    @Autowired
    private BaseCustomerOrderService baseCustomerOrderService;
    @Autowired
    private OpsOrderAssignResultService opsOrderAssignResultService;
    @Autowired
    private OpsOrderModiDataService opsOrderModiDataService;
    @Autowired
    private AdjustInventoryService adjustInventoryService;
    @Autowired
    private OpsSalesNoticeTaskFeignApi opsSalesNoticeTaskFeignApi;
    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private CustomerEventPublisher customerEventPublisher;
    @Autowired
    private PurchaseReplyPushJobMapper purchaseReplyPushJobMapper;
    @Autowired
    private BasePoService basePoService;
    @Autowired
    private CommonServiceFeignApi commonServiceFeignApi;




    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelPurchaseOrder(String poNo, Integer poItem, Integer poSplitNo, PurchaseCancelEventParam param) throws OpsException {
        //BUGID:18651 C14717
        if(Objects.isNull(poSplitNo)){
            poSplitNo = 0;
        }
        List<OpsRequestpurchase> list = param.getRequestpurchaseList();
        // 解析采购删单来源 // 12991 增加采购删单标识
        PurchaseCancelSourceEnum cancelSourceEnum = PurchaseCancelSourceEnum.parse(param.getCancelSource());
        // 物流模块
        // List<OrderInventoryQueryDto> dtoList = opsChangeOrderService.delPoSoDelDoItemInventoryByPo(poNo, poItem, poSplitNo, list);
        // bugid 10555 20230423 c14717 SELECT * from ops_event_purchase_order where event_code ='PURCHASE_ORDER_CANCEL'
        List<OrderInventoryQueryDto> dtoList = opsChangeOrderService.delPoSoDelDoNew(poNo, poItem, poSplitNo, list, cancelSourceEnum);
        // bugid 12911 20240104 c14717 采购删除后删除调库计划

        adjustInventoryService.delPoAfterDelTPlan(poNo, poItem, poSplitNo);
        // 订单模块
        // 如果是订单还原，则不往下执行
        if (!PurchaseCancelSourceEnum.needResetRcv(cancelSourceEnum)) {
            return;
        }
        // 处理客单状态
        for (OrderInventoryQueryDto dto : dtoList) {
            handleCustomerOrderForCancelPo(dto,cancelSourceEnum);
        }
    }

    private void handleCustomerOrderForCancelPo(OrderInventoryQueryDto dto,PurchaseCancelSourceEnum cancelSourceEnum) throws OpsException {
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByKey(dto.getOrderId(), Integer.valueOf(dto.getOrderItem()));
        dto.setOrderFullNo(rcvdetail.getRorderFno());
        if (StringUtils.isEmpty(dto.getModelno())) {
            dto.setModelno(rcvdetail.getModelNo());
        }
        // 用配置表校验，如果通过了才会继续执行
        // 如果客户订单被删除，则此处为false,则不用执行【客户订单重新处理】或【客户订单状态设置为异常】
        boolean enable = baseCustomerOrderService.enableUpdateStatus(RcvOrderStatusEnum.getEnumByType(rcvdetail.getStatus()), RcvOrderStatusEnum.INIT);
        // 如果客户订单没有被删除，且则会重新处理客户订单或客户订单状态设置为异常
        if (enable) {
            if (dto.getDelDo()) {
                // 客户订单重新处理
                updateOrderToInit(dto);
            } else {
                // 客户订单状态设置为异常
                updateOrderToException(dto, rcvdetail,cancelSourceEnum);
            }
        }
    }


    private void updateOrderToInit(OrderInventoryQueryDto dto) throws OpsException {
        // 1.找到OrderStatus，软删除
        customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_PURCHASE_CANCEL, dto.getOrderId(), Integer.valueOf(dto.getOrderItem()));
        // 2.找到AssignResult，软删除
        opsOrderAssignResultService.updateForPoCancelToReAllot(dto.getOrderId(), Integer.valueOf(dto.getOrderItem()));
        // 3.找到RcvDetail，变更状态为待处理
        baseCustomerOrderService.updateStatusToInit(dto.getOrderId(), dto.getOrderItem());
        // 4.找到modiData，插入变更日志
        String remark = String.format("采购取消,订单重置,型号为%s,关联单号为%s", dto.getModelno(), new OrderNoInfo(dto.getPoNo(), dto.getPoItemNo(), dto.getPoSplitNo()).getFullNo());
        opsOrderModiDataService.insertModiDataForResetInit(dto.getOrderId(), Integer.valueOf(dto.getOrderItem()), dto.getOrderFullNo(), remark);
    }

    private void updateOrderToException(OrderInventoryQueryDto dto, Rcvdetail rcvdetail,PurchaseCancelSourceEnum cancelSourceEnum) throws OpsException {
        String poNo = dto.getPoNo();
        Integer poItemNo = dto.getPoItemNo();
        Integer poSplitNo = dto.getPoSplitNo();
        if(PurchaseCancelSourceEnum.CANCEL_REQUEST_PURCHASE==cancelSourceEnum){
            poNo = null;
            poItemNo = null;
            poSplitNo = null;
        }
        // 1.找到OrderStatus，变更状态为异常
        //opsCustomerOrderStatusEventService.updateStatusToException(dto,cancelSourceEnum);
        customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_PURCHASE_CANCEL, dto.getOrderId(), Integer.valueOf(dto.getOrderItem()));
        // 2.找到AssignResult，变更状态为异常
        opsOrderAssignResultService.updateForPoCancelToException(dto.getOrderId(), Integer.valueOf(dto.getOrderItem()), dto.getModelno(), poNo, poItemNo, poSplitNo);
        // 3.找到RcvDetail，变更状态为异常
        baseCustomerOrderService.updateStatus(dto.getOrderId(), Integer.valueOf(dto.getOrderItem()), RcvOrderStatusEnum.RESOLVE);
        // 4.找到modiData，插入变更日志
        String remark = String.format("采购取消,型号为%s,关联单号为%s", dto.getModelno(), new OrderNoInfo(dto.getPoNo(), dto.getPoItemNo(), dto.getPoSplitNo()).getFullNo());
        opsOrderModiDataService.insertModiDataForCancelPo(dto.getOrderId(), Integer.valueOf(dto.getOrderItem()), rcvdetail.getRorderFno(), remark);
    }

    // 事件处理器：采购返信纳期、延期提醒
    @Override
    public void purchaseReplyDate(String poNo, Integer poItem, Integer poSplitNo, PoReplyInfoDto replyInfo) throws OpsException {
        //1.写入采购返信信息推送job表
        insertPurchaseReplyPushJob(replyInfo, "N");
        //2.写入李营超notice表
        List<Rcvdetail> rcvdetailList = new ArrayList<>();
        List<OpsInventoryMove> moves = baseInventoryService.findInventoryMoveByPo(poNo, poItem, poSplitNo);
        for (OpsInventoryMove move : moves) {
            // 查询关联的客单的rcvdetails
            List<Rcvdetail> rcvdetails = opsDoService.findRcvDetailsByMove(move.getInventoryId());
            rcvdetailList.addAll(rcvdetails);
        }
        // 去重分组
        Map<String, List<Rcvdetail>> rcvMap = rcvdetailList.stream().collect(Collectors.groupingBy(Rcvdetail::getRorderFno));
        rcvMap.forEach((rorderFno, list) -> {
            if (!CollectionUtils.isEmpty(list)) {
                Rcvdetail rcvdetail = list.get(0);
                Rcvmaster rcvmaster = baseCustomerOrderService.findRcvMaster(rcvdetail.getRorderNo());
                String jsonStr = getJsonStr(replyInfo, rcvdetail, rcvmaster, "N");
                ResultVo<String> vo = opsSalesNoticeTaskFeignApi.execInsertOpsSalesNoticeTask(jsonStr);
                if (!vo.isSuccess()){
                    log.error("采购返信信息推送noticeTask失败,{},\n原参数：{}", vo.getMessage(),jsonStr);
                }
            }
        });
    }


    @Override
    public void purchaseDelayDate(String poNo, Integer poItem, Integer poSplitNo, PoReplyInfoDto delayInfo) throws OpsException {
        //1.写入采购返信信息推送job表
        insertPurchaseReplyPushJob(delayInfo, "M");
        //2.写入李营超notice表
        List<Rcvdetail> rcvdetailList = new ArrayList<>();
        List<OpsInventoryMove> moves = baseInventoryService.findInventoryMoveByPo(poNo, poItem, poSplitNo);
        for (OpsInventoryMove move : moves) {
            // 查询关联的客单的rcvdetails
            List<Rcvdetail> rcvdetails = opsDoService.findRcvDetailsByMove(move.getInventoryId());
            rcvdetailList.addAll(rcvdetails);
        }
        // 去重分组
        Map<String, List<Rcvdetail>> rcvMap = rcvdetailList.stream().collect(Collectors.groupingBy(Rcvdetail::getRorderFno));
        rcvMap.forEach((rorderFno, list) -> {
            if (!CollectionUtils.isEmpty(list)) {
                Rcvdetail rcvdetail = list.get(0);
                Rcvmaster rcvmaster = baseCustomerOrderService.findRcvMaster(rcvdetail.getRorderNo());
                String jsonStr = getJsonStr(delayInfo, rcvdetail, rcvmaster, "M");
                ResultVo<String> vo = opsSalesNoticeTaskFeignApi.execInsertOpsSalesNoticeTask(jsonStr);
                if (!vo.isSuccess()){
                    log.error("采购返信信息推送noticeTask失败,{},\n原参数：{}", vo.getMessage(),jsonStr);
                }
            }
        });
    }
    private void insertPurchaseReplyPushJob(PoReplyInfoDto replyInfo,String businessCode) {
        try {
            if("M".equals(businessCode)){

            } else if ("N".equals(businessCode)) {
                String orderType = replyInfo.getOrderType();
                if (basePoService.isSalesType(orderType)) {
                    //根据采购单号查询
                    RcvView rcvView = baseCustomerOrderService.findRcvViewByNo(replyInfo.getPoOrderNo(), replyInfo.getPoItemNo());
                    replyInfo.setRorderFno(rcvView.getRorderFno());
                    replyInfo.setRorderNo(rcvView.getRorderNo());
                    replyInfo.setRorderItem(rcvView.getRorderItem());
                    replyInfo.setCustomerNo(rcvView.getCustomerNo());
                    replyInfo.setUserNo(rcvView.getUserNo());
                    replyInfo.setEndUser(rcvView.getEndUser());
                    replyInfo.setDeptNoCustomer(rcvView.getDeptNo());
                    if (StringUtils.isNotBlank(replyInfo.getEndUser())) {
                        ResultVo<List<CustomerVO>> resultVo = commonServiceFeignApi.findCustomerInfoByNoOrName(replyInfo.getEndUser());
                        if (resultVo.isSuccess()) {
                            List<CustomerVO> data = resultVo.getData();
                            if (CollectionUtils.isNotEmpty(data)) {
                                CustomerVO customerVO = data.get(0);
                                if (customerVO != null) {
                                    replyInfo.setLeader(customerVO.getPSNSMCID());
                                }
                            }
                        }
                    }
                }
            }
            PurchaseReplyPushJob data = new PurchaseReplyPushJob();
            data.setBusinessCode(businessCode);
            data.setOrderFno(replyInfo.getPoOrderFNo());
            data.setParameter(JSONUtil.toJsonStr(replyInfo));
            data.setHandleStatus(0);
            data.setCreateTime(new Date());
            purchaseReplyPushJobMapper.insertSelective(data);
        } catch (Exception e) {
            log.error("采购返信纳期、延期提醒异常：{}", e);
        }
    }

    private String getJsonStr(PoReplyInfoDto delayInfo, Rcvdetail rcvdetail, Rcvmaster rcvmaster, String businessCode) {
        class NoticeTask {
            private String businessCode;
            private String orderFno;
            private Param parameter;


            public NoticeTask(String businessCode, String orderFno, Object obj) {
                this.businessCode = businessCode;
                this.orderFno = orderFno;
                this.parameter = new Param(obj);
            }

            public String getBusinessCode() {
                return businessCode;
            }

            public void setBusinessCode(String businessCode) {
                this.businessCode = businessCode;
            }

            public String getOrderFno() {
                return orderFno;
            }

            public void setOrderFno(String orderFno) {
                this.orderFno = orderFno;
            }

            public Param getParameter() {
                return parameter;
            }

            public void setParameter(Param parameter) {
                this.parameter = parameter;
            }

            class Param {
                private Object data;

                public Param(Object data) {
                    this.data = data;
                }

                public Object getData() {
                    return data;
                }

                public void setData(Object data) {
                    this.data = data;
                }
            }


        }

        PoReplyInfoDto dto = new PoReplyInfoDto();
        BeanUtils.copyProperties(delayInfo, dto);

        dto.setRorderFno(rcvdetail.getRorderFno());
        dto.setRorderNo(rcvdetail.getRorderNo());
        dto.setRorderItem(rcvdetail.getRorderItem());
        dto.setCustomerNo(rcvmaster.getCustomerNo());
        dto.setUserNo(rcvmaster.getUserNo());
        dto.setEndUser(rcvmaster.getEndUser());
        dto.setOrderType(rcvdetail.getOrderType().toString());
        String orderTypeName = OrderTypeEnum.getCodeName(rcvdetail.getOrderType().toString());
        dto.setOrderTypeName(orderTypeName);
        dto.setHopeDeliveryDate(rcvdetail.getDlvDate());

        NoticeTask noticeTask = new NoticeTask(businessCode, dto.getRorderFno(), dto);
        String jsonStr = JSONUtil.toJsonStr(Collections.singleton(noticeTask));
        return jsonStr;
    }

}
