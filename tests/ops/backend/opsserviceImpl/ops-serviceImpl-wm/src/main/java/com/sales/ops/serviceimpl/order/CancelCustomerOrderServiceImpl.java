package com.sales.ops.serviceimpl.order;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.sales.ops.common.constants.Constants;
import com.sales.ops.common.errorEnum.OrderCancelCodeEnum;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.AssertUtil;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.Rcvmaster;
import com.sales.ops.db.extdao.NotifyShipmentPlanDao;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.purchase.RequestCancelDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.PurchaseCancelSourceEnum;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.feign.RequestPurchaseFeignApi;
import com.sales.ops.service.ba.BaseWarehouseService;
import com.sales.ops.service.inventory.AdjustInventoryService;
import com.sales.ops.service.inventory.WmDispatchService;
import com.sales.ops.service.log.OpsOrderModiDataService;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.BaseOrderAssignResultService;
import com.sales.ops.service.order.CancelCustomerOrderService;
import com.sales.ops.service.order.CreateTransferPlanService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.BaseRoService;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.event.v3.status.service.OrderStatusService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.OrderStateServiceFeignApi;
import com.smc.smccloud.service.PreStockFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author C12961
 * @date 2023/2/20 15:56
 */
@Slf4j
@Service
public class CancelCustomerOrderServiceImpl implements CancelCustomerOrderService {


    @Autowired
    private BaseCustomerOrderService baseCustomerOrderService;
    @Autowired
    private BaseWarehouseService baseWarehouseService;
    @Autowired
    private BaseOrderAssignResultService baseOrderAssignResultService;
    @Autowired
    private OpsOrderModiDataService opsOrderModiDataService;
    @Autowired
    private WmDispatchService wmDispatchService;
    @Autowired
    private RequestPurchaseFeignApi requestPurchaseFeignApi;
    @Autowired
    private OPSRedisUtils opsRedisUtils;
    @Autowired
    private OrderStateServiceFeignApi orderStateServiceFeignApi;
    @Autowired
    private AdjustInventoryService adjustInventoryService;
    @Autowired
    private BasePoService basePoService;
    @Autowired
    private CustomerEventPublisher customerEventPublisher;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private BaseRoService baseRoService;
    @Autowired
    private BaseDoService baseDoService;

    @Autowired
    private CreateTransferPlanService createTransferPlanService;

    @Autowired
    private NotifyShipmentPlanDao notifyShipmentPlanDao;
    @Autowired
    private PreStockFeignApi preStockFeignApi;
    @Autowired
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    public static final String DICT_CLASS_CODE = "9002";
    public static final String DICT_CODE = "34";


    public enum PoHandleType {
        NO("0", "什么也不做"),
        CANCEL_PURCHASE("1", "取消采购单"),
        TRANSFER_PLAN("2", "创建调库计划");


        private String code;
        private String desc;


        PoHandleType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }


        public static PoHandleType parse(Boolean secondProcessor, Boolean adjust) {
            if (secondProcessor != null && secondProcessor) {
                if (adjust != null && adjust) {
                    return TRANSFER_PLAN;
                } else {
                    return NO;
                }
            } else {
                return CANCEL_PURCHASE;
            }
        }
    }

    /**
     * 验证订单是否可以自动还原
     * @param rcvdetail
     * @param input
     * @param output
     * @return
     * @throws OpsException
     */
    @Override
    public int validatePurchaseInfoToAutoToInitStatus(Rcvdetail rcvdetail, AutoOrderChangeToInitStatusDto input, OrderChangeToInitStatusDto output) throws OpsException{
        if((rcvdetail.getStatus() >= RcvOrderStatusEnum.CGING.getType()
                && rcvdetail.getStatus() < RcvOrderStatusEnum.CKED.getType())
                || rcvdetail.getStatus() == RcvOrderStatusEnum.CREDIT.getType()){
            // 查询采购单信息
            List<PurchaseInfoForCancel> purchaseInfo = getPurchaseInfo(input.getOrderId(), Integer.valueOf(input.getOrderItem()));
            List<PurchaseInfoForCancel> purchases = new ArrayList<>();
            for (PurchaseInfoForCancel purchase : purchaseInfo) {
                // 如果采购可删，则客单可自动删单，采购单删单
                if (purchase.getIscandelete() == 1) {
                    output.setDelPo(true);
                    purchase.setDeleteok(true);
                    purchases.add(purchase);
                }
                // 如果采购不可删
                else if (purchase.getRisk() == 1) {
                    output.setDelPo(false);
                    purchase.setDeleteok(false);
                    purchases.add(purchase);
                    if(input.isTransfer()){
                        purchase.setTransfer(true);
                        if(StringUtils.isNotBlank(input.getEndUser())){
                            purchase.setEndUser(input.getEndUser());
                        }
                    }
                }
                // 如果是高风险或null则需要人工处理，退出自动还原
                else {
                    log.info("订单{}不允许自动还原:{}", input.getOrderId(), purchaseInfo);
                    // 采购高风险需要人工处理
                    return 2;
                }
            }
            output.setPo(purchases);
        } else {
            // 订单状态不可还原
            return 3;
        }
        //  返回可自动订单还原
        return 1;
    }

    /*****************************取消订单操作*****************************/

    /**
     * OpsException:各种原因无法执行取消操作
     * 订单未接入（rcv表里查不到）
     * 订单状态不允许（发货中之后）
     * 物流指令不允许取消（交易出库不能取消）
     *
     * @description
     * @author C12961
     * @date 2022/3/24 13:50
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public OrderCancelCodeEnum autoCancelRcvOrder(CancelForOrderDto dto) throws OpsException {
        String orderFno = dto.getOrderFno();
        Rcvdetail rcvDetail = baseCustomerOrderService.findRcvDetailByFno(orderFno);
        log.info("【开始自动删单】：{}", orderFno);
        // 1.订单状态不允许取消
        if (!enableCancel(rcvDetail)) {
            return OrderCancelCodeEnum.STATUS_NOT_ALLOWED;
        }
        // 2.校验采购单状态
        List<PurchaseInfoForCancel> purchases = validatePurchaseInfo(dto);
        if (purchases == null) {
            return OrderCancelCodeEnum.PURCHASE_NOT_ALLOWED;
        }
        // 允许自动删除
        CancelInputForOrderDto cancelInputDto = createCancelParams(dto, purchases);
        log.info("订单{}允许自动删除:{}", orderFno, JSONUtil.toJsonPrettyStr(cancelInputDto));
        OrderCancelCodeEnum resultCode = cancelRcvOrder(cancelInputDto);
        resultCode = resultCode == OrderCancelCodeEnum.SUCCESS_CANCEL ? OrderCancelCodeEnum.AUTO_SUCCESS_CANCEL : resultCode;
        log.info("客户单删单结果：{}", resultCode);
        return resultCode;
    }

    // 校验采购单状态,如果不允许自动删单，则返回null,如果允许，则设置Deleteok或Transfer
    private List<PurchaseInfoForCancel> validatePurchaseInfo(CancelForOrderDto dto) throws OpsException {
        // 解析采购单应该做什么
        PoHandleType poHandleType = PoHandleType.parse(dto.isSecondProcess(), dto.getAdjust());
        // 查询采购单信息
        List<PurchaseInfoForCancel> purchaseInfo = getPurchaseInfo(dto.getOrderId(), Integer.valueOf(dto.getOrderItem()));
        List<PurchaseInfoForCancel> purchases = new ArrayList<>();
        // 如果采购单要删单，则设置采购单删单按钮
        if (poHandleType == PoHandleType.CANCEL_PURCHASE) {
            for (PurchaseInfoForCancel purchase : purchaseInfo) {
                // 如果采购可删，则客单可自动删单，采购单删单
                if (purchase.getIscandelete() == 1) {
                    purchase.setDeleteok(true);
                    purchases.add(purchase);
                }
                // 如果采购不可删
                else if (purchase.getRisk() == 1) {
                    purchase.setDeleteok(false);
                    purchases.add(purchase);
                }
                // 如果是高风险或null则需要人工处理，退出自动删单
                else {
                    log.info("订单{}不允许自动删除:{}", dto.getOrderFno(), purchaseInfo);
                    return null;
                }
            }
        }
        // 12625 如果是调库计划，则设置调库计划按钮
        else if (poHandleType == PoHandleType.TRANSFER_PLAN) {
            for (PurchaseInfoForCancel purchase : purchaseInfo) {
                purchase.setTransfer(true);
                //bugid:14473 c14717 20240626
                if(StringUtils.isNotBlank(dto.getEndUser())){
                    purchase.setEndUser(dto.getEndUser());
                }
                purchases.add(purchase);
            }
        }
        // 既不是自动删单，也不是二次审批,则啥都不做
        else {
            purchases = purchaseInfo;
        }
        return purchases;
    }

    private CancelInputForOrderDto createCancelParams(CancelForOrderDto dto, List<PurchaseInfoForCancel> purchases) {
        CancelInputForOrderDto cancelInputForOrderDto = new CancelInputForOrderDto();
        cancelInputForOrderDto.setCancelForOrderDto(dto);
        cancelInputForOrderDto.setPurchaseList(purchases);
        return cancelInputForOrderDto;
    }

    /**
     * 取消客户订单
     *
     * @return 1：取消成功;
     * 0：物流不允许取消;
     * exception: 取消失败
     * @description 取消客户订单
     * @author C12961
     * @date 2022/3/24 10:08
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public OrderCancelCodeEnum cancelRcvOrder(CancelInputForOrderDto cancelInputDto) throws OpsException {
        CancelForOrderDto cancelDto = cancelInputDto.getCancelForOrderDto();
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(cancelDto.getOrderId(), cancelDto.getOrderItem());
        Rcvmaster rcvmaster = baseCustomerOrderService.findRcvMaster(cancelDto.getOrderId());
        if (!enableCancel(rcvdetail)) {
            log.info("{}:{}", OrderCancelCodeEnum.STATUS_NOT_ALLOWED.getDesc(), rcvdetail.getStatus());
            return OrderCancelCodeEnum.STATUS_NOT_ALLOWED;
        }
        log.info("【开始删除客户订单】：{}\n{}", cancelDto.getOrderFno(), JSONUtil.toJsonPrettyStr(cancelDto));
        //log.info("1.开始取消物流指令：");
        OrderCancelCodeEnum result = cancelDo(cancelDto, rcvdetail);
        if (result==OrderCancelCodeEnum.WM_NOT_ALLOWED) {
            return result;
        }
        // 变更订单状态事件
        //log.info("2.开始修改订单状态");
        //opsCustomerOrderStatusEventService.updateStatusForCancelRcv(cancelDto.getOrderId(), cancelDto.getOrderItem());
        orderStatusService.updateStatusToCancel(cancelDto.getOrderId(), Integer.valueOf(cancelDto.getOrderItem()));
        baseCustomerOrderService.updateStatusToCancel(cancelDto.getOrderId(), cancelDto.getOrderItem());
        //log.info("订单状态修改成功");
        //log.info("3.开始删除result表");
        baseOrderAssignResultService.deleteResultOrder(rcvdetail.getRorderNo(), rcvdetail.getRorderItem());
        //log.info("删除result表成功");
        //log.info("4.开始取消采购单");
        result = cancelPo(cancelInputDto);
        //log.info("取消采购单完成");
        //log.info("4.开始创建调库计划");
        Boolean planFl = null;
        try {
            planFl = createTransferPlanService.createTransferPlan(cancelInputDto.getPurchaseList(),
                    cancelInputDto.getCancelForOrderDto().getUserDto().getUserName(), rcvmaster.getEndUser());
        } catch (Exception e) {
            log.error("删单生成调库计划失败",e);
            planFl = false;
        }
        if(Objects.nonNull(planFl)){
            if(!planFl){
                result = OrderCancelCodeEnum.SUCCESS_CANCEL_AND_FAIL_TRANS;
            }
        }
        //log.info("5.插入modidata");
        opsOrderModiDataService.insertModiDataForCancelOrder(rcvdetail.getRorderNo(), rcvdetail.getRorderItem(), rcvdetail.getRorderFno(),
                cancelDto.getReason(), cancelDto.getDuty(), cancelDto.getOrigin());
        CustomerOrderCancelForDeliveryDateDTO eventDto = new CustomerOrderCancelForDeliveryDateDTO(cancelDto, rcvmaster,rcvdetail);
        customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_CANCEL, cancelDto.getOrderId(), Integer.valueOf(cancelDto.getOrderItem()), eventDto);
        log.info("【删除客户订单完成】：" + cancelDto.getOrderFno());
        //更新先行在库状态
        try {
            ResultVo<DataTypeVO> dict = dictDataServiceFeignApi.getDataTypeCodesInfo(DICT_CLASS_CODE, DICT_CODE);
            boolean success = dict.isSuccess();
            if (!success || dict.getData() == null) {
                throw Exceptions.OpsException("获取数据字典失败:" + dict.getMessage());
            }
            String extNote1 = dict.getData().getExtNote1();
            //1 走旧队列 2 走新事件和同步更新
            if (extNote1.equals("2") ) {
                ResultVo<String> resultVO = preStockFeignApi.purchaseOrderCancelHandle(cancelDto.getOrderFno());
                if (!resultVO.isSuccess()) {
                    log.error("先行在库订单状态变更处理异常 {},{}", cancelDto.getOrderFno(), resultVO.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("先行在库订单状态变更处理异常 {},{}", cancelDto.getOrderFno(), e.getMessage(), e);
        }
        return result;
    }




    /**
     * @description 客户订单取消--取消物流指令
     * @author C12961
     * @date 2023/2/21 16:07
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public OrderCancelCodeEnum cancelDo(CancelForOrderDto cancelDto, Rcvdetail rcvdetail) throws OpsException {
        // 如果已接单处理，则取消物流指令
        if (rcvdetail.getStatus() > RcvOrderStatusEnum.INIT.getType()
                && !Objects.equals(rcvdetail.getStatus(), RcvOrderStatusEnum.EXCEPT.getType())) {
            Boolean success = false;
            try {
                boolean isWT = false;
                if (StringUtils.isNotBlank(rcvdetail.getStockCode())) {
                    OpsWarehouse opsWarehouse = baseWarehouseService.getWarehouseByCode(rcvdetail.getStockCode());
                    if (Objects.nonNull(opsWarehouse) && WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType())) {
                        isWT = true;
                    }
                }
                if (isWT) {
                    log.info("委托在库取消订单");
                    success = wmDispatchService.cancellationOfOrderWT(cancelDto);
                } else {
                    success = wmDispatchService.cancellationOfOrder(cancelDto);
                    //通知发货  bugid:17720 c14717 20250522
                    if(success){
                        notifyShipmentPlanDao.updateNotifyShipmentFinish(cancelDto.getOrderId(),cancelDto.getOrderItem(),"删单");
                    }
                }
            } catch (Exception e) {
                log.error("{}", e);
                throw Exceptions.OpsException("物流指令取消失败:" + e.getMessage());
            }
            if (!success) {
                log.info("删除物流指令失败 ：" + JSONUtil.toJsonPrettyStr(cancelDto));
                return OrderCancelCodeEnum.WM_NOT_ALLOWED;
            }
            log.info("删除物流指令成功");
        } else {
            log.info("订单无指令，无需删除物流指令");
        }
        return OrderCancelCodeEnum.SUCCESS_CANCEL;
    }

    /**
     * @return 0代表因为低风险没删除采购单，1代表删除成功
     * @description 客户订单取消--取消采购单和请购单
     * @author C12961
     * @date 2023/2/21 16:08
     */
    private OrderCancelCodeEnum cancelPo(CancelInputForOrderDto cancelInputDto) {
        List<PurchaseInfoForCancel> purchaseList = cancelInputDto.getPurchaseList();
        boolean lowRisk = false;
        if (!CollectionUtils.isEmpty(purchaseList)) {
            for (PurchaseInfoForCancel info : purchaseList) {
                log.info("采购单实体：{}", JSONUtil.toJsonPrettyStr(info));
                if (info.isDeleteok() && !info.isMerge()) {
                    RequestCancelDto dto = new RequestCancelDto();
                    dto.setOrderno(info.getRequestno());
                    dto.setItemno(info.getRequestItemno());
                    dto.setSplititemno(info.getRequestSplitno());
                    dto.setSourceType(PurchaseCancelSourceEnum.CANCEL_CUSTOMER_ORDER.getType());
                    try {
                        log.info("取消采购单：{}", JSONUtil.toJsonPrettyStr(dto));
                        CommonResult<Boolean> booleanCommonResult = requestPurchaseFeignApi.cancelPurchase(dto);
                        Boolean result = booleanCommonResult.isSuccess();
                        if (result) {
                            log.info("采购单取消成功");
                        } else {
                            log.info("采购单取消失败");
                            // todo 通知业务
                            // todo return
                        }
                    } catch (Exception e) {
                        log.info("接口调用失败：", e);
                    }
                } else {
                    log.info("该采购单不需要删除");
                    if (info.getRisk() == 1) {
                        lowRisk = true;// 有采购单，但是没删,因为低风险
                    }
                }
            }
        } else {
            log.info("该订单无采购单关联");
        }
        return lowRisk ? OrderCancelCodeEnum.SUCCESS_CANCEL_AND_LOW_RISK:OrderCancelCodeEnum.SUCCESS_CANCEL ;
    }

    /**
     * @description 判断该客户订单能否删单
     * @author C12961
     * @date 2023/2/21 16:08
     */
    @Override
    public boolean enableCancel(Rcvdetail rcvdetail) {
        List<String> list = new ArrayList<>();
        Object obj = opsRedisUtils.get(Constants.CAN_DEL_STATUS);
        if (obj == null) {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            ResultVo<List<String>> listResultVo = orderStateServiceFeignApi.canDelOrderStatus();
            if (listResultVo.isSuccess() && CollectionUtils.isNotEmpty(listResultVo.getData())) {
                list = listResultVo.getData();
            }
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        } else {
            list = JSONArray.parseArray(obj.toString(), String.class);
        }
        // 是否可删单
        if (list.contains(rcvdetail.getStatus().toString())) {
            // 非N，出库处理中且发货数量大于0不可删
            // expQty != null && expQty != 0 && status == (int) RcvOrderStatusEnum.CKING.getType()
            if (rcvdetail.getExpQty() != null && rcvdetail.getExpQty() != 0 && Integer.valueOf(rcvdetail.getStatus()).equals(Integer.valueOf(RcvOrderStatusEnum.CKING.getType()))) {
                return false;
            }
            return true;
        } else {
            // N的不可删
            return false;
        }
    }

    private List<PurchaseInfoForCancel> getPurchaseInfo(String orderId, Integer orderItem) throws OpsException {
        CommonResult<List<PurchaseInfoForCancel>> result = requestPurchaseFeignApi.getPurchase(orderId, orderItem);
        log.info("查询是否有采购单：{}", JSONUtil.toJsonPrettyStr(result));
        AssertUtil.isTrue(result.isSuccess(), OrderCancelCodeEnum.SEARCH_PO_FAILURE.getDesc());
        List<PurchaseInfoForCancel> purchaseInfo = result.getData();
        return purchaseInfo;
    }


}
